package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzk;
import com.google.android.gms.internal.zzlb;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
public final class zzli extends GoogleApiClient {
    private final Context mContext;
    private final int zzaaM;
    private final Looper zzaaO;
    private final GoogleApiAvailability zzaaP;
    final Api.zza<? extends zzqw, zzqx> zzaaQ;
    final com.google.android.gms.common.internal.zzf zzabI;
    final com.google.android.gms.common.internal.zzk zzabZ;
    private volatile boolean zzacb;
    private final zza zzace;
    zzd zzacf;
    private com.google.android.gms.common.api.zza zzacn;
    private final Lock zzabt = new ReentrantLock();
    final Queue<zzf<?>> zzaca = new LinkedList();
    private long zzacc = 120000;
    private long zzacd = 5000;
    final Map<Api.zzc<?>, Api.zzb> zzacg = new HashMap();
    final Map<Api.zzc<?>, ConnectionResult> zzach = new HashMap();
    Set<Scope> zzaci = new HashSet();
    private ConnectionResult zzack = null;
    private final Set<zzlm<?>> zzacl = Collections.newSetFromMap(new WeakHashMap());
    final Set<zzf<?>> zzacm = Collections.newSetFromMap(new ConcurrentHashMap(16, 0.75f, 2));
    private final zze zzaco = new zze() { // from class: com.google.android.gms.internal.zzli.1
        @Override // com.google.android.gms.internal.zzli.zze
        public void zzc(zzf<?> zzfVar) {
            zzli.this.zzacm.remove(zzfVar);
            if (zzfVar.zznF() == null || zzli.this.zzacn == null) {
                return;
            }
            zzli.this.zzacn.remove(zzfVar.zznF().intValue());
        }
    };
    private final GoogleApiClient.ConnectionCallbacks zzacp = new GoogleApiClient.ConnectionCallbacks() { // from class: com.google.android.gms.internal.zzli.2
        @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
        public void onConnected(Bundle connectionHint) {
            zzli.this.zzabt.lock();
            try {
                zzli.this.zzacj.onConnected(connectionHint);
            } finally {
                zzli.this.zzabt.unlock();
            }
        }

        @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
        public void onConnectionSuspended(int cause) {
            zzli.this.zzabt.lock();
            try {
                zzli.this.zzacj.onConnectionSuspended(cause);
            } finally {
                zzli.this.zzabt.unlock();
            }
        }
    };
    private final zzk.zza zzacq = new zzk.zza() { // from class: com.google.android.gms.internal.zzli.3
        @Override // com.google.android.gms.common.internal.zzk.zza
        public boolean isConnected() {
            return zzli.this.isConnected();
        }

        @Override // com.google.android.gms.common.internal.zzk.zza
        public Bundle zzmS() {
            return null;
        }
    };
    final Map<Api<?>, Integer> zzabJ = new HashMap();
    private final Condition zzabY = this.zzabt.newCondition();
    private volatile zzlj zzacj = new zzlh(this);

