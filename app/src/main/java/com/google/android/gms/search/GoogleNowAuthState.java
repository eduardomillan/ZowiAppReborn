package com.google.android.gms.search;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class GoogleNowAuthState implements SafeParcelable {
    public static final Parcelable.Creator<GoogleNowAuthState> CREATOR = new zza();
    final int mVersionCode;
    String zzaUM;
    String zzaUN;
    long zzaUO;

    GoogleNowAuthState(int versionCode, String authCode, String accessToken, long nextAllowedTimeMillis) {
        this.mVersionCode = versionCode;
        this.zzaUM = authCode;
        this.zzaUN = accessToken;
        this.zzaUO = nextAllowedTimeMillis;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getAccessToken() {
        return this.zzaUN;
    }

    public String getAuthCode() {
        return this.zzaUM;
    }

    public long getNextAllowedTimeMillis() {
        return this.zzaUO;
    }

    public String toString() {
        return "mAuthCode = " + this.zzaUM + "\nmAccessToken = " + this.zzaUN + "\nmNextAllowedTimeMillis = " + this.zzaUO;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        zza.zza(this, parcel, i);
    }
}
