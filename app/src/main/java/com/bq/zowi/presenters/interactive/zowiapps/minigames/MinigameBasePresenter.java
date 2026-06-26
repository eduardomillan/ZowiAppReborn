package com.bq.zowi.presenters.interactive.zowiapps.minigames;

import com.bq.zowi.presenters.interactive.InteractiveBasePresenter;
import com.bq.zowi.views.interactive.zowiapps.minigames.MinigameBaseView;
import com.bq.zowi.wireframes.zowiapps.minigames.MinigameBaseWireframe;

/* JADX INFO: loaded from: classes.dex */
public interface MinigameBasePresenter<V extends MinigameBaseView, W extends MinigameBaseWireframe> extends InteractiveBasePresenter<V, W> {
    void achievementContinueButtonClicked();

    void gameOver(int i);

    void gameReady();

    void helpButtonPressed();

    void homeButtonPressed();

    void playButtonPressed();

    void playerNameEnteredForRanking(String str);

    void rankingButtonPressed();
}
