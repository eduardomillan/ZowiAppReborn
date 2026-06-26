package com.bq.zowi.interactors;

import com.bq.zowi.controllers.ZowiDataController;
import java.util.concurrent.TimeUnit;
import rx.Single;
import rx.functions.Func1;

/* JADX INFO: loaded from: classes.dex */
public class MeasureZowiBatteryLevelInteractorImpl implements MeasureZowiBatteryLevelInteractor {
    private static final int RECEIVE_BATTERY_LEVEL_TIMEOUT_MILLIS = 500;
    private SendCommandToZowiInteractor sendCommandToZowiInteractor;
    private ZowiDataController zowiDataController;

    public MeasureZowiBatteryLevelInteractorImpl(SendCommandToZowiInteractor sendCommandToZowiInteractor, ZowiDataController zowiDataController) {
        this.sendCommandToZowiInteractor = sendCommandToZowiInteractor;
        this.zowiDataController = zowiDataController;
    }

    @Override // com.bq.zowi.interactors.MeasureZowiBatteryLevelInteractor
    public Single<Boolean> measureAndManageZowiBatteryLevel() {
        return this.zowiDataController.getBatteryLevel().flatMap(new Func1<Float, Single<? extends Boolean>>() { // from class: com.bq.zowi.interactors.MeasureZowiBatteryLevelInteractorImpl.1
            @Override // rx.functions.Func1
            public Single<? extends Boolean> call(Float receivedBatteryLevel) {
                return MeasureZowiBatteryLevelInteractorImpl.this.manageZowiBatteryLevel(receivedBatteryLevel.floatValue());
            }
        }).timeout(500L, TimeUnit.MILLISECONDS, Single.just(true));
    }

    @Override // com.bq.zowi.interactors.MeasureZowiBatteryLevelInteractor
    public Single<Boolean> manageZowiBatteryLevel(float batteryLevel) {
        return batteryLevel >= 50.0f ? Single.just(true) : Single.just(false);
    }
}
