package com.comscore.analytics;

/* JADX INFO: loaded from: classes.dex */
class ad implements Runnable {
    final /* synthetic */ ApplicationState a;
    final /* synthetic */ ApplicationState b;
    final /* synthetic */ SessionState c;
    final /* synthetic */ SessionState d;
    final /* synthetic */ Core e;

    ad(Core core, ApplicationState applicationState, ApplicationState applicationState2, SessionState sessionState, SessionState sessionState2) {
        this.e = core;
        this.a = applicationState;
        this.b = applicationState2;
        this.c = sessionState;
        this.d = sessionState2;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean z;
        boolean z2 = true;
        synchronized (this.e) {
            if (this.a != this.b) {
                this.e.a(this.e.y);
                this.e.b(this.b);
                this.e.s();
                this.e.y = this.b;
                z = true;
            } else {
                z = false;
            }
            if (this.c != this.d) {
                this.e.a(this.e.L);
                this.e.b(this.d);
                this.e.t();
                this.e.L = this.d;
            } else {
                z2 = false;
            }
            if (z) {
                this.e.a(this.a, this.e.y);
            }
            if (z2) {
                this.e.a(this.c, this.e.L);
            }
        }
    }
}
