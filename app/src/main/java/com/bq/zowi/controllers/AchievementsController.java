package com.bq.zowi.controllers;

import com.bq.zowi.models.Achievement;
import java.util.ArrayList;
import rx.Single;

/* JADX INFO: loaded from: classes.dex */
public interface AchievementsController {
    Single<Achievement> getAchievement(Achievement.Id id);

    Single<ArrayList<Achievement>> getAchievementsList();

    void resetAchievementsList();

    Single<Achievement> unlockAchievement(Achievement.Id id);

    Single<Void> unlockAllAchievements();
}
