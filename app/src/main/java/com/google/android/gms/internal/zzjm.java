package com.google.android.gms.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class zzjm extends com.google.android.gms.measurement.zze<zzjm> {
    private Map<Integer, Double> zzMh = new HashMap(4);

    public String toString() {
        HashMap map = new HashMap();
        for (Map.Entry<Integer, Double> entry : this.zzMh.entrySet()) {
            map.put("metric" + entry.getKey(), entry.getValue());
        }
        return zzB(map);
    }

    @Override // com.google.android.gms.measurement.zze
    public void zza(zzjm zzjmVar) {
        zzjmVar.zzMh.putAll(this.zzMh);
    }

    public Map<Integer, Double> zzhY() {
        return Collections.unmodifiableMap(this.zzMh);
    }
}
