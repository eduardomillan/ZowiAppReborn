package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzjb {
    public zziz zza(Context context, AdSizeParcel adSizeParcel, boolean z, boolean z2, zzan zzanVar, VersionInfoParcel versionInfoParcel) {
        return zza(context, adSizeParcel, z, z2, zzanVar, versionInfoParcel, null, null);
    }

    public zziz zza(Context context, AdSizeParcel adSizeParcel, boolean z, boolean z2, zzan zzanVar, VersionInfoParcel versionInfoParcel, zzcg zzcgVar, com.google.android.gms.ads.internal.zzd zzdVar) {
        zzjc zzjcVar = new zzjc(zzjd.zzb(context, adSizeParcel, z, z2, zzanVar, versionInfoParcel, zzcgVar, zzdVar));
        zzjcVar.setWebViewClient(com.google.android.gms.ads.internal.zzp.zzbx().zzb(zzjcVar, z2));
        zzjcVar.setWebChromeClient(com.google.android.gms.ads.internal.zzp.zzbx().zzf(zzjcVar));
        return zzjcVar;
    }
}
