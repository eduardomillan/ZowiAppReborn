package com.google.android.gms.internal;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/* JADX INFO: loaded from: classes.dex */
public class zzii {
    private HandlerThread zzJh = null;
    private Handler mHandler = null;
    private int zzJi = 0;
    private final Object zzpd = new Object();

    public Looper zzgM() {
        Looper looper;
        synchronized (this.zzpd) {
            if (this.zzJi != 0) {
                com.google.android.gms.common.internal.zzx.zzb(this.zzJh, "Invalid state: mHandlerThread should already been initialized.");
            } else if (this.zzJh == null) {
                com.google.android.gms.ads.internal.util.client.zzb.v("Starting the looper thread.");
                this.zzJh = new HandlerThread("LooperProvider");
                this.zzJh.start();
                this.mHandler = new Handler(this.zzJh.getLooper());
                com.google.android.gms.ads.internal.util.client.zzb.v("Looper thread started.");
            } else {
                com.google.android.gms.ads.internal.util.client.zzb.v("Resuming the looper thread");
                this.zzpd.notifyAll();
            }
            this.zzJi++;
            looper = this.zzJh.getLooper();
        }
        return looper;
    }

    public void zzgN() {
        synchronized (this.zzpd) {
            com.google.android.gms.common.internal.zzx.zzb(this.zzJi > 0, "Invalid state: release() called more times than expected.");
            int i = this.zzJi - 1;
            this.zzJi = i;
            if (i == 0) {
                this.mHandler.post(new Runnable() { // from class: com.google.android.gms.internal.zzii.1
                    @Override // java.lang.Runnable
                    public void run() {
                        synchronized (zzii.this.zzpd) {
                            com.google.android.gms.ads.internal.util.client.zzb.v("Suspending the looper thread");
                            while (zzii.this.zzJi == 0) {
                                try {
                                    zzii.this.zzpd.wait();
                                    com.google.android.gms.ads.internal.util.client.zzb.v("Looper thread resumed");
                                } catch (InterruptedException e) {
                                    com.google.android.gms.ads.internal.util.client.zzb.v("Looper thread interrupted.");
                                }
                            }
                        }
                    }
                });
            }
        }
    }
}
