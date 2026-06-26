package com.comscore.streaming;

import com.comscore.utils.Utils;
import java.util.HashMap;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class StreamSenseClip {
    HashMap<String, String> a = new HashMap<>();
    private int b;
    private int c;
    private long d;
    private long e;
    private long f;
    private long g;
    private String h;

    public StreamSenseClip() {
        reset();
    }

    private void a(String str, HashMap<String, String> map) {
        String str2 = map.get(str);
        if (str2 != null) {
            this.a.put(str, str2);
        }
    }

    protected int a() {
        return this.b;
    }

    protected HashMap<String, String> a(StreamSenseEventType streamSenseEventType, HashMap<String, String> map) {
        if (map == null) {
            map = new HashMap<>();
        }
        map.put("ns_st_cn", getClipId());
        map.put("ns_st_bt", String.valueOf(e()));
        if (streamSenseEventType == StreamSenseEventType.PLAY || streamSenseEventType == null) {
            map.put("ns_st_sq", String.valueOf(this.c));
        }
        if (streamSenseEventType == StreamSenseEventType.PAUSE || streamSenseEventType == StreamSenseEventType.END || streamSenseEventType == StreamSenseEventType.KEEP_ALIVE || streamSenseEventType == StreamSenseEventType.HEART_BEAT || streamSenseEventType == null) {
            map.put("ns_st_pt", String.valueOf(f()));
            map.put("ns_st_pc", String.valueOf(this.b));
        }
        map.putAll(getLabels());
        return map;
    }

    protected void a(int i) {
        this.b = i;
    }

    protected void a(long j) {
        this.d = j;
    }

    protected void a(HashMap<String, String> map, StreamSenseState streamSenseState) {
        if (map != null) {
            this.a.putAll(map);
        }
        b(this.a, streamSenseState);
    }

    protected void b() {
        this.b++;
    }

    protected void b(int i) {
        this.c = i;
    }

    protected void b(long j) {
        this.f = j;
    }

    protected void b(HashMap<String, String> map, StreamSenseState streamSenseState) {
        String str;
        String str2;
        String str3;
        String str4 = map.get("ns_st_cn");
        if (str4 != null) {
            setClipId(str4);
            map.remove("ns_st_cn");
        }
        String str5 = map.get("ns_st_bt");
        if (str5 != null) {
            try {
                this.d = Long.parseLong(str5);
                map.remove("ns_st_bt");
            } catch (NumberFormatException e) {
            }
        }
        a("ns_st_cl", map);
        a("ns_st_pn", map);
        a("ns_st_tp", map);
        a("ns_st_ub", map);
        a("ns_st_br", map);
        if ((streamSenseState == StreamSenseState.PLAYING || streamSenseState == null) && (str = map.get("ns_st_sq")) != null) {
            try {
                this.c = Integer.parseInt(str);
                map.remove("ns_st_sq");
            } catch (NumberFormatException e2) {
            }
        }
        if (streamSenseState != StreamSenseState.BUFFERING && (str3 = map.get("ns_st_pt")) != null) {
            try {
                this.f = Long.parseLong(str3);
                map.remove("ns_st_pt");
            } catch (NumberFormatException e3) {
            }
        }
        if ((streamSenseState == StreamSenseState.PAUSED || streamSenseState == StreamSenseState.IDLE || streamSenseState == null) && (str2 = map.get("ns_st_pc")) != null) {
            try {
                this.b = Integer.parseInt(str2);
                map.remove("ns_st_pc");
            } catch (NumberFormatException e4) {
            }
        }
    }

    protected int c() {
        return this.c;
    }

    protected void c(long j) {
        this.g = j;
    }

    protected void d() {
        this.c++;
    }

    protected void d(long j) {
        this.e = j;
    }

    protected long e() {
        long j = this.d;
        return this.e >= 0 ? j + (System.currentTimeMillis() - this.e) : j;
    }

    protected long f() {
        long j = this.f;
        return this.g >= 0 ? j + (System.currentTimeMillis() - this.g) : j;
    }

    protected long g() {
        return this.g;
    }

    public String getClipId() {
        if (Utils.isEmpty(this.h)) {
            setClipId("1");
        }
        return this.h;
    }

    public String getLabel(String str) {
        return this.a.get(str);
    }

    public HashMap<String, String> getLabels() {
        return this.a;
    }

    protected long h() {
        return this.e;
    }

    public void reset() {
        reset(null);
    }

    public void reset(Set<String> set) {
        if (set == null || set.isEmpty()) {
            this.a.clear();
        } else {
            StreamSenseUtils.filterMap(this.a, set);
        }
        if (!this.a.containsKey("ns_st_cl")) {
            this.a.put("ns_st_cl", "0");
        }
        if (!this.a.containsKey("ns_st_pn")) {
            this.a.put("ns_st_pn", "1");
        }
        if (!this.a.containsKey("ns_st_tp")) {
            this.a.put("ns_st_tp", "1");
        }
        a(0);
        b(0);
        a(0L);
        d(-1L);
        b(0L);
        c(-1L);
    }

    public void setClipId(String str) {
        this.h = str;
    }

    public void setLabel(String str, String str2) {
        HashMap<String, String> map = new HashMap<>();
        map.put(str, str2);
        a(map, (StreamSenseState) null);
    }

    public void setLabels(HashMap<String, String> map) {
        a(map, (StreamSenseState) null);
    }
}
