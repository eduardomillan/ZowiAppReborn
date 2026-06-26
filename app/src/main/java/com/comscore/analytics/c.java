package com.comscore.analytics;

/* JADX INFO: loaded from: classes.dex */
class c implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Core b;

    c(Core core, String str) {
        this.b = core;
        this.a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.b(this.a);
    }
}
