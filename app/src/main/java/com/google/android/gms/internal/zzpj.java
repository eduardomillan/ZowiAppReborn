package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class zzpj extends com.google.android.gms.measurement.zze<zzpj> {
    public String mCategory;
    public String zzaLI;
    public long zzaLJ;
    public String zzaLw;

    public String getLabel() {
        return this.zzaLw;
    }

    public long getTimeInMillis() {
        return this.zzaLJ;
    }

    public void setTimeInMillis(long milliseconds) {
        this.zzaLJ = milliseconds;
    }

    public String toString() {
        HashMap map = new HashMap();
        map.put("variableName", this.zzaLI);
        map.put("timeInMillis", Long.valueOf(this.zzaLJ));
        map.put("category", this.mCategory);
        map.put("label", this.zzaLw);
        return zzB(map);
    }

    @Override // com.google.android.gms.measurement.zze
    public void zza(zzpj zzpjVar) {
        if (!TextUtils.isEmpty(this.zzaLI)) {
            zzpjVar.zzdX(this.zzaLI);
        }
        if (this.zzaLJ != 0) {
            zzpjVar.setTimeInMillis(this.zzaLJ);
        }
        if (!TextUtils.isEmpty(this.mCategory)) {
            zzpjVar.zzdQ(this.mCategory);
        }
        if (TextUtils.isEmpty(this.zzaLw)) {
            return;
        }
        zzpjVar.zzdS(this.zzaLw);
    }

    public void zzdQ(String str) {
        this.mCategory = str;
    }

    public void zzdS(String str) {
        this.zzaLw = str;
    }

    public void zzdX(String str) {
        this.zzaLI = str;
    }

    public String zzyJ() {
        return this.mCategory;
    }

    public String zzyR() {
        return this.zzaLI;
    }
}
