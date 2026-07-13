package com.bq.zowi.bridge;

import com.bq.zowi.interactors.ChangeZowiNameInteractor;
import rx.Single;

/**
 * Bridge from core (RxJava2) ChangeZowiNameInteractor to old (RxJava1) interface.
 * Converts {@code Completable} to {@code Single&lt;Void&gt;}.
 */
public class ChangeZowiNameInteractorBridge implements com.bq.zowi.interactors.ChangeZowiNameInteractor {
    private final com.bq.zowi.usecases.ChangeZowiNameInteractor core;

    public ChangeZowiNameInteractorBridge(com.bq.zowi.usecases.ChangeZowiNameInteractor core) {
        this.core = core;
    }

    @Override
    public Single<Void> changeZowiName(String name) {
        return RxBridge.toRxSingleVoid(core.changeZowiName(name));
    }

    @Override
    public Single<Void> resetZowiNameToFactory() {
        return RxBridge.toRxSingleVoid(core.resetZowiNameToFactory());
    }
}
