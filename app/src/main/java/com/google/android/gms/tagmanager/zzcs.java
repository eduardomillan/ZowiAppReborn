package com.google.android.gms.tagmanager;

/* JADX INFO: loaded from: classes.dex */
class zzcs implements zzcd {
    private final long zzPk;
    private final int zzPl;
    private double zzPm;
    private final Object zzPo;
    private long zzaYS;

    public zzcs() {
        this(60, 2000L);
    }

    public zzcs(int i, long j) {
        this.zzPo = new Object();
        this.zzPl = i;
        this.zzPm = this.zzPl;
        this.zzPk = j;
    }

    @Override // com.google.android.gms.tagmanager.zzcd
    public boolean zzkF() {
        boolean z;
        synchronized (this.zzPo) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (this.zzPm < this.zzPl) {
                double d = (jCurrentTimeMillis - this.zzaYS) / this.zzPk;
                if (d > 0.0d) {
                    this.zzPm = Math.min(this.zzPl, d + this.zzPm);
                }
            }
            this.zzaYS = jCurrentTimeMillis;
            if (this.zzPm >= 1.0d) {
                this.zzPm -= 1.0d;
                z = true;
            } else {
                zzbg.zzaH("No more tokens available.");
                z = false;
            }
        }
        return z;
    }
}
