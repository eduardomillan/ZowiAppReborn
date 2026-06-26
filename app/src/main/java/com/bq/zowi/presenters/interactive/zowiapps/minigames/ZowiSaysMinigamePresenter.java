package com.bq.zowi.presenters.interactive.zowiapps.minigames;

import com.bq.zowi.views.interactive.zowiapps.minigames.ZowiSaysMinigameView;
import com.bq.zowi.wireframes.zowiapps.minigames.MinigameBaseWireframe;

/* JADX INFO: loaded from: classes.dex */
public interface ZowiSaysMinigamePresenter extends MinigameBasePresenter<ZowiSaysMinigameView, MinigameBaseWireframe> {
    void bottomLeftActionPressed();

    void bottomRightActionPressed();

    void topLeftActionPressed();

    void topRightActionPressed();
}
