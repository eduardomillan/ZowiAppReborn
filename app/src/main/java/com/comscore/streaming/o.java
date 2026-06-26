package com.comscore.streaming;

import android.media.MediaPlayer;

/* JADX INFO: loaded from: classes.dex */
class o implements MediaPlayer.OnCompletionListener {
    final /* synthetic */ StreamSenseVideoView a;

    o(StreamSenseVideoView streamSenseVideoView) {
        this.a = streamSenseVideoView;
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mediaPlayer) {
        this.a.a(mediaPlayer);
        if (this.a.n != null) {
            this.a.n.onCompletion(mediaPlayer);
        }
    }
}
