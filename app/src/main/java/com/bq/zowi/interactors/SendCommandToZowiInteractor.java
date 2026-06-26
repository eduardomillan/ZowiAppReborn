package com.bq.zowi.interactors;

import com.bq.zowi.models.commands.Command;
import rx.Single;

/* JADX INFO: loaded from: classes.dex */
public interface SendCommandToZowiInteractor {
    Single<Void> sendCommandToZowi(Command command);
}
