package com.google.android.gms.ads.internal.overlay;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.ads.internal.InterstitialAdParameterParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.internal.safeparcel.zza;

/* JADX INFO: loaded from: classes.dex */
public class zzf implements Parcelable.Creator<AdOverlayInfoParcel> {
    static void zza(AdOverlayInfoParcel adOverlayInfoParcel, Parcel parcel, int i) {
        int iZzaq = com.google.android.gms.common.internal.safeparcel.zzb.zzaq(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, adOverlayInfoParcel.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) adOverlayInfoParcel.zzBA, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, adOverlayInfoParcel.zzeK(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, adOverlayInfoParcel.zzeL(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, adOverlayInfoParcel.zzeM(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 6, adOverlayInfoParcel.zzeN(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 7, adOverlayInfoParcel.zzBF, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 8, adOverlayInfoParcel.zzBG);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 9, adOverlayInfoParcel.zzBH, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 10, adOverlayInfoParcel.zzeP(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 11, adOverlayInfoParcel.orientation);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 12, adOverlayInfoParcel.zzBJ);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 13, adOverlayInfoParcel.url, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 14, (Parcelable) adOverlayInfoParcel.zzqj, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 15, adOverlayInfoParcel.zzeO(), false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 17, (Parcelable) adOverlayInfoParcel.zzBM, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 16, adOverlayInfoParcel.zzBL, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzI(parcel, iZzaq);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: zzg, reason: merged with bridge method [inline-methods] */
    public AdOverlayInfoParcel createFromParcel(Parcel parcel) {
        int iZzap = com.google.android.gms.common.internal.safeparcel.zza.zzap(parcel);
        int iZzg = 0;
        AdLauncherIntentInfoParcel adLauncherIntentInfoParcel = null;
        IBinder iBinderZzq = null;
        IBinder iBinderZzq2 = null;
        IBinder iBinderZzq3 = null;
        IBinder iBinderZzq4 = null;
        String strZzp = null;
        boolean zZzc = false;
        String strZzp2 = null;
        IBinder iBinderZzq5 = null;
        int iZzg2 = 0;
        int iZzg3 = 0;
        String strZzp3 = null;
        VersionInfoParcel versionInfoParcel = null;
        IBinder iBinderZzq6 = null;
        String strZzp4 = null;
        InterstitialAdParameterParcel interstitialAdParameterParcel = null;
        while (parcel.dataPosition() < iZzap) {
            int iZzao = com.google.android.gms.common.internal.safeparcel.zza.zzao(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbM(iZzao)) {
                case 1:
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 2:
                    adLauncherIntentInfoParcel = (AdLauncherIntentInfoParcel) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzao, AdLauncherIntentInfoParcel.CREATOR);
                    break;
                case 3:
                    iBinderZzq = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel, iZzao);
                    break;
                case 4:
                    iBinderZzq2 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel, iZzao);
                    break;
                case 5:
                    iBinderZzq3 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel, iZzao);
                    break;
                case 6:
                    iBinderZzq4 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel, iZzao);
                    break;
                case 7:
                    strZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 8:
                    zZzc = com.google.android.gms.common.internal.safeparcel.zza.zzc(parcel, iZzao);
                    break;
                case 9:
                    strZzp2 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 10:
                    iBinderZzq5 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel, iZzao);
                    break;
                case 11:
                    iZzg2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 12:
                    iZzg3 = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    break;
                case 13:
                    strZzp3 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 14:
                    versionInfoParcel = (VersionInfoParcel) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzao, VersionInfoParcel.CREATOR);
                    break;
                case 15:
                    iBinderZzq6 = com.google.android.gms.common.internal.safeparcel.zza.zzq(parcel, iZzao);
                    break;
                case 16:
                    strZzp4 = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    break;
                case 17:
                    interstitialAdParameterParcel = (InterstitialAdParameterParcel) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzao, InterstitialAdParameterParcel.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzao);
                    break;
            }
        }
        if (parcel.dataPosition() != iZzap) {
            throw new zza.C0037zza("Overread allowed size end=" + iZzap, parcel);
        }
        return new AdOverlayInfoParcel(iZzg, adLauncherIntentInfoParcel, iBinderZzq, iBinderZzq2, iBinderZzq3, iBinderZzq4, strZzp, zZzc, strZzp2, iBinderZzq5, iZzg2, iZzg3, strZzp3, versionInfoParcel, iBinderZzq6, strZzp4, interstitialAdParameterParcel);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: zzw, reason: merged with bridge method [inline-methods] */
    public AdOverlayInfoParcel[] newArray(int i) {
        return new AdOverlayInfoParcel[i];
    }
}
