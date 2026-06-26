package com.comscore.streaming;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.VideoView;
import com.comscore.utils.CSLog;
import java.util.HashMap;
import java.util.Timer;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class StreamSenseVideoView extends VideoView {
    private final String a;
    private final boolean b;
    private final int c;
    private final int d;
    private long e;
    private StreamSense f;
    private String g;
    private long h;
    private long i;
    private String j;
    private boolean k;
    private Timer l;
    private Timer m;
    private MediaPlayer.OnCompletionListener n;
    private final MediaPlayer.OnCompletionListener o;

    public StreamSenseVideoView(Context context) {
        super(context);
        this.a = "StreamSenseVideoView";
        this.b = true;
        this.c = 500;
        this.d = 500;
        this.e = -1L;
        this.f = null;
        this.g = "0x0";
        this.h = 0L;
        this.i = -1L;
        this.k = false;
        this.l = null;
        this.m = null;
        this.n = null;
        this.o = new o(this);
        h();
    }

    public StreamSenseVideoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = "StreamSenseVideoView";
        this.b = true;
        this.c = 500;
        this.d = 500;
        this.e = -1L;
        this.f = null;
        this.g = "0x0";
        this.h = 0L;
        this.i = -1L;
        this.k = false;
        this.l = null;
        this.m = null;
        this.n = null;
        this.o = new o(this);
        h();
    }

    public StreamSenseVideoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = "StreamSenseVideoView";
        this.b = true;
        this.c = 500;
        this.d = 500;
        this.e = -1L;
        this.f = null;
        this.g = "0x0";
        this.h = 0L;
        this.i = -1L;
        this.k = false;
        this.l = null;
        this.m = null;
        this.n = null;
        this.o = new o(this);
        h();
    }

    static /* synthetic */ long a(StreamSenseVideoView streamSenseVideoView, long j) {
        long j2 = streamSenseVideoView.h + j;
        streamSenseVideoView.h = j2;
        return j2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(MediaPlayer mediaPlayer) {
        CSLog.d("StreamSenseVideoView", "onPlaybackCompleted");
        c(i(), getCurrentPlayerSafePosition());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(HashMap<String, String> map) {
        f();
        if (this.l == null) {
            long currentPlayerSafePosition = getCurrentPlayerSafePosition();
            CSLog.d("StreamSenseVideoView", "startStartTimer:" + currentPlayerSafePosition);
            this.l = new Timer();
            this.l.schedule(new m(this, currentPlayerSafePosition, map), 500L);
        }
    }

    private void a(HashMap<String, String> map, long j) {
        f();
        d();
        if (this.f != null) {
            this.f.notify(StreamSenseEventType.END, map, j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a() {
        try {
            return isPlaying();
        } catch (IllegalStateException e) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(HashMap<String, String> map, long j) {
        this.k = false;
        d();
        if (this.f != null) {
            this.f.notify(StreamSenseEventType.PLAY, map, j);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b() {
        return this.k;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        CSLog.d("StreamSenseVideoView", "onPauseForBuffering");
        this.k = true;
        this.i = System.currentTimeMillis();
        if (a()) {
            c(i(), getCurrentPlayerSafePosition());
            e();
        }
    }

    private void c(HashMap<String, String> map, long j) {
        if (d() || this.f == null) {
            return;
        }
        this.f.notify(StreamSenseEventType.PAUSE, map, j);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized boolean d() {
        boolean z;
        if (this.l != null) {
            CSLog.d("StreamSenseVideoView", "cancelStartTimer");
            this.l.cancel();
            this.l = null;
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    private void e() {
        a(i());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized boolean f() {
        boolean z;
        CSLog.d("StreamSenseVideoView", "cancelPlayingPollTimer()");
        if (this.m != null) {
            this.m.cancel();
            this.m = null;
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void g() {
        if (this.m == null) {
            CSLog.d("StreamSenseVideoView", "startPlayingPollTimer");
            long currentPlayerSafePosition = getCurrentPlayerSafePosition();
            this.m = new Timer();
            this.m.schedule(new n(this, currentPlayerSafePosition), 250L);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long getCurrentPlayerSafePosition() {
        try {
            return getCurrentPosition();
        } catch (IllegalStateException e) {
            CSLog.e("StreamSenseVideoView", "getCurrentSafePlayerPosition");
            return 0L;
        }
    }

    private HashMap<String, String> getPlayerMetadata() {
        HashMap<String, String> map = new HashMap<>();
        if (this.e <= 0) {
            this.e = getDuration();
        }
        map.put("ns_st_cl", String.valueOf(this.e));
        if (this.g == null || this.g.equals("0x0")) {
            this.g = getWidth() + "x" + getHeight();
        }
        map.put("ns_st_cs", this.g);
        map.put("ns_st_cu", this.j);
        map.put("ns_st_mp", StreamSenseVideoView.class.getSimpleName());
        map.put("ns_st_mv", Integer.toString(Build.VERSION.SDK_INT));
        return map;
    }

    private void h() {
        super.setOnCompletionListener(this.o);
    }

    private HashMap<String, String> i() {
        HashMap<String, String> map = new HashMap<>();
        map.put("ns_ts", String.valueOf(System.currentTimeMillis()));
        map.putAll(getPlayerMetadata());
        return map;
    }

    @Override // android.widget.VideoView, android.view.SurfaceView, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        CSLog.d("StreamSenseVideoView", "onDetachedFromWindow");
        a(i(), getCurrentPlayerSafePosition());
    }

    @Override // android.widget.VideoView, android.widget.MediaController.MediaPlayerControl
    public void pause() {
        super.pause();
        CSLog.d("StreamSenseVideoView", "pause");
        f();
        if (this.k) {
            this.h += System.currentTimeMillis() - this.i;
        }
        this.k = false;
        this.i = -1L;
        c(i(), getCurrentPlayerSafePosition());
    }

    @Override // android.widget.VideoView
    @TargetApi(8)
    public void resume() {
        if (Build.VERSION.SDK_INT >= 8) {
            super.resume();
        }
        CSLog.d("StreamSenseVideoView", "resume");
        b(i(), getCurrentPlayerSafePosition());
    }

    @Override // android.widget.VideoView, android.widget.MediaController.MediaPlayerControl
    public void seekTo(int i) {
        super.seekTo(i);
        CSLog.d("StreamSenseVideoView", "seekTo:" + i);
        if (a()) {
            f();
            d();
            c(i(), -1L);
            e();
        }
    }

    @Override // android.widget.VideoView
    public void setOnCompletionListener(MediaPlayer.OnCompletionListener onCompletionListener) {
        super.setOnCompletionListener(this.o);
        this.n = onCompletionListener;
    }

    public void setStreamSense(StreamSense streamSense) {
        this.f = streamSense;
        this.f.setLabel("ns_st_pv", "4.1307.02");
    }

    @Override // android.widget.VideoView
    public void setVideoPath(String str) {
        super.setVideoPath(str);
        this.j = str;
    }

    @Override // android.widget.VideoView
    public void setVideoURI(Uri uri) {
        super.setVideoURI(uri);
        this.j = uri.toString();
    }

    @Override // android.widget.VideoView, android.widget.MediaController.MediaPlayerControl
    public void start() {
        super.start();
        if (this.l == null) {
            CSLog.d("StreamSenseVideoView", com.comscore.utils.Constants.DEFAULT_START_PAGE_NAME);
            e();
        }
    }

    @Override // android.widget.VideoView
    public void stopPlayback() {
        super.stopPlayback();
        CSLog.d("StreamSenseVideoView", "stopPlayback");
        a(i(), getCurrentPlayerSafePosition());
    }
}
