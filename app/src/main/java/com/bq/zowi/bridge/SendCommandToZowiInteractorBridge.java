package com.bq.zowi.bridge;

import com.bq.zowi.models.commands.Command;
import com.bq.zowi.interactors.SendCommandToZowiInteractor;
import rx.Single;

/**
 * Bridge from core (RxJava2) SendCommandToZowiInteractor to old (RxJava1) interface.
 * Converts {@code Completable} to {@code Single&lt;Void&gt;}.
 */
public class SendCommandToZowiInteractorBridge implements com.bq.zowi.interactors.SendCommandToZowiInteractor {
    private final com.bq.zowi.usecases.SendCommandToZowiInteractor core;

    public SendCommandToZowiInteractorBridge(com.bq.zowi.usecases.SendCommandToZowiInteractor core) {
        this.core = core;
    }

    @Override
    public Single<Void> sendCommandToZowi(Command command) {
        return RxBridge.toRxSingleVoid(core.sendCommandToZowi(command));
    }
}
