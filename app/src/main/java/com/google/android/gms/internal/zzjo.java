package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class zzjo extends com.google.android.gms.measurement.zze<zzjo> {
    private String zzGY;
    private String zzMi;
    private String zzMj;
    private String zzMk;
    private boolean zzMl;
    private String zzMm;
    private boolean zzMn;
    private double zzMo;

    public String getClientId() {
        return this.zzMj;
    }

    public String getUserId() {
        return this.zzGY;
    }

    public void setClientId(String clientId) {
        this.zzMj = clientId;
    }

    public void setSampleRate(double percentage) {
        com.google.android.gms.common.internal.zzx.zzb(percentage >= 0.0d && percentage <= 100.0d, "Sample rate must be between 0% and 100%");
        this.zzMo = percentage;
    }

    public void setUserId(String userId) {
        this.zzGY = userId;
    }

    public String toString() {
        HashMap map = new HashMap();
        map.put("hitType", this.zzMi);
        map.put("clientId", this.zzMj);
        map.put("userId", this.zzGY);
        map.put("androidAdId", this.zzMk);
        map.put("AdTargetingEnabled", Boolean.valueOf(this.zzMl));
        map.put("sessionControl", this.zzMm);
        map.put("nonInteraction", Boolean.valueOf(this.zzMn));
        map.put("sampleRate", Double.valueOf(this.zzMo));
        return zzB(map);
    }

    public void zzG(boolean z) {
        this.zzMl = z;
    }

    public void zzH(boolean z) {
        this.zzMn = z;
    }

    @Override // com.google.android.gms.measurement.zze
    public void zza(zzjo zzjoVar) {
        if (!TextUtils.isEmpty(this.zzMi)) {
            zzjoVar.zzaU(this.zzMi);
        }
        if (!TextUtils.isEmpty(this.zzMj)) {
            zzjoVar.setClientId(this.zzMj);
        }
        if (!TextUtils.isEmpty(this.zzGY)) {
            zzjoVar.setUserId(this.zzGY);
        }
        if (!TextUtils.isEmpty(this.zzMk)) {
            zzjoVar.zzaV(this.zzMk);
        }
        if (this.zzMl) {
            zzjoVar.zzG(true);
        }
        if (!TextUtils.isEmpty(this.zzMm)) {
            zzjoVar.zzaW(this.zzMm);
        }
        if (this.zzMn) {
            zzjoVar.zzH(this.zzMn);
        }
        if (this.zzMo != 0.0d) {
            zzjoVar.setSampleRate(this.zzMo);
        }
    }

    public void zzaU(String str) {
        this.zzMi = str;
    }

    public void zzaV(String str) {
        this.zzMk = str;
    }

    public void zzaW(String str) {
        this.zzMm = str;
    }

    public String zzia() {
        return this.zzMi;
    }

    public String zzib() {
        return this.zzMk;
    }

    public boolean zzic() {
        return this.zzMl;
    }

    public String zzid() {
        return this.zzMm;
    }

    public boolean zzie() {
        return this.zzMn;
    }

    public double zzif() {
        return this.zzMo;
    }
}
