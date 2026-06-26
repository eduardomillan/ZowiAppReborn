package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.util.Log;
import com.google.ads.AdRequest;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.common.stats.zzb;
import com.google.android.gms.internal.zzav;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class AdvertisingIdClient {
    private static boolean zzoh = false;
    private final Context mContext;
    com.google.android.gms.common.zza zzob;
    zzav zzoc;
    boolean zzod;
    Object zzoe;
    zza zzof;
    final long zzog;

    public static final class Info {
        private final String zzom;
        private final boolean zzon;

        public Info(String advertisingId, boolean limitAdTrackingEnabled) {
            this.zzom = advertisingId;
            this.zzon = limitAdTrackingEnabled;
        }

        public String getId() {
            return this.zzom;
        }

        public boolean isLimitAdTrackingEnabled() {
            return this.zzon;
        }

        public String toString() {
            return "{" + this.zzom + "}" + this.zzon;
        }
    }

    static class zza extends Thread {
        private WeakReference<AdvertisingIdClient> zzoi;
        private long zzoj;
        CountDownLatch zzok = new CountDownLatch(1);
        boolean zzol = false;

        public zza(AdvertisingIdClient advertisingIdClient, long j) {
            this.zzoi = new WeakReference<>(advertisingIdClient);
            this.zzoj = j;
            start();
        }

        private void disconnect() {
            AdvertisingIdClient advertisingIdClient = this.zzoi.get();
            if (advertisingIdClient != null) {
                advertisingIdClient.finish();
                this.zzol = true;
            }
        }

        public void cancel() {
            this.zzok.countDown();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                if (this.zzok.await(this.zzoj, TimeUnit.MILLISECONDS)) {
                    return;
                }
                disconnect();
            } catch (InterruptedException e) {
                disconnect();
            }
        }

        public boolean zzaK() {
            return this.zzol;
        }
    }

    public AdvertisingIdClient(Context context) {
        this(context, 30000L);
    }

    public AdvertisingIdClient(Context context, long timeoutInMillis) {
        this.zzoe = new Object();
        zzx.zzw(context);
        this.mContext = context;
        this.zzod = false;
        this.zzog = timeoutInMillis;
    }

    public static Info getAdvertisingIdInfo(Context context) throws GooglePlayServicesRepairableException, IllegalStateException, GooglePlayServicesNotAvailableException, IOException {
        AdvertisingIdClient advertisingIdClient = new AdvertisingIdClient(context, -1L);
        try {
            advertisingIdClient.zzb(false);
            return advertisingIdClient.getInfo();
        } finally {
            advertisingIdClient.finish();
        }
    }

    public static void setShouldSkipGmsCoreVersionCheck(boolean shouldSkipGmsCoreVersionCheck) {
        zzoh = shouldSkipGmsCoreVersionCheck;
    }

    static zzav zza(Context context, com.google.android.gms.common.zza zzaVar) throws IOException {
        try {
            return zzav.zza.zzb(zzaVar.zzno());
        } catch (InterruptedException e) {
            throw new IOException("Interrupted exception");
        } catch (Throwable th) {
            throw new IOException(th);
        }
    }

    private void zzaJ() {
        synchronized (this.zzoe) {
            if (this.zzof != null) {
                this.zzof.cancel();
                try {
                    this.zzof.join();
                } catch (InterruptedException e) {
                }
            }
            if (this.zzog > 0) {
                this.zzof = new zza(this, this.zzog);
            }
        }
    }

    static com.google.android.gms.common.zza zzo(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException, IOException {
        try {
            context.getPackageManager().getPackageInfo(GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE, 0);
            if (zzoh) {
                Log.d(AdRequest.LOGTAG, "Skipping gmscore version check");
                switch (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)) {
                    case 0:
                    case 2:
                        break;
                    case 1:
                    default:
                        throw new IOException("Google Play services not available");
                }
            } else {
                try {
                    GooglePlayServicesUtil.zzaa(context);
                } catch (GooglePlayServicesNotAvailableException th) {
                    throw new IOException(th);
                }
            }
            com.google.android.gms.common.zza zzaVar = new com.google.android.gms.common.zza();
            Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
            intent.setPackage("com.google.android.gms");
            try {
                if (zzb.zzqh().zza(context, intent, zzaVar, 1)) {
                    return zzaVar;
                }
                throw new IOException("Connection failure");
            } finally {
                IOException iOException = new IOException(th);
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new GooglePlayServicesNotAvailableException(9);
        }
    }

    protected void finalize() throws Throwable {
        finish();
        super.finalize();
    }

    public void finish() {
        zzx.zzcj("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.mContext == null || this.zzob == null) {
                return;
            }
            try {
                if (this.zzod) {
                    zzb.zzqh().zza(this.mContext, this.zzob);
                }
            } catch (IllegalArgumentException e) {
                Log.i("AdvertisingIdClient", "AdvertisingIdClient unbindService failed.", e);
            }
            this.zzod = false;
            this.zzoc = null;
            this.zzob = null;
        }
    }

    public Info getInfo() throws IOException {
        Info info;
        zzx.zzcj("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.zzod) {
                zzx.zzw(this.zzob);
                zzx.zzw(this.zzoc);
                info = new Info(this.zzoc.getId(), this.zzoc.zzc(true));
            } else {
                synchronized (this.zzoe) {
                    if (this.zzof == null || !this.zzof.zzaK()) {
                        throw new IOException("AdvertisingIdClient is not connected.");
                    }
                }
                try {
                    zzb(false);
                    if (!this.zzod) {
                        throw new IOException("AdvertisingIdClient cannot reconnect.");
                    }
                    zzx.zzw(this.zzob);
                    zzx.zzw(this.zzoc);
                    try {
                        info = new Info(this.zzoc.getId(), this.zzoc.zzc(true));
                    } catch (RemoteException e) {
                        Log.i("AdvertisingIdClient", "GMS remote exception ", e);
                        throw new IOException("Remote exception");
                    }
                } catch (Exception e2) {
                    throw new IOException("AdvertisingIdClient cannot reconnect.", e2);
                }
            }
        }
        zzaJ();
        return info;
    }

    public void start() throws GooglePlayServicesRepairableException, IllegalStateException, GooglePlayServicesNotAvailableException, IOException {
        zzb(true);
    }

    protected void zzb(boolean z) throws GooglePlayServicesRepairableException, IllegalStateException, GooglePlayServicesNotAvailableException, IOException {
        zzx.zzcj("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.zzod) {
                finish();
            }
            this.zzob = zzo(this.mContext);
            this.zzoc = zza(this.mContext, this.zzob);
            this.zzod = true;
            if (z) {
                zzaJ();
            }
        }
    }
}
