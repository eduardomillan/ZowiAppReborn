package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzag;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
abstract class zzak {
    private final Set<String> zzaWY;
    private final String zzaWZ;

    public zzak(String str, String... strArr) {
        this.zzaWZ = str;
        this.zzaWY = new HashSet(strArr.length);
        for (String str2 : strArr) {
            this.zzaWY.add(str2);
        }
    }

    public String zzCT() {
        return this.zzaWZ;
    }

    public Set<String> zzCU() {
        return this.zzaWY;
    }

    public abstract boolean zzCo();

    public abstract zzag.zza zzI(Map<String, zzag.zza> map);

    boolean zzf(Set<String> set) {
        return set.containsAll(this.zzaWY);
    }
}
