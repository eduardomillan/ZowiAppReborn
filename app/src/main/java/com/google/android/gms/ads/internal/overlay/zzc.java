package com.google.android.gms.ads.internal.overlay;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import com.bq.zowi.views.interactive.pad.PadViewActivity;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzc extends zzi implements AudioManager.OnAudioFocusChangeListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener, TextureView.SurfaceTextureListener {
    private static final Map<Integer, String> zzAO = new HashMap();
    private final zzp zzAP;
    private int zzAQ;
    private int zzAR;
    private MediaPlayer zzAS;
    private Uri zzAT;
    private int zzAU;
    private int zzAV;
    private int zzAW;
    private int zzAX;
    private int zzAY;
    private float zzAZ;
    private boolean zzBa;
    private boolean zzBb;
    private int zzBc;
    private zzh zzBd;

    static {
        zzAO.put(-1004, "MEDIA_ERROR_IO");
        zzAO.put(-1007, "MEDIA_ERROR_MALFORMED");
        zzAO.put(-1010, "MEDIA_ERROR_UNSUPPORTED");
        zzAO.put(-110, "MEDIA_ERROR_TIMED_OUT");
        zzAO.put(100, "MEDIA_ERROR_SERVER_DIED");
        zzAO.put(1, "MEDIA_ERROR_UNKNOWN");
        zzAO.put(1, "MEDIA_INFO_UNKNOWN");
        zzAO.put(Integer.valueOf(PadViewActivity.HIGH_SPEED), "MEDIA_INFO_VIDEO_TRACK_LAGGING");
        zzAO.put(3, "MEDIA_INFO_VIDEO_RENDERING_START");
        zzAO.put(701, "MEDIA_INFO_BUFFERING_START");
        zzAO.put(702, "MEDIA_INFO_BUFFERING_END");
        zzAO.put(800, "MEDIA_INFO_BAD_INTERLEAVING");
        zzAO.put(801, "MEDIA_INFO_NOT_SEEKABLE");
        zzAO.put(802, "MEDIA_INFO_METADATA_UPDATE");
        zzAO.put(901, "MEDIA_INFO_UNSUPPORTED_SUBTITLE");
        zzAO.put(902, "MEDIA_INFO_SUBTITLE_TIMED_OUT");
    }

    public zzc(Context context, zzp zzpVar) {
        super(context);
        this.zzAQ = 0;
        this.zzAR = 0;
        this.zzAZ = 1.0f;
        setSurfaceTextureListener(this);
        this.zzAP = zzpVar;
        this.zzAP.zza((zzi) this);
    }

    private void zzb(float f) {
        if (this.zzAS == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("AdMediaPlayerView setMediaPlayerVolume() called before onPrepared().");
        } else {
            try {
                this.zzAS.setVolume(f, f);
            } catch (IllegalStateException e) {
            }
        }
    }

    private void zzeA() {
        com.google.android.gms.ads.internal.util.client.zzb.v("AdMediaPlayerView audio focus lost");
        this.zzBb = false;
        zzeB();
    }

    private void zzeB() {
        if (this.zzBa || !this.zzBb) {
            zzb(0.0f);
        } else {
            zzb(this.zzAZ);
        }
    }

    private AudioManager zzeC() {
        return (AudioManager) getContext().getSystemService("audio");
    }

    private void zzes() {
        com.google.android.gms.ads.internal.util.client.zzb.v("AdMediaPlayerView init MediaPlayer");
        SurfaceTexture surfaceTexture = getSurfaceTexture();
        if (this.zzAT == null || surfaceTexture == null) {
            return;
        }
        zzt(false);
        try {
            this.zzAS = new MediaPlayer();
            this.zzAS.setOnBufferingUpdateListener(this);
            this.zzAS.setOnCompletionListener(this);
            this.zzAS.setOnErrorListener(this);
            this.zzAS.setOnInfoListener(this);
            this.zzAS.setOnPreparedListener(this);
            this.zzAS.setOnVideoSizeChangedListener(this);
            this.zzAW = 0;
            this.zzAS.setDataSource(getContext(), this.zzAT);
            this.zzAS.setSurface(new Surface(surfaceTexture));
            this.zzAS.setAudioStreamType(3);
            this.zzAS.setScreenOnWhilePlaying(true);
            this.zzAS.prepareAsync();
            zzt(1);
        } catch (IOException | IllegalArgumentException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to initialize MediaPlayer at " + this.zzAT, e);
            onError(this.zzAS, 1, 0);
        }
    }

    private void zzet() {
        if (!zzew() || this.zzAS.getCurrentPosition() <= 0 || this.zzAR == 3) {
            return;
        }
        com.google.android.gms.ads.internal.util.client.zzb.v("AdMediaPlayerView nudging MediaPlayer");
        zzb(0.0f);
        this.zzAS.start();
        int currentPosition = this.zzAS.getCurrentPosition();
        long jCurrentTimeMillis = com.google.android.gms.ads.internal.zzp.zzbz().currentTimeMillis();
        while (zzew() && this.zzAS.getCurrentPosition() == currentPosition && com.google.android.gms.ads.internal.zzp.zzbz().currentTimeMillis() - jCurrentTimeMillis <= 250) {
        }
        this.zzAS.pause();
        zzeB();
    }

    private void zzeu() {
        AudioManager audioManagerZzeC = zzeC();
        if (audioManagerZzeC == null || this.zzBb) {
            return;
        }
        if (audioManagerZzeC.requestAudioFocus(this, 3, 2) == 1) {
            zzez();
        } else {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("AdMediaPlayerView audio focus request failed");
        }
    }

    private void zzev() {
        com.google.android.gms.ads.internal.util.client.zzb.v("AdMediaPlayerView abandon audio focus");
        AudioManager audioManagerZzeC = zzeC();
        if (audioManagerZzeC == null || !this.zzBb) {
            return;
        }
        if (audioManagerZzeC.abandonAudioFocus(this) == 1) {
            this.zzBb = false;
        } else {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("AdMediaPlayerView abandon audio focus failed");
        }
    }

    private boolean zzew() {
        return (this.zzAS == null || this.zzAQ == -1 || this.zzAQ == 0 || this.zzAQ == 1) ? false : true;
    }

    private void zzez() {
        com.google.android.gms.ads.internal.util.client.zzb.v("AdMediaPlayerView audio focus gained");
        this.zzBb = true;
        zzeB();
    }

    private void zzt(int i) {
        if (i == 3) {
            this.zzAP.zzfe();
        } else if (this.zzAQ == 3 && i != 3) {
            this.zzAP.zzff();
        }
        this.zzAQ = i;
    }

    private void zzt(boolean z) {
        com.google.android.gms.ads.internal.util.client.zzb.v("AdMediaPlayerView release");
        if (this.zzAS != null) {
            this.zzAS.reset();
            this.zzAS.release();
            this.zzAS = null;
            zzt(0);
            if (z) {
                this.zzAR = 0;
                zzu(0);
            }
            zzev();
        }
    }

    private void zzu(int i) {
        this.zzAR = i;
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzi
    public int getCurrentPosition() {
        if (zzew()) {
            return this.zzAS.getCurrentPosition();
        }
        return 0;
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzi
    public int getDuration() {
        if (zzew()) {
            return this.zzAS.getDuration();
        }
        return -1;
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzi
    public int getVideoHeight() {
        if (this.zzAS != null) {
            return this.zzAS.getVideoHeight();
        }
        return 0;
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzi
    public int getVideoWidth() {
        if (this.zzAS != null) {
            return this.zzAS.getVideoWidth();
        }
        return 0;
    }

    @Override // android.media.AudioManager.OnAudioFocusChangeListener
    public void onAudioFocusChange(int focusChange) {
        if (focusChange > 0) {
            zzez();
        } else if (focusChange < 0) {
            zzeA();
        }
    }

    @Override // android.media.MediaPlayer.OnBufferingUpdateListener
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        this.zzAW = percent;
    }

    @Override // android.media.MediaPlayer.OnCompletionListener
    public void onCompletion(MediaPlayer mp) {
        com.google.android.gms.ads.internal.util.client.zzb.v("AdMediaPlayerView completion");
        zzt(5);
        zzu(5);
        zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.ads.internal.overlay.zzc.2
            @Override // java.lang.Runnable
            public void run() {
                if (zzc.this.zzBd != null) {
                    zzc.this.zzBd.zzeT();
                }
            }
        });
    }

    @Override // android.media.MediaPlayer.OnErrorListener
    public boolean onError(MediaPlayer mp, int what, int extra) {
        final String str = zzAO.get(Integer.valueOf(what));
        final String str2 = zzAO.get(Integer.valueOf(extra));
        com.google.android.gms.ads.internal.util.client.zzb.zzaH("AdMediaPlayerView MediaPlayer error: " + str + ":" + str2);
        zzt(-1);
        zzu(-1);
        zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.ads.internal.overlay.zzc.3
            @Override // java.lang.Runnable
            public void run() {
                if (zzc.this.zzBd != null) {
                    zzc.this.zzBd.zzh(str, str2);
                }
            }
        });
        return true;
    }

    @Override // android.media.MediaPlayer.OnInfoListener
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        com.google.android.gms.ads.internal.util.client.zzb.v("AdMediaPlayerView MediaPlayer info: " + zzAO.get(Integer.valueOf(what)) + ":" + zzAO.get(Integer.valueOf(extra)));
        return true;
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int defaultSize = getDefaultSize(this.zzAU, widthMeasureSpec);
        int defaultSize2 = getDefaultSize(this.zzAV, heightMeasureSpec);
        if (this.zzAU > 0 && this.zzAV > 0) {
            int mode = View.MeasureSpec.getMode(widthMeasureSpec);
            int size = View.MeasureSpec.getSize(widthMeasureSpec);
            int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
            defaultSize2 = View.MeasureSpec.getSize(heightMeasureSpec);
            if (mode == 1073741824 && mode2 == 1073741824) {
                if (this.zzAU * defaultSize2 < this.zzAV * size) {
                    defaultSize = (this.zzAU * defaultSize2) / this.zzAV;
                } else if (this.zzAU * defaultSize2 > this.zzAV * size) {
                    defaultSize2 = (this.zzAV * size) / this.zzAU;
                    defaultSize = size;
                } else {
                    defaultSize = size;
                }
            } else if (mode == 1073741824) {
                int i = (this.zzAV * size) / this.zzAU;
                if (mode2 != Integer.MIN_VALUE || i <= defaultSize2) {
                    defaultSize2 = i;
                    defaultSize = size;
                } else {
                    defaultSize = size;
                }
            } else if (mode2 == 1073741824) {
                defaultSize = (this.zzAU * defaultSize2) / this.zzAV;
                if (mode == Integer.MIN_VALUE && defaultSize > size) {
                    defaultSize = size;
                }
            } else {
                int i2 = this.zzAU;
                int i3 = this.zzAV;
                if (mode2 != Integer.MIN_VALUE || i3 <= defaultSize2) {
                    defaultSize2 = i3;
                    defaultSize = i2;
                } else {
                    defaultSize = (this.zzAU * defaultSize2) / this.zzAV;
                }
                if (mode == Integer.MIN_VALUE && defaultSize > size) {
                    defaultSize2 = (this.zzAV * size) / this.zzAU;
                    defaultSize = size;
                }
            }
        }
        setMeasuredDimension(defaultSize, defaultSize2);
        if (Build.VERSION.SDK_INT == 16) {
            if ((this.zzAX > 0 && this.zzAX != defaultSize) || (this.zzAY > 0 && this.zzAY != defaultSize2)) {
                zzet();
            }
            this.zzAX = defaultSize;
            this.zzAY = defaultSize2;
        }
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mediaPlayer) {
        com.google.android.gms.ads.internal.util.client.zzb.v("AdMediaPlayerView prepared");
        zzt(2);
        this.zzAP.zzeR();
        zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.ads.internal.overlay.zzc.1
            @Override // java.lang.Runnable
            public void run() {
                if (zzc.this.zzBd != null) {
                    zzc.this.zzBd.zzeR();
                }
            }
        });
        this.zzAU = mediaPlayer.getVideoWidth();
        this.zzAV = mediaPlayer.getVideoHeight();
        if (this.zzBc != 0) {
            seekTo(this.zzBc);
        }
        zzet();
        com.google.android.gms.ads.internal.util.client.zzb.zzaG("AdMediaPlayerView stream dimensions: " + this.zzAU + " x " + this.zzAV);
        if (this.zzAR == 3) {
            play();
        }
        zzeu();
        zzeB();
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        com.google.android.gms.ads.internal.util.client.zzb.v("AdMediaPlayerView surface created");
        zzes();
        zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.ads.internal.overlay.zzc.4
            @Override // java.lang.Runnable
            public void run() {
                if (zzc.this.zzBd != null) {
                    zzc.this.zzBd.zzeQ();
                }
            }
        });
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        com.google.android.gms.ads.internal.util.client.zzb.v("AdMediaPlayerView surface destroyed");
        if (this.zzAS != null && this.zzBc == 0) {
            this.zzBc = this.zzAS.getCurrentPosition();
        }
        zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.ads.internal.overlay.zzc.5
            @Override // java.lang.Runnable
            public void run() {
                if (zzc.this.zzBd != null) {
                    zzc.this.zzBd.onPaused();
                    zzc.this.zzBd.zzeU();
                }
            }
        });
        zzt(true);
        return true;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int w, int h) {
        com.google.android.gms.ads.internal.util.client.zzb.v("AdMediaPlayerView surface changed");
        boolean z = this.zzAR == 3;
        boolean z2 = this.zzAU == w && this.zzAV == h;
        if (this.zzAS != null && z && z2) {
            if (this.zzBc != 0) {
                seekTo(this.zzBc);
            }
            play();
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        this.zzAP.zzb(this);
    }

    @Override // android.media.MediaPlayer.OnVideoSizeChangedListener
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        com.google.android.gms.ads.internal.util.client.zzb.v("AdMediaPlayerView size changed: " + width + " x " + height);
        this.zzAU = mp.getVideoWidth();
        this.zzAV = mp.getVideoHeight();
        if (this.zzAU == 0 || this.zzAV == 0) {
            return;
        }
        requestLayout();
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzi
    public void pause() {
        com.google.android.gms.ads.internal.util.client.zzb.v("AdMediaPlayerView pause");
        if (zzew() && this.zzAS.isPlaying()) {
            this.zzAS.pause();
            zzt(4);
            zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.ads.internal.overlay.zzc.7
                @Override // java.lang.Runnable
                public void run() {
                    if (zzc.this.zzBd != null) {
                        zzc.this.zzBd.onPaused();
                    }
                }
            });
        }
        zzu(4);
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzi
    public void play() {
        com.google.android.gms.ads.internal.util.client.zzb.v("AdMediaPlayerView play");
        if (zzew()) {
            this.zzAS.start();
            zzt(3);
            zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.ads.internal.overlay.zzc.6
                @Override // java.lang.Runnable
                public void run() {
                    if (zzc.this.zzBd != null) {
                        zzc.this.zzBd.zzeS();
                    }
                }
            });
        }
        zzu(3);
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzi
    public void seekTo(int millis) {
        com.google.android.gms.ads.internal.util.client.zzb.v("AdMediaPlayerView seek " + millis);
        if (!zzew()) {
            this.zzBc = millis;
        } else {
            this.zzAS.seekTo(millis);
            this.zzBc = 0;
        }
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzi
    public void setMimeType(String mimeType) {
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzi
    public void setVideoPath(String path) {
        setVideoURI(Uri.parse(path));
    }

    public void setVideoURI(Uri uri) {
        this.zzAT = uri;
        this.zzBc = 0;
        zzes();
        requestLayout();
        invalidate();
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzi
    public void stop() {
        com.google.android.gms.ads.internal.util.client.zzb.v("AdMediaPlayerView stop");
        if (this.zzAS != null) {
            this.zzAS.stop();
            this.zzAS.release();
            this.zzAS = null;
            zzt(0);
            zzu(0);
            zzev();
        }
        this.zzAP.onStop();
    }

    @Override // android.view.View
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode());
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzi
    public void zza(float f) {
        this.zzAZ = f;
        zzeB();
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzi
    public void zza(zzh zzhVar) {
        this.zzBd = zzhVar;
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzi
    public String zzer() {
        return "MediaPlayer";
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzi
    public void zzex() {
        this.zzBa = true;
        zzeB();
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzi
    public void zzey() {
        this.zzBa = false;
        zzeB();
    }
}
