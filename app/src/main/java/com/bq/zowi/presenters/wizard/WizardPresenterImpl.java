package com.bq.zowi.presenters.wizard;

import com.bq.zowi.api.SessionController;
import com.bq.zowi.models.ConnectionSuccessData;
import com.bq.zowi.models.ZowiName;
import com.bq.zowi.presenters.BasePresenterImpl;
import com.bq.zowi.usecases.ChangeZowiNameInteractor;
import com.bq.zowi.usecases.ConnectToZowiInteractor;
import com.bq.zowi.usecases.FindZowisInteractor;
import com.bq.zowi.utils.Grove;
import com.bq.zowi.utils.NameValidator;
import com.bq.zowi.views.wizard.WizardView;
import com.bq.zowi.wireframes.wizard.WizardWireframe;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

public class WizardPresenterImpl extends BasePresenterImpl<WizardView, WizardWireframe> implements WizardPresenter {
    private ChangeZowiNameInteractor changeZowiNameInteractor;
    private ConnectToZowiInteractor connectToZowiInteractor;
    private FindZowisInteractor findZowisInteractor;
    private SessionController sessionController;
    private Scheduler uiScheduler;

    public WizardPresenterImpl(FindZowisInteractor findZowisInteractor, ConnectToZowiInteractor connectToZowiInteractor, ChangeZowiNameInteractor changeZowiNameInteractor, SessionController sessionController, Scheduler uiScheduler) {
        this.findZowisInteractor = findZowisInteractor;
        this.connectToZowiInteractor = connectToZowiInteractor;
        this.changeZowiNameInteractor = changeZowiNameInteractor;
        this.uiScheduler = uiScheduler;
        this.sessionController = sessionController;
    }

    @Override
    public void findZowis() {
        getView().showSearchingForZowis();
        getView().showSpinner();
        this.disposables.add(this.findZowisInteractor.findZowis().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            foundZowiDevice -> {
                String deviceAddress = foundZowiDevice.getAddress();
                WizardPresenterImpl.this.getView().showZowiFound(deviceAddress);
            },
            error -> WizardPresenterImpl.this.getView().showNoZowisFound()
        ));
    }

    @Override
    public void connectToZowi(final String deviceAddress) {
        getView().showSpinner();
        this.disposables.add(this.connectToZowiInteractor.connectToZowiAndRetrieveData(deviceAddress).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            connectionSuccessData -> WizardPresenterImpl.this.manageZowiConnectionSuccess(connectionSuccessData, deviceAddress),
            error -> {
                WizardPresenterImpl.this.getView().hideSpinner();
                WizardPresenterImpl.this.getView().showConnectionError();
                Grove.d("CONNECT to Zowi ERROR! " + error.toString(), new Object[0]);
            }
        ));
    }

    @Override
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
        this.disposables.add(this.changeZowiNameInteractor.changeZowiName(nameToSave).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            () -> {
                WizardPresenterImpl.this.sessionController.saveActiveZowiName(nameToSave);
                WizardPresenterImpl.this.wizardComplete(deviceAddress);
            },
            error -> Grove.e(error, "CHANGING ZOWI NAME DURING WIZARD ERROR!!!", new Object[0])
        ));
    }

    @Override
    public void wizardComplete(String deviceAddress) {
        this.sessionController.saveActiveZowiDeviceAddress(deviceAddress);
        getWireframe().presentWizardCompleteView();
    }

    @Override
    public void dismissWizard() {
        this.sessionController.saveWizardDismissed(true);
        getWireframe().presentWizardCompleteView();
    }

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
