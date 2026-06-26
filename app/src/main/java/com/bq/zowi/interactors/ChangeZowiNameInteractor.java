package com.bq.zowi.interactors;

import rx.Single;

/* JADX INFO: loaded from: classes.dex */
public interface ChangeZowiNameInteractor {
    Single<Void> changeZowiName(String str);

    Single<Void> resetZowiNameToFactory();
}
