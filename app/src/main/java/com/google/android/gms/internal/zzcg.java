package com.google.android.gms.internal;

import android.text.TextUtils;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class zzcg {
    boolean zzvA;
    private String zzvT;
    private zzce zzvU;
    private zzcg zzvV;
    private final List<zzce> zzvR = new LinkedList();
    private final Map<String, String> zzvS = new LinkedHashMap();
    private final Object zzpd = new Object();

    public zzcg(boolean z, String str, String str2) {
        this.zzvA = z;
        this.zzvS.put("action", str);
        this.zzvS.put("ad_format", str2);
    }

    public void zzT(String str) {
        if (this.zzvA) {
            synchronized (this.zzpd) {
                this.zzvT = str;
            }
        }
    }

    public boolean zza(zzce zzceVar, long j, String... strArr) {
        synchronized (this.zzpd) {
            for (String str : strArr) {
                this.zzvR.add(new zzce(j, str, zzceVar));
            }
        }
        return true;
    }

    public boolean zza(zzce zzceVar, String... strArr) {
        if (!this.zzvA || zzceVar == null) {
            return false;
        }
        return zza(zzceVar, com.google.android.gms.ads.internal.zzp.zzbz().elapsedRealtime(), strArr);
    }

    public zzce zzb(long j) {
        if (this.zzvA) {
            return new zzce(j, null, null);
        }
        return null;
    }

    public void zzc(zzcg zzcgVar) {
        synchronized (this.zzpd) {
            this.zzvV = zzcgVar;
        }
    }

    public zzce zzdn() {
        return zzb(com.google.android.gms.ads.internal.zzp.zzbz().elapsedRealtime());
    }

    public void zzdo() {
        synchronized (this.zzpd) {
            this.zzvU = zzdn();
        }
    }

    public String zzdp() {
        String string;
        StringBuilder sb = new StringBuilder();
        synchronized (this.zzpd) {
            for (zzce zzceVar : this.zzvR) {
                long time = zzceVar.getTime();
                String strZzdk = zzceVar.zzdk();
                zzce zzceVarZzdl = zzceVar.zzdl();
                if (zzceVarZzdl != null && time > 0) {
                    sb.append(strZzdk).append('.').append(time - zzceVarZzdl.getTime()).append(',');
                }
            }
            this.zzvR.clear();
            if (!TextUtils.isEmpty(this.zzvT)) {
                sb.append(this.zzvT);
            } else if (sb.length() > 0) {
                sb.setLength(sb.length() - 1);
            }
            string = sb.toString();
        }
        return string;
    }

    public zzce zzdq() {
        zzce zzceVar;
        synchronized (this.zzpd) {
            zzceVar = this.zzvU;
        }
        return zzceVar;
    }

    public void zze(String str, String str2) {
        zzca zzcaVarZzgo;
        if (!this.zzvA || TextUtils.isEmpty(str2) || (zzcaVarZzgo = com.google.android.gms.ads.internal.zzp.zzby().zzgo()) == null) {
            return;
        }
        synchronized (this.zzpd) {
            zzcaVarZzgo.zzR(str).zza(this.zzvS, str, str2);
        }
    }

    Map<String, String> zzn() {
        Map<String, String> mapZza;
        synchronized (this.zzpd) {
            zzca zzcaVarZzgo = com.google.android.gms.ads.internal.zzp.zzby().zzgo();
            mapZza = (zzcaVarZzgo == null || this.zzvV == null) ? this.zzvS : zzcaVarZzgo.zza(this.zzvS, this.zzvV.zzn());
        }
        return mapZza;
    }
}
