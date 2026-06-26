package com.google.android.gms.ads.internal.client;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class zzg implements Parcelable.Creator<AdRequestParcel> {
    static void zza(AdRequestParcel adRequestParcel, Parcel parcel, int i) {
        int iZzaq = com.google.android.gms.common.internal.safeparcel.zzb.zzaq(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, adRequestParcel.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, adRequestParcel.zzsB);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, adRequestParcel.extras, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 4, adRequestParcel.zzsC);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 5, adRequestParcel.zzsD, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, adRequestParcel.zzsE);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 7, adRequestParcel.zzsF);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, adRequestParcel.zzsG);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, adRequestParcel.zzsH, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, (Parcelable) adRequestParcel.zzsI, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 11, (Parcelable) adRequestParcel.zzsJ, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 12, adRequestParcel.zzsK, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 13, adRequestParcel.zzsL, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 14, adRequestParcel.zzsM, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 15, adRequestParcel.zzsN, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 17, adRequestParcel.zzsP, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 16, adRequestParcel.zzsO, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzI(parcel, iZzaq);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: zzb, reason: merged with bridge method [inline-methods] */
    public AdRequestParcel createFromParcel(Parcel parcel) {
        int iZzap = com.google.android.gms.common.internal.safeparcel.zza.zzap(parcel);
        int iZzg = 0;
        long jZzi = 0;
        Bundle bundleZzr = null;
        int iZzg2 = 0;
        ArrayList<String> arrayListZzD = null;
        boolean zZzc = false;
        int iZzg3 = 0;
        boolean zZzc2 = false;
        String strZzp = null;
        SearchAdRequestParcel searchAdRequestParcel = null;
        Location location = null;
        String strZzp2 = null;
        Bundle bundleZzr2 = null;
        Bundle bundleZzr3 = null;
        ArrayList<String> arrayListZzD2 = null;
        String strZzp3 = null;
        String strZzp4 = null;
        while (parcel.dataPosition() < iZzap) {
            int iZzao = com.google.android.gms.common.internal.safeparcel.zza.zzao(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbM(iZzao)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 2:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzao);
                    break;
                case 3:
                    bundleZzr = com.google.android.gms.common.internal.safeparcel.zza.zzr(parcel, iZzao);
                    break;
                case 4:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 5:
                    arrayListZzD = com.google.android.gms.common.internal.safeparcel.zza.zzD(parcel, iZzao);
                    break;
                case 6:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzao);
                    break;
                case 7:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 8:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzao);
                    break;
                case 9:
                    strZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 10:
                    searchAdRequestParcel = (SearchAdRequestParcel) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzao, SearchAdRequestParcel.CREATOR);
                    break;
                case 11:
                    location = (Location) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzao, Location.CREATOR);
                    break;
                case 12:
                    strZzp2 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 13:
                    bundleZzr2 = com.google.android.gms.common.internal.safeparcel.zza.zzr(parcel, iZzao);
                    break;
                case 14:
                    bundleZzr3 = com.google.android.gms.common.internal.safeparcel.zza.zzr(parcel, iZzao);
                    break;
                case 15:
                    arrayListZzD2 = com.google.android.gms.common.internal.safeparcel.zza.zzD(parcel, iZzao);
                    break;
                case 16:
                    strZzp3 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 17:
                    strZzp4 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzao);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzap) {
            throw new zza.C0037zza("Overread allowed size end=" + iZzap, parcel);
        }
        return new AdRequestParcel(iZzg, jZzi, bundleZzr, iZzg2, arrayListZzD, zZzc, iZzg3, zZzc2, strZzp, searchAdRequestParcel, location, strZzp2, bundleZzr2, bundleZzr3, arrayListZzD2, strZzp3, strZzp4);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: zzk, reason: merged with bridge method [inline-methods] */
    public AdRequestParcel[] newArray(int i) {
        return new AdRequestParcel[i];
    }
}
