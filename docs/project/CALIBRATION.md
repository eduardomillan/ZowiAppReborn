# Zowi Calibration System

Zowi has 4 servos — two in the legs (left `YL`, right `YR`) and two in the feet (left `RL`, right `RR`). Each servo has a trim value that compensates for mechanical deviations from its neutral position.

This document describes the calibration feature end-to-end: theory, protocol, architecture layers, UI, and resources.

---

## 1. Theoretical Operation

### Servo Model

| Servo | Variable | Description |
|-------|----------|-------------|
| Left Leg | `trimLeftLegYL` | Trim offset for the left leg servo |
| Right Leg | `trimRightLegYR` | Trim offset for the right leg servo |
| Left Foot | `trimLeftFootRL` | Trim offset for the left foot servo |
| Right Foot | `trimRightFootRR` | Trim offset for the right foot servo |

- **Neutral position**: every servo centers at **90 degrees** (`BASE_GRADE = 90`).
- **Trim range**: **-30 to +30** degrees per servo.
- **Effective degree sent to the robot**: `trim + 90` (so a trim of +5 results in 95 degrees).

### Serial Protocol (Bluetooth SPP, 115200 baud)

Two command types are used, both subclassing `MotionlessCommand`:

| Prefix | Constant | Behaviour |
|--------|----------|-----------|
| `C` | `CALIBRATE_TRIM` | Persists the trim offsets in the robot's EEPROM (survives power cycles) |
| `G` | `CALIBRATE_GRADES` | Moves the servos to those degrees in real time (volatile, does not persist) |

Serialization format (sent with `\r\n` terminator):

```
C <YL> <YR> <RL> <RR>\r\n
G <YL> <YR> <RL> <RR>\r\n
```

### Calibration Flow

1. On warning-continue, the app sends `C 0 0 0 0` (reset stored trims) followed by `G 90 90 90 90` (move all servos to neutral).
2. While the user adjusts the +/- trim buttons, the app sends `G` commands in real time so the robot moves instantly and the user can see the effect.
3. When the user confirms, the app sends `C <trims>` to permanently store the offsets, then plays a `VICTORY` animation and returns to the home screen.

The debounce between consecutive trim changes is **200 ms** (`MIN_TIME_BETWEEN_CALIBRATION_CHANGES`).

---

## 2. Screen Flow (4-Page Wizard)

The calibration UI is a `NonSwipeableViewPager` with four pages, driven by `CalibrationPagerAdapter`:

```
Settings → [CALIBRATE ZOWI] → CalibrationViewActivity
  Page 0: WARNING    — Warning dialog + Cancel / Continue
  Page 1: LEGS       — Left/right leg +/- trim buttons
  Page 2: FEET       — Left/right foot +/- trim buttons
  Page 3: CHECK      — Test movement (VICTORY) + Restart / Confirm
```

### Page Details

| Page | Layout | Description |
|------|--------|-------------|
| WARNING | `component_calibration_warning.xml` | Displays a warning title and description. Two buttons: Cancel (returns home) and Continue (sends initial commands, navigates to LEGS). |
| LEGS | `component_calibration_legs.xml` | Shows `calibrate_legs_image`. Two columns: left leg (YL) and right leg (YR), each with +/- buttons and a text label showing the current trim. "Next Step" button navigates to FEET. |
| FEET | `component_calibration_feet.xml` | Shows `calibrate_feet_image`. Two columns: left foot (RL) and right foot (RR), each with +/- buttons and a text label. "Finish" button navigates to CHECK. |
| CHECK | `component_calibration_check.xml` | Shows `calibrate_standing_image`. "Test Movement" link sends trims + VICTORY animation. "Restart" resets and goes back to WARNING. "Confirm" sends trims permanently, plays VICTORY, and returns to Home. |

---

## 3. Classes by Architecture Layer

### Domain — `zowi-core` (platform-agnostic)

| Class | File | Role |
|-------|------|------|
| `CalibrationCommand` | `zowi-core/.../models/commands/CalibrationCommand.kt` | Command model. Extends `MotionlessCommand`. Constants `CALIBRATE_TRIM = "C"` and `CALIBRATE_GRADES = "G"`. Serializes to `"C/Y/G <YL> <YR> <RL> <RR>\r\n"`. |
| `Command.Action.CALIBRATE_TRIM` | `zowi-core/.../models/commands/Command.kt` | Enum value for the `C` command type. |
| `Command.Action.CALIBRATE_GRADES` | `zowi-core/.../models/commands/Command.kt` | Enum value for the `G` command type. |

