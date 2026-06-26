package com.google.android.gms.ads.internal.request;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class CapabilityParcel implements SafeParcelable {
    public static final Parcelable.Creator<CapabilityParcel> CREATOR = new zzi();
    public final int versionCode;
    public final boolean zzFa;
    public final boolean zzFb;

    CapabilityParcel(int versionCode, boolean inAppPurchaseSupported, boolean defaultInAppPurchaseSupported) {
        this.versionCode = versionCode;
        this.zzFa = inAppPurchaseSupported;
        this.zzFb = defaultInAppPurchaseSupported;
    }

    public CapabilityParcel(boolean inAppPurchaseSupported, boolean defaultInAppPurchaseSupported) {
        this(1, inAppPurchaseSupported, defaultInAppPurchaseSupported);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("iap_supported", this.zzFa);
        bundle.putBoolean("default_iap_supported", this.zzFb);
        return bundle;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        zzi.zza(this, dest, flags);
    }
}
