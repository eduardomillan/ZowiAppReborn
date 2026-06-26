package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.ads.internal.client.zzw;
import com.google.android.gms.ads.internal.client.zzx;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.internal.zzgr;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzad extends com.google.android.gms.dynamic.zzg<zzx> {
    public zzad() {
        super("com.google.android.gms.ads.MobileAdsSettingManagerCreatorImpl");
    }

    private zzw zzu(Context context) {
        try {
            return zzw.zza.zzo(zzas(context).zza(com.google.android.gms.dynamic.zze.zzy(context), 8115000));
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not get remote MobileAdsSettingManager.", e);
            return null;
        } catch (zzg.zza e2) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not get remote MobileAdsSettingManager.", e2);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.google.android.gms.dynamic.zzg
    /* JADX INFO: renamed from: zzq, reason: merged with bridge method [inline-methods] */
    public zzx zzd(IBinder iBinder) {
        return zzx.zza.zzp(iBinder);
    }

    public zzw zzt(Context context) {
        zzw zzwVarZzu;
        if (zzl.zzcF().zzR(context) && (zzwVarZzu = zzu(context)) != null) {
            return zzwVarZzu;
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Using MobileAdsSettingManager from the client jar.");
        new VersionInfoParcel(8115000, 8115000, true);
        return com.google.android.gms.ads.internal.zzm.zzq(context);
    }
}
