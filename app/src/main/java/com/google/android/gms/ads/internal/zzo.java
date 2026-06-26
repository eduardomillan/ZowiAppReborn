package com.google.android.gms.ads.internal;

import android.os.Handler;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzid;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzo {
    private final zza zzpG;
    private AdRequestParcel zzpH;
    private boolean zzpI;
    private boolean zzpJ;
    private long zzpK;
    private final Runnable zzx;

    public static class zza {
        private final Handler mHandler;

        public zza(Handler handler) {
            this.mHandler = handler;
        }

        public boolean postDelayed(Runnable runnable, long timeFromNowInMillis) {
            return this.mHandler.postDelayed(runnable, timeFromNowInMillis);
        }

        public void removeCallbacks(Runnable runnable) {
            this.mHandler.removeCallbacks(runnable);
        }
    }

    public zzo(com.google.android.gms.ads.internal.zza zzaVar) {
        this(zzaVar, new zza(zzid.zzIE));
    }

    zzo(com.google.android.gms.ads.internal.zza zzaVar, zza zzaVar2) {
        this.zzpI = false;
        this.zzpJ = false;
        this.zzpK = 0L;
        this.zzpG = zzaVar2;
        final WeakReference weakReference = new WeakReference(zzaVar);
        this.zzx = new Runnable() { // from class: com.google.android.gms.ads.internal.zzo.1
            @Override // java.lang.Runnable
            public void run() {
                zzo.this.zzpI = false;
                com.google.android.gms.ads.internal.zza zzaVar3 = (com.google.android.gms.ads.internal.zza) weakReference.get();
                if (zzaVar3 != null) {
                    zzaVar3.zzd(zzo.this.zzpH);
                }
            }
        };
    }

    public void cancel() {
        this.zzpI = false;
        this.zzpG.removeCallbacks(this.zzx);
    }

    public void pause() {
        this.zzpJ = true;
        if (this.zzpI) {
            this.zzpG.removeCallbacks(this.zzx);
        }
    }

    public void resume() {
        this.zzpJ = false;
        if (this.zzpI) {
            this.zzpI = false;
            zza(this.zzpH, this.zzpK);
        }
    }

    public void zza(AdRequestParcel adRequestParcel, long j) {
        if (this.zzpI) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("An ad refresh is already scheduled.");
            return;
        }
        this.zzpH = adRequestParcel;
        this.zzpI = true;
        this.zzpK = j;
        if (this.zzpJ) {
            return;
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaG("Scheduling ad refresh " + j + " milliseconds from now.");
        this.zzpG.postDelayed(this.zzx, j);
    }

    public boolean zzbp() {
        return this.zzpI;
    }

    public void zzg(AdRequestParcel adRequestParcel) {
        zza(adRequestParcel, 60000L);
    }
}
