package com.comscore.analytics;

/* JADX INFO: loaded from: classes.dex */
class o implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ Core b;

    o(Core core, int i) {
        this.b = core;
        this.a = i;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.a.setCacheMaxBatchFiles(this.a);
    }
}
