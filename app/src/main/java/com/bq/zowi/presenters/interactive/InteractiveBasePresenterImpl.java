package com.bq.zowi.presenters.interactive;

import com.bq.zowi.api.BTConnectionController;
import com.bq.zowi.api.SessionController;
import com.bq.zowi.models.ConnectionSuccessData;
import com.bq.zowi.models.ZowiName;
import com.bq.zowi.presenters.BasePresenterImpl;
import com.bq.zowi.usecases.CheckInstalledZowiAppInteractor;
import com.bq.zowi.usecases.ConnectToZowiInteractor;
import com.bq.zowi.usecases.MeasureZowiBatteryLevelInteractor;
import com.bq.zowi.usecases.SendAppToZowiInteractor;
import com.bq.zowi.utils.Grove;
import com.bq.zowi.views.interactive.InteractiveBaseView;
import com.bq.zowi.wireframes.interactive.InteractiveWireframe;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

public abstract class InteractiveBasePresenterImpl<V extends InteractiveBaseView, W extends InteractiveWireframe> extends BasePresenterImpl<V, W> implements InteractiveBasePresenter<V, W> {
    protected final CheckInstalledZowiAppInteractor checkInstalledZowiAppInteractor;
    protected final ConnectToZowiInteractor connectToZowiInteractor;
    protected final BTConnectionController connectionController;
    protected final String factoryFirmwarePath;
    protected final MeasureZowiBatteryLevelInteractor measureZowiBatteryLevelInteractor;
    protected final SendAppToZowiInteractor sendAppToZowiInteractor;
    protected final SessionController sessionController;
    protected final Scheduler uiScheduler;

    public InteractiveBasePresenterImpl(SessionController sessionController, BTConnectionController connectionController, ConnectToZowiInteractor connectToZowiInteractor, MeasureZowiBatteryLevelInteractor measureZowiBatteryLevelInteractor, CheckInstalledZowiAppInteractor checkInstalledZowiAppInteractor, SendAppToZowiInteractor sendAppToZowiInteractor, String factoryFirmwarePath, Scheduler uiScheduler) {
        this.sessionController = sessionController;
        this.connectionController = connectionController;
        this.connectToZowiInteractor = connectToZowiInteractor;
        this.measureZowiBatteryLevelInteractor = measureZowiBatteryLevelInteractor;
        this.checkInstalledZowiAppInteractor = checkInstalledZowiAppInteractor;
        this.sendAppToZowiInteractor = sendAppToZowiInteractor;
        this.factoryFirmwarePath = factoryFirmwarePath;
        this.uiScheduler = uiScheduler;
    }

    @Override
    public void onCreateView() {
        super.onCreateView();
        addZowiConnectionStatusListener();
    }

    @Override
    public void manageConnection() {
        if (!this.connectionController.isSendingHexToZowi() && !this.connectionController.isConnected()) {
            connectToZowi();
        }
    }

    @Override
    public void manageZowiName() {
        if (!this.connectionController.isSendingHexToZowi()) {
            if (this.connectionController.isConnected()) {
                if (ZowiName.isFactoryName(this.sessionController.loadActiveZowiName())) {
                    getView().showZowiName(this.sessionController.loadDefaultZowiName());
                    return;
                } else {
                    getView().showZowiName(this.sessionController.loadActiveZowiName());
                    return;
                }
            }
            getView().showZowiName(this.sessionController.loadDefaultZowiName());
        }
    }

    @Override
    public void measureAndManageBatteryLevel() {
        if (!this.connectionController.isSendingHexToZowi() && this.connectionController.isConnected()) {
            measureBatteryLevel();
        }
    }

    @Override
    public void checkAndManageZowiAppInstalled() {
        if (!this.connectionController.isSendingHexToZowi() && this.connectionController.isConnected()) {
            checkZowiAppInstalled();
        }
    }

