package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class zzai extends zzak {
    private static final String ID = com.google.android.gms.internal.zzad.EVENT.toString();
    private final zzcp zzaVS;

    public zzai(zzcp zzcpVar) {
        super(ID, new String[0]);
        this.zzaVS = zzcpVar;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzCo() {
        return false;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzI(Map<String, zzag.zza> map) {
        String strZzDw = this.zzaVS.zzDw();
        return strZzDw == null ? zzdf.zzDX() : zzdf.zzQ(strZzDw);
    }
}