### View — Android `app`

| Class | File | Role |
|-------|------|------|
| `CalibrationView` | `app/.../views/interactive/settings/CalibrationView.java` | Interface extending `InteractiveBaseView`. Declares: `showLegsCalibration`, `showFeetCalibration`, `showCheckCalibration`, `showLeftLegTrimValue`, `showRightLegTrimValue`, `showLeftFootTrimValue`, `showRightFootTrimValue`. |
| `CalibrationViewActivity` | `app/.../views/interactive/settings/CalibrationViewActivity.java` | Activity hosting the 4-page `NonSwipeableViewPager`. Wires all +/- trim buttons, warning cancel/continue, next-step, test-movement, restart, and confirm buttons to presenter methods via `findResolvedView`. Resolves presenter and wireframe through `AndroidDependencyInjector`. |
| `CalibrationPagerAdapter` | (inner class of `CalibrationViewActivity`) | `PagerAdapter` mapping positions 0–3 to the four component layouts. Uses `resolvePageId` (resource-name-first resolution). |

### Presenter

| Class | File | Role |
|-------|------|------|
| `CalibrationPresenter` | `app/.../presenters/interactive/settings/CalibrationPresenter.java` | Interface extending `InteractiveBasePresenter<CalibrationView, CalibrationWireframe>`. Declares all `*ButtonPressed` methods. |
| `CalibrationPresenterImpl` | `app/.../presenters/interactive/settings/CalibrationPresenterImpl.java` | Implementation. Holds 4 trim state variables (initialized to 0), `BASE_GRADE = 90`, `MIN_TRIM = -30`, `MAX_TRIM = 30`, 200 ms debounce. Builds `CalibrationCommand` and `AnimationCommand` objects and sends them via `SendCommandToZowiInteractor`. |

Key methods in `CalibrationPresenterImpl`:

| Method | Action |
|--------|--------|
| `warningCancelButtonPressed()` | Navigates home via wireframe. |
| `warningContinueButtonPressed()` | Sends `C 0 0 0 0` then `G 90 90 90 90`. On success, shows LEGS page. |
| `legsCalibration*Increase/DecreaseButtonPressed()` | Increments/decrements the corresponding trim (clamped to [-30,30]), updates view text, sends `G <trims+90>` if debounce allows. |
| `feetCalibration*Increase/DecreaseButtonPressed()` | Same as above for foot trims. |
| `checkCalibrationTestMovementButtonPressed()` | Sends `C <trims>` then `AnimationCommand(VICTORY)`. |
| `calibrationConfirmedButtonPressed()` | Sends `C <trims>`, on success navigates home and sends `VICTORY`. |
| `calibrationParametersChanged()` | Sends `G (YL+90) (YR+90) (RL+90) (RR+90)` to move servos in real time. |
| `isCalibrationChangeBlocked()` | Returns true if less than 200 ms since last change (debounce). |

### Wireframe (Navigation)

| Class | File | Role |
|-------|------|------|
| `CalibrationWireframe` | `app/.../wireframes/settings/CalibrationWireframe.java` | Interface. Declares `presentHome()`. |
| `CalibrationWireframeImpl` | `app/.../wireframes/settings/CalibrationWireframeImpl.java` | Implementation. `presentHome()` starts `HomeViewActivity` with `FLAG_ACTIVITY_CLEAR_TOP` and finishes the current activity. |

### Dependency Injection

| Class | Method |
|-------|--------|
| `DependencyInjector` | `provideCalibrationPresenter()` |
| `AndroidDependencyInjector` | `provideCalibrationWireframe(FragmentActivity)` |

### Settings Entry Point

| Class | Method | Notes |
|-------|--------|-------|
| `SettingsPresenterImpl` | `calibrateZowi()` | Gated by `!isZowiAltered()` — if the robot firmware is altered, calibration is blocked. |
| `SettingsWireframeImpl` | `presentCalibrationView()` | Launches `CalibrationViewActivity`. |
| `SettingsViewActivity` | Menu entry `calibrateZowiButton` | Visible only in `zowiDependantViews` (when robot is connected). |

### Communication with the Robot

| Class | Role |
|-------|------|
| `SendCommandToZowiInteractor` | Interactor that sends a `Command` to the robot via `BTConnectionController`. |
| `AnimationCommand` | Used for the `VICTORY` animation when testing or confirming calibration. |

