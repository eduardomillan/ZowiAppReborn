package com.google.android.gms.ads.internal.request;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.ads.internal.request.zzc;
import com.google.android.gms.ads.internal.zzp;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzbr;
import com.google.android.gms.internal.zzby;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzgs;
import com.google.android.gms.internal.zzgt;
import com.google.android.gms.internal.zzhz;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public abstract class zzd extends zzhz implements zzc.zza {
    private AdResponseParcel zzDf;
    private final zzc.zza zzEi;
    private final Object zzpd = new Object();
    private final AdRequestInfoParcel zzzz;

    @zzgr
    public static final class zza extends zzd {
        private final Context mContext;

        public zza(Context context, AdRequestInfoParcel adRequestInfoParcel, zzc.zza zzaVar) {
            super(adRequestInfoParcel, zzaVar);
            this.mContext = context;
        }

        @Override // com.google.android.gms.ads.internal.request.zzd
        public void zzfH() {
        }

        @Override // com.google.android.gms.ads.internal.request.zzd
        public zzj zzfI() {
            return zzgt.zza(this.mContext, new zzbr(zzby.zzul.get()), zzgs.zzfQ());
        }
    }

    @zzgr
    public static class zzb extends zzd implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
        private Context mContext;
        private final zzc.zza zzEi;
        protected zze zzEj;
        private boolean zzEk;
        private final Object zzpd;
        private AdRequestInfoParcel zzzz;

        public zzb(Context context, AdRequestInfoParcel adRequestInfoParcel, zzc.zza zzaVar) {
            Looper mainLooper;
            super(adRequestInfoParcel, zzaVar);
            this.zzpd = new Object();
            this.mContext = context;
            this.zzzz = adRequestInfoParcel;
            this.zzEi = zzaVar;
            if (zzby.zzuK.get().booleanValue()) {
                this.zzEk = true;
                mainLooper = zzp.zzbG().zzgM();
            } else {
                mainLooper = context.getMainLooper();
            }
            this.zzEj = new zze(context, mainLooper, this, this, adRequestInfoParcel.zzqj.zzJw);
            connect();
        }

        protected void connect() {
            this.zzEj.zzoZ();
        }

        @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
        public void onConnected(Bundle connectionHint) {
            zzfu();
        }

        @Override // com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
        public void onConnectionFailed(ConnectionResult result) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaF("Cannot connect to remote service, fallback to local instance.");
            zzfJ().zzfu();
            Bundle bundle = new Bundle();
            bundle.putString("action", "gms_connection_failed_fallback_to_local");
            zzp.zzbv().zzb(this.mContext, this.zzzz.zzqj.zzJu, "gmob-apps", bundle, true);
        }

        @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
        public void onConnectionSuspended(int cause) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaF("Disconnected from remote ad request service.");
        }

        @Override // com.google.android.gms.ads.internal.request.zzd
        public void zzfH() {
            synchronized (this.zzpd) {
                if (this.zzEj.isConnected() || this.zzEj.isConnecting()) {
                    this.zzEj.disconnect();
                }
                Binder.flushPendingCommands();
                if (this.zzEk) {
                    zzp.zzbG().zzgN();
                    this.zzEk = false;
                }
            }
        }

        @Override // com.google.android.gms.ads.internal.request.zzd
        public zzj zzfI() {
            zzj zzjVarZzfM;
            synchronized (this.zzpd) {
                try {
                    zzjVarZzfM = this.zzEj.zzfM();
                } catch (DeadObjectException | IllegalStateException e) {
                    zzjVarZzfM = null;
                }
            }
            return zzjVarZzfM;
        }

        zzhz zzfJ() {
            return new zza(this.mContext, this.zzzz, this.zzEi);
        }
    }

    public zzd(AdRequestInfoParcel adRequestInfoParcel, zzc.zza zzaVar) {
        this.zzzz = adRequestInfoParcel;
        this.zzEi = zzaVar;
    }

    @Override // com.google.android.gms.internal.zzhz
    public final void onStop() {
        zzfH();
    }

    boolean zza(zzj zzjVar, AdRequestInfoParcel adRequestInfoParcel) {
        try {
            zzjVar.zza(adRequestInfoParcel, new zzg(this));
            return true;
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not fetch ad response from ad request service.", e);
            zzp.zzby().zzc(e, true);
            this.zzEi.zzb(new AdResponseParcel(0));
            return false;
        } catch (NullPointerException e2) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not fetch ad response from ad request service due to an Exception.", e2);
            zzp.zzby().zzc(e2, true);
            this.zzEi.zzb(new AdResponseParcel(0));
            return false;
        } catch (SecurityException e3) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not fetch ad response from ad request service due to an Exception.", e3);
            zzp.zzby().zzc(e3, true);
            this.zzEi.zzb(new AdResponseParcel(0));
            return false;
        } catch (Throwable th) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not fetch ad response from ad request service due to an Exception.", th);
            zzp.zzby().zzc(th, true);
            this.zzEi.zzb(new AdResponseParcel(0));
            return false;
        }
    }

    @Override // com.google.android.gms.ads.internal.request.zzc.zza
    public void zzb(AdResponseParcel adResponseParcel) {
        synchronized (this.zzpd) {
            this.zzDf = adResponseParcel;
            this.zzpd.notify();
        }
    }

    @Override // com.google.android.gms.internal.zzhz
    public void zzbn() {
        try {
            zzj zzjVarZzfI = zzfI();
            if (zzjVarZzfI == null) {
                this.zzEi.zzb(new AdResponseParcel(0));
            } else if (zza(zzjVarZzfI, this.zzzz)) {
                zzi(zzp.zzbz().elapsedRealtime());
            }
        } finally {
            zzfH();
        }
    }

    protected boolean zzf(long j) {
        long jElapsedRealtime = 60000 - (zzp.zzbz().elapsedRealtime() - j);
        if (jElapsedRealtime <= 0) {
            return false;
        }
        try {
            this.zzpd.wait(jElapsedRealtime);
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public abstract void zzfH();

    public abstract zzj zzfI();

    protected void zzi(long j) {
        synchronized (this.zzpd) {
            while (this.zzDf == null) {
                if (!zzf(j)) {
                    if (this.zzDf != null) {
                        this.zzEi.zzb(this.zzDf);
                    } else {
                        this.zzEi.zzb(new AdResponseParcel(0));
                    }
                    return;
                }
            }
            this.zzEi.zzb(this.zzDf);
        }
    }
}
