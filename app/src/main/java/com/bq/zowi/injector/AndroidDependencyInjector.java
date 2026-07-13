package com.bq.zowi.injector;

import android.bluetooth.BluetoothAdapter;
import android.content.SharedPreferences;
import androidx.fragment.app.FragmentActivity;
import com.bq.zowi.R;
import com.bq.zowi.ZowiApplication;
import com.bq.zowi.controllers.AchievementsController;
import com.bq.zowi.controllers.AchievementsControllerImpl;
import com.bq.zowi.controllers.AppController;
import com.bq.zowi.controllers.AppControllerImpl;
import com.bq.zowi.controllers.AssetController;
import com.bq.zowi.controllers.AssetControllerImpl;
import com.bq.zowi.controllers.BTAdapterController;
import com.bq.zowi.controllers.BTAdapterControllerImpl;
import com.bq.zowi.controllers.BTConnectionController;
import com.bq.zowi.controllers.BTConnectionControllerImpl;
import com.bq.zowi.controllers.GameController;
import com.bq.zowi.controllers.GameControllerImpl;
import com.bq.zowi.controllers.KitonNetworkController;
import com.bq.zowi.controllers.KitonNetworkControllerImpl;
import com.bq.zowi.controllers.KitonNetworkService;
import com.bq.zowi.controllers.ProjectController;
import com.bq.zowi.controllers.ProjectControllerImpl;
import com.bq.zowi.controllers.RankingController;
import com.bq.zowi.controllers.RankingControllerImpl;
import com.bq.zowi.controllers.SessionController;
import com.bq.zowi.controllers.SessionControllerImpl;
import com.bq.zowi.controllers.TimelineGameControllerImpl;
import com.bq.zowi.adapters.CoreAdapterProvider;
import com.bq.zowi.crashreporting.BugsnagCustomErrorReporter;
import com.bq.zowi.crashreporting.CustomErrorReporter;
import com.bq.zowi.injector.DependencyCache;
import com.bq.zowi.interactors.CheckAchievementAndUnlockItInteractor;
import com.bq.zowi.interactors.CheckAchievementAndUnlockItInteractorImpl;
import com.bq.zowi.interactors.CheckInstalledZowiAppInteractor;
import com.bq.zowi.interactors.CheckInstalledZowiAppInteractorImpl;
import com.bq.zowi.interactors.ConnectToZowiInteractor;
import com.bq.zowi.interactors.ConnectToZowiInteractorImpl;
import com.bq.zowi.interactors.FindZowisInteractor;
import com.bq.zowi.interactors.FindZowisInteractorImpl;
import com.bq.zowi.interactors.ForgetPlayingHistoryInteractor;
import com.bq.zowi.interactors.ForgetPlayingHistoryInteractorImpl;
import com.bq.zowi.interactors.ForgetZowiInteractor;
import com.bq.zowi.interactors.ForgetZowiInteractorImpl;
import com.bq.zowi.interactors.SendAppToZowiInteractor;
import com.bq.zowi.interactors.SendAppToZowiInteractorImpl;
import com.bq.zowi.interactors.SendCommandToZowiInteractor;
import com.bq.zowi.interactors.SendCommandToZowiInteractorImpl;
import com.bq.zowi.wireframes.achievements.AchievementsWireframe;
import com.bq.zowi.wireframes.achievements.AchievementsWireframeImpl;
import com.bq.zowi.wireframes.home.HomeWireframe;
import com.bq.zowi.wireframes.home.HomeWireframeImpl;
import com.bq.zowi.wireframes.pad.PadWireframe;
import com.bq.zowi.wireframes.pad.PadWireframeImpl;
import com.bq.zowi.wireframes.projects.ProjectQuizWireframe;
import com.bq.zowi.wireframes.projects.ProjectQuizWireframeImpl;
import com.bq.zowi.wireframes.projects.ProjectWireframe;
import com.bq.zowi.wireframes.projects.ProjectWireframeImpl;
import com.bq.zowi.wireframes.settings.CalibrationWireframe;
import com.bq.zowi.wireframes.settings.CalibrationWireframeImpl;
import com.bq.zowi.wireframes.settings.SettingsWireframe;
import com.bq.zowi.wireframes.settings.SettingsWireframeImpl;
import com.bq.zowi.wireframes.splash.SplashWireframe;
import com.bq.zowi.wireframes.splash.SplashWireframeImpl;
import com.bq.zowi.wireframes.timeline.TimelineWireframe;
import com.bq.zowi.wireframes.timeline.TimelineWireframeImpl;
import com.bq.zowi.wireframes.welcome.WelcomeWireframe;
import com.bq.zowi.wireframes.welcome.WelcomeWireframeImpl;
import com.bq.zowi.wireframes.wizard.WizardWireframe;
import com.bq.zowi.wireframes.wizard.WizardWireframeImpl;
import com.bq.zowi.wireframes.zowiapps.MouthsEditorWireframeImpl;
import com.bq.zowi.wireframes.zowiapps.minigames.MinigameBaseWireframe;
import com.bq.zowi.wireframes.zowiapps.minigames.MinigameBaseWireframeImpl;
import org.jetbrains.annotations.NotNull;
import retrofit.RestAdapter;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/* JADX INFO: loaded from: classes.dex */
public class AndroidDependencyInjector extends DependencyInjector {
    private static final String ZOWI_SHARED_PREFS_NAME = "zowiSharedPrefsName";
    protected static AndroidDependencyInjector instance;
    private final String BUILD_CONFIG_DEBUG = "DEBUG";
    private final String BUILD_CONFIG_RELEASE = "RELEASE";
    private ZowiApplication application;
    private CoreAdapterProvider coreProvider;

