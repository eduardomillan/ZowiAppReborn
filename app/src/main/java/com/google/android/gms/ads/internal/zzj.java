package com.google.android.gms.ads.internal;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.client.zzq;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.internal.zzcw;
import com.google.android.gms.internal.zzcx;
import com.google.android.gms.internal.zzcy;
import com.google.android.gms.internal.zzcz;
import com.google.android.gms.internal.zzem;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzmi;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzj extends zzq.zza {
    private final Context mContext;
    private com.google.android.gms.ads.internal.client.zzo zzoT;
    private NativeAdOptionsParcel zzoY;
    private final zzem zzox;
    private final String zzpa;
    private final VersionInfoParcel zzpb;
    private zzcw zzpg;
    private zzcx zzph;
    private zzmi<String, zzcz> zzpj = new zzmi<>();
    private zzmi<String, zzcy> zzpi = new zzmi<>();

    public zzj(Context context, String str, zzem zzemVar, VersionInfoParcel versionInfoParcel) {
        this.mContext = context;
        this.zzpa = str;
        this.zzox = zzemVar;
        this.zzpb = versionInfoParcel;
    }

    @Override // com.google.android.gms.ads.internal.client.zzq
    public void zza(NativeAdOptionsParcel nativeAdOptionsParcel) {
        this.zzoY = nativeAdOptionsParcel;
    }

    @Override // com.google.android.gms.ads.internal.client.zzq
    public void zza(zzcw zzcwVar) {
        this.zzpg = zzcwVar;
    }

    @Override // com.google.android.gms.ads.internal.client.zzq
    public void zza(zzcx zzcxVar) {
        this.zzph = zzcxVar;
    }

    @Override // com.google.android.gms.ads.internal.client.zzq
    public void zza(String str, zzcz zzczVar, zzcy zzcyVar) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Custom template ID for native custom template ad is empty. Please provide a valid template id.");
        }
        this.zzpj.put(str, zzczVar);
        this.zzpi.put(str, zzcyVar);
    }

    @Override // com.google.android.gms.ads.internal.client.zzq
    public void zzb(com.google.android.gms.ads.internal.client.zzo zzoVar) {
        this.zzoT = zzoVar;
    }

    @Override // com.google.android.gms.ads.internal.client.zzq
    public com.google.android.gms.ads.internal.client.zzp zzbk() {
        return new zzi(this.mContext, this.zzpa, this.zzox, this.zzpb, this.zzoT, this.zzpg, this.zzph, this.zzpj, this.zzpi, this.zzoY);
    }
}
