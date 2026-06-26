package com.comscore.streaming;

import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
class n extends TimerTask {
    final /* synthetic */ long a;
    final /* synthetic */ StreamSenseVideoView b;

    n(StreamSenseVideoView streamSenseVideoView, long j) {
        this.b = streamSenseVideoView;
        this.a = j;
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
        if (this.b.m != null) {
            boolean z = this.a == this.b.getCurrentPlayerSafePosition();
            if (this.b.a() && z && !this.b.b()) {
                this.b.f();
                this.b.c();
            } else {
                this.b.f();
                this.b.g();
            }
        }
    }
}
