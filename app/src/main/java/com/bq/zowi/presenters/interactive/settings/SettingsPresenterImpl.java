package com.bq.zowi.presenters.interactive.settings;

import com.bq.zowi.api.BTConnectionController;
import com.bq.zowi.api.SessionController;
import com.bq.zowi.presenters.interactive.InteractiveBasePresenterImpl;
import com.bq.zowi.usecases.ChangeZowiNameInteractor;
import com.bq.zowi.usecases.CheckInstalledZowiAppInteractor;
import com.bq.zowi.usecases.ConnectToZowiInteractor;
import com.bq.zowi.usecases.ForgetPlayingHistoryInteractor;
import com.bq.zowi.usecases.ForgetZowiInteractor;
import com.bq.zowi.usecases.MeasureZowiBatteryLevelInteractor;
import com.bq.zowi.usecases.SendAppToZowiInteractor;
import com.bq.zowi.utils.Grove;
import com.bq.zowi.utils.NameValidator;
import com.bq.zowi.views.interactive.settings.SettingsView;
import com.bq.zowi.wireframes.settings.SettingsWireframe;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class SettingsPresenterImpl extends InteractiveBasePresenterImpl<SettingsView, SettingsWireframe> implements SettingsPresenter {
    private final ChangeZowiNameInteractor changeZowiNameInteractor;
    private final String factoryFirmwarePath;
    private final ForgetPlayingHistoryInteractor forgetPlayingHistoryInteractor;
    private final ForgetZowiInteractor forgetZowiInteractor;
    private final SendAppToZowiInteractor sendAppToZowiInteractor;
    private final SessionController sessionController;
    private final Scheduler uiScheduler;

    public SettingsPresenterImpl(String factoryFirmwarePath, SendAppToZowiInteractor sendAppToZowiInteractor, ChangeZowiNameInteractor changeZowiNameInteractor, ForgetZowiInteractor forgetZowiInteractor, ForgetPlayingHistoryInteractor forgetPlayingHistoryInteractor, SessionController sessionController, BTConnectionController connectionController, ConnectToZowiInteractor connectToZowiInteractor, MeasureZowiBatteryLevelInteractor measureZowiBatteryLevelInteractor, CheckInstalledZowiAppInteractor checkInstalledZowiAppInteractor, Scheduler uiScheduler) {
        super(sessionController, connectionController, connectToZowiInteractor, measureZowiBatteryLevelInteractor, checkInstalledZowiAppInteractor, sendAppToZowiInteractor, factoryFirmwarePath, uiScheduler);
        this.factoryFirmwarePath = factoryFirmwarePath;
        this.sessionController = sessionController;
        this.forgetZowiInteractor = forgetZowiInteractor;
        this.forgetPlayingHistoryInteractor = forgetPlayingHistoryInteractor;
        this.sendAppToZowiInteractor = sendAppToZowiInteractor;
        this.changeZowiNameInteractor = changeZowiNameInteractor;
        this.uiScheduler = uiScheduler;
    }

    @Override
    public void homeButtonPressed() {
        ((SettingsWireframe) getWireframe()).presentHome();
    }

    @Override
    public void changeZowiName(String newZowiName) {
        final String nameToSave;
        if (newZowiName.length() == 0) {
            nameToSave = this.sessionController.loadDefaultZowiName();
        } else {
            if (!NameValidator.isNameValid(newZowiName)) {
                ((SettingsView) getView()).showInvalidNameError();
                return;
            }
            nameToSave = newZowiName;
        }
        this.disposables.add(this.changeZowiNameInteractor.changeZowiName(nameToSave).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            () -> {
                SettingsPresenterImpl.this.sessionController.saveActiveZowiName(nameToSave);
                ((SettingsView) SettingsPresenterImpl.this.getView()).showNameChangeSuccess();
                ((SettingsView) SettingsPresenterImpl.this.getView()).showZowiName(nameToSave);
            },
            error -> ((SettingsView) SettingsPresenterImpl.this.getView()).showNameChangeError()
        ));
    }

    @Override
    public void lookForAppUpdates() {
        ((SettingsWireframe) getWireframe()).openGooglePlayToCheckUpdates();
    }

    @Override
    public void forgetPlayingHistory() {
        this.disposables.add(this.forgetPlayingHistoryInteractor.forgetPlayingHistory().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            () -> ((SettingsView) SettingsPresenterImpl.this.getView()).showForgetPlayingHistorySuccess(),
            error -> ((SettingsView) SettingsPresenterImpl.this.getView()).showForgetPlayingHistoryError()
        ));
    }

    @Override
    public void forgetZowi() {
        this.disposables.add(this.forgetZowiInteractor.forgetZowi().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            () -> ((SettingsView) SettingsPresenterImpl.this.getView()).showForgetZowiSuccess(),
            error -> ((SettingsView) SettingsPresenterImpl.this.getView()).showForgetZowiError()
        ));
    }

    @Override
    public void calibrateZowi() {
        ((SettingsWireframe) getWireframe()).presentCalibrationView();
    }

    @Override
    public void visitHospital() {
        ((SettingsWireframe) getWireframe()).openHospitalWeb();
    }

    @Override
    public void manageLowBatteryWhenCalibratingForInstallingFirmware() {
        if (this.connectionController.isConnected()) {
            this.disposables.add(this.measureZowiBatteryLevelInteractor.measureAndManageZowiBatteryLevel().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
                isBatteryLevelOverThreshold -> {
                    if (isBatteryLevelOverThreshold.booleanValue()) {
                        Grove.d("Battery level is OK", new Object[0]);
                        ((SettingsView) SettingsPresenterImpl.this.getView()).showRestoringInfoWhenCalibratingAlteredZowi();
                    } else {
                        Grove.d("Battery level is too low!", new Object[0]);
                        ((SettingsView) SettingsPresenterImpl.this.getView()).showLowBatteryForInstallingFirmwareDialog(SettingsPresenterImpl.this.sessionController.loadActiveZowiName(), false);
                    }
                },
                error -> Grove.d("BATTERY_LEVEL RETRIEVAL ERROR!!! " + error.getMessage(), new Object[0])
            ));
        }
    }
}
