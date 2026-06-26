package com.bq.zowi.presenters.interactive.settings;

import com.bq.zowi.controllers.BTConnectionController;
import com.bq.zowi.controllers.SessionController;
import com.bq.zowi.interactors.CheckInstalledZowiAppInteractor;
import com.bq.zowi.interactors.ConnectToZowiInteractor;
import com.bq.zowi.interactors.MeasureZowiBatteryLevelInteractor;
import com.bq.zowi.interactors.SendAppToZowiInteractor;
import com.bq.zowi.interactors.SendCommandToZowiInteractor;
import com.bq.zowi.models.commands.AnimationCommand;
import com.bq.zowi.models.commands.CalibrationCommand;
import com.bq.zowi.models.commands.Command;
import com.bq.zowi.presenters.interactive.InteractiveBasePresenterImpl;
import com.bq.zowi.subscribers.CommandSingleSubscriber;
import com.bq.zowi.views.interactive.settings.CalibrationView;
import com.bq.zowi.wireframes.settings.CalibrationWireframe;
import java.util.Date;
import rx.Scheduler;
import rx.Single;
import rx.functions.Func1;

/* JADX INFO: loaded from: classes.dex */
public class CalibrationPresenterImpl extends InteractiveBasePresenterImpl<CalibrationView, CalibrationWireframe> implements CalibrationPresenter {
    private static final long MIN_TIME_BETWEEN_CALIBRATION_CHANGES = 200;
    private final int BASE_GRADE;
    private final int MAX_TRIM;
    private final int MIN_TRIM;
    private long lastCalibrationChangeTime;
    private SendCommandToZowiInteractor sendCommandToZowiInteractor;
    private int trimLeftFootRL;
    private int trimLeftLegYL;
    private int trimRightFootRR;
    private int trimRightLegYR;

    public CalibrationPresenterImpl(SessionController sessionController, BTConnectionController connectionController, ConnectToZowiInteractor connectToZowiInteractor, MeasureZowiBatteryLevelInteractor measureZowiBatteryLevelInteractor, CheckInstalledZowiAppInteractor checkInstalledZowiAppInteractor, SendAppToZowiInteractor sendAppToZowiInteractor, String factoryFirmwarePath, Scheduler uiScheduler, SendCommandToZowiInteractor sendCommandToZowiInteractor) {
        super(sessionController, connectionController, connectToZowiInteractor, measureZowiBatteryLevelInteractor, checkInstalledZowiAppInteractor, sendAppToZowiInteractor, factoryFirmwarePath, uiScheduler);
        this.BASE_GRADE = 90;
        this.trimLeftLegYL = 0;
        this.trimRightLegYR = 0;
        this.trimLeftFootRL = 0;
        this.trimRightFootRR = 0;
        this.MAX_TRIM = 30;
        this.MIN_TRIM = -30;
        this.lastCalibrationChangeTime = -1L;
        this.sendCommandToZowiInteractor = sendCommandToZowiInteractor;
    }

    @Override // com.bq.zowi.presenters.interactive.settings.CalibrationPresenter
    public void warningCancelButtonPressed() {
        ((CalibrationWireframe) getWireframe()).presentHome();
    }

    @Override // com.bq.zowi.presenters.interactive.settings.CalibrationPresenter
    public void warningContinueButtonPressed() {
        this.sendCommandToZowiInteractor.sendCommandToZowi(new CalibrationCommand(CalibrationCommand.CALIBRATE_TRIM, 0, 0, 0, 0)).flatMap(new Func1<Void, Single<Void>>() { // from class: com.bq.zowi.presenters.interactive.settings.CalibrationPresenterImpl.1
            @Override // rx.functions.Func1
            public Single<Void> call(Void aVoid) {
                ((CalibrationView) CalibrationPresenterImpl.this.getView()).showLegsCalibration();
                return CalibrationPresenterImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(new CalibrationCommand(CalibrationCommand.CALIBRATE_GRADES, 90, 90, 90, 90));
            }
        }).subscribe(new CommandSingleSubscriber());
    }

    @Override // com.bq.zowi.presenters.interactive.settings.CalibrationPresenter
    public void calibrationParametersChanged() {
        this.sendCommandToZowiInteractor.sendCommandToZowi(new CalibrationCommand(CalibrationCommand.CALIBRATE_GRADES, this.trimLeftLegYL + 90, this.trimRightLegYR + 90, this.trimLeftFootRL + 90, this.trimRightFootRR + 90)).subscribe(new CommandSingleSubscriber());
    }

    @Override // com.bq.zowi.presenters.interactive.settings.CalibrationPresenter
    public void legsCalibrationContinueButtonPressed() {
    }

    @Override // com.bq.zowi.presenters.interactive.settings.CalibrationPresenter
    public void feetCalibrationContinueButtonPressed() {
    }

    @Override // com.bq.zowi.presenters.interactive.settings.CalibrationPresenter
    public void checkCalibrationTestMovementButtonPressed() {
        this.sendCommandToZowiInteractor.sendCommandToZowi(new CalibrationCommand(CalibrationCommand.CALIBRATE_TRIM, this.trimLeftLegYL, this.trimRightLegYR, this.trimLeftFootRL, this.trimRightFootRR)).flatMap(new Func1<Void, Single<Void>>() { // from class: com.bq.zowi.presenters.interactive.settings.CalibrationPresenterImpl.2
            @Override // rx.functions.Func1
            public Single<Void> call(Void aVoid) {
                return CalibrationPresenterImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(new AnimationCommand(Command.Action.VICTORY));
            }
        }).subscribe(new CommandSingleSubscriber());
    }

