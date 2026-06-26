package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Process;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.internal.zzmn;
import com.google.android.gms.internal.zzmp;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class zza {
    private static Object zzaVD = new Object();
    private static zza zzaVE;
    private volatile boolean mClosed;
    private final Context mContext;
    private final Thread zzIl;
    private volatile AdvertisingIdClient.Info zzMr;
    private volatile long zzaVA;
    private volatile long zzaVB;
    private InterfaceC0100zza zzaVC;
    private volatile long zzaVz;
    private final zzmn zzpW;

    /* JADX INFO: renamed from: com.google.android.gms.tagmanager.zza$zza, reason: collision with other inner class name */
    public interface InterfaceC0100zza {
        AdvertisingIdClient.Info zzCn();
    }

    private zza(Context context) {
        this(context, null, zzmp.zzqt());
    }

    public zza(Context context, InterfaceC0100zza interfaceC0100zza, zzmn zzmnVar) {
        this.zzaVz = 900000L;
        this.zzaVA = 30000L;
        this.mClosed = false;
        this.zzaVC = new InterfaceC0100zza() { // from class: com.google.android.gms.tagmanager.zza.1
            @Override // com.google.android.gms.tagmanager.zza.InterfaceC0100zza
            public AdvertisingIdClient.Info zzCn() {
                try {
                    return AdvertisingIdClient.getAdvertisingIdInfo(zza.this.mContext);
                } catch (GooglePlayServicesNotAvailableException e) {
                    zzbg.zzaH("GooglePlayServicesNotAvailableException getting Advertising Id Info");
                    return null;
                } catch (GooglePlayServicesRepairableException e2) {
                    zzbg.zzaH("GooglePlayServicesRepairableException getting Advertising Id Info");
                    return null;
                } catch (IOException e3) {
                    zzbg.zzaH("IOException getting Ad Id Info");
                    return null;
                } catch (IllegalStateException e4) {
                    zzbg.zzaH("IllegalStateException getting Advertising Id Info");
                    return null;
                } catch (Exception e5) {
                    zzbg.zzaH("Unknown exception. Could not get the Advertising Id Info.");
                    return null;
                }
            }
        };
        this.zzpW = zzmnVar;
        if (context != null) {
            this.mContext = context.getApplicationContext();
        } else {
            this.mContext = context;
        }
        if (interfaceC0100zza != null) {
            this.zzaVC = interfaceC0100zza;
        }
        this.zzIl = new Thread(new Runnable() { // from class: com.google.android.gms.tagmanager.zza.2
            @Override // java.lang.Runnable
            public void run() {
                zza.this.zzCl();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zzCl() {
        Process.setThreadPriority(10);
        while (!this.mClosed) {
            try {
                this.zzMr = this.zzaVC.zzCn();
                Thread.sleep(this.zzaVz);
            } catch (InterruptedException e) {
                zzbg.zzaG("sleep interrupted in AdvertiserDataPoller thread; continuing");
            }
        }
    }

    private void zzCm() {
        if (this.zzpW.currentTimeMillis() - this.zzaVB < this.zzaVA) {
            return;
        }
        interrupt();
        this.zzaVB = this.zzpW.currentTimeMillis();
    }

    public static zza zzaN(Context context) {
        if (zzaVE == null) {
            synchronized (zzaVD) {
                if (zzaVE == null) {
                    zzaVE = new zza(context);
                    zzaVE.start();
                }
            }
        }
        return zzaVE;
    }

    public void interrupt() {
        this.zzIl.interrupt();
    }

    public boolean isLimitAdTrackingEnabled() {
        zzCm();
        if (this.zzMr == null) {
            return true;
        }
        return this.zzMr.isLimitAdTrackingEnabled();
    }

    public void start() {
        this.zzIl.start();
    }

    public String zzCk() {
        zzCm();
        if (this.zzMr == null) {
            return null;
        }
        return this.zzMr.getId();
    }
}
