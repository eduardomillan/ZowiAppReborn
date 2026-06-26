package com.google.android.gms.ads.internal.reward.client;

import android.os.RemoteException;
import com.google.android.gms.ads.reward.RewardItem;

/* JADX INFO: loaded from: classes.dex */
public class zze implements RewardItem {
    private final zza zzHc;

    public zze(zza zzaVar) {
        this.zzHc = zzaVar;
    }

    @Override // com.google.android.gms.ads.reward.RewardItem
    public int getAmount() {
        if (this.zzHc == null) {
            return 0;
        }
        try {
            return this.zzHc.getAmount();
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not forward getAmount to RewardItem", e);
            return 0;
        }
    }

    @Override // com.google.android.gms.ads.reward.RewardItem
    public String getType() {
        if (this.zzHc == null) {
            return null;
        }
        try {
            return this.zzHc.getType();
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not forward getType to RewardItem", e);
            return null;
        }
    }
}
