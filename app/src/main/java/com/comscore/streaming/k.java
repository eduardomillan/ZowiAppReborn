package com.comscore.streaming;

import java.util.HashMap;
import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
class k extends TimerTask {
    final /* synthetic */ long a;
    final /* synthetic */ HashMap b;
    final /* synthetic */ StreamSenseMediaPlayer c;

    k(StreamSenseMediaPlayer streamSenseMediaPlayer, long j, HashMap map) {
        this.c = streamSenseMediaPlayer;
        this.a = j;
        this.b = map;
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
        if (this.c.m() - this.a < 500) {
            this.c.d();
            this.c.a((HashMap<String, String>) this.b);
        } else {
            this.c.d();
            this.c.f((HashMap<String, String>) this.b);
            this.c.j();
        }
    }
}
