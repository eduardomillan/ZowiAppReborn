package com.comscore.streaming.plugin;

import com.comscore.streaming.StreamSenseEventType;
import com.comscore.streaming.StreamSenseState;
import com.comscore.utils.CSLog;
import java.util.HashMap;
import java.util.Iterator;

/* JADX INFO: loaded from: classes.dex */
class b implements Runnable {
    final /* synthetic */ StreamSensePlugin a;

    b(StreamSensePlugin streamSensePlugin) {
        this.a = streamSensePlugin;
    }

    @Override // java.lang.Runnable
    public void run() {
        boolean zD;
        if (!this.a.m || this.a.t == null) {
            this.a.stopSmartStateDetection();
            return;
        }
        StreamSenseState state = this.a.getState();
        long jCurrentTimeMillis = System.currentTimeMillis();
        long position = this.a.t.getPosition();
        this.a.startSmartStateDetection();
        if (this.a.d.isEmpty() || position != this.a.a(0)) {
            this.a.d.add(Long.valueOf(position));
            this.a.e.add(Long.valueOf(jCurrentTimeMillis));
            if (this.a.d.size() <= 1 || this.a.a(0) >= this.a.a(1)) {
                if (this.a.d.size() > 1) {
                    CSLog.d(this, "Playback Position: " + this.a.a(0));
                }
                CSLog.d(this, "Historical Data Count: " + this.a.d.size());
                if (this.a.d.size() < this.a.s) {
                    return;
                } else {
                    zD = false;
                }
            } else {
                long jA = this.a.a(0);
                long jB = this.a.b(0);
                this.a.d.clear();
                this.a.e.clear();
                this.a.d.add(Long.valueOf(jA));
                this.a.e.add(Long.valueOf(jB));
                zD = this.a.i;
                if (this.a.d.size() > 1) {
                    CSLog.d(this, "Playback Position: " + this.a.a(0));
                }
                CSLog.d(this, "Historical Data Count: " + this.a.d.size());
            }
            if (this.a.d.size() > this.a.r) {
                this.a.d.remove(0);
                this.a.e.remove(0);
            }
            if (this.a.i && !zD) {
                zD = this.a.d();
            }
        } else {
            this.a.e.set(this.a.e.size() - 1, Long.valueOf(System.currentTimeMillis()));
            zD = false;
        }
        CSLog.d(this, "Current state: " + this.a.getState());
        switch (this.a.getState()) {
            case BUFFERING:
            case IDLE:
                if (this.a.k && position > this.a.f && !zD) {
                    Iterator it = this.a.h.iterator();
                    while (it.hasNext()) {
                        ((StreamSensePluginListener) it.next()).onPreStateChange(this.a.getState());
                    }
                    CSLog.d(this, "Moving to PLAYING state");
                    if (this.a.g) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("ns_st_ui", "seek");
                        this.a.notify(StreamSenseEventType.PLAY, map, position);
                    } else {
                        this.a.notify(StreamSenseEventType.PLAY, null, ((Long) this.a.d.get(0)).longValue());
                    }
                    this.a.g = false;
                }
                break;
            case PAUSED:
                if (this.a.k && position > this.a.f && !zD) {
                    Iterator it2 = this.a.h.iterator();
                    while (it2.hasNext()) {
                        ((StreamSensePluginListener) it2.next()).onPreStateChange(this.a.getState());
                    }
                    CSLog.d(this, "Moving to PLAYING state");
                    if (this.a.g) {
                        HashMap<String, String> map2 = new HashMap<>();
                        map2.put("ns_st_ui", "seek");
                        this.a.notify(StreamSenseEventType.PLAY, map2, position);
                    } else {
                        this.a.notify(StreamSenseEventType.PLAY, null, ((Long) this.a.d.get(0)).longValue());
                    }
                    this.a.g = false;
                } else if (this.a.i && zD) {
                    this.a.g = true;
                }
                break;
            case PLAYING:
                if (this.a.i && zD) {
                    Iterator it3 = this.a.h.iterator();
                    while (it3.hasNext()) {
                        ((StreamSensePluginListener) it3.next()).onPreStateChange(this.a.getState());
                    }
                    CSLog.d(this, "Moving to PAUSE (seek) state");
                    this.a.notify(StreamSenseEventType.PAUSE, null, this.a.f);
                    this.a.g = true;
                } else if (this.a.l && this.a.a(position)) {
                    Iterator it4 = this.a.h.iterator();
                    while (it4.hasNext()) {
                        ((StreamSensePluginListener) it4.next()).onPreStateChange(this.a.getState());
                    }
                    CSLog.d(this, "Moving to END state");
                    this.a.notify(StreamSenseEventType.END, null, this.a.t.getDuration());
                    this.a.g = false;
                } else if (this.a.j && Math.abs(position - this.a.f) <= this.a.n) {
                    Iterator it5 = this.a.h.iterator();
                    while (it5.hasNext()) {
                        ((StreamSensePluginListener) it5.next()).onPreStateChange(this.a.getState());
                    }
                    CSLog.d(this, "Moving to PAUSE state");
                    this.a.notify(StreamSenseEventType.PAUSE, null, this.a.f);
                }
                break;
        }
        if (state != this.a.getState()) {
            Iterator it6 = this.a.h.iterator();
            while (it6.hasNext()) {
                ((StreamSensePluginListener) it6.next()).onPostStateChange(this.a.getState());
            }
            if (this.a.getState() == StreamSenseState.PAUSED) {
                this.a.d.clear();
                this.a.e.clear();
            }
        }
        this.a.f = position;
    }
}
