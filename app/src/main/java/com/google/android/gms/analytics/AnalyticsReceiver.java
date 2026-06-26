package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.analytics.internal.zzaf;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzqy;

/* JADX INFO: loaded from: classes.dex */
public final class AnalyticsReceiver extends BroadcastReceiver {
    static zzqy zzLh;
    static Boolean zzLi;
    static Object zzpy = new Object();

    public static boolean zzV(Context context) {
        zzx.zzw(context);
        if (zzLi != null) {
            return zzLi.booleanValue();
        }
        boolean zZza = zzam.zza(context, (Class<? extends BroadcastReceiver>) AnalyticsReceiver.class, false);
        zzLi = Boolean.valueOf(zZza);
        return zZza;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        zzf zzfVarZzX = zzf.zzX(context);
        zzaf zzafVarZziu = zzfVarZzX.zziu();
        String action = intent.getAction();
        if (zzfVarZzX.zziv().zzjA()) {
            zzafVarZziu.zza("Device AnalyticsReceiver got", action);
        } else {
            zzafVarZziu.zza("Local AnalyticsReceiver got", action);
        }
        if ("com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(action)) {
            boolean zZzW = AnalyticsService.zzW(context);
            Intent intent2 = new Intent(context, (Class<?>) AnalyticsService.class);
            intent2.setAction("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
            synchronized (zzpy) {
                context.startService(intent2);
                if (zZzW) {
                    try {
                        if (zzLh == null) {
                            zzLh = new zzqy(context, 1, "Analytics WakeLock");
                            zzLh.setReferenceCounted(false);
                        }
                        zzLh.acquire(1000L);
                    } catch (SecurityException e) {
                        zzafVarZziu.zzbd("Analytics service at risk of not starting. For more reliable analytics, add the WAKE_LOCK permission to your manifest. See http://goo.gl/8Rd3yj for instructions.");
                    }
                }
            }
        }
    }
}
