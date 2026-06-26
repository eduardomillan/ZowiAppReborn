package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class zzir {
    private final Object zzJI = new Object();
    private final List<Runnable> zzJJ = new ArrayList();
    private final List<Runnable> zzJK = new ArrayList();
    private boolean zzJL = false;

    private void zze(Runnable runnable) {
        zzic.zza(runnable);
    }

    private void zzf(Runnable runnable) {
        com.google.android.gms.ads.internal.util.client.zza.zzJt.post(runnable);
    }

    public void zzc(Runnable runnable) {
        synchronized (this.zzJI) {
            if (this.zzJL) {
                zze(runnable);
            } else {
                this.zzJJ.add(runnable);
            }
        }
    }

    public void zzd(Runnable runnable) {
        synchronized (this.zzJI) {
            if (this.zzJL) {
                zzf(runnable);
            } else {
                this.zzJK.add(runnable);
            }
        }
    }

    public void zzgV() {
        synchronized (this.zzJI) {
            if (this.zzJL) {
                return;
            }
            Iterator<Runnable> it = this.zzJJ.iterator();
            while (it.hasNext()) {
                zze(it.next());
            }
            Iterator<Runnable> it2 = this.zzJK.iterator();
            while (it2.hasNext()) {
                zzf(it2.next());
            }
            this.zzJJ.clear();
            this.zzJK.clear();
            this.zzJL = true;
        }
    }
}
