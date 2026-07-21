# Pad Movements - Serializations to Zowi

This document describes the string serializations that are transmitted over Bluetooth from the **PadPresenter** when the user interacts with the gamepad (cursor) controls in the Android app. These strings are assembled by `Command.getCommandValue()` (e.g., from `ForwardBackwardCommand`, `LeftRightCommand`, `StaticCommand`, `StopCommand`) and sent by `SendCommandToZowiInteractorImpl` → `AndroidBtConnectionController.sendMessage()`.

The serializations follow the protocol format: `<command> <duration>`, where duration is added only for MovementCommands and StaticCommands. All strings end with the carriage‑return + line‑feed sequence (`\r\n`) from `Command.CRLN`.

---

## Duration

All pad controls use a **default duration of 1000 ms** (1 second) unless changed by the caller. The full duration is appended to the command (if applicable) and then a `\r\n` is added. Example: `M 1 1000\r\n`.

---

## Direction mappings

| User Action (PadPresenter method) | Command class | Direction | Protocol string (with \r\n) | Description |
|----------------------------------|---------------|-----------|------------------------------|-------------|
| `upArrowPressed()` | ForwardBackwardCommand | FORWARD | `M 1 1000\r\n` | Walk forward |
| `downArrowPressed()` | ForwardBackwardCommand | BACKWARD | `M 2 1000\r\n` | Walk backward |
| `rightArrowPressed()` | LeftRightCommand | RIGHT | `M 4 1000\r\n` | Turn right |
| `leftArrowPressed()` | LeftRightCommand | LEFT | `M 3 1000\r\n` | Turn left |
| `crusaitoRight()` | LeftRightCommand | RIGHT | `M 10 1000\r\n` | Crusaito right |
| `crusaitoLeft()` | LeftRightCommand | LEFT | `M 9 1000\r\n` | Crusaito left |
| `moonwalkerRight()` | LeftRightCommand | RIGHT | `M 4 1000\r\n` | Moonwalker right (same as `M 4`) |
| `moonwalkerLeft()` | LeftRightCommand | LEFT | `M 3 1000\r\n` | Moonwalker left (same as `M 3`) |
| `flappingForward()` | ForwardBackwardCommand | FORWARD | `M 12 1000\r\n` | Flapping forward |
| `flappingBackward()` | ForwardBackwardCommand | BACKWARD | `M 13 1000\r\n` | Flapping backward |
| `bendRight()` | LeftRightCommand | RIGHT | `M 16 1000\r\n` | Bend right |
| `bendLeft()` | LeftRightCommand | LEFT | `M 15 1000\r\n` | Bend left |
| `shakeLegRight()` | LeftRightCommand | RIGHT | `M 18 1000\r\n` | Shake leg right |
| `shakeLegLeft()` | LeftRightCommand | LEFT | `M 17 1000\r\n` | Shake leg left |
| `updownPressed()` | StaticCommand(UPDOWN) | - | `M 5 1000\r\n` | Up‑down movement |
| `jumpPressed()` | StaticCommand(JUMP) | - | `M 11 1000\r\n` | Jump |
| `tipToeSwingPressed()` | StaticCommand(TIP_TOE) | - | `M 14 1000\r\n` | Tip‑toe swing |
| `jitterPressed()` | StaticCommand(JITTER) | - | `M 19 1000\r\n` | Jitter |
| `ascendingTurnPressed()` | StaticCommand(ASCENDING_TURN) | - | `M 20 1000\r\n` | Ascending turn |
| `swingPressed()` | StaticCommand(SWING) | - | `M 8 1000\r\n` | Swing |
| `actionButtonReleased()` | StopCommand | - | `S\r\n` | Stop current movement |

**Note:** The same command strings (e.g., `M 3` for turn left, `M 4` for turn right) are also emitted for legacy movement areas like `TimelinePresenter`, `ZowiSaysMinigamePresenter`, and others, but they are generated from the same underlying `MovementCommand` hierarchy.

---

## Protocol reference table (summarized)

| Command value | Description |
|---------------|-------------|
| `M 1` | Walk forward |
| `M 2` | Walk backward |
| `M 3` | Turn left |
| `M 4` | Turn right |
| `M 5` | Up‑down |
| `M 6` | Moonwalker left |
| `M 8` | Swing |
| `M 9` | Crusaito left |
| `M 10` | Crusaito right |
| `M 11` | Jump |
| `M 12` | Flapping forward |
| `M 13` | Flapping backward |
| `M 14` | Tip‑toe |
| `M 15` | Bend left |
| `M 16` | Bend right |
| `M 17` | Shake leg left |
| `M 18` | Shake leg right |
| `M 19` | Jitter |
| `M 20` | Ascending turn |
| `H 1` | Happy (animations) |
| `H 2` | Super‑happy |
| `H 3` | Sad |
| `H 4` | Sleepy |
| `H 5` | Fart |
| `H 6` | Confused |
| `H 7` | In‑love |
| `H 8` | Angry |
| `H 9` | Anxious |
| `H 10` | Magic |
| `H 11` | Wave |
| `H 12` | Victory |
| `H 13` | Game over |
| `S` | Stop |

All animation commands (`H …`) are used for facial expressions in other presenters (e.g., `AchievementsPresenter`, `ZowiSaysMinigamePresenter`).
