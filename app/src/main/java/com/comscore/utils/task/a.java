package com.comscore.utils.task;

import com.comscore.analytics.Core;
import com.comscore.utils.CSLog;
import com.comscore.utils.Constants;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
class a implements Runnable {
    private AtomicBoolean a;
    private AtomicBoolean b;
    private Runnable c;
    private Core d;
    private long e;
    private long f;
    private long g;
    private boolean h;
    private boolean i;

    a(Runnable runnable, Core core) {
        this(runnable, core, 0L);
    }

    a(Runnable runnable, Core core, long j) {
        this(runnable, core, j, false, 0L);
    }

    a(Runnable runnable, Core core, long j, boolean z, long j2) {
        this.c = runnable;
        this.d = core;
        this.e = (j > 0 ? j : 0L) + System.currentTimeMillis();
        this.i = j > 0;
        this.f = System.currentTimeMillis();
        this.h = z;
        this.g = j2;
        this.a = new AtomicBoolean();
        this.b = new AtomicBoolean();
        this.b.set(false);
        this.a.set(false);
    }

    long a() {
        long jCurrentTimeMillis = this.e - System.currentTimeMillis();
        if (jCurrentTimeMillis > 0) {
            return jCurrentTimeMillis;
        }
        return 0L;
    }

    long b() {
        return this.f;
    }

    boolean c() {
        return this.a.get();
    }

    boolean d() {
        return this.i;
    }

    boolean e() {
        return this.b.get();
    }

    long f() {
        return this.e;
    }

    boolean g() {
        return this.h;
    }

    long h() {
        return this.g;
    }

    Runnable i() {
        return this.c;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.a.set(true);
        try {
            this.c.run();
        } catch (Exception e) {
            CSLog.e((Class<? extends Object>) getClass(), "Unexpected error running asynchronous task: ");
            CSLog.printStackTrace(e);
            this.d.getStorage().add(Constants.EXCEPTION_OCURRENCES_KEY, 1L);
            this.d.setEnabled(false);
        }
        this.a.set(false);
        this.b.set(true);
    }
}
