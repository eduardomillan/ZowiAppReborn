package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzmn;

/* JADX INFO: loaded from: classes.dex */
class zzbe implements zzcd {
    private final long zzPk;
    private final int zzPl;
    private double zzPm;
    private long zzPn;
    private final Object zzPo = new Object();
    private final String zzPp;
    private final long zzaXx;
    private final zzmn zzpW;

    public zzbe(int i, long j, long j2, String str, zzmn zzmnVar) {
        this.zzPl = i;
        this.zzPm = this.zzPl;
        this.zzPk = j;
        this.zzaXx = j2;
        this.zzPp = str;
        this.zzpW = zzmnVar;
    }

    @Override // com.google.android.gms.tagmanager.zzcd
    public boolean zzkF() {
        boolean z = false;
        synchronized (this.zzPo) {
            long jCurrentTimeMillis = this.zzpW.currentTimeMillis();
            if (jCurrentTimeMillis - this.zzPn < this.zzaXx) {
                zzbg.zzaH("Excessive " + this.zzPp + " detected; call ignored.");
            } else {
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
                    zzbg.zzaH("Excessive " + this.zzPp + " detected; call ignored.");
                }
            }
        }
        return z;
    }
}
