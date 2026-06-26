package com.bq.zowi.views.interactive.zowiapps.minigames;

/* JADX INFO: loaded from: classes.dex */
public interface ZowiSaysMinigameView extends MinigameBaseView {
    void blockUserControls();

    void hideProgress();

    void setProgressValue(int i, int i2, int i3);

    void showProgress();

    void showUserControls();
}
