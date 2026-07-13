package com.bq.zowi.presenters.interactive.home;

import com.bq.zowi.api.AchievementsController;
import com.bq.zowi.api.AppController;
import com.bq.zowi.api.BTConnectionController;
import com.bq.zowi.api.SessionController;
import com.bq.zowi.models.Achievement;
import com.bq.zowi.models.viewmodels.AchievementViewModel;
import com.bq.zowi.presenters.interactive.InteractiveBasePresenterImpl;
import com.bq.zowi.usecases.CheckAchievementAndUnlockItInteractor;
import com.bq.zowi.usecases.CheckInstalledZowiAppInteractor;
import com.bq.zowi.usecases.ConnectToZowiInteractor;
import com.bq.zowi.usecases.MeasureZowiBatteryLevelInteractor;
import com.bq.zowi.usecases.SendAppToZowiInteractor;
import com.bq.zowi.utils.Grove;
import com.bq.zowi.views.interactive.home.HomeView;
import com.bq.zowi.wireframes.home.HomeWireframe;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;

public class HomePresenterImpl extends InteractiveBasePresenterImpl<HomeView, HomeWireframe> implements HomePresenter {
    private static final int APP_ACHIEVEMENT_MIN_DAYS_OF_USE = 4;
    private static final Achievement.Id APP_DAYS_OF_USE_ACHIEVEMENT = Achievement.Id.fart;
    private static final Achievement.Id APP_SECOND_USAGE_ACHIEVEMENT = Achievement.Id.crusaito;
    private final AchievementsController achievementsController;
    private final CheckAchievementAndUnlockItInteractor achievementsInteractor;
    private final AppController appController;

    public HomePresenterImpl(SessionController sessionController, BTConnectionController connectionController, ConnectToZowiInteractor connectToZowiInteractor, MeasureZowiBatteryLevelInteractor measureZowiBatteryLevelInteractor, CheckInstalledZowiAppInteractor checkInstalledZowiAppInteractor, SendAppToZowiInteractor sendAppToZowiInteractor, String factoryFirmwarePath, AppController appController, CheckAchievementAndUnlockItInteractor achievementsInteractor, AchievementsController achievementsController, Scheduler uiScheduler) {
        super(sessionController, connectionController, connectToZowiInteractor, measureZowiBatteryLevelInteractor, checkInstalledZowiAppInteractor, sendAppToZowiInteractor, factoryFirmwarePath, uiScheduler);
        this.appController = appController;
        this.achievementsInteractor = achievementsInteractor;
        this.achievementsController = achievementsController;
    }

    @Override
    public void manageConnection() {
        super.manageConnection();
        this.disposables.add(this.achievementsController.getAchievementsList().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            achievementsList -> {
                for (Achievement achievement : achievementsList) {
                    if (achievement.id.equals(Achievement.Id.mouths_editor.toString())) {
                        ((HomeView) HomePresenterImpl.this.getView()).setUnlockStatusMouthsEditor(achievement.unlocked);
                    }
                }
            },
            error -> Grove.e(error, "CONFIGURE ENABLED ACTIONS ERROR!!!", new Object[0])
        ));
    }

    @Override
    public void loadProject(String projectId) {
        ((HomeWireframe) getWireframe()).presentProject(projectId);
    }

    @Override
    public void loadTimeline() {
        ((HomeWireframe) getWireframe()).presentTimelineView();
    }

    @Override
    public void loadGamepad() {
        ((HomeWireframe) getWireframe()).presentPadView();
    }

    @Override
    public void loadZowiSaysMinigame() {
        ((HomeWireframe) getWireframe()).presentZowiSaysMinigameView();
    }

    @Override
    public void loadMouthsMinigame() {
        ((HomeWireframe) getWireframe()).presentMouthsMinigameView();
    }

    @Override
    public void loadMouthsEditor() {
        ((HomeWireframe) getWireframe()).presentMouthsEditorView();
    }

    @Override
    public void loadZowiRunnerMinigame() {
        ((HomeWireframe) getWireframe()).presentZowiRunnerMinigameView();
    }

    @Override
    public void loadSettings() {
        ((HomeWireframe) getWireframe()).presentSettings();
    }

    @Override
    public void loadAchievements() {
        ((HomeWireframe) getWireframe()).presentAchievements();
    }

    @Override
    public void logAppStarted() {
        this.disposables.add(this.appController.isFirstUsage().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).flatMap(new Function<Boolean, Single<Achievement>>() {
            @Override
            public Single<Achievement> apply(Boolean isFirstUsage) {
                if (!isFirstUsage.booleanValue()) {
                    return HomePresenterImpl.this.achievementsInteractor.checkAchievementAndUnlockIt(HomePresenterImpl.APP_SECOND_USAGE_ACHIEVEMENT);
                }
                return Single.just(null);
            }
        }).map(new Function<Achievement, Void>() {
            @Override
            public Void apply(Achievement achievement) {
                if (achievement != null) {
                    ((HomeView) HomePresenterImpl.this.getView()).showAchievementUnlock(new AchievementViewModel(achievement.id, achievement.unlocked));
                }
                return null;
            }
        }).flatMap(new Function<Void, Single<Void>>() {
            @Override
            public Single<Void> apply(Void aVoid) {
                return HomePresenterImpl.this.appController.logAppStarted();
            }
        }).flatMap(new Function<Void, Single<Integer>>() {
            @Override
            public Single<Integer> apply(Void aVoid) {
                return HomePresenterImpl.this.appController.getDaysOfUse();
            }
        }).flatMap(new Function<Integer, Single<Achievement>>() {
            @Override
            public Single<Achievement> apply(Integer daysOfUse) {
                if (daysOfUse.intValue() >= 4) {
                    return HomePresenterImpl.this.achievementsInteractor.checkAchievementAndUnlockIt(HomePresenterImpl.APP_DAYS_OF_USE_ACHIEVEMENT);
                }
                return Single.just(null);
            }
        }).subscribe(
            achievement -> {
                if (achievement != null) {
                    ((HomeView) HomePresenterImpl.this.getView()).showAchievementUnlock(new AchievementViewModel(achievement.id, achievement.unlocked));
                }
            },
            error -> Grove.e(error, "Failed loading time-based achievement.", new Object[0])
        ));
    }
}
