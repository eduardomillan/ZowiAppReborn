package com.google.android.gms.ads.internal.reward.client;

import com.google.android.gms.ads.internal.reward.client.zzd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

/* JADX INFO: loaded from: classes.dex */
public class zzg extends zzd.zza {
    private final RewardedVideoAdListener zzHd;

    public zzg(RewardedVideoAdListener rewardedVideoAdListener) {
        this.zzHd = rewardedVideoAdListener;
    }

    @Override // com.google.android.gms.ads.internal.reward.client.zzd
    public void onRewardedVideoAdClosed() {
        if (this.zzHd != null) {
            this.zzHd.onRewardedVideoAdClosed();
        }
    }

    @Override // com.google.android.gms.ads.internal.reward.client.zzd
    public void onRewardedVideoAdFailedToLoad(int errorCode) {
        if (this.zzHd != null) {
            this.zzHd.onRewardedVideoAdFailedToLoad(errorCode);
        }
    }

    @Override // com.google.android.gms.ads.internal.reward.client.zzd
    public void onRewardedVideoAdLeftApplication() {
        if (this.zzHd != null) {
            this.zzHd.onRewardedVideoAdLeftApplication();
        }
    }

    @Override // com.google.android.gms.ads.internal.reward.client.zzd
    public void onRewardedVideoAdLoaded() {
        if (this.zzHd != null) {
            this.zzHd.onRewardedVideoAdLoaded();
        }
    }

    @Override // com.google.android.gms.ads.internal.reward.client.zzd
    public void onRewardedVideoAdOpened() {
        if (this.zzHd != null) {
            this.zzHd.onRewardedVideoAdOpened();
        }
    }

    @Override // com.google.android.gms.ads.internal.reward.client.zzd
    public void onRewardedVideoStarted() {
        if (this.zzHd != null) {
            this.zzHd.onRewardedVideoStarted();
        }
    }

    @Override // com.google.android.gms.ads.internal.reward.client.zzd
    public void zza(zza zzaVar) {
        if (this.zzHd != null) {
            this.zzHd.onRewarded(new zze(zzaVar));
        }
    }
}
