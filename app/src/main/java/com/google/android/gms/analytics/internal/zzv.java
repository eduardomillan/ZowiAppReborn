package com.google.android.gms.analytics.internal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import com.google.android.gms.analytics.AnalyticsReceiver;

/* JADX INFO: loaded from: classes.dex */
public class zzv extends zzd {
    private boolean zzOb;
    private boolean zzOc;
    private AlarmManager zzOd;

    protected zzv(zzf zzfVar) {
        super(zzfVar);
        this.zzOd = (AlarmManager) getContext().getSystemService("alarm");
    }

    private PendingIntent zzkm() {
        Intent intent = new Intent(getContext(), (Class<?>) AnalyticsReceiver.class);
        intent.setAction("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
        return PendingIntent.getBroadcast(getContext(), 0, intent, 0);
    }

    public void cancel() {
        zziE();
        this.zzOc = false;
        this.zzOd.cancel(zzkm());
    }

    public boolean zzbp() {
        return this.zzOc;
    }

    @Override // com.google.android.gms.analytics.internal.zzd
    protected void zzhR() {
        ActivityInfo receiverInfo;
        try {
            this.zzOd.cancel(zzkm());
            if (zziv().zzjJ() <= 0 || (receiverInfo = getContext().getPackageManager().getReceiverInfo(new ComponentName(getContext(), (Class<?>) AnalyticsReceiver.class), 2)) == null || !receiverInfo.enabled) {
                return;
            }
            zzba("Receiver registered. Using alarm for local dispatch.");
            this.zzOb = true;
        } catch (PackageManager.NameNotFoundException e) {
        }
    }

    public boolean zzkk() {
        return this.zzOb;
    }

    public void zzkl() {
        zziE();
        com.google.android.gms.common.internal.zzx.zza(zzkk(), "Receiver not registered");
        long jZzjJ = zziv().zzjJ();
        if (jZzjJ > 0) {
            cancel();
            long jElapsedRealtime = zzit().elapsedRealtime() + jZzjJ;
            this.zzOc = true;
            this.zzOd.setInexactRepeating(2, jElapsedRealtime, 0L, zzkm());
        }
    }
}
