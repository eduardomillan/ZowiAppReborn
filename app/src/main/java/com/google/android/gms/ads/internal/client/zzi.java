package com.google.android.gms.ads.internal.client;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* JADX INFO: loaded from: classes.dex */
public class zzi implements Parcelable.Creator<AdSizeParcel> {
    static void zza(AdSizeParcel adSizeParcel, Parcel parcel, int i) {
        int iZzaq = com.google.android.gms.common.internal.safeparcel.zzb.zzaq(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, adSizeParcel.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, adSizeParcel.zzte, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, adSizeParcel.height);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, adSizeParcel.heightPixels);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, adSizeParcel.zztf);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 6, adSizeParcel.width);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 7, adSizeParcel.widthPixels);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, (Parcelable[]) adSizeParcel.zztg, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, adSizeParcel.zzth);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, adSizeParcel.zzti);
        com.google.android.gms.common.internal.safeparcel.zzb.zzI(parcel, iZzaq);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: zzc, reason: merged with bridge method [inline-methods] */
    public AdSizeParcel createFromParcel(Parcel parcel) {
        AdSizeParcel[] adSizeParcelArr = null;
        boolean zZzc = false;
        int iZzap = com.google.android.gms.common.internal.safeparcel.zza.zzap(parcel);
        boolean zZzc2 = false;
        int iZzg = 0;
        int iZzg2 = 0;
        boolean zZzc3 = false;
        int iZzg3 = 0;
        int iZzg4 = 0;
        String strZzp = null;
        int iZzg5 = 0;
        while (parcel.dataPosition() < iZzap) {
            int iZzao = com.google.android.gms.common.internal.safeparcel.zza.zzao(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbM(iZzao)) {
                case 1:
                    iZzg5 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 2:
                    strZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 3:
                    iZzg4 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 4:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 5:
                    zZzc3 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzao);
                    break;
                case 6:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 7:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 8:
                    adSizeParcelArr = (AdSizeParcel[]) com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzao, AdSizeParcel.CREATOR);
                    break;
                case 9:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzao);
                    break;
                case 10:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzao);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzao);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzap) {
            throw new zza.C0037zza("Overread allowed size end=" + iZzap, parcel);
        }
        return new AdSizeParcel(iZzg5, strZzp, iZzg4, iZzg3, zZzc3, iZzg2, iZzg, adSizeParcelArr, zZzc2, zZzc);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: zzl, reason: merged with bridge method [inline-methods] */
    public AdSizeParcel[] newArray(int i) {
        return new AdSizeParcel[i];
    }
}
