package com.google.android.gms.internal;

import com.google.android.gms.internal.zzis;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzit<T> implements zzis<T> {
    protected T zzJN;
    private final Object zzpd = new Object();
    protected int zzys = 0;
    protected final BlockingQueue<zzit<T>.zza> zzJM = new LinkedBlockingQueue();

    class zza {
        public final zzis.zzc<T> zzJO;
        public final zzis.zza zzJP;

        public zza(zzis.zzc<T> zzcVar, zzis.zza zzaVar) {
            this.zzJO = zzcVar;
            this.zzJP = zzaVar;
        }
    }

    public int getStatus() {
        return this.zzys;
    }

    public void reject() {
        synchronized (this.zzpd) {
            if (this.zzys != 0) {
                throw new UnsupportedOperationException();
            }
            this.zzys = -1;
            Iterator it = this.zzJM.iterator();
            while (it.hasNext()) {
                ((zza) it.next()).zzJP.run();
            }
            this.zzJM.clear();
        }
    }

    public void zza(zzis.zzc<T> zzcVar, zzis.zza zzaVar) {
        synchronized (this.zzpd) {
            if (this.zzys == 1) {
                zzcVar.zzc(this.zzJN);
            } else if (this.zzys == -1) {
                zzaVar.run();
            } else if (this.zzys == 0) {
                this.zzJM.add(new zza(zzcVar, zzaVar));
            }
        }
    }

    public void zzg(T t) {
        synchronized (this.zzpd) {
            if (this.zzys != 0) {
                throw new UnsupportedOperationException();
            }
            this.zzJN = t;
            this.zzys = 1;
            Iterator it = this.zzJM.iterator();
            while (it.hasNext()) {
                ((zza) it.next()).zzJO.zzc(t);
            }
            this.zzJM.clear();
        }
    }
}
