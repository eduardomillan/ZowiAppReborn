package com.comscore.analytics;

/* JADX INFO: loaded from: classes.dex */
class f implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ String b;
    final /* synthetic */ Core c;

    f(Core core, String str, String str2) {
        this.c = core;
        this.a = str;
        this.b = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.c.b(this.a, this.b);
    }
}
