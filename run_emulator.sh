#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
LOG_FILE="${EMULATOR_LOG_FILE:-/tmp/zowi-emulator.log}"
LOCALE_TAG="${EMULATOR_LOCALE:-es-ES}"
LANGUAGE_CODE="${LOCALE_TAG%%-*}"
COUNTRY_CODE="${LOCALE_TAG##*-}"
WINDOW_SKIN="${EMULATOR_SKIN:-1920x1080}"
WINDOW_SCALE="${EMULATOR_SCALE:-0.9}"
LANDSCAPE_ROTATION="${EMULATOR_ROTATION:-1}"
UI_DENSITY="${EMULATOR_DENSITY:-350}"
FONT_SCALE="${EMULATOR_FONT_SCALE:-1.0}"

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

wait_for_boot() {
  local boot_state=""

  adb_cmd wait-for-device >/dev/null

  for _ in $(seq 1 72); do
    boot_state="$(adb_shell getprop sys.boot_completed)"
    if [[ "${boot_state}" == "1" ]]; then
      return 0
    fi
    sleep 5
  done

  echo "El emulador no terminó de arrancar a tiempo." >&2
  return 1
}

get_current_locale() {
  adb_shell getprop persist.sys.locale
}

get_emulator_serial() {
  "${ADB_BIN}" devices | awk '/^emulator-[0-9]+[[:space:]]+device$/ {print $1; exit}'
}

adb_cmd() {
  if [[ -n "${EMULATOR_SERIAL:-}" ]]; then
    "${ADB_BIN}" -s "${EMULATOR_SERIAL}" "$@"
  else
    "${ADB_BIN}" "$@"
  fi
}

adb_shell() {
  adb_cmd shell "$@" 2>/dev/null | tr -d '\r'
}

ensure_spanish_locale() {
  local current_locale=""

  current_locale="$(get_current_locale)"
  if [[ "${current_locale}" == "${LOCALE_TAG}" || "${current_locale}" == es* ]]; then
    echo "Idioma confirmado: ${current_locale}"
    return 0
  fi

  if ! adb_cmd root >/dev/null 2>&1; then
    echo "No se ha podido obtener acceso root en el emulador para forzar el idioma ${LOCALE_TAG}." >&2
    return 1
  fi

  adb_cmd wait-for-device >/dev/null
  adb_shell setprop persist.sys.language "${LANGUAGE_CODE}" >/dev/null
  adb_shell setprop persist.sys.country "${COUNTRY_CODE}" >/dev/null
  adb_shell setprop persist.sys.locale "${LOCALE_TAG}" >/dev/null
  adb_shell stop >/dev/null
  adb_shell start >/dev/null

  wait_for_boot

  current_locale="$(get_current_locale)"
  if [[ "${current_locale}" == "${LOCALE_TAG}" || "${current_locale}" == es* ]]; then
    echo "Idioma confirmado: ${current_locale}"
    return 0
  fi

  echo "No se ha podido confirmar el idioma español. Locale actual: ${current_locale:-desconocido}" >&2
  return 1
}

ensure_landscape_mode() {
  local rotation=""

  adb_shell settings put system accelerometer_rotation 0 >/dev/null
  adb_shell settings put system user_rotation "${LANDSCAPE_ROTATION}" >/dev/null
  adb_shell settings put secure show_rotation_suggestions 0 >/dev/null

  rotation="$(adb_shell settings get system user_rotation)"
  if [[ "${rotation}" != "${LANDSCAPE_ROTATION}" ]]; then
    echo "No se ha podido confirmar la orientación horizontal. Rotación actual: ${rotation:-desconocida}" >&2
    return 1
  fi

  echo "Orientación horizontal confirmada."
}

get_current_density() {
  adb_shell wm density | awk -F': ' '/Override density|Physical density/ {print $2}' | tail -n 1
}

ensure_compact_ui() {
  local current_density=""
  local current_font_scale=""

  current_density="$(get_current_density)"
  if [[ "${current_density}" != "${UI_DENSITY}" ]]; then
    adb_shell wm density "${UI_DENSITY}" >/dev/null
  fi

  adb_shell settings put system font_scale "${FONT_SCALE}" >/dev/null

  current_density="$(get_current_density)"
  current_font_scale="$(adb_shell settings get system font_scale)"

  if [[ "${current_density}" != "${UI_DENSITY}" ]]; then
    echo "No se ha podido fijar la densidad visual a ${UI_DENSITY}. Densidad actual: ${current_density:-desconocida}" >&2
    return 1
  fi

  if [[ "${current_font_scale}" != "${FONT_SCALE}" ]]; then
    echo "No se ha podido fijar la escala de fuente a ${FONT_SCALE}. Escala actual: ${current_font_scale:-desconocida}" >&2
    return 1
  fi

  echo "Densidad visual compacta confirmada (${UI_DENSITY} dpi, fuente ${FONT_SCALE})."
}

SDK_DIR="$(find_sdk_dir)"
ADB_BIN="${SDK_DIR}/platform-tools/adb"
EMULATOR_BIN="${SDK_DIR}/emulator/emulator"
EMULATOR_SERIAL=""

if [[ ! -x "${ADB_BIN}" ]]; then
  echo "No se ha encontrado adb en ${ADB_BIN}." >&2
  exit 1
fi

if [[ ! -x "${EMULATOR_BIN}" ]]; then
  echo "No se ha encontrado emulator en ${EMULATOR_BIN}." >&2
  exit 1
fi

AVD_NAME="${1:-}"
if [[ -z "${AVD_NAME}" ]]; then
  mapfile -t AVAILABLE_AVDS < <("${EMULATOR_BIN}" -list-avds)
  if [[ "${#AVAILABLE_AVDS[@]}" -eq 0 ]]; then
    echo "No hay AVDs disponibles." >&2
    exit 1
  fi
  AVD_NAME="${AVAILABLE_AVDS[0]}"
fi

EXTRA_ARGS=()
if [[ -z "${DISPLAY:-}" && -z "${WAYLAND_DISPLAY:-}" ]]; then
  EXTRA_ARGS+=(-no-window -gpu swiftshader_indirect)
else
  EXTRA_ARGS+=(-skin "${WINDOW_SKIN}" -scale "${WINDOW_SCALE}")
fi

if ! "${ADB_BIN}" devices | grep -q '^emulator-.*device$'; then
  echo "Lanzando el AVD ${AVD_NAME}..."
  nohup "${EMULATOR_BIN}" \
    -avd "${AVD_NAME}" \
    -no-snapshot-load \
    -no-boot-anim \
    -prop persist.sys.language="${LANGUAGE_CODE}" \
    -prop persist.sys.country="${COUNTRY_CODE}" \
    -prop persist.sys.locale="${LOCALE_TAG}" \
    "${EXTRA_ARGS[@]}" \
    >"${LOG_FILE}" 2>&1 &
else
  echo "Ya hay un emulador en ejecución; se reutilizará."
fi

EMULATOR_SERIAL="$(get_emulator_serial)"
wait_for_boot
EMULATOR_SERIAL="$(get_emulator_serial)"
ensure_spanish_locale
ensure_landscape_mode
ensure_compact_ui

echo "Emulador listo en español, horizontal y con interfaz compacta."
echo "AVD: ${AVD_NAME}"
echo "Serial: ${EMULATOR_SERIAL}"
echo "Log: ${LOG_FILE}"
