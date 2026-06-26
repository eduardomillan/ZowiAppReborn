package com.google.android.gms.ads.internal.purchase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.internal.zzfr;
import com.google.android.gms.internal.zzgr;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class GInAppPurchaseManagerInfoParcel implements SafeParcelable {
    public static final zza CREATOR = new zza();
    public final int versionCode;
    public final zzfr zzCw;
    public final Context zzCx;
    public final zzj zzCy;
    public final zzk zzqE;

    GInAppPurchaseManagerInfoParcel(int versionCode, IBinder wrappedInAppPurchaseVerifier, IBinder wrappedInAppPurchase, IBinder wrappedAppContext, IBinder wrappedOnPlayStorePurchaseFinishedListener) {
        this.versionCode = versionCode;
        this.zzqE = (zzk) com.google.android.gms.dynamic.zze.zzp(zzd.zza.zzbk(wrappedInAppPurchaseVerifier));
        this.zzCw = (zzfr) com.google.android.gms.dynamic.zze.zzp(zzd.zza.zzbk(wrappedInAppPurchase));
        this.zzCx = (Context) com.google.android.gms.dynamic.zze.zzp(zzd.zza.zzbk(wrappedAppContext));
        this.zzCy = (zzj) com.google.android.gms.dynamic.zze.zzp(zzd.zza.zzbk(wrappedOnPlayStorePurchaseFinishedListener));
    }

    public GInAppPurchaseManagerInfoParcel(Context appContext, zzk inAppPurchaseVerifier, zzfr inAppPurchase, zzj onPlayStorePurchaseFinishedListener) {
        this.versionCode = 2;
        this.zzCx = appContext;
        this.zzqE = inAppPurchaseVerifier;
        this.zzCw = inAppPurchase;
        this.zzCy = onPlayStorePurchaseFinishedListener;
    }

    public static void zza(Intent intent, GInAppPurchaseManagerInfoParcel gInAppPurchaseManagerInfoParcel) {
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("com.google.android.gms.ads.internal.purchase.InAppPurchaseManagerInfo", gInAppPurchaseManagerInfoParcel);
        intent.putExtra("com.google.android.gms.ads.internal.purchase.InAppPurchaseManagerInfo", bundle);
    }

    public static GInAppPurchaseManagerInfoParcel zzc(Intent intent) {
        try {
            Bundle bundleExtra = intent.getBundleExtra("com.google.android.gms.ads.internal.purchase.InAppPurchaseManagerInfo");
            bundleExtra.setClassLoader(GInAppPurchaseManagerInfoParcel.class.getClassLoader());
            return (GInAppPurchaseManagerInfoParcel) bundleExtra.getParcelable("com.google.android.gms.ads.internal.purchase.InAppPurchaseManagerInfo");
        } catch (Exception e) {
            return null;
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        zza.zza(this, out, flags);
    }

    IBinder zzfi() {
        return com.google.android.gms.dynamic.zze.zzy(this.zzCy).asBinder();
    }

    IBinder zzfj() {
        return com.google.android.gms.dynamic.zze.zzy(this.zzqE).asBinder();
    }

    IBinder zzfk() {
        return com.google.android.gms.dynamic.zze.zzy(this.zzCw).asBinder();
    }

    IBinder zzfl() {
        return com.google.android.gms.dynamic.zze.zzy(this.zzCx).asBinder();
    }
}
