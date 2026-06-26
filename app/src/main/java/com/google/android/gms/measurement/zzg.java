package com.google.android.gms.measurement;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Process;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzpb;
import com.google.android.gms.internal.zzpd;
import java.lang.Thread;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public final class zzg {
    private static volatile zzg zzaLc;
    private final Context mContext;
    private volatile zzpb zzNs;
    private final List<zzh> zzaLd;
    private final com.google.android.gms.measurement.zzb zzaLe;
    private final zza zzaLf;
    private Thread.UncaughtExceptionHandler zzaLg;

    private class zza extends ThreadPoolExecutor {
        public zza() {
            super(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
            setThreadFactory(new zzb());
        }

        @Override // java.util.concurrent.AbstractExecutorService
        protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
            return new FutureTask<T>(runnable, value) { // from class: com.google.android.gms.measurement.zzg.zza.1
                @Override // java.util.concurrent.FutureTask
                protected void setException(Throwable error) {
                    Thread.UncaughtExceptionHandler uncaughtExceptionHandler = zzg.this.zzaLg;
                    if (uncaughtExceptionHandler != null) {
                        uncaughtExceptionHandler.uncaughtException(Thread.currentThread(), error);
                    } else if (Log.isLoggable("GAv4", 6)) {
                        Log.e("GAv4", "MeasurementExecutor: job failed with " + error);
                    }
                    super.setException(error);
                }
            };
        }
    }

    private static class zzb implements ThreadFactory {
        private static final AtomicInteger zzaLk = new AtomicInteger();

        private zzb() {
        }

        @Override // java.util.concurrent.ThreadFactory
        public Thread newThread(Runnable target) {
            return new zzc(target, "measurement-" + zzaLk.incrementAndGet());
        }
    }

    private static class zzc extends Thread {
        zzc(Runnable runnable, String str) {
            super(runnable, str);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Process.setThreadPriority(10);
            super.run();
        }
    }

    zzg(Context context) {
        Context applicationContext = context.getApplicationContext();
        zzx.zzw(applicationContext);
        this.mContext = applicationContext;
        this.zzaLf = new zza();
        this.zzaLd = new CopyOnWriteArrayList();
        this.zzaLe = new com.google.android.gms.measurement.zzb();
    }

    public static zzg zzaJ(Context context) {
        zzx.zzw(context);
        if (zzaLc == null) {
            synchronized (zzg.class) {
                if (zzaLc == null) {
                    zzaLc = new zzg(context);
                }
            }
        }
        return zzaLc;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zzb(com.google.android.gms.measurement.zzc zzcVar) {
        zzx.zzcj("deliver should be called from worker thread");
        zzx.zzb(zzcVar.zzyj(), "Measurement must be submitted");
        List<zzi> listZzyg = zzcVar.zzyg();
        if (listZzyg.isEmpty()) {
            return;
        }
        HashSet hashSet = new HashSet();
        for (zzi zziVar : listZzyg) {
            Uri uriZzhI = zziVar.zzhI();
            if (!hashSet.contains(uriZzhI)) {
                hashSet.add(uriZzhI);
                zziVar.zzb(zzcVar);
            }
        }
    }

    public static void zzis() {
        if (!(Thread.currentThread() instanceof zzc)) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public void zza(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.zzaLg = uncaughtExceptionHandler;
    }

    public <V> Future<V> zzb(Callable<V> callable) {
        zzx.zzw(callable);
        if (!(Thread.currentThread() instanceof zzc)) {
            return this.zzaLf.submit(callable);
        }
        FutureTask futureTask = new FutureTask(callable);
        futureTask.run();
        return futureTask;
    }

    void zze(com.google.android.gms.measurement.zzc zzcVar) {
        if (zzcVar.zzyn()) {
            throw new IllegalStateException("Measurement prototype can't be submitted");
        }
        if (zzcVar.zzyj()) {
            throw new IllegalStateException("Measurement can only be submitted once");
        }
        final com.google.android.gms.measurement.zzc zzcVarZzye = zzcVar.zzye();
        zzcVarZzye.zzyk();
        this.zzaLf.execute(new Runnable() { // from class: com.google.android.gms.measurement.zzg.1
            @Override // java.lang.Runnable
            public void run() {
                zzcVarZzye.zzyl().zza(zzcVarZzye);
                Iterator it = zzg.this.zzaLd.iterator();
                while (it.hasNext()) {
                    ((zzh) it.next()).zza(zzcVarZzye);
                }
                zzg.this.zzb(zzcVarZzye);
            }
        });
    }

    public void zzg(Runnable runnable) {
        zzx.zzw(runnable);
        this.zzaLf.submit(runnable);
    }

    public zzpb zzyr() {
        if (this.zzNs == null) {
            synchronized (this) {
                if (this.zzNs == null) {
                    zzpb zzpbVar = new zzpb();
                    PackageManager packageManager = this.mContext.getPackageManager();
                    String packageName = this.mContext.getPackageName();
                    zzpbVar.setAppId(packageName);
                    zzpbVar.setAppInstallerId(packageManager.getInstallerPackageName(packageName));
                    String str = null;
                    try {
                        PackageInfo packageInfo = packageManager.getPackageInfo(this.mContext.getPackageName(), 0);
                        if (packageInfo != null) {
                            CharSequence applicationLabel = packageManager.getApplicationLabel(packageInfo.applicationInfo);
                            if (!TextUtils.isEmpty(applicationLabel)) {
                                packageName = applicationLabel.toString();
                            }
                            str = packageInfo.versionName;
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        Log.e("GAv4", "Error retrieving package info: appName set to " + packageName);
                    }
                    zzpbVar.setAppName(packageName);
                    zzpbVar.setAppVersion(str);
                    this.zzNs = zzpbVar;
                }
            }
        }
        return this.zzNs;
    }

    public zzpd zzys() {
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        zzpd zzpdVar = new zzpd();
        zzpdVar.setLanguage(zzam.zza(Locale.getDefault()));
        zzpdVar.zzhX(displayMetrics.widthPixels);
        zzpdVar.zzhY(displayMetrics.heightPixels);
        return zzpdVar;
    }
}
