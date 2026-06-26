package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzlb;

/* JADX INFO: loaded from: classes.dex */
abstract class zzma<R extends Result> extends zzlb.zza<R, zzmb> {

    static abstract class zza extends zzma<Status> {
        public zza(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        @Override // com.google.android.gms.internal.zzlc
        /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
        public Status zzb(Status status) {
            return status;
        }
    }

    public zzma(GoogleApiClient googleApiClient) {
        super(zzlx.zzRk, googleApiClient);
    }
}
