package com.google.android.gms.internal;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzdw implements zzdk {
    @Override // com.google.android.gms.internal.zzdk
    public void zza(zziz zzizVar, Map<String, String> map) {
        int i;
        zzdu zzduVarZzbI = com.google.android.gms.ads.internal.zzp.zzbI();
        if (map.containsKey("abort")) {
            if (zzduVarZzbI.zza(zzizVar)) {
                return;
            }
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Precache abort but no preload task running.");
            return;
        }
        String str = map.get("src");
        if (str == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Precache video action is missing the src parameter.");
            return;
        }
        try {
            i = Integer.parseInt(map.get("player"));
        } catch (NumberFormatException e) {
            i = 0;
        }
        String str2 = map.containsKey("mimetype") ? map.get("mimetype") : "";
        if (zzduVarZzbI.zzb(zzizVar)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Precache task already running.");
        } else {
            com.google.android.gms.common.internal.zzb.zzs(zzizVar.zzhb());
            new zzdt(zzizVar, zzizVar.zzhb().zzoG.zza(zzizVar, i, str2), str).zzfu();
        }
    }
}
