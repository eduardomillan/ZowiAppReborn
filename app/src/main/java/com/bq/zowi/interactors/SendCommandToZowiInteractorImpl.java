package com.bq.zowi.interactors;

import com.bq.zowi.controllers.BTConnectionController;
import com.bq.zowi.models.commands.Command;
import com.bq.zowi.utils.Grove;
import rx.Single;
import rx.SingleSubscriber;

/* JADX INFO: loaded from: classes.dex */
public class SendCommandToZowiInteractorImpl implements SendCommandToZowiInteractor {
    private BTConnectionController btConnectionController;

    public SendCommandToZowiInteractorImpl(BTConnectionController btConnectionController) {
        this.btConnectionController = btConnectionController;
    }

    @Override // com.bq.zowi.interactors.SendCommandToZowiInteractor
    public Single<Void> sendCommandToZowi(final Command command) {
        return Single.create(new Single.OnSubscribe<Void>() { // from class: com.bq.zowi.interactors.SendCommandToZowiInteractorImpl.1
            @Override // rx.functions.Action1
            public void call(SingleSubscriber<? super Void> singleSubscriber) {
                SendCommandToZowiInteractorImpl.this.btConnectionController.sendMessage(command.getCommandValue());
                Grove.d("Message sent: " + command.getCommandValue(), new Object[0]);
                singleSubscriber.onSuccess(null);
            }
        });
    }
}
