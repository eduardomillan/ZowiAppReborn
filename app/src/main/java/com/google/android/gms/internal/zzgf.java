package com.google.android.gms.internal;

import android.content.Context;
import android.os.SystemClock;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.internal.zzgg;
import com.google.android.gms.internal.zzhs;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public abstract class zzgf extends zzhz {
    protected final Context mContext;
    protected final zzgg.zza zzDd;
    protected final zzhs.zza zzDe;
    protected AdResponseParcel zzDf;
    protected final Object zzDh;
    protected final Object zzpd;

    protected static final class zza extends Exception {
        private final int zzDv;

        public zza(String str, int i) {
            super(str);
            this.zzDv = i;
        }

        public int getErrorCode() {
            return this.zzDv;
        }
    }

    protected zzgf(Context context, zzhs.zza zzaVar, zzgg.zza zzaVar2) {
        super(true);
        this.zzpd = new Object();
        this.zzDh = new Object();
        this.mContext = context;
        this.zzDe = zzaVar;
        this.zzDf = zzaVar.zzHD;
        this.zzDd = zzaVar2;
    }

    @Override // com.google.android.gms.internal.zzhz
    public void onStop() {
    }

    protected abstract zzhs zzA(int i);

    @Override // com.google.android.gms.internal.zzhz
    public void zzbn() {
        synchronized (this.zzpd) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaF("AdRendererBackgroundTask started.");
            int i = this.zzDe.errorCode;
            try {
                zzh(SystemClock.elapsedRealtime());
            } catch (zza e) {
                int errorCode = e.getErrorCode();
                if (errorCode == 3 || errorCode == -1) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzaG(e.getMessage());
                } else {
                    com.google.android.gms.ads.internal.util.client.zzb.zzaH(e.getMessage());
                }
                if (this.zzDf == null) {
                    this.zzDf = new AdResponseParcel(errorCode);
                } else {
                    this.zzDf = new AdResponseParcel(errorCode, this.zzDf.zzzc);
                }
                zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.internal.zzgf.1
                    @Override // java.lang.Runnable
                    public void run() {
                        zzgf.this.onStop();
                    }
                });
                i = errorCode;
            }
            final zzhs zzhsVarZzA = zzA(i);
            zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.internal.zzgf.2
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (zzgf.this.zzpd) {
                        zzgf.this.zzi(zzhsVarZzA);
                    }
                }
            });
        }
    }

    protected abstract void zzh(long j) throws zza;

    protected void zzi(zzhs zzhsVar) {
        this.zzDd.zzb(zzhsVar);
    }
}
