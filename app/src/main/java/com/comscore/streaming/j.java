package com.comscore.streaming;

import android.media.MediaPlayer;

/* JADX INFO: loaded from: classes.dex */
class j implements MediaPlayer.OnPreparedListener {
    final /* synthetic */ StreamSenseMediaPlayer a;

    j(StreamSenseMediaPlayer streamSenseMediaPlayer) {
        this.a = streamSenseMediaPlayer;
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mediaPlayer) {
        this.a.k = String.valueOf(this.a.getCurrentPosition());
        if (this.a.v != null) {
            this.a.v.onPrepared(mediaPlayer);
        }
    }
}
