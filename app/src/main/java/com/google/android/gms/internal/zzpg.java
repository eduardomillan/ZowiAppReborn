package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class zzpg extends com.google.android.gms.measurement.zze<zzpg> {
    public boolean zzaLx;
    public String zzaqZ;

    public String getDescription() {
        return this.zzaqZ;
    }

    public void setDescription(String description) {
        this.zzaqZ = description;
    }

    public String toString() {
        HashMap map = new HashMap();
        map.put("description", this.zzaqZ);
        map.put("fatal", Boolean.valueOf(this.zzaLx));
        return zzB(map);
    }

    @Override // com.google.android.gms.measurement.zze
    public void zza(zzpg zzpgVar) {
        if (!TextUtils.isEmpty(this.zzaqZ)) {
            zzpgVar.setDescription(this.zzaqZ);
        }
        if (this.zzaLx) {
            zzpgVar.zzak(this.zzaLx);
        }
    }

    public void zzak(boolean z) {
        this.zzaLx = z;
    }

    public boolean zzyK() {
        return this.zzaLx;
    }
}
