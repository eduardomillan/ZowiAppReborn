package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class zzbi extends zzak {
    private static final String ID = com.google.android.gms.internal.zzad.LOWERCASE_STRING.toString();
    private static final String zzaWU = com.google.android.gms.internal.zzae.ARG0.toString();

    public zzbi() {
        super(ID, zzaWU);
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzCo() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzI(Map<String, zzag.zza> map) {
        return zzdf.zzQ(zzdf.zzg(map.get(zzaWU)).toLowerCase());
    }
}
