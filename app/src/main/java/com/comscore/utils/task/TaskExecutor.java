package com.comscore.utils.task;

import com.comscore.analytics.Core;
import com.comscore.utils.CSLog;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* JADX INFO: loaded from: classes.dex */
public class TaskExecutor {
    private static final long b = 50000;
    Core a;
    private BlockingQueue<a> d = new LinkedBlockingQueue();
    private TaskThread c = new TaskThread(this);

    public TaskExecutor(Core core) {
        this.a = core;
        this.c.start();
    }

    long a() {
        long jMin = b;
        Iterator it = this.d.iterator();
        while (true) {
            long j = jMin;
            if (!it.hasNext()) {
                return j;
            }
            jMin = Math.min(j, ((a) it.next()).a());
        }
    }

    void a(a aVar) {
        this.d.remove(aVar);
    }

    a b() {
        for (a aVar : this.d) {
            if (aVar.f() <= System.currentTimeMillis()) {
                return aVar;
            }
        }
        return null;
    }

    public boolean containsTask(Runnable runnable) {
        for (a aVar : this.d) {
            if (aVar.i() == runnable || ((runnable instanceof a) && aVar == runnable)) {
                return true;
            }
        }
        return false;
    }

    public boolean execute(Runnable runnable, long j) {
        return execute(runnable, j, false, 0L);
    }

    public boolean execute(Runnable runnable, long j, boolean z, long j2) {
        for (a aVar : this.d) {
            if (aVar != null && aVar.i() == runnable) {
                return false;
            }
        }
        this.d.add(new a(runnable, this.a, j, z, j2));
        this.c.c();
        return true;
    }

    public boolean execute(Runnable runnable, boolean z) {
        if (!this.a.isEnabled()) {
            return false;
        }
        if (z) {
            execute(runnable, 0L);
            return true;
        }
        try {
            runnable.run();
            return true;
        } catch (Exception e) {
            CSLog.e((Class<? extends Object>) getClass(), "Unexpected error: ");
            CSLog.printStackTrace(e);
            return true;
        }
    }

    public void removeAllEnqueuedTasks() {
        this.d.clear();
    }

    public void removeEnqueuedTask(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        for (a aVar : this.d) {
            if (aVar.i() == runnable) {
                this.d.remove(aVar);
                return;
            }
        }
    }

    public int size() {
        return this.d.size();
    }

    public void waitForLastNonDelayedTaskToFinish() {
        a aVar = null;
        a[] aVarArr = new a[this.d.size()];
        this.d.toArray(aVarArr);
        int length = aVarArr.length - 1;
        while (true) {
            if (length >= 0) {
                if (aVarArr[length] != null && !aVarArr[length].d()) {
                    aVar = aVarArr[length];
                    break;
                }
                length--;
            } else {
                break;
            }
        }
        waitForTaskToFinish(aVar, 0L);
    }

    public void waitForTaskToFinish(Runnable runnable, long j) {
        a aVar;
        if (runnable instanceof a) {
            aVar = (a) runnable;
        } else {
            aVar = null;
            for (a aVar2 : this.d) {
                if (aVar2.i() == runnable) {
                    aVar = aVar2;
                }
            }
        }
        if (aVar != null) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            while (this.d.contains(aVar)) {
                if (j > 0 && System.currentTimeMillis() >= jCurrentTimeMillis + j) {
                    return;
                } else {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

    public void waitForTasks() {
        while (this.d.size() != 0) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
            }
        }
    }
}
