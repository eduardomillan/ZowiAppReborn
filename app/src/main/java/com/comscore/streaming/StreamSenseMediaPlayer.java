package com.comscore.streaming;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.HashMap;
import java.util.Timer;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class StreamSenseMediaPlayer extends MediaPlayer {
    Timer a;
    Timer b;
    private final String c = "local_file";
    private final int d = 500;
    private final boolean e;
    private final boolean f;
    private final boolean g;
    private final boolean h;
    private final int i;
    private StreamSense j;
    private String k;
    private String l;
    private boolean m;
    private boolean n;
    private String o;
    private MediaPlayer.OnCompletionListener p;
    private final MediaPlayer.OnCompletionListener q;
    private MediaPlayer.OnInfoListener r;
    private final MediaPlayer.OnInfoListener s;
    private MediaPlayer.OnSeekCompleteListener t;
    private final MediaPlayer.OnSeekCompleteListener u;
    private MediaPlayer.OnPreparedListener v;
    private final MediaPlayer.OnPreparedListener w;
    private Timer x;
    private Timer y;

    public StreamSenseMediaPlayer() {
        this.e = Build.VERSION.SDK_INT < 9;
        this.f = false;
        this.g = false;
        this.h = true;
        this.i = 500;
        this.j = null;
        this.k = "0";
        this.l = "0x0";
        this.m = false;
        this.n = false;
        this.p = null;
        this.q = new g(this);
        this.r = null;
        this.s = new h(this);
        this.t = null;
        this.u = new i(this);
        this.v = null;
        this.w = new j(this);
        this.x = null;
        this.a = null;
        this.b = null;
        this.y = null;
        c();
    }

    public StreamSenseMediaPlayer(boolean z) {
        this.e = Build.VERSION.SDK_INT < 9;
        this.f = false;
        this.g = false;
        this.h = true;
        this.i = 500;
        this.j = null;
        this.k = "0";
        this.l = "0x0";
        this.m = false;
        this.n = false;
        this.p = null;
        this.q = new g(this);
        this.r = null;
        this.s = new h(this);
        this.t = null;
        this.u = new i(this);
        this.v = null;
        this.w = new j(this);
        this.x = null;
        this.a = null;
        this.b = null;
        this.y = null;
        c();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        this.m = true;
        if (!n() || isPlayerPausedForSeeking()) {
            return;
        }
        k();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(MediaPlayer mediaPlayer) {
        l();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(HashMap<String, String> map) {
        long jM = m();
        f();
        boolean zG = g();
        if (this.x == null && !zG) {
            this.x = new Timer();
            this.x.schedule(new k(this, jM, map), 500L);
        }
    }

    private void a(HashMap<String, String> map, long j) {
        boolean zD = d();
        boolean zF = f();
        g();
        if (zD || zF || this.j == null) {
            return;
        }
        this.j.notify(StreamSenseEventType.PAUSE, map, j);
    }

    private synchronized void a(boolean z) {
        boolean z2 = this.a != null;
        d();
        boolean zG = g();
        if (!z2 && !zG) {
            f(o());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        this.m = false;
        if (!n() || isPlayerPausedForSeeking()) {
            return;
        }
        a(true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(MediaPlayer mediaPlayer) {
        if (n()) {
            a(true);
        }
    }

    private synchronized void b(HashMap<String, String> map) {
        boolean zD = d();
        boolean zF = f();
        if (!(this.b != null) && !zD && !zF) {
            d(map);
            d(map);
        }
    }

    private void c() {
        super.setOnCompletionListener(this.q);
        super.setOnInfoListener(this.s);
        super.setOnSeekCompleteListener(this.u);
        super.setOnPreparedListener(this.w);
    }

    private void c(HashMap<String, String> map) {
        d();
        f();
        g();
        if (this.j != null) {
            this.j.notify(StreamSenseEventType.BUFFER, map, m());
        }
    }

    private void d(HashMap<String, String> map) {
        a(map, m());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized boolean d() {
        boolean z;
        if (this.x != null) {
            this.x.cancel();
            this.x = null;
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    private void e() {
        a(o());
    }

    private void e(HashMap<String, String> map) {
        f();
        d();
        g();
        i();
        if (this.j != null) {
            this.j.notify(StreamSenseEventType.END, map, m());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f(HashMap<String, String> map) {
        d();
        if (g() || this.j == null) {
            return;
        }
        this.j.notify(StreamSenseEventType.PLAY, map, m());
    }

    private synchronized boolean f() {
        boolean z;
        if (this.a != null) {
            this.a.cancel();
            this.a = null;
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    private synchronized boolean g() {
        boolean z;
        if (this.b != null) {
            this.b.cancel();
            this.b = null;
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    private void h() {
        b(o());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized boolean i() {
        boolean z;
        if (this.y != null) {
            this.y.cancel();
            this.y = null;
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void j() {
        if (this.e && this.y == null) {
            long jM = m();
            this.y = new Timer();
            this.y.schedule(new l(this, jM), 250L);
        }
    }

    private void k() {
        c(o());
    }

    private void l() {
        e(o());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long m() {
        try {
            return getCurrentPosition();
        } catch (IllegalStateException e) {
            return 0L;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean n() {
        try {
            return isPlaying();
        } catch (IllegalStateException e) {
            return false;
        }
    }

    private HashMap<String, String> o() {
        HashMap<String, String> map = new HashMap<>();
        map.put("ns_ts", String.valueOf(System.currentTimeMillis()));
        map.putAll(p());
        return map;
    }

    private HashMap<String, String> p() {
        HashMap<String, String> map = new HashMap<>();
        map.put("ns_st_cl", this.k);
        map.put("ns_st_cs", this.l);
        map.put("ns_st_cu", this.o);
        map.put("ns_st_mp", StreamSenseMediaPlayer.class.getSimpleName());
        map.put("ns_st_mv", Integer.toString(Build.VERSION.SDK_INT));
        return map;
    }

    public boolean isPlayerPausedForBuffering() {
        return this.m;
    }

    public boolean isPlayerPausedForSeeking() {
        return this.n;
    }

    @Override // android.media.MediaPlayer
    public void pause() {
        super.pause();
        i();
        this.m = false;
        this.n = false;
        h();
    }

    @Override // android.media.MediaPlayer
    public void prepare() throws IOException {
        super.prepare();
        this.l = getVideoWidth() + "x" + getVideoHeight();
    }

    @Override // android.media.MediaPlayer
    public void prepareAsync() {
        super.prepareAsync();
        this.l = getVideoWidth() + "x" + getVideoHeight();
    }

    @Override // android.media.MediaPlayer
    public void release() {
        super.release();
        l();
    }

    @Override // android.media.MediaPlayer
    public void seekTo(int i) {
        super.seekTo(i);
        this.n = true;
        if (n()) {
            a(o(), m());
        }
    }

    @Override // android.media.MediaPlayer
    public void setDataSource(Context context, Uri uri) throws IOException {
        super.setDataSource(context, uri);
        this.o = uri.toString();
    }

    @Override // android.media.MediaPlayer
    public void setDataSource(FileDescriptor fileDescriptor) throws IOException {
        super.setDataSource(fileDescriptor);
        this.o = "local_file";
    }

    @Override // android.media.MediaPlayer
    public void setDataSource(FileDescriptor fileDescriptor, long j, long j2) throws IOException {
        super.setDataSource(fileDescriptor, j, j2);
        this.o = "local_file";
    }

    @Override // android.media.MediaPlayer
    public void setDataSource(String str) throws IOException {
        super.setDataSource(str);
        this.o = str;
    }

    @Override // android.media.MediaPlayer
    public void setOnCompletionListener(MediaPlayer.OnCompletionListener onCompletionListener) {
        super.setOnCompletionListener(this.q);
        this.p = onCompletionListener;
    }

    @Override // android.media.MediaPlayer
    public void setOnInfoListener(MediaPlayer.OnInfoListener onInfoListener) {
        super.setOnInfoListener(this.s);
        this.r = onInfoListener;
    }

    @Override // android.media.MediaPlayer
    public void setOnPreparedListener(MediaPlayer.OnPreparedListener onPreparedListener) {
        super.setOnPreparedListener(this.w);
        this.v = onPreparedListener;
    }

    @Override // android.media.MediaPlayer
    public void setOnSeekCompleteListener(MediaPlayer.OnSeekCompleteListener onSeekCompleteListener) {
        super.setOnSeekCompleteListener(this.u);
        this.t = onSeekCompleteListener;
    }

    public void setStreamSense(StreamSense streamSense) {
        this.j = streamSense;
        this.j.setLabel("ns_st_pv", "4.1307.02");
    }

    @Override // android.media.MediaPlayer
    public void start() {
        super.start();
        e();
    }

    @Override // android.media.MediaPlayer
    public void stop() {
        super.stop();
        l();
    }
}
