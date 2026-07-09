#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
APP_ID="${APP_ID:-com.bq.zowi}"
GRADLEW_BIN="${ROOT_DIR}/gradlew"
GRADLE_TASK="${RUN_APP_GRADLE_TASK:-:app:installDebug}"
DEFAULT_ACTIVITY="${APP_ID}/com.bq.zowi.views.splash.SplashViewActivity"

find_sdk_dir() {
  local sdk_dir

  if [[ -n "${ANDROID_SDK_ROOT:-}" && -d "${ANDROID_SDK_ROOT}" ]]; then
    echo "${ANDROID_SDK_ROOT}"
    return 0
  fi

  if [[ -n "${ANDROID_HOME:-}" && -d "${ANDROID_HOME}" ]]; then
    echo "${ANDROID_HOME}"
    return 0
  fi

  if [[ -f "${ROOT_DIR}/local.properties" ]]; then
    sdk_dir="$(awk -F= '/^sdk.dir=/{print $2}' "${ROOT_DIR}/local.properties" | tail -n 1)"
    if [[ -n "${sdk_dir}" && -d "${sdk_dir}" ]]; then
      echo "${sdk_dir}"
      return 0
    fi
  fi

  for sdk_dir in "${HOME}/Android/Sdk" "${HOME}/Android" "/opt/android-sdk" "/usr/lib/android-sdk"; do
    if [[ -d "${sdk_dir}" ]]; then
      echo "${sdk_dir}"
      return 0
    fi
  done

  echo "No se ha encontrado el Android SDK." >&2
  return 1
}

get_device_serial() {
  "${ADB_BIN}" devices | awk '/^[^[:space:]]+[[:space:]]+device$/ {print $1; exit}'
}

adb_cmd() {
  if [[ -n "${DEVICE_SERIAL:-}" ]]; then
    "${ADB_BIN}" -s "${DEVICE_SERIAL}" "$@"
  else
    "${ADB_BIN}" "$@"
  fi
}

wait_for_device() {
  adb_cmd wait-for-device >/dev/null
  for _ in $(seq 1 72); do
    if [[ "$(adb_cmd shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')" == "1" ]]; then
      return 0
    fi
    sleep 5
  done

  echo "El dispositivo no terminó de arrancar a tiempo." >&2
  return 1
}

ensure_device() {
  local avd_name="${1:-}"

  DEVICE_SERIAL="$(get_device_serial)"
  if [[ -n "${DEVICE_SERIAL}" ]]; then
    wait_for_device
    return 0
  fi

  if [[ ! -x "${ROOT_DIR}/run_emulator.sh" ]]; then
    echo "No hay dispositivo conectado y falta ${ROOT_DIR}/run_emulator.sh para arrancar uno." >&2
    return 1
  fi

  echo "No hay dispositivo listo; arrancando emulador..."
  if [[ -n "${avd_name}" ]]; then
    "${ROOT_DIR}/run_emulator.sh" "${avd_name}"
  else
    "${ROOT_DIR}/run_emulator.sh"
  fi

  DEVICE_SERIAL="$(get_device_serial)"
  if [[ -z "${DEVICE_SERIAL}" ]]; then
    echo "No se ha encontrado ningún dispositivo después de arrancar el emulador." >&2
    return 1
  fi

  wait_for_device
}

resolve_launchable_activity() {
  local activity=""

  activity="$(adb_cmd shell cmd package resolve-activity --brief "${APP_ID}" 2>/dev/null | tr -d '\r' | tail -n 1)"
  if [[ -n "${activity}" && "${activity}" == */* ]]; then
    echo "${activity}"
    return 0
  fi

  echo "${DEFAULT_ACTIVITY}"
}

if [[ ! -x "${GRADLEW_BIN}" ]]; then
  echo "No se ha encontrado gradlew en ${GRADLEW_BIN}." >&2
  exit 1
fi

SDK_DIR="$(find_sdk_dir)"
ADB_BIN="${SDK_DIR}/platform-tools/adb"
DEVICE_SERIAL=""

if [[ ! -x "${ADB_BIN}" ]]; then
  echo "No se ha encontrado adb en ${ADB_BIN}." >&2
  exit 1
fi

AVD_NAME="${1:-}"
ensure_device "${AVD_NAME}"

echo "Compilando e instalando la app..."
(
  cd "${ROOT_DIR}"
  "${GRADLEW_BIN}" --no-daemon "${GRADLE_TASK}"
)

DEVICE_SERIAL="$(get_device_serial)"
wait_for_device

LAUNCH_ACTIVITY="$(resolve_launchable_activity)"

echo "Abriendo la app..."
adb_cmd shell am force-stop "${APP_ID}" >/dev/null 2>&1 || true
adb_cmd shell am start -W -n "${LAUNCH_ACTIVITY}" >/dev/null

echo "App desplegada y abierta."
echo "Dispositivo: ${DEVICE_SERIAL}"
echo "Actividad: ${LAUNCH_ACTIVITY}"
