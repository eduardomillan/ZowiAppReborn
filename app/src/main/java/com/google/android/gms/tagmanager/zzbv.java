package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
abstract class zzbv extends zzca {
    public zzbv(String str) {
        super(str);
    }

    @Override // com.google.android.gms.tagmanager.zzca
    protected boolean zza(zzag.zza zzaVar, zzag.zza zzaVar2, Map<String, zzag.zza> map) {
        zzde zzdeVarZzh = zzdf.zzh(zzaVar);
        zzde zzdeVarZzh2 = zzdf.zzh(zzaVar2);
        if (zzdeVarZzh == zzdf.zzDV() || zzdeVarZzh2 == zzdf.zzDV()) {
            return false;
        }
        return zza(zzdeVarZzh, zzdeVarZzh2, map);
    }

    protected abstract boolean zza(zzde zzdeVar, zzde zzdeVar2, Map<String, zzag.zza> map);
}
