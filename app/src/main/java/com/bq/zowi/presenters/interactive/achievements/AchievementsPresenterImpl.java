package com.bq.zowi.presenters.interactive.achievements;

import com.bq.zowi.controllers.AchievementsController;
import com.bq.zowi.controllers.BTConnectionController;
import com.bq.zowi.controllers.SessionController;
import com.bq.zowi.interactors.CheckInstalledZowiAppInteractor;
import com.bq.zowi.interactors.ConnectToZowiInteractor;
import com.bq.zowi.interactors.MeasureZowiBatteryLevelInteractor;
import com.bq.zowi.interactors.SendAppToZowiInteractor;
import com.bq.zowi.interactors.SendCommandToZowiInteractor;
import com.bq.zowi.models.Achievement;
import com.bq.zowi.models.commands.AnimationCommand;
import com.bq.zowi.models.commands.Command;
import com.bq.zowi.models.viewmodels.AchievementViewModel;
import com.bq.zowi.presenters.interactive.InteractiveBasePresenterImpl;
import com.bq.zowi.subscribers.CommandSingleSubscriber;
import com.bq.zowi.utils.Grove;
import com.bq.zowi.views.interactive.achievements.AchievementsView;
import com.bq.zowi.wireframes.achievements.AchievementsWireframe;
import java.util.ArrayList;
import rx.Scheduler;
import rx.SingleSubscriber;
import rx.schedulers.Schedulers;

/* JADX INFO: loaded from: classes.dex */
public class AchievementsPresenterImpl extends InteractiveBasePresenterImpl<AchievementsView, AchievementsWireframe> implements AchievementsPresenter {
    private AchievementsController achievementsController;
    private SendCommandToZowiInteractor sendCommandToZowiInteractor;
    private Scheduler uiScheduler;

    public AchievementsPresenterImpl(SessionController sessionController, BTConnectionController connectionController, ConnectToZowiInteractor connectToZowiInteractor, MeasureZowiBatteryLevelInteractor measureZowiBatteryLevelInteractor, CheckInstalledZowiAppInteractor checkInstalledZowiAppInteractor, SendAppToZowiInteractor sendAppToZowiInteractor, SendCommandToZowiInteractor sendCommandToZowiInteractor, String factoryFirmwarePath, Scheduler uiScheduler, AchievementsController achievementsController) {
        super(sessionController, connectionController, connectToZowiInteractor, measureZowiBatteryLevelInteractor, checkInstalledZowiAppInteractor, sendAppToZowiInteractor, factoryFirmwarePath, uiScheduler);
        this.uiScheduler = uiScheduler;
        this.achievementsController = achievementsController;
        this.sendCommandToZowiInteractor = sendCommandToZowiInteractor;
    }

    @Override // com.bq.zowi.presenters.interactive.achievements.AchievementsPresenter
    public void loadAchievements() {
        this.subscriptions.add(this.achievementsController.getAchievementsList().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<ArrayList<Achievement>>() { // from class: com.bq.zowi.presenters.interactive.achievements.AchievementsPresenterImpl.1
            @Override // rx.SingleSubscriber
            public void onSuccess(ArrayList<Achievement> achievementsList) {
                ArrayList<AchievementViewModel> movementsList = new ArrayList<>();
                ArrayList<AchievementViewModel> animationsList = new ArrayList<>();
                ArrayList<AchievementViewModel> gamesList = new ArrayList<>();
                for (Achievement achievement : achievementsList) {
                    if (achievement.type.equals(Achievement.Type.movement.toString())) {
                        movementsList.add(new AchievementViewModel(achievement.id, achievement.unlocked));
                    }
                    if (achievement.type.equals(Achievement.Type.animation.toString())) {
                        animationsList.add(new AchievementViewModel(achievement.id, achievement.unlocked));
                    }
                    if (achievement.type.equals(Achievement.Type.game.toString())) {
                        gamesList.add(new AchievementViewModel(achievement.id, achievement.unlocked));
                    }
                }
                ((AchievementsView) AchievementsPresenterImpl.this.getView()).paintAchievements(movementsList, animationsList, gamesList);
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
                Grove.d("GET ACHIEVEMENT LIST ERROR" + error.getMessage(), new Object[0]);
            }
        }));
    }

    @Override // com.bq.zowi.presenters.interactive.achievements.AchievementsPresenter
    public void homeButtonPressed() {
        ((AchievementsWireframe) getWireframe()).presentHome();
    }

    @Override // com.bq.zowi.presenters.interactive.achievements.AchievementsPresenter
    public void easterEggCombinationPressed() {
        this.subscriptions.add(this.achievementsController.unlockAllAchievements().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<Void>() { // from class: com.bq.zowi.presenters.interactive.achievements.AchievementsPresenterImpl.2
            @Override // rx.SingleSubscriber
            public void onSuccess(Void value) {
                AchievementsPresenterImpl.this.loadAchievements();
                AchievementsPresenterImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(new AnimationCommand(Command.Action.VICTORY)).subscribe(new CommandSingleSubscriber());
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
                Grove.d("UNLOCK ALL ACHIEVEMENTS ERROR" + error.getMessage(), new Object[0]);
            }
        }));
    }
}
