package com.comscore.streaming;

/* JADX INFO: loaded from: classes.dex */
class b implements Runnable {
    final /* synthetic */ StreamSense a;

    b(StreamSense streamSense) {
        this.a = streamSense;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.a.a();
    }
}
