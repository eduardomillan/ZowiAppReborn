package com.google.android.gms.analytics.internal;

import android.os.Handler;
import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
abstract class zzt {
    private static volatile Handler zzNX;
    private final zzf zzME;
    private volatile long zzNY;
    private boolean zzNZ;
    private final Runnable zzx;

    zzt(zzf zzfVar) {
        com.google.android.gms.common.internal.zzx.zzw(zzfVar);
        this.zzME = zzfVar;
        this.zzx = new Runnable() { // from class: com.google.android.gms.analytics.internal.zzt.1
            @Override // java.lang.Runnable
            public void run() {
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    zzt.this.zzME.zziw().zzg(this);
                    return;
                }
                boolean zZzbp = zzt.this.zzbp();
                zzt.this.zzNY = 0L;
                if (!zZzbp || zzt.this.zzNZ) {
                    return;
                }
                zzt.this.run();
            }
        };
    }

    private Handler getHandler() {
        Handler handler;
        if (zzNX != null) {
            return zzNX;
        }
        synchronized (zzt.class) {
            if (zzNX == null) {
                zzNX = new Handler(this.zzME.getContext().getMainLooper());
            }
            handler = zzNX;
        }
        return handler;
    }

    public void cancel() {
        this.zzNY = 0L;
        getHandler().removeCallbacks(this.zzx);
    }

    public abstract void run();

    public boolean zzbp() {
        return this.zzNY != 0;
    }

    public long zzkh() {
        if (this.zzNY == 0) {
            return 0L;
        }
        return Math.abs(this.zzME.zzit().currentTimeMillis() - this.zzNY);
    }

    public void zzt(long j) {
        cancel();
        if (j >= 0) {
            this.zzNY = this.zzME.zzit().currentTimeMillis();
            if (getHandler().postDelayed(this.zzx, j)) {
                return;
            }
            this.zzME.zziu().zze("Failed to schedule delayed post. time", Long.valueOf(j));
        }
    }

    public void zzu(long j) {
        if (zzbp()) {
            if (j < 0) {
                cancel();
                return;
            }
            long jAbs = j - Math.abs(this.zzME.zzit().currentTimeMillis() - this.zzNY);
            long j2 = jAbs >= 0 ? jAbs : 0L;
            getHandler().removeCallbacks(this.zzx);
            if (getHandler().postDelayed(this.zzx, j2)) {
                return;
            }
            this.zzME.zziu().zze("Failed to adjust delayed post. time", Long.valueOf(j2));
        }
    }
}
