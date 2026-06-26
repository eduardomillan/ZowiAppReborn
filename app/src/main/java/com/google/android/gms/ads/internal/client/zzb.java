package com.google.android.gms.ads.internal.client;

import com.google.android.gms.ads.internal.client.zzn;
import com.google.android.gms.internal.zzgr;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzb extends zzn.zza {
    private final zza zzsy;

    public zzb(zza zzaVar) {
        this.zzsy = zzaVar;
    }

    @Override // com.google.android.gms.ads.internal.client.zzn
    public void onAdClicked() {
        this.zzsy.onAdClicked();
    }
}
