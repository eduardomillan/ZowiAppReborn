package com.bq.zowi.presenters.wizard;

import android.bluetooth.BluetoothDevice;
import com.bq.analytics.core.AnalyticsController;
import com.bq.analytics.hit.UserTiming;
import com.bq.zowi.analytics.AnalyticsUtils;
import com.bq.zowi.controllers.SessionController;
import com.bq.zowi.interactors.ChangeZowiNameInteractor;
import com.bq.zowi.interactors.ConnectToZowiInteractor;
import com.bq.zowi.interactors.FindZowisInteractor;
import com.bq.zowi.models.ConnectionSuccessData;
import com.bq.zowi.models.ZowiName;
import com.bq.zowi.presenters.BasePresenterImpl;
import com.bq.zowi.utils.Grove;
import com.bq.zowi.utils.NameValidator;
import com.bq.zowi.views.wizard.WizardView;
import com.bq.zowi.wireframes.wizard.WizardWireframe;
import java.util.Date;
import rx.Scheduler;
import rx.SingleSubscriber;
import rx.schedulers.Schedulers;

/* JADX INFO: loaded from: classes.dex */
public class WizardPresenterImpl extends BasePresenterImpl<WizardView, WizardWireframe> implements WizardPresenter {
    private AnalyticsController analyticsController;
    private ChangeZowiNameInteractor changeZowiNameInteractor;
    private ConnectToZowiInteractor connectToZowiInteractor;
    private FindZowisInteractor findZowisInteractor;
    private SessionController sessionController;
    private Scheduler uiScheduler;
    private long wizardStartTimeMillis = -1;

    public WizardPresenterImpl(FindZowisInteractor findZowisInteractor, ConnectToZowiInteractor connectToZowiInteractor, ChangeZowiNameInteractor changeZowiNameInteractor, SessionController sessionController, Scheduler uiScheduler, AnalyticsController analyticsController) {
        this.findZowisInteractor = findZowisInteractor;
        this.connectToZowiInteractor = connectToZowiInteractor;
        this.changeZowiNameInteractor = changeZowiNameInteractor;
        this.uiScheduler = uiScheduler;
        this.findZowisInteractor = findZowisInteractor;
        this.connectToZowiInteractor = connectToZowiInteractor;
        this.sessionController = sessionController;
        this.analyticsController = analyticsController;
    }

    @Override // com.bq.zowi.presenters.BasePresenterImpl, com.bq.zowi.presenters.BasePresenter
    public void onCreateView() {
        super.onCreateView();
        this.wizardStartTimeMillis = new Date().getTime();
    }

    @Override // com.bq.zowi.presenters.wizard.WizardPresenter
    public void findZowis() {
        getView().showSearchingForZowis();
        getView().showSpinner();
        this.subscriptions.add(this.findZowisInteractor.findZowis().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<BluetoothDevice>() { // from class: com.bq.zowi.presenters.wizard.WizardPresenterImpl.1
            @Override // rx.SingleSubscriber
            public void onSuccess(BluetoothDevice foundZowiBTDevice) {
                String deviceAddress = foundZowiBTDevice.getAddress();
                WizardPresenterImpl.this.getView().showZowiFound(deviceAddress);
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
                WizardPresenterImpl.this.getView().showNoZowisFound();
            }
        }));
    }

    @Override // com.bq.zowi.presenters.wizard.WizardPresenter
    public void connectToZowi(final String deviceAddress) {
        getView().showSpinner();
        this.subscriptions.add(this.connectToZowiInteractor.connectToZowiAndRetrieveData(deviceAddress).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<ConnectionSuccessData>() { // from class: com.bq.zowi.presenters.wizard.WizardPresenterImpl.2
            @Override // rx.SingleSubscriber
            public void onSuccess(ConnectionSuccessData connectionSuccessData) {
                WizardPresenterImpl.this.manageZowiConnectionSuccess(connectionSuccessData, deviceAddress);
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
                WizardPresenterImpl.this.getView().hideSpinner();
                WizardPresenterImpl.this.getView().showConnectionError();
                Grove.d("CONNECT to Zowi ERROR! " + error.toString(), new Object[0]);
            }
        }));
    }

    @Override // com.bq.zowi.presenters.wizard.WizardPresenter
    public void changeZowiName(String name, final String deviceAddress) {
        final String nameToSave;
        if (name.length() == 0) {
            nameToSave = this.sessionController.loadDefaultZowiName();
        } else {
            if (!NameValidator.isNameValid(name)) {
                getView().showInvalidNameError();
                return;
            }
            nameToSave = name;
        }
        this.subscriptions.add(this.changeZowiNameInteractor.changeZowiName(nameToSave).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<Void>() { // from class: com.bq.zowi.presenters.wizard.WizardPresenterImpl.3
            @Override // rx.SingleSubscriber
            public void onSuccess(Void value) {
                WizardPresenterImpl.this.sessionController.saveActiveZowiName(nameToSave);
                WizardPresenterImpl.this.wizardComplete(deviceAddress);
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
                Grove.e(error, "CHANGING ZOWI NAME DURING WIZARD ERROR!!!", new Object[0]);
            }
        }));
    }

    @Override // com.bq.zowi.presenters.wizard.WizardPresenter
    public void wizardComplete(String deviceAddress) {
        if (this.wizardStartTimeMillis != -1) {
            this.analyticsController.send(new UserTiming(AnalyticsUtils.EVENT_WIZARD_DURATION, Long.valueOf(new Date().getTime() - this.wizardStartTimeMillis), AnalyticsUtils.EVENT_WIZARD_DURATION_FINISH, null));
        }
        this.sessionController.saveActiveZowiDeviceAddress(deviceAddress);
        getWireframe().presentWizardCompleteView();
    }

    @Override // com.bq.zowi.presenters.wizard.WizardPresenter
    public void dismissWizard() {
        getWireframe().presentWizardCompleteView();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void manageZowiConnectionSuccess(ConnectionSuccessData connectionSuccessData, String deviceAddress) {
        getView().hideSpinner();
        if (connectionSuccessData != null) {
            if (connectionSuccessData.getZowiName() != null) {
                if (ZowiName.isFactoryName(connectionSuccessData.getZowiName())) {
                    getView().showConnectionSuccessWithEditableName(deviceAddress);
                    return;
                } else {
                    this.sessionController.saveActiveZowiName(connectionSuccessData.getZowiName());
                    wizardComplete(deviceAddress);
                    return;
                }
            }
            return;
        }
        wizardComplete(deviceAddress);
    }
}
