# Bluetooth Regression Checklist

## Scope
Use this checklist before shipping Bluetooth-related changes or validating on a new Android device.

## 1. Device + ADB readiness
- Confirm USB debugging is enabled on the phone.
- Confirm the host is authorized (`adb devices` shows device as `device`, not `unauthorized`).
- Confirm app installs and launches with the debug task.

## 2. Runtime permissions (Android 12+)
- Grant `BLUETOOTH_SCAN`.
- Grant `BLUETOOTH_CONNECT`.
- Grant `BLUETOOTH_ADVERTISE` when requested by flow.
- Deny once and retry to verify user-facing recovery messaging.

## 3. Discovery and filtering
- Start discovery and verify at least one scan cycle reaches `DISCOVERY_FINISHED`.
- Verify target robot appears either by expected name prefix (`Zowi`) or accepted MAC prefix.
- Verify no crash occurs if a discovered device has null/empty name.

## 4. Connection and control path
- Connect to the selected robot.
- Verify initial command exchange succeeds after connection.
- Verify reconnect path after turning Bluetooth off/on.

## 5. Negative scenarios
- Robot powered off: app should fail gracefully and allow retry.
- Bluetooth disabled mid-session: app should surface disconnect state cleanly.
- Permission revoked from system settings: app should request/recover without crash.

## 6. Logging hygiene
- In release builds, verbose discovery/candidate logs should stay disabled.
- Error logs that indicate actionable failures should remain visible.

## 7. Evidence to capture
- Device model and Android version.
- App version (`versionName`/`versionCode`).
- Outcome for each section above (pass/fail + short note).
