package com.comscore.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import com.comscore.analytics.Core;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public class ConnectivityChangeReceiver extends BroadcastReceiver {
    protected HashSet<String> d;
    private final Core f;
    private Runnable e = null;
    protected boolean a = false;
    protected boolean b = false;
    protected long c = -1;

    public ConnectivityChangeReceiver(Core core) {
        this.d = null;
        this.f = core;
        this.d = new HashSet<>();
    }

    protected void a() {
        if (this.f.isEnabled()) {
            CSLog.d(this, "onConnectedWifi()");
            a(b(this.f.getAppContext()));
            if (this.f.getOfflineTransmissionMode() == TransmissionMode.NEVER || this.f.getOfflineTransmissionMode() == TransmissionMode.DISABLED || this.a) {
                return;
            }
            this.a = true;
            a(false);
        }
    }

    protected void a(long j) {
        if (this.f.isEnabled()) {
            this.e = new a(this);
            this.f.getTaskExecutor().execute(this.e, j);
        }
    }

    protected void a(Context context) {
        if (this.f.isEnabled()) {
            CSLog.d(this, "onConnectedMobile()");
            a(Constants.CONNECTIVITY_MOBILE_MARKER);
            if ((this.f.getOfflineTransmissionMode() == TransmissionMode.DEFAULT || (this.f.getOfflineTransmissionMode() == TransmissionMode.PIGGYBACK && Connectivity.isDataConnected(context))) && !this.a) {
                this.a = true;
                a(false);
            }
        }
    }

    protected void a(String str) {
        if (this.f.isEnabled() && Utils.isNotEmpty(str) && this.d != null && !this.d.contains(str)) {
            if (this.d.size() != 0) {
                d();
            }
            this.d.add(str);
        }
    }

    protected void a(boolean z) {
        if (this.f.isEnabled()) {
            if (!this.b) {
                if (this.c < 0) {
                    this.c = SystemClock.uptimeMillis() + 30000;
                    return;
                }
                return;
            }
            c();
            if (this.c < SystemClock.uptimeMillis() || this.c < 0 || !z) {
                this.c = SystemClock.uptimeMillis() + 30000;
            }
            a(this.c - SystemClock.uptimeMillis());
            CSLog.d(this, "scheduleFlushTask(): Flushing in " + (this.c - SystemClock.uptimeMillis()));
        }
    }

    protected String b(Context context) {
        return Connectivity.getCurrentSSID(context);
    }

    protected void b() {
        if (this.f.isEnabled()) {
            CSLog.d(this, "onDisconnected()");
            c();
            this.a = false;
            this.c = -1L;
        }
    }

    protected synchronized void b(boolean z) {
        CSLog.d(this, "flushing");
        this.f.flush(z);
        this.c = -1L;
    }

    protected void c() {
        if (this.e != null) {
            CSLog.d(this, "cancelFlushTask()");
            this.f.getTaskExecutor().removeEnqueuedTask(this.e);
            this.e = null;
        }
    }

    protected void d() {
        if (this.f.isEnabled()) {
            this.f.getKeepAlive().reset(3000L);
        }
    }

    @Override // android.content.BroadcastReceiver
    public synchronized void onReceive(Context context, Intent intent) {
        if (intent.getExtras() != null) {
            if (Connectivity.isConnectedWiFi(context)) {
                a();
            } else if (Connectivity.isConnectedMobile(context)) {
                a(context);
            } else {
                b();
            }
        }
    }

    public synchronized void start() {
        if (this.f.isEnabled()) {
            this.b = true;
            if (this.a && this.c > 0) {
                a(true);
            }
        }
    }

    public synchronized void stop() {
        this.b = false;
        c();
    }
}
