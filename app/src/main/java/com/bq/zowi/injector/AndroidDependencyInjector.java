package com.bq.zowi.injector;

import android.bluetooth.BluetoothAdapter;
import android.content.SharedPreferences;
import androidx.fragment.app.FragmentActivity;
import com.bq.zowi.R;
import com.bq.zowi.ZowiApplication;
import com.bq.zowi.adapters.CoreAdapterProvider;
import com.bq.zowi.adapters.CoreInteractorProvider;
import com.bq.zowi.bridge.AppControllerBridge;
import com.bq.zowi.bridge.AchievementsControllerBridge;
import com.bq.zowi.bridge.AssetControllerBridge;
import com.bq.zowi.bridge.BTConnectionControllerBridge;
import com.bq.zowi.bridge.ChangeZowiNameInteractorBridge;
import com.bq.zowi.bridge.CheckAchievementAndUnlockItInteractorBridge;
import com.bq.zowi.bridge.CheckInstalledZowiAppInteractorBridge;
import com.bq.zowi.bridge.ConnectToZowiInteractorBridge;
import com.bq.zowi.bridge.FindZowisInteractorBridge;
import com.bq.zowi.bridge.ForgetPlayingHistoryInteractorBridge;
import com.bq.zowi.bridge.ForgetZowiInteractorBridge;
import com.bq.zowi.bridge.GameControllerBridge;
import com.bq.zowi.bridge.MeasureZowiBatteryLevelInteractorBridge;
import com.bq.zowi.bridge.ProjectControllerBridge;
import com.bq.zowi.bridge.RankingControllerBridge;
import com.bq.zowi.bridge.SendAppToZowiInteractorBridge;
import com.bq.zowi.bridge.SendCommandToZowiInteractorBridge;
import com.bq.zowi.bridge.SessionControllerBridge;
import com.bq.zowi.controllers.AchievementsController;
import com.bq.zowi.controllers.AppController;
import com.bq.zowi.controllers.AssetController;
import com.bq.zowi.controllers.BTAdapterController;
import com.bq.zowi.controllers.BTAdapterControllerImpl;
import com.bq.zowi.controllers.BTConnectionController;
import com.bq.zowi.controllers.GameController;
import com.bq.zowi.controllers.KitonNetworkController;
import com.bq.zowi.controllers.KitonNetworkControllerImpl;
import com.bq.zowi.controllers.KitonNetworkService;
import com.bq.zowi.controllers.ProjectController;
import com.bq.zowi.controllers.RankingController;
import com.bq.zowi.controllers.SessionController;
import com.bq.zowi.crashreporting.BugsnagCustomErrorReporter;
import com.bq.zowi.crashreporting.CustomErrorReporter;
import com.bq.zowi.interactors.CheckAchievementAndUnlockItInteractor;
import com.bq.zowi.interactors.CheckInstalledZowiAppInteractor;
import com.bq.zowi.interactors.ChangeZowiNameInteractor;
import com.bq.zowi.interactors.ConnectToZowiInteractor;
import com.bq.zowi.interactors.FindZowisInteractor;
import com.bq.zowi.interactors.ForgetPlayingHistoryInteractor;
import com.bq.zowi.interactors.ForgetZowiInteractor;
import com.bq.zowi.interactors.MeasureZowiBatteryLevelInteractor;
import com.bq.zowi.interactors.SendAppToZowiInteractor;
import com.bq.zowi.interactors.SendCommandToZowiInteractor;
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

public class AndroidDependencyInjector extends DependencyInjector {
    private static final String ZOWI_SHARED_PREFS_NAME = "zowiSharedPrefsName";
    protected static AndroidDependencyInjector instance;
    private final String BUILD_CONFIG_DEBUG = "DEBUG";
    private final String BUILD_CONFIG_RELEASE = "RELEASE";
    private ZowiApplication application;
    private CoreAdapterProvider coreProvider;
    private CoreInteractorProvider coreInteractorProvider;

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

    @Override
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

