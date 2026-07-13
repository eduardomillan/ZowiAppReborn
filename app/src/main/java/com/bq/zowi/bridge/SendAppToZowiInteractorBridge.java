package com.bq.zowi.bridge;

import com.bq.zowi.interactors.SendAppToZowiInteractor;
import rx.Observable;

/**
 * Bridge from core (RxJava2) SendAppToZowiInteractor to old (RxJava1) interface.
 * Converts {@code Observable&lt;Integer&gt;} between RxJava versions.
 */
public class SendAppToZowiInteractorBridge implements com.bq.zowi.interactors.SendAppToZowiInteractor {
    private final com.bq.zowi.usecases.SendAppToZowiInteractor core;

    public SendAppToZowiInteractorBridge(com.bq.zowi.usecases.SendAppToZowiInteractor core) {
        this.core = core;
    }

    @Override
    public Observable<Integer> sendAppToZowi(String appHexPath) {
        return RxBridge.toRxObservable(core.sendAppToZowi(appHexPath));
    }
}
