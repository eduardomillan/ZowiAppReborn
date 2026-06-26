package com.google.android.gms.ads.internal.client;

import android.os.Parcel;
import com.google.android.gms.internal.zzgr;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class ThinAdSizeParcel extends AdSizeParcel {
    public ThinAdSizeParcel(AdSizeParcel originalAdSize) {
        super(originalAdSize.versionCode, originalAdSize.zzte, originalAdSize.height, originalAdSize.heightPixels, originalAdSize.zztf, originalAdSize.width, originalAdSize.widthPixels, originalAdSize.zztg, originalAdSize.zzth, originalAdSize.zzti);
    }

    @Override // com.google.android.gms.ads.internal.client.AdSizeParcel, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        int iZzaq = com.google.android.gms.common.internal.safeparcel.zzb.zzaq(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, this.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, this.zzte, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, this.height);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 6, this.width);
        com.google.android.gms.common.internal.safeparcel.zzb.zzI(parcel, iZzaq);
    }
}
