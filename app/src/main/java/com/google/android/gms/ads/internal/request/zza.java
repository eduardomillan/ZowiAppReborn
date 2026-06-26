package com.google.android.gms.ads.internal.request;

import android.content.Context;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.internal.zzan;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzhs;
import com.google.android.gms.internal.zzhz;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zza {

    /* JADX INFO: renamed from: com.google.android.gms.ads.internal.request.zza$zza, reason: collision with other inner class name */
    public interface InterfaceC0018zza {
        void zza(zzhs.zza zzaVar);
    }

    public zzhz zza(Context context, AdRequestInfoParcel.zza zzaVar, zzan zzanVar, InterfaceC0018zza interfaceC0018zza) {
        zzhz zzmVar = zzaVar.zzEn.extras.getBundle("sdk_less_server_data") != null ? new zzm(context, zzaVar, interfaceC0018zza) : new zzb(context, zzaVar, zzanVar, interfaceC0018zza);
        zzmVar.zzfu();
        return zzmVar;
    }
}
