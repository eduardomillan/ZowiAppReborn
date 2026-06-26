package com.google.android.gms.internal;

import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzio<T> implements zziq<T> {
    private final zzir zzJA = new zzir();
    private final T zzJy;

    public zzio(T t) {
        this.zzJy = t;
        this.zzJA.zzgV();
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override // java.util.concurrent.Future
    public T get() {
        return this.zzJy;
    }

    @Override // java.util.concurrent.Future
    public T get(long timeout, TimeUnit unit) {
        return this.zzJy;
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        return false;
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return true;
    }

    @Override // com.google.android.gms.internal.zziq
    public void zzc(Runnable runnable) {
        this.zzJA.zzc(runnable);
    }
}
