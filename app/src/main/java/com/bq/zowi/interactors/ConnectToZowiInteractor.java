package com.bq.zowi.interactors;

import com.bq.zowi.models.ConnectionSuccessData;
import rx.Single;

/* JADX INFO: loaded from: classes.dex */
public interface ConnectToZowiInteractor {
    Single<Void> connectToZowi(String str, boolean z);

    Single<ConnectionSuccessData> connectToZowiAndRetrieveData(String str);

    Single<Void> disconnectFromZowi(String str);
}
