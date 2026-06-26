package com.bq.zowi.presenters.interactive.pad;

import com.bq.zowi.models.commands.Command;
import com.bq.zowi.presenters.interactive.InteractiveBasePresenter;
import com.bq.zowi.views.interactive.pad.PadView;
import com.bq.zowi.wireframes.pad.PadWireframe;

/* JADX INFO: loaded from: classes.dex */
public interface PadPresenter extends InteractiveBasePresenter<PadView, PadWireframe> {
    void actionButtonReleased();

    void actionPressed(Command command);

    void animationsPressed();

    void ascendingTurnPressed();

    void bendLeft();

    void bendRight();

    void configureDuration(String str);

    void crusaitoLeft();

    void crusaitoRight();

    void downArrowPressed();

    void flappingBackward();

    void flappingForward();

    void gameReady();

    void helpButtonPressed();

    void homeButtonPressed();

    void jitterPressed();

    void jumpPressed();

    void leftArrowPressed();

    void moonwalkerLeft();

    void moonwalkerRight();

    void mouthsPressed();

    void rightArrowPressed();

    void shakeLegLeft();

    void shakeLegRight();

    void swingPressed();

    void tipToeSwingPressed();

    void upArrowPressed();

    void updownPressed();
}
