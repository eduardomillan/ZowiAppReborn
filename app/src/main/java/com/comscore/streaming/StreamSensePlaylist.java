package com.comscore.streaming;

import com.comscore.utils.CSLog;
import java.util.HashMap;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class StreamSensePlaylist {
    private StreamSenseClip a;
    private String b;
    private int c;
    private int d;
    private int e;
    private long f;
    private long g;
    private int i;
    private boolean j = false;
    private HashMap<String, String> h = new HashMap<>();

    public StreamSensePlaylist() {
        this.a = null;
        this.a = new StreamSenseClip();
        reset();
    }

    protected int a() {
        return this.c;
    }

    protected HashMap<String, String> a(StreamSenseEventType streamSenseEventType, HashMap<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        map.put("ns_st_bp", String.valueOf(c()));
        map.put("ns_st_sp", String.valueOf(this.c));
        map.put("ns_st_id", String.valueOf(this.b));
        if (this.e > 0) {
            map.put("ns_st_bc", String.valueOf(this.e));
        }
        if (streamSenseEventType == StreamSenseEventType.PAUSE || streamSenseEventType == StreamSenseEventType.END || streamSenseEventType == StreamSenseEventType.KEEP_ALIVE || streamSenseEventType == StreamSenseEventType.HEART_BEAT || streamSenseEventType == null) {
            map.put("ns_st_pa", String.valueOf(d()));
            map.put("ns_st_pp", String.valueOf(this.d));
        }
        if ((streamSenseEventType == StreamSenseEventType.PLAY || streamSenseEventType == null) && !j()) {
            map.put("ns_st_pb", "1");
            a(true);
        }
        map.putAll(getLabels());
        return map;
    }

    protected void a(int i) {
        this.c = i;
    }

    protected void a(long j) {
        CSLog.d(this, "addPlaybackTime(" + j + ")");
        if (this.a.g() >= 0) {
            long jG = j - this.a.g();
            this.a.c(-1L);
            this.a.b(this.a.f() + jG);
            d(d() + jG);
            CSLog.d(this, "addPlaybackTime(" + j + ") ->" + jG);
        }
    }

    protected void a(HashMap<String, String> map, StreamSenseState streamSenseState) {
        if (map != null) {
            this.h.putAll(map);
        }
        b(this.h, streamSenseState);
    }

    protected void a(boolean z) {
        this.j = z;
    }

    protected void b() {
        this.c++;
    }

    protected void b(int i) {
        this.d = i;
    }

    protected void b(long j) {
        CSLog.d(this, "addBufferingTime(" + j + ")");
        if (this.a.h() >= 0) {
            long jH = j - this.a.h();
            this.a.d(-1L);
            this.a.a(this.a.e() + jH);
            c(c() + jH);
            CSLog.d(this, "addBufferingTime(" + j + ") ->" + jH);
        }
    }

    protected void b(HashMap<String, String> map, StreamSenseState streamSenseState) {
        String str;
        String str2;
        String str3 = map.get("ns_st_sp");
        if (str3 != null) {
            try {
                this.c = Integer.parseInt(str3);
                map.remove("ns_st_sp");
            } catch (NumberFormatException e) {
            }
        }
        String str4 = map.get("ns_st_bc");
        if (str4 != null) {
            try {
                this.e = Integer.parseInt(str4);
                map.remove("ns_st_bc");
            } catch (NumberFormatException e2) {
            }
        }
        String str5 = map.get("ns_st_bp");
        if (str5 != null) {
            try {
                this.f = Long.parseLong(str5);
                map.remove("ns_st_bp");
            } catch (NumberFormatException e3) {
            }
        }
        String str6 = map.get("ns_st_id");
        if (str6 != null) {
            this.b = str6;
            map.remove("ns_st_id");
        }
        if (streamSenseState != StreamSenseState.BUFFERING && (str2 = map.get("ns_st_pa")) != null) {
            try {
                this.g = Long.parseLong(str2);
                map.remove("ns_st_pa");
            } catch (NumberFormatException e4) {
            }
        }
        if ((streamSenseState == StreamSenseState.PAUSED || streamSenseState == StreamSenseState.IDLE || streamSenseState == null) && (str = map.get("ns_st_pp")) != null) {
            try {
                this.d = Integer.parseInt(str);
                map.remove("ns_st_pp");
            } catch (NumberFormatException e5) {
            }
        }
    }

    protected long c() {
        long j = this.f;
        return this.a.h() >= 0 ? j + (System.currentTimeMillis() - this.a.h()) : j;
    }

    protected void c(int i) {
        this.e = i;
    }

    protected void c(long j) {
        this.f = j;
    }

    protected long d() {
        long j = this.g;
        return this.a.g() >= 0 ? j + (System.currentTimeMillis() - this.a.g()) : j;
    }

    protected void d(int i) {
        this.i = i;
    }

    protected void d(long j) {
        this.g = j;
    }

    protected int e() {
        return this.d;
    }

    protected void f() {
        this.d++;
        this.a.b();
    }

    protected int g() {
        return this.e;
    }

    public StreamSenseClip getClip() {
        return this.a;
    }

    public String getLabel(String str) {
        return this.h.get(str);
    }

    public HashMap<String, String> getLabels() {
        return this.h;
    }

    public String getPlaylistId() {
        return this.b;
    }

    protected void h() {
        this.e++;
    }

    protected void i() {
        this.i++;
    }

    protected boolean j() {
        return this.j;
    }

    public void reset() {
        reset(null);
    }

    public void reset(Set<String> set) {
        if (set == null || set.isEmpty()) {
            this.h.clear();
        } else {
            StreamSenseUtils.filterMap(this.h, set);
        }
        setPlaylistId(System.currentTimeMillis() + "_" + this.i);
        c(0L);
        d(0L);
        b(0);
        a(0);
        c(0);
        this.j = false;
    }

    public void setLabel(String str, String str2) {
        HashMap<String, String> map = new HashMap<>();
        map.put(str, str2);
        a(map, (StreamSenseState) null);
    }

    public void setLabels(HashMap<String, String> map) {
        a(map, (StreamSenseState) null);
    }

    public void setPlaylistId(String str) {
        this.b = str;
    }
}
