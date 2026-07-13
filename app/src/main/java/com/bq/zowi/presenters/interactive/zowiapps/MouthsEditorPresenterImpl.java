package com.bq.zowi.presenters.interactive.zowiapps;

import com.bq.zowi.api.BTConnectionController;
import com.bq.zowi.api.SessionController;
import com.bq.zowi.models.commands.LedCommand;
import com.bq.zowi.presenters.interactive.InteractiveBasePresenterImpl;
import com.bq.zowi.subscribers.CommandSingleSubscriber;
import com.bq.zowi.usecases.CheckInstalledZowiAppInteractor;
import com.bq.zowi.usecases.ConnectToZowiInteractor;
import com.bq.zowi.usecases.MeasureZowiBatteryLevelInteractor;
import com.bq.zowi.usecases.SendAppToZowiInteractor;
import com.bq.zowi.usecases.SendCommandToZowiInteractor;
import com.bq.zowi.views.interactive.zowiapps.MouthsEditorView;
import com.bq.zowi.wireframes.zowiapps.MouthsEditorWireframe;
import io.reactivex.Scheduler;

public class MouthsEditorPresenterImpl extends InteractiveBasePresenterImpl<MouthsEditorView, MouthsEditorWireframe> implements MouthsEditorPresenter {
    private SendCommandToZowiInteractor sendCommandToZowiInteractor;

    public MouthsEditorPresenterImpl(SessionController sessionController, BTConnectionController connectionController, ConnectToZowiInteractor connectToZowiInteractor, MeasureZowiBatteryLevelInteractor measureZowiBatteryLevelInteractor, CheckInstalledZowiAppInteractor checkInstalledZowiAppInteractor, SendAppToZowiInteractor sendAppToZowiInteractor, SendCommandToZowiInteractor sendCommandToZowiInteractor, String factoryFirmwarePath, Scheduler uiScheduler) {
        super(sessionController, connectionController, connectToZowiInteractor, measureZowiBatteryLevelInteractor, checkInstalledZowiAppInteractor, sendAppToZowiInteractor, factoryFirmwarePath, uiScheduler);
        this.sendCommandToZowiInteractor = sendCommandToZowiInteractor;
    }

    @Override
    public void homeButtonPressed() {
        ((MouthsEditorWireframe) getWireframe()).presentHome();
    }

    @Override
    public void helpButtonPressed() {
        ((MouthsEditorView) getView()).showHelp();
    }

    @Override
    public void sendLedMouth(String binaryMouthRepresentation) {
        this.sendCommandToZowiInteractor.sendCommandToZowi(new LedCommand(binaryMouthRepresentation)).subscribe(new CommandSingleSubscriber());
    }
}
