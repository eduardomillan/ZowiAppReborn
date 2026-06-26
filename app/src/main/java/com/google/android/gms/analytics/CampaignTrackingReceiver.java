package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.google.android.gms.analytics.internal.zzaf;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzqy;

/* JADX INFO: loaded from: classes.dex */
public class CampaignTrackingReceiver extends BroadcastReceiver {
    static zzqy zzLh;
    static Boolean zzLi;
    static Object zzpy = new Object();

    public static boolean zzV(Context context) {
        zzx.zzw(context);
        if (zzLi != null) {
            return zzLi.booleanValue();
        }
        boolean zZza = zzam.zza(context, (Class<? extends BroadcastReceiver>) CampaignTrackingReceiver.class, true);
        zzLi = Boolean.valueOf(zZza);
        return zZza;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        zzf zzfVarZzX = zzf.zzX(context);
        zzaf zzafVarZziu = zzfVarZzX.zziu();
        String stringExtra = intent.getStringExtra("referrer");
        String action = intent.getAction();
        zzafVarZziu.zza("CampaignTrackingReceiver received", action);
        if (!"com.android.vending.INSTALL_REFERRER".equals(action) || TextUtils.isEmpty(stringExtra)) {
            zzafVarZziu.zzbd("CampaignTrackingReceiver received unexpected intent without referrer extra");
            return;
        }
        boolean zZzW = CampaignTrackingService.zzW(context);
        if (!zZzW) {
            zzafVarZziu.zzbd("CampaignTrackingService not registered or disabled. Installation tracking not possible. See http://goo.gl/8Rd3yj for instructions.");
        }
        zzaS(stringExtra);
        if (zzfVarZzX.zziv().zzjA()) {
            zzafVarZziu.zzbe("Received unexpected installation campaign on package side");
            return;
        }
        Class<? extends CampaignTrackingService> clsZzhJ = zzhJ();
        zzx.zzw(clsZzhJ);
        Intent intent2 = new Intent(context, clsZzhJ);
        intent2.putExtra("referrer", stringExtra);
        synchronized (zzpy) {
            context.startService(intent2);
            if (zZzW) {
                try {
                    if (zzLh == null) {
                        zzLh = new zzqy(context, 1, "Analytics campaign WakeLock");
                        zzLh.setReferenceCounted(false);
                    }
                    zzLh.acquire(1000L);
                } catch (SecurityException e) {
                    zzafVarZziu.zzbd("CampaignTrackingService service at risk of not starting. For more reliable installation campaign reports, add the WAKE_LOCK permission to your manifest. See http://goo.gl/8Rd3yj for instructions.");
                }
            }
        }
    }

    protected void zzaS(String str) {
    }

    protected Class<? extends CampaignTrackingService> zzhJ() {
        return CampaignTrackingService.class;
    }
}
