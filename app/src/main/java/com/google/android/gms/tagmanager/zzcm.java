package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzaf;
import com.google.android.gms.tagmanager.zzp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
class zzcm implements zzp.zze {
    private boolean mClosed;
    private final Context mContext;
    private final String zzaVQ;
    private String zzaWn;
    private zzbf<zzaf.zzj> zzaYk;
    private zzs zzaYl;
    private final ScheduledExecutorService zzaYn;
    private final zza zzaYo;
    private ScheduledFuture<?> zzaYp;

    interface zza {
        zzcl zza(zzs zzsVar);
    }

    interface zzb {
        ScheduledExecutorService zzDt();
    }

    public zzcm(Context context, String str, zzs zzsVar) {
        this(context, str, zzsVar, null, null);
    }

    zzcm(Context context, String str, zzs zzsVar, zzb zzbVar, zza zzaVar) {
        this.zzaYl = zzsVar;
        this.mContext = context;
        this.zzaVQ = str;
        this.zzaYn = (zzbVar == null ? new zzb() { // from class: com.google.android.gms.tagmanager.zzcm.1
            @Override // com.google.android.gms.tagmanager.zzcm.zzb
            public ScheduledExecutorService zzDt() {
                return Executors.newSingleThreadScheduledExecutor();
            }
        } : zzbVar).zzDt();
        if (zzaVar == null) {
            this.zzaYo = new zza() { // from class: com.google.android.gms.tagmanager.zzcm.2
                @Override // com.google.android.gms.tagmanager.zzcm.zza
                public zzcl zza(zzs zzsVar2) {
                    return new zzcl(zzcm.this.mContext, zzcm.this.zzaVQ, zzsVar2);
                }
            };
        } else {
            this.zzaYo = zzaVar;
        }
    }

    private synchronized void zzDs() {
        if (this.mClosed) {
            throw new IllegalStateException("called method after closed");
        }
    }

    private zzcl zzeX(String str) {
        zzcl zzclVarZza = this.zzaYo.zza(this.zzaYl);
        zzclVarZza.zza(this.zzaYk);
        zzclVarZza.zzeH(this.zzaWn);
        zzclVarZza.zzeW(str);
        return zzclVarZza;
    }

    @Override // com.google.android.gms.common.api.Releasable
    public synchronized void release() {
        zzDs();
        if (this.zzaYp != null) {
            this.zzaYp.cancel(false);
        }
        this.zzaYn.shutdown();
        this.mClosed = true;
    }

    @Override // com.google.android.gms.tagmanager.zzp.zze
    public synchronized void zza(zzbf<zzaf.zzj> zzbfVar) {
        zzDs();
        this.zzaYk = zzbfVar;
    }

    @Override // com.google.android.gms.tagmanager.zzp.zze
    public synchronized void zzeH(String str) {
        zzDs();
        this.zzaWn = str;
    }

    @Override // com.google.android.gms.tagmanager.zzp.zze
    public synchronized void zzf(long j, String str) {
        zzbg.v("loadAfterDelay: containerId=" + this.zzaVQ + " delay=" + j);
        zzDs();
        if (this.zzaYk == null) {
            throw new IllegalStateException("callback must be set before loadAfterDelay() is called.");
        }
        if (this.zzaYp != null) {
            this.zzaYp.cancel(false);
        }
        this.zzaYp = this.zzaYn.schedule(zzeX(str), j, TimeUnit.MILLISECONDS);
    }
}
