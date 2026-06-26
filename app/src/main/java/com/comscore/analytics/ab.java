package com.comscore.analytics;

/* JADX INFO: loaded from: classes.dex */
class ab implements Runnable {
    final /* synthetic */ Core a;

    ab(Core core) {
        this.a = core;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (!this.a.z() && this.a.z.get() > 0) {
            this.a.z.getAndDecrement();
            this.a.n();
        }
    }
}
