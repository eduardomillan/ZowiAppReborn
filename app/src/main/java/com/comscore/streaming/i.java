package com.comscore.streaming;

import android.media.MediaPlayer;

/* JADX INFO: loaded from: classes.dex */
class i implements MediaPlayer.OnSeekCompleteListener {
    final /* synthetic */ StreamSenseMediaPlayer a;

    i(StreamSenseMediaPlayer streamSenseMediaPlayer) {
        this.a = streamSenseMediaPlayer;
    }

    @Override // android.media.MediaPlayer.OnSeekCompleteListener
    public void onSeekComplete(MediaPlayer mediaPlayer) {
        if (this.a.n) {
            this.a.n = false;
            this.a.b(mediaPlayer);
        }
        if (this.a.t != null) {
            this.a.t.onSeekComplete(mediaPlayer);
        }
    }
}
