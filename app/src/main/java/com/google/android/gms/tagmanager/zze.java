package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzag;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class zze extends zzak {
    private static final String ID = com.google.android.gms.internal.zzad.ADWORDS_CLICK_REFERRER.toString();
    private static final String zzaVH = com.google.android.gms.internal.zzae.COMPONENT.toString();
    private static final String zzaVI = com.google.android.gms.internal.zzae.CONVERSION_ID.toString();
    private final Context context;

    public zze(Context context) {
        super(ID, zzaVI);
        this.context = context;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzCo() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzI(Map<String, zzag.zza> map) {
        zzag.zza zzaVar = map.get(zzaVI);
        if (zzaVar == null) {
            return zzdf.zzDX();
        }
        String strZzg = zzdf.zzg(zzaVar);
        zzag.zza zzaVar2 = map.get(zzaVH);
        String strZzf = zzax.zzf(this.context, strZzg, zzaVar2 != null ? zzdf.zzg(zzaVar2) : null);
        return strZzf != null ? zzdf.zzQ(strZzf) : zzdf.zzDX();
    }
}
