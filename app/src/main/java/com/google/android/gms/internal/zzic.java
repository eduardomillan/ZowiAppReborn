package com.google.android.gms.internal;

import android.os.Process;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzic {
    private static final ExecutorService zzIr = Executors.newFixedThreadPool(10, zzay("Default"));
    private static final ExecutorService zzIs = Executors.newFixedThreadPool(5, zzay("Loader"));

    public static zziq<Void> zza(int i, final Runnable runnable) {
        return i == 1 ? zza(zzIs, new Callable<Void>() { // from class: com.google.android.gms.internal.zzic.1
            @Override // java.util.concurrent.Callable
            /* JADX INFO: renamed from: zzgA, reason: merged with bridge method [inline-methods] */
            public Void call() {
                runnable.run();
                return null;
            }
        }) : zza(zzIr, new Callable<Void>() { // from class: com.google.android.gms.internal.zzic.2
            @Override // java.util.concurrent.Callable
            /* JADX INFO: renamed from: zzgA, reason: merged with bridge method [inline-methods] */
            public Void call() {
                runnable.run();
                return null;
            }
        });
    }

    public static zziq<Void> zza(Runnable runnable) {
        return zza(0, runnable);
    }

    public static <T> zziq<T> zza(Callable<T> callable) {
        return zza(zzIr, callable);
    }

    public static <T> zziq<T> zza(ExecutorService executorService, final Callable<T> callable) {
        final zzin zzinVar = new zzin();
        try {
            final Future<?> futureSubmit = executorService.submit(new Runnable() { // from class: com.google.android.gms.internal.zzic.3
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        Process.setThreadPriority(10);
                        zzinVar.zzf(callable.call());
                    } catch (Exception e) {
                        com.google.android.gms.ads.internal.zzp.zzby().zzc(e, true);
                        zzinVar.cancel(true);
                    }
                }
            });
            zzinVar.zzd(new Runnable() { // from class: com.google.android.gms.internal.zzic.4
                @Override // java.lang.Runnable
                public void run() {
                    if (zzinVar.isCancelled()) {
                        futureSubmit.cancel(true);
                    }
                }
            });
        } catch (RejectedExecutionException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Thread execution is rejected.", e);
            zzinVar.cancel(true);
        }
        return zzinVar;
    }

    private static ThreadFactory zzay(final String str) {
        return new ThreadFactory() { // from class: com.google.android.gms.internal.zzic.5
            private final AtomicInteger zzIw = new AtomicInteger(1);

            @Override // java.util.concurrent.ThreadFactory
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "AdWorker(" + str + ") #" + this.zzIw.getAndIncrement());
            }
        };
    }
}
