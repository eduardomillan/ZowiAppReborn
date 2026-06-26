package com.google.android.gms.auth.api.signin.internal;

/* JADX INFO: loaded from: classes.dex */
public class zzc {
    static int zzTo = 31;
    private int zzTp = 1;

    public zzc zzP(boolean z) {
        this.zzTp = (z ? 1 : 0) + (this.zzTp * zzTo);
        return this;
    }

    public zzc zzl(Object obj) {
        this.zzTp = (obj == null ? 0 : obj.hashCode()) + (this.zzTp * zzTo);
        return this;
    }

    public int zzmd() {
        return this.zzTp;
    }
}
