package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.internal.zzel;

/* JADX INFO: loaded from: classes.dex */
public class zzab {
    private static final Object zzpy = new Object();
    private static zzab zztM;
    private zzw zztN;
    private RewardedVideoAd zztO;

    private zzab() {
    }

    public static zzab zzcV() {
        zzab zzabVar;
        synchronized (zzpy) {
            if (zztM == null) {
                zztM = new zzab();
            }
            zzabVar = zztM;
        }
        return zzabVar;
    }

    public RewardedVideoAd getRewardedVideoAdInstance(Context context) {
        RewardedVideoAd rewardedVideoAd;
        synchronized (zzpy) {
            if (this.zztO != null) {
                rewardedVideoAd = this.zztO;
            } else {
                this.zztO = new com.google.android.gms.ads.internal.reward.client.zzi(context, zzl.zzcK().zza(context, new zzel()));
                rewardedVideoAd = this.zztO;
            }
        }
        return rewardedVideoAd;
    }

    public void initialize(Context context) {
        synchronized (zzpy) {
            if (this.zztN != null) {
                return;
            }
            if (context == null) {
                throw new IllegalArgumentException("Context cannot be null.");
            }
            try {
                this.zztN = zzl.zzcI().zzt(context);
                this.zztN.zza();
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Fail to initialize mobile ads setting manager");
            }
        }
    }

    public void zza(Context context, String str, zzac zzacVar) {
        initialize(context);
    }
}
