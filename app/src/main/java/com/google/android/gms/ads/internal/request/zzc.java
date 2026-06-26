package com.google.android.gms.ads.internal.request;

import android.content.Context;
import com.google.android.gms.ads.internal.request.zzd;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.zzby;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzhz;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzc {

    public interface zza {
        void zzb(AdResponseParcel adResponseParcel);
    }

    interface zzb {
        boolean zzd(AdRequestInfoParcel adRequestInfoParcel);
    }

    public static zzhz zza(final Context context, AdRequestInfoParcel adRequestInfoParcel, zza zzaVar) {
        return zza(context, adRequestInfoParcel, zzaVar, new zzb() { // from class: com.google.android.gms.ads.internal.request.zzc.1
            @Override // com.google.android.gms.ads.internal.request.zzc.zzb
            public boolean zzd(AdRequestInfoParcel adRequestInfoParcel2) {
                return adRequestInfoParcel2.zzqj.zzJx || (GooglePlayServicesUtil.zzag(context) && !zzby.zzuL.get().booleanValue());
            }
        });
    }

    static zzhz zza(Context context, AdRequestInfoParcel adRequestInfoParcel, zza zzaVar, zzb zzbVar) {
        return zzbVar.zzd(adRequestInfoParcel) ? zzb(context, adRequestInfoParcel, zzaVar) : zzc(context, adRequestInfoParcel, zzaVar);
    }

    private static zzhz zzb(Context context, AdRequestInfoParcel adRequestInfoParcel, zza zzaVar) {
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Fetching ad response from local ad request service.");
        zzd.zza zzaVar2 = new zzd.zza(context, adRequestInfoParcel, zzaVar);
        zzaVar2.zzfu();
        return zzaVar2;
    }

    private static zzhz zzc(Context context, AdRequestInfoParcel adRequestInfoParcel, zza zzaVar) {
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Fetching ad response from remote ad request service.");
        if (com.google.android.gms.ads.internal.client.zzl.zzcF().zzR(context)) {
            return new zzd.zzb(context, adRequestInfoParcel, zzaVar);
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaH("Failed to connect to remote ad request service.");
        return null;
    }
}
