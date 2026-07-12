# ZowiAppReborn Architecture - VIEW

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
