package com.bq.zowi.interactors;

import com.bq.zowi.controllers.AchievementsController;
import com.bq.zowi.models.Achievement;
import com.bq.zowi.models.commands.AnimationCommand;
import com.bq.zowi.models.commands.Command;
import com.bq.zowi.subscribers.CommandSingleSubscriber;
import rx.Single;
import rx.functions.Func1;

/* JADX INFO: loaded from: classes.dex */
public class CheckAchievementAndUnlockItInteractorImpl implements CheckAchievementAndUnlockItInteractor {
    AchievementsController achievementsController;
    private SendCommandToZowiInteractor sendCommandToZowiInteractor;

    public CheckAchievementAndUnlockItInteractorImpl(AchievementsController achievementsController, SendCommandToZowiInteractor sendCommandToZowiInteractor) {
        this.achievementsController = achievementsController;
        this.sendCommandToZowiInteractor = sendCommandToZowiInteractor;
    }

    @Override // com.bq.zowi.interactors.CheckAchievementAndUnlockItInteractor
    public Single<Achievement> checkAchievementAndUnlockIt(final Achievement.Id achievementId) {
        return this.achievementsController.getAchievement(achievementId).flatMap(new Func1<Achievement, Single<? extends Achievement>>() { // from class: com.bq.zowi.interactors.CheckAchievementAndUnlockItInteractorImpl.1
            @Override // rx.functions.Func1
            public Single<? extends Achievement> call(Achievement achievement) {
                if (achievement.unlocked) {
                    return Single.just(null);
                }
                CheckAchievementAndUnlockItInteractorImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(new AnimationCommand(Command.Action.VICTORY)).subscribe(new CommandSingleSubscriber());
                return CheckAchievementAndUnlockItInteractorImpl.this.achievementsController.unlockAchievement(achievementId);
            }
        });
    }
}
