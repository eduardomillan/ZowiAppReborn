package com.comscore.analytics;

/* JADX INFO: loaded from: classes.dex */
class x implements Runnable {
    final /* synthetic */ Core a;

    x(Core core) {
        this.a = core;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.a.update();
    }
}
