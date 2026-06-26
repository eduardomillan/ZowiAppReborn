package com.google.android.gms.analytics.internal;

import com.google.android.gms.internal.zzmn;

/* JADX INFO: loaded from: classes.dex */
class zzaj {
    private final zzmn zzpW;
    private long zzzB;

    public zzaj(zzmn zzmnVar) {
        com.google.android.gms.common.internal.zzx.zzw(zzmnVar);
        this.zzpW = zzmnVar;
    }

    public zzaj(zzmn zzmnVar, long j) {
        com.google.android.gms.common.internal.zzx.zzw(zzmnVar);
        this.zzpW = zzmnVar;
        this.zzzB = j;
    }

    public void clear() {
        this.zzzB = 0L;
    }

    public void start() {
        this.zzzB = this.zzpW.elapsedRealtime();
    }

    public boolean zzv(long j) {
        return this.zzzB == 0 || this.zzpW.elapsedRealtime() - this.zzzB > j;
    }
}
