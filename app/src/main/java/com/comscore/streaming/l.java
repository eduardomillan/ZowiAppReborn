package com.comscore.streaming;

import java.util.TimerTask;

/* JADX INFO: loaded from: classes.dex */
class l extends TimerTask {
    final /* synthetic */ long a;
    final /* synthetic */ StreamSenseMediaPlayer b;

    l(StreamSenseMediaPlayer streamSenseMediaPlayer, long j) {
        this.b = streamSenseMediaPlayer;
        this.a = j;
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
        if (this.b.y != null) {
            boolean z = this.a == this.b.m();
            if (this.b.n() && !this.b.isPlayerPausedForSeeking() && z && !this.b.isPlayerPausedForBuffering()) {
                this.b.a();
            } else if (this.b.n() && !this.b.isPlayerPausedForSeeking() && !z && this.b.isPlayerPausedForBuffering()) {
                this.b.b();
            }
            this.b.i();
            this.b.j();
        }
    }
}
