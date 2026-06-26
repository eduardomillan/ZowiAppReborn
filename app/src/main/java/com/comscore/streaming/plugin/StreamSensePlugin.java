package com.comscore.streaming.plugin;

import com.comscore.streaming.StreamSense;
import com.comscore.streaming.StreamSenseEventType;
import com.comscore.utils.CSLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class StreamSensePlugin extends StreamSense {
    private static final float[][] b = {new float[]{-1.0f, 1.0f}, new float[]{-0.5f, 0.0f, 0.5f}, new float[]{-0.3f, -0.1f, 0.1f, 0.3f}, new float[]{-0.2f, -0.1f, 0.0f, 0.1f, 0.2f}, new float[]{-0.14286f, -0.08571f, -0.02857f, 0.02857f, 0.08571f, 0.14286f}, new float[]{-0.10714f, -0.07143f, -0.03571f, 0.0f, 0.03571f, 0.07143f, 0.10714f}, new float[]{-0.08333f, -0.05952f, -0.03571f, -0.0119f, 0.0119f, 0.03571f, 0.05952f, 0.08333f}, new float[]{-0.06667f, -0.05f, -0.03333f, -0.01667f, 0.0f, 0.01667f, 0.03333f, 0.05f, 0.06667f}, new float[]{-0.05455f, -0.04242f, -0.0303f, -0.01818f, -0.00606f, 0.00606f, 0.01818f, 0.0303f, 0.04242f, 0.05455f}, new float[]{-0.04545f, -0.03636f, -0.02727f, -0.01818f, -0.00909f, 0.0f, 0.00909f, 0.01818f, 0.02727f, 0.03636f, 0.04545f}, new float[]{-0.03846f, -0.03147f, -0.02448f, -0.01748f, -0.01049f, -0.0035f, 0.0035f, 0.01049f, 0.01748f, 0.02448f, 0.03147f, 0.03846f}, new float[]{-0.03297f, -0.02747f, -0.02198f, -0.01648f, -0.01099f, -0.00549f, 0.0f, 0.00549f, 0.01099f, 0.01648f, 0.02198f, 0.02747f, 0.03297f}};
    private Runnable c;
    private StreamSensePlayer t;
    private int n = 10;
    private int o = 1000;
    private float p = 1.25f;
    private int s = 2;
    private int q = 300;
    private int r = 6;
    private boolean i = false;
    private boolean j = false;
    private boolean k = false;
    private boolean l = false;
    private boolean m = false;
    private long f = 0;
    private boolean g = false;
    private List<Long> d = new ArrayList();
    private List<Long> e = new ArrayList();
    private List<StreamSensePluginListener> h = new ArrayList();

    public StreamSensePlugin(String str, String str2, String str3) {
        setLabel("ns_st_mp", str);
        setLabel("ns_st_pv", str2);
        setLabel("ns_st_mv", str3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long a(int i) {
        return this.d.get((this.d.size() - 1) - i).longValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(long j) {
        return this.t.getDuration() > 0 && Math.abs(j - this.t.getDuration()) < ((long) this.o);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public long b(int i) {
        return this.e.get((this.e.size() - 1) - i).longValue();
    }

    private Runnable c() {
        return new b(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean d() {
        if (this.d.size() < 2) {
            CSLog.e(this, "isSeekInProgress: not enough previous playback positions");
            return false;
        }
        if (this.d.size() > 1 && a(0) < a(1)) {
            return true;
        }
        int i = this.q;
        float fLongValue = 0.0f;
        for (int i2 = 0; i2 < this.d.size(); i2++) {
            fLongValue += this.d.get(i2).longValue() * b[this.d.size() - 2][i2];
        }
        return fLongValue / ((float) i) > this.p;
    }

    public void addListener(StreamSensePluginListener streamSensePluginListener) {
        this.h.add(streamSensePluginListener);
    }

    public void clearAllListeners() {
        this.h.clear();
    }

    public void clearListener(StreamSensePluginListener streamSensePluginListener) {
        this.h.remove(streamSensePluginListener);
    }

    public StreamSensePlayer getPlayer() {
        return this.t;
    }

    @Override // com.comscore.streaming.StreamSense
    public void notify(StreamSenseEventType streamSenseEventType, HashMap<String, String> map, long j) {
        if (map == null) {
            map = new HashMap<>();
        }
        Iterator<StreamSensePluginListener> it = this.h.iterator();
        while (it.hasNext()) {
            it.next().onGetLabels(streamSenseEventType, map);
        }
        long position = j > 0 ? j : this.t != null ? this.t.getPosition() : 0L;
        if (position < 0) {
            position = 0;
        }
        super.notify(streamSenseEventType, map, position);
    }

    public void setDetectEnd(boolean z) {
        this.l = z;
    }

    public void setDetectPause(boolean z) {
        this.j = z;
    }

    public void setDetectPlay(boolean z) {
        this.k = z;
    }

    public void setDetectSeek(boolean z) {
        this.i = z;
    }

    public void setEndDetectionErrorMargin(int i) {
        this.o = i;
    }

    public void setMaximumNumberOfEntriesInHistory(int i) {
        if (i < 2 || i > 13) {
            return;
        }
        this.r = i;
    }

    public void setMinimumNumberOfTimeUpdateEventsBeforeSensingAnything(int i) {
        if (i < 2 || i > 13) {
            return;
        }
        this.s = i;
    }

    public void setPauseDetectionErrorMargin(int i) {
        this.n = i;
    }

    public void setPlayer(StreamSensePlayer streamSensePlayer) {
        this.t = streamSensePlayer;
    }

    public void setPulseSamplingInterval(int i) {
        if (i > 0) {
            this.q = i;
        }
    }

    public void setSeekDetectionMinQuotient(float f) {
        if (f > 1.0f) {
            this.p = f;
        }
    }

    public void setSmartStateDetection(boolean z) {
        this.m = z;
    }

    public void startSmartStateDetection() {
        stopSmartStateDetection();
        if (!this.m || this.t == null) {
            return;
        }
        this.c = c();
        this.a.getTaskExecutor().execute(this.c, this.q);
    }

    public void stopSmartStateDetection() {
        if (this.c != null) {
            this.a.getTaskExecutor().removeEnqueuedTask(this.c);
            this.c = null;
        }
    }
}
