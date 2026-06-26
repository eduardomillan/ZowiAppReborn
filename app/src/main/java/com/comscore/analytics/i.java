package com.comscore.analytics;

import com.comscore.utils.TransmissionMode;

/* JADX INFO: loaded from: classes.dex */
class i implements Runnable {
    final /* synthetic */ TransmissionMode a;
    final /* synthetic */ Core b;

    i(Core core, TransmissionMode transmissionMode) {
        this.b = core;
        this.a = transmissionMode;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.b(this.a);
    }
}
