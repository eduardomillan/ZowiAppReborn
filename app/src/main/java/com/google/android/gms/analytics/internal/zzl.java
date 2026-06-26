package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.analytics.AnalyticsReceiver;
import com.google.android.gms.analytics.AnalyticsService;
import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.google.android.gms.analytics.CampaignTrackingService;
import com.google.android.gms.internal.zzjn;
import com.google.android.gms.internal.zzjo;
import com.google.android.gms.internal.zzpb;
import com.google.android.gms.internal.zzpc;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
class zzl extends zzd {
    private boolean mStarted;
    private final zzaj zzNA;
    private long zzNB;
    private boolean zzNC;
    private final zzj zzNt;
    private final zzah zzNu;
    private final zzag zzNv;
    private final zzi zzNw;
    private long zzNx;
    private final zzt zzNy;
    private final zzt zzNz;

    protected zzl(zzf zzfVar, zzg zzgVar) {
        super(zzfVar);
        com.google.android.gms.common.internal.zzx.zzw(zzgVar);
        this.zzNx = Long.MIN_VALUE;
        this.zzNv = zzgVar.zzk(zzfVar);
        this.zzNt = zzgVar.zzm(zzfVar);
        this.zzNu = zzgVar.zzn(zzfVar);
        this.zzNw = zzgVar.zzo(zzfVar);
        this.zzNA = new zzaj(zzit());
        this.zzNy = new zzt(zzfVar) { // from class: com.google.android.gms.analytics.internal.zzl.1
            @Override // com.google.android.gms.analytics.internal.zzt
            public void run() {
                zzl.this.zzje();
            }
        };
        this.zzNz = new zzt(zzfVar) { // from class: com.google.android.gms.analytics.internal.zzl.2
            @Override // com.google.android.gms.analytics.internal.zzt
            public void run() {
                zzl.this.zzjf();
            }
        };
    }

    private void zza(zzh zzhVar, zzpc zzpcVar) {
        com.google.android.gms.common.internal.zzx.zzw(zzhVar);
        com.google.android.gms.common.internal.zzx.zzw(zzpcVar);
        com.google.android.gms.analytics.zza zzaVar = new com.google.android.gms.analytics.zza(zziq());
        zzaVar.zzaP(zzhVar.zziN());
        zzaVar.enableAdvertisingIdCollection(zzhVar.zziO());
        com.google.android.gms.measurement.zzc zzcVarZzhG = zzaVar.zzhG();
        zzjo zzjoVar = (zzjo) zzcVarZzhG.zze(zzjo.class);
        zzjoVar.zzaU("data");
        zzjoVar.zzH(true);
        zzcVarZzhG.zzb(zzpcVar);
        zzjn zzjnVar = (zzjn) zzcVarZzhG.zze(zzjn.class);
        zzpb zzpbVar = (zzpb) zzcVarZzhG.zze(zzpb.class);
        for (Map.Entry<String, String> entry : zzhVar.zzn().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if ("an".equals(key)) {
                zzpbVar.setAppName(value);
            } else if ("av".equals(key)) {
                zzpbVar.setAppVersion(value);
            } else if ("aid".equals(key)) {
                zzpbVar.setAppId(value);
            } else if ("aiid".equals(key)) {
                zzpbVar.setAppInstallerId(value);
            } else if ("uid".equals(key)) {
                zzjoVar.setUserId(value);
            } else {
                zzjnVar.set(key, value);
            }
        }
        zzb("Sending installation campaign to", zzhVar.zziN(), zzpcVar);
        zzcVarZzhG.zzL(zziy().zzkO());
        zzcVarZzhG.zzyi();
    }

    private boolean zzbh(String str) {
        return getContext().checkCallingOrSelfPermission(str) == 0;
    }

