package com.bq.zowi.presenters.interactive.zowiapps;

import com.bq.zowi.presenters.interactive.InteractiveBasePresenter;
import com.bq.zowi.views.interactive.zowiapps.MouthsEditorView;
import com.bq.zowi.wireframes.zowiapps.MouthsEditorWireframe;

/* JADX INFO: loaded from: classes.dex */
public interface MouthsEditorPresenter extends InteractiveBasePresenter<MouthsEditorView, MouthsEditorWireframe> {
    void helpButtonPressed();

    void homeButtonPressed();

    void sendLedMouth(String str);
}
