package com.comscore.analytics;

import com.comscore.utils.TransmissionMode;

/* JADX INFO: loaded from: classes.dex */
class h implements Runnable {
    final /* synthetic */ TransmissionMode a;
    final /* synthetic */ Core b;

    h(Core core, TransmissionMode transmissionMode) {
        this.b = core;
        this.a = transmissionMode;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.a(this.a);
    }
}
