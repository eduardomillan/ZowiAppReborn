package com.bq.zowi.interactors;

import com.bq.zowi.controllers.BTConnectionController;
import com.bq.zowi.controllers.SessionController;
import rx.Single;
import rx.functions.Func1;

/* JADX INFO: loaded from: classes.dex */
public class ForgetZowiInteractorImpl implements ForgetZowiInteractor {
    private final ChangeZowiNameInteractor changeZowiNameInteractor;
    private final BTConnectionController connectionController;
    private final SessionController sessionController;

    public ForgetZowiInteractorImpl(ChangeZowiNameInteractor changeZowiNameInteractor, SessionController sessionController, BTConnectionController connectionController) {
        this.changeZowiNameInteractor = changeZowiNameInteractor;
        this.sessionController = sessionController;
        this.connectionController = connectionController;
    }

    @Override // com.bq.zowi.interactors.ForgetZowiInteractor
    public Single<Void> forgetZowi() {
        return this.changeZowiNameInteractor.resetZowiNameToFactory().map(new Func1<Void, Void>() { // from class: com.bq.zowi.interactors.ForgetZowiInteractorImpl.1
            @Override // rx.functions.Func1
            public Void call(Void aVoid) {
                ForgetZowiInteractorImpl.this.sessionController.resetActiveZowi();
                ForgetZowiInteractorImpl.this.connectionController.stopConnection();
                return null;
            }
        });
    }
}
