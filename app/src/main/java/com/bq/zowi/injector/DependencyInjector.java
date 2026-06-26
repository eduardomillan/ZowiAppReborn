package com.bq.zowi.injector;

import com.bq.analytics.core.AnalyticsController;
import com.bq.zowi.controllers.AchievementsController;
import com.bq.zowi.controllers.AppController;
import com.bq.zowi.controllers.AssetController;
import com.bq.zowi.controllers.BTAdapterController;
import com.bq.zowi.controllers.BTConnectionController;
import com.bq.zowi.controllers.GameController;
import com.bq.zowi.controllers.KitonNetworkController;
import com.bq.zowi.controllers.ProjectController;
import com.bq.zowi.controllers.RankingController;
import com.bq.zowi.controllers.SessionController;
import com.bq.zowi.controllers.ZowiDataController;
import com.bq.zowi.controllers.ZowiDataControllerImpl;
import com.bq.zowi.crashreporting.CustomErrorReporter;
import com.bq.zowi.injector.DependencyCache;
import com.bq.zowi.interactors.ChangeZowiNameInteractor;
import com.bq.zowi.interactors.ChangeZowiNameInteractorImpl;
import com.bq.zowi.interactors.CheckAchievementAndUnlockItInteractor;
import com.bq.zowi.interactors.CheckAchievementAndUnlockItInteractorImpl;
import com.bq.zowi.interactors.CheckInstalledZowiAppInteractor;
import com.bq.zowi.interactors.ConnectToZowiInteractor;
import com.bq.zowi.interactors.FindZowisInteractor;
import com.bq.zowi.interactors.ForgetPlayingHistoryInteractor;
import com.bq.zowi.interactors.ForgetZowiInteractor;
import com.bq.zowi.interactors.MeasureZowiBatteryLevelInteractor;
import com.bq.zowi.interactors.MeasureZowiBatteryLevelInteractorImpl;
import com.bq.zowi.interactors.SendAppToZowiInteractor;
import com.bq.zowi.interactors.SendCommandToZowiInteractor;
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
import org.jetbrains.annotations.NotNull;
import rx.Scheduler;

/* JADX INFO: loaded from: classes.dex */
public abstract class DependencyInjector {
    private final DependencyCache cache = new DependencyCache();

    public abstract CheckAchievementAndUnlockItInteractor checkAchievementAndUnlockItInteractor();

    public abstract void init();

    public abstract AchievementsController provideAchievementsController();

    public abstract AnalyticsController provideAnalyticsController();

    protected abstract AppController provideAppController();

    public abstract AssetController provideAssetController();

    public abstract BTAdapterController provideBTAdapterController();

    public abstract BTConnectionController provideBTConnectionController();

    public abstract CheckInstalledZowiAppInteractor provideCheckInstalledZowiAppInteractor();

    public abstract ConnectToZowiInteractor provideConnectToZowiInteractor();

    public abstract CustomErrorReporter provideErrorReporter();

    public abstract String provideFactoryFirmwarePath();

    public abstract int provideFactoryFirmwareVersion();

    public abstract FindZowisInteractor provideFindZowisInteractor();

    public abstract ForgetPlayingHistoryInteractor provideForgetPlayingHistoryInteractor();

    public abstract ForgetZowiInteractor provideForgetZowiInteractor();

    public abstract GameController provideGameController();

    public abstract KitonNetworkController provideKitonNetworkController();

    public abstract ProjectController provideProjectController();

    public abstract RankingController provideRankingController();

    public abstract SendAppToZowiInteractor provideSendAppToZowiInteractor();

    public abstract SendCommandToZowiInteractor provideSendCommandToZowiInteractor();

    public abstract SessionController provideSessionController();

    public abstract GameController provideTimelineGameController();

    public abstract Scheduler provideUiScheduler();

    public abstract void shutdown();

    protected DependencyCache getCache() {
        return this.cache;
    }

    public ZowiDataController provideZowiDataController() {
        return (ZowiDataController) getCache().get(ZowiDataControllerImpl.class, new DependencyCache.Provider<ZowiDataControllerImpl>() { // from class: com.bq.zowi.injector.DependencyInjector.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bq.zowi.injector.DependencyCache.Provider
            @NotNull
            public ZowiDataControllerImpl get() {
                return new ZowiDataControllerImpl(DependencyInjector.this.provideBTConnectionController(), DependencyInjector.this.provideSendCommandToZowiInteractor(), DependencyInjector.this.provideUiScheduler());
            }
        });
    }

