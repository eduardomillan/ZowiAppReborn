package com.bq.zowi.bridge;

import com.bq.zowi.controllers.AppController;
import rx.Single;

public class AppControllerBridge implements AppController {
    private final com.bq.zowi.api.AppController core;

    public AppControllerBridge(com.bq.zowi.api.AppController core) {
        this.core = core;
    }

    @Override
    public Single<Integer> getDaysOfUse() {
        return RxBridge.toRxSingle(core.getDaysOfUse());
    }

    @Override
    public Single<Boolean> isFirstUsage() {
        return RxBridge.toRxSingle(core.isFirstUsage());
    }

    @Override
    public Single<Void> logAppStarted() {
        return RxBridge.toRxSingle(core.logAppStarted());
    }

    @Override
    public Single<Void> resetAppLogs() {
        return RxBridge.toRxSingle(core.resetAppLogs());
    }
}
