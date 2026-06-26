package com.bq.zowi.presenters.interactive.zowiapps.minigames;

import com.bq.zowi.models.commands.Command;
import com.bq.zowi.presenters.interactive.InteractiveBasePresenter;
import com.bq.zowi.views.interactive.zowiapps.minigames.ZowiRunnerMinigameView;
import com.bq.zowi.wireframes.interactive.InteractiveWireframe;

/* JADX INFO: loaded from: classes.dex */
public interface ZowiRunnerMinigamePresenter extends InteractiveBasePresenter<ZowiRunnerMinigameView, InteractiveWireframe> {
    void leftButtonPressed();

    void noTilt();

    void playButtonPressed();

    void rightButtonPressed();

    void stopButtonPressed();

    void tilt(Command.Direction direction);
}
