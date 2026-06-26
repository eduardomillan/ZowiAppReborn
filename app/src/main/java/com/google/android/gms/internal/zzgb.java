package com.google.android.gms.internal;

import com.google.android.gms.ads.purchase.PlayStorePurchaseListener;
import com.google.android.gms.internal.zzfw;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzgb extends zzfw.zza {
    private final PlayStorePurchaseListener zztJ;

    public zzgb(PlayStorePurchaseListener playStorePurchaseListener) {
        this.zztJ = playStorePurchaseListener;
    }

    @Override // com.google.android.gms.internal.zzfw
    public boolean isValidPurchase(String productId) {
        return this.zztJ.isValidPurchase(productId);
    }

    @Override // com.google.android.gms.internal.zzfw
    public void zza(zzfv zzfvVar) {
        this.zztJ.onInAppPurchaseFinished(new zzfz(zzfvVar));
    }
}
