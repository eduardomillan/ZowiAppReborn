package com.google.android.gms.internal;

import com.google.android.gms.internal.zzdz;
import java.util.Map;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzgv {
    private String zzBY;
    private String zzFO;
    zzdz.zzd zzFQ;
    zziz zzoM;
    private final Object zzpd = new Object();
    private zzin<zzgx> zzFP = new zzin<>();
    public final zzdk zzFR = new zzdk() { // from class: com.google.android.gms.internal.zzgv.1
        @Override // com.google.android.gms.internal.zzdk
        public void zza(zziz zzizVar, Map<String, String> map) {
            synchronized (zzgv.this.zzpd) {
                if (zzgv.this.zzFP.isDone()) {
                    return;
                }
                if (zzgv.this.zzBY.equals(map.get("request_id"))) {
                    zzgx zzgxVar = new zzgx(1, map);
                    com.google.android.gms.ads.internal.util.client.zzb.zzaH("Invalid " + zzgxVar.getType() + " request error: " + zzgxVar.zzfU());
                    zzgv.this.zzFP.zzf(zzgxVar);
                }
            }
        }
    };
    public final zzdk zzFS = new zzdk() { // from class: com.google.android.gms.internal.zzgv.2
        @Override // com.google.android.gms.internal.zzdk
        public void zza(zziz zzizVar, Map<String, String> map) {
            synchronized (zzgv.this.zzpd) {
                if (zzgv.this.zzFP.isDone()) {
                    return;
                }
                zzgx zzgxVar = new zzgx(-2, map);
                if (!zzgv.this.zzBY.equals(zzgxVar.getRequestId())) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzaH(zzgxVar.getRequestId() + " ==== " + zzgv.this.zzBY);
                    return;
                }
                String url = zzgxVar.getUrl();
                if (url == null) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzaH("URL missing in loadAdUrl GMSG.");
                    return;
                }
                if (url.contains("%40mediation_adapters%40")) {
                    String strReplaceAll = url.replaceAll("%40mediation_adapters%40", zzhy.zza(zzizVar.getContext(), map.get("check_adapters"), zzgv.this.zzFO));
                    zzgxVar.setUrl(strReplaceAll);
                    com.google.android.gms.ads.internal.util.client.zzb.v("Ad request URL modified to " + strReplaceAll);
                }
                zzgv.this.zzFP.zzf(zzgxVar);
            }
        }
    };

    public zzgv(String str, String str2) {
        this.zzFO = str2;
        this.zzBY = str;
    }

    public void zzb(zzdz.zzd zzdVar) {
        this.zzFQ = zzdVar;
    }

    public void zze(zziz zzizVar) {
        this.zzoM = zzizVar;
    }

    public zzdz.zzd zzfR() {
        return this.zzFQ;
    }

    public Future<zzgx> zzfS() {
        return this.zzFP;
    }

    public void zzfT() {
        if (this.zzoM != null) {
            this.zzoM.destroy();
            this.zzoM = null;
        }
    }
}
