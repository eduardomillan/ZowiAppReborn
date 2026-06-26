package com.comscore.streaming;

/* JADX INFO: loaded from: classes.dex */
class c implements Runnable {
    final /* synthetic */ StreamSense a;

    c(StreamSense streamSense) {
        this.a = streamSense;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.a.b();
    }
}
