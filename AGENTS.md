# AGENTS.md — ZowiAppReborn

Android app that revives the original 2016 BQ Zowi robot experience. A Gradle multi-module project, not a single module.

## Modules (this is a 3-module repo, not one)

- `app` — `com.android.application`, the Android UI (MVP). `minSdk 21`, `targetSdk 34`, Java/Kotlin 11. Depends on `:zowi-core`.
- `zowi-core` — `java-library` + Kotlin, platform-agnostic domain (`api/`, `usecases/`, `models/`). No Android deps; has JUnit+Mockito tests.
- `zowi-cli` — standalone JVM `application` (`com.bq.zowi.cli.ZowiCliKt`) that drives the robot over serial/BT without Android. Depends on `:zowi-core`, jSerialComm.

## Build & verify commands

- Build debug APK: `./gradlew :app:assembleDebug`
- Run app unit tests (none committed yet under `app/src/test`): `./gradlew :app:testDebugUnitTest`
- Run domain/CLI unit tests (these DO exist): `./gradlew :zowi-core:test` and `./gradlew :zowi-cli:test`
- Build + test the CLI: `./gradlew :zowi-cli:build`
- Run the CLI: `./gradlew :zowi-cli:run --args="help"` (commands: `scan`, `connect <port>`, `send ...`, `battery`, `achievements`, `rankings <game>`, etc.)
- Lint: `./gradlew :app:lintDebug` (note: `lintOptions.abortOnError = false`, lint does not fail the build)
- Install/launch on device/emulator: `./run_app.sh [AVD]` (starts emulator if needed); `./run_emulator.sh [AVD]` boots an emulator in Spanish, landscape, density 350.

JDK is pinned in `gradle.properties` to `org.gradle.java.home` (JDK 17). AVDs and the Android SDK are auto-discovered by the shell scripts (`ANDROID_SDK_ROOT`/`ANDROID_HOME`/`local.properties`).

## Architecture conventions (non-obvious)

- Android UI follows **Activity + Presenter + Wireframe**. Keep screen logic in `presenters/`, navigation in `wireframes/` (do not start activities directly from presenters/controllers).
- Dependency creation is centralized in `DependencyInjector` / `AndroidDependencyInjector`. Reuse it; do not hand-construct controllers/interactors.
- Interactive robot screens should extend `InteractiveBaseActivity` / `InteractiveBasePresenterImpl` (owns connection banner, reconnect, battery, firmware, pairing wizard).
- Robot communication is layered in `app/adapters/`: `BTAdapterController*`, `BTConnectionController*`, `ZowiDataController*`, and the `ConnectToZowi* / SendCommandToZowi* / SendAppToZowi*` interactors. The CLI mirrors these as `zowi-cli/adapters/Cli*` over serial.
- App content is **asset-driven**: `app/src/main/assets/projects/*.json`, `assets/achievements/initial_list.json`, `.hex` firmware payloads, `assets/app_config.properties`. Static game/project content belongs in assets + resource IDs, not hardcoded objects.
- Persistent state uses `SharedPreferences` only (no Room/SQLite), via `*ControllerImpl` classes; keys use `_progress`, `_ranking`, `_project_completeness`, `_project_quiz_blockade` suffixes.
- UI frequently resolves layouts/view IDs by **resource name first** (`setResolvedContentView`, `findResolvedView`, `resolveViewId`) before falling back to generated `R` IDs. Preserve that pattern.

## Source-set gotcha

`app/build.gradle` compiles `src/main/java` plus `src/main/stubs` and excludes bundled third-party source trees kept in-repo for compatibility. Be careful moving source files or changing source sets.

## Bluetooth / hardware

- The robot requires **Bluetooth Classic SPP** at 115200 baud, 8N1. No BLE.
- For Bluetooth changes, use `docs/project/bluetooth-regression-checklist.md` as the validation checklist (Android 12+ permissions, pairing, regression).
- CLI serial pairing on Linux: `sudo ./scripts/pair_zowi.sh` (binds `/dev/rfcomm0`).

## Docs

Detailed design lives in `docs/project/` (start with `ARCHITECTURE.md`, `IMPLEMENTATION.md`, `DESIGN.md`). Copilot-specific guidance is in `.github/copilot-instructions.md`. CLI usage: `docs/project/ZOWI_CLI_ANDROID_HOWTO.md`.
