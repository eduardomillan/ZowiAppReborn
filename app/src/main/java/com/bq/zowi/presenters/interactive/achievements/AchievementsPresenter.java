package com.bq.zowi.presenters.interactive.achievements;

import com.bq.zowi.presenters.interactive.InteractiveBasePresenter;
import com.bq.zowi.views.interactive.achievements.AchievementsView;
import com.bq.zowi.wireframes.achievements.AchievementsWireframe;

/* JADX INFO: loaded from: classes.dex */
public interface AchievementsPresenter extends InteractiveBasePresenter<AchievementsView, AchievementsWireframe> {
    void easterEggCombinationPressed();

    void homeButtonPressed();

    void loadAchievements();
}
