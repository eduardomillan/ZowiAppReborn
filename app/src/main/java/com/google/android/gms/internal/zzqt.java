package com.google.android.gms.internal;

import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzqq;
import com.google.android.gms.search.GoogleNowAuthState;
import com.google.android.gms.search.SearchAuth;
import com.google.android.gms.search.SearchAuthApi;

/* JADX INFO: loaded from: classes.dex */
public class zzqt implements SearchAuthApi {

    static abstract class zza extends zzqq.zza {
        zza() {
        }

        @Override // com.google.android.gms.internal.zzqq
        public void zza(Status status, GoogleNowAuthState googleNowAuthState) {
            throw new UnsupportedOperationException();
        }

        @Override // com.google.android.gms.internal.zzqq
        public void zzbb(Status status) {
            throw new UnsupportedOperationException();
        }
    }

    static class zzb extends zzlb.zza<Status, zzqs> {
        private final GoogleApiClient zzVs;
        private final String zzaUN;
        private final boolean zzaUQ;

        protected zzb(GoogleApiClient googleApiClient, String str) {
            super(SearchAuth.zzRk, googleApiClient);
            this.zzaUQ = Log.isLoggable("SearchAuth", 3);
            this.zzVs = googleApiClient;
            this.zzaUN = str;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.zzlb.zza
        public void zza(zzqs zzqsVar) throws RemoteException {
            if (this.zzaUQ) {
                Log.d("SearchAuth", "ClearTokenImpl started");
            }
            String packageName = this.zzVs.getContext().getPackageName();
            zzqsVar.zzpc().zzb(new zza() { // from class: com.google.android.gms.internal.zzqt.zzb.1
                @Override // com.google.android.gms.internal.zzqt.zza, com.google.android.gms.internal.zzqq
                public void zzbb(Status status) {
                    if (zzb.this.zzaUQ) {
                        Log.d("SearchAuth", "ClearTokenImpl success");
                    }
                    zzb.this.zzb(status);
                }
            }, packageName, this.zzaUN);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.zzlc
        /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
        public Status zzb(Status status) {
            if (this.zzaUQ) {
                Log.d("SearchAuth", "ClearTokenImpl received failure: " + status.getStatusMessage());
            }
            return status;
        }
    }

    static class zzc extends zzlb.zza<SearchAuthApi.GoogleNowAuthResult, zzqs> {
        private final GoogleApiClient zzVs;
        private final boolean zzaUQ;
        private final String zzaUS;

        protected zzc(GoogleApiClient googleApiClient, String str) {
            super(SearchAuth.zzRk, googleApiClient);
            this.zzaUQ = Log.isLoggable("SearchAuth", 3);
            this.zzVs = googleApiClient;
            this.zzaUS = str;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.zzlb.zza
        public void zza(zzqs zzqsVar) throws RemoteException {
            if (this.zzaUQ) {
                Log.d("SearchAuth", "GetGoogleNowAuthImpl started");
            }
            String packageName = this.zzVs.getContext().getPackageName();
            zzqsVar.zzpc().zza(new zza() { // from class: com.google.android.gms.internal.zzqt.zzc.1
                @Override // com.google.android.gms.internal.zzqt.zza, com.google.android.gms.internal.zzqq
                public void zza(Status status, GoogleNowAuthState googleNowAuthState) {
                    if (zzc.this.zzaUQ) {
                        Log.d("SearchAuth", "GetGoogleNowAuthImpl success");
                    }
                    zzc.this.zzb(new zzd(status, googleNowAuthState));
                }
            }, packageName, this.zzaUS);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.google.android.gms.internal.zzlc
        /* JADX INFO: renamed from: zzbc, reason: merged with bridge method [inline-methods] */
        public SearchAuthApi.GoogleNowAuthResult zzb(Status status) {
            if (this.zzaUQ) {
                Log.d("SearchAuth", "GetGoogleNowAuthImpl received failure: " + status.getStatusMessage());
            }
            return new zzd(status, null);
        }
    }

    static class zzd implements SearchAuthApi.GoogleNowAuthResult {
        private final Status zzSC;
        private final GoogleNowAuthState zzaUU;

        zzd(Status status, GoogleNowAuthState googleNowAuthState) {
            this.zzSC = status;
            this.zzaUU = googleNowAuthState;
        }

        @Override // com.google.android.gms.search.SearchAuthApi.GoogleNowAuthResult
        public GoogleNowAuthState getGoogleNowAuthState() {
            return this.zzaUU;
        }

        @Override // com.google.android.gms.common.api.Result
        public Status getStatus() {
            return this.zzSC;
        }
    }

    @Override // com.google.android.gms.search.SearchAuthApi
    public PendingResult<Status> clearToken(GoogleApiClient client, String accessToken) {
        return client.zza(new zzb(client, accessToken));
    }

    @Override // com.google.android.gms.search.SearchAuthApi
    public PendingResult<SearchAuthApi.GoogleNowAuthResult> getGoogleNowAuth(GoogleApiClient client, String webAppClientId) {
        return client.zza(new zzc(client, webAppClientId));
    }
}
