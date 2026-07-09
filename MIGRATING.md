# Migration Feasibility Analysis

This document summarizes the analysis performed on 2026-07-09 regarding the feasibility of migrating the ZowiAppReborn project from its current native Android (Java) implementation to alternative platforms.

## Current Project Overview

ZowiAppReborn is a native Android application written in Java (minSdk 21, targetSdk 34) that controls the Zowi bipedal robot over **Bluetooth Classic SPP (RFCOMM)** using the standard Serial Port Profile UUID (`00001101-0000-1000-8000-00805F9B34FB`). The robot is based on an ATmega32u4 microcontroller with an **HC-06/HC-05 Bluetooth Classic module**, which does not support Bluetooth Low Energy (BLE).

## Key Findings

### Bluetooth Classic is a hardware requirement, not a software choice

The Zowi robot ships with an HC-06/HC-05 module that only speaks Bluetooth Classic SPP. Switching to BLE would require physically replacing the Bluetooth module on the robot and reflashing its firmware. No amount of software changes can make the original hardware communicate over BLE.

### Bluetooth Classic compatibility on recent Android versions

Bluetooth Classic remains fully supported on Android through API 34 (Android 14) and beyond. Google has not deprecated or removed SPP support. What has changed is the permissions model:
- Android 12+ introduced dedicated runtime permissions (`BLUETOOTH_SCAN`, `BLUETOOTH_CONNECT`, `BLUETOOTH_ADVERTISE`)
- The app already targets API 34 and follows the modern permissions model

An APK compiled against API 36 (Android 16) will run on older versions (down to minSdk 21) as long as behavioral changes per API level are reviewed and no unsupported APIs are used without SDK version checks.

### Flutter is not recommended

Flutter's Bluetooth ecosystem is centered on BLE (`flutter_blue`). Bluetooth Classic SPP support relies on community packages (`flutter_bluetooth_serial`) with limited maturity. The firmware flashing protocol (STK500v1 over raw serial) would require platform channels with native code, negating Flutter's primary advantage of code sharing.

### Web (browser-based) is not feasible

The Web Bluetooth API only supports BLE. There is no mechanism to access Bluetooth Classic SPP from a browser. A web-based solution would require an intermediate bridge device (e.g., ESP32 or Raspberry Pi) connected to the robot via serial and exposing a WebSocket API.

### Desktop cross-platform is feasible

Bluetooth Classic SPP is fully supported on both Windows (via `Windows.Devices.Bluetooth.Rfcomm` or WinSock) and Linux (via BlueZ RFCOMM sockets). Multiple technology options exist.

## Recommended Approach: Qt (C++ with QML)

After evaluating all viable options, **Qt (C++) with QML** is the recommended target for a desktop migration:

| Criterion | Qt | Electron |
|---|---|---|
| Bluetooth Classic SPP | Native `QBluetoothSocket`, maintained by Qt Group | `node-bluetooth-serial-port`, a niche npm package |
| Firmware flashing (STK500v1) | Direct in C++ with full control over timing and threading | Possible but fragile via Node.js native bindings |
| Binary size / RAM | ~20-40 MB / ~40-60 MB | ~120-150 MB / ~150-300 MB |
| UI development speed | Medium (QML helps) | Fast (HTML/CSS/JS ecosystem) |
| Long-term maintenance risk | Low (Qt Group, 30+ year track record, LTS releases) | Medium (Bluetooth package maintainer risk) |
| Cross-platform | Native on both Windows and Linux | Works on both, but Bluetooth layer adds fragility |

### Why not Electron

The single point of failure for Electron is Bluetooth Classic support. The `node-bluetooth-serial-port` package is maintained by a small number of contributors. If it becomes incompatible with a future Node.js, Windows, or BlueZ update, the application's core functionality is blocked. For a robot control application where Bluetooth is the only communication channel, this dependency risk is unacceptable.

## Summary

- **Web**: Not possible (no Bluetooth Classic in browsers)
- **Flutter**: Not recommended (BLE-centric ecosystem, platform channels required for SPP)
- **Electron**: Possible but fragile (Bluetooth SPP dependency risk)
- **Qt (C++ + QML)**: **Recommended** — native Bluetooth Classic, long-term stability, appropriate performance profile, and the closest conceptual match to the current Java architecture.
