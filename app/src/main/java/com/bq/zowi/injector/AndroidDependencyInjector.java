package com.bq.zowi.injector;

import android.bluetooth.BluetoothAdapter;
import android.content.SharedPreferences;
import androidx.fragment.app.FragmentActivity;
import com.bq.zowi.R;
import com.bq.zowi.ZowiApplication;
import com.bq.zowi.adapters.CoreAdapterProvider;
import com.bq.zowi.adapters.CoreInteractorProvider;
import com.bq.zowi.api.AchievementsController;
import com.bq.zowi.api.AppController;
import com.bq.zowi.api.BTConnectionController;
import com.bq.zowi.api.GameController;
import com.bq.zowi.api.ProjectController;
import com.bq.zowi.api.RankingController;
import com.bq.zowi.api.SessionController;
import com.bq.zowi.crashreporting.BugsnagCustomErrorReporter;
import com.bq.zowi.crashreporting.CustomErrorReporter;
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
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import org.jetbrains.annotations.NotNull;

public class AndroidDependencyInjector extends DependencyInjector {
    private static final String ZOWI_SHARED_PREFS_NAME = "zowiSharedPrefsName";
    protected static AndroidDependencyInjector instance;
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

    // ---- Core controllers ----

    @Override
    public SessionController provideSessionController() {
        return coreProvider.getSessionController();
    }

    @Override
    public BTConnectionController provideBTConnectionController() {
        return coreProvider.getBtConnectionController();
    }

    @Override
    public GameController provideGameController() {
        return coreProvider.getGameController();
    }

    @Override
    public GameController provideTimelineGameController() {
        return provideGameController();
    }

    @Override
    public AchievementsController provideAchievementsController() {
        return coreProvider.getAchievementsController();
    }

    @Override
    public AppController provideAppController() {
        return coreProvider.getAppController();
    }

    @Override
    public ProjectController provideProjectController() {
        return coreProvider.getProjectController();
    }

    @Override
    public RankingController provideRankingController() {
        return coreProvider.getRankingController();
    }

    // ---- Core interactors ----

    @Override
    public FindZowisInteractor provideFindZowisInteractor() {
        return coreInteractorProvider.getFindZowisInteractor();
    }

    @Override
    public ConnectToZowiInteractor provideConnectToZowiInteractor() {
        return coreInteractorProvider.getConnectToZowiInteractor();
    }

    @Override
    public SendCommandToZowiInteractor provideSendCommandToZowiInteractor() {
        return coreInteractorProvider.getSendCommandToZowiInteractor();
    }

    @Override
    public SendAppToZowiInteractor provideSendAppToZowiInteractor() {
        return coreInteractorProvider.getSendAppToZowiInteractor();
    }

    @Override
    public CheckInstalledZowiAppInteractor provideCheckInstalledZowiAppInteractor() {
        return coreInteractorProvider.getCheckInstalledZowiAppInteractor();
    }

    @Override
    public CheckAchievementAndUnlockItInteractor provideCheckAchievementAndUnlockItInteractor() {
        return coreInteractorProvider.getCheckAchievementAndUnlockItInteractor();
    }

    @Override
    public ChangeZowiNameInteractor provideChangeZowiNameInteractor() {
        return coreInteractorProvider.getChangeZowiNameInteractor();
    }

    @Override
    public MeasureZowiBatteryLevelInteractor provideMeasureZowiBatteryLevelInteractor() {
        return coreInteractorProvider.getMeasureZowiBatteryLevelInteractor();
    }

    @Override
    public ForgetZowiInteractor provideForgetZowiInteractor() {
        return coreInteractorProvider.getForgetZowiInteractor();
    }

    @Override
    public ForgetPlayingHistoryInteractor provideForgetPlayingHistoryInteractor() {
        return coreInteractorProvider.getForgetPlayingHistoryInteractor();
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
        return new BugsnagCustomErrorReporter(provideApplication(), provideApplication().getString(R.string.bugsnag_api_key), null);
    }
}
