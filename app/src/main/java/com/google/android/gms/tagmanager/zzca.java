package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzca extends zzak {
    private static final String zzaWU = com.google.android.gms.internal.zzae.ARG0.toString();
    private static final String zzaXS = com.google.android.gms.internal.zzae.ARG1.toString();

    public zzca(String str) {
        super(str, zzaWU, zzaXS);
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public /* bridge */ /* synthetic */ String zzCT() {
        return super.zzCT();
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public /* bridge */ /* synthetic */ Set zzCU() {
        return super.zzCU();
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzCo() {
        return true;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzI(Map<String, zzag.zza> map) {
        Iterator<zzag.zza> it = map.values().iterator();
        while (it.hasNext()) {
            if (it.next() == zzdf.zzDX()) {
                return zzdf.zzQ(false);
            }
        }
        zzag.zza zzaVar = map.get(zzaWU);
        zzag.zza zzaVar2 = map.get(zzaXS);
        return zzdf.zzQ(Boolean.valueOf((zzaVar == null || zzaVar2 == null) ? false : zza(zzaVar, zzaVar2, map)));
    }

    protected abstract boolean zza(zzag.zza zzaVar, zzag.zza zzaVar2, Map<String, zzag.zza> map);
}
