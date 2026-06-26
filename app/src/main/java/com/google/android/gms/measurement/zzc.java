package com.google.android.gms.measurement;

import com.comscore.utils.Constants;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzmn;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class zzc {
    private final zzf zzaKP;
    private boolean zzaKQ;
    private long zzaKR;
    private long zzaKS;
    private long zzaKT;
    private long zzaKU;
    private long zzaKV;
    private boolean zzaKW;
    private final Map<Class<? extends zze>, zze> zzaKX;
    private final List<zzi> zzaKY;
    private final zzmn zzpW;

    zzc(zzc zzcVar) {
        this.zzaKP = zzcVar.zzaKP;
        this.zzpW = zzcVar.zzpW;
        this.zzaKR = zzcVar.zzaKR;
        this.zzaKS = zzcVar.zzaKS;
        this.zzaKT = zzcVar.zzaKT;
        this.zzaKU = zzcVar.zzaKU;
        this.zzaKV = zzcVar.zzaKV;
        this.zzaKY = new ArrayList(zzcVar.zzaKY);
        this.zzaKX = new HashMap(zzcVar.zzaKX.size());
        for (Map.Entry<Class<? extends zze>, zze> entry : zzcVar.zzaKX.entrySet()) {
            zze zzeVarZzf = zzf(entry.getKey());
            entry.getValue().zza(zzeVarZzf);
            this.zzaKX.put(entry.getKey(), zzeVarZzf);
        }
    }

    zzc(zzf zzfVar, zzmn zzmnVar) {
        zzx.zzw(zzfVar);
        zzx.zzw(zzmnVar);
        this.zzaKP = zzfVar;
        this.zzpW = zzmnVar;
        this.zzaKU = Constants.SESSION_INACTIVE_PERIOD;
        this.zzaKV = 3024000000L;
        this.zzaKX = new HashMap();
        this.zzaKY = new ArrayList();
    }

    private static <T extends zze> T zzf(Class<T> cls) {
        try {
            return cls.newInstance();
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("dataType default constructor is not accessible", e);
        } catch (InstantiationException e2) {
            throw new IllegalArgumentException("dataType doesn't have default constructor", e2);
        }
    }

    public void zzL(long j) {
        this.zzaKS = j;
    }

    public void zzb(zze zzeVar) {
        zzx.zzw(zzeVar);
        Class<?> cls = zzeVar.getClass();
        if (cls.getSuperclass() != zze.class) {
            throw new IllegalArgumentException();
        }
        zzeVar.zza(zze(cls));
    }

    public <T extends zze> T zzd(Class<T> cls) {
        return (T) this.zzaKX.get(cls);
    }

    public <T extends zze> T zze(Class<T> cls) {
        T t = (T) this.zzaKX.get(cls);
        if (t != null) {
            return t;
        }
        T t2 = (T) zzf(cls);
        this.zzaKX.put((Class<? extends zze>) cls, t2);
        return t2;
    }

    public zzc zzye() {
        return new zzc(this);
    }

    public Collection<zze> zzyf() {
        return this.zzaKX.values();
    }

    public List<zzi> zzyg() {
        return this.zzaKY;
    }

    public long zzyh() {
        return this.zzaKR;
    }

    public void zzyi() {
        zzym().zze(this);
    }

    public boolean zzyj() {
        return this.zzaKQ;
    }

    void zzyk() {
        this.zzaKT = this.zzpW.elapsedRealtime();
        if (this.zzaKS != 0) {
            this.zzaKR = this.zzaKS;
        } else {
            this.zzaKR = this.zzpW.currentTimeMillis();
        }
        this.zzaKQ = true;
    }

    zzf zzyl() {
        return this.zzaKP;
    }

    zzg zzym() {
        return this.zzaKP.zzym();
    }

    boolean zzyn() {
        return this.zzaKW;
    }

    void zzyo() {
        this.zzaKW = true;
    }
}
