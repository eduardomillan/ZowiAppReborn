package com.google.android.gms.internal;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzfd {
    private final boolean zzAq;
    private final String zzAr;
    private final zziz zzoM;

    public zzfd(zziz zzizVar, Map<String, String> map) {
        this.zzoM = zzizVar;
        this.zzAr = map.get("forceOrientation");
        if (map.containsKey("allowOrientationChange")) {
            this.zzAq = Boolean.parseBoolean(map.get("allowOrientationChange"));
        } else {
            this.zzAq = true;
        }
    }

    public void execute() {
        if (this.zzoM == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("AdWebView is null");
        } else {
            this.zzoM.setRequestedOrientation("portrait".equalsIgnoreCase(this.zzAr) ? com.google.android.gms.ads.internal.zzp.zzbx().zzgH() : "landscape".equalsIgnoreCase(this.zzAr) ? com.google.android.gms.ads.internal.zzp.zzbx().zzgG() : this.zzAq ? -1 : com.google.android.gms.ads.internal.zzp.zzbx().zzgI());
        }
    }
}