    protected AndroidDependencyInjector() {
    }

    public AndroidDependencyInjector setApplication(ZowiApplication application) {
        this.application = application;
        return this;
    }

    public ZowiApplication provideApplication() {
        return this.application;
    }

    public static AndroidDependencyInjector getInstance() {
        if (instance == null) {
            instance = new AndroidDependencyInjector();
        }
        return instance;
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public void init() {
        CustomErrorReporter reporter = provideErrorReporter();
        reporter.init();
        reporter.setReleaseStage("RELEASE");

        coreProvider = new CoreAdapterProvider(
            application.getApplicationContext(),
            provideSharedPreferences(),
            application.getAssets(),
            BluetoothAdapter.getDefaultAdapter(),
            application.getString(R.string.zowi_default_name)
        );
    }

    public CoreAdapterProvider provideCoreProvider() {
        return coreProvider;
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public void shutdown() {
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public Scheduler provideUiScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public FindZowisInteractor provideFindZowisInteractor() {
        return (FindZowisInteractor) getCache().get(FindZowisInteractorImpl.class, new DependencyCache.Provider<FindZowisInteractorImpl>() { // from class: com.bq.zowi.injector.AndroidDependencyInjector.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bq.zowi.injector.DependencyCache.Provider
            @NotNull
            public FindZowisInteractorImpl get() {
                return new FindZowisInteractorImpl(AndroidDependencyInjector.this.provideBTAdapterController());
            }
        });
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public ConnectToZowiInteractor provideConnectToZowiInteractor() {
        return (ConnectToZowiInteractor) getCache().get(ConnectToZowiInteractorImpl.class, new DependencyCache.Provider<ConnectToZowiInteractorImpl>() { // from class: com.bq.zowi.injector.AndroidDependencyInjector.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bq.zowi.injector.DependencyCache.Provider
            @NotNull
            public ConnectToZowiInteractorImpl get() {
                return new ConnectToZowiInteractorImpl(AndroidDependencyInjector.this.provideBTConnectionController(), AndroidDependencyInjector.this.provideBTAdapterController(), AndroidDependencyInjector.this.provideZowiDataController(), AndroidDependencyInjector.this.provideKitonNetworkController());
            }
        });
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public SendAppToZowiInteractor provideSendAppToZowiInteractor() {
        return (SendAppToZowiInteractor) getCache().get(SendAppToZowiInteractorImpl.class, new DependencyCache.Provider<SendAppToZowiInteractorImpl>() { // from class: com.bq.zowi.injector.AndroidDependencyInjector.3
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bq.zowi.injector.DependencyCache.Provider
            @NotNull
            public SendAppToZowiInteractorImpl get() {
                return new SendAppToZowiInteractorImpl(AndroidDependencyInjector.this.provideConnectToZowiInteractor(), AndroidDependencyInjector.this.provideBTConnectionController(), AndroidDependencyInjector.this.provideAssetController(), AndroidDependencyInjector.this.provideSessionController(), AndroidDependencyInjector.this.provideZowiDataController(), AndroidDependencyInjector.this.provideKitonNetworkController());
            }
        });
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public SendCommandToZowiInteractor provideSendCommandToZowiInteractor() {
        return (SendCommandToZowiInteractor) getCache().get(SendCommandToZowiInteractorImpl.class, new DependencyCache.Provider<SendCommandToZowiInteractorImpl>() { // from class: com.bq.zowi.injector.AndroidDependencyInjector.4
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bq.zowi.injector.DependencyCache.Provider
            @NotNull
            public SendCommandToZowiInteractorImpl get() {
                return new SendCommandToZowiInteractorImpl(AndroidDependencyInjector.this.provideBTConnectionController());
            }
        });
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public CheckAchievementAndUnlockItInteractor checkAchievementAndUnlockItInteractor() {
        return (CheckAchievementAndUnlockItInteractor) getCache().get(CheckAchievementAndUnlockItInteractorImpl.class, new DependencyCache.Provider<CheckAchievementAndUnlockItInteractorImpl>() { // from class: com.bq.zowi.injector.AndroidDependencyInjector.5
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bq.zowi.injector.DependencyCache.Provider
            @NotNull
            public CheckAchievementAndUnlockItInteractorImpl get() {
                return new CheckAchievementAndUnlockItInteractorImpl(AndroidDependencyInjector.this.provideAchievementsController(), AndroidDependencyInjector.this.provideSendCommandToZowiInteractor());
            }
        });
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public CheckInstalledZowiAppInteractor provideCheckInstalledZowiAppInteractor() {
        return (CheckInstalledZowiAppInteractor) getCache().get(CheckInstalledZowiAppInteractorImpl.class, new DependencyCache.Provider<CheckInstalledZowiAppInteractorImpl>() { // from class: com.bq.zowi.injector.AndroidDependencyInjector.6
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bq.zowi.injector.DependencyCache.Provider
            @NotNull
            public CheckInstalledZowiAppInteractorImpl get() {
                return new CheckInstalledZowiAppInteractorImpl(AndroidDependencyInjector.this.provideFactoryFirmwareVersion(), AndroidDependencyInjector.this.provideZowiDataController());
            }
        });
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public ForgetZowiInteractor provideForgetZowiInteractor() {
        return (ForgetZowiInteractor) getCache().get(ForgetZowiInteractorImpl.class, new DependencyCache.Provider<ForgetZowiInteractorImpl>() { // from class: com.bq.zowi.injector.AndroidDependencyInjector.7
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bq.zowi.injector.DependencyCache.Provider
            @NotNull
            public ForgetZowiInteractorImpl get() {
                return new ForgetZowiInteractorImpl(AndroidDependencyInjector.this.provideChangeZowiNameInteractor(), AndroidDependencyInjector.this.provideSessionController(), AndroidDependencyInjector.this.provideBTConnectionController());
            }
        });
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public ForgetPlayingHistoryInteractor provideForgetPlayingHistoryInteractor() {
        return (ForgetPlayingHistoryInteractor) getCache().get(ForgetPlayingHistoryInteractorImpl.class, new DependencyCache.Provider<ForgetPlayingHistoryInteractorImpl>() { // from class: com.bq.zowi.injector.AndroidDependencyInjector.8
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bq.zowi.injector.DependencyCache.Provider
            @NotNull
            public ForgetPlayingHistoryInteractorImpl get() {
                return new ForgetPlayingHistoryInteractorImpl(AndroidDependencyInjector.this.provideProjectController(), AndroidDependencyInjector.this.provideAppController(), AndroidDependencyInjector.this.provideGameController(), AndroidDependencyInjector.this.provideRankingController(), AndroidDependencyInjector.this.provideAchievementsController());
            }
        });
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public SessionController provideSessionController() {
        return (SessionController) getCache().get(SessionControllerImpl.class, new DependencyCache.Provider<SessionControllerImpl>() { // from class: com.bq.zowi.injector.AndroidDependencyInjector.9
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bq.zowi.injector.DependencyCache.Provider
            @NotNull
            public SessionControllerImpl get() {
                return new SessionControllerImpl(AndroidDependencyInjector.this.application.getString(R.string.zowi_default_name), AndroidDependencyInjector.this.provideSharedPreferences());
            }
        });
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public ProjectController provideProjectController() {
        return (ProjectController) getCache().get(ProjectControllerImpl.class, new DependencyCache.Provider<ProjectControllerImpl>() { // from class: com.bq.zowi.injector.AndroidDependencyInjector.10
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bq.zowi.injector.DependencyCache.Provider
            @NotNull
            public ProjectControllerImpl get() {
                return new ProjectControllerImpl(AndroidDependencyInjector.this.application.getAssets(), AndroidDependencyInjector.this.provideSharedPreferences());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public SharedPreferences provideSharedPreferences() {
        return this.application.getSharedPreferences(ZOWI_SHARED_PREFS_NAME, 0);
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public AssetController provideAssetController() {
        return (AssetController) getCache().get(AssetControllerImpl.class, new DependencyCache.Provider<AssetControllerImpl>() { // from class: com.bq.zowi.injector.AndroidDependencyInjector.11
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bq.zowi.injector.DependencyCache.Provider
            @NotNull
            public AssetControllerImpl get() {
                return new AssetControllerImpl(AndroidDependencyInjector.this.application.getAssets());
            }
        });
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public AchievementsController provideAchievementsController() {
        return (AchievementsController) getCache().get(AchievementsControllerImpl.class, new DependencyCache.Provider<AchievementsControllerImpl>() { // from class: com.bq.zowi.injector.AndroidDependencyInjector.12
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bq.zowi.injector.DependencyCache.Provider
            @NotNull
            public AchievementsControllerImpl get() {
                return new AchievementsControllerImpl(AndroidDependencyInjector.this.provideSharedPreferences(), AndroidDependencyInjector.this.application.getAssets());
            }
        });
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public String provideFactoryFirmwarePath() {
        return this.application.getString(R.string.factory_firmware_asset_path);
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public int provideFactoryFirmwareVersion() {
        return com.bq.zowi.utils.ResourceResolver.getIntegerByResourceId("factory_firmware_version", this.application);
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public BTConnectionController provideBTConnectionController() {
        return (BTConnectionController) getCache().get(BTConnectionControllerImpl.class, new DependencyCache.Provider<BTConnectionControllerImpl>() { // from class: com.bq.zowi.injector.AndroidDependencyInjector.13
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bq.zowi.injector.DependencyCache.Provider
            @NotNull
            public BTConnectionControllerImpl get() {
                return new BTConnectionControllerImpl(AndroidDependencyInjector.this.application.getApplicationContext(), BluetoothAdapter.getDefaultAdapter(), AndroidDependencyInjector.this.provideSessionController(), AndroidDependencyInjector.this.provideUiScheduler());
            }
        });
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public BTAdapterController provideBTAdapterController() {
        return (BTAdapterController) getCache().get(BTAdapterControllerImpl.class, new DependencyCache.Provider<BTAdapterControllerImpl>() { // from class: com.bq.zowi.injector.AndroidDependencyInjector.14
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bq.zowi.injector.DependencyCache.Provider
            @NotNull
            public BTAdapterControllerImpl get() {
                return new BTAdapterControllerImpl(AndroidDependencyInjector.this.application.getApplicationContext(), BluetoothAdapter.getDefaultAdapter());
            }
        });
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    protected AppController provideAppController() {
        return (AppController) getCache().get(AppControllerImpl.class, new DependencyCache.Provider<AppControllerImpl>() { // from class: com.bq.zowi.injector.AndroidDependencyInjector.15
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bq.zowi.injector.DependencyCache.Provider
            @NotNull
            public AppControllerImpl get() {
                return new AppControllerImpl(AndroidDependencyInjector.this.provideSharedPreferences());
            }
        });
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public RankingController provideRankingController() {
        return (RankingController) getCache().get(RankingControllerImpl.class, new DependencyCache.Provider<RankingControllerImpl>() { // from class: com.bq.zowi.injector.AndroidDependencyInjector.16
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bq.zowi.injector.DependencyCache.Provider
            @NotNull
            public RankingControllerImpl get() {
                return new RankingControllerImpl(AndroidDependencyInjector.this.provideSharedPreferences());
            }
        });
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public GameController provideGameController() {
        return (GameController) getCache().get(GameControllerImpl.class, new DependencyCache.Provider<GameControllerImpl>() { // from class: com.bq.zowi.injector.AndroidDependencyInjector.17
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bq.zowi.injector.DependencyCache.Provider
            @NotNull
            public GameControllerImpl get() {
                return new GameControllerImpl(AndroidDependencyInjector.this.provideSharedPreferences());
            }
        });
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public GameController provideTimelineGameController() {
        return (GameController) getCache().get(TimelineGameControllerImpl.class, new DependencyCache.Provider<TimelineGameControllerImpl>() { // from class: com.bq.zowi.injector.AndroidDependencyInjector.18
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bq.zowi.injector.DependencyCache.Provider
            @NotNull
            public TimelineGameControllerImpl get() {
                return new TimelineGameControllerImpl(AndroidDependencyInjector.this.provideSharedPreferences());
            }
        });
    }

    public ProjectWireframe provideProjectWireframe(FragmentActivity activity) {
        return new ProjectWireframeImpl(activity);
    }

    public ProjectQuizWireframe provideProjectQuizWireframe(FragmentActivity activity) {
        return new ProjectQuizWireframeImpl(activity);
    }

    public SplashWireframe provideSplashWireframe(FragmentActivity activity) {
        return new SplashWireframeImpl(activity);
    }

    public WelcomeWireframe provideWelcomeWireframe(FragmentActivity activity) {
        return new WelcomeWireframeImpl(activity);
    }

    public WizardWireframe provideWizardWireframe(FragmentActivity activity) {
        return new WizardWireframeImpl(activity);
    }

    public HomeWireframe provideHomeWireframe(FragmentActivity activity) {
        return new HomeWireframeImpl(activity);
    }

    public PadWireframe providePadWireframe(FragmentActivity activity) {
        return new PadWireframeImpl(activity);
    }

    public TimelineWireframe provideTimelineWireframe(FragmentActivity activity) {
        return new TimelineWireframeImpl(activity);
    }

    public MinigameBaseWireframe provideMinigameWireframe(FragmentActivity activity) {
        return new MinigameBaseWireframeImpl(activity);
    }

    public MouthsEditorWireframeImpl provideMouthsEditorWireframe(FragmentActivity activity) {
        return new MouthsEditorWireframeImpl(activity);
    }

    public AchievementsWireframe provideAchievementsWireframe(FragmentActivity activity) {
        return new AchievementsWireframeImpl(activity);
    }

    public SettingsWireframe provideSettingsWireframe(FragmentActivity activity) {
        return new SettingsWireframeImpl(activity);
    }

    public CalibrationWireframe provideCalibrationWireframe(FragmentActivity activity) {
        return new CalibrationWireframeImpl(activity);
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public CustomErrorReporter provideErrorReporter() {
        return (CustomErrorReporter) getCache().get(BugsnagCustomErrorReporter.class, new DependencyCache.Provider<BugsnagCustomErrorReporter>() { // from class: com.bq.zowi.injector.AndroidDependencyInjector.19
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bq.zowi.injector.DependencyCache.Provider
            @NotNull
            public BugsnagCustomErrorReporter get() {
                return new BugsnagCustomErrorReporter(AndroidDependencyInjector.this.provideApplication(), AndroidDependencyInjector.this.provideApplication().getString(R.string.bugsnag_api_key), null);
            }
        });
    }

    @Override // com.bq.zowi.injector.DependencyInjector
    public KitonNetworkController provideKitonNetworkController() {
        return (KitonNetworkController) getCache().get(KitonNetworkControllerImpl.class, new DependencyCache.Provider<KitonNetworkControllerImpl>() { // from class: com.bq.zowi.injector.AndroidDependencyInjector.21
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.bq.zowi.injector.DependencyCache.Provider
            @NotNull
            public KitonNetworkControllerImpl get() {
                String kitonEndPoint = AndroidDependencyInjector.this.application.getString(R.string.kiton_production_endpoint);
                RestAdapter kitonRestAdapter = new RestAdapter.Builder().setEndpoint(kitonEndPoint).setLogLevel(RestAdapter.LogLevel.NONE).build();
                KitonNetworkService kitonNetworkService = (KitonNetworkService) kitonRestAdapter.create(KitonNetworkService.class);
                return new KitonNetworkControllerImpl(kitonNetworkService, AndroidDependencyInjector.this.provideSharedPreferences());
            }
        });
    }
}
