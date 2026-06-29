# Achievements System - Documentation

## Overview

The achievements system in the Zowi app is a mechanism that allows users to unlock and track different achievements based on their actions. Achievements are organized into three categories: movements, animations, and games.

---

## Main Components

### 1. Data Model: `Achievement`

Location: [com/bq/zowi/models/Achievement.java](app/src/main/java/com/bq/zowi/models/Achievement.java)

**Properties:**
- `id` (String): Unique achievement identifier (e.g., "crusaito", "flapping", "shake_leg", etc.)
- `type` (String): Achievement type - can be "movement", "animation", or "game"
- `unlocked` (boolean): Indicates whether the achievement has been unlocked

**Available IDs in the `Achievement.Id` enumeration:**
```
crusaito, flapping, shake_leg, tip_toe, jitter, ascending_turn, swing, 
super_happy, sleepy, fart, confused, in_love, angry, anxious, magic, 
wave, mouths_editor
```

**Available types in the `Achievement.Type` enumeration:**
```
movement    → Movement achievements
animation   → Animation achievements
game        → Game achievements
```

---

### 2. Data View: `AchievementViewModel`

Location: [com/bq/zowi/models/viewmodels/AchievementViewModel.java](app/src/main/java/com/bq/zowi/models/viewmodels/AchievementViewModel.java)

**Purpose:** Map achievements to visual resources (texts, images, descriptions).

**Resource naming conventions:**
- Title: `achievement_title_{id}`
  - Example: `achievement_title_crusaito`
- Description: `achievement_description_{id}`
  - Example: `achievement_description_flapping`
- Unlock condition: `achievement_unlock_condition_{id}`
  - Example: `achievement_unlock_condition_shake_leg`
- Badge image: `{id}_badge`
  - Example: `flapping_badge`

> **Note:** These conventions allow the UI to automatically find resources without hardcoding references.

---

### 3. Controller: `AchievementsControllerImpl`

Location: [com/bq/zowi/controllers/AchievementsControllerImpl.java](app/src/main/java/com/bq/zowi/controllers/AchievementsControllerImpl.java)

**Responsibilities:** Manage achievements persistence and business logic.

#### Storage

Achievements are stored in Android's **SharedPreferences**:
- **Key:** `achievementsList`
- **Format:** JSON serialized (using Gson)

#### Versioning

A versioning system is in place to handle changes in achievements:
- **Version key:** `achievementsListVersion`
- **Current version:** 2
- **Purpose:** Allows migrating achievements when the initial list changes

#### Main Methods

**`resetAchievementsList()`**
- Resets achievements to their initial state (from `achievements/initial_list.json`)
- Useful to "reset" user progression

**`getAchievementsList()`**
- Returns a `Single<ArrayList<Achievement>>` with all achievements
- Retrieves data from SharedPreferences
- Uses RxJava for asynchronous operations

**`unlockAchievement(Achievement.Id achievementId)`**
- Unlocks a specific achievement
- Searches for the achievement by ID
- Sets `unlocked = true`
- Persists the change to SharedPreferences
- Sends an analytics event with the achievement type (movement, animation, etc.)

**`unlockAllAchievements()`**
- Unlocks all achievements at once
- Marks all achievements as `unlocked = true`
- Persists all changes to SharedPreferences

**`getAchievement(Achievement.Id achievementId)`**
- Gets a specific achievement by its ID
- Returns a `Single<Achievement>`

#### Bootstrap (Initialization)

The `bootstrapAchievements()` method runs in the constructor and:

1. **Checks version:** Determines if this is the first time or if there's an older version
2. **Initialization:** If no achievements exist, loads them from `achievements/initial_list.json`
3. **Migration:** If the previous version is less than 2:
   - Takes the current achievements (with their unlock status)
   - Takes the new achievements from the initial list
   - Merges both lists, preserving user unlocks
   - Adds any new achievements

This mechanism ensures that:
- User progression is not lost when the app is updated
- New achievements are added automatically
- Deleted achievements remain in persistence (not lost)

---

### 4. Presenter: `AchievementsPresenterImpl`

Location: [com/bq/zowi/presenters/interactive/achievements/AchievementsPresenterImpl.java](app/src/main/java/com/bq/zowi/presenters/interactive/achievements/AchievementsPresenterImpl.java)

**Responsibilities:** Orchestrate UI logic and connect the controller to the view.

**Main Methods:**

**`loadAchievements()`**
- Calls the controller to get all achievements
- Groups achievements by type into three lists:
  - `movementsList` → "movement" type achievements
  - `animationsList` → "animation" type achievements
  - `gamesList` → "game" type achievements
- Converts each achievement to an `AchievementViewModel`
- Passes the three lists to the view for display

**`homeButtonPressed()`**
- Event: User presses the "Home" button
- Action: Delegates to the wireframe to present the home screen

**`easterEggCombinationPressed()`**
- Event: User presses a special key combination (easter egg)
- Action:
  1. Unlocks all achievements
  2. Reloads achievements on screen
  3. Sends a "VICTORY" animation command to the Zowi robot

