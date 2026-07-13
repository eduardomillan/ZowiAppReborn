# Zowi CLI — How to Use from Terminal

The Zowi CLI (`zowi-cli`) is a standalone Kotlin application that provides a
command-line interface for communicating with a Zowi robot over a serial
connection. It shares the same platform-agnostic core (`zowi-core`) as the
Android app, making it a useful tool for testing robot protocols, debugging
Bluetooth issues, and validating core logic without an Android device.

## Prerequisites

- **Java 11** or later (`java -version` to check).
- The Gradle wrapper (`./gradlew`) from the repository root.
- A serial connection to the Zowi robot (USB-Serial adapter, HC-05/HC-06
  Bluetooth-to-Serial module, or an RFCOMM virtual port).

## Building

From the repository root:

```bash
./gradlew :zowi-cli:build
```

This compiles `zowi-core` and `zowi-cli`, and runs the test suite (52 tests).

## Running

Use the Gradle `run` task, passing commands after `--args`:

```bash
./gradlew :zowi-cli:run --args="help"
```

All examples below use this pattern.

## Quick Start

### 1. Scan for serial ports

```bash
./gradlew :zowi-cli:run --args="scan"
```

Lists every serial port the operating system exposes. Example output:

```
Available serial ports:
  /dev/ttyUSB0  (USB Serial)
  /dev/ttyS0    (Physical Port S0)
```

