package com.google.android.gms.analytics.internal;

import android.app.Application;
import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.internal.zzmn;
import com.google.android.gms.internal.zzmp;
import java.lang.Thread;

/* JADX INFO: loaded from: classes.dex */
public class zzf {
    private static zzf zzMI;
    private final Context mContext;
    private final Context zzMJ;
    private final zzr zzMK;
    private final zzaf zzML;
    private final com.google.android.gms.measurement.zzg zzMM;
    private final zzb zzMN;
    private final zzv zzMO;
    private final zzan zzMP;
    private final zzai zzMQ;
    private final GoogleAnalytics zzMR;
    private final zzn zzMS;
    private final zza zzMT;
    private final zzk zzMU;
    private final zzu zzMV;
    private final zzmn zzpW;

    protected zzf(zzg zzgVar) {
        Context applicationContext = zzgVar.getApplicationContext();
        com.google.android.gms.common.internal.zzx.zzb(applicationContext, "Application context can't be null");
        com.google.android.gms.common.internal.zzx.zzb(applicationContext instanceof Application, "getApplicationContext didn't return the application");
        Context contextZziG = zzgVar.zziG();
        com.google.android.gms.common.internal.zzx.zzw(contextZziG);
        this.mContext = applicationContext;
        this.zzMJ = contextZziG;
        this.zzpW = zzgVar.zzh(this);
        this.zzMK = zzgVar.zzg(this);
        zzaf zzafVarZzf = zzgVar.zzf(this);
        zzafVarZzf.zza();
        this.zzML = zzafVarZzf;
        if (zziv().zzjA()) {
            zziu().zzbc("Google Analytics " + zze.VERSION + " is starting up.");
        } else {
            zziu().zzbc("Google Analytics " + zze.VERSION + " is starting up. To enable debug logging on a device run:\n  adb shell setprop log.tag.GAv4 DEBUG\n  adb logcat -s GAv4");
        }
        zzai zzaiVarZzq = zzgVar.zzq(this);
        zzaiVarZzq.zza();
        this.zzMQ = zzaiVarZzq;
        zzan zzanVarZze = zzgVar.zze(this);
        zzanVarZze.zza();
        this.zzMP = zzanVarZze;
        zzb zzbVarZzl = zzgVar.zzl(this);
        zzn zznVarZzd = zzgVar.zzd(this);
        zza zzaVarZzc = zzgVar.zzc(this);
        zzk zzkVarZzb = zzgVar.zzb(this);
        zzu zzuVarZza = zzgVar.zza(this);
        com.google.android.gms.measurement.zzg zzgVarZzY = zzgVar.zzY(applicationContext);
        zzgVarZzY.zza(zziF());
        this.zzMM = zzgVarZzY;
        GoogleAnalytics googleAnalyticsZzi = zzgVar.zzi(this);
        zznVarZzd.zza();
        this.zzMS = zznVarZzd;
        zzaVarZzc.zza();
        this.zzMT = zzaVarZzc;
        zzkVarZzb.zza();
        this.zzMU = zzkVarZzb;
        zzuVarZza.zza();
        this.zzMV = zzuVarZza;
        zzv zzvVarZzp = zzgVar.zzp(this);
        zzvVarZzp.zza();
        this.zzMO = zzvVarZzp;
        zzbVarZzl.zza();
        this.zzMN = zzbVarZzl;
        if (zziv().zzjA()) {
            zziu().zzb("Device AnalyticsService version", zze.VERSION);
        }
        googleAnalyticsZzi.zza();
        this.zzMR = googleAnalyticsZzi;
        zzbVarZzl.start();
    }

    public static zzf zzX(Context context) {
        com.google.android.gms.common.internal.zzx.zzw(context);
        if (zzMI == null) {
            synchronized (zzf.class) {
                if (zzMI == null) {
                    zzmn zzmnVarZzqt = zzmp.zzqt();
                    long jElapsedRealtime = zzmnVarZzqt.elapsedRealtime();
                    zzf zzfVar = new zzf(new zzg(context.getApplicationContext()));
                    zzMI = zzfVar;
                    GoogleAnalytics.zzhN();
                    long jElapsedRealtime2 = zzmnVarZzqt.elapsedRealtime() - jElapsedRealtime;
                    long jLongValue = zzy.zzOU.get().longValue();
                    if (jElapsedRealtime2 > jLongValue) {
                        zzfVar.zziu().zzc("Slow initialization (ms)", Long.valueOf(jElapsedRealtime2), Long.valueOf(jLongValue));
                    }
                }
            }
        }
        return zzMI;
    }

    private void zza(zzd zzdVar) {
        com.google.android.gms.common.internal.zzx.zzb(zzdVar, "Analytics service not created/initialized");
        com.google.android.gms.common.internal.zzx.zzb(zzdVar.isInitialized(), "Analytics service not initialized");
    }

    public Context getContext() {
        return this.mContext;
    }

    public zzb zzhP() {
        zza(this.zzMN);
        return this.zzMN;
    }

    public zzan zzhQ() {
        zza(this.zzMP);
        return this.zzMP;
    }

    public zzk zziB() {
        zza(this.zzMU);
        return this.zzMU;
    }

    public zzu zziC() {
        return this.zzMV;
    }

    protected Thread.UncaughtExceptionHandler zziF() {
        return new Thread.UncaughtExceptionHandler() { // from class: com.google.android.gms.analytics.internal.zzf.1
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public void uncaughtException(Thread thread, Throwable error) {
                zzaf zzafVarZziH = zzf.this.zziH();
                if (zzafVarZziH != null) {
                    zzafVarZziH.zze("Job execution failed", error);
                }
            }
        };
    }

    public Context zziG() {
        return this.zzMJ;
    }

    public zzaf zziH() {
        return this.zzML;
    }

    public GoogleAnalytics zziI() {
        com.google.android.gms.common.internal.zzx.zzw(this.zzMR);
        com.google.android.gms.common.internal.zzx.zzb(this.zzMR.isInitialized(), "Analytics instance not initialized");
        return this.zzMR;
    }

    public zzai zziJ() {
        if (this.zzMQ == null || !this.zzMQ.isInitialized()) {
            return null;
        }
        return this.zzMQ;
    }

    public zza zziK() {
        zza(this.zzMT);
        return this.zzMT;
    }

    public zzn zziL() {
        zza(this.zzMS);
        return this.zzMS;
    }

    public void zzis() {
        com.google.android.gms.measurement.zzg.zzis();
    }

    public zzmn zzit() {
        return this.zzpW;
    }

    public zzaf zziu() {
        zza(this.zzML);
        return this.zzML;
    }

    public zzr zziv() {
        return this.zzMK;
    }

    public com.google.android.gms.measurement.zzg zziw() {
        com.google.android.gms.common.internal.zzx.zzw(this.zzMM);
        return this.zzMM;
    }

    public zzv zzix() {
        zza(this.zzMO);
        return this.zzMO;
    }

    public zzai zziy() {
        zza(this.zzMQ);
        return this.zzMQ;
    }
}
