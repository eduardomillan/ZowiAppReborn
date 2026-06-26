package com.google.android.gms.playlog.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzqd;

/* JADX INFO: loaded from: classes.dex */
public class zzd implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private final zzqd.zza zzaRP;
    private zzf zzaRE = null;
    private boolean zzaRQ = true;

    public zzd(zzqd.zza zzaVar) {
        this.zzaRP = zzaVar;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
    public void onConnected(Bundle connectionHint) {
        this.zzaRE.zzap(false);
        if (this.zzaRQ && this.zzaRP != null) {
            this.zzaRP.zzBr();
        }
        this.zzaRQ = false;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
    public void onConnectionFailed(ConnectionResult result) {
        this.zzaRE.zzap(true);
        if (this.zzaRQ && this.zzaRP != null) {
            if (result.hasResolution()) {
                this.zzaRP.zzf(result.getResolution());
            } else {
                this.zzaRP.zzBs();
            }
        }
        this.zzaRQ = false;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
    public void onConnectionSuspended(int cause) {
        this.zzaRE.zzap(true);
    }

    public void zza(zzf zzfVar) {
        this.zzaRE = zzfVar;
    }

    public void zzao(boolean z) {
        this.zzaRQ = z;
    }
}
