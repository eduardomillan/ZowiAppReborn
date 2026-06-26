package com.google.android.gms.analytics.internal;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzd extends zzc {
    private boolean zzMF;
    private boolean zzMG;

    protected zzd(zzf zzfVar) {
        super(zzfVar);
    }

    public boolean isInitialized() {
        return this.zzMF && !this.zzMG;
    }

    public void zza() {
        zzhR();
        this.zzMF = true;
    }

    protected abstract void zzhR();

    protected void zziE() {
        if (!isInitialized()) {
            throw new IllegalStateException("Not initialized");
        }
    }
}
