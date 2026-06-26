package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.ads.internal.client.zzs;
import com.google.android.gms.ads.internal.client.zzt;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.internal.zzel;
import com.google.android.gms.internal.zzgr;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zze extends com.google.android.gms.dynamic.zzg<zzt> {
    public zze() {
        super("com.google.android.gms.ads.AdManagerCreatorImpl");
    }

    private zzs zza(Context context, AdSizeParcel adSizeParcel, String str, zzel zzelVar, int i) {
        try {
            return zzs.zza.zzk(zzas(context).zza(com.google.android.gms.dynamic.zze.zzy(context), adSizeParcel, str, zzelVar, 8115000, i));
        } catch (RemoteException | zzg.zza e) {
            com.google.android.gms.ads.internal.util.client.zzb.zza("Could not create remote AdManager.", e);
            return null;
        }
    }

    public zzs zza(Context context, AdSizeParcel adSizeParcel, String str, zzel zzelVar) {
        zzs zzsVarZza;
        if (zzl.zzcF().zzR(context) && (zzsVarZza = zza(context, adSizeParcel, str, zzelVar, 1)) != null) {
            return zzsVarZza;
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Using BannerAdManager from the client jar.");
        return new com.google.android.gms.ads.internal.zzf(context, adSizeParcel, str, zzelVar, new VersionInfoParcel(8115000, 8115000, true), com.google.android.gms.ads.internal.zzd.zzbd());
    }

    public zzs zzb(Context context, AdSizeParcel adSizeParcel, String str, zzel zzelVar) {
        zzs zzsVarZza;
        if (zzl.zzcF().zzR(context) && (zzsVarZza = zza(context, adSizeParcel, str, zzelVar, 2)) != null) {
            return zzsVarZza;
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaH("Using InterstitialAdManager from the client jar.");
        return new com.google.android.gms.ads.internal.zzk(context, adSizeParcel, str, zzelVar, new VersionInfoParcel(8115000, 8115000, true), com.google.android.gms.ads.internal.zzd.zzbd());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.dynamic.zzg
    /* JADX INFO: renamed from: zze, reason: merged with bridge method [inline-methods] */
    public zzt zzd(IBinder iBinder) {
        return zzt.zza.zzl(iBinder);
    }
}
