package com.google.android.gms.internal;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzdq implements zzdk {
    static final Map<String, Integer> zzxS = new HashMap();
    private final com.google.android.gms.ads.internal.zze zzxQ;
    private final zzfc zzxR;

    static {
        zzxS.put("resize", 1);
        zzxS.put("playVideo", 2);
        zzxS.put("storePicture", 3);
        zzxS.put("createCalendarEvent", 4);
        zzxS.put("setOrientationProperties", 5);
        zzxS.put("closeResizedAd", 6);
    }

    public zzdq(com.google.android.gms.ads.internal.zze zzeVar, zzfc zzfcVar) {
        this.zzxQ = zzeVar;
        this.zzxR = zzfcVar;
    }

    @Override // com.google.android.gms.internal.zzdk
    public void zza(zziz zzizVar, Map<String, String> map) {
        int iIntValue = zzxS.get(map.get("a")).intValue();
        if (iIntValue != 5 && this.zzxQ != null && !this.zzxQ.zzbe()) {
            this.zzxQ.zzp(null);
        }
        switch (iIntValue) {
            case 1:
                this.zzxR.zzg(map);
                break;
            case 2:
            default:
                com.google.android.gms.ads.internal.util.client.zzb.zzaG("Unknown MRAID command called.");
                break;
            case 3:
                new zzfe(zzizVar, map).execute();
                break;
            case 4:
                new zzfb(zzizVar, map).execute();
                break;
            case 5:
                new zzfd(zzizVar, map).execute();
                break;
            case 6:
                this.zzxR.zzn(true);
                break;
        }
    }
}
