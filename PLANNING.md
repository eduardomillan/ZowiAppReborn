# ZowiAppReborn - Project Plan and Progress Tracking (Status as of 2026-07-12)

## 1) Project goal
Recover and stabilize the Zowi Android app so that it:
- Builds reliably with the current toolchain.
- Starts without crashes on modern emulator/device targets (API 33/34+).
- Preserves core functionality while fixing legacy/decompiled artifacts.

## 2) Current status (executive summary)
- Build: OK (`assembleDebug` succeeded in recent validations).
- Install: OK (`adb install -r` succeeded).
- Runtime navigation: OK on main interactive routes under current smoke checks.
- Release management: version `1.9.2.0` published (`master` + tag `1.9.2.0`).
- Original startup crash: mitigated.
- Residual risk: broad route coverage is improved but still incomplete across all features/device variants.
- Dead code cleanup: analytics SDKs, decompiled libraries, and redundant source files removed (~20,200+ lines).

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

### 5.5 Milestone A progress (Home/Wizard path)
- Migrated `HomeViewActivity` and `WizardViewActivity` view lookups to name-based ID resolution with fallbacks.
- Added null-safe listener/view handling in both activities to prevent startup/navigation NPEs.
- Fixed `EduBar` inflation crash in Wizard by resolving layout/view/dimen resources by name with fallback IDs.
- Validation result: app process stays alive and reaches `WizardViewActivity` as resumed activity in runtime checks.

### 5.6 Settings/Timeline follow-up
- Migrated `SettingsViewActivity` and `CalibrationViewActivity` to name-based view resolution and null-safe dialog handling.
- Migrated `TimelineActivity` and its row/adapters (`CommandsTileViewHolder`, `TimelineDraggableItemAdapter`) to resilient resource lookup.
- Validation result: debug build succeeds and launcher smoke test completes without `AndroidRuntime` fatal output in the latest run.

### 5.7 Release and UX/runtime hardening (latest)
- Version updated to `1.9.1.4` and published to `origin/master` with release tag `1.9.1.4`.
- Added global app version visibility across activities (title text + top-right badge).
- Updated app update destination and settings wording from Google Play to GitHub/GitHub Packages flow.
- Confirmed and persisted "No tengo un Zowi" behavior across relaunches.
- Investigated Huawei tablet black-screen reports and applied transition hardening:
  - Replaced static `R.anim.*` transition usage in interactive base activity with runtime animation lookup by name.
  - Added guard to avoid applying transitions when animation lookup fails.
- Fixed occasional user-visible `false` text in status/dialog feedback by resolving critical strings by name at runtime (with fallback IDs).
- Performed physical-device validation on Huawei AGS2-L09 (Android 8):
  - Build/install and Bluetooth flow validated.
  - No black-screen recurrence observed after latest patch in current checks.

### 5.8 Tag normalization
- Normalized legacy release tag naming. Example:`1.9.1.3`

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
- Emulator vs. physical-device differences may affect timing/Bluetooth/permissions and graphics behavior.
- Additional long-session Bluetooth tests are still pending for full confidence on stability.

## 9) Definition of done for this phase
This phase is considered complete when:
- Startup is stable and repeatable.
- At least one functional end-to-end basic route runs without crash (Welcome -> interactive flow -> return).
- There is a clear log of validated and pending paths.

## 10) Latest verification snapshot
- Debug build: successful.
- APK installation: successful.
- Process alive after launch/navigation: yes.
- Foreground activity in latest physical-device check: interactive Home flow stable.
- AndroidRuntime fatal in latest run: none.
- Black-screen symptom on Huawei tablet after latest transition fix: not reproduced in current validation.
- Release state: `master` updated and tag `1.9.2.0` published.

## 11) Version history

| Version | Date | Tag | Description |
|---|---|---|---|
| `1.9.1.3` | 2026-06-29 | `1.9.1.3` | Stabilize no-Zowi flow, config URLs, Home/Wizard startup, resource resolution refactor, Gradle/AGP compatibility fix |
| `1.9.1.4` | 2026-06-29 | `1.9.1.4` | Bluetooth stability, UI text fixes, global version display, Huawei black-screen transition hardening |
| `1.9.1.5` | 2026-06-30 | `1.9.1.5` | Restore original robot functions, achievements unlocking conditions and documentation |
| `1.9.1.6` | 2026-07-09 | `1.9.1.6` | Fix Discover project rendering, local run tooling, emulator launch script |
| — | 2026-07-12 | `removed_analytics_bis` | Remove all analytics and decompiled SDK dead code (Adobe, comScore, BQ Analytics, Google Ads, Gson sources). ~20,200 lines deleted |
| `1.9.2.0` | 2026-07-12 | `1.9.2.0` | Remove decompiled Bugsnag, Retrofit, JetBrains/IntelliJ annotations, duplicate R.java files. Clean up build.gradle excludes |

---
This document is intended for fast collaborator onboarding and coordination of next iterations.
