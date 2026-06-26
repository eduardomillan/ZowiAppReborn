package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.reward.client.RewardedVideoAdRequestParcel;
import com.google.android.gms.ads.internal.reward.mediation.client.RewardItemParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.internal.zzhs;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzhg extends com.google.android.gms.ads.internal.zzb implements zzhk {
    private com.google.android.gms.ads.internal.reward.client.zzd zzGX;
    private String zzGY;
    private boolean zzGZ;
    private HashMap<String, zzhh> zzHa;

    public zzhg(Context context, AdSizeParcel adSizeParcel, zzem zzemVar, VersionInfoParcel versionInfoParcel) {
        super(context, adSizeParcel, null, zzemVar, versionInfoParcel, null);
        this.zzHa = new HashMap<>();
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.ads.internal.client.zzs
    public void destroy() {
        com.google.android.gms.common.internal.zzx.zzci("destroy must be called on the main UI thread.");
        for (String str : this.zzHa.keySet()) {
            try {
                zzhh zzhhVar = this.zzHa.get(str);
                if (zzhhVar != null && zzhhVar.zzgc() != null) {
                    zzhhVar.zzgc().destroy();
                }
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Fail to destroy adapter: " + str);
            }
        }
    }

    public boolean isLoaded() {
        com.google.android.gms.common.internal.zzx.zzci("isLoaded must be called on the main UI thread.");
        return this.zzot.zzql == null && this.zzot.zzqm == null && this.zzot.zzqo != null && !this.zzGZ;
    }

    @Override // com.google.android.gms.internal.zzhk
    public void onRewardedVideoAdClosed() {
        if (this.zzGX == null) {
            return;
        }
        try {
            this.zzGX.onRewardedVideoAdClosed();
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not call RewardedVideoAdListener.onAdClosed().", e);
        }
    }

    @Override // com.google.android.gms.internal.zzhk
    public void onRewardedVideoAdLeftApplication() {
        if (this.zzGX == null) {
            return;
        }
        try {
            this.zzGX.onRewardedVideoAdLeftApplication();
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not call RewardedVideoAdListener.onAdLeftApplication().", e);
        }
    }

    @Override // com.google.android.gms.internal.zzhk
    public void onRewardedVideoAdOpened() {
        zza(this.zzot.zzqo, false);
        if (this.zzGX == null) {
            return;
        }
        try {
            this.zzGX.onRewardedVideoAdOpened();
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not call RewardedVideoAdListener.onAdOpened().", e);
        }
    }

    @Override // com.google.android.gms.internal.zzhk
    public void onRewardedVideoStarted() {
        com.google.android.gms.ads.internal.zzp.zzbH().zza(this.zzot.context, this.zzot.zzqj.zzJu, this.zzot.zzqo, this.zzot.zzqh, false, this.zzot.zzqo.zzzu.zzyU);
        if (this.zzGX == null) {
            return;
        }
        try {
            this.zzGX.onRewardedVideoStarted();
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not call RewardedVideoAdListener.onVideoStarted().", e);
        }
    }

    @Override // com.google.android.gms.ads.internal.zzb, com.google.android.gms.ads.internal.zza, com.google.android.gms.ads.internal.client.zzs
    public void pause() {
        com.google.android.gms.common.internal.zzx.zzci("pause must be called on the main UI thread.");
        for (String str : this.zzHa.keySet()) {
            try {
                zzhh zzhhVar = this.zzHa.get(str);
                if (zzhhVar != null && zzhhVar.zzgc() != null) {
                    zzhhVar.zzgc().pause();
                }
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Fail to pause adapter: " + str);
            }
        }
    }

    @Override // com.google.android.gms.ads.internal.zzb, com.google.android.gms.ads.internal.zza, com.google.android.gms.ads.internal.client.zzs
    public void resume() {
        com.google.android.gms.common.internal.zzx.zzci("resume must be called on the main UI thread.");
        for (String str : this.zzHa.keySet()) {
            try {
                zzhh zzhhVar = this.zzHa.get(str);
                if (zzhhVar != null && zzhhVar.zzgc() != null) {
                    zzhhVar.zzgc().resume();
                }
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Fail to resume adapter: " + str);
            }
        }
    }

    public void setUserId(String userId) {
        com.google.android.gms.common.internal.zzx.zzci("setUserId must be called on the main UI thread.");
        this.zzGY = userId;
    }

    public void zza(RewardedVideoAdRequestParcel rewardedVideoAdRequestParcel) {
        com.google.android.gms.common.internal.zzx.zzci("loadAd must be called on the main UI thread.");
        if (TextUtils.isEmpty(rewardedVideoAdRequestParcel.zzqh)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Invalid ad unit id. Aborting.");
            return;
        }
        this.zzGZ = false;
        this.zzot.zzqh = rewardedVideoAdRequestParcel.zzqh;
        super.zzb(rewardedVideoAdRequestParcel.zzEn);
    }

    public void zza(com.google.android.gms.ads.internal.reward.client.zzd zzdVar) {
        com.google.android.gms.common.internal.zzx.zzci("setRewardedVideoAdListener must be called on the main UI thread.");
        this.zzGX = zzdVar;
    }

    @Override // com.google.android.gms.internal.zzhk
    public void zza(RewardItemParcel rewardItemParcel) {
        com.google.android.gms.ads.internal.zzp.zzbH().zza(this.zzot.context, this.zzot.zzqj.zzJu, this.zzot.zzqo, this.zzot.zzqh, false, this.zzot.zzqo.zzzu.zzyV);
        if (this.zzGX == null) {
            return;
        }
        try {
            if (this.zzot.zzqo == null || this.zzot.zzqo.zzHx == null || TextUtils.isEmpty(this.zzot.zzqo.zzHx.zzzd)) {
                this.zzGX.zza(new zzhe(rewardItemParcel.type, rewardItemParcel.zzHv));
            } else {
                this.zzGX.zza(new zzhe(this.zzot.zzqo.zzHx.zzzd, this.zzot.zzqo.zzHx.zzze));
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not call RewardedVideoAdListener.onRewarded().", e);
        }
    }

    @Override // com.google.android.gms.ads.internal.zza
    public void zza(final zzhs.zza zzaVar, zzcg zzcgVar) {
        if (zzaVar.errorCode != -2) {
            zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.internal.zzhg.1
                @Override // java.lang.Runnable
                public void run() {
                    zzhg.this.zzb(new zzhs(zzaVar, null, null, null, null, null, null));
                }
            });
            return;
        }
        this.zzot.zzqH = 0;
        this.zzot.zzqm = new zzhn(this.zzot.context, this.zzGY, zzaVar, this);
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("AdRenderer: " + this.zzot.zzqm.getClass().getName());
        this.zzot.zzqm.zzfu();
    }

    @Override // com.google.android.gms.ads.internal.zzb, com.google.android.gms.ads.internal.zza
    public boolean zza(zzhs zzhsVar, zzhs zzhsVar2) {
        if (this.zzGX == null) {
            return true;
        }
        try {
            this.zzGX.onRewardedVideoAdLoaded();
            return true;
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not call RewardedVideoAdListener.onAdLoaded().", e);
            return true;
        }
    }

    public zzhh zzau(String str) {
        zzhh zzhhVar = this.zzHa.get(str);
        if (zzhhVar != null) {
            return zzhhVar;
        }
        try {
            zzhh zzhhVar2 = new zzhh(this.zzox.zzae(str), this);
            try {
                this.zzHa.put(str, zzhhVar2);
                return zzhhVar2;
            } catch (Exception e) {
                zzhhVar = zzhhVar2;
                e = e;
                com.google.android.gms.ads.internal.util.client.zzb.zzd("Fail to instantiate adapter " + str, e);
                return zzhhVar;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    @Override // com.google.android.gms.ads.internal.zza
    protected boolean zze(int i) {
        com.google.android.gms.ads.internal.util.client.zzb.zzaH("Failed to load ad: " + i);
        if (this.zzGX == null) {
            return false;
        }
        try {
            this.zzGX.onRewardedVideoAdFailedToLoad(i);
            return true;
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not call RewardedVideoAdListener.onAdFailedToLoad().", e);
            return false;
        }
    }

    public void zzga() {
        com.google.android.gms.common.internal.zzx.zzci("showAd must be called on the main UI thread.");
        if (!isLoaded()) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("The reward video has not loaded.");
            return;
        }
        this.zzGZ = true;
        zzhh zzhhVarZzau = zzau(this.zzot.zzqo.zzzw);
        if (zzhhVarZzau == null || zzhhVarZzau.zzgc() == null) {
            return;
        }
        try {
            zzhhVarZzau.zzgc().showVideo();
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not call showVideo.", e);
        }
    }

    @Override // com.google.android.gms.internal.zzhk
    public void zzgb() {
        onAdClicked();
    }
}
