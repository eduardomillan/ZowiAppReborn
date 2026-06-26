package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

/* JADX INFO: loaded from: classes.dex */
public class zzdc {
    private Context mContext;
    private Tracker zzLw;
    private GoogleAnalytics zzLy;

    static class zza implements Logger {
        zza() {
        }

        private static int zzjB(int i) {
            switch (i) {
                case 2:
                    return 0;
                case 3:
                case 4:
                    return 1;
                case 5:
                    return 2;
                case 6:
                default:
                    return 3;
            }
        }

        @Override // com.google.android.gms.analytics.Logger
        public void error(Exception exception) {
            zzbg.zzb("", exception);
        }

        @Override // com.google.android.gms.analytics.Logger
        public void error(String message) {
            zzbg.e(message);
        }

        @Override // com.google.android.gms.analytics.Logger
        public int getLogLevel() {
            return zzjB(zzbg.getLogLevel());
        }

        @Override // com.google.android.gms.analytics.Logger
        public void info(String message) {
            zzbg.zzaG(message);
        }

        @Override // com.google.android.gms.analytics.Logger
        public void setLogLevel(int logLevel) {
            zzbg.zzaH("GA uses GTM logger. Please use TagManager.setLogLevel(int) instead.");
        }

        @Override // com.google.android.gms.analytics.Logger
        public void verbose(String message) {
            zzbg.v(message);
        }

        @Override // com.google.android.gms.analytics.Logger
        public void warn(String message) {
            zzbg.zzaH(message);
        }
    }

    public zzdc(Context context) {
        this.mContext = context;
    }

    private synchronized void zzfc(String str) {
        if (this.zzLy == null) {
            this.zzLy = GoogleAnalytics.getInstance(this.mContext);
            this.zzLy.setLogger(new zza());
            this.zzLw = this.zzLy.newTracker(str);
        }
    }

    public Tracker zzfb(String str) {
        zzfc(str);
        return this.zzLw;
    }
}
