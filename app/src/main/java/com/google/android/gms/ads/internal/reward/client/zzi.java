package com.google.android.gms.ads.internal.reward.client;

import android.content.Context;
import android.os.RemoteException;
import android.text.TextUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

/* JADX INFO: loaded from: classes.dex */
public class zzi implements RewardedVideoAd {
    private final Context mContext;
    private String zzGY;
    private RewardedVideoAdListener zzHd;
    private final zzb zzHe;
    private final Object zzpd = new Object();

    public zzi(Context context, zzb zzbVar) {
        this.zzHe = zzbVar;
        this.mContext = context;
    }

    @Override // com.google.android.gms.ads.reward.RewardedVideoAd
    public void destroy() {
        synchronized (this.zzpd) {
            if (this.zzHe == null) {
                return;
            }
            try {
                this.zzHe.destroy();
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not forward destroy to RewardedVideoAd", e);
            }
        }
    }

    @Override // com.google.android.gms.ads.reward.RewardedVideoAd
    public RewardedVideoAdListener getRewardedVideoAdListener() {
        RewardedVideoAdListener rewardedVideoAdListener;
        synchronized (this.zzpd) {
            rewardedVideoAdListener = this.zzHd;
        }
        return rewardedVideoAdListener;
    }

    @Override // com.google.android.gms.ads.reward.RewardedVideoAd
    public String getUserId() {
        String str;
        synchronized (this.zzpd) {
            str = this.zzGY;
        }
        return str;
    }

    @Override // com.google.android.gms.ads.reward.RewardedVideoAd
    public boolean isLoaded() {
        boolean zIsLoaded = false;
        synchronized (this.zzpd) {
            if (this.zzHe != null) {
                try {
                    zIsLoaded = this.zzHe.isLoaded();
                } catch (RemoteException e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not forward isLoaded to RewardedVideoAd", e);
                }
            }
        }
        return zIsLoaded;
    }

    @Override // com.google.android.gms.ads.reward.RewardedVideoAd
    public void loadAd(String adUnitId, AdRequest adRequest) {
        synchronized (this.zzpd) {
            if (this.zzHe == null) {
                return;
            }
            try {
                this.zzHe.zza(com.google.android.gms.ads.internal.client.zzh.zzcB().zza(this.mContext, adRequest.zzaF(), adUnitId));
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not forward loadAd to RewardedVideoAd", e);
            }
        }
    }

    @Override // com.google.android.gms.ads.reward.RewardedVideoAd
    public void pause() {
        synchronized (this.zzpd) {
            if (this.zzHe == null) {
                return;
            }
            try {
                this.zzHe.pause();
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not forward pause to RewardedVideoAd", e);
            }
        }
    }

    @Override // com.google.android.gms.ads.reward.RewardedVideoAd
    public void resume() {
        synchronized (this.zzpd) {
            if (this.zzHe == null) {
                return;
            }
            try {
                this.zzHe.resume();
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not forward resume to RewardedVideoAd", e);
            }
        }
    }

    @Override // com.google.android.gms.ads.reward.RewardedVideoAd
    public void setRewardedVideoAdListener(RewardedVideoAdListener listener) {
        synchronized (this.zzpd) {
            this.zzHd = listener;
            if (this.zzHe != null) {
                try {
                    this.zzHe.zza(new zzg(listener));
                } catch (RemoteException e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not forward setRewardedVideoAdListener to RewardedVideoAd", e);
                }
            }
        }
    }

    @Override // com.google.android.gms.ads.reward.RewardedVideoAd
    public void setUserId(String userId) {
        synchronized (this.zzpd) {
            if (!TextUtils.isEmpty(this.zzGY)) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("A user id has already been set, ignoring.");
                return;
            }
            this.zzGY = userId;
            if (this.zzHe != null) {
                try {
                    this.zzHe.setUserId(userId);
                } catch (RemoteException e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not forward setUserId to RewardedVideoAd", e);
                }
            }
        }
    }

    @Override // com.google.android.gms.ads.reward.RewardedVideoAd
    public void show() {
        synchronized (this.zzpd) {
            if (this.zzHe == null) {
                return;
            }
            try {
                this.zzHe.show();
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not forward show to RewardedVideoAd", e);
            }
        }
    }
}
