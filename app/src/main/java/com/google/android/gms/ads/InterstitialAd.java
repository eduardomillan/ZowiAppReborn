package com.google.android.gms.ads;

import android.content.Context;
import com.google.android.gms.ads.internal.client.zzaa;
import com.google.android.gms.ads.purchase.InAppPurchaseListener;
import com.google.android.gms.ads.purchase.PlayStorePurchaseListener;

/* JADX INFO: loaded from: classes.dex */
public final class InterstitialAd {
    private final zzaa zznU;

    public InterstitialAd(Context context) {
        this.zznU = new zzaa(context);
    }

    public AdListener getAdListener() {
        return this.zznU.getAdListener();
    }

    public String getAdUnitId() {
        return this.zznU.getAdUnitId();
    }

    public InAppPurchaseListener getInAppPurchaseListener() {
        return this.zznU.getInAppPurchaseListener();
    }

    public String getMediationAdapterClassName() {
        return this.zznU.getMediationAdapterClassName();
    }

    public boolean isLoaded() {
        return this.zznU.isLoaded();
    }

    public boolean isLoading() {
        return this.zznU.isLoading();
    }

    public void loadAd(AdRequest adRequest) {
        this.zznU.zza(adRequest.zzaF());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void setAdListener(AdListener adListener) {
        this.zznU.setAdListener(adListener);
        if (adListener != 0 && (adListener instanceof com.google.android.gms.ads.internal.client.zza)) {
            this.zznU.zza((com.google.android.gms.ads.internal.client.zza) adListener);
        } else if (adListener == 0) {
            this.zznU.zza((com.google.android.gms.ads.internal.client.zza) null);
        }
    }

    public void setAdUnitId(String adUnitId) {
        this.zznU.setAdUnitId(adUnitId);
    }

    public void setInAppPurchaseListener(InAppPurchaseListener inAppPurchaseListener) {
        this.zznU.setInAppPurchaseListener(inAppPurchaseListener);
    }

    public void setPlayStorePurchaseParams(PlayStorePurchaseListener playStorePurchaseListener, String publicKey) {
        this.zznU.setPlayStorePurchaseParams(playStorePurchaseListener, publicKey);
    }

    public void show() {
        this.zznU.show();
    }
}
