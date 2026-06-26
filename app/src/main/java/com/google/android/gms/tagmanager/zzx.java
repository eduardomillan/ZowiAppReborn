package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class zzx extends zzdd {
    private static final String ID = com.google.android.gms.internal.zzad.DATA_LAYER_WRITE.toString();
    private static final String VALUE = com.google.android.gms.internal.zzae.VALUE.toString();
    private static final String zzaWP = com.google.android.gms.internal.zzae.CLEAR_PERSISTENT_DATA_LAYER_PREFIX.toString();
    private final DataLayer zzaVR;

    public zzx(DataLayer dataLayer) {
        super(ID, VALUE);
        this.zzaVR = dataLayer;
    }

    private void zza(zzag.zza zzaVar) {
        String strZzg;
        if (zzaVar == null || zzaVar == zzdf.zzDR() || (strZzg = zzdf.zzg(zzaVar)) == zzdf.zzDW()) {
            return;
        }
        this.zzaVR.zzeI(strZzg);
    }

    private void zzb(zzag.zza zzaVar) {
        if (zzaVar == null || zzaVar == zzdf.zzDR()) {
            return;
        }
        Object objZzl = zzdf.zzl(zzaVar);
        if (objZzl instanceof List) {
            for (Object obj : (List) objZzl) {
                if (obj instanceof Map) {
                    this.zzaVR.push((Map) obj);
                }
            }
        }
    }

    @Override // com.google.android.gms.tagmanager.zzdd
    public void zzK(Map<String, zzag.zza> map) {
        zzb(map.get(VALUE));
        zza(map.get(zzaWP));
    }
}
