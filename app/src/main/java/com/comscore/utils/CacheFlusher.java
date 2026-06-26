package com.comscore.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import com.comscore.analytics.Core;

/* JADX INFO: loaded from: classes.dex */
public class CacheFlusher implements Runnable {
    protected Core a;
    protected Handler b;
    protected boolean c;
    protected long d = -1;

    public CacheFlusher(Core core) {
        this.a = core;
    }

    protected void a() {
        if (this.a.getStorage().has("plannedFlushTime").booleanValue()) {
            try {
                this.d = Long.parseLong(this.a.getStorage().get("plannedFlushTime"), 10);
            } catch (Exception e) {
            }
        }
    }

    protected void a(long j) {
        this.d = j;
        this.a.getStorage().set("plannedFlushTime", Long.toString(j, 10));
    }

    protected void b() {
        if (this.d < 0) {
            a(SystemClock.uptimeMillis() + (this.a.getCacheFlushingInterval() * 1000));
        }
        this.b.postAtTime(this, this.d);
    }

    protected synchronized void c() {
        a(this.a.getCacheFlushingInterval() > 0 ? SystemClock.uptimeMillis() + (this.a.getCacheFlushingInterval() * 1000) : -1L);
        if (this.b != null) {
            this.b.removeCallbacks(this);
            b();
        }
    }

    protected void d() {
        HandlerThread handlerThread = new HandlerThread("CacheFlusher");
        handlerThread.start();
        this.b = new Handler(handlerThread.getLooper());
        a();
    }

    protected void e() {
        if (this.b != null) {
            this.b.getLooper().quit();
            this.b = null;
        }
    }

    @Override // java.lang.Runnable
    public synchronized void run() {
        CSLog.d(this, "run(): Flushing the cache");
        this.a.flush(false);
        a(-1L);
        b();
    }

    public synchronized void start() {
        CSLog.d(this, "start()");
        this.c = true;
        if (this.b == null && this.a.getCacheFlushingInterval() > 0 && this.a.getCustomerC2() != null) {
            d();
            b();
        }
    }

    public synchronized void stop() {
        CSLog.d(this, "stop()");
        this.c = false;
        e();
    }

    public synchronized void update() {
        if (this.a.getCacheFlushingInterval() <= 0 || this.a.getCustomerC2() == null) {
            a(-1L);
            e();
        } else if (this.b == null && this.c) {
            a(-1L);
            start();
        } else if (this.b != null) {
            c();
        }
    }
}
