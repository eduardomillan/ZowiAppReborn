package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzag;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class zzc extends zzak {
    private static final String ID = com.google.android.gms.internal.zzad.ADVERTISING_TRACKING_ENABLED.toString();
    private final zza zzaVG;

    public zzc(Context context) {
        this(zza.zzaN(context));
    }

    zzc(zza zzaVar) {
        super(ID, new String[0]);
        this.zzaVG = zzaVar;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzCo() {
        return false;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzI(Map<String, zzag.zza> map) {
        return zzdf.zzQ(Boolean.valueOf(!this.zzaVG.isLimitAdTrackingEnabled()));
    }
}
