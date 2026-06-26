package com.google.android.gms.auth.api.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.api.signin.EmailSignInConfig;
import com.google.android.gms.auth.api.signin.FacebookSignInConfig;
import com.google.android.gms.auth.api.signin.GoogleSignInConfig;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;

/* JADX INFO: loaded from: classes.dex */
public final class SignInConfiguration implements SafeParcelable {
    final int versionCode;
    private String zzTl;
    private final String zzTs;
    private EmailSignInConfig zzTt;
    private GoogleSignInConfig zzTu;
    private FacebookSignInConfig zzTv;
    private String zzTw;
    private static int zzTr = 31;
    public static final Parcelable.Creator<SignInConfiguration> CREATOR = new zzh();

    SignInConfiguration(int versionCode, String consumerPkgName, String serverClientId, EmailSignInConfig emailConfig, GoogleSignInConfig googleConfig, FacebookSignInConfig facebookConfig, String apiKey) {
        this.versionCode = versionCode;
        this.zzTs = zzx.zzcr(consumerPkgName);
        this.zzTl = serverClientId;
        this.zzTt = emailConfig;
        this.zzTu = googleConfig;
        this.zzTv = facebookConfig;
        this.zzTw = apiKey;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x002c A[Catch: ClassCastException -> 0x0097, TryCatch #0 {ClassCastException -> 0x0097, blocks: (B:5:0x0004, B:7:0x0012, B:9:0x001a, B:11:0x0024, B:13:0x002c, B:15:0x0036, B:17:0x003a, B:19:0x0040, B:21:0x0044, B:23:0x004a, B:25:0x004e, B:40:0x008a, B:37:0x007d, B:34:0x0070, B:31:0x0063, B:28:0x0056), top: B:46:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003a A[Catch: ClassCastException -> 0x0097, TryCatch #0 {ClassCastException -> 0x0097, blocks: (B:5:0x0004, B:7:0x0012, B:9:0x001a, B:11:0x0024, B:13:0x002c, B:15:0x0036, B:17:0x003a, B:19:0x0040, B:21:0x0044, B:23:0x004a, B:25:0x004e, B:40:0x008a, B:37:0x007d, B:34:0x0070, B:31:0x0063, B:28:0x0056), top: B:46:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0044 A[Catch: ClassCastException -> 0x0097, TryCatch #0 {ClassCastException -> 0x0097, blocks: (B:5:0x0004, B:7:0x0012, B:9:0x001a, B:11:0x0024, B:13:0x002c, B:15:0x0036, B:17:0x003a, B:19:0x0040, B:21:0x0044, B:23:0x004a, B:25:0x004e, B:40:0x008a, B:37:0x007d, B:34:0x0070, B:31:0x0063, B:28:0x0056), top: B:46:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0063 A[Catch: ClassCastException -> 0x0097, TryCatch #0 {ClassCastException -> 0x0097, blocks: (B:5:0x0004, B:7:0x0012, B:9:0x001a, B:11:0x0024, B:13:0x002c, B:15:0x0036, B:17:0x003a, B:19:0x0040, B:21:0x0044, B:23:0x004a, B:25:0x004e, B:40:0x008a, B:37:0x007d, B:34:0x0070, B:31:0x0063, B:28:0x0056), top: B:46:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0070 A[Catch: ClassCastException -> 0x0097, TryCatch #0 {ClassCastException -> 0x0097, blocks: (B:5:0x0004, B:7:0x0012, B:9:0x001a, B:11:0x0024, B:13:0x002c, B:15:0x0036, B:17:0x003a, B:19:0x0040, B:21:0x0044, B:23:0x004a, B:25:0x004e, B:40:0x008a, B:37:0x007d, B:34:0x0070, B:31:0x0063, B:28:0x0056), top: B:46:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x007d A[Catch: ClassCastException -> 0x0097, TryCatch #0 {ClassCastException -> 0x0097, blocks: (B:5:0x0004, B:7:0x0012, B:9:0x001a, B:11:0x0024, B:13:0x002c, B:15:0x0036, B:17:0x003a, B:19:0x0040, B:21:0x0044, B:23:0x004a, B:25:0x004e, B:40:0x008a, B:37:0x007d, B:34:0x0070, B:31:0x0063, B:28:0x0056), top: B:46:0x0004 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean equals(java.lang.Object r4) {
        /*
            r3 = this;
            r0 = 0
            if (r4 != 0) goto L4
        L3:
            return r0
        L4:
            com.google.android.gms.auth.api.signin.internal.SignInConfiguration r4 = (com.google.android.gms.auth.api.signin.internal.SignInConfiguration) r4     // Catch: java.lang.ClassCastException -> L97
            java.lang.String r1 = r3.zzTs     // Catch: java.lang.ClassCastException -> L97
            java.lang.String r2 = r4.zzme()     // Catch: java.lang.ClassCastException -> L97
            boolean r1 = r1.equals(r2)     // Catch: java.lang.ClassCastException -> L97
            if (r1 == 0) goto L3
            java.lang.String r1 = r3.zzTl     // Catch: java.lang.ClassCastException -> L97
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch: java.lang.ClassCastException -> L97
            if (r1 == 0) goto L56
            java.lang.String r1 = r4.zzmb()     // Catch: java.lang.ClassCastException -> L97
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch: java.lang.ClassCastException -> L97
            if (r1 == 0) goto L3
        L24:
            java.lang.String r1 = r3.zzTw     // Catch: java.lang.ClassCastException -> L97
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch: java.lang.ClassCastException -> L97
            if (r1 == 0) goto L63
            java.lang.String r1 = r4.zzmi()     // Catch: java.lang.ClassCastException -> L97
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch: java.lang.ClassCastException -> L97
            if (r1 == 0) goto L3
        L36:
            com.google.android.gms.auth.api.signin.EmailSignInConfig r1 = r3.zzTt     // Catch: java.lang.ClassCastException -> L97
            if (r1 != 0) goto L70
            com.google.android.gms.auth.api.signin.EmailSignInConfig r1 = r4.zzmf()     // Catch: java.lang.ClassCastException -> L97
            if (r1 != 0) goto L3
        L40:
            com.google.android.gms.auth.api.signin.FacebookSignInConfig r1 = r3.zzTv     // Catch: java.lang.ClassCastException -> L97
            if (r1 != 0) goto L7d
            com.google.android.gms.auth.api.signin.FacebookSignInConfig r1 = r4.zzmh()     // Catch: java.lang.ClassCastException -> L97
            if (r1 != 0) goto L3
        L4a:
            com.google.android.gms.auth.api.signin.GoogleSignInConfig r1 = r3.zzTu     // Catch: java.lang.ClassCastException -> L97
            if (r1 != 0) goto L8a
            com.google.android.gms.auth.api.signin.GoogleSignInConfig r1 = r4.zzmg()     // Catch: java.lang.ClassCastException -> L97
            if (r1 != 0) goto L3
        L54:
            r0 = 1
            goto L3
        L56:
            java.lang.String r1 = r3.zzTl     // Catch: java.lang.ClassCastException -> L97
            java.lang.String r2 = r4.zzmb()     // Catch: java.lang.ClassCastException -> L97
            boolean r1 = r1.equals(r2)     // Catch: java.lang.ClassCastException -> L97
            if (r1 == 0) goto L3
            goto L24
        L63:
            java.lang.String r1 = r3.zzTw     // Catch: java.lang.ClassCastException -> L97
            java.lang.String r2 = r4.zzmi()     // Catch: java.lang.ClassCastException -> L97
            boolean r1 = r1.equals(r2)     // Catch: java.lang.ClassCastException -> L97
            if (r1 == 0) goto L3
            goto L36
        L70:
            com.google.android.gms.auth.api.signin.EmailSignInConfig r1 = r3.zzTt     // Catch: java.lang.ClassCastException -> L97
            com.google.android.gms.auth.api.signin.EmailSignInConfig r2 = r4.zzmf()     // Catch: java.lang.ClassCastException -> L97
            boolean r1 = r1.equals(r2)     // Catch: java.lang.ClassCastException -> L97
            if (r1 == 0) goto L3
            goto L40
        L7d:
            com.google.android.gms.auth.api.signin.FacebookSignInConfig r1 = r3.zzTv     // Catch: java.lang.ClassCastException -> L97
            com.google.android.gms.auth.api.signin.FacebookSignInConfig r2 = r4.zzmh()     // Catch: java.lang.ClassCastException -> L97
            boolean r1 = r1.equals(r2)     // Catch: java.lang.ClassCastException -> L97
            if (r1 == 0) goto L3
            goto L4a
        L8a:
            com.google.android.gms.auth.api.signin.GoogleSignInConfig r1 = r3.zzTu     // Catch: java.lang.ClassCastException -> L97
            com.google.android.gms.auth.api.signin.GoogleSignInConfig r2 = r4.zzmg()     // Catch: java.lang.ClassCastException -> L97
            boolean r1 = r1.equals(r2)     // Catch: java.lang.ClassCastException -> L97
            if (r1 == 0) goto L3
            goto L54
        L97:
            r1 = move-exception
            goto L3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.auth.api.signin.internal.SignInConfiguration.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        return new zzc().zzl(this.zzTs).zzl(this.zzTl).zzl(this.zzTw).zzl(this.zzTt).zzl(this.zzTu).zzl(this.zzTv).zzmd();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        zzh.zza(this, out, flags);
    }

    public String zzmb() {
        return this.zzTl;
    }

    public String zzme() {
        return this.zzTs;
    }

    public EmailSignInConfig zzmf() {
        return this.zzTt;
    }

    public GoogleSignInConfig zzmg() {
        return this.zzTu;
    }

    public FacebookSignInConfig zzmh() {
        return this.zzTv;
    }

    public String zzmi() {
        return this.zzTw;
    }
}
