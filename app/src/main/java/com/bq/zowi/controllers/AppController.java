package com.bq.zowi.controllers;

import rx.Single;

/* JADX INFO: loaded from: classes.dex */
public interface AppController {
    Single<Integer> getDaysOfUse();

    Single<Boolean> isFirstUsage();

    Single<Void> logAppStarted();

    Single<Void> resetAppLogs();
}
