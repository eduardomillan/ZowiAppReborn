package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
abstract class zzdd extends zzak {
    public zzdd(String str, String... strArr) {
        super(str, strArr);
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzCo() {
        return false;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzI(Map<String, zzag.zza> map) {
        zzK(map);
        return zzdf.zzDX();
    }

    public abstract void zzK(Map<String, zzag.zza> map);
}
