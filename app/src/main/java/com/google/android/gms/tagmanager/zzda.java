package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class zzda extends zzak {
    private static final String ID = com.google.android.gms.internal.zzad.TIME.toString();

    public zzda() {
        super(ID, new String[0]);
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzCo() {
        return false;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzI(Map<String, zzag.zza> map) {
        return zzdf.zzQ(Long.valueOf(System.currentTimeMillis()));
    }
}
