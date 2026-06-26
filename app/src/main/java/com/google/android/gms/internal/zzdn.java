package com.google.android.gms.internal;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzdn implements zzdk {
    private final zzdo zzxO;

    public zzdn(zzdo zzdoVar) {
        this.zzxO = zzdoVar;
    }

    @Override // com.google.android.gms.internal.zzdk
    public void zza(zziz zzizVar, Map<String, String> map) {
        boolean zEquals = "1".equals(map.get("transparentBackground"));
        boolean zEquals2 = "1".equals(map.get("blur"));
        try {
        } catch (NumberFormatException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Fail to parse float", e);
        }
        float f = map.get("blurRadius") != null ? Float.parseFloat(map.get("blurRadius")) : 0.0f;
        this.zzxO.zzd(zEquals);
        this.zzxO.zza(zEquals2, f);
    }
}
