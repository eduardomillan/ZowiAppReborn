package com.comscore.analytics;

/* JADX INFO: loaded from: classes.dex */
class s implements Runnable {
    final /* synthetic */ long a;
    final /* synthetic */ Core b;

    s(Core core, long j) {
        this.b = core;
        this.a = j;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.af = this.a;
        if (this.b.d != null) {
            this.b.d.update();
        }
    }
}
