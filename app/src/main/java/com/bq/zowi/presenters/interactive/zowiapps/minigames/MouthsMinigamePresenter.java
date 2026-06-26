package com.bq.zowi.presenters.interactive.zowiapps.minigames;

import com.bq.zowi.views.interactive.zowiapps.minigames.MouthsMiniGameView;
import com.bq.zowi.wireframes.zowiapps.minigames.MinigameBaseWireframe;

/* JADX INFO: loaded from: classes.dex */
public interface MouthsMinigamePresenter extends MinigameBasePresenter<MouthsMiniGameView, MinigameBaseWireframe> {
    void checkLedMouth(String str);

    @Override // com.bq.zowi.presenters.interactive.zowiapps.minigames.MinigameBasePresenter
    void playButtonPressed();
}
