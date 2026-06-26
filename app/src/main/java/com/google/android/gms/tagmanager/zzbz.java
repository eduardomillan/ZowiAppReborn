package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class zzbz extends zzak {
    private static final String ID = com.google.android.gms.internal.zzad.PLATFORM.toString();
    private static final zzag.zza zzaXR = zzdf.zzQ("Android");

    public zzbz() {
        super(ID, new String[0]);
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzCo() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzI(Map<String, zzag.zza> map) {
        return zzaXR;
    }
}