---

### 5. View: `AchievementsView`

Location: [com/bq/zowi/views/interactive/achievements/AchievementsView.java](app/src/main/java/com/bq/zowi/views/interactive/achievements/AchievementsView.java)

**Responsibilities:** Display achievements to the user.

**Main Method:**

**`paintAchievements(ArrayList<AchievementViewModel> movements, ArrayList<AchievementViewModel> animations, ArrayList<AchievementViewModel> games)`**
- Receives the three lists of achievements organized by type
- Displays each achievement with its:
  - Badge
  - Title
  - Description
  - Unlock condition
- Visually differentiates between locked and unlocked achievements

---

### 6. Wireframe: `AchievementsWireframeImpl`

Location: [com/bq/zowi/wireframes/achievements/AchievementsWireframeImpl.java](app/src/main/java/com/bq/zowi/wireframes/achievements/AchievementsWireframeImpl.java)

**Responsibilities:** Manage navigation between screens.

**Main Method:**

**`presentHome()`**
- Navigates to the home screen (`HomeViewActivity`)
- Uses Intent flags to clear the activity stack

---

## Workflow

### Flow 1: Load and display achievements

```
1. User opens the Achievements screen
   ↓
2. AchievementsView calls AchievementsPresenterImpl.loadAchievements()
   ↓
3. Presenter calls AchievementsControllerImpl.getAchievementsList()
   ↓
4. Controller reads from SharedPreferences
   ↓
5. Presenter receives the list and groups by type
   ↓
6. Presenter creates AchievementViewModels for each achievement
   ↓
7. Presenter calls view.paintAchievements(movements, animations, games)
   ↓
8. View renders the three groups of achievements
```

### Flow 2: Unlock an achievement

```
1. Event in the app (e.g., user performs a movement)
   ↓
2. AchievementsControllerImpl.unlockAchievement(Achievement.Id.crusaito) is called
   ↓
3. Controller searches for the achievement in SharedPreferences
   ↓
4. Controller sets unlocked = true
   ↓
5. Controller persists the change to SharedPreferences
   ↓
6. Controller sends an analytics event
   ↓
7. The achievement remains permanently unlocked
```

### Flow 3: Easter egg (unlock all)

```
1. User presses a special key combination
   ↓
2. AchievementsPresenterImpl.easterEggCombinationPressed()
   ↓
3. Presenter calls controller.unlockAllAchievements()
   ↓
4. Controller marks all achievements as unlocked = true
   ↓
5. Controller persists to SharedPreferences
   ↓
6. Presenter calls loadAchievements()
   ↓
7. Presenter sends "VICTORY" animation to the robot
   ↓
8. All unlocked achievements are displayed on screen
```

---

## Data: Initial Achievements List

**Location:** `assets/achievements/initial_list.json`

**Expected structure:**
```json
[
  {
    "id": "crusaito",
    "type": "movement",
    "unlocked": false
  },
  {
    "id": "flapping",
    "type": "animation",
    "unlocked": false
  },
  ...
]
```

---

## How to Modify Achievements

### Add a New Achievement

1. **Update the initial JSON:**
   - Edit `assets/achievements/initial_list.json`
   - Add entry: `{"id": "new_achievement", "type": "movement|animation|game", "unlocked": false}`

2. **Update the enumeration:**
   - Edit [Achievement.java](app/src/main/java/com/bq/zowi/models/Achievement.java)
   - Add `new_achievement` to `Achievement.Id`

3. **Add UI resources:**
   - File `res/values/strings.xml`: `achievement_title_new_achievement`, `achievement_description_new_achievement`, `achievement_unlock_condition_new_achievement`
   - Add image: `res/drawable/new_achievement_badge.png`

4. **Increment versioning:**
   - Change `ACHIEVEMENTS_LIST_VERSION` in [AchievementsControllerImpl.java](app/src/main/java/com/bq/zowi/controllers/AchievementsControllerImpl.java) from 2 to 3
   - This ensures existing users receive the new achievement

### Change Unlock Condition

1. **Locate where the achievement is unlocked:**
   - Search in the code for where `unlockAchievement(Achievement.Id.crusaito)` is called
   - Change the logic that triggers the unlock

2. **Example:**
   - Before: Unlocked when user performs the movement 5 times
   - After: Unlocked when user performs the movement 10 times

### Reset All User Achievements

- Call `AchievementsControllerImpl.resetAchievementsList()`
- This restores the initial list from JSON

---

## Technical Considerations

### Persistence

- **Storage:** SharedPreferences (device local storage)
- **Format:** JSON serialized with Gson
- **Synchronization:** Does not sync with servers (local-only)
- **Backup:** Included in Android's automatic backup

### Performance

