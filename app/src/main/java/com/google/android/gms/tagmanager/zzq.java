package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class zzq extends zzak {
    private static final String ID = com.google.android.gms.internal.zzad.CONTAINER_VERSION.toString();
    private final String zzYk;

    public zzq(String str) {
        super(ID, new String[0]);
        this.zzYk = str;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzCo() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzI(Map<String, zzag.zza> map) {
        return this.zzYk == null ? zzdf.zzDX() : zzdf.zzQ(this.zzYk);
    }
}
