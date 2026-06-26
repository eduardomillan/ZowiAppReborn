package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.ads.purchase.InAppPurchase;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzga implements InAppPurchase {
    private final zzfr zzCM;

    public zzga(zzfr zzfrVar) {
        this.zzCM = zzfrVar;
    }

    @Override // com.google.android.gms.ads.purchase.InAppPurchase
    public String getProductId() {
        try {
            return this.zzCM.getProductId();
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not forward getProductId to InAppPurchase", e);
            return null;
        }
    }

    @Override // com.google.android.gms.ads.purchase.InAppPurchase
    public void recordPlayBillingResolution(int billingResponseCode) {
        try {
            this.zzCM.recordPlayBillingResolution(billingResponseCode);
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not forward recordPlayBillingResolution to InAppPurchase", e);
        }
    }

    @Override // com.google.android.gms.ads.purchase.InAppPurchase
    public void recordResolution(int resolution) {
        try {
            this.zzCM.recordResolution(resolution);
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not forward recordResolution to InAppPurchase", e);
        }
    }
}
