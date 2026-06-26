package com.comscore.streaming;

import android.media.MediaPlayer;

/* JADX INFO: loaded from: classes.dex */
class g implements MediaPlayer.OnCompletionListener {
    final /* synthetic */ StreamSenseMediaPlayer a;

    g(StreamSenseMediaPlayer streamSenseMediaPlayer) {
        this.a = streamSenseMediaPlayer;
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mediaPlayer) {
        this.a.a(mediaPlayer);
        if (this.a.p != null) {
            this.a.p.onCompletion(mediaPlayer);
        }
    }
}
