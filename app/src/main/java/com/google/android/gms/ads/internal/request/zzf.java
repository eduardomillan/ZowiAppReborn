package com.google.android.gms.ads.internal.request;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.internal.safeparcel.zza;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class zzf implements Parcelable.Creator<AdRequestInfoParcel> {
    static void zza(AdRequestInfoParcel adRequestInfoParcel, Parcel parcel, int i) {
        int iZzaq = com.google.android.gms.common.internal.safeparcel.zzb.zzaq(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, adRequestInfoParcel.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, adRequestInfoParcel.zzEm, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, (Parcelable) adRequestInfoParcel.zzEn, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, (Parcelable) adRequestInfoParcel.zzqn, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, adRequestInfoParcel.zzqh, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, (Parcelable) adRequestInfoParcel.applicationInfo, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, (Parcelable) adRequestInfoParcel.zzEo, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, adRequestInfoParcel.zzEp, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, adRequestInfoParcel.zzEq, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, adRequestInfoParcel.zzEr, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 11, (Parcelable) adRequestInfoParcel.zzqj, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 12, adRequestInfoParcel.zzEs, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 13, adRequestInfoParcel.zzEt);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 14, adRequestInfoParcel.zzqD, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 15, adRequestInfoParcel.zzEu, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 17, (Parcelable) adRequestInfoParcel.zzEw, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 16, adRequestInfoParcel.zzEv);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 19, adRequestInfoParcel.zzEy);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 18, adRequestInfoParcel.zzEx);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 21, adRequestInfoParcel.zzEA, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 20, adRequestInfoParcel.zzEz);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 25, adRequestInfoParcel.zzEB);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 27, adRequestInfoParcel.zzED, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 26, adRequestInfoParcel.zzEC, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 29, (Parcelable) adRequestInfoParcel.zzqB, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 28, adRequestInfoParcel.zzqg, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 31, adRequestInfoParcel.zzEF);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 30, adRequestInfoParcel.zzEE, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 32, (Parcelable) adRequestInfoParcel.zzEG, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 33, adRequestInfoParcel.zzEH, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzI(parcel, iZzaq);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: zzD, reason: merged with bridge method [inline-methods] */
    public AdRequestInfoParcel[] newArray(int i) {
        return new AdRequestInfoParcel[i];
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: zzi, reason: merged with bridge method [inline-methods] */
    public AdRequestInfoParcel createFromParcel(Parcel parcel) {
        int iZzap = com.google.android.gms.common.internal.safeparcel.zza.zzap(parcel);
        int iZzg = 0;
        Bundle bundleZzr = null;
        AdRequestParcel adRequestParcel = null;
        AdSizeParcel adSizeParcel = null;
        String strZzp = null;
        ApplicationInfo applicationInfo = null;
        PackageInfo packageInfo = null;
        String strZzp2 = null;
        String strZzp3 = null;
        String strZzp4 = null;
        VersionInfoParcel versionInfoParcel = null;
        Bundle bundleZzr2 = null;
        int iZzg2 = 0;
        ArrayList<String> arrayListZzD = null;
        Bundle bundleZzr3 = null;
        boolean zZzc = false;
        Messenger messenger = null;
        int iZzg3 = 0;
        int iZzg4 = 0;
        float fZzl = 0.0f;
        String strZzp5 = null;
        long jZzi = 0;
        String strZzp6 = null;
        ArrayList<String> arrayListZzD2 = null;
        String strZzp7 = null;
        NativeAdOptionsParcel nativeAdOptionsParcel = null;
        ArrayList<String> arrayListZzD3 = null;
        long jZzi2 = 0;
        CapabilityParcel capabilityParcel = null;
        String strZzp8 = null;
        while (parcel.dataPosition() < iZzap) {
            int iZzao = com.google.android.gms.common.internal.safeparcel.zza.zzao(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbM(iZzao)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 2:
                    bundleZzr = com.google.android.gms.common.internal.safeparcel.zza.zzr(parcel, iZzao);
                    break;
                case 3:
                    adRequestParcel = (AdRequestParcel) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzao, AdRequestParcel.CREATOR);
                    break;
                case 4:
                    adSizeParcel = (AdSizeParcel) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzao, AdSizeParcel.CREATOR);
                    break;
                case 5:
                    strZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 6:
                    applicationInfo = (ApplicationInfo) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzao, ApplicationInfo.CREATOR);
                    break;
                case 7:
                    packageInfo = (PackageInfo) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzao, PackageInfo.CREATOR);
                    break;
                case 8:
                    strZzp2 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 9:
                    strZzp3 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 10:
                    strZzp4 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 11:
                    versionInfoParcel = (VersionInfoParcel) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzao, VersionInfoParcel.CREATOR);
                    break;
                case 12:
                    bundleZzr2 = com.google.android.gms.common.internal.safeparcel.zza.zzr(parcel, iZzao);
                    break;
                case 13:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 14:
                    arrayListZzD = com.google.android.gms.common.internal.safeparcel.zza.zzD(parcel, iZzao);
                    break;
                case 15:
                    bundleZzr3 = com.google.android.gms.common.internal.safeparcel.zza.zzr(parcel, iZzao);
                    break;
                case 16:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzao);
                    break;
                case 17:
                    messenger = (Messenger) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzao, Messenger.CREATOR);
                    break;
                case 18:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 19:
                    iZzg4 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 20:
                    fZzl = com.google.android.gms.common.internal.safeparcel.zza.zzl(parcel, iZzao);
                    break;
                case 21:
                    strZzp5 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 22:
                case 23:
                case 24:
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzao);
                    break;
                case 25:
                    jZzi = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzao);
                    break;
                case 26:
                    strZzp6 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 27:
                    arrayListZzD2 = com.google.android.gms.common.internal.safeparcel.zza.zzD(parcel, iZzao);
                    break;
                case 28:
                    strZzp7 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 29:
                    nativeAdOptionsParcel = (NativeAdOptionsParcel) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzao, NativeAdOptionsParcel.CREATOR);
                    break;
                case 30:
                    arrayListZzD3 = com.google.android.gms.common.internal.safeparcel.zza.zzD(parcel, iZzao);
                    break;
                case 31:
                    jZzi2 = com.google.android.gms.common.internal.safeparcel.zza.zzi(parcel, iZzao);
                    break;
                case 32:
                    capabilityParcel = (CapabilityParcel) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzao, CapabilityParcel.CREATOR);
                    break;
                case 33:
                    strZzp8 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzap) {
            throw new zza.C0037zza("Overread allowed size end=" + iZzap, parcel);
        }
        return new AdRequestInfoParcel(iZzg, bundleZzr, adRequestParcel, adSizeParcel, strZzp, applicationInfo, packageInfo, strZzp2, strZzp3, strZzp4, versionInfoParcel, bundleZzr2, iZzg2, arrayListZzD, bundleZzr3, zZzc, messenger, iZzg3, iZzg4, fZzl, strZzp5, jZzi, strZzp6, arrayListZzD2, strZzp7, nativeAdOptionsParcel, arrayListZzD3, jZzi2, capabilityParcel, strZzp8);
    }
}
