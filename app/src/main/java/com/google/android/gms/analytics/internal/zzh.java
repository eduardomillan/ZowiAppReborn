package com.google.android.gms.analytics.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class zzh {
    private final long zzMY;
    private final String zzMZ;
    private final String zzMj;
    private final boolean zzNa;
    private long zzNb;
    private final Map<String, String> zzvS;

    public zzh(long j, String str, String str2, boolean z, long j2, Map<String, String> map) {
        com.google.android.gms.common.internal.zzx.zzcr(str);
        com.google.android.gms.common.internal.zzx.zzcr(str2);
        this.zzMY = j;
        this.zzMj = str;
        this.zzMZ = str2;
        this.zzNa = z;
        this.zzNb = j2;
        if (map != null) {
            this.zzvS = new HashMap(map);
        } else {
            this.zzvS = Collections.emptyMap();
        }
    }

    public String getClientId() {
        return this.zzMj;
    }

    public long zziM() {
        return this.zzMY;
    }

    public String zziN() {
        return this.zzMZ;
    }

    public boolean zziO() {
        return this.zzNa;
    }

    public long zziP() {
        return this.zzNb;
    }

    public Map<String, String> zzn() {
        return this.zzvS;
    }

    public void zzn(long j) {
        this.zzNb = j;
    }
}
