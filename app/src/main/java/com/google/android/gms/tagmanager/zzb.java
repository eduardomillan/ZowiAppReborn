package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzag;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class zzb extends zzak {
    private static final String ID = com.google.android.gms.internal.zzad.ADVERTISER_ID.toString();
    private final zza zzaVG;

    public zzb(Context context) {
        this(zza.zzaN(context));
    }

    zzb(zza zzaVar) {
        super(ID, new String[0]);
        this.zzaVG = zzaVar;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzCo() {
        return false;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzI(Map<String, zzag.zza> map) {
        String strZzCk = this.zzaVG.zzCk();
        return strZzCk == null ? zzdf.zzDX() : zzdf.zzQ(strZzCk);
    }
}
