package com.bq.zowi.bridge;

import com.bq.zowi.interactors.MeasureZowiBatteryLevelInteractor;
import rx.Single;

/**
 * Bridge from core (RxJava2) MeasureZowiBatteryLevelInteractor to old (RxJava1) interface.
 * Converts {@code Single&lt;Boolean&gt;} between RxJava versions.
 */
public class MeasureZowiBatteryLevelInteractorBridge implements com.bq.zowi.interactors.MeasureZowiBatteryLevelInteractor {
    private final com.bq.zowi.usecases.MeasureZowiBatteryLevelInteractor core;

    public MeasureZowiBatteryLevelInteractorBridge(com.bq.zowi.usecases.MeasureZowiBatteryLevelInteractor core) {
        this.core = core;
    }

    @Override
    public Single<Boolean> manageZowiBatteryLevel(float batteryLevel) {
        return RxBridge.toRxSingle(core.manageZowiBatteryLevel(batteryLevel));
    }

    @Override
    public Single<Boolean> measureAndManageZowiBatteryLevel() {
        return RxBridge.toRxSingle(core.measureAndManageZowiBatteryLevel());
    }
}
