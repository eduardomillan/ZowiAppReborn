package com.google.android.gms.auth.api.credentials.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;
import com.google.android.gms.auth.api.credentials.CredentialsApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzlb;

/* JADX INFO: loaded from: classes.dex */
public final class zzc implements CredentialsApi {

    private static class zza extends com.google.android.gms.auth.api.credentials.internal.zza {
        private zzlb.zzb<Status> zzSI;

        zza(zzlb.zzb<Status> zzbVar) {
            this.zzSI = zzbVar;
        }

        @Override // com.google.android.gms.auth.api.credentials.internal.zza, com.google.android.gms.auth.api.credentials.internal.zzg
        public void zzg(Status status) {
            this.zzSI.zzp(status);
        }
    }

    @Override // com.google.android.gms.auth.api.credentials.CredentialsApi
    public PendingResult<Status> delete(GoogleApiClient client, final Credential credential) {
        return client.zzb(new zzd<Status>(client) { // from class: com.google.android.gms.auth.api.credentials.internal.zzc.3
            @Override // com.google.android.gms.auth.api.credentials.internal.zzd
            protected void zza(Context context, zzh zzhVar) throws RemoteException {
                zzhVar.zza(new zza(this), new DeleteRequest(credential));
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.internal.zzlc
            /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
            public Status zzb(Status status) {
                return status;
            }
        });
    }

    @Override // com.google.android.gms.auth.api.credentials.CredentialsApi
    public PendingResult<Status> disableAutoSignIn(GoogleApiClient client) {
        return client.zzb(new zzd<Status>(client) { // from class: com.google.android.gms.auth.api.credentials.internal.zzc.4
            @Override // com.google.android.gms.auth.api.credentials.internal.zzd
            protected void zza(Context context, zzh zzhVar) throws RemoteException {
                zzhVar.zza(new zza(this));
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.internal.zzlc
            /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
            public Status zzb(Status status) {
                return status;
            }
        });
    }

    @Override // com.google.android.gms.auth.api.credentials.CredentialsApi
    public PendingResult<CredentialRequestResult> request(GoogleApiClient client, final CredentialRequest request) {
        return client.zza(new zzd<CredentialRequestResult>(client) { // from class: com.google.android.gms.auth.api.credentials.internal.zzc.1
            @Override // com.google.android.gms.auth.api.credentials.internal.zzd
            protected void zza(Context context, zzh zzhVar) throws RemoteException {
                zzhVar.zza(new com.google.android.gms.auth.api.credentials.internal.zza() { // from class: com.google.android.gms.auth.api.credentials.internal.zzc.1.1
                    @Override // com.google.android.gms.auth.api.credentials.internal.zza, com.google.android.gms.auth.api.credentials.internal.zzg
                    public void zza(Status status, Credential credential) {
                        zzb(new zzb(status, credential));
                    }

                    @Override // com.google.android.gms.auth.api.credentials.internal.zza, com.google.android.gms.auth.api.credentials.internal.zzg
                    public void zzg(Status status) {
                        zzb(zzb.zzh(status));
                    }
                }, request);
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.internal.zzlc
            /* JADX INFO: renamed from: zzi, reason: merged with bridge method [inline-methods] */
            public CredentialRequestResult zzb(Status status) {
                return zzb.zzh(status);
            }
        });
    }

    @Override // com.google.android.gms.auth.api.credentials.CredentialsApi
    public PendingResult<Status> save(GoogleApiClient client, final Credential credential) {
        return client.zzb(new zzd<Status>(client) { // from class: com.google.android.gms.auth.api.credentials.internal.zzc.2
            @Override // com.google.android.gms.auth.api.credentials.internal.zzd
            protected void zza(Context context, zzh zzhVar) throws RemoteException {
                zzhVar.zza(new zza(this), new SaveRequest(credential));
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.google.android.gms.internal.zzlc
            /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
            public Status zzb(Status status) {
                return status;
            }
        });
    }
}
