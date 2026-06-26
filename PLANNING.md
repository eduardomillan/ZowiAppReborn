# ZowiAppReborn - Project Plan and Progress Tracking (Status as of 2026-06-26)

## 1) Project goal
Recover and stabilize the Zowi Android app so that it:
- Builds reliably with the current toolchain.
- Starts without crashes on modern emulator/device targets (API 33/34+).
- Preserves core functionality while fixing legacy/decompiled artifacts.

## 2) Current status (executive summary)
- Build: OK (`assembleDebug` succeeded in recent validations).
- Install: OK (`adb install -r` succeeded).
- Initial launch: OK (app reaches and remains in `WelcomeViewActivity` in foreground).
- Original startup crash: mitigated.
- Residual risk: many navigation paths are still untested after recent fixes, so deeper screens may still fail.

## 3) Main technical issue identified
In a large part of legacy/decompiled code, there are direct references to resource IDs (`R.layout`, `R.id`, `R.color`, `R.integer`, `R.dimen`, `R.drawable`, etc.) that do not always match the actual runtime resource table.

This caused errors such as:
- `Resources$NotFoundException`
- `InflateException`
- `NullPointerException` after `findViewById(...)` returning `null`

## 4) Strategy applied
An incremental and low-risk strategy was adopted:
- Resolve resources by name at runtime using `getIdentifier(...)`, with fallback to original `R.*` IDs.
- Introduce reusable helpers to avoid repeating lookup logic.
- Fix the startup-critical path first (Splash/Welcome/MakerBox), then extend to similar patterns.
- Validate every iteration with a short loop: build -> install -> launch -> `logcat` review.

## 5) Work completed so far (high level)
### 5.1 Deployment and debugging infrastructure
- Updated tasks to automate emulator/build/install/launch flows.
- Added more robust wait sequences to avoid ADB/Package Manager race conditions.

### 5.2 Startup stabilization on modern Android
- Added mitigation in error reporting integration (Bugsnag) to avoid receiver registration crashes on newer API levels.

### 5.3 Resource resolution refactor
- Added dynamic resource-resolution approach in base activities and utility helpers.
- Migrated key screens/components to reduce dependency on stale decompiled IDs.

### 5.4 Welcome/MakerBox chain hardening
- Applied fixes in `MakerBox` and `MakerBoxDialog*` family:
  - Layout/ID/drawable/anim/dimen lookup by resource name.
  - Defensive null checks for views/listeners.
  - Corrected `TypedArray` usage (avoid double recycle).
- Result: Welcome no longer crashes in current smoke tests.

## 6) Collaboration conventions going forward
- When touching legacy screens, prioritize name-based runtime resolution for critical resources.
- Keep `R.*` fallback where applicable.
- Avoid large unvalidated changes; prefer incremental steps.
- After each change block, always run:
  1. `./gradlew assembleDebug`
  2. `adb install -r .../app-debug.apk`
  3. Launch and inspect `AndroidRuntime` in `logcat`

## 7) Recommended work plan (next milestones)
### Milestone A - Core navigation coverage
- Walk the app flow from Welcome to Home/Settings/Wizard/Interactive.
- Log and fix newly discovered crashes by priority (blockers first).

### Milestone B - Preventive pattern sweep
- Detect sensitive direct resource usages in interactive activities/components.
- Apply dynamic resolution helpers to high-risk points.

### Milestone C - Quality hardening
- Add a smoke-test checklist for critical screens.
- Document validated and pending routes in each PR.

### Milestone D - Progressive technical cleanup
- Reduce decompiled-code debt in touched areas.
- Preserve compatibility without introducing visual/behavioral regressions.

## 8) Open risks
- Failures may still appear in untested routes.
- Legacy resource inconsistencies may remain in less-used modules.
- Emulator vs. physical-device differences may affect timing/Bluetooth/permissions.

## 9) Definition of done for this phase
This phase is considered complete when:
- Startup is stable and repeatable.
- At least one functional end-to-end basic route runs without crash (Welcome -> interactive flow -> return).
- There is a clear log of validated and pending paths.

## 10) Latest verification snapshot
- Debug build: successful.
- APK installation: successful.
- Process alive after launch: yes.
- Foreground activity in latest check: `WelcomeViewActivity`.

---
This document is intended for fast collaborator onboarding and coordination of next iterations.
