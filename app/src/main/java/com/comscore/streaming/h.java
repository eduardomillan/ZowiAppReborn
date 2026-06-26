package com.comscore.streaming;

import android.media.MediaPlayer;

/* JADX INFO: loaded from: classes.dex */
class h implements MediaPlayer.OnInfoListener {
    final /* synthetic */ StreamSenseMediaPlayer a;

    h(StreamSenseMediaPlayer streamSenseMediaPlayer) {
        this.a = streamSenseMediaPlayer;
    }

    @Override // android.media.MediaPlayer.OnInfoListener
    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
        if (i == 701 && !this.a.n && !this.a.m) {
            this.a.a();
        } else if (i == 702 && !this.a.n && this.a.m) {
            this.a.b();
        }
        if (this.a.r != null) {
            return this.a.r.onInfo(mediaPlayer, i, i2);
        }
        return true;
    }
}
