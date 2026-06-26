package com.google.android.gms.auth.api.signin;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Patterns;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;

/* JADX INFO: loaded from: classes.dex */
public class EmailSignInConfig implements SafeParcelable {
    public static final Parcelable.Creator<EmailSignInConfig> CREATOR = new zza();
    final int versionCode;
    private final Uri zzSU;
    private String zzSV;
    private Uri zzSW;

    EmailSignInConfig(int versionCode, Uri serverWidgetUrl, String modeQueryName, Uri tosUrl) {
        zzx.zzb(serverWidgetUrl, "Server widget url cannot be null in order to use email/password sign in.");
        zzx.zzh(serverWidgetUrl.toString(), "Server widget url cannot be null in order to use email/password sign in.");
        zzx.zzb(Patterns.WEB_URL.matcher(serverWidgetUrl.toString()).matches(), "Invalid server widget url");
        this.versionCode = versionCode;
        this.zzSU = serverWidgetUrl;
        this.zzSV = modeQueryName;
        this.zzSW = tosUrl;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj != null) {
            try {
                EmailSignInConfig emailSignInConfig = (EmailSignInConfig) obj;
                if (this.zzSU.equals(emailSignInConfig.zzlO())) {
                    if (this.zzSW == null) {
                        if (emailSignInConfig.zzlP() == null) {
                            if (TextUtils.isEmpty(this.zzSV) ? this.zzSV.equals(emailSignInConfig.zzlQ()) : TextUtils.isEmpty(emailSignInConfig.zzlQ())) {
                            }
                        }
                    } else if (this.zzSW.equals(emailSignInConfig.zzlP())) {
                        z = TextUtils.isEmpty(this.zzSV) ? true : true;
                    }
                }
            } catch (ClassCastException e) {
            }
        }
        return z;
    }

    public int hashCode() {
        return new com.google.android.gms.auth.api.signin.internal.zzc().zzl(this.zzSU).zzl(this.zzSW).zzl(this.zzSV).zzmd();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        zza.zza(this, out, flags);
    }

    public Uri zzlO() {
        return this.zzSU;
    }

    public Uri zzlP() {
        return this.zzSW;
    }

    public String zzlQ() {
        return this.zzSV;
    }
}
