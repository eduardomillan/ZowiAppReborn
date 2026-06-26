package com.google.android.gms.internal;

/* JADX INFO: loaded from: classes.dex */
public class zzik {
    private long zzJk;
    private long zzJl = Long.MIN_VALUE;
    private Object zzpd = new Object();

    public zzik(long j) {
        this.zzJk = j;
    }

    public boolean tryAcquire() {
        boolean z;
        synchronized (this.zzpd) {
            long jElapsedRealtime = com.google.android.gms.ads.internal.zzp.zzbz().elapsedRealtime();
            if (this.zzJl + this.zzJk > jElapsedRealtime) {
                z = false;
            } else {
                this.zzJl = jElapsedRealtime;
                z = true;
            }
        }
        return z;
    }
}
