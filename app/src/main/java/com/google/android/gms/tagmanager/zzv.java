package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class zzv extends zzak {
    private static final String ID = com.google.android.gms.internal.zzad.CUSTOM_VAR.toString();
    private static final String NAME = com.google.android.gms.internal.zzae.NAME.toString();
    private static final String zzaWE = com.google.android.gms.internal.zzae.DEFAULT_VALUE.toString();
    private final DataLayer zzaVR;

    public zzv(DataLayer dataLayer) {
        super(ID, NAME);
        this.zzaVR = dataLayer;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzCo() {
        return false;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzI(Map<String, zzag.zza> map) {
        Object obj = this.zzaVR.get(zzdf.zzg(map.get(NAME)));
        if (obj != null) {
            return zzdf.zzQ(obj);
        }
        zzag.zza zzaVar = map.get(zzaWE);
        return zzaVar != null ? zzaVar : zzdf.zzDX();
    }
}
