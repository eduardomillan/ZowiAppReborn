package com.bq.zowi.presenters.interactive.home;

import com.bq.zowi.controllers.AchievementsController;
import com.bq.zowi.controllers.AppController;
import com.bq.zowi.controllers.BTConnectionController;
import com.bq.zowi.controllers.SessionController;
import com.bq.zowi.interactors.CheckAchievementAndUnlockItInteractor;
import com.bq.zowi.interactors.CheckInstalledZowiAppInteractor;
import com.bq.zowi.interactors.ConnectToZowiInteractor;
import com.bq.zowi.interactors.MeasureZowiBatteryLevelInteractor;
import com.bq.zowi.interactors.SendAppToZowiInteractor;
import com.bq.zowi.models.Achievement;
import com.bq.zowi.models.viewmodels.AchievementViewModel;
import com.bq.zowi.presenters.interactive.InteractiveBasePresenterImpl;
import com.bq.zowi.utils.Grove;
import com.bq.zowi.views.interactive.home.HomeView;
import com.bq.zowi.wireframes.home.HomeWireframe;
import java.util.ArrayList;
import rx.Scheduler;
import rx.Single;
import rx.SingleSubscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/* JADX INFO: loaded from: classes.dex */
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

    @Override // com.bq.zowi.presenters.interactive.InteractiveBasePresenterImpl, com.bq.zowi.presenters.interactive.InteractiveBasePresenter
    public void manageConnection() {
        super.manageConnection();
        this.achievementsController.getAchievementsList().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<ArrayList<Achievement>>() { // from class: com.bq.zowi.presenters.interactive.home.HomePresenterImpl.1
            @Override // rx.SingleSubscriber
            public void onSuccess(ArrayList<Achievement> achievementsList) {
                for (Achievement achievement : achievementsList) {
                    if (achievement.id.equals(Achievement.Id.mouths_editor.toString())) {
                        ((HomeView) HomePresenterImpl.this.getView()).setUnlockStatusMouthsEditor(achievement.unlocked);
                    }
                }
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
                Grove.e(error, "CONFIGURE ENABLED ACTIONS ERROR!!!", new Object[0]);
            }
        });
    }

    @Override // com.bq.zowi.presenters.interactive.home.HomePresenter
    public void loadProject(String projectId) {
        ((HomeWireframe) getWireframe()).presentProject(projectId);
    }

    @Override // com.bq.zowi.presenters.interactive.home.HomePresenter
    public void loadTimeline() {
        ((HomeWireframe) getWireframe()).presentTimelineView();
    }

    @Override // com.bq.zowi.presenters.interactive.home.HomePresenter
    public void loadGamepad() {
        ((HomeWireframe) getWireframe()).presentPadView();
    }

    @Override // com.bq.zowi.presenters.interactive.home.HomePresenter
    public void loadZowiSaysMinigame() {
        ((HomeWireframe) getWireframe()).presentZowiSaysMinigameView();
    }

    @Override // com.bq.zowi.presenters.interactive.home.HomePresenter
    public void loadMouthsMinigame() {
        ((HomeWireframe) getWireframe()).presentMouthsMinigameView();
    }

    @Override // com.bq.zowi.presenters.interactive.home.HomePresenter
    public void loadMouthsEditor() {
        ((HomeWireframe) getWireframe()).presentMouthsEditorView();
    }

    @Override // com.bq.zowi.presenters.interactive.home.HomePresenter
    public void loadZowiRunnerMinigame() {
        ((HomeWireframe) getWireframe()).presentZowiRunnerMinigameView();
    }

    @Override // com.bq.zowi.presenters.interactive.home.HomePresenter
    public void loadSettings() {
        ((HomeWireframe) getWireframe()).presentSettings();
    }

    @Override // com.bq.zowi.presenters.interactive.home.HomePresenter
    public void loadAchievements() {
        ((HomeWireframe) getWireframe()).presentAchievements();
    }

    @Override // com.bq.zowi.presenters.interactive.home.HomePresenter
    public void logAppStarted() {
        this.subscriptions.add(this.appController.isFirstUsage().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).flatMap(new Func1<Boolean, Single<Achievement>>() { // from class: com.bq.zowi.presenters.interactive.home.HomePresenterImpl.7
            @Override // rx.functions.Func1
            public Single<Achievement> call(Boolean isFirstUsage) {
                if (!isFirstUsage.booleanValue()) {
                    return HomePresenterImpl.this.achievementsInteractor.checkAchievementAndUnlockIt(HomePresenterImpl.APP_SECOND_USAGE_ACHIEVEMENT);
                }
                return Single.just(null);
            }
        }).map(new Func1<Achievement, Void>() { // from class: com.bq.zowi.presenters.interactive.home.HomePresenterImpl.6
            @Override // rx.functions.Func1
            public Void call(Achievement achievement) {
                if (achievement != null) {
                    ((HomeView) HomePresenterImpl.this.getView()).showAchievementUnlock(new AchievementViewModel(achievement.id, achievement.unlocked));
                    return null;
                }
                return null;
            }
        }).flatMap(new Func1<Void, Single<Void>>() { // from class: com.bq.zowi.presenters.interactive.home.HomePresenterImpl.5
            @Override // rx.functions.Func1
            public Single<Void> call(Void aVoid) {
                return HomePresenterImpl.this.appController.logAppStarted();
            }
        }).flatMap(new Func1<Void, Single<Integer>>() { // from class: com.bq.zowi.presenters.interactive.home.HomePresenterImpl.4
            @Override // rx.functions.Func1
            public Single<Integer> call(Void aVoid) {
                return HomePresenterImpl.this.appController.getDaysOfUse();
            }
        }).flatMap(new Func1<Integer, Single<Achievement>>() { // from class: com.bq.zowi.presenters.interactive.home.HomePresenterImpl.3
            @Override // rx.functions.Func1
            public Single<Achievement> call(Integer daysOfUse) {
                if (daysOfUse.intValue() >= 4) {
                    return HomePresenterImpl.this.achievementsInteractor.checkAchievementAndUnlockIt(HomePresenterImpl.APP_DAYS_OF_USE_ACHIEVEMENT);
                }
                return Single.just(null);
            }
        }).subscribe(new SingleSubscriber<Achievement>() { // from class: com.bq.zowi.presenters.interactive.home.HomePresenterImpl.2
            @Override // rx.SingleSubscriber
            public void onSuccess(Achievement achievement) {
                if (achievement != null) {
                    ((HomeView) HomePresenterImpl.this.getView()).showAchievementUnlock(new AchievementViewModel(achievement.id, achievement.unlocked));
                }
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
                Grove.e(error, "Failed loading time-based achievement.", new Object[0]);
            }
        }));
    }
}
