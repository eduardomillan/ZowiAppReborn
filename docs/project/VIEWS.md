# ZowiAppReborn — Views: Architecture & Screen Actuation Logic

## Table of Contents

- [Overview](#overview)
- [View Layer](#view-layer)
  - [Package Structure](#package-structure)
  - [Activity Hierarchy](#activity-hierarchy)
  - [MVP View Contracts](#mvp-view-contracts)
  - [Concrete Activities](#concrete-activities)
  - [Custom UI Components](#custom-ui-components)
  - [Adapters and ViewHolders](#adapters-and-viewholders)
  - [XML Layouts](#xml-layouts)
- [Navigation Structure](#navigation-structure)
  - [Launcher Entry Point](#launcher-entry-point)
  - [Navigation Tree](#navigation-tree)
  - [Intent Flags](#intent-flags)
  - [Navigation Routes (All Paths)](#navigation-routes-all-paths)
  - [Project Variants](#project-variants)
- [Screen Actuation Logic](#screen-actuation-logic)
  - [Cross-Cutting: Connection-Dependent Enable/Disable](#cross-cutting-connection-dependent-enabledisable)
  - [SplashViewActivity](#splashviewactivity)
  - [WelcomeViewActivity](#welcomeviewactivity)
  - [WizardViewActivity (Bluetooth pairing)](#wizardviewactivity-bluetooth-pairing)
  - [HomeViewActivity](#homeviewactivity)
  - [SettingsViewActivity](#settingsviewactivity)
  - [CalibrationViewActivity](#calibrationviewactivity)
  - [PadViewActivity (gamepad)](#padviewactivity-gamepad)
  - [TimelineActivity](#timelineactivity)
  - [MouthsEditorActivity](#mouthseditoractivity)
  - [MouthsMinigameActivity](#mouthsminigameactivity)
  - [ZowiSaysMinigameActivity](#zowisaysminigameactivity)
  - [ZowiRunnerMinigameActivity](#zowirunnerminigameactivity)
  - [ProjectViewActivity (Discover detail)](#projectviewactivity-discover-detail)
  - [ProjectQuizViewActivity (Run Test)](#projectquizviewactivity-run-test)
  - [AchievementsViewActivity](#achievementsviewactivity)

## Overview

ZowiAppReborn follows an **MVP (Model-View-Presenter)** architecture. The View layer is responsible
for rendering UI and forwarding user events to the Presenter. Activities implement MVP View
contracts and delegate all business logic to their associated Presenters.

The project does **not** use Fragments; all screens are implemented as Activities.

---

## View Layer

### Package Structure

```
com.bq.zowi.views/
  BaseActivity.java                  # Base Activity with Presenter binding
  BaseFragment.java                  # Base Fragment (unused)
  splash/                            # Splash screen
  welcome/                           # Welcome / onboarding screen
  wizard/                            # Bluetooth pairing wizard
  interactive/                       # Interactive screens (require Zowi connection)
    InteractiveBaseActivity.java     # Base for interactive screens
    InteractiveBaseView.java         # Contract interface for interactive screens
    achievements/                    # Achievements gallery
    home/                            # Home screen
    pad/                             # Action pad controller
    projects/                        # Project detail and quiz
    settings/                        # Settings and calibration
    timeline/                        # Timeline sequence editor
    zowiapps/                        # Zowi mini-games and editor
      minigames/                     # Mini-game base and concrete games
        MinigameBaseActivity.java    # Base for mini-games
        MinigameBaseView.java        # Contract interface for mini-games
```

---

### Activity Hierarchy

All Activities extend from a common base chain. The generic parameter `<T>` is the
Presenter type bound to each Activity.

```
AppCompatActivity
  └── BaseActivity<T extends BasePresenter<?, ?>>
        └── InteractiveBaseActivity<T extends InteractiveBasePresenter<?,?>>
              ├── HomeViewActivity
              ├── PadViewActivity
              ├── TimelineActivity
              ├── AchievementsViewActivity
              ├── ProjectViewActivity
              ├── ProjectQuizViewActivity
              ├── SettingsViewActivity
              ├── CalibrationViewActivity
              ├── MouthsEditorActivity
              └── MinigameBaseActivity<T extends MinigameBasePresenter<?,?>>
                    ├── MouthsMinigameActivity
                    ├── ZowiRunnerMinigameActivity
                    └── ZowiSaysMinigameActivity
  └── SplashViewActivity
  └── WelcomeViewActivity
  └── WizardViewActivity
```

#### Base Classes

| Class | Path | Description |
|---|---|---|
| `BaseActivity<T>` | `views/BaseActivity.java` | Abstract base. Resolves the Presenter via `resolvePresenter()`, manages lifecycle binding (`onCreateView` / `onDestroyView` / `unBindViewAndWireframe`), provides dynamic resource resolution helpers (`setResolvedContentView`, `findResolvedView`). |
| `InteractiveBaseActivity<T>` | `views/interactive/InteractiveBaseActivity.java` | Base for screens that interact with Zowi over Bluetooth. Implements `InteractiveBaseView`. Manages the notification bar (connection status, battery, firmware dialogs, achievements). |
| `MinigameBaseActivity<T>` | `views/interactive/zowiapps/minigames/MinigameBaseActivity.java` | Base for mini-game screens. Implements `MinigameBaseView`. Adds play/help/ranking buttons and the corresponding MakerBox dialogs. |
| `BaseFragment<T>` | `views/BaseFragment.java` | Abstract Fragment base (same pattern as `BaseActivity`). Currently **unused** in the project. |

---

### MVP View Contracts

Each screen defines a **View interface** that the Activity implements. Presenters program
against these interfaces, enabling testability.

| Interface | Extends | Path | Methods |
|---|---|---|---|
| `SplashView` | — | `views/splash/SplashView.java` | _(empty)_ |
| `WelcomeView` | — | `views/welcome/WelcomeView.java` | _(empty)_ |
| `WizardView` | — | `views/wizard/WizardView.java` | `showSearchingForZowis`, `showZowiFound`, `showConnectionSuccessWithEditableName`, `showNoZowisFound`, `showConnectionError`, `showInvalidNameError`, `showSpinner`, `hideSpinner` |
| `InteractiveBaseView` | — | `views/interactive/InteractiveBaseView.java` | `showZowiConnected`, `showZowiConnecting`, `showZowiDisconnected`, `showZowiName`, `showLowBatteryLevel`, `showGoodBatteryLevel`, `showDemoMode`, `showAchievementUnlock`, `showCorruptedZowiDialog`, `showInstallingFirmwareInfoDialog`, `showInstallingFwSuccessDialog`, `showInstallingFwErrorDialog`, `showFirmwareUpdatingDialog`, `hideFirmwareUpdatingDialog`, `showLowBatteryForInstallingFirmwareDialog`, `updateFirmwareUpdatingProgressBar`, `updateNotificationOnAlteredFirmwareDetected`, `updateNotificationOnFwInstallationSuccess`, `updateNotificationOnFwInstallationError`, `hideRestoreFirmwareDialog`, `isZowiAltered`, `showIsInstallingFw` |
| `HomeView` | `InteractiveBaseView` | `views/interactive/home/HomeView.java` | `setUnlockStatusMouthsEditor` |
| `PadView` | `InteractiveBaseView` | `views/interactive/pad/PadView.java` | `showActionsGrid`, `showHelp`, `setUnlockStatus*Button` (5 methods) |
| `TimelineView` | `InteractiveBaseView` | `views/interactive/timeline/TimelineView.java` | `addTimelineCommandToTimeline`, `addTimelineCommandsToTimeline`, `hideCommandsSelector`, `showCommandsSelector`, `showCommandIsBeingPlayed`, `showTimelineStoppedPlaying`, `showHelp`, `showAchievementUnlock` |
| `ProjectView` | `InteractiveBaseView` | `views/interactive/projects/ProjectView.java` | `paintProject`, `paintProjectCompleteness`, `paintProjectQuizBlocked`, `showHexLoadingProgress`, `hideHexLoadingProgress`, `setHexLoadingProgressValue`, `showHexLoadingSuccess`, `showHexLoadingError`, `showProjectLoadingError`, `showLowBatteryForInstallingProjectFirmwareDialog`, `updateNotificationOnProjectHextInstallationSuccess` |
| `ProjectQuizView` | `InteractiveBaseView` | `views/interactive/projects/ProjectQuizView.java` | `paintProjectQuiz`, `showFailureFeedback`, `showProjectQuizLoadingError`, `showQuizCompleteWithoutAchievement`, `showAchievementUnlock` |
| `SettingsView` | `InteractiveBaseView` | `views/interactive/settings/SettingsView.java` | `showNameChangeSuccess`, `showNameChangeError`, `showInvalidNameError`, `showForgetZowiSuccess`, `showForgetZowiError`, `showForgetPlayingHistorySuccess`, `showForgetPlayingHistoryError`, `showRestoringInfoWhenCalibratingAlteredZowi` |
| `CalibrationView` | `InteractiveBaseView` | `views/interactive/settings/CalibrationView.java` | `showLegsCalibration`, `showFeetCalibration`, `showCheckCalibration`, `showLeftLegTrimValue`, `showRightLegTrimValue`, `showLeftFootTrimValue`, `showRightFootTrimValue` |
| `AchievementsView` | `InteractiveBaseView` | `views/interactive/achievements/AchievementsView.java` | `paintAchievements` |
| `MouthsEditorView` | `InteractiveBaseView` | `views/interactive/zowiapps/MouthsEditorView.java` | `showHelp` |
| `MinigameBaseView` | `InteractiveBaseView` | `views/interactive/zowiapps/minigames/MinigameBaseView.java` | `showHelp`, `showPoinsEarned`, `showRanking`, `showAchievementUnlock` |
| `MouthsMiniGameView` | `MinigameBaseView` | `views/interactive/zowiapps/minigames/MouthsMiniGameView.java` | _(extends MinigameBaseView)_ |
| `ZowiRunnerMinigameView` | `MinigameBaseView` | `views/interactive/zowiapps/minigames/ZowiRunnerMinigameView.java` | _(extends MinigameBaseView)_ |
| `ZowiSaysMinigameView` | `MinigameBaseView` | `views/interactive/zowiapps/minigames/ZowiSaysMinigameView.java` | _(extends MinigameBaseView)_ |

---

### Concrete Activities

| Activity | Path | Layout |
|---|---|---|
| `SplashViewActivity` | `views/splash/SplashViewActivity.java` | `activity_splash_view` |
| `WelcomeViewActivity` | `views/welcome/WelcomeViewActivity.java` | `activity_welcome_view` |
| `WizardViewActivity` | `views/wizard/WizardViewActivity.java` | `activity_wizard_view` |
| `HomeViewActivity` | `views/interactive/home/HomeViewActivity.java` | `activity_home_view` |
| `PadViewActivity` | `views/interactive/pad/PadViewActivity.java` | `activity_pad_view` |
| `TimelineActivity` | `views/interactive/timeline/TimelineActivity.java` | `activity_timeline_view` |
| `AchievementsViewActivity` | `views/interactive/achievements/AchievementsViewActivity.java` | `activity_achievements_view` |
| `ProjectViewActivity` | `views/interactive/projects/ProjectViewActivity.java` | `activity_project_view` |
| `ProjectQuizViewActivity` | `views/interactive/projects/ProjectQuizViewActivity.java` | `activity_project_quiz_view` |
| `SettingsViewActivity` | `views/interactive/settings/SettingsViewActivity.java` | `activity_settings_view` |
| `CalibrationViewActivity` | `views/interactive/settings/CalibrationViewActivity.java` | `activity_calibration_view` |
| `MouthsEditorActivity` | `views/interactive/zowiapps/MouthsEditorActivity.java` | `activity_mouths_editor_view` |
| `MouthsMinigameActivity` | `views/interactive/zowiapps/minigames/MouthsMinigameActivity.java` | `activity_mouths_minigame_view` |
| `ZowiRunnerMinigameActivity` | `views/interactive/zowiapps/minigames/ZowiRunnerMinigameActivity.java` | `activity_zowi_runner_minigame_view` |
| `ZowiSaysMinigameActivity` | `views/interactive/zowiapps/minigames/ZowiSaysMinigameActivity.java` | `activity_zowi_says_minigame_view` |

---

### Custom UI Components

Reusable View classes in `com.bq.zowi.components`.

| Component | Path | Description |
|---|---|---|
| `EduBar` | `components/EduBar.java` | Custom notification/status bar shown at the top of interactive screens. |
| `QuizView` | `components/QuizView.java` | Custom view for rendering quiz questions and answers. |
| `GifView` | `components/GifView.java` | View capable of displaying animated GIF images. |
| `MultipleStatesButton` | `components/MultipleStatesButton.java` | Button with multiple visual states (enabled/disabled/selected). |
| `NonSwipeableViewPager` | `components/NonSwipeableViewPager.java` | ViewPager that disables swipe gestures (navigation via buttons only). |
| `SquareRelativeLayout` | `components/SquareRelativeLayout.java` | RelativeLayout that maintains a 1:1 aspect ratio. |
| `TopCropImageView` | `components/TopCropImageView.java` | ImageView that crops from the top instead of center. |
| `ZowiAppView` | `components/home/ZowiAppView.java` | Grid item representing a Zowi app on the home screen. |
| `MouthGridLayout` | `components/games/MouthGridLayout.java` | Grid layout for the mouth editor minigame. |
| `MouthGridItemView` | `components/games/MouthGridItemView.java` | Individual cell in the mouth grid editor. |
| `CommandTileView` | `components/timeline/CommandTileView.java` | Draggable command tile used in the timeline editor. |
| `SelectedCommandRowView` | `components/timeline/SelectedCommandRowView.java` | Row displaying a selected command in the timeline. |

#### MakerBox Dialog System

A family of modal dialog components used across mini-games and interactive screens.

| Component | Path | Description |
|---|---|---|
| `MakerBox` | `components/makerboxdialogs/MakerBox.java` | Base container for MakerBox-style UI elements. |
| `MakerBoxButton` | `components/makerboxdialogs/MakerBoxButton.java` | Styled button used inside MakerBox dialogs. |
| `MakerBoxDialog` | `components/makerboxdialogs/MakerBoxDialog.java` | Base modal dialog overlay. |
| `MakerBoxDialogAchievement` | `components/makerboxdialogs/MakerBoxDialogAchievement.java` | Dialog showing an achievement unlock. |
| `MakerBoxDialogFailure` | `components/makerboxdialogs/MakerBoxDialogFailure.java` | Dialog showing a failure/game-over message. |
| `MakerBoxDialogSuccess` | `components/makerboxdialogs/MakerBoxDialogSuccess.java` | Dialog showing a success message. |
| `MakerBoxDialogPointsEarnedEnableRanking` | `components/makerboxdialogs/MakerBoxDialogPointsEarnedEnableRanking.java` | Dialog showing points earned with ranking name input. |
| `MakerBoxDialogRanking` | `components/makerboxdialogs/MakerBoxDialogRanking.java` | Dialog showing the ranking leaderboard. |
| `MakerBoxDialogScrollable` | `components/makerboxdialogs/MakerBoxDialogScrollable.java` | Scrollable variant of the MakerBox dialog. |

---

### Adapters and ViewHolders

| Class | Path | Description |
|---|---|---|
| `RecyclerAdapter` | `components/recyclerview/RecyclerAdapter.java` | Generic RecyclerView adapter with ViewHolder resolution. |
| `RecyclerResolver` | `components/recyclerview/RecyclerResolver.java` | Resolves which ViewHolder/ layout to use for a given item type. |
| `RecyclerViewHolder` | `components/recyclerview/RecyclerViewHolder.java` | Base ViewHolder class. |
| `AutofitRecyclerView` | `components/recyclerview/AutofitRecyclerView.java` | RecyclerView that auto-adjusts column count based on item width. |
| `WrapContentLinearLayoutManager` | `components/recyclerview/WrapContentLinearLayoutManager.java` | LinearLayoutManager that gracefully handles layout errors. |
| `TimelineDraggableItemAdapter` | `views/interactive/timeline/TimelineDraggableItemAdapter.java` | Adapter for drag-and-drop timeline command items. |
| `TimelineDataProvider` | `views/interactive/timeline/TimelineDataProvider.java` | Data provider for the timeline drag-and-drop system. |
| `AbstractDataProvider` | `views/interactive/timeline/AbstractDataProvider.java` | Abstract data provider base for draggable items. |
| `CommandsTileViewHolder` | `views/interactive/timeline/CommandsTileViewHolder.java` | ViewHolder for command tiles in the timeline grid. |
| `CommandsGridViewHolderResolver` | `views/interactive/timeline/CommandsGridViewHolderResolver.java` | Resolves ViewHolder types for the commands grid. |
| `GridCommandResourceResolver` | `views/interactive/timeline/GridCommandResourceResolver.java` | Maps GridCommand types to drawable/string resources. |
| `TimelineSelectedCommandIconResolver` | `views/interactive/timeline/TimelineSelectedCommandIconResolver.java` | Resolves icons for selected timeline commands. |
| `AchievementRowViewHolder` | `views/interactive/achievements/AchievementRowViewHolder.java` | ViewHolder for achievement list rows. |
| `AchievementViewHolderResolver` | `views/interactive/achievements/AchievementViewHolderResolver.java` | Resolves ViewHolder layout for achievement items. |
| `RankingRowViewHolder` | `views/interactive/zowiapps/minigames/RankingRowViewHolder.java` | ViewHolder for ranking list rows. |
| `RankingViewHolderResolver` | `views/interactive/zowiapps/minigames/RankingViewHolderResolver.java` | Resolves ViewHolder layout for ranking entries. |

---

### XML Layouts

All layout files are in `app/src/main/res/layout/`. Key layouts by screen:

| Screen | Layout File |
|---|---|
| Splash | `activity_splash_view.xml` |
| Welcome | `activity_welcome_view.xml` |
| Wizard | `activity_wizard_view.xml` |
| Home | `activity_home_view.xml` |
| Pad | `activity_pad_view.xml` |
| Timeline | `activity_timeline_view` |
| Achievements | `activity_achievements_view.xml` |
| Project detail | `activity_project_view.xml` |
| Project quiz | `activity_project_quiz_view.xml` |
| Settings | `activity_settings_view.xml` |
| Calibration | `activity_calibration_view.xml` |
| Mouths editor | `activity_mouths_editor_view.xml` |
| Mouths minigame | `activity_mouths_minigame_view.xml` |
| Zowi Runner minigame | `activity_zowi_runner_minigame_view.xml` |
| Zowi Says minigame | `activity_zowi_says_minigame_view.xml` |

Shared component layouts:

| Layout | Used By |
|---|---|
| `component_edubar.xml` | `InteractiveBaseActivity` — top notification/status bar |
| `component_zowi_app.xml` | `ZowiAppView` — home screen app grid item |
| `component_home_projects.xml` | Home screen project list |
| `component_home_games.xml` | Home screen games grid |
| `component_quiz_view.xml` | `QuizView` — quiz question display |
| `quiz_question_view.xml` / `quiz_answer_row_view.xml` | Quiz question and answer rows |
| `component_makerbox.xml` | `MakerBox` base container |
| `component_makerbox_success.xml` | `MakerBoxDialogSuccess` |
| `component_makerbox_failure.xml` | `MakerBoxDialogFailure` |
| `component_makerbox_ranking.xml` | `MakerBoxDialogRanking` |
| `component_makerbox_achievement.xml` | `MakerBoxDialogAchievement` |
| `component_makerbox_scrollable.xml` | `MakerBoxDialogScrollable` |
| `component_makerbox_points_earned_enable_ranking.xml` | `MakerBoxDialogPointsEarnedEnableRanking` |
| `component_command_tile_view.xml` / `command_tile_view.xml` | Timeline command tiles |
| `component_selected_command_row_view.xml` / `timeline_selected_command_row_view.xml` | Timeline selected commands |
| `mouth_grid_item.xml` | `MouthGridItemView` — mouth editor cells |
| `ranking_entry_row_view.xml` / `achievement_row_view.xml` | List row layouts |
| `component_wizard_*.xml` | Wizard steps (welcome, searching, found, connected) |
| `component_calibration_*.xml` | Calibration steps (check, legs, feet) |
| `component_notification.xml` | Notification bar content |

---

## Navigation Structure

### Launcher Entry Point

`SplashViewActivity` is the launcher activity. After a 1.5s timer:

- **Active session exists** (device address saved or wizard dismissed) → `HomeViewActivity`
- **No active session** → `WelcomeViewActivity`

### Navigation Tree

```
SplashViewActivity
  │
  ├── (active session) ──────────────────────────────────┐
  │                                                       │
  └── (no session) ──► WelcomeViewActivity                │
                         │                                │
                         └── [Start Wizard] ──► WizardViewActivity
                                                    │
                                                    └── [Wizard complete] ──► HomeViewActivity ◄──────┘
                                                            │
                        ┌───────────────────────────────────┤
                        │                                   │
                        ├── [Settings] ──────────► SettingsViewActivity
                        │                              │
                        │                              ├── [Home] ──────────────────┐
                        │                              └── [Calibrate] ──► CalibrationViewActivity
                        │                                                           │
                        │                                                           └── [Home] ─────┐
                        │                                                                           │
                        ├── [Achievements] ──► AchievementsViewActivity                             │
                        │                           │                                                │
                        │                           └── [Home] ──────────────────────────────────────┤
                        │                                                                           │
                        ├── [Pad] ──► PadViewActivity                                                │
                        │                │                                                           │
                        │                └── [Home] ─────────────────────────────────────────────────┤
                        │                                                                           │
                        ├── [Timeline] ──► TimelineActivity                                        │
                        │                    │                                                       │
                        │                    └── [Home] ─────────────────────────────────────────────┤
                        │                                                                           │
                        ├── [Zowi Says] ──► ZowiSaysMinigameActivity                                │
                        │                     │                                                      │
                        │                     └── [Home] ────────────────────────────────────────────┤
                        │                                                                           │
                        ├── [Mouths Minigame] ──► MouthsMinigameActivity                            │
                        │                           │                                                │
                        │                           └── [Home] ──────────────────────────────────────┤
                        │                                                                           │
                        ├── [Mouths Editor] ──► MouthsEditorActivity (unlock required)               │
                        │                         │                                                  │
                        │                         └── [Home] ────────────────────────────────────────┤
                        │                                                                           │
                        ├── [Project x10] ──► ProjectViewActivity                                   │
                        │      (with project_id_extra) │                                             │
                        │                               ├── [Home] ──────────────────────────────────┤
                        │                               ├── [Run Test] ──► ProjectQuizViewActivity   │
                        │                               │                        │                   │
                        │                               │                        ├── [Home] ─────────┤
                        │                               │                        └── [Back] ─────────┘
                        │                               └── [Link] ──► External Browser
                        │
                        └── [Launch Wizard] (from any InteractiveBaseActivity status bar)
                                                    │
                                                    └──► WizardViewActivity
```

### Intent Flags

| Flag Value | Constant | Used When |
|---|---|---|
| `603979776` | `FLAG_ACTIVITY_NEW_TASK \| FLAG_ACTIVITY_CLEAR_TASK` | All "return to Home" and "Launch Wizard" navigation. Clears the entire back stack. |
| _(none)_ | Default | Home → sub-screens (Settings, Pad, Timeline, etc.). Pushes onto back stack normally. |

### Navigation Routes (All Paths)

| # | From | To | Trigger | Clears Back Stack |
|---|---|---|---|---|
| 1 | `SplashViewActivity` | `HomeViewActivity` | 1.5s timer (active session) | Yes |
| 2 | `SplashViewActivity` | `WelcomeViewActivity` | 1.5s timer (no session) | Yes |
| 3 | `WelcomeViewActivity` | `WizardViewActivity` | "Start Wizard" button | Yes |
| 4 | `WizardViewActivity` | `HomeViewActivity` | Wizard completion | Yes |
| 5 | `HomeViewActivity` | `SettingsViewActivity` | Settings button | No |
| 6 | `HomeViewActivity` | `AchievementsViewActivity` | Achievements button | No |
| 7 | `HomeViewActivity` | `PadViewActivity` | Gamepad button | No |
| 8 | `HomeViewActivity` | `TimelineActivity` | Timeline button | No |
| 9 | `HomeViewActivity` | `ZowiSaysMinigameActivity` | Zowi Says button | No |
| 10 | `HomeViewActivity` | `MouthsMinigameActivity` | Mouths Minigame button | No |
| 11 | `HomeViewActivity` | `MouthsEditorActivity` | Mouths Editor button (unlock required) | No |
| 12 | `HomeViewActivity` | `ProjectViewActivity` | Project button (x10 variants) | No |
| 13 | `SettingsViewActivity` | `HomeViewActivity` | Home button | Yes |
| 14 | `SettingsViewActivity` | `CalibrationViewActivity` | Calibrate option | Yes |
| 15 | `CalibrationViewActivity` | `HomeViewActivity` | Home button | Yes |
| 16 | `TimelineActivity` | `HomeViewActivity` | Home button | Yes |
| 17 | `AchievementsViewActivity` | `HomeViewActivity` | Home button | Yes |
| 18 | `PadViewActivity` | `HomeViewActivity` | Home button | Yes |
| 19 | `MouthsEditorActivity` | `HomeViewActivity` | Home button | Yes |
| 20 | `MouthsMinigameActivity` | `HomeViewActivity` | Home button | Yes |
| 21 | `ZowiSaysMinigameActivity` | `HomeViewActivity` | Home button | Yes |
| 22 | `ProjectViewActivity` | `HomeViewActivity` | Home button | Yes |
| 23 | `ProjectViewActivity` | `ProjectQuizViewActivity` | "Run Test" button | No |
| 24 | `ProjectViewActivity` | External Browser | Link button | No |
| 25 | `ProjectQuizViewActivity` | `HomeViewActivity` | Home button | Yes |
| 26 | `ProjectQuizViewActivity` | `ProjectViewActivity` | Back button (on failure) | No |
| 27 | Any `InteractiveBaseActivity` | `WizardViewActivity` | "Launch Wizard" status bar button | Yes |

### Project Variants

`ProjectViewActivity` is reused for 10 different projects, differentiated by the `project_id_extra` string extra:

| Extra Value | Project |
|---|---|
| `01_project_mueve` | Move |
| `02_project_choreography` | Choreography |
| `03_project_forma` | Shape |
| `04_project_bio1` | Bio 1 |
| `05_project_bio3` | Bio 3 |
| `06_project_reprogram` | Reprogram |
| `07_project_helloworld` | Hello World |
| `08_project_bitbloq2` | BitBloq 2 |
| `09_project_adivinawi` | Guess |
| `10_project_gravity` | Gravity |

---

## Screen Actuation Logic

This section documents, for every screen, **when controls are enabled/disabled, what each control
does, where it navigates, and what can go wrong**. It is the behavioral complement to the
navigation tree above. Source: View / Presenter / Wireframe triples under
`app/src/main/java/com/bq/zowi/`.

### Cross-Cutting: Connection-Dependent Enable/Disable

All interactive screens extend `InteractiveBaseActivity`, whose `updateStatusBar()` enables or
disables the `zowiDependantViews[]` array (alpha 0.5 + `setEnabled(false)`) based on a single status.
Precedence (first match wins): demo mode (no active Zowi) → disabled; installing firmware → disabled;
altered/custom firmware → **enabled** (and the Factory-Reset notification button appears); low
battery (< 50%, `BATTERY_LEVEL_LOWER_THRESHOLD`) → enabled; connected → enabled; connecting/
disconnected → disabled. Firmware restore / low-battery flows surface via shared MakerBox dialogs
(`showInstallingFirmwareInfoDialog`, `showLowBatteryForInstallingFirmwareDialog`,
`showInstallingFwSuccessDialog`/`ErrorDialog`, `showCorruptedZowiDialog`) driven by
`InteractiveBasePresenterImpl.installFactoryFirmware()` → `SendAppToZowiInteractor(factoryFirmwarePath)`
where `factoryFirmwarePath = ZOWI_BASE_v2.hex`.

### SplashViewActivity

| Control | Enable | Action |
|---|---|---|
| Continue (`splash_continue_button`) | always | cancels the 1.5 s timer `Runnable`, then `presenter.onContinueClicked()` |
| Exit (`splash_exit_button`) | always | cancels the timer, then `finish()` (closes app) |

- **Timer:** `Handler.postDelayed(runnable, 1500)` auto-navigates if the user does nothing; `onDestroy` removes callbacks (leak guard). `SplashPresenterImpl.initialize()` is a no-op.
- **Navigation decision** (`onContinueClicked`): `isActiveSession = sessionController.loadActiveZowiDeviceAddress() != null || sessionController.hasDismissedWizard()`. `SplashWireframeImpl.dismissSplash(isActiveSession)` → `presentHome()` (`HomeViewActivity`) when active, else `presentWelcome()` (`WelcomeViewActivity`). Both `finish()` (no return to Splash).
- **No EduBar**; version badge comes from `BaseActivity` (`Build <versionCode>`, title `… v<versionName>`).
- **Errors:** none surfaced.

### WelcomeViewActivity

| Control | Enable | Action / Navigation |
|---|---|---|
| Start wizard (`welcome_start_wizard_button`) | always | `presenter.startWizard()` → `WelcomeWireframe.showWizard()` → `WizardViewActivity` (flags `NEW_TASK|CLEAR_TOP` + `finish`, back stack reset) |
| Letter to parents | always | reveals scrollable `MakerBoxDialogScrollable` (informational) |

- **Errors:** none.

### WizardViewActivity (Bluetooth pairing)

A `NonSwipeableViewPager` of 4 pages: **START → SEARCH → PAIR → CONNECTED**; page changes are driven only by the presenter.

| Page | Control | Enable | Action |
|---|---|---|---|
| 0 | Find Zowis (`wizard_find_zowis_button`) | always | `findZowis()` |
| 0 | Dismiss (`wizard_dismiss_button`) | always | `dismissWizard()` |
| 1 | Retry | always | `findZowis()` |
| 2 | Connect (`wizard_connect_to_zowi_button`) | always; **onClick disables itself** (re-enabled only on error) | `connectToZowi(address)` |
| 3 | Name (`wizard_name_edit_text`) | live-validated by `TextWatcher` | letters only, max 10 (`NameValidator`) |
| 3 | Set name (`wizard_set_name_button`) | always | `changeZowiName(name, address)` |

- **Find:** `FindZowisInteractor` enables BT, starts discovery, and emits the **first** device whose name starts with `"Zowi"` or MAC OUI `B4:9D:0B:3` (`firstOrError()`) — single Zowi, no multi-device list. `onError` → `showNoZowisFound()` (retry + dismiss).
- **Connect:** `ConnectToZowiInteractor.connectToZowiAndRetrieveData` opens the SPP socket (`SPP_UUID 00001101-…-00805f9b34fb`), retries 3× @1 s, then disables+re-enables BT once; zips name/appId/battery with a **3 s timeout** → `ConnectionSuccessData` may be `null`.
- **Name:** empty → defaults to `"Zowi"`; invalid (non-letters or >10) → `showInvalidNameError()` (EduBar) and aborts; else `ChangeZowiNameInteractor` → `NameSetCommand` sent to the robot.
- **Completion:** `wizardComplete(address)` saves `activeZowiDeviceAddress`; `dismissWizard()` saves `wizardDismissed=true`. Both → `presentWizardCompleteView()` → `HomeViewActivity` (back stack cleared).
- **Permission gap:** no runtime permission request exists; relies on install-time normal `BLUETOOTH_SCAN`/`BLUETOOTH_CONNECT`. If revoked, `startDiscovery()`/`connect()` throw `SecurityException` that surfaces only as a generic error log (connect button just re-enables).
- **Errors:** no Zowis (retry/dismiss); connection error (button re-enable + log only); invalid name (EduBar + toast); name-change failure (log only, silent); 3 s timeout (proceeds without name, silent); `data != null` but `zowiName == null` (dead-end, no navigation).

### HomeViewActivity

| Control | Enable | Action / Navigation |
|---|---|---|
| Settings / Achievements / Gamepad / Timeline / ZowiSays / MouthsMinigame | always | `presentXView()` → respective `Activity` (Home stays in back stack) |
| **Mouths Editor tile** | **locked by `Achievement.Id.mouths_editor`** | if unlocked → `presentMouthsEditorView()`; if locked → `setClickable(false)`, click does nothing |
| 10 Project buttons | always | `presentProject(id)` → `ProjectViewActivity` (with `PROJECT_ID_EXTRA`) |
| ZowiRunner | (presenter/wireframe exist but **not wired in UI**) | `presentZowiRunnerMinigameView()` |

- `manageConnection()` on resume subscribes to `achievementsController.getAchievementsList()` and toggles the Mouths-Editor tile.
- **Time-based unlocks** (`onPostCreate`): 2nd usage → `crusaito`; ≥4 days of use → `fart` (surfaced via `showAchievementUnlock`).
- **Errors:** none surfaced.

### SettingsViewActivity

`zowiDependantViews = [loadFactoryFirmware*, changeZowiName*, calibrateZowi*]` → **Rename, Restore firmware, and Calibrate are disabled when not connected**; **Forget Zowi, Forget playing history, Look for updates, and Hospital are always enabled**.

| Control | Enable | Action / Use case | Errors |
|---|---|---|---|
| Home | always | `presentHome()` (flags + `finish`) | — |
| Change Zowi name | **conn-gated** | dialog → `ChangeZowiNameInteractor` (`NameSetCommand`); empty→default, invalid→EduBar | `showNameChangeSuccess/Error` (EduBar) |
| Look for updates | always | `openGooglePlayToCheckUpdates()` (`market://`, fallback `https://`) | `ActivityNotFoundException` → https fallback |
| Forget playing history | always | dialog → `ForgetPlayingHistoryInteractor` (reset projects/logs/game progress/rankings/achievements) | `showForgetPlayingHistorySuccess/Error` |
| Forget Zowi | always (even in demo) | dialog → `ForgetZowiInteractor` (factory name + `resetActiveZowi` + `stopConnection`) | `showForgetZowiSuccess/Error` |
| Calibrate Zowi | **conn-gated** | if **altered** → firmware-restore flow; else `presentCalibrationView()` | altered → low-battery/restore dialogs |
| Restore firmware / Load factory firmware | **conn-gated** | `manageLowBatteryForInstallingFirmware` → 50% battery check → restore-info dialog → `installFactoryFirmware(false)` → `SendAppToZowiInteractor(ZOWI_BASE_v2.hex)` | firmware updating/success/error dialogs |
| Hospital | always | `openHospitalWeb()` | `ActivityNotFoundException` → http fallback |

- **Notification bar** (base, on Settings too): Factory reset (only if `isAltered`), Connect (if disconnected), Launch wizard (if demo).

### CalibrationViewActivity

4-page pager **WARNING → LEGS → FEET → CHECK** (navigation done in the View). State: `trimLeftLegYL/RightLegYR/LeftFootRL/RightFootRR` (clamp `[-30, +30]`), `BASE_GRADE = 90`, 200 ms debounce (`MIN_TIME_BETWEEN_CALIBRATION_CHANGES`).

| Control | Action | Wire |
|---|---|---|
| Warning cancel | `presentHome()` | → Home (+`finish`) |
| Warning continue | send `CALIBRATE_TRIM 0 0 0 0` then `CALIBRATE_GRADES 90 90 90 90`, → LEGS page | `CalibrationCommand` |
| Legs/Foot +/- | increment/decrement trim (clamped); debounced → `CALIBRATE_GRADES <trim+90>…` | `CalibrationCommand` |
| Check "movement" | `CALIBRATE_TRIM <trims>` then `AnimationCommand(VICTORY)` (live preview) | — |
| Check "confirm" | `CALIBRATE_TRIM <trims>` then `presentHome()` + `VICTORY` | finalizes, → Home |
| Restart | reset trims/grades, → LEGS | — |

- `CalibrationCommand` wire: `"C <lYL> <rYR> <lRL> <rRR>\r\n"` (trim) or `"G …"` (grades = trim+90).
- **Every exit returns to Home** (no return to Settings). Entry only via Settings (which blocks altered Zowis into firmware restore first).

### PadViewActivity (gamepad)

`zowiDependantViews` = the **13 movement/action buttons** (disabled when not connected). Mouths/Animations openers are **not** gated.

| Control | Enable | Action / Command |
|---|---|---|
| Movement arrows (up/down/left/right, turn L/R) | conn-gated (touch down/up) | `ForwardBackwardCommand(WALK,…)`, `LeftRightCommand(TURN,…)` |
| Action buttons (bend, shakeleg, updown, jitter, swing, flapping, crusaito) | conn-gated; **achievement-locked** (`crusaito/flapping/shake_leg/jitter/swing` gated by unlock; locked→greyed, not clickable) | `LeftRightCommand(BEND/SHAKE_LEG/…)`, `StaticCommand(UPDOWN/JITTER/SWING/FLAPPING)` |
| Speed cycle | always | slow 2000 / medium 1000 / fast 700 ms (`configuredDuration`) |
| Mouths / Animations openers | always (not conn-gated) | open `GridCommand` selector → `MouthCommand` / `AnimationCommand` |
| Home / Help | always | `presentHome()` (+`finish`, fixes back-stack); `showHelp()` |

- **Incompatible-action blocking:** holding bend/shakeleg/crusaito blocks walk; flapping blocks moonwalker; turn blocks up/down & left/right (greyed `blocked_pad_*`); unblocked only when nothing conflicting is held.
- **Flow control:** `isCommandSendingBlocked()` allows one command in flight; reset only on `"F"` ack. Release → `StopCommand` (not gated). Demo mode → launch-wizard button + dependant views disabled.
- **First use:** help shown; `shake_leg` unlocked on first GAMEPAD play.
- **Errors:** not connected → dependant buttons disabled; mouths/animations openers still clickable but `sendCommandToZowi` is a no-op/swallowed.

### TimelineActivity

`zowiDependantViews` = **Play button only** (Stop, add buttons, items not gated).

| Control | Enable | Action |
|---|---|---|
| Add Movement / Animation / Mouth | always | `GridCommand` selector (movement/animation unlock-gated; mouth not) → `addTimelineCommandToTimeline(new TimelineCommand(cmd, 1))` |
| Play (`activity_timeline_play_button`) | **conn-gated** | `TimelineCommandSubscriber`; waits `"A"` ack; expands by repetitions + appends `StopCommand`; plays sequentially; ≥15 commands → `anxious` achievement; **plays once, no loop** |
| Stop | always | dispose subscriber, `StopCommand` |
| Per-item spinners (repetitions/duration/direction) | per command type | mutate the `TimelineCommand` (duration only if `allowedDurations`, direction only if `allowedDirections`, repetitions only if `isRepeatable`) |
| Drag reorder | always | long-press drag (`RecyclerViewDragDropManager`); not auto-saved |
| Delete | always | trash button per row (`TimelineDataProvider.removeItem`) |

- **Persistence:** `onPause` → `GameController.saveProgress(TIMELINE_GAME_ID, list)`; `onPostCreate` → `loadProgress` + resume.
- **Errors:** Play disabled when not connected, but a single-item tap is not connection-gated (can wait for an ack that never arrives); load/save failures logged (`Grove`).

### MouthsEditorActivity

6×5 `MouthGridLayout` (30 `MouthGridItemView` cells), togglable on touch. **No `zowiDependantViews` → grid always touchable.**

| Control | Action |
|---|---|
| LED grid | on finger lift → 30-bit `0/1` string → `LedCommand(binary)` (`"L 00<matrix>\r\n"`) → `sendCommandToZowi` |
| Home / Help | `presentHome()` (+`finish`); `showHelp()` |

- **Navigation:** Home and Help only. Does **not** navigate to the Mouths minigame — both are launched independently from Home.
- **Errors:** not connected → `sendLedMouth` is a no-op/swallowed (no BT guard, no UI error).

### MouthsMinigameActivity

| Control | Enable | Action |
|---|---|---|
| Play (`minigame_play_button`) | hidden after press | level 1; random `MouthCommand` (4 types); send to Zowi; 10 s countdown (−1000 ms every 4th level) |
| Mouth grid | touchable only while a round is active | draw → 30-bit compare to `mouthInGame.getLedMatrix()`; correct → `VICTORY`+`StopCommand` → next level |
| Home / Help / Ranking | always | `presentHome()`; `showHelp()`; `getRanking(MOUTHS_GAME_ID)` |

- **End:** countdown → `gameOver(score = level-1)`. `score ≥ 8` → `checkAchievementAndUnlockIt(mouths_editor)`; else `ANGRY`. Ranking if `score > 2` and top-10 (`MakerBoxDialogPointsEarnedEnableRanking` vs `MakerBoxDialogFailure`).
- **Fully playable offline** (local binary compare; Zowi show is cosmetic).

### ZowiSaysMinigameActivity

| Control | Enable | Action |
|---|---|---|
| 4 action buttons (top/bottom left/right) | active only while `isPlaying` | append `Command` to user sequence; correct→lengthen, wrong/longer→`gameOver` |
| Play / Help / Ranking / Home | always | `presentHome()`; `showHelp()` (first play only); `getRanking(ZOWI_SAYS_GAME_ID)` |

- **Play:** `ForwardBackwardCommand(WALK,FWD)`, `LeftRightCommand(BEND,RIGHT)`, `StaticCommand(JUMP)`, `LeftRightCommand(MOONWALKER,RIGHT)` + `StopCommand`; Zowi plays via `"A"` ack (`blockUserControls` overlay), user repeats.
- **End:** `gameOver(score)`; `score ≥ 12` → `checkAchievementAndUnlockIt(in_love)`; else `ANGRY`. Ranking if `score > 3` and top-10.
- **Errors:** command-send errors logged (swallowed); ranking retrieval error logged.

### ZowiRunnerMinigameActivity

| Control | Enable | Action |
|---|---|---|
| Play FAB | on press enables Left/Right, hides FAB, starts 2 s timer | every 2 s with no input → `StopCommand` (auto-stop) |
| Left / Right | disabled until Play | press → `duration = clamp(500 + |Δt|*1.6, 500, 1500)` → `ForwardRunnerCommand(WALK,FWD,duration)` |
| Accelerometer (tilt) | threshold `y>5`→RIGHT, `y<-5`→LEFT | `LeftRightRunnerCommand(TURN,dir,duration)` |
| Stop | on press | `StopCommand`, cancel timer |

- **No `GAME_ID`, no ranking, no achievement.** Commands via `CommandSingleSubscriber` (errors swallowed).
- **Navigation:** **no Home button wired** — returns via system Back only (possible UX gap).

### ProjectViewActivity (Discover detail)

| Control | Enable | Action |
|---|---|---|
| Run Test (`activity_project_run_test_button`) | **only if `!isQuizBlocked`**; when blocked shows `mm:ss` countdown (10 min) | `presentQuiz(projectId)` → `ProjectQuizViewActivity` |
| Home | always | `presentHome()` (+`finish`) |
| Install HEX | only if `projectHex != null` (conn-gated) | `manageLowBatteryForInstallingProjectFirmware` → 50% battery check → `SendAppToZowiInteractor(projectHex)` (progress dialog) |
| Link | always | open `project.projectUrlResourceId` in browser |
| Completed icon | display | `project_done_icon` / `project_not_done_icon` |

- **Firmware per project** (`project_hex`): only **06 reprogram → `ZOWI_Alarm_v2.hex`** and **09 adivinawi → `ZOWI_Adivinawi_v2.hex`** flash a distinct firmware; the other 8 have `""` (note: `projectHex` is non-null even when empty → potential no-op/error path rather than re-flashing `ZOWI_BASE_v2`).
- **Errors:** project load error (EduBar); HEX flash error has two variants (still-connected vs not-connected) with Retry; low-battery dialog → OK flashes, Cancel dismisses.

### ProjectQuizViewActivity (Run Test)

| Control | Enable | Action |
|---|---|---|
| Home / achievement-continue / success-continue | always | `presentHome()` |
| Quiz answer rows | auto-run | correct → advance; last → `onQuizComplete`; wrong → `quizFailure` |
| Failure "Continue" | always | `onBackPressed()` → back to `ProjectViewActivity` |

- **Complete:** `setProjectCompleted` + `checkAchievementAndUnlockIt(Achievement.Id.valueOf(achievementId))` → success/achievement dialog.
- **Failure:** `blockProjectQuiz` (blockade `600000 ms` = 10 min) → failure feedback; on return the Run Test button is blocked with `mm:ss` countdown.
- **Errors:** quiz load error (EduBar); wrong-answer `true`/`false` string leaks fixed in 1.9.1.4 (now resolved by resource name at runtime).

### AchievementsViewActivity

| Control | Enable | Action |
|---|---|---|
| Home | always | `presentHome()` (+`finish`) |
| Pager (3 pages) | always | MOVEMENTS / ANIMATIONS / GAMES `RecyclerView`s by `achievement.type` |
| Easter-egg unlocker1/2/3 | touch combo (one-shot) | `unlocker1`→cond1; `unlocker2`→cond2 (needs cond1); `unlocker3`→ if both → `unlockAllAchievements()` + `AnimationCommand(VICTORY)` |

- `loadAchievements()` splits the 17 seeded achievements (`assets/achievements/initial_list.json`) into the three lists; locked vs unlocked rendered via `AchievementRowViewHolder`.
- **Errors:** list/unlock errors logged only (no dialog).