    @Override
    public void installFactoryFirmware(final boolean isAutoUpdate) {
        getView().showIsInstallingFw();
        getView().hideRestoreFirmwareDialog();
        getView().showFirmwareUpdatingDialog(this.sessionController.loadActiveZowiName(), isAutoUpdate);
        this.disposables.add(this.sendAppToZowiInteractor.sendAppToZowi(this.factoryFirmwarePath).sample(100L, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            progress -> {
                ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).showIsInstallingFw();
                if (progress != null) {
                    ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).updateFirmwareUpdatingProgressBar(progress.intValue());
                } else {
                    ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).showInstallingFwErrorDialog(InteractiveBasePresenterImpl.this.sessionController.loadActiveZowiName(), isAutoUpdate, InteractiveBasePresenterImpl.this.connectionController.isConnected());
                    ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).hideFirmwareUpdatingDialog();
                }
            },
            e -> {
                ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).updateNotificationOnFwInstallationError();
                ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).showInstallingFwErrorDialog(InteractiveBasePresenterImpl.this.sessionController.loadActiveZowiName(), isAutoUpdate, InteractiveBasePresenterImpl.this.connectionController.isConnected());
                ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).hideFirmwareUpdatingDialog();
            },
            () -> {
                ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).updateNotificationOnFwInstallationSuccess();
                ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).showInstallingFwSuccessDialog(InteractiveBasePresenterImpl.this.sessionController.loadActiveZowiName(), isAutoUpdate);
                ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).hideFirmwareUpdatingDialog();
            }
        ));
    }

    @Override
    public void launchWizard() {
        getWireframe().presentWizard();
    }

    @Override
    public void manageLowBatteryForInstallingFirmware(boolean isAutoUpdate) {
        manageLowBatteryForInstallingFirmware(isAutoUpdate, false);
    }

    @Override
    public void manageLowBatteryForInstallingFirmware(boolean isAutoUpdate, boolean installIfEnoughBattery) {
        manageLowBatteryForInstallingFirmware(isAutoUpdate, installIfEnoughBattery, -1.0f);
    }

    @Override
    public void manageLowBatteryForInstallingFirmware(final boolean isAutoUpdate, final boolean installIfEnoughBattery, float batteryLevel) {
        Single<Boolean> measureBattery;
        if (batteryLevel == -1.0f) {
            measureBattery = this.measureZowiBatteryLevelInteractor.measureAndManageZowiBatteryLevel();
        } else {
            measureBattery = this.measureZowiBatteryLevelInteractor.manageZowiBatteryLevel(batteryLevel);
        }
        this.disposables.add(measureBattery.subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            isBatteryLevelOverThreshold -> {
                if (isBatteryLevelOverThreshold.booleanValue()) {
                    Grove.d("Battery level is OK.", new Object[0]);
                    if (installIfEnoughBattery) {
                        InteractiveBasePresenterImpl.this.installFactoryFirmware(isAutoUpdate);
                        return;
                    } else {
                        ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).showInstallingFirmwareInfoDialog();
                        return;
                    }
                }
                Grove.d("Battery level is too low!", new Object[0]);
                ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).showLowBatteryForInstallingFirmwareDialog(InteractiveBasePresenterImpl.this.sessionController.loadActiveZowiName(), isAutoUpdate);
            },
            error -> Grove.d("BATTERY_LEVEL RETRIEVAL ERROR!!! " + error.getMessage(), new Object[0])
        ));
    }

    private void addZowiConnectionStatusListener() {
        this.disposables.add(this.connectionController.getConnectionStatusObservable().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            integer -> {
                switch (integer.intValue()) {
                    case 0:
                        if (InteractiveBasePresenterImpl.this.sessionController.loadActiveZowiDeviceAddress() != null) {
                            ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).showZowiDisconnected();
                            ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).showZowiName(InteractiveBasePresenterImpl.this.sessionController.loadActiveZowiName());
                        } else {
                            ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).showDemoMode();
                            ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).showZowiName(InteractiveBasePresenterImpl.this.sessionController.loadDefaultZowiName());
                        }
                        break;
                    case 2:
                        ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).showZowiConnecting();
                        break;
                    case 3:
                        ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).showZowiConnected();
                        break;
                }
            },
            e -> e.printStackTrace()
        ));
    }

    private void connectToZowi() {
        String activeZowiAddress = this.sessionController.loadActiveZowiDeviceAddress();
        if (activeZowiAddress != null) {
            this.disposables.add(this.connectToZowiInteractor.connectToZowiAndRetrieveData(activeZowiAddress).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
                connectionSuccessData -> {
                    if (connectionSuccessData != null) {
                        if (connectionSuccessData.getZowiName() != null && !ZowiName.isFactoryName(connectionSuccessData.getZowiName())) {
                            InteractiveBasePresenterImpl.this.sessionController.saveActiveZowiName(connectionSuccessData.getZowiName());
                            ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).showZowiName(InteractiveBasePresenterImpl.this.sessionController.loadActiveZowiName());
                        }
                        if (connectionSuccessData.getBatteryLevel() != -1.0f) {
                            InteractiveBasePresenterImpl.this.manageBatteryLevel(connectionSuccessData.getBatteryLevel());
                        }
                        if (connectionSuccessData.getZowiAppId() != null) {
                            InteractiveBasePresenterImpl.this.manageZowiAppInstalled(connectionSuccessData.getZowiAppId(), connectionSuccessData.getBatteryLevel());
                            return;
                        }
                        return;
                    }
                    ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).showCorruptedZowiDialog(InteractiveBasePresenterImpl.this.sessionController.loadActiveZowiName());
                },
                error -> {
                    ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).showZowiDisconnected();
                    Grove.d(error, "Error connecting to Zowi", new Object[0]);
                }
            ));
        } else {
            getView().showDemoMode();
        }
    }

    private void measureBatteryLevel() {
        this.disposables.add(this.measureZowiBatteryLevelInteractor.measureAndManageZowiBatteryLevel().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            isBatteryLevelOverThreshold -> {
                if (isBatteryLevelOverThreshold.booleanValue()) {
                    Grove.d("Battery level is OK.", new Object[0]);
                    ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).showGoodBatteryLevel();
                } else {
                    Grove.d("Battery level is too low!.", new Object[0]);
                    ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).showLowBatteryLevel();
                }
            },
            error -> Grove.d("BATTERY_LEVEL RETRIEVAL ERROR!!! " + error.getMessage(), new Object[0])
        ));
    }

    public void manageBatteryLevel(float batteryLevel) {
        this.disposables.add(this.measureZowiBatteryLevelInteractor.manageZowiBatteryLevel(batteryLevel).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            isBatteryLevelOverThreshold -> {
                if (isBatteryLevelOverThreshold.booleanValue()) {
                    Grove.d("Battery level is OK.", new Object[0]);
                    ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).showGoodBatteryLevel();
                } else {
                    Grove.d("Battery level is too low!.", new Object[0]);
                    ((InteractiveBaseView) InteractiveBasePresenterImpl.this.getView()).showLowBatteryLevel();
                }
            },
            error -> Grove.d("BATTERY_LEVEL RETRIEVAL ERROR!!! " + error.getMessage(), new Object[0])
        ));
    }

    private void checkZowiAppInstalled() {
        this.disposables.add(this.checkInstalledZowiAppInteractor.checkAndManageInstalledZowiApp().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            installedZowiAppStatus -> InteractiveBasePresenterImpl.this.manageZowiAppInstalledFeedback(installedZowiAppStatus, -1.0f),
            error -> Grove.d("CHECK INSTALLED APP ERROR!!! " + error.getMessage(), new Object[0])
        ));
    }

    public void manageZowiAppInstalled(String zowiAppInstalledId, float batteryLevel) {
        CheckInstalledZowiAppInteractor.InstalledZowiAppStatus installedZowiAppStatus = this.checkInstalledZowiAppInteractor.manageInstalledZowiApp(zowiAppInstalledId);
        manageZowiAppInstalledFeedback(installedZowiAppStatus, batteryLevel);
    }

    public void manageZowiAppInstalledFeedback(CheckInstalledZowiAppInteractor.InstalledZowiAppStatus installedZowiAppStatus, float batteryLevel) {
        switch (installedZowiAppStatus) {
            case UPDATE_NEEDED:
                manageLowBatteryForInstallingFirmware(true, true, batteryLevel);
                break;
            case CUSTOM_FIRMWARE:
                getView().updateNotificationOnAlteredFirmwareDetected();
                break;
        }
    }
}
