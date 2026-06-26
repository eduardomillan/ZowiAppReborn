package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.ads.internal.client.zzq;
import com.google.android.gms.ads.internal.client.zzr;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.internal.zzel;
import com.google.android.gms.internal.zzgr;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzd extends com.google.android.gms.dynamic.zzg<zzr> {
    private static final zzd zzsA = new zzd();

    private zzd() {
        super("com.google.android.gms.ads.AdLoaderBuilderCreatorImpl");
    }

    public static zzq zza(Context context, String str, zzel zzelVar) {
        zzq zzqVarZzb;
        if (zzl.zzcF().zzR(context) && (zzqVarZzb = zzsA.zzb(context, str, zzelVar)) != null) {
            return zzqVarZzb;
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Using AdLoader from the client jar.");
        return new com.google.android.gms.ads.internal.zzj(context, str, zzelVar, new VersionInfoParcel(8115000, 8115000, true));
    }

    private zzq zzb(Context context, String str, zzel zzelVar) {
        try {
            return zzq.zza.zzi(zzas(context).zza(com.google.android.gms.dynamic.zze.zzy(context), str, zzelVar, 8115000));
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not create remote builder for AdLoader.", e);
            return null;
        } catch (zzg.zza e2) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not create remote builder for AdLoader.", e2);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.dynamic.zzg
    /* JADX INFO: renamed from: zzc, reason: merged with bridge method [inline-methods] */
    public zzr zzd(IBinder iBinder) {
        return zzr.zza.zzj(iBinder);
    }
}
