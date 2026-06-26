package com.comscore.analytics;

/* JADX INFO: loaded from: classes.dex */
class j implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ Core b;

    j(Core core, boolean z) {
        this.b = core;
        this.a = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.b.isSecure() != this.a) {
            this.b.ai = this.a;
        }
    }
}
