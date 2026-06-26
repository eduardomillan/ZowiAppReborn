package com.google.android.gms.auth.api.credentials.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class DeleteRequest implements SafeParcelable {
    public static final Parcelable.Creator<DeleteRequest> CREATOR = new zzf();
    final int mVersionCode;
    private final Credential zzSD;

    DeleteRequest(int version, Credential credential) {
        this.mVersionCode = version;
        this.zzSD = credential;
    }

    public DeleteRequest(Credential credential) {
        this(1, credential);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Credential getCredential() {
        return this.zzSD;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        zzf.zza(this, out, flags);
    }
}
