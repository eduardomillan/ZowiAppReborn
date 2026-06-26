package com.google.android.gms.analytics;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import com.google.android.gms.analytics.internal.zzaf;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzw;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzqy;

/* JADX INFO: loaded from: classes.dex */
public final class AnalyticsService extends Service {
    private static Boolean zzLj;
    private final Handler mHandler = new Handler();

    public static boolean zzW(Context context) {
        zzx.zzw(context);
        if (zzLj != null) {
            return zzLj.booleanValue();
        }
        boolean zZza = zzam.zza(context, (Class<? extends Service>) AnalyticsService.class);
        zzLj = Boolean.valueOf(zZza);
        return zZza;
    }

    private void zzhH() {
        try {
            synchronized (AnalyticsReceiver.zzpy) {
                zzqy zzqyVar = AnalyticsReceiver.zzLh;
                if (zzqyVar != null && zzqyVar.isHeld()) {
                    zzqyVar.release();
                }
            }
        } catch (SecurityException e) {
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        zzf zzfVarZzX = zzf.zzX(this);
        zzaf zzafVarZziu = zzfVarZzX.zziu();
        if (zzfVarZzX.zziv().zzjA()) {
            zzafVarZziu.zzba("Device AnalyticsService is starting up");
        } else {
            zzafVarZziu.zzba("Local AnalyticsService is starting up");
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        zzf zzfVarZzX = zzf.zzX(this);
        zzaf zzafVarZziu = zzfVarZzX.zziu();
        if (zzfVarZzX.zziv().zzjA()) {
            zzafVarZziu.zzba("Device AnalyticsService is shutting down");
        } else {
            zzafVarZziu.zzba("Local AnalyticsService is shutting down");
        }
        super.onDestroy();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, final int startId) {
        zzhH();
        final zzf zzfVarZzX = zzf.zzX(this);
        final zzaf zzafVarZziu = zzfVarZzX.zziu();
        String action = intent.getAction();
        if (zzfVarZzX.zziv().zzjA()) {
            zzafVarZziu.zza("Device AnalyticsService called. startId, action", Integer.valueOf(startId), action);
        } else {
            zzafVarZziu.zza("Local AnalyticsService called. startId, action", Integer.valueOf(startId), action);
        }
        if ("com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(action)) {
            zzfVarZzX.zzhP().zza(new zzw() { // from class: com.google.android.gms.analytics.AnalyticsService.1
                @Override // com.google.android.gms.analytics.internal.zzw
                public void zzc(Throwable th) {
                    AnalyticsService.this.mHandler.post(new Runnable() { // from class: com.google.android.gms.analytics.AnalyticsService.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (AnalyticsService.this.stopSelfResult(startId)) {
                                if (zzfVarZzX.zziv().zzjA()) {
                                    zzafVarZziu.zzba("Device AnalyticsService processed last dispatch request");
                                } else {
                                    zzafVarZziu.zzba("Local AnalyticsService processed last dispatch request");
                                }
                            }
                        }
                    });
                }
            });
        }
        return 2;
    }
}
