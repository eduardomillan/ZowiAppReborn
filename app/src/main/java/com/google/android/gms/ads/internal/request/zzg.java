package com.google.android.gms.ads.internal.request;

import com.google.android.gms.ads.internal.request.zzc;
import com.google.android.gms.ads.internal.request.zzk;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public final class zzg extends zzk.zza {
    private final WeakReference<zzc.zza> zzEI;

    public zzg(zzc.zza zzaVar) {
        this.zzEI = new WeakReference<>(zzaVar);
    }

    @Override // com.google.android.gms.ads.internal.request.zzk
    public void zzb(AdResponseParcel adResponseParcel) {
        zzc.zza zzaVar = this.zzEI.get();
        if (zzaVar != null) {
            zzaVar.zzb(adResponseParcel);
        }
    }
}
