package com.google.android.gms.internal;

import android.text.TextUtils;
import com.comscore.utils.Storage;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public final class zzpb extends com.google.android.gms.measurement.zze<zzpb> {
    private String zzOZ;
    private String zzPa;
    private String zzaLl;
    private String zzaLm;

    public void setAppId(String value) {
        this.zzaLl = value;
    }

    public void setAppInstallerId(String value) {
        this.zzaLm = value;
    }

    public void setAppName(String value) {
        this.zzOZ = value;
    }

    public void setAppVersion(String value) {
        this.zzPa = value;
    }

    public String toString() {
        HashMap map = new HashMap();
        map.put(Storage.APP_NAME_KEY, this.zzOZ);
        map.put("appVersion", this.zzPa);
        map.put("appId", this.zzaLl);
        map.put("appInstallerId", this.zzaLm);
        return zzB(map);
    }

    @Override // com.google.android.gms.measurement.zze
    public void zza(zzpb zzpbVar) {
        if (!TextUtils.isEmpty(this.zzOZ)) {
            zzpbVar.setAppName(this.zzOZ);
        }
        if (!TextUtils.isEmpty(this.zzPa)) {
            zzpbVar.setAppVersion(this.zzPa);
        }
        if (!TextUtils.isEmpty(this.zzaLl)) {
            zzpbVar.setAppId(this.zzaLl);
        }
        if (TextUtils.isEmpty(this.zzaLm)) {
            return;
        }
        zzpbVar.setAppInstallerId(this.zzaLm);
    }

    public String zzkp() {
        return this.zzOZ;
    }

    public String zzkr() {
        return this.zzPa;
    }

    public String zzuM() {
        return this.zzaLl;
    }

    public String zzyt() {
        return this.zzaLm;
    }
}