On **Linux (Lliurex / Ubuntu)**, Bluetooth serial ports typically appear as
`/dev/rfcomm0` after binding (see [Linux Bluetooth Setup](#linux-bluetooth-setup)
below). On **macOS**, they appear as `/dev/cu.*`. On **Windows**, as `COM3`,
`COM4`, etc.

### 2. Connect to the robot

```bash
./gradlew :zowi-cli:run --args="connect /dev/ttyUSB0"
```

Replace the address with the port shown by `scan`. The connection opens at
**115200 baud, 8N1** (the default Zowi firmware rate). On success:

```
Connected successfully!
```

### 3. Send commands

```bash
./gradlew :zowi-cli:run --args="send walk f"     # Walk forward
./gradlew :zowi-cli:run --args="send walk b"     # Walk backward
./gradlew :zowi-cli:run --args="send turn l"     # Turn left
./gradlew :zowi-cli:run --args="send turn r"     # Turn right
./gradlew :zowi-cli:run --args="send stop"       # Stop all movement
./gradlew :zowi-cli:run --args="send happy"      # Happy mouth animation
./gradlew :zowi-cli:run --args="send sad"        # Sad mouth animation
./gradlew :zowi-cli:run --args="send jump"       # Jump
./gradlew :zowi-cli:run --args="send tone 440 500"  # 440 Hz tone for 500 ms
```

## Full Command Reference

| Command | Description |
|---------|-------------|
| `help` | Print usage information |
| `scan` | List all available serial ports |
| `connect <address>` | Open a serial connection to the given port |
| `send <command> [args]` | Send a movement, animation, or tone command |
| `name` | Read the current Zowi name |
| `name <new_name>` | Set a new Zowi name |
| `battery` | Read battery level (OK / LOW) |
| `status` | Show connection state, session info, and days of use |
| `session` | Show session details (name, address, data directory) |
| `achievements` | List all achievements and their unlock status |
| `rankings <game>` | Show rankings for a specific game |
| `games` | Show play progress for each game mode |
| `forget zowi` | Forget the currently active Zowi |
| `forget history` | Wipe all game history and progress |

### Supported `send` sub-commands

| Sub-command | Args | Example |
|-------------|------|---------|
| `walk` | `f` (forward) or `b` (backward) | `send walk f` |
| `turn` | `l` (left) or `r` (right) | `send turn r` |
| `stop` | — | `send stop` |
| `happy` | — | `send happy` |
| `sad` | — | `send sad` |
| `jump` | — | `send jump` |
| `mouth` | (reserved) | — |
| `tone` | `<freq_hz> <duration_ms>` | `send tone 880 300` |

### Ranking game IDs

Valid values for `rankings <game>`:

- `ZOWI_SAYS_GAME_ID`
- `MOUTHS_GAME_ID`
- `TIMELINE_GAME_ID`
- `GAMEPAD_GAME_ID`

## Data Storage

All persistent state is stored as JSON in:

```
~/.zowi/zowi.json
```

This file tracks the active Zowi name, device address, wizard dismissal flag,
achievements, game progress, and rankings. Delete it to reset to factory
defaults.

Asset files (achievements list, project definitions) are loaded from:

```
~/.zowi/assets/
```

Copy the Android app's `app/src/main/assets/` directory there if you need
project or quiz data available in the CLI.

## Linux Bluetooth Setup (Lliurex / Ubuntu)

The Zowi robot communicates over Bluetooth SPP (Serial Port Profile). To
create a serial connection on Linux:

### Automated setup (recommended)

Run the pairing script as root. It scans for the Zowi, pairs it, trusts it,
and binds the RFCOMM serial port:

```bash
sudo ./scripts/pair_zowi.sh
```

The script will:
1. Ensure Bluetooth is powered on
2. Scan for nearby devices and let you pick the Zowi
3. Pair and trust the device
4. Bind `/dev/rfcomm0` to the Zowi's serial channel

After it completes, connect with:

```bash
./gradlew :zowi-cli:run --args="connect /dev/rfcomm0"
```

### Manual setup

If you prefer to do it by hand:

#### 1. Pair the device

```bash
bluetoothctl
  [bluetooth]# power on
  [bluetooth]# agent on
  [bluetooth]# default-agent
  [bluetooth]# scan on
  # Wait for the Zowi MAC address to appear (e.g., AA:BB:CC:DD:EE:FF)
  [bluetooth]# pair AA:BB:CC:DD:EE:FF
  [bluetooth]# trust AA:BB:CC:DD:EE:FF
  [bluetooth]# quit
```

#### 2. Bind the RFCOMM channel

```bash
sudo rfcomm bind /dev/rfcomm0 AA:BB:CC:DD:EE:FF 1
```

This creates `/dev/rfcomm0` on channel 1. Verify with:

```bash
ls -l /dev/rfcomm0
```

#### 3. Connect with the CLI

```bash
./gradlew :zowi-cli:run --args="connect /dev/rfcomm0"
```

#### 4. Release when done

```bash
sudo rfcomm release /dev/rfcomm0
```

### Permissions

If you get a "Permission denied" error on the serial port:

```bash
sudo usermod -aG dialout $USER
```

Log out and back in for the group change to take effect.

## Windows Setup

On Windows, the Bluetooth serial port typically appears as `COM3`, `COM4`, etc.
You can find it in **Device Manager > Ports (COM & LPT)**.

```powershell
./gradlew :zowi-cli:run --args="connect COM3"
```

## macOS Setup

On macOS, Bluetooth serial ports appear as `/dev/cu.HC-05-DevB` or similar.
Check with:

```bash
ls /dev/cu.*
```

```bash
./gradlew :zowi-cli:run --args="connect /dev/cu.HC-05-DevB"
```

## Troubleshooting

| Symptom | Cause | Fix |
|---------|-------|-----|
| `No serial ports found` | No serial devices connected | Check USB adapter or Bluetooth binding |
| `Failed to open port` | Permission denied or port busy | Add user to `dialout` group; close other serial clients |
| `Connection failed` | Wrong baud rate or channel | Ensure Zowi firmware is at 115200 baud; try channel 1 vs 2 |
| `Send failed` | Connection dropped | Re-run `connect`; check Bluetooth signal |
| `Not connected` | No active connection | Run `connect <address>` first |
| Asset not found errors | Missing asset files | Copy `app/src/main/assets/` to `~/.zowi/assets/` |

## Running Tests

```bash
./gradlew :zowi-cli:test
```

This runs the 52 unit tests covering `CliSessionController`,
`CliGameController`, `CliRankingController`, `CliAchievementsController`,
`JsonKeyValueStore`, and more. No hardware required.
