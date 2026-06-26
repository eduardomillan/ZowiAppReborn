package com.google.android.gms.ads.internal.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class zzh implements Parcelable.Creator<AdResponseParcel> {
    static void zza(AdResponseParcel adResponseParcel, Parcel parcel, int i) {
        int iZzaq = com.google.android.gms.common.internal.safeparcel.zzb.zzaq(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, adResponseParcel.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, adResponseParcel.zzBF, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, adResponseParcel.body, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 4, adResponseParcel.zzyY, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 5, adResponseParcel.errorCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 6, adResponseParcel.zzyZ, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, adResponseParcel.zzEJ);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, adResponseParcel.zzEK);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, adResponseParcel.zzEL);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 10, adResponseParcel.zzEM, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 11, adResponseParcel.zzzc);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 12, adResponseParcel.orientation);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 13, adResponseParcel.zzEN, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 14, adResponseParcel.zzEO);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 15, adResponseParcel.zzEP, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 19, adResponseParcel.zzER, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 18, adResponseParcel.zzEQ);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 21, adResponseParcel.zzES, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 23, adResponseParcel.zzth);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 22, adResponseParcel.zzET);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 25, adResponseParcel.zzEU);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 24, adResponseParcel.zzEv);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 27, adResponseParcel.zzEW);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 26, adResponseParcel.zzEV);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 29, adResponseParcel.zzEY, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 28, (Parcelable) adResponseParcel.zzEX, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 31, adResponseParcel.zzti);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 30, adResponseParcel.zzEZ, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzI(parcel, iZzaq);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: zzE, reason: merged with bridge method [inline-methods] */
    public AdResponseParcel[] newArray(int i) {
        return new AdResponseParcel[i];
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: zzj, reason: merged with bridge method [inline-methods] */
    public AdResponseParcel createFromParcel(Parcel parcel) {
        int iZzap = com.google.android.gms.common.internal.safeparcel.zza.zzap(parcel);
        int iZzg = 0;
        String strZzp = null;
        String strZzp2 = null;
        ArrayList<String> arrayListZzD = null;
        int iZzg2 = 0;
        ArrayList<String> arrayListZzD2 = null;
        long jZzi = 0;
        boolean zZzc = false;
        long jZzi2 = 0;
        ArrayList<String> arrayListZzD3 = null;
        long jZzi3 = 0;
        int iZzg3 = 0;
        String strZzp3 = null;
        long jZzi4 = 0;
        String strZzp4 = null;
        boolean zZzc2 = false;
        String strZzp5 = null;
        String strZzp6 = null;
        boolean zZzc3 = false;
        boolean zZzc4 = false;
        boolean zZzc5 = false;
        boolean zZzc6 = false;
        boolean zZzc7 = false;
        int iZzg4 = 0;
        LargeParcelTeleporter largeParcelTeleporter = null;
        String strZzp7 = null;
        String strZzp8 = null;
        boolean zZzc8 = false;
        while (parcel.dataPosition() < iZzap) {
            int iZzao = com.google.android.gms.common.internal.safeparcel.zza.zzao(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbM(iZzao)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 2:
                    strZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 3:
                    strZzp2 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 4:
                    arrayListZzD = com.google.android.gms.common.internal.safeparcel.zza.zzD(parcel, iZzao);
                    break;
                case 5:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 6:
                    arrayListZzD2 = com.google.android.gms.common.internal.safeparcel.zza.zzD(parcel, iZzao);
                    break;
                case 7:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzao);
                    break;
                case 8:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzao);
                    break;
                case 9:
                    jZzi2 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzao);
                    break;
                case 10:
                    arrayListZzD3 = com.google.android.gms.common.internal.safeparcel.zza.zzD(parcel, iZzao);
                    break;
                case 11:
                    jZzi3 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzao);
                    break;
                case 12:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 13:
                    strZzp3 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 14:
                    jZzi4 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzao);
                    break;
                case 15:
                    strZzp4 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 16:
                case 17:
                case 20:
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzao);
                    break;
                case 18:
                    zZzc2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzao);
                    break;
                case 19:
                    strZzp5 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 21:
                    strZzp6 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 22:
                    zZzc3 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzao);
                    break;
                case 23:
                    zZzc4 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzao);
                    break;
                case 24:
                    zZzc5 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzao);
                    break;
                case 25:
                    zZzc6 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzao);
                    break;
                case 26:
                    zZzc7 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzao);
                    break;
                case 27:
                    iZzg4 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 28:
                    largeParcelTeleporter = (LargeParcelTeleporter) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzao, LargeParcelTeleporter.CREATOR);
                    break;
                case 29:
                    strZzp7 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 30:
                    strZzp8 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 31:
                    zZzc8 = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzao);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzap) {
            throw new zza.C0037zza("Overread allowed size end=" + iZzap, parcel);
        }
        return new AdResponseParcel(iZzg, strZzp, strZzp2, arrayListZzD, iZzg2, arrayListZzD2, jZzi, zZzc, jZzi2, arrayListZzD3, jZzi3, iZzg3, strZzp3, jZzi4, strZzp4, zZzc2, strZzp5, strZzp6, zZzc3, zZzc4, zZzc5, zZzc6, zZzc7, iZzg4, largeParcelTeleporter, strZzp7, strZzp8, zZzc8);
    }
}
