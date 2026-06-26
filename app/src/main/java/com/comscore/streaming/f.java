package com.comscore.streaming;

/* JADX INFO: loaded from: classes.dex */
abstract class f implements Runnable {
    final /* synthetic */ StreamSense d;

    private f(StreamSense streamSense) {
        this.d = streamSense;
    }

    /* synthetic */ f(StreamSense streamSense, a aVar) {
        this(streamSense);
    }

    public abstract StreamSenseState getNextState();
}
