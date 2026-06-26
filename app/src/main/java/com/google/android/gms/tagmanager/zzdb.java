package com.google.android.gms.tagmanager;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.PowerManager;
import android.os.Process;
import android.text.TextUtils;
import com.google.android.gms.internal.zzag;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
class zzdb extends zzak {
    private static final String ID = com.google.android.gms.internal.zzad.TIMER_LISTENER.toString();
    private static final String NAME = com.google.android.gms.internal.zzae.NAME.toString();
    private static final String zzaZu = com.google.android.gms.internal.zzae.INTERVAL.toString();
    private static final String zzaZv = com.google.android.gms.internal.zzae.LIMIT.toString();
    private static final String zzaZw = com.google.android.gms.internal.zzae.UNIQUE_TRIGGER_ID.toString();
    private final Context mContext;
    private Handler mHandler;
    private DataLayer zzaVR;
    private final Set<String> zzaZA;
    private boolean zzaZx;
    private boolean zzaZy;
    private final HandlerThread zzaZz;

    private final class zza implements Runnable {
        private final long zzaEE;
        private final String zzaZB;
        private final String zzaZC;
        private final long zzaZD;
        private long zzaZE;
        private final long zzzB = System.currentTimeMillis();

        zza(String str, String str2, long j, long j2) {
            this.zzaZB = str;
            this.zzaZC = str2;
            this.zzaEE = j;
            this.zzaZD = j2;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.zzaZD > 0 && this.zzaZE >= this.zzaZD) {
                if ("0".equals(this.zzaZC)) {
                    return;
                }
                zzdb.this.zzaZA.remove(this.zzaZC);
            } else {
                this.zzaZE++;
                if (zzcu()) {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    zzdb.this.zzaVR.push(DataLayer.mapOf("event", this.zzaZB, "gtm.timerInterval", String.valueOf(this.zzaEE), "gtm.timerLimit", String.valueOf(this.zzaZD), "gtm.timerStartTime", String.valueOf(this.zzzB), "gtm.timerCurrentTime", String.valueOf(jCurrentTimeMillis), "gtm.timerElapsedTime", String.valueOf(jCurrentTimeMillis - this.zzzB), "gtm.timerEventNumber", String.valueOf(this.zzaZE), "gtm.triggers", this.zzaZC));
                }
                zzdb.this.mHandler.postDelayed(this, this.zzaEE);
            }
        }

        protected boolean zzcu() {
            if (zzdb.this.zzaZy) {
                return zzdb.this.zzaZx;
            }
            ActivityManager activityManager = (ActivityManager) zzdb.this.mContext.getSystemService("activity");
            KeyguardManager keyguardManager = (KeyguardManager) zzdb.this.mContext.getSystemService("keyguard");
            PowerManager powerManager = (PowerManager) zzdb.this.mContext.getSystemService("power");
            for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
                if (Process.myPid() == runningAppProcessInfo.pid && runningAppProcessInfo.importance == 100 && !keyguardManager.inKeyguardRestrictedInputMode() && powerManager.isScreenOn()) {
                    return true;
                }
            }
            return false;
        }
    }

    public zzdb(Context context, DataLayer dataLayer) {
        super(ID, zzaZu, NAME);
        this.zzaZA = new HashSet();
        this.mContext = context;
        this.zzaVR = dataLayer;
        this.zzaZz = new HandlerThread("Google GTM SDK Timer", 10);
        this.zzaZz.start();
        this.mHandler = new Handler(this.zzaZz.getLooper());
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public boolean zzCo() {
        return false;
    }

    @Override // com.google.android.gms.tagmanager.zzak
    public zzag.zza zzI(Map<String, zzag.zza> map) {
        long j;
        long j2;
        String strZzg = zzdf.zzg(map.get(NAME));
        String strZzg2 = zzdf.zzg(map.get(zzaZw));
        String strZzg3 = zzdf.zzg(map.get(zzaZu));
        String strZzg4 = zzdf.zzg(map.get(zzaZv));
        try {
            j = Long.parseLong(strZzg3);
        } catch (NumberFormatException e) {
            j = 0;
        }
        try {
            j2 = Long.parseLong(strZzg4);
        } catch (NumberFormatException e2) {
            j2 = 0;
        }
        if (j > 0 && !TextUtils.isEmpty(strZzg)) {
            if (strZzg2 == null || strZzg2.isEmpty()) {
                strZzg2 = "0";
            }
            if (!this.zzaZA.contains(strZzg2)) {
                if (!"0".equals(strZzg2)) {
                    this.zzaZA.add(strZzg2);
                }
                this.mHandler.postDelayed(new zza(strZzg, strZzg2, j, j2), j);
            }
        }
        return zzdf.zzDX();
    }
}
