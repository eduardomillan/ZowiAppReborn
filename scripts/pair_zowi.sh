#!/usr/bin/env bash
# pair_zowi.sh — Scan, pair, and bind a Zowi robot via Bluetooth on Linux
# Tested on Lliurex 23 / Ubuntu 22.04 (bluez 5.66+)
set -euo pipefail

BLUE="\033[0;34m"
GREEN="\033[0;32m"
RED="\033[0;31m"
YELLOW="\033[0;33m"
NC="\033[0m"

RFCOMM_DEVICE="/dev/rfcomm0"
SCAN_DURATION=10

info()  { echo -e "${BLUE}[INFO]${NC}  $*"; }
ok()    { echo -e "${GREEN}[OK]${NC}    $*"; }
warn()  { echo -e "${YELLOW}[WARN]${NC}  $*"; }
fail()  { echo -e "${RED}[FAIL]${NC}  $*"; }

require_root() {
    if [[ $EUID -ne 0 ]]; then
        fail "This script must be run as root (for rfcomm bind)."
        echo "  Usage: sudo $0"
        exit 1
    fi
}

require_deps() {
    for cmd in bluetoothctl rfcomm hcitool; do
        if ! command -v "$cmd" &>/dev/null; then
            fail "Missing dependency: $cmd"
            echo "  Install with: sudo apt install bluez bluez-tools"
            exit 1
        fi
    done
}

ensure_bluetooth_on() {
    info "Ensuring Bluetooth is powered on..."
    bluetoothctl power on &>/dev/null
    sleep 1

    local state
    state=$(bluetoothctl show | grep "Powered:" | awk '{print $2}')
    if [[ "$state" != "yes" ]]; then
        fail "Bluetooth adapter is off. Enable it manually and retry."
        exit 1
    fi
    ok "Bluetooth adapter is powered on."
}

scan_for_zowi() {
    info "Scanning for Bluetooth devices for ${SCAN_DURATION}s..."
    info "Make sure your Zowi is turned ON and in pairing mode (hold power button)."
    echo ""

    bluetoothctl scan on &>/dev/null
    sleep "$SCAN_DURATION"
    bluetoothctl scan off &>/dev/null

    info "Discovered devices:"
    echo ""
    bluetoothctl devices | tee /tmp/zowi_scan.txt
    echo ""
}

select_device() {
    local mac_list=()

    while IFS= read -r line; do
        local mac
        mac=$(echo "$line" | awk '{print $2}')
        mac_list+=("$mac")
    done < <(bluetoothctl devices)

    if [[ ${#mac_list[@]} -eq 0 ]]; then
        fail "No Bluetooth devices found."
        echo "  Tips:"
        echo "  - Ensure Zowi is powered on"
        echo "  - Hold the power button 3s to enter pairing mode (LED blinks fast)"
        echo "  - Move the Zowi closer to your adapter"
        exit 1
    fi

    echo -e "${BLUE}Enter the Zowi MAC address from the list above${NC}"
    echo -e "(e.g. AA:BB:CC:DD:EE:FF), or press Enter to scan again:"
    read -rp "> " user_mac

    if [[ -z "$user_mac" ]]; then
        scan_for_zowi
        select_device
        return
    fi

    ZOWI_MAC="$user_mac"
    info "Selected: $ZOWI_MAC"
}

pair_device() {
    info "Pairing with $ZOWI_MAC..."

    bluetoothctl agent on &>/dev/null
    bluetoothctl default-agent &>/dev/null

    local pair_output
    pair_output=$(bluetoothctl pair "$ZOWI_MAC" 2>&1)
    echo "$pair_output"

    if echo "$pair_output" | grep -q "Failed\|not available\|Connection refused"; then
        fail "Pairing failed."
        echo "  Tips:"
        echo "  - Remove old pairing: bluetoothctl remove $ZOWI_MAC"
        echo "  - Try again after resetting Zowi (hold power 5s)"
        exit 1
    fi

    ok "Paired successfully."
}

trust_device() {
    info "Trusting $ZOWI_MAC..."
    bluetoothctl trust "$ZOWI_MAC" &>/dev/null
    ok "Device trusted."
}

cleanup_old_rfcomm() {
    if [[ -e "$RFCOMM_DEVICE" ]]; then
        warn "Old $RFCOMM_DEVICE exists, releasing..."
        rfcomm release "$RFCOMM_DEVICE" 2>/dev/null || true
        sleep 1
    fi
}

bind_rfcomm() {
    cleanup_old_rfcomm

    info "Binding RFCOMM channel 1 -> $RFCOMM_DEVICE..."
    rfcomm bind "$RFCOMM_DEVICE" "$ZOWI_MAC" 1

    sleep 1
    if [[ -e "$RFCOMM_DEVICE" ]]; then
        ok "RFCOMM bound: $RFCOMM_DEVICE -> $ZOWI_MAC"
    else
        fail "RFCOMM binding failed. $RFCOMM_DEVICE not created."
        echo "  Try channel 2: sudo rfcomm bind /dev/rfcomm0 $ZOWI_MAC 2"
        exit 1
    fi
}

print_next_steps() {
    echo ""
    echo -e "${GREEN}========================================${NC}"
    echo -e "${GREEN}  Zowi is ready!${NC}"
    echo -e "${GREEN}========================================${NC}"
    echo ""
    echo "  Connect with the CLI:"
    echo "    ./gradlew :zowi-cli:run --args=\"connect $RFCOMM_DEVICE\""
    echo ""
    echo "  Test connection:"
    echo "    ./gradlew :zowi-cli:run --args=\"status\""
    echo "    ./gradlew :zowi-cli:run --args=\"send happy\""
    echo ""
    echo "  When done, release the port:"
    echo "    sudo rfcomm release $RFCOMM_DEVICE"
    echo ""
}

save_state() {
    local state_file="/tmp/zowi_pair_state"
    cat > "$state_file" <<EOF
ZOWI_MAC=$ZOWI_MAC
RFCOMM_DEVICE=$RFCOMM_DEVICE
PAIRED_AT=$(date -Iseconds)
EOF
    info "State saved to $state_file"
}

main() {
    echo ""
    echo -e "${BLUE}=== Zowi Bluetooth Pairing Script ===${NC}"
    echo ""

    require_root
    require_deps
    ensure_bluetooth_on
    scan_for_zowi
    select_device
    pair_device
    trust_device
    bind_rfcomm
    save_state
    print_next_steps
}

main "$@"
