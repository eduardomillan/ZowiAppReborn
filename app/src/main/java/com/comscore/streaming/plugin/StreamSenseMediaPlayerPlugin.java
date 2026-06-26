package com.comscore.streaming.plugin;

import android.media.MediaPlayer;
import android.os.Build;
import com.comscore.streaming.StreamSenseEventType;
import com.comscore.streaming.StreamSenseState;
import com.comscore.utils.CSLog;
import com.comscore.utils.Utils;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class StreamSenseMediaPlayerPlugin extends StreamSensePlugin implements StreamSensePlayer, StreamSensePluginListener {
    private static final String b = "MediaPlayer";
    private static final String c = "4.1404.10";
    private static final String d = String.valueOf(Build.VERSION.SDK_INT);
    private MediaPlayer e;

    public StreamSenseMediaPlayerPlugin(MediaPlayer mediaPlayer) {
        super(b, c, d);
        this.e = null;
        this.e = mediaPlayer;
        setPlayer(this);
        addListener(this);
        setSmartStateDetection(true);
        setDetectPlay(true);
        setDetectPause(true);
        setDetectSeek(true);
        setDetectEnd(true);
        startSmartStateDetection();
        setLabel("ns_st_it", "c");
        setLabel("ns_st_pn", "1");
        setLabel("ns_st_tp", "1");
    }

    @Override // com.comscore.streaming.plugin.StreamSensePlayer
    public long getDuration() {
        if (this.e != null) {
            return this.e.getDuration();
        }
        return -1L;
    }

    @Override // com.comscore.streaming.plugin.StreamSensePlayer
    public long getPosition() {
        if (this.e == null) {
            return -1L;
        }
        try {
            return this.e.getCurrentPosition();
        } catch (IllegalStateException e) {
            return -1L;
        }
    }

    @Override // com.comscore.streaming.plugin.StreamSensePluginListener
    public void onGetLabels(StreamSenseEventType streamSenseEventType, HashMap<String, String> map) {
        CSLog.d(this, "onGetLabels: " + streamSenseEventType);
        long j = Utils.getLong(getClip().getLabel("ns_st_cl"));
        if (this.e != null && j <= 0) {
            getClip().setLabel("ns_st_cl", String.valueOf(this.e.getDuration()));
        }
        String label = getClip().getLabel("ns_st_cs");
        if (this.e != null) {
            if (label == null || label.equals("0x0")) {
                getClip().setLabel("ns_st_cs", this.e.getVideoWidth() + "x" + this.e.getVideoHeight());
            }
        }
    }

    @Override // com.comscore.streaming.plugin.StreamSensePluginListener
    public void onPostStateChange(StreamSenseState streamSenseState) {
        CSLog.d(this, "onPostStateChange = " + streamSenseState);
        switch (streamSenseState) {
            case IDLE:
                stopSmartStateDetection();
                break;
        }
    }

    @Override // com.comscore.streaming.plugin.StreamSensePluginListener
    public void onPreStateChange(StreamSenseState streamSenseState) {
        CSLog.d(this, "onPreStateChange = " + streamSenseState);
    }

    public void release() {
        notify(StreamSenseEventType.END, getPosition());
        stopSmartStateDetection();
    }
}
