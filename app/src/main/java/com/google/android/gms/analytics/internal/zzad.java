package com.google.android.gms.analytics.internal;

/* JADX INFO: loaded from: classes.dex */
public class zzad {
    private final long zzPk;
    private final int zzPl;
    private double zzPm;
    private long zzPn;
    private final Object zzPo;
    private final String zzPp;

    public zzad(int i, long j, String str) {
        this.zzPo = new Object();
        this.zzPl = i;
        this.zzPm = this.zzPl;
        this.zzPk = j;
        this.zzPp = str;
    }

    public zzad(String str) {
        this(60, 2000L, str);
    }

    public boolean zzkF() {
        boolean z;
        synchronized (this.zzPo) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (this.zzPm < this.zzPl) {
                double d = (jCurrentTimeMillis - this.zzPn) / this.zzPk;
                if (d > 0.0d) {
                    this.zzPm = Math.min(this.zzPl, d + this.zzPm);
                }
            }
            this.zzPn = jCurrentTimeMillis;
            if (this.zzPm >= 1.0d) {
                this.zzPm -= 1.0d;
                z = true;
            } else {
                zzae.zzaH("Excessive " + this.zzPp + " detected; call ignored.");
                z = false;
            }
        }
        return z;
    }
}
