package com.comscore.streaming;

import com.comscore.utils.Date;
import com.comscore.utils.Utils;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class StreamingTag {
    private int d = 0;
    private long b = 0;
    private long c = 0;
    private HashMap<String, String> e = null;
    private boolean g = false;
    private p f = p.None;
    private StreamSense a = new StreamSense();

    public StreamingTag() {
        this.a.setLabel("ns_st_it", "r");
    }

    private HashMap<String, String> a(HashMap<String, String> map) {
        HashMap<String, String> mapMapOfStrings = Utils.mapOfStrings(map);
        if (!mapMapOfStrings.containsKey("ns_st_ci")) {
            mapMapOfStrings.put("ns_st_ci", "0");
        }
        if (!mapMapOfStrings.containsKey("c3")) {
            mapMapOfStrings.put("c3", "*null");
        }
        if (!mapMapOfStrings.containsKey("c4")) {
            mapMapOfStrings.put("c4", "*null");
        }
        if (!mapMapOfStrings.containsKey("c6")) {
            mapMapOfStrings.put("c6", "*null");
        }
        return mapMapOfStrings;
    }

    private void a(long j) {
        if (this.a.getState() != StreamSenseState.IDLE && this.a.getState() != StreamSenseState.PAUSED) {
            this.a.notify(StreamSenseEventType.END, b(j));
        } else if (this.a.getState() == StreamSenseState.PAUSED) {
            this.a.notify(StreamSenseEventType.END, this.c);
        }
    }

    private void a(long j, HashMap<String, String> map) {
        a(j);
        this.d++;
        if (!map.containsKey("ns_st_cn")) {
            map.put("ns_st_cn", String.valueOf(this.d));
        }
        if (!map.containsKey("ns_st_pn")) {
            map.put("ns_st_pn", "1");
        }
        if (!map.containsKey("ns_st_tp")) {
            map.put("ns_st_tp", "0");
        }
        this.a.setClip(map);
        this.e = map;
        this.b = j;
        this.c = 0L;
        this.a.notify(StreamSenseEventType.PLAY, this.c);
    }

    private void a(HashMap<String, String> map, p pVar) {
        long jUnixTime = Date.unixTime();
        HashMap<String, String> mapA = a(Utils.mapOfStrings(map));
        if (this.f == p.None) {
            this.f = pVar;
        }
        if (this.g && this.f == pVar && c(mapA)) {
            this.a.getClip().setLabels(mapA);
            if (this.a.getState() != StreamSenseState.PLAYING) {
                this.b = jUnixTime;
                this.a.notify(StreamSenseEventType.PLAY, this.c);
            }
        } else {
            a(jUnixTime, mapA);
        }
        this.g = true;
        this.f = pVar;
    }

    private boolean a(String str, HashMap<String, String> map, HashMap<String, String> map2) {
        if (str != null) {
            if ((map2 != null) & (map != null)) {
                String str2 = map.get(str);
                String str3 = map2.get(str);
                if (str2 != null && str3 != null) {
                    return str2.equals(str3);
                }
            }
        }
        return false;
    }

    private long b(long j) {
        if (this.b <= 0 || j < this.b) {
            this.c = 0L;
        } else {
            this.c += j - this.b;
        }
        return this.c;
    }

    private void b(HashMap<String, String> map) {
        long jUnixTime = Date.unixTime();
        a(jUnixTime);
        this.d++;
        HashMap<String, String> mapA = a(Utils.mapOfStrings(map));
        if (!mapA.containsKey("ns_st_cn")) {
            mapA.put("ns_st_cn", String.valueOf(this.d));
        }
        if (!mapA.containsKey("ns_st_pn")) {
            mapA.put("ns_st_pn", "1");
        }
        if (!mapA.containsKey("ns_st_tp")) {
            mapA.put("ns_st_tp", "1");
        }
        if (!mapA.containsKey("ns_st_ad")) {
            mapA.put("ns_st_ad", "1");
        }
        this.a.setClip(mapA);
        this.c = 0L;
        this.a.notify(StreamSenseEventType.PLAY, this.c);
        this.b = jUnixTime;
        this.g = false;
    }

    private boolean c(HashMap<String, String> map) {
        HashMap<String, String> mapA = a(Utils.mapOfStrings(map));
        return a("ns_st_ci", this.e, mapA) && a("c3", this.e, mapA) && a("c4", this.e, mapA) && a("c6", this.e, mapA);
    }

    @Deprecated
    public void playAdvertisement() {
        HashMap<String, String> map = new HashMap<>();
        map.put("ns_st_ct", "va");
        b(map);
    }

    public void playAudioAdvertisement() {
        playAudioAdvertisement(new HashMap<>());
    }

    public void playAudioAdvertisement(HashMap<String, String> map) {
        HashMap<String, String> map2 = new HashMap<>();
        if (map != null) {
            map2.putAll(map);
        }
        if (!map2.containsKey("ns_st_ct")) {
            map2.put("ns_st_ct", "aa");
        }
        b(map2);
    }

    public void playAudioContentPart(HashMap<String, String> map) {
        HashMap<String, String> map2 = new HashMap<>();
        if (map != null) {
            map2.putAll(map);
        }
        if (!map2.containsKey("ns_st_ct")) {
            map2.put("ns_st_ct", "ac");
        }
        a(map2, p.AudioContent);
    }

    @Deprecated
    public void playContentPart(HashMap<String, String> map) {
        HashMap<String, String> map2 = new HashMap<>();
        if (map != null) {
            map2.putAll(map);
        }
        if (!map2.containsKey("ns_st_ct")) {
            map2.put("ns_st_ct", "vc");
        }
        a(map2, p.VideoContent);
    }

    public void playVideoAdvertisement() {
        playVideoAdvertisement(new HashMap<>());
    }

    public void playVideoAdvertisement(HashMap<String, String> map) {
        HashMap<String, String> map2 = new HashMap<>();
        if (map != null) {
            map2.putAll(map);
        }
        if (!map2.containsKey("ns_st_ct")) {
            map2.put("ns_st_ct", "va");
        }
        b(map2);
    }

    public void playVideoContentPart(HashMap<String, String> map) {
        HashMap<String, String> map2 = new HashMap<>();
        if (map != null) {
            map2.putAll(map);
        }
        if (!map2.containsKey("ns_st_ct")) {
            map2.put("ns_st_ct", "vc");
        }
        a(map2, p.VideoContent);
    }

    public void stop() {
        this.a.notify(StreamSenseEventType.PAUSE, b(Date.unixTime()));
    }
}