---

## 4. Layout Files

| Layout | Page | Key Views |
|--------|------|-----------|
| `activity_calibration_view.xml` | Host | `NonSwipeableViewPager` (`activity_calibration_view_pager`) containing the 4 pages |
| `component_calibration_warning.xml` | 0 — Warning | Title, description, cancel button (`activity_calibration_warning_cancel_button`), continue button (`activity_calibration_warning_continue_button`) |
| `component_calibration_legs.xml` | 1 — Legs | `calibrate_legs_image`, left leg text + +/− buttons, right leg text + +/− buttons, next step button (`calibration_legs_next_step`) |
| `component_calibration_feet.xml` | 2 — Feet | `calibrate_feet_image`, left foot text + +/− buttons, right foot text + +/− buttons, finish button (`calibration_feet_next_step`) |
| `component_calibration_check.xml` | 3 — Check | `calibrate_standing_image`, test movement link (`calibration_check_movement_button`), restart button (`calibration_check_restart_button`), confirm button (`calibration_check_confirm_button`) |

---

## 5. Image Resources

All images exist in 5 density buckets (`drawable-mdpi-v4` through `drawable-xxxhdpi-v4`).

| Resource | Used In | Description |
|----------|---------|-------------|
| `calibrate_legs_image.png` | `component_calibration_legs.xml` | Illustration of Zowi's legs to guide the leg trim adjustment |
| `calibrate_feet_image.png` | `component_calibration_feet.xml` | Illustration of Zowi's feet to guide the foot trim adjustment |
| `calibrate_standing_image.png` | `component_calibration_check.xml` | Robot standing upright, shown on the verification page |
| `calibrate_zowi_button.png` | `activity_settings_view.xml` | Settings menu button (normal state) |
| `pressed_calibrate_zowi_button.png` | `activity_settings_view.xml` | Settings menu button (pressed/focused state) |
| `settings_calibrate_zowi_button_selector.xml` | `activity_settings_view.xml` | State-list drawable selector (normal → pressed) |

---

## 6. Tests

All calibration tests live in `zowi-core/src/test/kotlin/com/bq/zowi/models/commands/`:

| Test Class | Test Method | What It Validates |
|------------|-------------|-------------------|
| `CommandValidationTest.kt` | `calibrationCommandRejectsInvalidAction` | A `CalibrationCommand` rejects actions other than `CALIBRATE_TRIM` / `CALIBRATE_GRADES` |
| `CommandCopyTest.kt` | `calibrationCommandCopyIsIndependent` | `copy()` produces an independent clone |
| `CommandSerializationTest.kt` | `calibrationTrimSerializesCorrectly` | `C 0 0 0 0\r\n` |
| `CommandSerializationTest.kt` | `calibrationGradesSerializesCorrectly` | `G 90 90 90 90\r\n` |
| `CommandSerializationTest.kt` | `calibrationCommandWithZerosSerializesCorrectly` | Edge case with all-zero values |

---

## 7. Navigation Diagram

```
Home/Other → SettingsViewActivity
  │
  │  ["CALIBRATE ZOWI" option/button]
  │  (blocked if isZowiAltered() → firmware-restore first)
  │
  ▼
SettingsPresenterImpl.calibrateZowi()
  │
  ▼
SettingsWireframeImpl.presentCalibrationView()
  │
  ▼
CalibrationViewActivity  (4-page NonSwipeableViewPager)
  │
  ├─ Page 0 [WARNING]
  │    ├─ Cancel    → presentHome()
  │    └─ Continue  → sends C 0 0 0 0 + G 90 90 90 90 → shows LEGS
  │
  ├─ Page 1 [LEGS]
  │    ├─ +/- buttons → sends G <trims+90> (real-time servo move)
  │    └─ Next Step   → shows FEET
  │
  ├─ Page 2 [FEET]
  │    ├─ +/- buttons → sends G <trims+90> (real-time servo move)
  │    └─ Finish      → shows CHECK
  │
  └─ Page 3 [CHECK]
       ├─ Test Movement → sends C <trims> + AnimationCommand(VICTORY)
       ├─ Restart       → sends C 0 0 0 0 + G 90 90 90 90 → shows WARNING
       └─ Confirm       → sends C <trims> → presentHome() + AnimationCommand(VICTORY)
```
