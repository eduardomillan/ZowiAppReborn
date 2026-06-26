package com.comscore.applications;

import android.util.Log;
import com.comscore.analytics.Core;
import com.comscore.utils.Constants;
import com.comscore.utils.OfflineMeasurementsCache;
import com.comscore.utils.Storage;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class KeepAlive implements Runnable {
    protected final long a;
    protected long c;
    private Core d;
    protected long b = -1;
    private boolean e = false;
    private boolean f = false;

    public KeepAlive(Core core, long j) {
        this.a = j;
        this.c = this.a;
        this.d = core;
    }

    private long a(Storage storage) {
        String str = storage.get(Constants.LAST_MEASUREMENT_PROCESSED_TIMESTAMP_KEY);
        if (str == null || str.length() <= 0) {
            return 0L;
        }
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return 0L;
        }
    }

    protected void a() {
        if (this.d.isEnabled()) {
            this.d.getTaskExecutor().execute(this, this.b - System.currentTimeMillis(), true, this.a);
            this.f = true;
        }
    }

    public void cancel() {
        Log.d("KeepAlive", "cancel()");
        this.d.getTaskExecutor().removeEnqueuedTask(this);
        this.f = false;
    }

    public void processKeepAlive(boolean z) {
        if (this.d.isEnabled() && this.d.isKeepAliveEnabled()) {
            OfflineMeasurementsCache offlineCache = this.d.getOfflineCache();
            long jA = a(this.d.getStorage());
            long jCurrentTimeMillis = System.currentTimeMillis() - jA;
            Log.d("KeepAlive", "processKeepAlive(" + z + ") timeSinceLastTransmission=" + (System.currentTimeMillis() - jCurrentTimeMillis) + " currentTimeout=" + this.c);
            if (jA == 0 || jCurrentTimeMillis <= this.c - 1000) {
                return;
            }
            Log.d("KeepAlive", "Sending Keep-alive");
            if (z) {
                offlineCache.saveApplicationMeasurement(EventType.KEEPALIVE, null, true);
            } else {
                this.d.notify(EventType.KEEPALIVE, new HashMap<>(), true);
            }
            this.d.getStorage().set(Constants.LAST_MEASUREMENT_PROCESSED_TIMESTAMP_KEY, String.valueOf(System.currentTimeMillis()));
        }
    }

    public void reset() {
        reset(this.a);
    }

    public void reset(long j) {
        if (this.d.isEnabled()) {
            cancel();
            Log.d("KeepAlive", "reset:" + j);
            this.b = System.currentTimeMillis() + j;
            this.c = j;
            if (this.e) {
                start(0);
            }
        }
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.d.isEnabled() && this.f) {
            Log.d("KeepAlive", "run()");
            sendKeepAlive();
        }
    }

    public void sendKeepAlive() {
        processKeepAlive(false);
    }

    public void start(int i) {
        if (this.d.isEnabled()) {
            cancel();
            this.e = true;
            Log.d("KeepAlive", "start(" + i + ")");
            if (this.d.isKeepAliveEnabled()) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (this.b < jCurrentTimeMillis) {
                    this.b = jCurrentTimeMillis + ((long) i);
                }
                a();
            }
        }
    }

    public void stop() {
        Log.d("KeepAlive", "stop");
        this.e = false;
        cancel();
        processKeepAlive(true);
    }
}
