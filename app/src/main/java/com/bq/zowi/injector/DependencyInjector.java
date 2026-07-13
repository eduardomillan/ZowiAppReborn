package com.bq.zowi.injector;

import com.bq.zowi.api.AchievementsController;
import com.bq.zowi.api.AppController;
import com.bq.zowi.api.BTConnectionController;
import com.bq.zowi.api.GameController;
import com.bq.zowi.api.ProjectController;
import com.bq.zowi.api.RankingController;
import com.bq.zowi.api.SessionController;
import com.bq.zowi.crashreporting.CustomErrorReporter;
import com.bq.zowi.presenters.interactive.achievements.AchievementsPresenter;
import com.bq.zowi.presenters.interactive.achievements.AchievementsPresenterImpl;
import com.bq.zowi.presenters.interactive.home.HomePresenter;
import com.bq.zowi.presenters.interactive.home.HomePresenterImpl;
import com.bq.zowi.presenters.interactive.pad.PadPresenter;
import com.bq.zowi.presenters.interactive.pad.PadPresenterImpl;
import com.bq.zowi.presenters.interactive.projects.ProjectPresenter;
import com.bq.zowi.presenters.interactive.projects.ProjectPresenterImpl;
import com.bq.zowi.presenters.interactive.projects.ProjectQuizPresenter;
import com.bq.zowi.presenters.interactive.projects.ProjectQuizPresenterImpl;
import com.bq.zowi.presenters.interactive.settings.CalibrationPresenter;
import com.bq.zowi.presenters.interactive.settings.CalibrationPresenterImpl;
import com.bq.zowi.presenters.interactive.settings.SettingsPresenter;
import com.bq.zowi.presenters.interactive.settings.SettingsPresenterImpl;
import com.bq.zowi.presenters.interactive.timeline.TimelinePresenter;
import com.bq.zowi.presenters.interactive.timeline.TimelinePresenterImpl;
import com.bq.zowi.presenters.interactive.zowiapps.MouthsEditorPresenter;
import com.bq.zowi.presenters.interactive.zowiapps.MouthsEditorPresenterImpl;
import com.bq.zowi.presenters.interactive.zowiapps.minigames.MouthsMinigamePresenter;
import com.bq.zowi.presenters.interactive.zowiapps.minigames.MouthsMinigamePresenterImpl;
import com.bq.zowi.presenters.interactive.zowiapps.minigames.ZowiRunnerMinigamePresenter;
import com.bq.zowi.presenters.interactive.zowiapps.minigames.ZowiRunnerMinigamePresenterImpl;
import com.bq.zowi.presenters.interactive.zowiapps.minigames.ZowiSaysMinigamePresenter;
import com.bq.zowi.presenters.interactive.zowiapps.minigames.ZowiSaysMinigamePresenterImpl;
import com.bq.zowi.presenters.splash.SplashPresenter;
import com.bq.zowi.presenters.splash.SplashPresenterImpl;
import com.bq.zowi.presenters.welcome.WelcomePresenter;
import com.bq.zowi.presenters.welcome.WelcomePresenterImpl;
import com.bq.zowi.presenters.wizard.WizardPresenter;
import com.bq.zowi.presenters.wizard.WizardPresenterImpl;
import com.bq.zowi.usecases.ChangeZowiNameInteractor;
import com.bq.zowi.usecases.CheckAchievementAndUnlockItInteractor;
import com.bq.zowi.usecases.CheckInstalledZowiAppInteractor;
import com.bq.zowi.usecases.ConnectToZowiInteractor;
import com.bq.zowi.usecases.FindZowisInteractor;
import com.bq.zowi.usecases.ForgetPlayingHistoryInteractor;
import com.bq.zowi.usecases.ForgetZowiInteractor;
import com.bq.zowi.usecases.MeasureZowiBatteryLevelInteractor;
import com.bq.zowi.usecases.SendAppToZowiInteractor;
import com.bq.zowi.usecases.SendCommandToZowiInteractor;
import io.reactivex.Scheduler;

