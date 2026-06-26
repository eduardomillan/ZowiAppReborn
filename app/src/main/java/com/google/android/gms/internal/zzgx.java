package com.google.android.gms.internal;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzgr
class zzgx {
    private final String zzBY;
    private int zzDv;
    private String zzF;
    private final List<String> zzGl;
    private final List<String> zzGm;
    private final String zzGn;
    private final String zzGo;
    private final String zzGp;
    private final String zzGq;
    private final boolean zzGr;

    public zzgx(int i, Map<String, String> map) {
        this.zzF = map.get("url");
        this.zzGo = map.get("base_uri");
        this.zzGp = map.get("post_parameters");
        this.zzGr = parseBoolean(map.get("drt_include"));
        this.zzGn = map.get("activation_overlay_url");
        this.zzGm = zzat(map.get("check_packages"));
        this.zzBY = map.get("request_id");
        this.zzGq = map.get("type");
        this.zzGl = zzat(map.get("errors"));
        this.zzDv = i;
    }

    private static boolean parseBoolean(String bool) {
        return bool != null && (bool.equals("1") || bool.equals("true"));
    }

    private List<String> zzat(String str) {
        if (str == null) {
            return null;
        }
        return Arrays.asList(str.split(","));
    }

    public int getErrorCode() {
        return this.zzDv;
    }

    public String getRequestId() {
        return this.zzBY;
    }

    public String getType() {
        return this.zzGq;
    }

    public String getUrl() {
        return this.zzF;
    }

    public void setUrl(String url) {
        this.zzF = url;
    }

    public List<String> zzfU() {
        return this.zzGl;
    }

    public String zzfV() {
        return this.zzGp;
    }

    public boolean zzfW() {
        return this.zzGr;
    }
}
