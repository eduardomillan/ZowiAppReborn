package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.reward.client.RewardedVideoAdRequestParcel;
import com.google.android.gms.ads.internal.reward.client.zzb;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;

/* JADX INFO: loaded from: classes.dex */
public class zzhf extends zzb.zza {
    private final Context mContext;
    private final zzhg zzGW;
    private final VersionInfoParcel zzpb;
    private final Object zzpd = new Object();

    public zzhf(Context context, zzem zzemVar, VersionInfoParcel versionInfoParcel) {
        this.mContext = context;
        this.zzpb = versionInfoParcel;
        this.zzGW = new zzhg(context, AdSizeParcel.zzcC(), zzemVar, versionInfoParcel);
    }

    @Override // com.google.android.gms.ads.internal.reward.client.zzb
    public void destroy() {
        synchronized (this.zzpd) {
            this.zzGW.destroy();
        }
    }

    @Override // com.google.android.gms.ads.internal.reward.client.zzb
    public boolean isLoaded() {
        boolean zIsLoaded;
        synchronized (this.zzpd) {
            zIsLoaded = this.zzGW.isLoaded();
        }
        return zIsLoaded;
    }

    @Override // com.google.android.gms.ads.internal.reward.client.zzb
    public void pause() {
        synchronized (this.zzpd) {
            this.zzGW.pause();
        }
    }

    @Override // com.google.android.gms.ads.internal.reward.client.zzb
    public void resume() {
        synchronized (this.zzpd) {
            this.zzGW.resume();
        }
    }

    @Override // com.google.android.gms.ads.internal.reward.client.zzb
    public void setUserId(String userId) {
        synchronized (this.zzpd) {
            this.zzGW.setUserId(userId);
        }
    }

    @Override // com.google.android.gms.ads.internal.reward.client.zzb
    public void show() {
        synchronized (this.zzpd) {
            this.zzGW.zzga();
        }
    }

    @Override // com.google.android.gms.ads.internal.reward.client.zzb
    public void zza(RewardedVideoAdRequestParcel rewardedVideoAdRequestParcel) {
        synchronized (this.zzpd) {
            this.zzGW.zza(rewardedVideoAdRequestParcel);
        }
    }

    @Override // com.google.android.gms.ads.internal.reward.client.zzb
    public void zza(com.google.android.gms.ads.internal.reward.client.zzd zzdVar) {
        synchronized (this.zzpd) {
            this.zzGW.zza(zzdVar);
        }
    }
}