    public MeasureZowiBatteryLevelInteractor provideMeasureZowiBatteryLevelInteractor() {
        return new MeasureZowiBatteryLevelInteractorImpl(provideSendCommandToZowiInteractor(), provideZowiDataController());
    }

    public SplashPresenter provideSplashPresenter() {
        return new SplashPresenterImpl(provideSessionController(), provideUiScheduler());
    }

    public WelcomePresenter provideWelcomePresenter() {
        return new WelcomePresenterImpl();
    }

    public WizardPresenter provideWizardPresenter() {
        return new WizardPresenterImpl(provideFindZowisInteractor(), provideConnectToZowiInteractor(), provideChangeZowiNameInteractor(), provideSessionController(), provideUiScheduler(), provideAnalyticsController());
    }

    public HomePresenter provideHomePresenter() {
        return new HomePresenterImpl(provideSessionController(), provideBTConnectionController(), provideConnectToZowiInteractor(), provideMeasureZowiBatteryLevelInteractor(), provideCheckInstalledZowiAppInteractor(), provideSendAppToZowiInteractor(), provideFactoryFirmwarePath(), provideAppController(), provideCheckAchievementAndUnlockItInteractor(), provideAchievementsController(), provideUiScheduler());
    }

    public SettingsPresenter provideSettingsPresenter() {
        return new SettingsPresenterImpl(provideFactoryFirmwarePath(), provideSendAppToZowiInteractor(), provideChangeZowiNameInteractor(), provideForgetZowiInteractor(), provideForgetPlayingHistoryInteractor(), provideSessionController(), provideBTConnectionController(), provideConnectToZowiInteractor(), provideMeasureZowiBatteryLevelInteractor(), provideCheckInstalledZowiAppInteractor(), provideUiScheduler());
    }

    public ChangeZowiNameInteractor provideChangeZowiNameInteractor() {
        return new ChangeZowiNameInteractorImpl(provideSendCommandToZowiInteractor());
    }

    public CalibrationPresenter provideCalibrationPresenter() {
        return new CalibrationPresenterImpl(provideSessionController(), provideBTConnectionController(), provideConnectToZowiInteractor(), provideMeasureZowiBatteryLevelInteractor(), provideCheckInstalledZowiAppInteractor(), provideSendAppToZowiInteractor(), provideFactoryFirmwarePath(), provideUiScheduler(), provideSendCommandToZowiInteractor());
    }

    public AchievementsPresenter provideAchievementsPresenter() {
        return new AchievementsPresenterImpl(provideSessionController(), provideBTConnectionController(), provideConnectToZowiInteractor(), provideMeasureZowiBatteryLevelInteractor(), provideCheckInstalledZowiAppInteractor(), provideSendAppToZowiInteractor(), provideSendCommandToZowiInteractor(), provideFactoryFirmwarePath(), provideUiScheduler(), provideAchievementsController());
    }

    public CheckAchievementAndUnlockItInteractor provideCheckAchievementAndUnlockItInteractor() {
        return new CheckAchievementAndUnlockItInteractorImpl(provideAchievementsController(), provideSendCommandToZowiInteractor());
    }

    public PadPresenter providePadPresenter() {
        return new PadPresenterImpl(provideSessionController(), provideGameController(), provideCheckAchievementAndUnlockItInteractor(), provideBTConnectionController(), provideConnectToZowiInteractor(), provideSendCommandToZowiInteractor(), provideMeasureZowiBatteryLevelInteractor(), provideCheckInstalledZowiAppInteractor(), provideSendAppToZowiInteractor(), provideAchievementsController(), provideFactoryFirmwarePath(), provideUiScheduler(), provideAnalyticsController());
    }

    public TimelinePresenter provideTimelinePresenter() {
        return new TimelinePresenterImpl(provideSessionController(), provideBTConnectionController(), provideConnectToZowiInteractor(), provideTimelineGameController(), provideSendCommandToZowiInteractor(), provideMeasureZowiBatteryLevelInteractor(), provideCheckInstalledZowiAppInteractor(), provideSendAppToZowiInteractor(), provideFactoryFirmwarePath(), provideCheckAchievementAndUnlockItInteractor(), provideAchievementsController(), provideUiScheduler(), provideAnalyticsController());
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
