package com.google.android.gms.analytics;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import com.google.android.gms.analytics.internal.zzaf;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzqy;

/* JADX INFO: loaded from: classes.dex */
public class CampaignTrackingService extends Service {
    private static Boolean zzLj;
    private Handler mHandler;

    private Handler getHandler() {
        Handler handler = this.mHandler;
        if (handler != null) {
            return handler;
        }
        Handler handler2 = new Handler(getMainLooper());
        this.mHandler = handler2;
        return handler2;
    }

    public static boolean zzW(Context context) {
        zzx.zzw(context);
        if (zzLj != null) {
            return zzLj.booleanValue();
        }
        boolean zZza = zzam.zza(context, (Class<? extends Service>) CampaignTrackingService.class);
        zzLj = Boolean.valueOf(zZza);
        return zZza;
    }

    private void zzhH() {
        try {
            synchronized (CampaignTrackingReceiver.zzpy) {
                zzqy zzqyVar = CampaignTrackingReceiver.zzLh;
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
        zzf.zzX(this).zziu().zzba("CampaignTrackingService is starting up");
    }

    @Override // android.app.Service
    public void onDestroy() {
        zzf.zzX(this).zziu().zzba("CampaignTrackingService is shutting down");
        super.onDestroy();
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, final int startId) {
        zzhH();
        zzf zzfVarZzX = zzf.zzX(this);
        final zzaf zzafVarZziu = zzfVarZzX.zziu();
        String stringExtra = null;
        if (zzfVarZzX.zziv().zzjA()) {
            zzafVarZziu.zzbe("Unexpected installation campaign (package side)");
        } else {
            stringExtra = intent.getStringExtra("referrer");
        }
        final Handler handler = getHandler();
        if (TextUtils.isEmpty(stringExtra)) {
            if (!zzfVarZzX.zziv().zzjA()) {
                zzafVarZziu.zzbd("No campaign found on com.android.vending.INSTALL_REFERRER \"referrer\" extra");
            }
            zzfVarZzX.zziw().zzg(new Runnable() { // from class: com.google.android.gms.analytics.CampaignTrackingService.1
                @Override // java.lang.Runnable
                public void run() {
                    CampaignTrackingService.this.zza(zzafVarZziu, handler, startId);
                }
            });
        } else {
            int iZzjE = zzfVarZzX.zziv().zzjE();
            if (stringExtra.length() > iZzjE) {
                zzafVarZziu.zzc("Campaign data exceed the maximum supported size and will be clipped. size, limit", Integer.valueOf(stringExtra.length()), Integer.valueOf(iZzjE));
                stringExtra = stringExtra.substring(0, iZzjE);
            }
            zzafVarZziu.zza("CampaignTrackingService called. startId, campaign", Integer.valueOf(startId), stringExtra);
            zzfVarZzX.zzhP().zza(stringExtra, new Runnable() { // from class: com.google.android.gms.analytics.CampaignTrackingService.2
                @Override // java.lang.Runnable
                public void run() {
                    CampaignTrackingService.this.zza(zzafVarZziu, handler, startId);
                }
            });
        }
        return 2;
    }

    protected void zza(final zzaf zzafVar, Handler handler, final int i) {
        handler.post(new Runnable() { // from class: com.google.android.gms.analytics.CampaignTrackingService.3
            @Override // java.lang.Runnable
            public void run() {
                boolean zStopSelfResult = CampaignTrackingService.this.stopSelfResult(i);
                if (zStopSelfResult) {
                    zzafVar.zza("Install campaign broadcast processed", Boolean.valueOf(zStopSelfResult));
                }
            }
        });
    }
}
