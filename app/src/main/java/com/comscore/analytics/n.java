package com.comscore.analytics;

/* JADX INFO: loaded from: classes.dex */
class n implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ Core b;

    n(Core core, int i) {
        this.b = core;
        this.a = i;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.a.setCacheMaxMeasurements(this.a);
    }
}
