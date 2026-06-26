package com.google.android.gms.ads.internal.request;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class StringParcel implements SafeParcelable {
    public static final Parcelable.Creator<StringParcel> CREATOR = new zzn();
    final int mVersionCode;
    String zzvY;

    StringParcel(int versionCode, String content) {
        this.mVersionCode = versionCode;
        this.zzvY = content;
    }

    public StringParcel(String content) {
        this.mVersionCode = 1;
        this.zzvY = content;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        zzn.zza(this, dest, flags);
    }

    public String zzfP() {
        return this.zzvY;
    }
}
