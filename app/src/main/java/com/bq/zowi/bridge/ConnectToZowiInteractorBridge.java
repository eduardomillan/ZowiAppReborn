package com.bq.zowi.bridge;

import com.bq.zowi.models.ConnectionSuccessData;
import com.bq.zowi.interactors.ConnectToZowiInteractor;
import rx.Single;

/**
 * Bridge from core (RxJava2) ConnectToZowiInteractor to old (RxJava1) interface.
 * Converts {@code Completable} to {@code Single&lt;Void&gt;} for void-returning methods.
 */
public class ConnectToZowiInteractorBridge implements com.bq.zowi.interactors.ConnectToZowiInteractor {
    private final com.bq.zowi.usecases.ConnectToZowiInteractor core;

    public ConnectToZowiInteractorBridge(com.bq.zowi.usecases.ConnectToZowiInteractor core) {
        this.core = core;
    }

    @Override
    public Single<Void> connectToZowi(String deviceAddress, boolean duplex) {
        return RxBridge.toRxSingleVoid(core.connectToZowi(deviceAddress, duplex));
    }

    @Override
    public Single<ConnectionSuccessData> connectToZowiAndRetrieveData(String deviceAddress) {
        return RxBridge.toRxSingle(core.connectToZowiAndRetrieveData(deviceAddress));
    }

    @Override
    public Single<Void> disconnectFromZowi(String deviceAddress) {
        return RxBridge.toRxSingleVoid(core.disconnectFromZowi(deviceAddress));
    }
}
