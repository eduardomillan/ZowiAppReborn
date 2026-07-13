package com.bq.zowi.bridge;

import com.bq.zowi.interactors.ForgetZowiInteractor;
import rx.Single;

/**
 * Bridge from core (RxJava2) ForgetZowiInteractor to old (RxJava1) interface.
 * Converts {@code Completable} to {@code Single&lt;Void&gt;}.
 */
public class ForgetZowiInteractorBridge implements com.bq.zowi.interactors.ForgetZowiInteractor {
    private final com.bq.zowi.usecases.ForgetZowiInteractor core;

    public ForgetZowiInteractorBridge(com.bq.zowi.usecases.ForgetZowiInteractor core) {
        this.core = core;
    }

    @Override
    public Single<Void> forgetZowi() {
        return RxBridge.toRxSingleVoid(core.forgetZowi());
    }
}
