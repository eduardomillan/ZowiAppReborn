package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes.dex */
public final class zzk implements Handler.Callback {
    private final Handler mHandler;
    private final zza zzafP;
    private final ArrayList<GoogleApiClient.ConnectionCallbacks> zzafQ = new ArrayList<>();
    final ArrayList<GoogleApiClient.ConnectionCallbacks> zzafR = new ArrayList<>();
    private final ArrayList<GoogleApiClient.OnConnectionFailedListener> zzafS = new ArrayList<>();
    private volatile boolean zzafT = false;
    private final AtomicInteger zzafU = new AtomicInteger(0);
    private boolean zzafV = false;
    private final Object zzpd = new Object();

    public interface zza {
        boolean isConnected();

        Bundle zzmS();
    }

    public zzk(Looper looper, zza zzaVar) {
        this.zzafP = zzaVar;
        this.mHandler = new Handler(looper, this);
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message msg) {
        if (msg.what != 1) {
            Log.wtf("GmsClientEvents", "Don't know how to handle message: " + msg.what, new Exception());
            return false;
        }
        GoogleApiClient.ConnectionCallbacks connectionCallbacks = (GoogleApiClient.ConnectionCallbacks) msg.obj;
        synchronized (this.zzpd) {
            if (this.zzafT && this.zzafP.isConnected() && this.zzafQ.contains(connectionCallbacks)) {
                connectionCallbacks.onConnected(this.zzafP.zzmS());
            }
        }
        return true;
    }

    public boolean isConnectionCallbacksRegistered(GoogleApiClient.ConnectionCallbacks listener) {
        boolean zContains;
        zzx.zzw(listener);
        synchronized (this.zzpd) {
            zContains = this.zzafQ.contains(listener);
        }
        return zContains;
    }

    public boolean isConnectionFailedListenerRegistered(GoogleApiClient.OnConnectionFailedListener listener) {
        boolean zContains;
        zzx.zzw(listener);
        synchronized (this.zzpd) {
            zContains = this.zzafS.contains(listener);
        }
        return zContains;
    }

    public void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks listener) {
        zzx.zzw(listener);
        synchronized (this.zzpd) {
            if (this.zzafQ.contains(listener)) {
                Log.w("GmsClientEvents", "registerConnectionCallbacks(): listener " + listener + " is already registered");
            } else {
                this.zzafQ.add(listener);
            }
        }
        if (this.zzafP.isConnected()) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, listener));
        }
    }

    public void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener listener) {
        zzx.zzw(listener);
        synchronized (this.zzpd) {
            if (this.zzafS.contains(listener)) {
                Log.w("GmsClientEvents", "registerConnectionFailedListener(): listener " + listener + " is already registered");
            } else {
                this.zzafS.add(listener);
            }
        }
    }

    public void unregisterConnectionCallbacks(GoogleApiClient.ConnectionCallbacks listener) {
        zzx.zzw(listener);
        synchronized (this.zzpd) {
            if (!this.zzafQ.remove(listener)) {
                Log.w("GmsClientEvents", "unregisterConnectionCallbacks(): listener " + listener + " not found");
            } else if (this.zzafV) {
                this.zzafR.add(listener);
            }
        }
    }

    public void unregisterConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener listener) {
        zzx.zzw(listener);
        synchronized (this.zzpd) {
            if (!this.zzafS.remove(listener)) {
                Log.w("GmsClientEvents", "unregisterConnectionFailedListener(): listener " + listener + " not found");
            }
        }
    }

    public void zzbG(int i) {
        zzx.zza(Looper.myLooper() == this.mHandler.getLooper(), "onUnintentionalDisconnection must only be called on the Handler thread");
        this.mHandler.removeMessages(1);
        synchronized (this.zzpd) {
            this.zzafV = true;
            ArrayList<GoogleApiClient.ConnectionCallbacks> arrayList = new ArrayList(this.zzafQ);
            int i2 = this.zzafU.get();
            for (GoogleApiClient.ConnectionCallbacks connectionCallbacks : arrayList) {
                if (!this.zzafT || this.zzafU.get() != i2) {
                    break;
                } else if (this.zzafQ.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnectionSuspended(i);
                }
            }
            this.zzafR.clear();
            this.zzafV = false;
        }
    }

    public void zzh(Bundle bundle) {
        zzx.zza(Looper.myLooper() == this.mHandler.getLooper(), "onConnectionSuccess must only be called on the Handler thread");
        synchronized (this.zzpd) {
            zzx.zzZ(!this.zzafV);
            this.mHandler.removeMessages(1);
            this.zzafV = true;
            zzx.zzZ(this.zzafR.size() == 0);
            ArrayList<GoogleApiClient.ConnectionCallbacks> arrayList = new ArrayList(this.zzafQ);
            int i = this.zzafU.get();
            for (GoogleApiClient.ConnectionCallbacks connectionCallbacks : arrayList) {
                if (!this.zzafT || !this.zzafP.isConnected() || this.zzafU.get() != i) {
                    break;
                } else if (!this.zzafR.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnected(bundle);
                }
            }
            this.zzafR.clear();
            this.zzafV = false;
        }
    }

    public void zzi(ConnectionResult connectionResult) {
        zzx.zza(Looper.myLooper() == this.mHandler.getLooper(), "onConnectionFailure must only be called on the Handler thread");
        this.mHandler.removeMessages(1);
        synchronized (this.zzpd) {
            ArrayList<GoogleApiClient.OnConnectionFailedListener> arrayList = new ArrayList(this.zzafS);
            int i = this.zzafU.get();
            for (GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener : arrayList) {
                if (!this.zzafT || this.zzafU.get() != i) {
                    return;
                }
                if (this.zzafS.contains(onConnectionFailedListener)) {
                    onConnectionFailedListener.onConnectionFailed(connectionResult);
                }
            }
        }
    }

    public void zzpk() {
        this.zzafT = false;
        this.zzafU.incrementAndGet();
    }

    public void zzpl() {
        this.zzafT = true;
    }
}
