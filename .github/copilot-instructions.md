# Copilot instructions for ZowiAppReborn

## Build, test, and lint commands

This repository is a single-module Android app built with the Gradle wrapper.

- Build the debug APK: `./gradlew :app:assembleDebug`
- Install the debug APK on a connected device/emulator: `./gradlew :app:installDebug`
- Run the full module build: `./gradlew :app:build`
- Run Android lint: `./gradlew :app:lintDebug`
- Run unit tests: `./gradlew :app:testDebugUnitTest`
- Run one unit test class or method: `./gradlew :app:testDebugUnitTest --tests "com.bq.zowi.SomeTest"` or `./gradlew :app:testDebugUnitTest --tests "com.bq.zowi.SomeTest.someMethod"`
- Run instrumentation tests on a connected device: `./gradlew :app:connectedDebugAndroidTest`
- Run one instrumentation test class or method: `./gradlew :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.bq.zowi.SomeTest` or `./gradlew :app:connectedDebugAndroidTest -Pandroid.testInstrumentationRunnerArguments.class=com.bq.zowi.SomeTest#someMethod`

There are currently no committed test sources under `app/src/test` or `app/src/androidTest`, so the test tasks exist even if they have little or nothing to execute right now.

For local app runs, prefer the repository scripts:

- `./run_emulator.sh [AVD_NAME]` starts an emulator in Spanish, landscape, with compact UI density.
- `./run_app.sh [AVD_NAME]` builds, installs, and launches the app, starting the emulator first if needed.

For Bluetooth-focused changes, use `docs/project/bluetooth-regression-checklist.md` as the repository's validation checklist.

## High-level architecture

The app recreates the original 2016 BQ Zowi Android experience as a single `app` module. The manifest declares only activities, all forced to landscape, and the launch flow is `SplashViewActivity -> Welcome/Wizard or Home` depending on saved session state.

The UI follows a consistent **Activity + Presenter + Wireframe** split:

- `views/**` contains activities and base UI classes.
- `presenters/**` owns screen behavior, Rx subscriptions, and orchestration.
- `wireframes/**` handles navigation by starting activities.

Dependency creation is centralized in `DependencyInjector` and `AndroidDependencyInjector`. `ZowiApplication` initializes the injector once, presenters are created from it, and activities immediately bind themselves to a presenter and wireframe in `resolvePresenter()`.

Interactive screens such as Home, Settings, Timeline, Pad, Projects, and minigames all inherit shared robot-state behavior from `InteractiveBaseActivity` and `InteractiveBasePresenterImpl`. That shared layer owns the connection banner, reconnect flow, battery handling, firmware installation dialogs, and relaunching the pairing wizard.

Robot communication is layered:

- `BTAdapterController*` handles Bluetooth enable/disable and discovery.
- `BTConnectionController*` owns the raw transport and firmware flashing stream.
- `ZowiDataController*` parses incoming robot messages into battery, distance, noise, app ID, and robot name events.
- `ConnectToZowiInteractor*`, `SendCommandToZowiInteractor*`, and `SendAppToZowiInteractor*` compose those controllers into connection, command, and firmware/programming flows.
- `KitonNetworkController*` and `KitonNetworkService` send the robot/app heartbeat telemetry after connection and firmware updates.

App content is partly asset-driven. `app/src/main/assets/projects/*.json` defines projects and quizzes, `assets/achievements/initial_list.json` bootstraps achievements, firmware/app payloads are stored as `.hex` assets, and `assets/app_config.properties` supplies settings URLs. Persistent runtime state is stored in `SharedPreferences` through controller classes such as `SessionControllerImpl`, `GameControllerImpl`, `RankingControllerImpl`, `ProjectControllerImpl`, and `AchievementsControllerImpl`; there is no Room or SQLite layer.

## Key conventions

- Keep new screen logic in presenters, not activities. Activities mostly bind views, forward clicks, and render presenter output.
- Use wireframes for navigation instead of starting activities directly from presenters or controllers.
- Reuse the injector instead of manually constructing cross-cutting dependencies. Most controllers and interactors are cached and shared there.
- For interactive robot screens, extend the existing `InteractiveBaseActivity` / `InteractiveBasePresenterImpl` path instead of reimplementing connection, battery, or firmware UX.
- Static game/project content belongs in assets plus resource IDs, not hardcoded Java objects. `ProjectControllerImpl` and `AchievementsControllerImpl` are the reference patterns.
- UI code frequently resolves layouts and view IDs by resource name first (`setResolvedContentView`, `findResolvedView`, `resolveViewId`) and only then falls back to the generated `R` ID. Preserve that pattern when touching activities or pager pages.
- Session/game progress is conventionally stored with `SharedPreferences` key suffixes (`_progress`, `_ranking`, `_project_completeness`, `_project_quiz_blockade`) rather than a database migration-heavy store.
- `app/build.gradle` intentionally compiles both `src/main/java` and `src/main/stubs`, while excluding bundled third-party source trees that are kept in the repository for compatibility. Be careful when moving source files or changing source sets.
