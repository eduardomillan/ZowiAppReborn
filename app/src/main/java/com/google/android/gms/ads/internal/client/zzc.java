package com.google.android.gms.ads.internal.client;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.internal.client.zzo;
import com.google.android.gms.internal.zzgr;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzc extends zzo.zza {
    private final AdListener zzsz;

    public zzc(AdListener adListener) {
        this.zzsz = adListener;
    }

    @Override // com.google.android.gms.ads.internal.client.zzo
    public void onAdClosed() {
        this.zzsz.onAdClosed();
    }

    @Override // com.google.android.gms.ads.internal.client.zzo
    public void onAdFailedToLoad(int errorCode) {
        this.zzsz.onAdFailedToLoad(errorCode);
    }

    @Override // com.google.android.gms.ads.internal.client.zzo
    public void onAdLeftApplication() {
        this.zzsz.onAdLeftApplication();
    }

    @Override // com.google.android.gms.ads.internal.client.zzo
    public void onAdLoaded() {
        this.zzsz.onAdLoaded();
    }

    @Override // com.google.android.gms.ads.internal.client.zzo
    public void onAdOpened() {
        this.zzsz.onAdOpened();
    }
}
