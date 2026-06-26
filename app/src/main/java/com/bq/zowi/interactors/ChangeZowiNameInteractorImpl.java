package com.bq.zowi.interactors;

import com.bq.zowi.models.ZowiName;
import com.bq.zowi.models.commands.NameSetCommand;
import rx.Single;

/* JADX INFO: loaded from: classes.dex */
public class ChangeZowiNameInteractorImpl implements ChangeZowiNameInteractor {
    private final SendCommandToZowiInteractor sendCommandToZowiInteractor;

    public ChangeZowiNameInteractorImpl(SendCommandToZowiInteractor sendCommandToZowiInteractor) {
        this.sendCommandToZowiInteractor = sendCommandToZowiInteractor;
    }

    @Override // com.bq.zowi.interactors.ChangeZowiNameInteractor
    public Single<Void> changeZowiName(String name) {
        return this.sendCommandToZowiInteractor.sendCommandToZowi(new NameSetCommand(name));
    }

    @Override // com.bq.zowi.interactors.ChangeZowiNameInteractor
    public Single<Void> resetZowiNameToFactory() {
        return this.sendCommandToZowiInteractor.sendCommandToZowi(new NameSetCommand(ZowiName.getFactoryName()));
    }
}
