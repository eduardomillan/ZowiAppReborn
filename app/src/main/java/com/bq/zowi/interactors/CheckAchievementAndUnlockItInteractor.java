package com.bq.zowi.interactors;

import com.bq.zowi.models.Achievement;
import rx.Single;

/* JADX INFO: loaded from: classes.dex */
public interface CheckAchievementAndUnlockItInteractor {
    Single<Achievement> checkAchievementAndUnlockIt(Achievement.Id id);
}
