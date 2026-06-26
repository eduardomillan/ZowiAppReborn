package com.comscore.streaming;

/* JADX INFO: loaded from: classes.dex */
class a implements Runnable {
    final /* synthetic */ StreamSense a;

    a(StreamSense streamSense) {
        this.a = streamSense;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.a.j();
    }
}
