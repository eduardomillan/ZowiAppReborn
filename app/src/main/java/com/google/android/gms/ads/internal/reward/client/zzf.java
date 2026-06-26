package com.google.android.gms.ads.internal.reward.client;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.ads.internal.client.zzl;
import com.google.android.gms.ads.internal.reward.client.zzb;
import com.google.android.gms.ads.internal.reward.client.zzc;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.internal.zzel;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzhf;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzf extends com.google.android.gms.dynamic.zzg<zzc> {
    public zzf() {
        super("com.google.android.gms.ads.reward.RewardedVideoAdCreatorImpl");
    }

    private zzb zzb(Context context, zzel zzelVar) {
        try {
            return zzb.zza.zzaa(zzas(context).zza(com.google.android.gms.dynamic.zze.zzy(context), zzelVar, 8115000));
        } catch (RemoteException | zzg.zza e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not get remote RewardedVideoAd.", e);
            return null;
        }
    }

    public zzb zza(Context context, zzel zzelVar) {
        zzb zzbVarZzb;
        if (zzl.zzcF().zzR(context) && (zzbVarZzb = zzb(context, zzelVar)) != null) {
            return zzbVarZzb;
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Using RewardedVideoAd from the client jar.");
        return new zzhf(context, zzelVar, new VersionInfoParcel(8115000, 8115000, true));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.dynamic.zzg
    /* JADX INFO: renamed from: zzad, reason: merged with bridge method [inline-methods] */
    public zzc zzd(IBinder iBinder) {
        return zzc.zza.zzab(iBinder);
    }
}
