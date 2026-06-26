package com.bq.zowi.views.interactive.zowiapps.minigames;

import com.bq.zowi.models.viewmodels.AchievementViewModel;
import com.bq.zowi.models.viewmodels.RankingEntryViewModel;
import com.bq.zowi.views.interactive.InteractiveBaseView;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public interface MinigameBaseView extends InteractiveBaseView {
    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    void showAchievementUnlock(AchievementViewModel achievementViewModel);

    void showHelp();

    void showPoinsEarned(int i, boolean z);

    void showRanking(ArrayList<RankingEntryViewModel> arrayList);
}
