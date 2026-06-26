package com.google.android.gms.ads.internal.client;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.zzgr;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class AdRequestParcel implements SafeParcelable {
    public static final zzg CREATOR = new zzg();
    public final Bundle extras;
    public final int versionCode;
    public final long zzsB;
    public final int zzsC;
    public final List<String> zzsD;
    public final boolean zzsE;
    public final int zzsF;
    public final boolean zzsG;
    public final String zzsH;
    public final SearchAdRequestParcel zzsI;
    public final Location zzsJ;
    public final String zzsK;
    public final Bundle zzsL;
    public final Bundle zzsM;
    public final List<String> zzsN;
    public final String zzsO;
    public final String zzsP;

    public AdRequestParcel(int versionCode, long birthday, Bundle extras, int gender, List<String> keywords, boolean isTestDevice, int tagForChildDirectedTreatment, boolean manualImpressionsEnabled, String publisherProvidedId, SearchAdRequestParcel searchAdRequestParcel, Location location, String contentUrl, Bundle networkExtras, Bundle customTargeting, List<String> categoryExclusions, String requestAgent, String requestPackage) {
        this.versionCode = versionCode;
        this.zzsB = birthday;
        this.extras = extras == null ? new Bundle() : extras;
        this.zzsC = gender;
        this.zzsD = keywords;
        this.zzsE = isTestDevice;
        this.zzsF = tagForChildDirectedTreatment;
        this.zzsG = manualImpressionsEnabled;
        this.zzsH = publisherProvidedId;
        this.zzsI = searchAdRequestParcel;
        this.zzsJ = location;
        this.zzsK = contentUrl;
        this.zzsL = networkExtras;
        this.zzsM = customTargeting;
        this.zzsN = categoryExclusions;
        this.zzsO = requestAgent;
        this.zzsP = requestPackage;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (!(other instanceof AdRequestParcel)) {
            return false;
        }
        AdRequestParcel adRequestParcel = (AdRequestParcel) other;
        return this.versionCode == adRequestParcel.versionCode && this.zzsB == adRequestParcel.zzsB && com.google.android.gms.common.internal.zzw.equal(this.extras, adRequestParcel.extras) && this.zzsC == adRequestParcel.zzsC && com.google.android.gms.common.internal.zzw.equal(this.zzsD, adRequestParcel.zzsD) && this.zzsE == adRequestParcel.zzsE && this.zzsF == adRequestParcel.zzsF && this.zzsG == adRequestParcel.zzsG && com.google.android.gms.common.internal.zzw.equal(this.zzsH, adRequestParcel.zzsH) && com.google.android.gms.common.internal.zzw.equal(this.zzsI, adRequestParcel.zzsI) && com.google.android.gms.common.internal.zzw.equal(this.zzsJ, adRequestParcel.zzsJ) && com.google.android.gms.common.internal.zzw.equal(this.zzsK, adRequestParcel.zzsK) && com.google.android.gms.common.internal.zzw.equal(this.zzsL, adRequestParcel.zzsL) && com.google.android.gms.common.internal.zzw.equal(this.zzsM, adRequestParcel.zzsM) && com.google.android.gms.common.internal.zzw.equal(this.zzsN, adRequestParcel.zzsN) && com.google.android.gms.common.internal.zzw.equal(this.zzsO, adRequestParcel.zzsO) && com.google.android.gms.common.internal.zzw.equal(this.zzsP, adRequestParcel.zzsP);
    }

    public int hashCode() {
        return com.google.android.gms.common.internal.zzw.hashCode(Integer.valueOf(this.versionCode), Long.valueOf(this.zzsB), this.extras, Integer.valueOf(this.zzsC), this.zzsD, Boolean.valueOf(this.zzsE), Integer.valueOf(this.zzsF), Boolean.valueOf(this.zzsG), this.zzsH, this.zzsI, this.zzsJ, this.zzsK, this.zzsL, this.zzsM, this.zzsN, this.zzsO, this.zzsP);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        zzg.zza(this, out, flags);
    }
}
