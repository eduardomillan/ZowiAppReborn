package com.bq.zowi.presenters.interactive.achievements;

import com.bq.zowi.api.AchievementsController;
import com.bq.zowi.api.BTConnectionController;
import com.bq.zowi.api.SessionController;
import com.bq.zowi.models.Achievement;
import com.bq.zowi.models.commands.AnimationCommand;
import com.bq.zowi.models.commands.Command;
import com.bq.zowi.models.viewmodels.AchievementViewModel;
import com.bq.zowi.presenters.interactive.InteractiveBasePresenterImpl;
import com.bq.zowi.subscribers.CommandSingleSubscriber;
import com.bq.zowi.usecases.CheckInstalledZowiAppInteractor;
import com.bq.zowi.usecases.ConnectToZowiInteractor;
import com.bq.zowi.usecases.MeasureZowiBatteryLevelInteractor;
import com.bq.zowi.usecases.SendAppToZowiInteractor;
import com.bq.zowi.usecases.SendCommandToZowiInteractor;
import com.bq.zowi.utils.Grove;
import com.bq.zowi.views.interactive.achievements.AchievementsView;
import com.bq.zowi.wireframes.achievements.AchievementsWireframe;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;

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

    @Override
    public void loadAchievements() {
        this.disposables.add(this.achievementsController.getAchievementsList().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            achievementsList -> {
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
            },
            error -> Grove.d("GET ACHIEVEMENT LIST ERROR" + error.getMessage(), new Object[0])
        ));
    }

    @Override
    public void homeButtonPressed() {
        ((AchievementsWireframe) getWireframe()).presentHome();
    }

    @Override
    public void easterEggCombinationPressed() {
        this.disposables.add(this.achievementsController.unlockAllAchievements().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            v -> {
                AchievementsPresenterImpl.this.loadAchievements();
                AchievementsPresenterImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(new AnimationCommand(Command.Action.VICTORY)).subscribe(new CommandSingleSubscriber());
            },
            error -> Grove.d("UNLOCK ALL ACHIEVEMENTS ERROR" + error.getMessage(), new Object[0])
        ));
    }
}
