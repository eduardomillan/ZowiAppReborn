package com.google.android.gms.ads.internal.purchase;

import android.content.Intent;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.internal.zzgr;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzk {
    private final String zztG;

    public zzk(String str) {
        this.zztG = str;
    }

    public boolean zza(String str, int i, Intent intent) {
        if (str == null || intent == null) {
            return false;
        }
        String strZze = zzp.zzbF().zze(intent);
        String strZzf = zzp.zzbF().zzf(intent);
        if (strZze == null || strZzf == null) {
            return false;
        }
        if (!str.equals(zzp.zzbF().zzao(strZze))) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Developer payload not match.");
            return false;
        }
        if (this.zztG == null || zzl.zzc(this.zztG, strZze, strZzf)) {
            return true;
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaH("Fail to verify signature.");
        return false;
    }

    public String zzfq() {
        return zzp.zzbv().zzgD();
    }
}
