package com.comscore.analytics;

/* JADX INFO: loaded from: classes.dex */
class af implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Core b;

    af(Core core, String str) {
        this.b = core;
        this.a = str;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.a(this.a);
    }
}