        coreInteractorProvider = new CoreInteractorProvider(coreProvider, provideFactoryFirmwareVersion());
    }

    public CoreAdapterProvider provideCoreProvider() {
        return coreProvider;
    }

    @Override
    public void shutdown() {
    }

    @Override
    public Scheduler provideUiScheduler() {
        return AndroidSchedulers.mainThread();
    }

    // ---- Interactor bridges ----

    @Override
    public FindZowisInteractor provideFindZowisInteractor() {
        return (FindZowisInteractor) getCache().get(FindZowisInteractorBridge.class, new DependencyCache.Provider<FindZowisInteractorBridge>() {
            @Override @NotNull
            public FindZowisInteractorBridge get() {
                return new FindZowisInteractorBridge(coreInteractorProvider.getFindZowisInteractor(), BluetoothAdapter.getDefaultAdapter());
            }
        });
    }

    @Override
    public ConnectToZowiInteractor provideConnectToZowiInteractor() {
        return (ConnectToZowiInteractor) getCache().get(ConnectToZowiInteractorBridge.class, new DependencyCache.Provider<ConnectToZowiInteractorBridge>() {
            @Override @NotNull
            public ConnectToZowiInteractorBridge get() {
                return new ConnectToZowiInteractorBridge(coreInteractorProvider.getConnectToZowiInteractor());
            }
        });
    }

    @Override
    public SendAppToZowiInteractor provideSendAppToZowiInteractor() {
        return (SendAppToZowiInteractor) getCache().get(SendAppToZowiInteractorBridge.class, new DependencyCache.Provider<SendAppToZowiInteractorBridge>() {
            @Override @NotNull
            public SendAppToZowiInteractorBridge get() {
                return new SendAppToZowiInteractorBridge(coreInteractorProvider.getSendAppToZowiInteractor());
            }
        });
    }

    @Override
    public SendCommandToZowiInteractor provideSendCommandToZowiInteractor() {
        return (SendCommandToZowiInteractor) getCache().get(SendCommandToZowiInteractorBridge.class, new DependencyCache.Provider<SendCommandToZowiInteractorBridge>() {
            @Override @NotNull
            public SendCommandToZowiInteractorBridge get() {
                return new SendCommandToZowiInteractorBridge(coreInteractorProvider.getSendCommandToZowiInteractor());
            }
        });
    }

    @Override
    public CheckAchievementAndUnlockItInteractor checkAchievementAndUnlockItInteractor() {
        return (CheckAchievementAndUnlockItInteractor) getCache().get(CheckAchievementAndUnlockItInteractorBridge.class, new DependencyCache.Provider<CheckAchievementAndUnlockItInteractorBridge>() {
            @Override @NotNull
            public CheckAchievementAndUnlockItInteractorBridge get() {
                return new CheckAchievementAndUnlockItInteractorBridge(coreInteractorProvider.getCheckAchievementAndUnlockItInteractor());
            }
        });
    }

    @Override
    public CheckAchievementAndUnlockItInteractor provideCheckAchievementAndUnlockItInteractor() {
        return checkAchievementAndUnlockItInteractor();
    }

    @Override
    public CheckInstalledZowiAppInteractor provideCheckInstalledZowiAppInteractor() {
        return (CheckInstalledZowiAppInteractor) getCache().get(CheckInstalledZowiAppInteractorBridge.class, new DependencyCache.Provider<CheckInstalledZowiAppInteractorBridge>() {
            @Override @NotNull
            public CheckInstalledZowiAppInteractorBridge get() {
                return new CheckInstalledZowiAppInteractorBridge(coreInteractorProvider.getCheckInstalledZowiAppInteractor());
            }
        });
    }

    @Override
    public ForgetZowiInteractor provideForgetZowiInteractor() {
        return (ForgetZowiInteractor) getCache().get(ForgetZowiInteractorBridge.class, new DependencyCache.Provider<ForgetZowiInteractorBridge>() {
            @Override @NotNull
            public ForgetZowiInteractorBridge get() {
                return new ForgetZowiInteractorBridge(coreInteractorProvider.getForgetZowiInteractor());
            }
        });
    }

    @Override
    public ForgetPlayingHistoryInteractor provideForgetPlayingHistoryInteractor() {
        return (ForgetPlayingHistoryInteractor) getCache().get(ForgetPlayingHistoryInteractorBridge.class, new DependencyCache.Provider<ForgetPlayingHistoryInteractorBridge>() {
            @Override @NotNull
            public ForgetPlayingHistoryInteractorBridge get() {
                return new ForgetPlayingHistoryInteractorBridge(coreInteractorProvider.getForgetPlayingHistoryInteractor());
            }
        });
    }

    @Override
    public ChangeZowiNameInteractor provideChangeZowiNameInteractor() {
        return (ChangeZowiNameInteractor) getCache().get(ChangeZowiNameInteractorBridge.class, new DependencyCache.Provider<ChangeZowiNameInteractorBridge>() {
            @Override @NotNull
            public ChangeZowiNameInteractorBridge get() {
                return new ChangeZowiNameInteractorBridge(coreInteractorProvider.getChangeZowiNameInteractor());
            }
        });
    }

    @Override
    public MeasureZowiBatteryLevelInteractor provideMeasureZowiBatteryLevelInteractor() {
        return (MeasureZowiBatteryLevelInteractor) getCache().get(MeasureZowiBatteryLevelInteractorBridge.class, new DependencyCache.Provider<MeasureZowiBatteryLevelInteractorBridge>() {
            @Override @NotNull
            public MeasureZowiBatteryLevelInteractorBridge get() {
                return new MeasureZowiBatteryLevelInteractorBridge(coreInteractorProvider.getMeasureZowiBatteryLevelInteractor());
            }
        });
    }

    // ---- Controller bridges ----

    @Override
    public SessionController provideSessionController() {
        return (SessionController) getCache().get(SessionControllerBridge.class, new DependencyCache.Provider<SessionControllerBridge>() {
            @Override @NotNull
            public SessionControllerBridge get() {
                return new SessionControllerBridge(coreProvider.getSessionController());
            }
        });
    }

    @Override
    public BTConnectionController provideBTConnectionController() {
        return (BTConnectionController) getCache().get(BTConnectionControllerBridge.class, new DependencyCache.Provider<BTConnectionControllerBridge>() {
            @Override @NotNull
            public BTConnectionControllerBridge get() {
                return new BTConnectionControllerBridge(coreProvider.getBtConnectionController());
            }
        });
    }

    @Override
    public BTAdapterController provideBTAdapterController() {
        return (BTAdapterController) getCache().get(BTAdapterControllerImpl.class, new DependencyCache.Provider<BTAdapterControllerImpl>() {
            @Override @NotNull
            public BTAdapterControllerImpl get() {
                return new BTAdapterControllerImpl(application.getApplicationContext(), BluetoothAdapter.getDefaultAdapter());
            }
        });
    }

    @Override
    public AchievementsController provideAchievementsController() {
        return (AchievementsController) getCache().get(AchievementsControllerBridge.class, new DependencyCache.Provider<AchievementsControllerBridge>() {
            @Override @NotNull
            public AchievementsControllerBridge get() {
                return new AchievementsControllerBridge(coreProvider.getAchievementsController());
            }
        });
    }

    @Override
    protected AppController provideAppController() {
        return (AppController) getCache().get(AppControllerBridge.class, new DependencyCache.Provider<AppControllerBridge>() {
            @Override @NotNull
            public AppControllerBridge get() {
                return new AppControllerBridge(coreProvider.getAppController());
            }
        });
    }

    @Override
    public GameController provideGameController() {
        return (GameController) getCache().get(GameControllerBridge.class, new DependencyCache.Provider<GameControllerBridge>() {
            @Override @NotNull
            public GameControllerBridge get() {
                return new GameControllerBridge(coreProvider.getGameController());
            }
        });
    }

    @Override
    public GameController provideTimelineGameController() {
        return provideGameController();
    }

    @Override
    public RankingController provideRankingController() {
        return (RankingController) getCache().get(RankingControllerBridge.class, new DependencyCache.Provider<RankingControllerBridge>() {
            @Override @NotNull
            public RankingControllerBridge get() {
                return new RankingControllerBridge(coreProvider.getRankingController());
            }
        });
    }

    @Override
    public ProjectController provideProjectController() {
        return (ProjectController) getCache().get(ProjectControllerBridge.class, new DependencyCache.Provider<ProjectControllerBridge>() {
            @Override @NotNull
            public ProjectControllerBridge get() {
                return new ProjectControllerBridge(coreProvider.getProjectController());
            }
        });
    }

    @Override
    public AssetController provideAssetController() {
        return (AssetController) getCache().get(AssetControllerBridge.class, new DependencyCache.Provider<AssetControllerBridge>() {
            @Override @NotNull
            public AssetControllerBridge get() {
                return new AssetControllerBridge(coreProvider.getAssetController());
            }
        });
    }

    // ---- Other providers ----

    public SharedPreferences provideSharedPreferences() {
        return this.application.getSharedPreferences(ZOWI_SHARED_PREFS_NAME, 0);
    }

    @Override
    public String provideFactoryFirmwarePath() {
        return this.application.getString(R.string.factory_firmware_asset_path);
    }

    @Override
    public int provideFactoryFirmwareVersion() {
        return com.bq.zowi.utils.ResourceResolver.getIntegerByResourceId("factory_firmware_version", this.application);
    }

    // ---- Wireframe providers ----

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

    @Override
    public CustomErrorReporter provideErrorReporter() {
        return (CustomErrorReporter) getCache().get(BugsnagCustomErrorReporter.class, new DependencyCache.Provider<BugsnagCustomErrorReporter>() {
            @Override @NotNull
            public BugsnagCustomErrorReporter get() {
                return new BugsnagCustomErrorReporter(provideApplication(), provideApplication().getString(R.string.bugsnag_api_key), null);
            }
        });
    }

    @Override
    public KitonNetworkController provideKitonNetworkController() {
        return (KitonNetworkController) getCache().get(KitonNetworkControllerImpl.class, new DependencyCache.Provider<KitonNetworkControllerImpl>() {
            @Override @NotNull
            public KitonNetworkControllerImpl get() {
                String kitonEndPoint = application.getString(R.string.kiton_production_endpoint);
                RestAdapter kitonRestAdapter = new RestAdapter.Builder().setEndpoint(kitonEndPoint).setLogLevel(RestAdapter.LogLevel.NONE).build();
                KitonNetworkService kitonNetworkService = (KitonNetworkService) kitonRestAdapter.create(KitonNetworkService.class);
                return new KitonNetworkControllerImpl(kitonNetworkService, provideSharedPreferences());
            }
        });
    }
}
