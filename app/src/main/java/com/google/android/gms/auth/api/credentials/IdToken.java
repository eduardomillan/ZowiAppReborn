package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class IdToken implements SafeParcelable {
    public static final Parcelable.Creator<IdToken> CREATOR = new zzd();
    final int mVersionCode;
    private final String zzSk;
    private final String zzSs;

    IdToken(int version, String accountType, String idToken) {
        this.mVersionCode = version;
        this.zzSk = accountType;
        this.zzSs = idToken;
    }

    public IdToken(String accountType, String idToken) {
        this(1, accountType, idToken);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public String getAccountType() {
        return this.zzSk;
    }

    public String getIdToken() {
        return this.zzSs;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        zzd.zza(this, out, flags);
    }
}
