package com.bq.zowi.bridge;

import com.bq.zowi.models.Achievement;
import com.bq.zowi.interactors.CheckAchievementAndUnlockItInteractor;
import rx.Single;

/**
 * Bridge from core (RxJava2) CheckAchievementAndUnlockItInteractor to old (RxJava1) interface.
 * The Achievement model class is shared between core and app modules.
 */
public class CheckAchievementAndUnlockItInteractorBridge implements com.bq.zowi.interactors.CheckAchievementAndUnlockItInteractor {
    private final com.bq.zowi.usecases.CheckAchievementAndUnlockItInteractor core;

    public CheckAchievementAndUnlockItInteractorBridge(com.bq.zowi.usecases.CheckAchievementAndUnlockItInteractor core) {
        this.core = core;
    }

    @Override
    public Single<Achievement> checkAchievementAndUnlockIt(Achievement.Id achievementId) {
        return RxBridge.toRxSingle(core.checkAchievementAndUnlockIt(achievementId));
    }
}