public abstract class DependencyInjector {
    public abstract void init();

    public abstract void shutdown();

    public abstract CustomErrorReporter provideErrorReporter();

    public abstract String provideFactoryFirmwarePath();

    public abstract int provideFactoryFirmwareVersion();

    public abstract Scheduler provideUiScheduler();

    public abstract SessionController provideSessionController();

    public abstract BTConnectionController provideBTConnectionController();

    public abstract GameController provideGameController();

    public abstract GameController provideTimelineGameController();

    public abstract AchievementsController provideAchievementsController();

    public abstract AppController provideAppController();

    public abstract ProjectController provideProjectController();

    public abstract RankingController provideRankingController();

    public abstract FindZowisInteractor provideFindZowisInteractor();

    public abstract ConnectToZowiInteractor provideConnectToZowiInteractor();

    public abstract SendCommandToZowiInteractor provideSendCommandToZowiInteractor();

    public abstract SendAppToZowiInteractor provideSendAppToZowiInteractor();

    public abstract CheckInstalledZowiAppInteractor provideCheckInstalledZowiAppInteractor();

    public abstract CheckAchievementAndUnlockItInteractor provideCheckAchievementAndUnlockItInteractor();

    public abstract ChangeZowiNameInteractor provideChangeZowiNameInteractor();

    public abstract MeasureZowiBatteryLevelInteractor provideMeasureZowiBatteryLevelInteractor();

    public abstract ForgetZowiInteractor provideForgetZowiInteractor();

    public abstract ForgetPlayingHistoryInteractor provideForgetPlayingHistoryInteractor();

    public SplashPresenter provideSplashPresenter() {
        return new SplashPresenterImpl(provideSessionController());
    }

    public WelcomePresenter provideWelcomePresenter() {
        return new WelcomePresenterImpl();
    }

    public WizardPresenter provideWizardPresenter() {
        return new WizardPresenterImpl(provideFindZowisInteractor(), provideConnectToZowiInteractor(), provideChangeZowiNameInteractor(), provideSessionController(), provideUiScheduler());
    }

    public HomePresenter provideHomePresenter() {
        return new HomePresenterImpl(provideSessionController(), provideBTConnectionController(), provideConnectToZowiInteractor(), provideMeasureZowiBatteryLevelInteractor(), provideCheckInstalledZowiAppInteractor(), provideSendAppToZowiInteractor(), provideFactoryFirmwarePath(), provideAppController(), provideCheckAchievementAndUnlockItInteractor(), provideAchievementsController(), provideUiScheduler());
    }

    public SettingsPresenter provideSettingsPresenter() {
        return new SettingsPresenterImpl(provideFactoryFirmwarePath(), provideSendAppToZowiInteractor(), provideChangeZowiNameInteractor(), provideForgetZowiInteractor(), provideForgetPlayingHistoryInteractor(), provideSessionController(), provideBTConnectionController(), provideConnectToZowiInteractor(), provideMeasureZowiBatteryLevelInteractor(), provideCheckInstalledZowiAppInteractor(), provideUiScheduler());
    }

    public CalibrationPresenter provideCalibrationPresenter() {
        return new CalibrationPresenterImpl(provideSessionController(), provideBTConnectionController(), provideConnectToZowiInteractor(), provideMeasureZowiBatteryLevelInteractor(), provideCheckInstalledZowiAppInteractor(), provideSendAppToZowiInteractor(), provideFactoryFirmwarePath(), provideUiScheduler(), provideSendCommandToZowiInteractor());
    }

    public AchievementsPresenter provideAchievementsPresenter() {
        return new AchievementsPresenterImpl(provideSessionController(), provideBTConnectionController(), provideConnectToZowiInteractor(), provideMeasureZowiBatteryLevelInteractor(), provideCheckInstalledZowiAppInteractor(), provideSendAppToZowiInteractor(), provideSendCommandToZowiInteractor(), provideFactoryFirmwarePath(), provideUiScheduler(), provideAchievementsController());
    }

