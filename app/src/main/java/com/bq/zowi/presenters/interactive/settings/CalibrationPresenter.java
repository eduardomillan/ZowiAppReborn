package com.bq.zowi.presenters.interactive.settings;

import com.bq.zowi.presenters.interactive.InteractiveBasePresenter;
import com.bq.zowi.views.interactive.settings.CalibrationView;
import com.bq.zowi.wireframes.settings.CalibrationWireframe;

/* JADX INFO: loaded from: classes.dex */
public interface CalibrationPresenter extends InteractiveBasePresenter<CalibrationView, CalibrationWireframe> {
    void calibrationConfirmedButtonPressed();

    void calibrationParametersChanged();

    void checkCalibrationTestMovementButtonPressed();

    void feetCalibrationContinueButtonPressed();

    void feetCalibrationLeftDecreaseButtonPressed();

    void feetCalibrationLeftIncreaseButtonPressed();

    void feetCalibrationRightDecreaseButtonPressed();

    void feetCalibrationRightIncreaseButtonPressed();

    void legsCalibrationContinueButtonPressed();

    void legsCalibrationLeftDecreaseButtonPressed();

    void legsCalibrationLeftIncreaseButtonPressed();

    void legsCalibrationRightDecreaseButtonPressed();

    void legsCalibrationRightIncreaseButtonPressed();

    void warningCancelButtonPressed();

    void warningContinueButtonPressed();
}
