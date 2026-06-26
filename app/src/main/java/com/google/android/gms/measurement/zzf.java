package com.google.android.gms.measurement;

import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzmn;
import com.google.android.gms.measurement.zzf;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzf<T extends zzf> {
    private final zzg zzaKZ;
    protected final zzc zzaLa;
    private final List<zzd> zzaLb;

    protected zzf(zzg zzgVar, zzmn zzmnVar) {
        zzx.zzw(zzgVar);
        this.zzaKZ = zzgVar;
        this.zzaLb = new ArrayList();
        zzc zzcVar = new zzc(this, zzmnVar);
        zzcVar.zzyo();
        this.zzaLa = zzcVar;
    }

    protected void zza(zzc zzcVar) {
    }

    protected void zzd(zzc zzcVar) {
        Iterator<zzd> it = this.zzaLb.iterator();
        while (it.hasNext()) {
            it.next().zza(this, zzcVar);
        }
    }

    public zzc zzhG() {
        zzc zzcVarZzye = this.zzaLa.zzye();
        zzd(zzcVarZzye);
        return zzcVarZzye;
    }

    protected zzg zzym() {
        return this.zzaKZ;
    }

    public zzc zzyp() {
        return this.zzaLa;
    }

    public List<zzi> zzyq() {
        return this.zzaLa.zzyg();
    }
}
