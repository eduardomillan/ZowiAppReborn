package com.google.android.gms.appdatasearch;

import android.accounts.Account;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.zzjr;
import com.google.android.gms.internal.zzjs;
import com.google.android.gms.internal.zzlb;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class GetRecentContextCall {

    public static class Request implements SafeParcelable {
        public static final zzf CREATOR = new zzf();
        final int mVersionCode;
        public final Account zzQq;
        public final boolean zzQr;
        public final boolean zzQs;
        public final boolean zzQt;
        public final String zzQu;

        public static final class zza {
            private Account zzQv;
            private boolean zzQw;
            private boolean zzQx;
            private boolean zzQy;
            private String zzQz;

            public zza zzL(boolean z) {
                this.zzQx = z;
                return this;
            }

            public zza zzby(String str) {
                this.zzQz = str;
                return this;
            }

            public Request zzlr() {
                return new Request(this.zzQv, this.zzQw, this.zzQx, this.zzQy, this.zzQz);
            }
        }

        public Request() {
            this(null, false, false, false, null);
        }

        Request(int versionCode, Account filterAccount, boolean includeDeviceOnlyData, boolean includeThirdPartyContext, boolean includeUsageEnded, String filterPackageName) {
            this.mVersionCode = versionCode;
            this.zzQq = filterAccount;
            this.zzQr = includeDeviceOnlyData;
            this.zzQs = includeThirdPartyContext;
            this.zzQt = includeUsageEnded;
            this.zzQu = filterPackageName;
        }

        public Request(Account filterAccount, boolean includeDeviceOnlyData, boolean includeThirdPartyContext, boolean includeUsageEnded, String filterPackageName) {
            this(1, filterAccount, includeDeviceOnlyData, includeThirdPartyContext, includeUsageEnded, filterPackageName);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            zzf zzfVar = CREATOR;
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            zzf zzfVar = CREATOR;
            zzf.zza(this, out, flags);
        }
    }

    public static class Response implements Result, SafeParcelable {
        public static final zzg CREATOR = new zzg();
        final int mVersionCode;
        public Status zzQA;
        public List<UsageInfo> zzQB;
        public String[] zzQC;

        public Response() {
            this.mVersionCode = 1;
        }

        Response(int versionCode, Status status, List<UsageInfo> usageInfo, String[] topRunningPackages) {
            this.mVersionCode = versionCode;
            this.zzQA = status;
            this.zzQB = usageInfo;
            this.zzQC = topRunningPackages;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            zzg zzgVar = CREATOR;
            return 0;
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.zzQA;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            zzg zzgVar = CREATOR;
            zzg.zza(this, out, flags);
        }
    }

    public static class zza extends zzlb.zza<Response, zzjs> {
        private final Request zzQo;

        public zza(Request request, GoogleApiClient googleApiClient) {
            super(com.google.android.gms.appdatasearch.zza.zzPT, googleApiClient);
            this.zzQo = request;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.zzlc
        /* JADX INFO: renamed from: zza, reason: merged with bridge method [inline-methods] */
        public Response zzb(Status status) {
            Response response = new Response();
            response.zzQA = status;
            return response;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.zzlb.zza
        public void zza(zzjs zzjsVar) throws RemoteException {
            zzjsVar.zzlw().zza(this.zzQo, new zzjr<Response>(this) { // from class: com.google.android.gms.appdatasearch.GetRecentContextCall.zza.1
                /* JADX WARN: Type inference incomplete: some casts might be missing */
                @Override // com.google.android.gms.internal.zzjr, com.google.android.gms.internal.zzjq
                public void zza(Response response) {
                    this.zzRb.zzp((T) response);
                }
            });
        }
    }
}