- Achievement operations use **RxJava** with `Schedulers.io()` (background) and observe on the UI thread
- SharedPreferences access is fast (Android's in-memory cache)
- No blocking I/O operations on the UI thread

### Analytics

- When an achievement is unlocked, an event is sent with:
  - Achievement type ("move" or "animation")
  - Achievement ID
  - Prefix: `ACHIEVEMENT_UNLOCK_`

---

## Debugging

### View saved achievements

```bash
adb shell "dumpsys backup com.bq.zowi" | grep achievementsList
```

Or use Android Studio's Device File Explorer to inspect SharedPreferences.

### Reset achievements

Open the SharedPreferences database and delete the `achievementsList` key, or call `resetAchievementsList()` from code.

### Check achievement version

```bash
adb shell "dumpsys backup com.bq.zowi" | grep achievementsListVersion
```

---

## Unlock Conditions for Each Achievement

The following table summarizes how each achievement is unlocked, based on the current code. The achievements are grouped by type.

### Movements (`type: movement`)

| ID | Initially unlocked | Unlock condition | Source |
|---|---|---|---|
| `ascending_turn` | **Yes** | Available from the start, no action required | `initial_list.json` (`"unlocked": true`) |
| `crusaito` | No | Open the app for the **second time** (not the first launch) | `HomePresenterImpl.logAppStarted()` → `APP_SECOND_USAGE_ACHIEVEMENT` |
| `shake_leg` | No | Open either the **Gamepad or the Timeline** for the first time (whichever is used first) | `PadPresenterImpl.gameReady()` and `TimelinePresenterImpl.gameReady()` → `FIRST_TIME_ACHIEVEMENT` |
| `flapping` | No | Complete the **quiz of project 01** ("Mueve") | `01_project_mueve.json` → `"achievement": "flapping"` |
| `swing` | No | Complete the **quiz of project 02** ("Choreography") | `02_project_choreography.json` → `"achievement": "swing"` |
| `jitter` | No | Complete the **quiz of project 05** ("Bio3") | `05_project_bio3.json` → `"achievement": "jitter"` |
| `tip_toe` | No | Complete the **quiz of project 08** ("Bitbloq2") | `08_project_bitbloq2.json` → `"achievement": "tip_toe"` |

### Animations (`type: animation`)

| ID | Initially unlocked | Unlock condition | Source |
|---|---|---|---|
| `fart` | No | Open the app on at least **4 different days** | `HomePresenterImpl.logAppStarted()` → `APP_DAYS_OF_USE_ACHIEVEMENT` (condition: `daysOfUse >= 4`) |
| `anxious` | No | Execute a Timeline sequence with **at least 15 commands** until completion | `TimelinePresenterImpl.TimelineCommandSubscriber.onCompleted()` (condition: `timelineCommandsCount >= 15`) |
| `in_love` | No | Score **12 or more** in the **Zowi Says** minigame | `ZowiSaysMinigamePresenterImpl.gameOver()` (condition: `score >= 12`) |
| `super_happy` | No | Complete the **quiz of project 07** ("Hello World") | `07_project_helloworld.json` → `"achievement": "super_happy"` |
| `sleepy` | No | Complete the **quiz of project 10** ("Gravity") | `10_project_gravity.json` → `"achievement": "sleepy"` |
| `confused` | No | Complete the **quiz of project 03** ("Forma") | `03_project_forma.json` → `"achievement": "confused"` |
| `angry` | No | Complete the **quiz of project 06** ("Reprogram") | `06_project_reprogram.json` → `"achievement": "angry"` |
| `magic` | No | Complete the **quiz of project 09** ("Adivinawi") | `09_project_adivinawi.json` → `"achievement": "magic"` |
| `wave` | No | Complete the **quiz of project 04** ("Bio1") | `04_project_bio1.json` → `"achievement": "wave"` |

### Games (`type: game`)

| ID | Initially unlocked | Unlock condition | Source |
|---|---|---|---|
| `mouths_editor` | No | Score **8 or more** in the **Mouths** minigame. Also gates the Mouths Editor button on the home screen | `MouthsMinigamePresenterImpl.gameOver()` (condition: `score >= 8`) |

### Summary by trigger type

| Trigger | Achievements unlocked |
|---|---|
| **Default (from start)** | `ascending_turn` |
| **2nd app launch** | `crusaito` |
| **4 days of use** | `fart` |
| **First time using Gamepad or Timeline** | `shake_leg` |
| **Timeline: sequence ≥ 15 commands** | `anxious` |
| **Zowi Says: score ≥ 12** | `in_love` |
| **Mouths minigame: score ≥ 8** | `mouths_editor` |
| **Complete project quiz 01** | `flapping` |
| **Complete project quiz 02** | `swing` |
| **Complete project quiz 03** | `confused` |
| **Complete project quiz 04** | `wave` |
| **Complete project quiz 05** | `jitter` |
| **Complete project quiz 06** | `angry` |
| **Complete project quiz 07** | `super_happy` |
| **Complete project quiz 08** | `tip_toe` |
| **Complete project quiz 09** | `magic` |
| **Complete project quiz 10** | `sleepy` |
