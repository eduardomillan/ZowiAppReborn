# ZowiAppReborn

This project intends to "reborn" the original Android app published by BQ in 2016, which allowed to manage the Zowi robots in an easy manner for children and young people, studying primary and secondary education.

## Documentation

All project documentation lives under [`docs/project/`](docs/project/).

### Extracted logic, architecture & design (new)
- [ARCHITECTURE.md](docs/project/ARCHITECTURE.md) — high-level layers and components (MVP + `zowi-core` domain + platform adapters), module map, and Bluetooth architecture. Includes Mermaid diagrams.
- [IMPLEMENTATION.md](docs/project/IMPLEMENTATION.md) — extracted application logic from abstract to concrete (command/protocol model, connection flow, robot behavior vocabulary, logic recovered from the initial decompiled commit) plus a **manual working-verification checklist** per screen. Includes Mermaid diagrams.
- [DESIGN.md](docs/project/DESIGN.md) — visual identity and graphic resources (palette, themes, layouts, drawables, animations, LED-mouth rendering). Includes Mermaid diagrams.

### Project notes & references
- [VIEWS.md](docs/project/VIEWS.md) — MVP architecture, full navigation tree (27 routes, 10 project variants), and per-screen actuation logic (controls, enable/disable, actions, errors).
- [NO_ANALYTICS.md](docs/project/NO_ANALYTICS.md) — removal of Adobe/comScore/BQ analytics SDKs.
- [MIGRATING.md](docs/project/MIGRATING.md) — porting feasibility analysis; BT Classic SPP hardware requirement.
- [bluetooth-regression-checklist.md](docs/project/bluetooth-regression-checklist.md) — QA checklist for BT changes (Android 12+ permissions, pairing, regression).
- [ACHIEVEMENTS.md](docs/project/ACHIEVEMENTS.md) — achievements system: model, categories, controllers.
- [ZOWI_CLI_ANDROID_HOWTO.md](docs/project/ZOWI_CLI_ANDROID_HOWTO.md) — how to run the `zowi-cli` from the terminal.
- [ZOWI_ROBOTICS.md](docs/project/ZOWI_ROBOTICS.md) — `com.bq.robotic` Bluetooth + firmware layers (`droid2ino`, `protocolSTK500v1`).
- [PLANNING.md](docs/project/PLANNING.md) — project plan/status.
- [ADVRECYCLERVIEW.md](docs/project/ADVRECYCLERVIEW.md) — advanced RecyclerView background (timeline drag-and-drop).
- [ANDROID_VERSIONS.md](docs/project/ANDROID_VERSIONS.md) — supported Android versions table.

Build/validation guidance also in [`.github/copilot-instructions.md`](.github/copilot-instructions.md).
