package com.comscore.analytics;

/* JADX INFO: loaded from: classes.dex */
class b implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ Core b;

    b(Core core, boolean z) {
        this.b = core;
        this.a = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.ae = this.a;
    }
}
