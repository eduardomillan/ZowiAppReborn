package com.google.android.gms.ads.internal.client;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;

/* JADX INFO: loaded from: classes.dex */
public class zzae implements Parcelable.Creator<SearchAdRequestParcel> {
    static void zza(SearchAdRequestParcel searchAdRequestParcel, Parcel parcel, int i) {
        int iZzaq = com.google.android.gms.common.internal.safeparcel.zzb.zzaq(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, searchAdRequestParcel.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 2, searchAdRequestParcel.zztP);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 3, searchAdRequestParcel.backgroundColor);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, searchAdRequestParcel.zztQ);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 5, searchAdRequestParcel.zztR);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 6, searchAdRequestParcel.zztS);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 7, searchAdRequestParcel.zztT);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 8, searchAdRequestParcel.zztU);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 9, searchAdRequestParcel.zztV);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, searchAdRequestParcel.zztW, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 11, searchAdRequestParcel.zztX);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 12, searchAdRequestParcel.zztY, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 13, searchAdRequestParcel.zztZ);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 14, searchAdRequestParcel.zzua);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 15, searchAdRequestParcel.zzub, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzI(parcel, iZzaq);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
    public SearchAdRequestParcel createFromParcel(Parcel parcel) {
        int iZzap = com.google.android.gms.common.internal.safeparcel.zza.zzap(parcel);
        int iZzg = 0;
        int iZzg2 = 0;
        int iZzg3 = 0;
        int iZzg4 = 0;
        int iZzg5 = 0;
        int iZzg6 = 0;
        int iZzg7 = 0;
        int iZzg8 = 0;
        int iZzg9 = 0;
        String strZzp = null;
        int iZzg10 = 0;
        String strZzp2 = null;
        int iZzg11 = 0;
        int iZzg12 = 0;
        String strZzp3 = null;
        while (parcel.dataPosition() < iZzap) {
            int iZzao = com.google.android.gms.common.internal.safeparcel.zza.zzao(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbM(iZzao)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 2:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 3:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 4:
                    iZzg4 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 5:
                    iZzg5 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 6:
                    iZzg6 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 7:
                    iZzg7 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 8:
                    iZzg8 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 9:
                    iZzg9 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 10:
                    strZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 11:
                    iZzg10 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 12:
                    strZzp2 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 13:
                    iZzg11 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 14:
                    iZzg12 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 15:
                    strZzp3 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzao);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzap) {
            throw new zza.C0037zza("Overread allowed size end=" + iZzap, parcel);
        }
        return new SearchAdRequestParcel(iZzg, iZzg2, iZzg3, iZzg4, iZzg5, iZzg6, iZzg7, iZzg8, iZzg9, strZzp, iZzg10, strZzp2, iZzg11, iZzg12, strZzp3);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: zzn, reason: merged with bridge method [inline-methods] */
    public SearchAdRequestParcel[] newArray(int i) {
        return new SearchAdRequestParcel[i];
    }
}
