package com.comscore.analytics;

/* JADX INFO: loaded from: classes.dex */
class z implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ Core b;

    z(Core core, boolean z) {
        this.b = core;
        this.a = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.a && !this.b.an) {
            this.b.an = true;
            this.b.setErrorHandlingEnabled(this.b.ao);
            this.b.reset();
            this.b.getConnectivityReceiver().start();
            this.b.getKeepAlive().start(3000);
            return;
        }
        if (this.a || !this.b.an) {
            return;
        }
        this.b.an = false;
        this.b.ao = this.b.ag;
        if (Thread.getDefaultUncaughtExceptionHandler() != this.b.ah) {
            Thread.setDefaultUncaughtExceptionHandler(this.b.ah);
        }
        this.b.getConnectivityReceiver().stop();
        this.b.getKeepAlive().stop();
        this.b.getOfflineCache().clear();
        this.b.f.removeAllEnqueuedTasks();
    }
}
