package com.comscore.analytics;

import com.comscore.utils.Constants;

/* JADX INFO: loaded from: classes.dex */
class k implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ Core b;

    k(Core core, boolean z) {
        this.b = core;
        this.a = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        Constants.DEBUG = this.a;
    }
}
