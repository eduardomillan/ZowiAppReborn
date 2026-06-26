package com.bq.zowi.presenters.interactive.settings;

import com.bq.zowi.controllers.BTConnectionController;
import com.bq.zowi.controllers.SessionController;
import com.bq.zowi.interactors.ChangeZowiNameInteractor;
import com.bq.zowi.interactors.CheckInstalledZowiAppInteractor;
import com.bq.zowi.interactors.ConnectToZowiInteractor;
import com.bq.zowi.interactors.ForgetPlayingHistoryInteractor;
import com.bq.zowi.interactors.ForgetZowiInteractor;
import com.bq.zowi.interactors.MeasureZowiBatteryLevelInteractor;
import com.bq.zowi.interactors.SendAppToZowiInteractor;
import com.bq.zowi.presenters.interactive.InteractiveBasePresenterImpl;
import com.bq.zowi.utils.Grove;
import com.bq.zowi.utils.NameValidator;
import com.bq.zowi.views.interactive.settings.SettingsView;
import com.bq.zowi.wireframes.settings.SettingsWireframe;
import rx.Scheduler;
import rx.SingleSubscriber;
import rx.schedulers.Schedulers;

/* JADX INFO: loaded from: classes.dex */
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

    @Override // com.bq.zowi.presenters.interactive.settings.SettingsPresenter
    public void homeButtonPressed() {
        ((SettingsWireframe) getWireframe()).presentHome();
    }

    @Override // com.bq.zowi.presenters.interactive.settings.SettingsPresenter
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
        this.subscriptions.add(this.changeZowiNameInteractor.changeZowiName(nameToSave).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<Void>() { // from class: com.bq.zowi.presenters.interactive.settings.SettingsPresenterImpl.1
            @Override // rx.SingleSubscriber
            public void onSuccess(Void value) {
                SettingsPresenterImpl.this.sessionController.saveActiveZowiName(nameToSave);
                ((SettingsView) SettingsPresenterImpl.this.getView()).showNameChangeSuccess();
                ((SettingsView) SettingsPresenterImpl.this.getView()).showZowiName(nameToSave);
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
                ((SettingsView) SettingsPresenterImpl.this.getView()).showNameChangeError();
            }
        }));
    }

    @Override // com.bq.zowi.presenters.interactive.settings.SettingsPresenter
    public void lookForAppUpdates() {
        ((SettingsWireframe) getWireframe()).openGooglePlayToCheckUpdates();
    }

    @Override // com.bq.zowi.presenters.interactive.settings.SettingsPresenter
    public void forgetPlayingHistory() {
        this.subscriptions.add(this.forgetPlayingHistoryInteractor.forgetPlayingHistory().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<Void>() { // from class: com.bq.zowi.presenters.interactive.settings.SettingsPresenterImpl.2
            @Override // rx.SingleSubscriber
            public void onSuccess(Void value) {
                ((SettingsView) SettingsPresenterImpl.this.getView()).showForgetPlayingHistorySuccess();
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
                ((SettingsView) SettingsPresenterImpl.this.getView()).showForgetPlayingHistoryError();
            }
        }));
    }

    @Override // com.bq.zowi.presenters.interactive.settings.SettingsPresenter
    public void forgetZowi() {
        this.subscriptions.add(this.forgetZowiInteractor.forgetZowi().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<Void>() { // from class: com.bq.zowi.presenters.interactive.settings.SettingsPresenterImpl.3
            @Override // rx.SingleSubscriber
            public void onSuccess(Void value) {
                ((SettingsView) SettingsPresenterImpl.this.getView()).showForgetZowiSuccess();
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
                ((SettingsView) SettingsPresenterImpl.this.getView()).showForgetZowiError();
            }
        }));
    }

    @Override // com.bq.zowi.presenters.interactive.settings.SettingsPresenter
    public void calibrateZowi() {
        ((SettingsWireframe) getWireframe()).presentCalibrationView();
    }

    @Override // com.bq.zowi.presenters.interactive.settings.SettingsPresenter
    public void visitHospital() {
        ((SettingsWireframe) getWireframe()).openHospitalWeb();
    }

    @Override // com.bq.zowi.presenters.interactive.settings.SettingsPresenter
    public void manageLowBatteryWhenCalibratingForInstallingFirmware() {
        if (this.connectionController.isConnected()) {
            this.measureZowiBatteryLevelInteractor.measureAndManageZowiBatteryLevel().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<Boolean>() { // from class: com.bq.zowi.presenters.interactive.settings.SettingsPresenterImpl.4
                @Override // rx.SingleSubscriber
                public void onSuccess(Boolean isBatteryLevelOverThreshold) {
                    if (isBatteryLevelOverThreshold.booleanValue()) {
                        Grove.d("Battery level is OK", new Object[0]);
                        ((SettingsView) SettingsPresenterImpl.this.getView()).showRestoringInfoWhenCalibratingAlteredZowi();
                    } else {
                        Grove.d("Battery level is too low!", new Object[0]);
                        ((SettingsView) SettingsPresenterImpl.this.getView()).showLowBatteryForInstallingFirmwareDialog(SettingsPresenterImpl.this.sessionController.loadActiveZowiName(), false);
                    }
                }

                @Override // rx.SingleSubscriber
                public void onError(Throwable error) {
                    Grove.d("BATTERY_LEVEL RETRIEVAL ERROR!!! " + error.getMessage(), new Object[0]);
                }
            });
        }
    }
}
