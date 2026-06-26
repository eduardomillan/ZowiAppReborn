package com.google.android.gms.analytics.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.analytics.internal.zzac;
import java.util.Collections;

/* JADX INFO: loaded from: classes.dex */
public class zzi extends zzd {
    private final zza zzNc;
    private zzac zzNd;
    private final zzt zzNe;
    private zzaj zzNf;

    protected class zza implements ServiceConnection {
        private volatile zzac zzNh;
        private volatile boolean zzNi;

        protected zza() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder binder) {
            com.google.android.gms.common.internal.zzx.zzci("AnalyticsServiceConnection.onServiceConnected");
            synchronized (this) {
                try {
                    if (binder == null) {
                        zzi.this.zzbe("Service connected with null binder");
                        return;
                    }
                    final zzac zzacVarZzaf = null;
                    try {
                        String interfaceDescriptor = binder.getInterfaceDescriptor();
                        if ("com.google.android.gms.analytics.internal.IAnalyticsService".equals(interfaceDescriptor)) {
                            zzacVarZzaf = zzac.zza.zzaf(binder);
                            zzi.this.zzba("Bound to IAnalyticsService interface");
                        } else {
                            zzi.this.zze("Got binder with a wrong descriptor", interfaceDescriptor);
                        }
                    } catch (RemoteException e) {
                        zzi.this.zzbe("Service connect failed to get IAnalyticsService");
                    }
                    if (zzacVarZzaf == null) {
                        try {
                            com.google.android.gms.common.stats.zzb.zzqh().zza(zzi.this.getContext(), zzi.this.zzNc);
                        } catch (IllegalArgumentException e2) {
                        }
                    } else if (this.zzNi) {
                        this.zzNh = zzacVarZzaf;
                    } else {
                        zzi.this.zzbd("onServiceConnected received after the timeout limit");
                        zzi.this.zziw().zzg(new Runnable() { // from class: com.google.android.gms.analytics.internal.zzi.zza.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (zzi.this.isConnected()) {
                                    return;
                                }
                                zzi.this.zzbb("Connected to service after a timeout");
                                zzi.this.zza(zzacVarZzaf);
                            }
                        });
                    }
                } finally {
                    notifyAll();
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(final ComponentName name) {
            com.google.android.gms.common.internal.zzx.zzci("AnalyticsServiceConnection.onServiceDisconnected");
            zzi.this.zziw().zzg(new Runnable() { // from class: com.google.android.gms.analytics.internal.zzi.zza.2
                @Override // java.lang.Runnable
                public void run() {
                    zzi.this.onServiceDisconnected(name);
                }
            });
        }

        public zzac zziT() {
            zzac zzacVar = null;
            zzi.this.zzis();
            Intent intent = new Intent("com.google.android.gms.analytics.service.START");
            intent.setComponent(new ComponentName("com.google.android.gms", "com.google.android.gms.analytics.service.AnalyticsService"));
            Context context = zzi.this.getContext();
            intent.putExtra("app_package_name", context.getPackageName());
            com.google.android.gms.common.stats.zzb zzbVarZzqh = com.google.android.gms.common.stats.zzb.zzqh();
            synchronized (this) {
                this.zzNh = null;
                this.zzNi = true;
                boolean zZza = zzbVarZzqh.zza(context, intent, zzi.this.zzNc, 129);
                zzi.this.zza("Bind to service requested", Boolean.valueOf(zZza));
                if (zZza) {
                    try {
                        wait(zzi.this.zziv().zzjW());
                    } catch (InterruptedException e) {
                        zzi.this.zzbd("Wait for service connect was interrupted");
                    }
                    this.zzNi = false;
                    zzacVar = this.zzNh;
                    this.zzNh = null;
                    if (zzacVar == null) {
                        zzi.this.zzbe("Successfully bound to service but never got onServiceConnected callback");
                    }
                } else {
                    this.zzNi = false;
                }
            }
            return zzacVar;
        }
    }

    protected zzi(zzf zzfVar) {
        super(zzfVar);
        this.zzNf = new zzaj(zzfVar.zzit());
        this.zzNc = new zza();
        this.zzNe = new zzt(zzfVar) { // from class: com.google.android.gms.analytics.internal.zzi.1
            @Override // com.google.android.gms.analytics.internal.zzt
            public void run() {
                zzi.this.zziS();
            }
        };
    }

    private void onDisconnect() {
        zzhP().zzin();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onServiceDisconnected(ComponentName name) {
        zzis();
        if (this.zzNd != null) {
            this.zzNd = null;
            zza("Disconnected from device AnalyticsService", name);
            onDisconnect();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zza(zzac zzacVar) {
        zzis();
        this.zzNd = zzacVar;
        zziR();
        zzhP().onServiceConnected();
    }

    private void zziR() {
        this.zzNf.start();
        this.zzNe.zzt(zziv().zzjV());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zziS() {
        zzis();
        if (isConnected()) {
            zzba("Inactivity, disconnecting from device AnalyticsService");
            disconnect();
        }
    }

    public boolean connect() {
        zzis();
        zziE();
        if (this.zzNd != null) {
            return true;
        }
        zzac zzacVarZziT = this.zzNc.zziT();
        if (zzacVarZziT == null) {
            return false;
        }
        this.zzNd = zzacVarZziT;
        zziR();
        return true;
    }

    public void disconnect() {
        zzis();
        zziE();
        try {
            com.google.android.gms.common.stats.zzb.zzqh().zza(getContext(), this.zzNc);
        } catch (IllegalArgumentException e) {
        } catch (IllegalStateException e2) {
        }
        if (this.zzNd != null) {
            this.zzNd = null;
            onDisconnect();
        }
    }

    public boolean isConnected() {
        zzis();
        zziE();
        return this.zzNd != null;
    }

    public boolean zzb(zzab zzabVar) {
        com.google.android.gms.common.internal.zzx.zzw(zzabVar);
        zzis();
        zziE();
        zzac zzacVar = this.zzNd;
        if (zzacVar == null) {
            return false;
        }
        try {
            zzacVar.zza(zzabVar.zzn(), zzabVar.zzkA(), zzabVar.zzkC() ? zziv().zzjO() : zziv().zzjP(), Collections.emptyList());
            zziR();
            return true;
        } catch (RemoteException e) {
            zzba("Failed to send hits to AnalyticsService");
            return false;
        }
    }

    @Override // com.google.android.gms.analytics.internal.zzd
    protected void zzhR() {
    }

    public boolean zziQ() {
        zzis();
        zziE();
        zzac zzacVar = this.zzNd;
        if (zzacVar == null) {
            return false;
        }
        try {
            zzacVar.zzik();
            zziR();
            return true;
        } catch (RemoteException e) {
            zzba("Failed to clear hits from AnalyticsService");
            return false;
        }
    }
}
