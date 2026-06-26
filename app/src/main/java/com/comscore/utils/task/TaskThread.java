package com.comscore.utils.task;

/* JADX INFO: loaded from: classes.dex */
public class TaskThread extends Thread {
    private boolean a = false;
    private Object b = new Object();
    private TaskExecutor c;

    TaskThread(TaskExecutor taskExecutor) {
        this.c = taskExecutor;
    }

    private void a(long j) {
        synchronized (this.b) {
            try {
                this.b.wait(j);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    boolean a() {
        return this.a;
    }

    void b() {
        this.a = true;
    }

    void c() {
        synchronized (this.b) {
            this.b.notify();
        }
    }

    void d() {
        long jA = this.c.a();
        if (jA > 0) {
            a(jA);
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        while (!a()) {
            a aVarB = this.c.b();
            if (aVarB != null) {
                aVarB.run();
                this.c.a(aVarB);
                if (aVarB.g()) {
                    this.c.execute(aVarB.i(), aVarB.h(), aVarB.g(), aVarB.h());
                }
            } else {
                d();
            }
        }
    }
}