    private void zzjc() {
        Context context = zziq().getContext();
        if (!AnalyticsReceiver.zzV(context)) {
            zzbd("AnalyticsReceiver is not registered or is disabled. Register the receiver for reliable dispatching on non-Google Play devices. See http://goo.gl/8Rd3yj for instructions.");
        } else if (!AnalyticsService.zzW(context)) {
            zzbe("AnalyticsService is not registered or is disabled. Analytics service at risk of not starting. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!CampaignTrackingReceiver.zzV(context)) {
            zzbd("CampaignTrackingReceiver is not registered, not exported or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.");
        } else {
            if (CampaignTrackingService.zzW(context)) {
                return;
            }
            zzbd("CampaignTrackingService is not registered or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zzje() {
        zzb(new zzw() { // from class: com.google.android.gms.analytics.internal.zzl.4
            @Override // com.google.android.gms.analytics.internal.zzw
            public void zzc(Throwable th) {
                zzl.this.zzjk();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zzjf() {
        try {
            this.zzNt.zziW();
            zzjk();
        } catch (SQLiteException e) {
            zzd("Failed to delete stale hits", e);
        }
        this.zzNz.zzt(zziv().zzkc());
    }

    private boolean zzjl() {
        if (this.zzNC) {
            return false;
        }
        return (!zziv().zzjA() || zziv().zzjB()) && zzjr() > 0;
    }

    private void zzjm() {
        zzv zzvVarZzix = zzix();
        if (zzvVarZzix.zzkk() && !zzvVarZzix.zzbp()) {
            long jZziX = zziX();
            if (jZziX == 0 || Math.abs(zzit().currentTimeMillis() - jZziX) > zziv().zzjK()) {
                return;
            }
            zza("Dispatch alarm scheduled (ms)", Long.valueOf(zziv().zzjJ()));
            zzvVarZzix.zzkl();
        }
    }

    private void zzjn() {
        long jMin;
        zzjm();
        long jZzjr = zzjr();
        long jZzkQ = zziy().zzkQ();
        if (jZzkQ != 0) {
            jMin = jZzjr - Math.abs(zzit().currentTimeMillis() - jZzkQ);
            if (jMin <= 0) {
                jMin = Math.min(zziv().zzjH(), jZzjr);
            }
        } else {
            jMin = Math.min(zziv().zzjH(), jZzjr);
        }
        zza("Dispatch scheduled (ms)", Long.valueOf(jMin));
        if (!this.zzNy.zzbp()) {
            this.zzNy.zzt(jMin);
        } else {
            this.zzNy.zzu(Math.max(1L, jMin + this.zzNy.zzkh()));
        }
    }

    private void zzjo() {
        zzjp();
        zzjq();
    }

    private void zzjp() {
        if (this.zzNy.zzbp()) {
            zzba("All hits dispatched or no network/service. Going to power save mode");
        }
        this.zzNy.cancel();
    }

    private void zzjq() {
        zzv zzvVarZzix = zzix();
        if (zzvVarZzix.zzbp()) {
            zzvVarZzix.cancel();
        }
    }

    protected void onServiceConnected() {
        zzis();
        if (zziv().zzjA()) {
            return;
        }
        zzjh();
    }

    void start() {
        zziE();
        com.google.android.gms.common.internal.zzx.zza(!this.mStarted, "Analytics backend already started");
        this.mStarted = true;
        if (!zziv().zzjA()) {
            zzjc();
        }
        zziw().zzg(new Runnable() { // from class: com.google.android.gms.analytics.internal.zzl.3
            @Override // java.lang.Runnable
            public void run() {
                zzl.this.zzjd();
            }
        });
    }

    public void zzI(boolean z) {
        zzjk();
    }

    public long zza(zzh zzhVar, boolean z) {
        long jZza;
        com.google.android.gms.common.internal.zzx.zzw(zzhVar);
        zziE();
        zzis();
        try {
            try {
                this.zzNt.beginTransaction();
                this.zzNt.zza(zzhVar.zziM(), zzhVar.getClientId());
                jZza = this.zzNt.zza(zzhVar.zziM(), zzhVar.getClientId(), zzhVar.zziN());
                if (z) {
                    zzhVar.zzn(1 + jZza);
                } else {
                    zzhVar.zzn(jZza);
                }
                this.zzNt.zzb(zzhVar);
                this.zzNt.setTransactionSuccessful();
                try {
                    this.zzNt.endTransaction();
                } catch (SQLiteException e) {
                    zze("Failed to end transaction", e);
                }
            } catch (SQLiteException e2) {
                zze("Failed to update Analytics property", e2);
                jZza = -1;
            }
            return jZza;
        } finally {
            try {
                this.zzNt.endTransaction();
            } catch (SQLiteException e3) {
                zze("Failed to end transaction", e3);
            }
        }
    }

    public void zza(zzab zzabVar) {
        com.google.android.gms.common.internal.zzx.zzw(zzabVar);
        com.google.android.gms.measurement.zzg.zzis();
        zziE();
        if (this.zzNC) {
            zzbb("Hit delivery not possible. Missing network permissions. See http://goo.gl/8Rd3yj for instructions");
        } else {
            zza("Delivering hit", zzabVar);
        }
        zzab zzabVarZzf = zzf(zzabVar);
        zzjg();
        if (this.zzNw.zzb(zzabVarZzf)) {
            zzbb("Hit sent to the device AnalyticsService for delivery");
            return;
        }
        if (zziv().zzjA()) {
            zziu().zza(zzabVarZzf, "Service unavailable on package side");
            return;
        }
        try {
            this.zzNt.zzc(zzabVarZzf);
            zzjk();
        } catch (SQLiteException e) {
            zze("Delivery failed to save hit to a database", e);
            zziu().zza(zzabVarZzf, "deliver: failed to insert hit to database");
        }
    }

    public void zza(final zzw zzwVar, final long j) {
        com.google.android.gms.measurement.zzg.zzis();
        zziE();
        long jZzkQ = zziy().zzkQ();
        zzb("Dispatching local hits. Elapsed time since last dispatch (ms)", Long.valueOf(jZzkQ != 0 ? Math.abs(zzit().currentTimeMillis() - jZzkQ) : -1L));
        if (!zziv().zzjA()) {
            zzjg();
        }
        try {
            if (zzji()) {
                zziw().zzg(new Runnable() { // from class: com.google.android.gms.analytics.internal.zzl.5
                    @Override // java.lang.Runnable
                    public void run() {
                        zzl.this.zza(zzwVar, j);
                    }
                });
                return;
            }
            zziy().zzkR();
            zzjk();
            if (zzwVar != null) {
                zzwVar.zzc(null);
            }
            if (this.zzNB != j) {
                this.zzNv.zzkJ();
            }
        } catch (Throwable th) {
            zze("Local dispatch failed", th);
            zziy().zzkR();
            zzjk();
            if (zzwVar != null) {
                zzwVar.zzc(th);
            }
        }
    }

    public void zzb(zzw zzwVar) {
        zza(zzwVar, this.zzNB);
    }

    public void zzbi(String str) {
        com.google.android.gms.common.internal.zzx.zzcr(str);
        zzis();
        zzir();
        zzpc zzpcVarZza = zzam.zza(zziu(), str);
        if (zzpcVarZza == null) {
            zzd("Parsing failed. Ignoring invalid campaign data", str);
            return;
        }
        String strZzkS = zziy().zzkS();
        if (str.equals(strZzkS)) {
            zzbd("Ignoring duplicate install campaign");
            return;
        }
        if (!TextUtils.isEmpty(strZzkS)) {
            zzd("Ignoring multiple install campaigns. original, new", strZzkS, str);
            return;
        }
        zziy().zzbm(str);
        if (zziy().zzkP().zzv(zziv().zzkf())) {
            zzd("Campaign received too late, ignoring", zzpcVarZza);
            return;
        }
        zzb("Received installation campaign", zzpcVarZza);
        Iterator<zzh> it = this.zzNt.zzr(0L).iterator();
        while (it.hasNext()) {
            zza(it.next(), zzpcVarZza);
        }
    }

    protected void zzc(zzh zzhVar) {
        zzis();
        zzb("Sending first hit to property", zzhVar.zziN());
        if (zziy().zzkP().zzv(zziv().zzkf())) {
            return;
        }
        String strZzkS = zziy().zzkS();
        if (TextUtils.isEmpty(strZzkS)) {
            return;
        }
        zzpc zzpcVarZza = zzam.zza(zziu(), strZzkS);
        zzb("Found relevant installation campaign", zzpcVarZza);
        zza(zzhVar, zzpcVarZza);
    }

    zzab zzf(zzab zzabVar) {
        Pair<String, Long> pairZzkW;
        if (!TextUtils.isEmpty(zzabVar.zzkE()) || (pairZzkW = zziy().zzkT().zzkW()) == null) {
            return zzabVar;
        }
        String str = ((Long) pairZzkW.second) + ":" + ((String) pairZzkW.first);
        HashMap map = new HashMap(zzabVar.zzn());
        map.put("_m", str);
        return zzab.zza(this, zzabVar, map);
    }

    @Override // com.google.android.gms.analytics.internal.zzd
    protected void zzhR() {
        this.zzNt.zza();
        this.zzNu.zza();
        this.zzNw.zza();
    }

    public long zziX() {
        com.google.android.gms.measurement.zzg.zzis();
        zziE();
        try {
            return this.zzNt.zziX();
        } catch (SQLiteException e) {
            zze("Failed to get min/max hit times from local store", e);
            return 0L;
        }
    }

    public void zzik() {
        com.google.android.gms.measurement.zzg.zzis();
        zziE();
        if (!zziv().zzjA()) {
            zzba("Delete all hits from local store");
            try {
                this.zzNt.zziU();
                this.zzNt.zziV();
                zzjk();
            } catch (SQLiteException e) {
                zzd("Failed to delete hits from store", e);
            }
        }
        zzjg();
        if (this.zzNw.zziQ()) {
            zzba("Device service unavailable. Can't clear hits stored on the device service.");
        }
    }

    public void zzin() {
        com.google.android.gms.measurement.zzg.zzis();
        zziE();
        zzba("Service disconnected");
    }

    void zzip() {
        zzis();
        this.zzNB = zzit().currentTimeMillis();
    }

    protected void zzjd() {
        zziE();
        zziy().zzkO();
        if (!zzbh("android.permission.ACCESS_NETWORK_STATE")) {
            zzbe("Missing required android.permission.ACCESS_NETWORK_STATE. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            zzjs();
        }
        if (!zzbh("android.permission.INTERNET")) {
            zzbe("Missing required android.permission.INTERNET. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            zzjs();
        }
        if (AnalyticsService.zzW(getContext())) {
            zzba("AnalyticsService registered in the app manifest and enabled");
        } else if (zziv().zzjA()) {
            zzbe("Device AnalyticsService not registered! Hits will not be delivered reliably.");
        } else {
            zzbd("AnalyticsService not registered in the app manifest. Hits might not be delivered reliably. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!this.zzNC && !zziv().zzjA() && !this.zzNt.isEmpty()) {
            zzjg();
        }
        zzjk();
    }

    protected void zzjg() {
        if (this.zzNC || !zziv().zzjC() || this.zzNw.isConnected()) {
            return;
        }
        if (this.zzNA.zzv(zziv().zzjX())) {
            this.zzNA.start();
            zzba("Connecting to service");
            if (this.zzNw.connect()) {
                zzba("Connected to service");
                this.zzNA.clear();
                onServiceConnected();
            }
        }
    }

    public void zzjh() {
        com.google.android.gms.measurement.zzg.zzis();
        zziE();
        zzir();
        if (!zziv().zzjC()) {
            zzbd("Service client disabled. Can't dispatch local hits to device AnalyticsService");
        }
        if (!this.zzNw.isConnected()) {
            zzba("Service not connected");
            return;
        }
        if (this.zzNt.isEmpty()) {
            return;
        }
        zzba("Dispatching local hits to device AnalyticsService");
        while (true) {
            try {
                List<zzab> listZzp = this.zzNt.zzp(zziv().zzjL());
                if (listZzp.isEmpty()) {
                    zzjk();
                    return;
                }
                while (!listZzp.isEmpty()) {
                    zzab zzabVar = listZzp.get(0);
                    if (!this.zzNw.zzb(zzabVar)) {
                        zzjk();
                        return;
                    }
                    listZzp.remove(zzabVar);
                    try {
                        this.zzNt.zzq(zzabVar.zzkz());
                    } catch (SQLiteException e) {
                        zze("Failed to remove hit that was send for delivery", e);
                        zzjo();
                        return;
                    }
                }
            } catch (SQLiteException e2) {
                zze("Failed to read hits from store", e2);
                zzjo();
                return;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0066, code lost:
    
        zzba("Store is empty, nothing to dispatch");
        zzjo();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x006e, code lost:
    
        r12.zzNt.setTransactionSuccessful();
        r12.zzNt.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0079, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x007a, code lost:
    
        zze("Failed to commit local dispatch transaction", r0);
        zzjo();
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00f9, code lost:
    
        if (r12.zzNw.isConnected() == false) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0103, code lost:
    
        if (zziv().zzjA() != false) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0105, code lost:
    
        zzba("Service connected, sending hits to the service");
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x010e, code lost:
    
        if (r8.isEmpty() != false) goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0110, code lost:
    
        r0 = r8.get(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x011d, code lost:
    
        if (r12.zzNw.zzb(r0) != false) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x011f, code lost:
    
        r0 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0126, code lost:
    
        if (r12.zzNu.zzkK() == false) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0128, code lost:
    
        r9 = r12.zzNu.zzm(r8);
        r10 = r9.iterator();
        r4 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0137, code lost:
    
        if (r10.hasNext() == false) goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0139, code lost:
    
        r4 = java.lang.Math.max(r4, r10.next().longValue());
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0148, code lost:
    
        r4 = java.lang.Math.max(r4, r0.zzkz());
        r8.remove(r0);
        zzb("Hit sent do device AnalyticsService for delivery", r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0158, code lost:
    
        r12.zzNt.zzq(r0.zzkz());
        r3.add(java.lang.Long.valueOf(r0.zzkz()));
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x016d, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x016e, code lost:
    
        zze("Failed to remove hit that was send for delivery", r0);
        zzjo();
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0176, code lost:
    
        r12.zzNt.setTransactionSuccessful();
        r12.zzNt.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0182, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0183, code lost:
    
        zze("Failed to commit local dispatch transaction", r0);
        zzjo();
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x018d, code lost:
    
        r8.removeAll(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0190, code lost:
    
        r12.zzNt.zzk(r9);
        r3.addAll(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0198, code lost:
    
        r0 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x019d, code lost:
    
        if (r3.isEmpty() == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x019f, code lost:
    
        r12.zzNt.setTransactionSuccessful();
        r12.zzNt.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x01ab, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x01ac, code lost:
    
        zze("Failed to commit local dispatch transaction", r0);
        zzjo();
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x01b6, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x01b7, code lost:
    
        zze("Failed to remove successfully uploaded hits", r0);
        zzjo();
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01bf, code lost:
    
        r12.zzNt.setTransactionSuccessful();
        r12.zzNt.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x01cb, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x01cc, code lost:
    
        zze("Failed to commit local dispatch transaction", r0);
        zzjo();
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x01d6, code lost:
    
        r12.zzNt.setTransactionSuccessful();
        r12.zzNt.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x01e3, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x01e4, code lost:
    
        zze("Failed to commit local dispatch transaction", r0);
        zzjo();
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0205, code lost:
    
        r0 = r4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected boolean zzji() {
        /*
            Method dump skipped, instruction units count: 520
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzl.zzji():boolean");
    }

    public void zzjj() {
        com.google.android.gms.measurement.zzg.zzis();
        zziE();
        zzbb("Sync dispatching local hits");
        long j = this.zzNB;
        if (!zziv().zzjA()) {
            zzjg();
        }
        do {
            try {
            } catch (Throwable th) {
                zze("Sync local dispatch failed", th);
                zzjk();
                return;
            }
        } while (zzji());
        zziy().zzkR();
        zzjk();
        if (this.zzNB != j) {
            this.zzNv.zzkJ();
        }
    }

    public void zzjk() {
        boolean zIsConnected;
        zziq().zzis();
        zziE();
        if (!zzjl()) {
            this.zzNv.unregister();
            zzjo();
            return;
        }
        if (this.zzNt.isEmpty()) {
            this.zzNv.unregister();
            zzjo();
            return;
        }
        if (zzy.zzON.get().booleanValue()) {
            zIsConnected = true;
        } else {
            this.zzNv.zzkH();
            zIsConnected = this.zzNv.isConnected();
        }
        if (zIsConnected) {
            zzjn();
        } else {
            zzjo();
            zzjm();
        }
    }

    public long zzjr() {
        if (this.zzNx != Long.MIN_VALUE) {
            return this.zzNx;
        }
        return zzhQ().zzku() ? ((long) zzhQ().zzll()) * 1000 : zziv().zzjI();
    }

    public void zzjs() {
        zziE();
        zzis();
        this.zzNC = true;
        this.zzNw.disconnect();
        zzjk();
    }

    public void zzs(long j) {
        com.google.android.gms.measurement.zzg.zzis();
        zziE();
        if (j < 0) {
            j = 0;
        }
        this.zzNx = j;
        zzjk();
    }
}
