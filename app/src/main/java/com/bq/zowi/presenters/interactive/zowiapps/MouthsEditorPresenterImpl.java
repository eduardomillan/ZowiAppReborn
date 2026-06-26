package com.bq.zowi.presenters.interactive.zowiapps;

import com.bq.zowi.controllers.BTConnectionController;
import com.bq.zowi.controllers.SessionController;
import com.bq.zowi.interactors.CheckInstalledZowiAppInteractor;
import com.bq.zowi.interactors.ConnectToZowiInteractor;
import com.bq.zowi.interactors.MeasureZowiBatteryLevelInteractor;
import com.bq.zowi.interactors.SendAppToZowiInteractor;
import com.bq.zowi.interactors.SendCommandToZowiInteractor;
import com.bq.zowi.models.commands.LedCommand;
import com.bq.zowi.presenters.interactive.InteractiveBasePresenterImpl;
import com.bq.zowi.subscribers.CommandSingleSubscriber;
import com.bq.zowi.views.interactive.zowiapps.MouthsEditorView;
import com.bq.zowi.wireframes.zowiapps.MouthsEditorWireframe;
import rx.Scheduler;

/* JADX INFO: loaded from: classes.dex */
public class MouthsEditorPresenterImpl extends InteractiveBasePresenterImpl<MouthsEditorView, MouthsEditorWireframe> implements MouthsEditorPresenter {
    private SendCommandToZowiInteractor sendCommandToZowiInteractor;

    public MouthsEditorPresenterImpl(SessionController sessionController, BTConnectionController connectionController, ConnectToZowiInteractor connectToZowiInteractor, MeasureZowiBatteryLevelInteractor measureZowiBatteryLevelInteractor, CheckInstalledZowiAppInteractor checkInstalledZowiAppInteractor, SendAppToZowiInteractor sendAppToZowiInteractor, SendCommandToZowiInteractor sendCommandToZowiInteractor, String factoryFirmwarePath, Scheduler uiScheduler) {
        super(sessionController, connectionController, connectToZowiInteractor, measureZowiBatteryLevelInteractor, checkInstalledZowiAppInteractor, sendAppToZowiInteractor, factoryFirmwarePath, uiScheduler);
        this.sendCommandToZowiInteractor = sendCommandToZowiInteractor;
    }

    @Override // com.bq.zowi.presenters.interactive.zowiapps.MouthsEditorPresenter
    public void homeButtonPressed() {
        ((MouthsEditorWireframe) getWireframe()).presentHome();
    }

    @Override // com.bq.zowi.presenters.interactive.zowiapps.MouthsEditorPresenter
    public void helpButtonPressed() {
        ((MouthsEditorView) getView()).showHelp();
    }

    @Override // com.bq.zowi.presenters.interactive.zowiapps.MouthsEditorPresenter
    public void sendLedMouth(String binaryMouthRepresentation) {
        this.sendCommandToZowiInteractor.sendCommandToZowi(new LedCommand(binaryMouthRepresentation)).subscribe(new CommandSingleSubscriber());
    }
}
