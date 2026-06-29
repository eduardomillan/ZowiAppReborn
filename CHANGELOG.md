# Changelog

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