    final class zza extends Handler {
        zza(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    zzli.this.zzod();
                    return;
                case 2:
                    zzli.this.resume();
                    return;
                case 3:
                    ((zzb) msg.obj).zzg(zzli.this);
                    return;
                case 4:
                    throw ((RuntimeException) msg.obj);
                default:
                    Log.w("GoogleApiClientImpl", "Unknown message id: " + msg.what);
                    return;
            }
        }
    }

    static abstract class zzb {
        private final zzlj zzacy;

        protected zzb(zzlj zzljVar) {
            this.zzacy = zzljVar;
        }

        public final void zzg(zzli zzliVar) {
            zzliVar.zzabt.lock();
            try {
                if (zzliVar.zzacj != this.zzacy) {
                    return;
                }
                zznO();
            } finally {
                zzliVar.zzabt.unlock();
            }
        }

        protected abstract void zznO();
    }

    private static class zzc implements IBinder.DeathRecipient, zze {
        private final WeakReference<com.google.android.gms.common.api.zza> zzacA;
        private final WeakReference<IBinder> zzacB;
        private final WeakReference<zzf<?>> zzacz;

        private zzc(zzf zzfVar, com.google.android.gms.common.api.zza zzaVar, IBinder iBinder) {
            this.zzacA = new WeakReference<>(zzaVar);
            this.zzacz = new WeakReference<>(zzfVar);
            this.zzacB = new WeakReference<>(iBinder);
        }

        private void zzoh() {
            zzf<?> zzfVar = this.zzacz.get();
            com.google.android.gms.common.api.zza zzaVar = this.zzacA.get();
            if (zzaVar != null && zzfVar != null) {
                zzaVar.remove(zzfVar.zznF().intValue());
            }
            IBinder iBinder = this.zzacB.get();
            if (this.zzacB != null) {
                iBinder.unlinkToDeath(this, 0);
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            zzoh();
        }

        @Override // com.google.android.gms.internal.zzli.zze
        public void zzc(zzf<?> zzfVar) {
            zzoh();
        }
    }

    static class zzd extends zzll {
        private WeakReference<zzli> zzacC;

        zzd(zzli zzliVar) {
            this.zzacC = new WeakReference<>(zzliVar);
        }

        @Override // com.google.android.gms.internal.zzll
        public void zzoi() {
            zzli zzliVar = this.zzacC.get();
            if (zzliVar == null) {
                return;
            }
            zzliVar.resume();
        }
    }

    interface zze {
        void zzc(zzf<?> zzfVar);
    }

    interface zzf<A extends Api.zzb> {
        void cancel();

        boolean isReady();

        void zza(zze zzeVar);

        void zzb(A a) throws DeadObjectException;

        Integer zznF();

        void zznJ();

        int zznK();

        Api.zzc<A> zznx();

        void zzv(Status status);

        void zzw(Status status);
    }

    public zzli(Context context, Looper looper, com.google.android.gms.common.internal.zzf zzfVar, GoogleApiAvailability googleApiAvailability, Api.zza<? extends zzqw, zzqx> zzaVar, Map<Api<?>, Api.ApiOptions> map, ArrayList<GoogleApiClient.ConnectionCallbacks> arrayList, ArrayList<GoogleApiClient.OnConnectionFailedListener> arrayList2, int i) {
        this.mContext = context;
        this.zzabZ = new com.google.android.gms.common.internal.zzk(looper, this.zzacq);
        this.zzaaO = looper;
        this.zzace = new zza(looper);
        this.zzaaP = googleApiAvailability;
        this.zzaaM = i;
        Iterator<GoogleApiClient.ConnectionCallbacks> it = arrayList.iterator();
        while (it.hasNext()) {
            this.zzabZ.registerConnectionCallbacks(it.next());
        }
        Iterator<GoogleApiClient.OnConnectionFailedListener> it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            this.zzabZ.registerConnectionFailedListener(it2.next());
        }
        Map<Api<?>, zzf.zza> mapZzoM = zzfVar.zzoM();
        for (Api<?> api : map.keySet()) {
            Api.ApiOptions apiOptions = map.get(api);
            int i2 = mapZzoM.get(api) != null ? mapZzoM.get(api).zzafk ? 1 : 2 : 0;
            this.zzabJ.put(api, Integer.valueOf(i2));
            this.zzacg.put(api.zznx(), api.zzny() ? zza(api.zznw(), apiOptions, context, looper, zzfVar, this.zzacp, zza(api, i2)) : zza(api.zznv(), apiOptions, context, looper, zzfVar, this.zzacp, zza(api, i2)));
        }
        this.zzabI = zzfVar;
        this.zzaaQ = zzaVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resume() {
        this.zzabt.lock();
        try {
            if (zzoc()) {
                connect();
            }
        } finally {
            this.zzabt.unlock();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <C extends Api.zzb, O> C zza(Api.zza<C, O> zzaVar, Object obj, Context context, Looper looper, com.google.android.gms.common.internal.zzf zzfVar, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return (C) zzaVar.zza(context, looper, zzfVar, obj, connectionCallbacks, onConnectionFailedListener);
    }

    private GoogleApiClient.OnConnectionFailedListener zza(final Api<?> api, final int i) {
        return new GoogleApiClient.OnConnectionFailedListener() { // from class: com.google.android.gms.internal.zzli.4
            @Override // com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
            public void onConnectionFailed(ConnectionResult result) {
                zzli.this.zzabt.lock();
                try {
                    zzli.this.zzacj.zza(result, api, i);
                } finally {
                    zzli.this.zzabt.unlock();
                }
            }
        };
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static <C extends Api.zzd, O> com.google.android.gms.common.internal.zzac zza(Api.zze<C, O> zzeVar, Object obj, Context context, Looper looper, com.google.android.gms.common.internal.zzf zzfVar, GoogleApiClient.ConnectionCallbacks connectionCallbacks, GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
        return new com.google.android.gms.common.internal.zzac(context, looper, zzeVar.zznA(), connectionCallbacks, onConnectionFailedListener, zzfVar, zzeVar.zzn(obj));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zza(final GoogleApiClient googleApiClient, final zzlo zzloVar, final boolean z) {
        zzlx.zzagw.zzb(googleApiClient).setResultCallback(new ResultCallback<Status>() { // from class: com.google.android.gms.internal.zzli.7
            @Override // com.google.android.gms.common.api.ResultCallback
            /* JADX INFO: renamed from: zzo, reason: merged with bridge method [inline-methods] */
            public void onResult(Status status) {
                if (status.isSuccess() && zzli.this.isConnected()) {
                    zzli.this.reconnect();
                }
                zzloVar.zzb(status);
                if (z) {
                    googleApiClient.disconnect();
                }
            }
        });
    }

    private static void zza(zzf<?> zzfVar, com.google.android.gms.common.api.zza zzaVar, IBinder iBinder) {
        if (zzfVar.isReady()) {
            zzfVar.zza(new zzc(zzfVar, zzaVar, iBinder));
            return;
        }
        if (iBinder == null || !iBinder.isBinderAlive()) {
            zzfVar.zza(null);
            zzfVar.cancel();
            zzaVar.remove(zzfVar.zznF().intValue());
        } else {
            zzc zzcVar = new zzc(zzfVar, zzaVar, iBinder);
            zzfVar.zza(zzcVar);
            try {
                iBinder.linkToDeath(zzcVar, 0);
            } catch (RemoteException e) {
                zzfVar.cancel();
                zzaVar.remove(zzfVar.zznF().intValue());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zzod() {
        this.zzabt.lock();
        try {
            if (zzof()) {
                connect();
            }
        } finally {
            this.zzabt.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public ConnectionResult blockingConnect() {
        ConnectionResult connectionResult;
        com.google.android.gms.common.internal.zzx.zza(Looper.myLooper() != Looper.getMainLooper(), "blockingConnect must not be called on the UI thread");
        this.zzabt.lock();
        try {
            connect();
            while (isConnecting()) {
                this.zzabY.await();
            }
            connectionResult = isConnected() ? ConnectionResult.zzZY : this.zzack != null ? this.zzack : new ConnectionResult(13, null);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            connectionResult = new ConnectionResult(15, null);
        } finally {
            this.zzabt.unlock();
        }
        return connectionResult;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0033, code lost:
    
        r0 = new com.google.android.gms.common.ConnectionResult(14, null);
     */
    @Override // com.google.android.gms.common.api.GoogleApiClient
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.android.gms.common.ConnectionResult blockingConnect(long r6, java.util.concurrent.TimeUnit r8) {
        /*
            r5 = this;
            android.os.Looper r0 = android.os.Looper.myLooper()
            android.os.Looper r1 = android.os.Looper.getMainLooper()
            if (r0 == r1) goto L41
            r0 = 1
        Lb:
            java.lang.String r1 = "blockingConnect must not be called on the UI thread"
            com.google.android.gms.common.internal.zzx.zza(r0, r1)
            java.lang.String r0 = "TimeUnit must not be null"
            com.google.android.gms.common.internal.zzx.zzb(r8, r0)
            java.util.concurrent.locks.Lock r0 = r5.zzabt
            r0.lock()
            r5.connect()     // Catch: java.lang.Throwable -> L81
            long r0 = r8.toNanos(r6)     // Catch: java.lang.Throwable -> L81
        L21:
            boolean r2 = r5.isConnecting()     // Catch: java.lang.Throwable -> L81
            if (r2 == 0) goto L59
            java.util.concurrent.locks.Condition r2 = r5.zzabY     // Catch: java.lang.InterruptedException -> L43 java.lang.Throwable -> L81
            long r0 = r2.awaitNanos(r0)     // Catch: java.lang.InterruptedException -> L43 java.lang.Throwable -> L81
            r2 = 0
            int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r2 > 0) goto L21
            com.google.android.gms.common.ConnectionResult r0 = new com.google.android.gms.common.ConnectionResult     // Catch: java.lang.InterruptedException -> L43 java.lang.Throwable -> L81
            r1 = 14
            r2 = 0
            r0.<init>(r1, r2)     // Catch: java.lang.InterruptedException -> L43 java.lang.Throwable -> L81
            java.util.concurrent.locks.Lock r1 = r5.zzabt
            r1.unlock()
        L40:
            return r0
        L41:
            r0 = 0
            goto Lb
        L43:
            r0 = move-exception
            java.lang.Thread r0 = java.lang.Thread.currentThread()     // Catch: java.lang.Throwable -> L81
            r0.interrupt()     // Catch: java.lang.Throwable -> L81
            com.google.android.gms.common.ConnectionResult r0 = new com.google.android.gms.common.ConnectionResult     // Catch: java.lang.Throwable -> L81
            r1 = 15
            r2 = 0
            r0.<init>(r1, r2)     // Catch: java.lang.Throwable -> L81
            java.util.concurrent.locks.Lock r1 = r5.zzabt
            r1.unlock()
            goto L40
        L59:
            boolean r0 = r5.isConnected()     // Catch: java.lang.Throwable -> L81
            if (r0 == 0) goto L67
            com.google.android.gms.common.ConnectionResult r0 = com.google.android.gms.common.ConnectionResult.zzZY     // Catch: java.lang.Throwable -> L81
            java.util.concurrent.locks.Lock r1 = r5.zzabt
            r1.unlock()
            goto L40
        L67:
            com.google.android.gms.common.ConnectionResult r0 = r5.zzack     // Catch: java.lang.Throwable -> L81
            if (r0 == 0) goto L73
            com.google.android.gms.common.ConnectionResult r0 = r5.zzack     // Catch: java.lang.Throwable -> L81
            java.util.concurrent.locks.Lock r1 = r5.zzabt
            r1.unlock()
            goto L40
        L73:
            com.google.android.gms.common.ConnectionResult r0 = new com.google.android.gms.common.ConnectionResult     // Catch: java.lang.Throwable -> L81
            r1 = 13
            r2 = 0
            r0.<init>(r1, r2)     // Catch: java.lang.Throwable -> L81
            java.util.concurrent.locks.Lock r1 = r5.zzabt
            r1.unlock()
            goto L40
        L81:
            r0 = move-exception
            java.util.concurrent.locks.Lock r1 = r5.zzabt
            r1.unlock()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzli.blockingConnect(long, java.util.concurrent.TimeUnit):com.google.android.gms.common.ConnectionResult");
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public PendingResult<Status> clearDefaultAccountAndReconnect() {
        com.google.android.gms.common.internal.zzx.zza(isConnected(), "GoogleApiClient is not connected yet.");
        final zzlo zzloVar = new zzlo(this);
        if (this.zzacg.containsKey(zzlx.zzRk)) {
            zza((GoogleApiClient) this, zzloVar, false);
        } else {
            final AtomicReference atomicReference = new AtomicReference();
            GoogleApiClient.ConnectionCallbacks connectionCallbacks = new GoogleApiClient.ConnectionCallbacks() { // from class: com.google.android.gms.internal.zzli.5
                @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
                public void onConnected(Bundle connectionHint) {
                    zzli.this.zza((GoogleApiClient) atomicReference.get(), zzloVar, true);
                }

                @Override // com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
                public void onConnectionSuspended(int cause) {
                }
            };
            GoogleApiClient googleApiClientBuild = new GoogleApiClient.Builder(this.mContext).addApi(zzlx.API).addConnectionCallbacks(connectionCallbacks).addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() { // from class: com.google.android.gms.internal.zzli.6
                @Override // com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
                public void onConnectionFailed(ConnectionResult result) {
                    zzloVar.zzb(new Status(8));
                }
            }).setHandler(this.zzace).build();
            atomicReference.set(googleApiClientBuild);
            googleApiClientBuild.connect();
        }
        return zzloVar;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void connect() {
        this.zzabt.lock();
        try {
            this.zzacj.connect();
        } finally {
            this.zzabt.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void disconnect() {
        this.zzabt.lock();
        try {
            zzof();
            this.zzacj.disconnect();
        } finally {
            this.zzabt.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void dump(String prefix, FileDescriptor fd, PrintWriter writer, String[] args) {
        writer.append((CharSequence) prefix).append("mState=").append((CharSequence) this.zzacj.getName());
        writer.append(" mResuming=").print(this.zzacb);
        writer.append(" mWorkQueue.size()=").print(this.zzaca.size());
        writer.append(" mUnconsumedRunners.size()=").println(this.zzacm.size());
        String str = prefix + "  ";
        for (Api<?> api : this.zzabJ.keySet()) {
            writer.append((CharSequence) prefix).append((CharSequence) api.getName()).println(":");
            this.zzacg.get(api.zznx()).dump(str, fd, writer, args);
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public ConnectionResult getConnectionResult(Api<?> api) {
        ConnectionResult connectionResult;
        Api.zzc<?> zzcVarZznx = api.zznx();
        this.zzabt.lock();
        try {
            if (!isConnected() && !zzoc()) {
                throw new IllegalStateException("Cannot invoke getConnectionResult unless GoogleApiClient is connected");
            }
            if (!this.zzacg.containsKey(zzcVarZznx)) {
                this.zzabt.unlock();
                throw new IllegalArgumentException(api.getName() + " was never registered with GoogleApiClient");
            }
            if (this.zzacg.get(zzcVarZznx).isConnected()) {
                connectionResult = ConnectionResult.zzZY;
            } else if (this.zzach.containsKey(zzcVarZznx)) {
                connectionResult = this.zzach.get(zzcVarZznx);
            } else {
                Log.i("GoogleApiClientImpl", zzog());
                Log.wtf("GoogleApiClientImpl", api.getName() + " requested in getConnectionResult is not connected but is not present in the failed connections map", new Exception());
                connectionResult = new ConnectionResult(8, null);
            }
            return connectionResult;
        } finally {
            this.zzabt.unlock();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public Context getContext() {
        return this.mContext;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public Looper getLooper() {
        return this.zzaaO;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public int getSessionId() {
        return System.identityHashCode(this);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public boolean hasConnectedApi(Api<?> api) {
        Api.zzb zzbVar = this.zzacg.get(api.zznx());
        return zzbVar != null && zzbVar.isConnected();
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public boolean isConnected() {
        return this.zzacj instanceof zzlf;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public boolean isConnecting() {
        return this.zzacj instanceof zzlg;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public boolean isConnectionCallbacksRegistered(GoogleApiClient.ConnectionCallbacks listener) {
        return this.zzabZ.isConnectionCallbacksRegistered(listener);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public boolean isConnectionFailedListenerRegistered(GoogleApiClient.OnConnectionFailedListener listener) {
        return this.zzabZ.isConnectionFailedListenerRegistered(listener);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void reconnect() {
        disconnect();
        connect();
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks listener) {
        this.zzabZ.registerConnectionCallbacks(listener);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener listener) {
        this.zzabZ.registerConnectionFailedListener(listener);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void stopAutoManage(final FragmentActivity lifecycleActivity) {
        if (this.zzaaM < 0) {
            throw new IllegalStateException("Called stopAutoManage but automatic lifecycle management is not enabled.");
        }
        zzlp zzlpVarZza = zzlp.zza(lifecycleActivity);
        if (zzlpVarZza == null) {
            new Handler(this.mContext.getMainLooper()).post(new Runnable() { // from class: com.google.android.gms.internal.zzli.8
                @Override // java.lang.Runnable
                public void run() {
                    if (lifecycleActivity.isFinishing() || lifecycleActivity.getSupportFragmentManager().isDestroyed()) {
                        return;
                    }
                    zzlp.zzb(lifecycleActivity).zzbp(zzli.this.zzaaM);
                }
            });
        } else {
            zzlpVarZza.zzbp(this.zzaaM);
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void unregisterConnectionCallbacks(GoogleApiClient.ConnectionCallbacks listener) {
        this.zzabZ.unregisterConnectionCallbacks(listener);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public void unregisterConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener listener) {
        this.zzabZ.unregisterConnectionFailedListener(listener);
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public <C extends Api.zzb> C zza(Api.zzc<C> zzcVar) {
        C c = (C) this.zzacg.get(zzcVar);
        com.google.android.gms.common.internal.zzx.zzb(c, "Appropriate Api was not requested.");
        return c;
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public <A extends Api.zzb, R extends Result, T extends zzlb.zza<R, A>> T zza(T t) {
        com.google.android.gms.common.internal.zzx.zzb(t.zznx() != null, "This task can not be enqueued (it's probably a Batch or malformed)");
        com.google.android.gms.common.internal.zzx.zzb(this.zzacg.containsKey(t.zznx()), "GoogleApiClient is not configured to use the API required for this call.");
        this.zzabt.lock();
        try {
            return (T) this.zzacj.zza(t);
        } finally {
            this.zzabt.unlock();
        }
    }

    void zza(zzb zzbVar) {
        this.zzace.sendMessage(this.zzace.obtainMessage(3, zzbVar));
    }

    void zza(RuntimeException runtimeException) {
        this.zzace.sendMessage(this.zzace.obtainMessage(4, runtimeException));
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public boolean zza(Api<?> api) {
        return this.zzacg.containsKey(api.zznx());
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public <A extends Api.zzb, T extends zzlb.zza<? extends Result, A>> T zzb(T t) {
        com.google.android.gms.common.internal.zzx.zzb(t.zznx() != null, "This task can not be executed (it's probably a Batch or malformed)");
        this.zzabt.lock();
        try {
            if (zzoc()) {
                this.zzaca.add(t);
                while (!this.zzaca.isEmpty()) {
                    zzf<A> zzfVar = (zzf) this.zzaca.remove();
                    zzb(zzfVar);
                    zzfVar.zzv(Status.zzabd);
                }
            } else {
                t = (T) this.zzacj.zzb(t);
            }
            return t;
        } finally {
            this.zzabt.unlock();
        }
    }

    <A extends Api.zzb> void zzb(zzf<A> zzfVar) {
        this.zzacm.add(zzfVar);
        zzfVar.zza(this.zzaco);
    }

    void zzg(ConnectionResult connectionResult) {
        this.zzabt.lock();
        try {
            this.zzack = connectionResult;
            this.zzacj = new zzlh(this);
            this.zzacj.begin();
            this.zzabY.signalAll();
        } finally {
            this.zzabt.unlock();
        }
    }

    void zznY() {
        for (zzf<?> zzfVar : this.zzacm) {
            zzfVar.zza(null);
            if (zzfVar.zznF() == null) {
                zzfVar.cancel();
            } else {
                zzfVar.zznJ();
                zza(zzfVar, this.zzacn, zza((Api.zzc) zzfVar.zznx()).zznz());
            }
        }
        this.zzacm.clear();
        Iterator<zzlm<?>> it = this.zzacl.iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
        this.zzacl.clear();
    }

    void zznZ() {
        Iterator<Api.zzb> it = this.zzacg.values().iterator();
        while (it.hasNext()) {
            it.next().disconnect();
        }
    }

    @Override // com.google.android.gms.common.api.GoogleApiClient
    public <L> zzlm<L> zzo(L l) {
        com.google.android.gms.common.internal.zzx.zzb(l, "Listener must not be null");
        this.zzabt.lock();
        try {
            zzlm<L> zzlmVar = new zzlm<>(this.zzaaO, l);
            this.zzacl.add(zzlmVar);
            return zzlmVar;
        } finally {
            this.zzabt.unlock();
        }
    }

    void zzoa() {
        this.zzabt.lock();
        try {
            this.zzacj = new zzlg(this, this.zzabI, this.zzabJ, this.zzaaP, this.zzaaQ, this.zzabt, this.mContext);
            this.zzacj.begin();
            this.zzabY.signalAll();
        } finally {
            this.zzabt.unlock();
        }
    }

    void zzob() {
        this.zzabt.lock();
        try {
            zzof();
            this.zzacj = new zzlf(this);
            this.zzacj.begin();
            this.zzabY.signalAll();
        } finally {
            this.zzabt.unlock();
        }
    }

    boolean zzoc() {
        return this.zzacb;
    }

    void zzoe() {
        if (zzoc()) {
            return;
        }
        this.zzacb = true;
        if (this.zzacf == null) {
            this.zzacf = (zzd) zzll.zza(this.mContext.getApplicationContext(), new zzd(this), this.zzaaP);
        }
        this.zzace.sendMessageDelayed(this.zzace.obtainMessage(1), this.zzacc);
        this.zzace.sendMessageDelayed(this.zzace.obtainMessage(2), this.zzacd);
    }

    boolean zzof() {
        if (!zzoc()) {
            return false;
        }
        this.zzacb = false;
        this.zzace.removeMessages(2);
        this.zzace.removeMessages(1);
        if (this.zzacf != null) {
            this.zzacf.unregister();
            this.zzacf = null;
        }
        return true;
    }

    String zzog() {
        StringWriter stringWriter = new StringWriter();
        dump("", null, new PrintWriter(stringWriter), null);
        return stringWriter.toString();
    }
}
