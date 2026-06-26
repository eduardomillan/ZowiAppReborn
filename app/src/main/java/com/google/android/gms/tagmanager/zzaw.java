package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzag;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class zzaw extends zzak {
    private static final String ID = com.google.android.gms.internal.zzad.INSTALL_REFERRER.toString();
    private static final String zzaVH = com.google.android.gms.internal.zzae.COMPONENT.toString();
    private final Context context;

    public zzaw(Context context) {
        super(ID, new String[0]);
        this.context = context;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzCo() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzI(Map<String, zzag.zza> map) {
        String strZzn = zzax.zzn(this.context, map.get(zzaVH) != null ? zzdf.zzg(map.get(zzaVH)) : null);
        return strZzn != null ? zzdf.zzQ(strZzn) : zzdf.zzDX();
    }
}
