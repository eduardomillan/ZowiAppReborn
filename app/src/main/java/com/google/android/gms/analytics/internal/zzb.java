package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.analytics.AnalyticsReceiver;
import com.google.android.gms.analytics.AnalyticsService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
public class zzb extends zzd {
    private final zzl zzMw;

    public zzb(zzf zzfVar, zzg zzgVar) {
        super(zzfVar);
        com.google.android.gms.common.internal.zzx.zzw(zzgVar);
        this.zzMw = zzgVar.zzj(zzfVar);
    }

    void onServiceConnected() {
        zzis();
        this.zzMw.onServiceConnected();
    }

    public void setLocalDispatchPeriod(final int dispatchPeriodInSeconds) {
        zziE();
        zzb("setLocalDispatchPeriod (sec)", Integer.valueOf(dispatchPeriodInSeconds));
        zziw().zzg(new Runnable() { // from class: com.google.android.gms.analytics.internal.zzb.1
            @Override // java.lang.Runnable
            public void run() {
                zzb.this.zzMw.zzs(((long) dispatchPeriodInSeconds) * 1000);
            }
        });
    }

    public void start() {
        this.zzMw.start();
    }

    public void zzI(final boolean z) {
        zza("Network connectivity status changed", Boolean.valueOf(z));
        zziw().zzg(new Runnable() { // from class: com.google.android.gms.analytics.internal.zzb.2
            @Override // java.lang.Runnable
            public void run() {
                zzb.this.zzMw.zzI(z);
            }
        });
    }

    public long zza(zzh zzhVar) {
        zziE();
        com.google.android.gms.common.internal.zzx.zzw(zzhVar);
        zzis();
        long jZza = this.zzMw.zza(zzhVar, true);
        if (jZza == 0) {
            this.zzMw.zzc(zzhVar);
        }
        return jZza;
    }

    public void zza(final zzab zzabVar) {
        com.google.android.gms.common.internal.zzx.zzw(zzabVar);
        zziE();
        zzb("Hit delivery requested", zzabVar);
        zziw().zzg(new Runnable() { // from class: com.google.android.gms.analytics.internal.zzb.4
            @Override // java.lang.Runnable
            public void run() {
                zzb.this.zzMw.zza(zzabVar);
            }
        });
    }

    public void zza(final zzw zzwVar) {
        zziE();
        zziw().zzg(new Runnable() { // from class: com.google.android.gms.analytics.internal.zzb.6
            @Override // java.lang.Runnable
            public void run() {
                zzb.this.zzMw.zzb(zzwVar);
            }
        });
    }

    public void zza(final String str, final Runnable runnable) {
        com.google.android.gms.common.internal.zzx.zzh(str, "campaign param can't be empty");
        zziw().zzg(new Runnable() { // from class: com.google.android.gms.analytics.internal.zzb.3
            @Override // java.lang.Runnable
            public void run() {
                zzb.this.zzMw.zzbi(str);
                if (runnable != null) {
                    runnable.run();
                }
            }
        });
    }

    @Override // com.google.android.gms.analytics.internal.zzd
    protected void zzhR() {
        this.zzMw.zza();
    }

    public void zzik() {
        zziE();
        zzir();
        zziw().zzg(new Runnable() { // from class: com.google.android.gms.analytics.internal.zzb.5
            @Override // java.lang.Runnable
            public void run() {
                zzb.this.zzMw.zzik();
            }
        });
    }

    public void zzil() {
        zziE();
        Context context = getContext();
        if (!AnalyticsReceiver.zzV(context) || !AnalyticsService.zzW(context)) {
            zza((zzw) null);
            return;
        }
        Intent intent = new Intent(context, (Class<?>) AnalyticsService.class);
        intent.setAction("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
        context.startService(intent);
    }

    public boolean zzim() {
        zziE();
        try {
            zziw().zzb(new Callable<Void>() { // from class: com.google.android.gms.analytics.internal.zzb.7
                @Override // java.util.concurrent.Callable
                /* JADX INFO: renamed from: zzgA, reason: merged with bridge method [inline-methods] */
                public Void call() throws Exception {
                    zzb.this.zzMw.zzjj();
                    return null;
                }
            }).get(4L, TimeUnit.SECONDS);
            return true;
        } catch (InterruptedException e) {
            zzd("syncDispatchLocalHits interrupted", e);
            return false;
        } catch (ExecutionException e2) {
            zze("syncDispatchLocalHits failed", e2);
            return false;
        } catch (TimeoutException e3) {
            zzd("syncDispatchLocalHits timed out", e3);
            return false;
        }
    }

    public void zzin() {
        zziE();
        com.google.android.gms.measurement.zzg.zzis();
        this.zzMw.zzin();
    }

    public void zzio() {
        zzba("Radio powered up");
        zzil();
    }

    void zzip() {
        zzis();
        this.zzMw.zzip();
    }
}
