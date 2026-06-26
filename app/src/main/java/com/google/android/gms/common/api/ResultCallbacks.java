package com.google.android.gms.common.api;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.internal.zzlc;

/* JADX INFO: loaded from: classes.dex */
public abstract class ResultCallbacks<R extends Result> implements ResultCallback<R> {
    public abstract void onFailure(Status status);

    @Override // com.google.android.gms.common.api.ResultCallback
    public final void onResult(R result) {
        Status status = result.getStatus();
        if (status.isSuccess()) {
            onSuccess(result);
        } else {
            onFailure(status);
            zzlc.zzd(result);
        }
    }

    public abstract void onSuccess(R r);
}
