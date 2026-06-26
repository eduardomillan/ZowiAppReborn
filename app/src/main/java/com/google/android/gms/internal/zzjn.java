package com.google.android.gms.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class zzjn extends com.google.android.gms.measurement.zze<zzjn> {
    private final Map<String, Object> zzvS = new HashMap();

    private String zzaT(String str) {
        com.google.android.gms.common.internal.zzx.zzcr(str);
        if (str != null && str.startsWith("&")) {
            str = str.substring(1);
        }
        com.google.android.gms.common.internal.zzx.zzh(str, "Name can not be empty or \"&\"");
        return str;
    }

    public void set(String name, String value) {
        this.zzvS.put(zzaT(name), value);
    }

    public String toString() {
        return zzB(this.zzvS);
    }

    @Override // com.google.android.gms.measurement.zze
    public void zza(zzjn zzjnVar) {
        com.google.android.gms.common.internal.zzx.zzw(zzjnVar);
        zzjnVar.zzvS.putAll(this.zzvS);
    }

    public Map<String, Object> zzhZ() {
        return Collections.unmodifiableMap(this.zzvS);
    }
}