    @Override // com.bq.zowi.presenters.interactive.settings.CalibrationPresenter
    public void calibrationConfirmedButtonPressed() {
        this.sendCommandToZowiInteractor.sendCommandToZowi(new CalibrationCommand(CalibrationCommand.CALIBRATE_TRIM, this.trimLeftLegYL, this.trimRightLegYR, this.trimLeftFootRL, this.trimRightFootRR)).flatMap(new Func1<Void, Single<Void>>() { // from class: com.bq.zowi.presenters.interactive.settings.CalibrationPresenterImpl.3
            @Override // rx.functions.Func1
            public Single<Void> call(Void aVoid) {
                ((CalibrationWireframe) CalibrationPresenterImpl.this.getWireframe()).presentHome();
                return CalibrationPresenterImpl.this.sendCommandToZowiInteractor.sendCommandToZowi(new AnimationCommand(Command.Action.VICTORY));
            }
        }).subscribe(new CommandSingleSubscriber());
    }

    @Override // com.bq.zowi.presenters.interactive.settings.CalibrationPresenter
    public void legsCalibrationLeftIncreaseButtonPressed() {
        if (this.trimLeftLegYL < 30) {
            this.trimLeftLegYL++;
            ((CalibrationView) getView()).showLeftLegTrimValue(this.trimLeftLegYL);
            if (!isCalibrationChangeBlocked()) {
                calibrationParametersChanged();
            }
        }
    }

    @Override // com.bq.zowi.presenters.interactive.settings.CalibrationPresenter
    public void legsCalibrationLeftDecreaseButtonPressed() {
        if (this.trimLeftLegYL > -30) {
            this.trimLeftLegYL--;
            ((CalibrationView) getView()).showLeftLegTrimValue(this.trimLeftLegYL);
            if (!isCalibrationChangeBlocked()) {
                calibrationParametersChanged();
            }
        }
    }

    @Override // com.bq.zowi.presenters.interactive.settings.CalibrationPresenter
    public void legsCalibrationRightIncreaseButtonPressed() {
        if (this.trimRightLegYR < 30) {
            this.trimRightLegYR++;
            ((CalibrationView) getView()).showRightLegTrimValue(this.trimRightLegYR);
            if (!isCalibrationChangeBlocked()) {
                calibrationParametersChanged();
            }
        }
    }

    @Override // com.bq.zowi.presenters.interactive.settings.CalibrationPresenter
    public void legsCalibrationRightDecreaseButtonPressed() {
        if (this.trimRightLegYR > -30) {
            this.trimRightLegYR--;
            ((CalibrationView) getView()).showRightLegTrimValue(this.trimRightLegYR);
            if (!isCalibrationChangeBlocked()) {
                calibrationParametersChanged();
            }
        }
    }

    @Override // com.bq.zowi.presenters.interactive.settings.CalibrationPresenter
    public void feetCalibrationLeftIncreaseButtonPressed() {
        if (this.trimLeftFootRL < 30) {
            this.trimLeftFootRL++;
            ((CalibrationView) getView()).showLeftFootTrimValue(this.trimLeftFootRL);
            if (!isCalibrationChangeBlocked()) {
                calibrationParametersChanged();
            }
        }
    }

    @Override // com.bq.zowi.presenters.interactive.settings.CalibrationPresenter
    public void feetCalibrationLeftDecreaseButtonPressed() {
        if (this.trimLeftFootRL > -30) {
            this.trimLeftFootRL--;
            ((CalibrationView) getView()).showLeftFootTrimValue(this.trimLeftFootRL);
            if (!isCalibrationChangeBlocked()) {
                calibrationParametersChanged();
            }
        }
    }

    @Override // com.bq.zowi.presenters.interactive.settings.CalibrationPresenter
    public void feetCalibrationRightIncreaseButtonPressed() {
        if (this.trimRightFootRR < 30) {
            this.trimRightFootRR++;
            ((CalibrationView) getView()).showRightFootTrimValue(this.trimRightFootRR);
            if (!isCalibrationChangeBlocked()) {
                calibrationParametersChanged();
            }
        }
    }

    @Override // com.bq.zowi.presenters.interactive.settings.CalibrationPresenter
    public void feetCalibrationRightDecreaseButtonPressed() {
        if (this.trimRightFootRR > -30) {
            this.trimRightFootRR--;
            ((CalibrationView) getView()).showRightFootTrimValue(this.trimRightFootRR);
            if (!isCalibrationChangeBlocked()) {
                calibrationParametersChanged();
            }
        }
    }

    private boolean isCalibrationChangeBlocked() {
        long currentTime = new Date().getTime();
        if (this.lastCalibrationChangeTime != -1 && currentTime - this.lastCalibrationChangeTime <= MIN_TIME_BETWEEN_CALIBRATION_CHANGES) {
            return true;
        }
        this.lastCalibrationChangeTime = currentTime;
        return false;
    }
}
