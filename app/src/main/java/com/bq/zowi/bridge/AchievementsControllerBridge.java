package com.bq.zowi.bridge;

import com.bq.zowi.controllers.AchievementsController;
import com.bq.zowi.models.Achievement;
import java.util.ArrayList;
import rx.Single;

public class AchievementsControllerBridge implements AchievementsController {
    private final com.bq.zowi.api.AchievementsController core;

    public AchievementsControllerBridge(com.bq.zowi.api.AchievementsController core) {
        this.core = core;
    }

    @Override
    public Single<Achievement> getAchievement(Achievement.Id id) {
        return RxBridge.toRxSingle(core.getAchievement(id));
    }

    @Override
    public Single<ArrayList<Achievement>> getAchievementsList() {
        return RxBridge.toRxSingle(core.getAchievementsList());
    }

    @Override
    public void resetAchievementsList() {
        core.resetAchievementsList();
    }

    @Override
    public Single<Achievement> unlockAchievement(Achievement.Id id) {
        return RxBridge.toRxSingle(core.unlockAchievement(id));
    }

    @Override
    public Single<Void> unlockAllAchievements() {
        return RxBridge.toRxSingle(core.unlockAllAchievements());
    }
}
