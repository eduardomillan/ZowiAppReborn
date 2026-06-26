package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
abstract class zzcz extends zzca {
    public zzcz(String str) {
        super(str);
    }

    @Override // com.google.android.gms.tagmanager.zzca
    protected boolean zza(zzag.zza zzaVar, zzag.zza zzaVar2, Map<String, zzag.zza> map) {
        String strZzg = zzdf.zzg(zzaVar);
        String strZzg2 = zzdf.zzg(zzaVar2);
        if (strZzg == zzdf.zzDW() || strZzg2 == zzdf.zzDW()) {
            return false;
        }
        return zza(strZzg, strZzg2, map);
    }

    protected abstract boolean zza(String str, String str2, Map<String, zzag.zza> map);
}
