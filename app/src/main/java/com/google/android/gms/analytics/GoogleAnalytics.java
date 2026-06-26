package com.google.android.gms.analytics;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.analytics.internal.zzae;
import com.google.android.gms.analytics.internal.zzak;
import com.google.android.gms.analytics.internal.zzal;
import com.google.android.gms.analytics.internal.zzan;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzy;
import com.google.android.gms.common.internal.zzx;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class GoogleAnalytics extends com.google.android.gms.analytics.zza {
    private static List<Runnable> zzLz = new ArrayList();
    private boolean zzLA;
    private Set<zza> zzLB;
    private boolean zzLC;
    private boolean zzLD;
    private volatile boolean zzLE;
    private boolean zzLF;
    private boolean zzpA;

    interface zza {
        void zzn(Activity activity);

        void zzo(Activity activity);
    }

    class zzb implements Application.ActivityLifecycleCallbacks {
        zzb() {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity) {
            GoogleAnalytics.this.zzl(activity);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity) {
            GoogleAnalytics.this.zzm(activity);
        }
    }

    public GoogleAnalytics(zzf context) {
        super(context);
        this.zzLB = new HashSet();
    }

    public static GoogleAnalytics getInstance(Context context) {
        return zzf.zzX(context).zziI();
    }

    public static void zzhN() {
        synchronized (GoogleAnalytics.class) {
            if (zzLz != null) {
                Iterator<Runnable> it = zzLz.iterator();
                while (it.hasNext()) {
                    it.next().run();
                }
                zzLz = null;
            }
        }
    }

    private com.google.android.gms.analytics.internal.zzb zzhP() {
        return zzhF().zzhP();
    }

    private zzan zzhQ() {
        return zzhF().zzhQ();
    }

    public void dispatchLocalHits() {
        zzhP().zzil();
    }

    public void enableAutoActivityReports(Application application) {
        if (Build.VERSION.SDK_INT < 14 || this.zzLC) {
            return;
        }
        application.registerActivityLifecycleCallbacks(new zzb());
        this.zzLC = true;
    }

    public boolean getAppOptOut() {
        return this.zzLE;
    }

    public String getClientId() {
        zzx.zzcj("getClientId can not be called from the main thread");
        return zzhF().zziL().zzjt();
    }

    @Deprecated
    public Logger getLogger() {
        return zzae.getLogger();
    }

    public boolean isDryRunEnabled() {
        return this.zzLD;
    }

    public boolean isInitialized() {
        return this.zzpA && !this.zzLA;
    }

    public Tracker newTracker(int configResId) {
        Tracker tracker;
        zzal zzalVarZzad;
        synchronized (this) {
            tracker = new Tracker(zzhF(), null, null);
            if (configResId > 0 && (zzalVarZzad = new zzak(zzhF()).zzad(configResId)) != null) {
                tracker.zza(zzalVarZzad);
            }
            tracker.zza();
        }
        return tracker;
    }

    public Tracker newTracker(String trackingId) {
        Tracker tracker;
        synchronized (this) {
            tracker = new Tracker(zzhF(), trackingId, null);
            tracker.zza();
        }
        return tracker;
    }

    public void reportActivityStart(Activity activity) {
        if (this.zzLC) {
            return;
        }
        zzl(activity);
    }

    public void reportActivityStop(Activity activity) {
        if (this.zzLC) {
            return;
        }
        zzm(activity);
    }

    public void setAppOptOut(boolean optOut) {
        this.zzLE = optOut;
        if (this.zzLE) {
            zzhP().zzik();
        }
    }

    public void setDryRun(boolean dryRun) {
        this.zzLD = dryRun;
    }

    public void setLocalDispatchPeriod(int dispatchPeriodInSeconds) {
        zzhP().setLocalDispatchPeriod(dispatchPeriodInSeconds);
    }

    @Deprecated
    public void setLogger(Logger logger) {
        zzae.setLogger(logger);
        if (this.zzLF) {
            return;
        }
        Log.i(zzy.zzOg.get(), "GoogleAnalytics.setLogger() is deprecated. To enable debug logging, please run:\nadb shell setprop log.tag." + zzy.zzOg.get() + " DEBUG");
        this.zzLF = true;
    }

    public void zza() {
        zzhM();
        this.zzpA = true;
    }

    void zza(zza zzaVar) {
        this.zzLB.add(zzaVar);
        Context context = zzhF().getContext();
        if (context instanceof Application) {
            enableAutoActivityReports((Application) context);
        }
    }

    void zzb(zza zzaVar) {
        this.zzLB.remove(zzaVar);
    }

    void zzhM() {
        Logger logger;
        zzan zzanVarZzhQ = zzhQ();
        if (zzanVarZzhQ.zzks()) {
            getLogger().setLogLevel(zzanVarZzhQ.getLogLevel());
        }
        if (zzanVarZzhQ.zzkw()) {
            setDryRun(zzanVarZzhQ.zzkx());
        }
        if (!zzanVarZzhQ.zzks() || (logger = zzae.getLogger()) == null) {
            return;
        }
        logger.setLogLevel(zzanVarZzhQ.getLogLevel());
    }

    void zzhO() {
        zzhP().zzim();
    }

    void zzl(Activity activity) {
        Iterator<zza> it = this.zzLB.iterator();
        while (it.hasNext()) {
            it.next().zzn(activity);
        }
    }

    void zzm(Activity activity) {
        Iterator<zza> it = this.zzLB.iterator();
        while (it.hasNext()) {
            it.next().zzo(activity);
        }
    }
}
