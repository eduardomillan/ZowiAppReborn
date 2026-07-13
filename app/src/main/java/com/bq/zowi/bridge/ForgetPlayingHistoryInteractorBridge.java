package com.bq.zowi.bridge;

import com.bq.zowi.interactors.ForgetPlayingHistoryInteractor;
import rx.Single;

/**
 * Bridge from core (RxJava2) ForgetPlayingHistoryInteractor to old (RxJava1) interface.
 * Converts {@code Completable} to {@code Single&lt;Void&gt;}.
 */
public class ForgetPlayingHistoryInteractorBridge implements com.bq.zowi.interactors.ForgetPlayingHistoryInteractor {
    private final com.bq.zowi.usecases.ForgetPlayingHistoryInteractor core;

    public ForgetPlayingHistoryInteractorBridge(com.bq.zowi.usecases.ForgetPlayingHistoryInteractor core) {
        this.core = core;
    }

    @Override
    public Single<Void> forgetPlayingHistory() {
        return RxBridge.toRxSingleVoid(core.forgetPlayingHistory());
    }
}
