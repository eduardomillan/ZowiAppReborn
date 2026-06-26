package com.comscore.analytics;

/* JADX INFO: loaded from: classes.dex */
class ae implements Runnable {
    final /* synthetic */ Core a;

    ae(Core core) {
        this.a = core;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.a.a();
        this.a.l();
    }
}
