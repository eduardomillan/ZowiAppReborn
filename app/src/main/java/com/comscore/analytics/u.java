package com.comscore.analytics;

/* JADX INFO: loaded from: classes.dex */
class u implements Runnable {
    final /* synthetic */ Core a;

    u(Core core) {
        this.a = core;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.a.a.flush();
    }
}