    public PadPresenter providePadPresenter() {
        return new PadPresenterImpl(provideSessionController(), provideGameController(), provideCheckAchievementAndUnlockItInteractor(), provideBTConnectionController(), provideConnectToZowiInteractor(), provideSendCommandToZowiInteractor(), provideMeasureZowiBatteryLevelInteractor(), provideCheckInstalledZowiAppInteractor(), provideSendAppToZowiInteractor(), provideAchievementsController(), provideFactoryFirmwarePath(), provideUiScheduler());
    }

    public TimelinePresenter provideTimelinePresenter() {
        return new TimelinePresenterImpl(provideSessionController(), provideBTConnectionController(), provideConnectToZowiInteractor(), provideTimelineGameController(), provideSendCommandToZowiInteractor(), provideMeasureZowiBatteryLevelInteractor(), provideCheckInstalledZowiAppInteractor(), provideSendAppToZowiInteractor(), provideFactoryFirmwarePath(), provideCheckAchievementAndUnlockItInteractor(), provideAchievementsController(), provideUiScheduler());
    }

    public ZowiSaysMinigamePresenter provideZowiSaysMinigamePresenter() {
        return new ZowiSaysMinigamePresenterImpl(provideSessionController(), provideBTConnectionController(), provideConnectToZowiInteractor(), provideSendCommandToZowiInteractor(), provideMeasureZowiBatteryLevelInteractor(), provideRankingController(), provideGameController(), provideCheckAchievementAndUnlockItInteractor(), provideCheckInstalledZowiAppInteractor(), provideSendAppToZowiInteractor(), provideFactoryFirmwarePath(), provideUiScheduler());
    }

    public MouthsMinigamePresenter provideMouthsMinigamePresenter() {
        return new MouthsMinigamePresenterImpl(provideSessionController(), provideBTConnectionController(), provideConnectToZowiInteractor(), provideSendCommandToZowiInteractor(), provideMeasureZowiBatteryLevelInteractor(), provideRankingController(), provideGameController(), provideCheckAchievementAndUnlockItInteractor(), provideCheckInstalledZowiAppInteractor(), provideSendAppToZowiInteractor(), provideFactoryFirmwarePath(), provideUiScheduler());
    }

    public MouthsEditorPresenter provideMouthsEditorPresenter() {
        return new MouthsEditorPresenterImpl(provideSessionController(), provideBTConnectionController(), provideConnectToZowiInteractor(), provideMeasureZowiBatteryLevelInteractor(), provideCheckInstalledZowiAppInteractor(), provideSendAppToZowiInteractor(), provideSendCommandToZowiInteractor(), provideFactoryFirmwarePath(), provideUiScheduler());
    }

    public ZowiRunnerMinigamePresenter provideZowiRunnerMinigamePresenter() {
        return new ZowiRunnerMinigamePresenterImpl(provideSessionController(), provideBTConnectionController(), provideConnectToZowiInteractor(), provideSendCommandToZowiInteractor(), provideMeasureZowiBatteryLevelInteractor(), provideCheckInstalledZowiAppInteractor(), provideSendAppToZowiInteractor(), provideFactoryFirmwarePath(), provideUiScheduler());
    }

    public ProjectPresenter provideProjectPresenter() {
        return new ProjectPresenterImpl(provideSessionController(), provideBTConnectionController(), provideConnectToZowiInteractor(), provideProjectController(), provideMeasureZowiBatteryLevelInteractor(), provideCheckInstalledZowiAppInteractor(), provideSendAppToZowiInteractor(), provideFactoryFirmwarePath(), provideUiScheduler());
    }

    public ProjectQuizPresenter provideProjectQuizPresenter() {
        return new ProjectQuizPresenterImpl(provideSessionController(), provideBTConnectionController(), provideConnectToZowiInteractor(), provideProjectController(), provideMeasureZowiBatteryLevelInteractor(), provideCheckInstalledZowiAppInteractor(), provideSendAppToZowiInteractor(), provideFactoryFirmwarePath(), provideCheckAchievementAndUnlockItInteractor(), provideUiScheduler());
    }
}
