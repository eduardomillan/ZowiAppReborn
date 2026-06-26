package com.comscore.analytics;

/* JADX INFO: loaded from: classes.dex */
class g implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Core b;

    g(Core core, String str) {
        this.b = core;
        this.a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.d(this.a);
    }
}
