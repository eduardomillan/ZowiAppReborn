package com.google.android.gms.internal;

import com.google.android.gms.ads.formats.NativeCustomTemplateAd;
import com.google.android.gms.internal.zzcy;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzdd extends zzcy.zza {
    private final NativeCustomTemplateAd.OnCustomClickListener zzxl;

    public zzdd(NativeCustomTemplateAd.OnCustomClickListener onCustomClickListener) {
        this.zzxl = onCustomClickListener;
    }

    @Override // com.google.android.gms.internal.zzcy
    public void zza(zzcu zzcuVar, String str) {
        this.zzxl.onCustomClick(new zzcv(zzcuVar), str);
    }
}
