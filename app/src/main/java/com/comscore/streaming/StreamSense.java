package com.comscore.streaming;

import android.content.Context;
import com.comscore.analytics.Core;
import com.comscore.analytics.comScore;
import com.comscore.utils.CSLog;
import com.comscore.utils.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class StreamSense {
    protected Core a;
    private HashMap<String, String> b;
    private String c = null;
    private long d;
    private long e;
    private StreamSenseState f;
    private int g;
    private StreamSensePlaylist h;
    private Runnable i;
    private boolean j;
    private Runnable k;
    private f l;
    private Runnable m;
    private long n;
    private int o;
    private long p;
    private boolean q;
    private StreamSenseState r;
    private String s;
    private String t;
    private HashMap<String, String> u;
    private List<StreamSenseListener> v;
    private List<HashMap<String, Long>> w;
    private int x;
    private int y;

    public StreamSense() {
        this.f = null;
        this.h = null;
        this.i = null;
        this.j = true;
        this.l = null;
        CSLog.d(this, "StreamSense()");
        this.a = comScore.getCore();
        this.b = new HashMap<>();
        this.g = 1;
        this.f = StreamSenseState.IDLE;
        this.h = new StreamSensePlaylist();
        this.i = null;
        this.j = true;
        this.m = null;
        this.o = 0;
        f();
        this.k = null;
        this.l = null;
        this.q = false;
        this.r = null;
        this.e = 0L;
        this.x = Constants.DEFAULT_KEEP_ALIVE_INTERVAL;
        this.y = 500;
        this.v = new ArrayList();
        this.w = n();
        reset();
    }

    private long a(long j) {
        for (HashMap<String, Long> map : this.w) {
            Long l = map.get(Constants.HEARTBEAT_PLAYING_TIME_KEY);
            if (l == null || j < l.longValue()) {
                return map.get(Constants.HEARTBEAT_INTERVAL_KEY).longValue();
            }
        }
        return 0L;
    }

    private StreamSenseState a(StreamSenseEventType streamSenseEventType) {
        if (streamSenseEventType == StreamSenseEventType.PLAY) {
            return StreamSenseState.PLAYING;
        }
        if (streamSenseEventType == StreamSenseEventType.PAUSE) {
            return StreamSenseState.PAUSED;
        }
        if (streamSenseEventType == StreamSenseEventType.BUFFER) {
            return StreamSenseState.BUFFERING;
        }
        if (streamSenseEventType == StreamSenseEventType.END) {
            return StreamSenseState.IDLE;
        }
        return null;
    }

    private HashMap<String, String> a(StreamSenseEventType streamSenseEventType, HashMap<String, String> map) {
        if (!this.a.isEnabled()) {
            new HashMap();
        }
        CSLog.d(this, "createMeasurementLabels(" + streamSenseEventType + ")");
        HashMap<String, String> map2 = new HashMap<>();
        if (map != null) {
            map2.putAll(map);
        }
        if (!map2.containsKey("ns_ts")) {
            map2.put("ns_ts", String.valueOf(System.currentTimeMillis()));
        }
        if (streamSenseEventType != null && !map2.containsKey("ns_st_ev")) {
            map2.put("ns_st_ev", streamSenseEventType.toString());
        }
        map2.putAll(getLabels());
        b(streamSenseEventType, map2);
        this.h.a(streamSenseEventType, map2);
        this.h.getClip().a(streamSenseEventType, map2);
        if (!map2.containsKey("ns_st_mp")) {
            map2.put("ns_st_mp", this.s);
        }
        if (!map2.containsKey("ns_st_mv")) {
            map2.put("ns_st_mv", this.t);
        }
        if (!map2.containsKey("ns_st_ub")) {
            map2.put("ns_st_ub", "0");
        }
        if (!map2.containsKey("ns_st_br")) {
            map2.put("ns_st_br", "0");
        }
        if (!map2.containsKey("ns_st_pn")) {
            map2.put("ns_st_pn", "1");
        }
        if (!map2.containsKey("ns_st_tp")) {
            map2.put("ns_st_tp", "1");
        }
        if (!map2.containsKey("ns_st_it")) {
            map2.put("ns_st_it", "c");
        }
        map2.put("ns_st_sv", Constants.STREAMSENSE_VERSION);
        return map2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(StreamSenseState streamSenseState, HashMap<String, String> map) {
        if (this.a.isEnabled()) {
            CSLog.d(this, "transitionTo(" + streamSenseState + ", " + map + ")");
            l();
            if (c(streamSenseState)) {
                StreamSenseState state = getState();
                long j = this.d;
                long jF = j >= 0 ? f(map) - j : 0L;
                b(getState(), map);
                c(streamSenseState, map);
                d(streamSenseState);
                Iterator<StreamSenseListener> it = this.v.iterator();
                while (it.hasNext()) {
                    it.next().onStateChange(state, streamSenseState, map, jF);
                }
                c(map);
                this.h.b(map, streamSenseState);
                this.h.getClip().b(map, streamSenseState);
                HashMap<String, String> mapA = a(streamSenseState.toEventType(), map);
                mapA.putAll(map);
                if (b(this.f)) {
                    a(mapA);
                    this.r = this.f;
                    this.g++;
                }
            }
        }
    }

    private void a(StreamSenseState streamSenseState, HashMap<String, String> map, long j) {
        if (this.a.isEnabled()) {
            CSLog.d(this, "transitionTo(" + streamSenseState + ", " + map + ", " + j + ")");
            l();
            this.l = new d(this, streamSenseState, map);
            this.a.getTaskExecutor().execute(this.l, j);
        }
    }

    private boolean a(StreamSenseState streamSenseState) {
        if (this.a.isEnabled()) {
            return streamSenseState == StreamSenseState.PLAYING || streamSenseState == StreamSenseState.PAUSED;
        }
        return false;
    }

    @Deprecated
    public static StreamSense analyticsFor(StreamSenseMediaPlayer streamSenseMediaPlayer) {
        StreamSense streamSense = new StreamSense();
        streamSense.engageTo(streamSenseMediaPlayer);
        streamSense.setPausePlaySwitchDelayEnabled(true);
        return streamSense;
    }

    @Deprecated
    public static StreamSense analyticsFor(StreamSenseVideoView streamSenseVideoView) {
        StreamSense streamSense = new StreamSense();
        streamSense.engageTo(streamSenseVideoView);
        return streamSense;
    }

    private HashMap<String, String> b(StreamSenseEventType streamSenseEventType, HashMap<String, String> map) {
        if (!this.a.isEnabled()) {
            return new HashMap<>();
        }
        if (map == null) {
            map = new HashMap<>();
        }
        map.put("ns_st_ec", String.valueOf(this.g));
        if (!map.containsKey("ns_st_po")) {
            long jG = this.e;
            long jF = f(map);
            if (streamSenseEventType == StreamSenseEventType.PLAY || streamSenseEventType == StreamSenseEventType.KEEP_ALIVE || streamSenseEventType == StreamSenseEventType.HEART_BEAT || (streamSenseEventType == null && this.f == StreamSenseState.PLAYING)) {
                jG += jF - this.h.getClip().g();
            }
            map.put("ns_st_po", String.valueOf(jG));
        }
        if (streamSenseEventType != StreamSenseEventType.HEART_BEAT) {
            return map;
        }
        map.put("ns_st_hc", String.valueOf(this.o));
        map.put("ns_st_pe", "1");
        return map;
    }

    private void b(StreamSenseState streamSenseState, HashMap<String, String> map) {
        if (this.a.isEnabled()) {
            CSLog.d(this, "onExit(" + streamSenseState + ", " + map + ")");
            long jF = f(map);
            if (streamSenseState == StreamSenseState.PLAYING) {
                this.h.a(jF);
                e();
                h();
            } else if (streamSenseState == StreamSenseState.BUFFERING) {
                this.h.b(jF);
                k();
            } else if (streamSenseState == StreamSenseState.IDLE) {
                getClip().reset(getClip().getLabels().keySet());
            }
        }
    }

    private void b(HashMap<String, String> map) {
        if (this.a.isEnabled() && f(map) < 0) {
            map.put("ns_ts", String.valueOf(System.currentTimeMillis()));
        }
    }

    private boolean b(StreamSenseState streamSenseState) {
        return ((streamSenseState == StreamSenseState.PAUSED && (this.r == StreamSenseState.IDLE || this.r == null)) || streamSenseState == StreamSenseState.BUFFERING || this.r == streamSenseState) ? false : true;
    }

    private void c() {
        if (this.a.isEnabled()) {
            k();
            if (isPauseOnBufferingEnabled() && b(StreamSenseState.PAUSED)) {
                this.i = new a(this);
                this.a.getTaskExecutor().execute(this.i, this.y);
            }
        }
    }

    private void c(StreamSenseState streamSenseState, HashMap<String, String> map) {
        if (this.a.isEnabled()) {
            CSLog.d(this, "onEnter(" + streamSenseState + ", " + map + ")");
            long jF = f(map);
            this.e = e(map);
            if (streamSenseState == StreamSenseState.PLAYING) {
                d();
                g();
                this.h.getClip().c(jF);
                if (b(streamSenseState)) {
                    this.h.getClip().d();
                    if (this.h.a() < 1) {
                        this.h.a(1);
                        return;
                    }
                    return;
                }
                return;
            }
            if (streamSenseState == StreamSenseState.PAUSED) {
                if (b(streamSenseState)) {
                    this.h.f();
                }
            } else if (streamSenseState != StreamSenseState.BUFFERING) {
                if (streamSenseState == StreamSenseState.IDLE) {
                    f();
                }
            } else {
                this.h.getClip().d(jF);
                if (this.j) {
                    c();
                }
            }
        }
    }

    private void c(HashMap<String, String> map) {
        if (this.a.isEnabled()) {
            String str = map.get("ns_st_mp");
            if (str != null) {
                this.s = str;
                map.remove("ns_st_mp");
            }
            String str2 = map.get("ns_st_mv");
            if (str2 != null) {
                this.t = str2;
                map.remove("ns_st_mv");
            }
            String str3 = map.get("ns_st_ec");
            if (str3 != null) {
                try {
                    this.g = Integer.parseInt(str3);
                    map.remove("ns_st_ec");
                } catch (NumberFormatException e) {
                }
            }
        }
    }

    private boolean c(StreamSenseState streamSenseState) {
        return (streamSenseState == null || getState() == streamSenseState) ? false : true;
    }

    private void d() {
        long jA;
        if (this.a.isEnabled()) {
            i();
            if (this.w != null) {
                if (this.n >= 0) {
                    jA = this.n;
                    CSLog.d(this, "Resuming heart beat timer. Next event in " + jA + " ms");
                } else {
                    jA = a(this.h.getClip().f());
                    CSLog.d(this, "Starting heart beat timer. Next event in " + jA + " ms");
                }
                if (jA > 0) {
                    this.p = System.currentTimeMillis() + jA;
                    this.m = new b(this);
                    this.a.getTaskExecutor().execute(this.m, jA);
                }
            }
        }
    }

    private void d(StreamSenseState streamSenseState) {
        if (this.a.isEnabled()) {
            this.f = streamSenseState;
            this.d = System.currentTimeMillis();
        }
    }

    private void d(HashMap<String, String> map) {
        if (this.a.isEnabled()) {
            this.u = g(null);
            this.u.putAll(map);
        }
    }

    private long e(HashMap<String, String> map) {
        if (!map.containsKey("ns_st_po")) {
            return -1L;
        }
        try {
            return Long.valueOf(map.get("ns_st_po")).longValue();
        } catch (NumberFormatException e) {
            return -1L;
        }
    }

    private void e() {
        if (this.a.isEnabled()) {
            CSLog.d(this, "Pausing heartbeat timer.");
            i();
            this.n = this.p - System.currentTimeMillis();
            this.p = -1L;
        }
    }

    private long f(HashMap<String, String> map) {
        if (!map.containsKey("ns_ts")) {
            return -1L;
        }
        try {
            return Long.valueOf(map.get("ns_ts")).longValue();
        } catch (NumberFormatException e) {
            return -1L;
        }
    }

    private void f() {
        if (this.a.isEnabled()) {
            CSLog.d(this, "Resetting heartbeat timer.");
            this.n = -1L;
            this.p = -1L;
            this.o = 0;
        }
    }

    private HashMap<String, String> g(HashMap<String, String> map) {
        return a(this.f.toEventType(), map);
    }

    private void g() {
        if (this.a.isEnabled()) {
            CSLog.d(this, "Starting keep alive timer");
            h();
            this.k = new c(this);
            this.a.getTaskExecutor().execute(this.k, this.x, true, this.x);
        }
    }

    public static String getVersion() {
        return Constants.STREAMSENSE_VERSION;
    }

    private void h() {
        if (this.a.isEnabled()) {
            CSLog.d(this, "stopKeepAliveTask()");
            if (this.k != null) {
                this.a.getTaskExecutor().removeEnqueuedTask(this.k);
                this.k = null;
            }
        }
    }

    private void i() {
        if (this.a.isEnabled()) {
            CSLog.d(this, "releaseHeartBeatTask()");
            if (this.m != null) {
                this.a.getTaskExecutor().removeEnqueuedTask(this.m);
                this.m = null;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void j() {
        if (this.a.isEnabled()) {
            CSLog.d(this, "Firing paused on buffering event");
            if (this.r == StreamSenseState.PLAYING) {
                this.h.h();
                this.h.f();
                a(a(StreamSenseEventType.PAUSE, (HashMap<String, String>) null));
                this.g++;
                this.r = StreamSenseState.PAUSED;
            }
        }
    }

    private void k() {
        if (this.a.isEnabled()) {
            CSLog.d(this, "stopPausedOnBufferingTask()");
            if (this.i != null) {
                this.a.getTaskExecutor().removeEnqueuedTask(this.i);
                this.i = null;
            }
        }
    }

    private void l() {
        if (this.a.isEnabled()) {
            CSLog.d(this, "stopDelayedTransitionTask()");
            if (this.l != null) {
                this.a.getTaskExecutor().removeEnqueuedTask(this.l);
                this.l = null;
            }
        }
    }

    private boolean m() {
        Context appContext = this.a.getAppContext();
        String publisherSecret = this.a.getPublisherSecret();
        String pixelURL = this.a.getPixelURL();
        return appContext == null || publisherSecret == null || publisherSecret.length() == 0 || pixelURL == null || pixelURL.length() == 0;
    }

    private List<HashMap<String, Long>> n() {
        ArrayList arrayList = new ArrayList();
        HashMap map = new HashMap();
        map.put(Constants.HEARTBEAT_PLAYING_TIME_KEY, 60000L);
        map.put(Constants.HEARTBEAT_INTERVAL_KEY, Long.valueOf(Constants.HEARTBEAT_STAGE_ONE_INTERVAL));
        arrayList.add(map);
        HashMap map2 = new HashMap();
        map2.put(Constants.HEARTBEAT_PLAYING_TIME_KEY, null);
        map2.put(Constants.HEARTBEAT_INTERVAL_KEY, 60000L);
        arrayList.add(map2);
        return arrayList;
    }

    protected void a() {
        if (this.a.isEnabled()) {
            CSLog.d(this, "Firing heart beat");
            this.o++;
            a(a(StreamSenseEventType.HEART_BEAT, (HashMap<String, String>) null));
            this.n = -1L;
            d();
        }
    }

    protected void a(HashMap<String, String> map) {
        a(map, true);
    }

    /* JADX WARN: Failed to analyze thrown exceptions
    java.util.ConcurrentModificationException
    	at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1096)
    	at java.base/java.util.ArrayList$Itr.next(ArrayList.java:1050)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.processInstructions(MethodThrowsVisitor.java:130)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.visit(MethodThrowsVisitor.java:68)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.checkInsn(MethodThrowsVisitor.java:178)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.processInstructions(MethodThrowsVisitor.java:131)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.visit(MethodThrowsVisitor.java:68)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.checkInsn(MethodThrowsVisitor.java:178)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.processInstructions(MethodThrowsVisitor.java:131)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.visit(MethodThrowsVisitor.java:68)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.checkInsn(MethodThrowsVisitor.java:178)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.processInstructions(MethodThrowsVisitor.java:131)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.visit(MethodThrowsVisitor.java:68)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.checkInsn(MethodThrowsVisitor.java:178)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.processInstructions(MethodThrowsVisitor.java:131)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.visit(MethodThrowsVisitor.java:68)
     */
    protected void a(HashMap<String, String> map, boolean z) {
        if (this.a.isEnabled()) {
            CSLog.d(this, "dispatch(" + map + ", " + z + ")");
            if (z) {
                d(map);
            }
            if (m()) {
                return;
            }
            this.a.getTaskExecutor().execute((Runnable) new e(this, map, this.c), true);
        }
    }

    public void addListener(StreamSenseListener streamSenseListener) {
        if (this.a.isEnabled()) {
            this.v.add(streamSenseListener);
        }
    }

    protected void b() {
        if (this.a.isEnabled()) {
            CSLog.d(this, "Firing keep alive");
            a(a(StreamSenseEventType.KEEP_ALIVE, (HashMap<String, String>) null));
            this.g++;
        }
    }

    @Deprecated
    public void engageTo(StreamSenseMediaPlayer streamSenseMediaPlayer) {
        streamSenseMediaPlayer.setStreamSense(this);
    }

    @Deprecated
    public void engageTo(StreamSenseVideoView streamSenseVideoView) {
        streamSenseVideoView.setStreamSense(this);
    }

    public HashMap<String, String> exportState() {
        return this.u;
    }

    public StreamSenseClip getClip() {
        return this.h.getClip();
    }

    public int getKeepAliveInterval() {
        return this.x;
    }

    public String getLabel(String str) {
        return this.b.get(str);
    }

    public HashMap<String, String> getLabels() {
        return this.b;
    }

    public int getPauseOnBufferingInterval() {
        return this.y;
    }

    public String getPixelURL() {
        return this.c;
    }

    public StreamSensePlaylist getPlaylist() {
        return this.h;
    }

    public StreamSenseState getState() {
        return this.f;
    }

    public void importState(HashMap<String, String> map) {
        if (this.a.isEnabled()) {
            reset();
            HashMap<String, String> mapMapOfStrings = Utils.mapOfStrings(map);
            this.h.b(mapMapOfStrings, null);
            this.h.getClip().b(mapMapOfStrings, null);
            c(mapMapOfStrings);
            this.g++;
        }
    }

    public boolean isPauseOnBufferingEnabled() {
        return this.j;
    }

    public boolean isPausePlaySwitchDelayEnabled() {
        return this.q;
    }

    public void notify(StreamSenseEventType streamSenseEventType, long j) {
        notify(streamSenseEventType, new HashMap<>(), j);
    }

    /* JADX WARN: Failed to analyze thrown exceptions
    java.util.ConcurrentModificationException
    	at java.base/java.util.ArrayList$Itr.checkForComodification(ArrayList.java:1096)
    	at java.base/java.util.ArrayList$Itr.next(ArrayList.java:1050)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.processInstructions(MethodThrowsVisitor.java:130)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.visit(MethodThrowsVisitor.java:68)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.checkInsn(MethodThrowsVisitor.java:178)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.processInstructions(MethodThrowsVisitor.java:131)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.visit(MethodThrowsVisitor.java:68)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.checkInsn(MethodThrowsVisitor.java:178)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.processInstructions(MethodThrowsVisitor.java:131)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.visit(MethodThrowsVisitor.java:68)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.checkInsn(MethodThrowsVisitor.java:178)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.processInstructions(MethodThrowsVisitor.java:131)
    	at jadx.core.dex.visitors.MethodThrowsVisitor.visit(MethodThrowsVisitor.java:68)
     */
    public void notify(StreamSenseEventType streamSenseEventType, HashMap<String, String> map, long j) {
        if (this.a.isEnabled()) {
            CSLog.d(this, "notify(" + streamSenseEventType + ", " + map + ")");
            StreamSenseState streamSenseStateA = a(streamSenseEventType);
            HashMap<String, String> mapMapOfStrings = Utils.mapOfStrings(map);
            b(mapMapOfStrings);
            if (!mapMapOfStrings.containsKey("ns_st_po")) {
                mapMapOfStrings.put("ns_st_po", String.valueOf(j));
            }
            if (streamSenseEventType != StreamSenseEventType.PLAY && streamSenseEventType != StreamSenseEventType.PAUSE && streamSenseEventType != StreamSenseEventType.BUFFER && streamSenseEventType != StreamSenseEventType.END) {
                HashMap<String, String> mapA = a(streamSenseEventType, mapMapOfStrings);
                mapA.putAll(mapMapOfStrings);
                a(mapA, false);
                this.g++;
                return;
            }
            if (isPausePlaySwitchDelayEnabled() && a(this.f) && a(streamSenseStateA) && (this.f != StreamSenseState.PLAYING || streamSenseStateA != StreamSenseState.PAUSED || this.l != null)) {
                a(streamSenseStateA, mapMapOfStrings, 500L);
            } else {
                a(streamSenseStateA, mapMapOfStrings);
            }
        }
    }

    public void removeListener(StreamSenseListener streamSenseListener) {
        if (this.a.isEnabled()) {
            this.v.remove(streamSenseListener);
        }
    }

    public void reset() {
        reset(null);
    }

    public void reset(Set<String> set) {
        if (this.a.isEnabled()) {
            CSLog.d(this, "Reset()");
            this.h.reset(set);
            this.h.d(0);
            this.h.setPlaylistId(System.currentTimeMillis() + "_1");
            this.h.getClip().reset(set);
            if (set == null || set.isEmpty()) {
                this.b.clear();
            } else {
                StreamSenseUtils.filterMap(this.b, set);
            }
            this.g = 1;
            this.o = 0;
            e();
            f();
            h();
            k();
            l();
            this.f = StreamSenseState.IDLE;
            this.d = -1L;
            this.r = null;
            this.s = Constants.DEFAULT_PLAYERNAME;
            this.t = Constants.STREAMSENSE_VERSION;
            this.u = null;
        }
    }

    public Boolean setClip(HashMap<String, String> map) {
        return setClip(map, false);
    }

    public Boolean setClip(HashMap<String, String> map, boolean z) {
        if (!this.a.isEnabled()) {
            return Boolean.FALSE;
        }
        Boolean bool = Boolean.FALSE;
        if (this.f != StreamSenseState.IDLE) {
            return bool;
        }
        this.h.getClip().reset();
        this.h.getClip().a(Utils.mapOfStrings(map), (StreamSenseState) null);
        if (z) {
            this.h.b();
        }
        return Boolean.TRUE;
    }

    public void setHeartbeatIntervals(List<HashMap<String, Long>> list) {
        if (this.a.isEnabled()) {
            this.w = list;
        }
    }

    public void setKeepAliveInterval(int i) {
        if (this.a.isEnabled()) {
            this.x = i;
        }
    }

    public void setLabel(String str, String str2) {
        if (this.a.isEnabled()) {
            if (str2 == null) {
                this.b.remove(str);
            } else {
                this.b.put(str, str2);
            }
        }
    }

    public void setLabels(HashMap<String, String> map) {
        if (this.a.isEnabled() && map != null) {
            if (this.b == null) {
                this.b = Utils.mapOfStrings(map);
            } else {
                this.b.putAll(Utils.mapOfStrings(map));
            }
        }
    }

    public void setPauseOnBufferingEnabled(boolean z) {
        if (this.a.isEnabled()) {
            this.j = z;
        }
    }

    public void setPauseOnBufferingInterval(int i) {
        if (this.a.isEnabled()) {
            this.y = i;
        }
    }

    public void setPausePlaySwitchDelayEnabled(boolean z) {
        if (this.a.isEnabled()) {
            this.q = z;
        }
    }

    public String setPixelURL(String str) {
        if (!this.a.isEnabled()) {
            return this.c;
        }
        if (str == null || str.length() == 0) {
            return null;
        }
        int iIndexOf = str.indexOf(63);
        if (iIndexOf < 0) {
            str = str + '?';
        } else if (iIndexOf < str.length() - 1) {
            for (String str2 : str.substring(iIndexOf + 1).split("&")) {
                String[] strArrSplit = str2.split("=");
                if (strArrSplit.length == 2) {
                    setLabel(strArrSplit[0], strArrSplit[1]);
                } else if (strArrSplit.length == 1) {
                    setLabel("name", strArrSplit[0]);
                }
            }
            str = str.substring(0, iIndexOf + 1);
        }
        this.c = str;
        return this.c;
    }

    public Boolean setPlaylist(HashMap<String, String> map) {
        if (!this.a.isEnabled()) {
            return Boolean.FALSE;
        }
        Boolean bool = Boolean.FALSE;
        if (this.f != StreamSenseState.IDLE) {
            return bool;
        }
        this.h.i();
        this.h.reset();
        this.h.getClip().reset();
        this.h.a(Utils.mapOfStrings(map), (StreamSenseState) null);
        return Boolean.TRUE;
    }
}
