package com.google.android.gms.internal;

import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzhx {
    private final String zzHG;
    private int zzIi;
    private int zzIj;
    private final zzhu zzpV;
    private final Object zzpd;

    zzhx(zzhu zzhuVar, String str) {
        this.zzpd = new Object();
        this.zzpV = zzhuVar;
        this.zzHG = str;
    }

    public zzhx(String str) {
        this(com.google.android.gms.ads.internal.zzp.zzby(), str);
    }

    public Bundle toBundle() {
        Bundle bundle;
        synchronized (this.zzpd) {
            bundle = new Bundle();
            bundle.putInt("pmnli", this.zzIi);
            bundle.putInt("pmnll", this.zzIj);
        }
        return bundle;
    }

    public void zzf(int i, int i2) {
        synchronized (this.zzpd) {
            this.zzIi = i;
            this.zzIj = i2;
            this.zzpV.zza(this.zzHG, this);
        }
    }
}
