# Changelog

## 1.9.1.4 - 2026-06-29

### Added
- Added global app version visibility in all screens (title includes version and top-right version badge).

### Changed
- App update destination and settings messaging were aligned to GitHub/GitHub Packages distribution flow.
- Release/tag naming was normalized for 1.9.1.3 (`v1.9.1.3` replaced by `1.9.1.3` in remote tags).

### Fixed
- Fixed intermittent black-screen behavior on Huawei Android 8 transitions by replacing static `R.anim` transition references with runtime animation name resolution and guarded application.
- Fixed user-visible `false` text leaks in interactive status and settings flows by resolving critical strings by resource name at runtime with fallback.
- Preserved previous no-Zowi flow/session behavior and runtime stability while applying the above fixes.

## 1.9.1.3 - 2026-06-29

### Added
- Added asset-based app configuration file at app/src/main/assets/app_config.properties for external URL values.
- Added session flag persistence to remember wizard dismissal when user selects "No tengo un Zowi".

### Changed
- Settings URLs for Hospital and Play Store are now loaded from app config instead of hardcoded literals.
- Wizard dismissal now stores user intent and Splash routing respects it to open Home on next launch.
- ProjectViewActivity startup view binding was hardened with resolved IDs and null-guarded listeners.

### Fixed
- Improved resilience of Project screen startup bindings to reduce null reference crashes in legacy/resource-mismatch scenarios.
