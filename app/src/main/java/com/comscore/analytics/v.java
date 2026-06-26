package com.comscore.analytics;

/* JADX INFO: loaded from: classes.dex */
class v implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ boolean b;
    final /* synthetic */ Core c;

    v(Core core, int i, boolean z) {
        this.c = core;
        this.a = i;
        this.b = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.c.a(this.a, this.b);
    }
}
