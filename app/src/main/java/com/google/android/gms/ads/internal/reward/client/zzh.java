package com.google.android.gms.ads.internal.reward.client;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.common.internal.safeparcel.zza;

/* JADX INFO: loaded from: classes.dex */
public class zzh implements Parcelable.Creator<RewardedVideoAdRequestParcel> {
    static void zza(RewardedVideoAdRequestParcel rewardedVideoAdRequestParcel, Parcel parcel, int i) {
        int iZzaq = com.google.android.gms.common.internal.safeparcel.zzb.zzaq(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, rewardedVideoAdRequestParcel.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, (Parcelable) rewardedVideoAdRequestParcel.zzEn, i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, rewardedVideoAdRequestParcel.zzqh, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzI(parcel, iZzaq);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: zzJ, reason: merged with bridge method [inline-methods] */
    public RewardedVideoAdRequestParcel[] newArray(int i) {
        return new RewardedVideoAdRequestParcel[i];
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: zzn, reason: merged with bridge method [inline-methods] */
    public RewardedVideoAdRequestParcel createFromParcel(Parcel parcel) {
        String strZzp;
        AdRequestParcel adRequestParcel;
        int iZzg;
        String str = null;
        int iZzap = com.google.android.gms.common.internal.safeparcel.zza.zzap(parcel);
        int i = 0;
        AdRequestParcel adRequestParcel2 = null;
        while (parcel.dataPosition() < iZzap) {
            int iZzao = com.google.android.gms.common.internal.safeparcel.zza.zzao(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.zza.zzbM(iZzao)) {
                case 1:
                    String str2 = str;
                    adRequestParcel = adRequestParcel2;
                    iZzg = com.google.android.gms.common.internal.safeparcel.zza.zzg(parcel, iZzao);
                    strZzp = str2;
                    break;
                case 2:
                    AdRequestParcel adRequestParcel3 = (AdRequestParcel) com.google.android.gms.common.internal.safeparcel.zza.zza(parcel, iZzao, AdRequestParcel.CREATOR);
                    iZzg = i;
                    strZzp = str;
                    adRequestParcel = adRequestParcel3;
                    break;
                case 3:
                    strZzp = com.google.android.gms.common.internal.safeparcel.zza.zzp(parcel, iZzao);
                    adRequestParcel = adRequestParcel2;
                    iZzg = i;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.zza.zzb(parcel, iZzao);
                    strZzp = str;
                    adRequestParcel = adRequestParcel2;
                    iZzg = i;
                    break;
            }
            i = iZzg;
            adRequestParcel2 = adRequestParcel;
            str = strZzp;
        }
        if (parcel.dataPosition() != iZzap) {
            throw new zza.C0037zza("Overread allowed size end=" + iZzap, parcel);
        }
        return new RewardedVideoAdRequestParcel(i, adRequestParcel2, str);
    }
}
