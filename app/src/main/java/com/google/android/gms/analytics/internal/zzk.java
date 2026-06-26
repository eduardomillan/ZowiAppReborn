package com.google.android.gms.analytics.internal;

import com.google.android.gms.internal.zzpb;

/* JADX INFO: loaded from: classes.dex */
public class zzk extends zzd {
    private final zzpb zzNs;

    zzk(zzf zzfVar) {
        super(zzfVar);
        this.zzNs = new zzpb();
    }

    public void zzhM() {
        zzan zzanVarZzhQ = zzhQ();
        String strZzkp = zzanVarZzhQ.zzkp();
        if (strZzkp != null) {
            this.zzNs.setAppName(strZzkp);
        }
        String strZzkr = zzanVarZzhQ.zzkr();
        if (strZzkr != null) {
            this.zzNs.setAppVersion(strZzkr);
        }
    }

    @Override // com.google.android.gms.analytics.internal.zzd
    protected void zzhR() {
        zziw().zzyr().zza(this.zzNs);
        zzhM();
    }

    public zzpb zzjb() {
        zziE();
        return this.zzNs;
    }
}
