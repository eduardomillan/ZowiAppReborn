package com.google.android.gms.internal;

import com.google.android.gms.ads.purchase.InAppPurchaseListener;
import com.google.android.gms.internal.zzfs;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzfx extends zzfs.zza {
    private final InAppPurchaseListener zztI;

    public zzfx(InAppPurchaseListener inAppPurchaseListener) {
        this.zztI = inAppPurchaseListener;
    }

    @Override // com.google.android.gms.internal.zzfs
    public void zza(zzfr zzfrVar) {
        this.zztI.onInAppPurchaseRequested(new zzga(zzfrVar));
    }
}
