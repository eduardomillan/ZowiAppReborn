package com.comscore.analytics;

import com.comscore.utils.Date;

/* JADX INFO: loaded from: classes.dex */
class ac implements Runnable {
    final /* synthetic */ Core a;

    ac(Core core) {
        this.a = core;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.a.z()) {
            return;
        }
        if (this.a.Q < 0) {
            this.a.Q = 0;
        }
        if (this.a.R < 0) {
            this.a.R = 0;
        }
        this.a.W = Date.unixTime();
        this.a.V++;
        if (this.a.L != SessionState.ACTIVE_USER) {
            this.a.n();
        } else {
            this.a.o();
        }
    }
}
