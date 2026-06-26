package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.internal.zzal;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class zzam extends zzal {
    private static AdvertisingIdClient zznq = null;
    private static CountDownLatch zznr = new CountDownLatch(1);
    private static volatile boolean zzns;
    private boolean zznt;

    class zza {
        private String zznu;
        private boolean zznv;

        public zza(String str, boolean z) {
            this.zznu = str;
            this.zznv = z;
        }

        public String getId() {
            return this.zznu;
        }

        public boolean isLimitAdTrackingEnabled() {
            return this.zznv;
        }
    }

    private static final class zzb implements Runnable {
        private Context zznx;

        public zzb(Context context) {
            this.zznx = context.getApplicationContext();
            if (this.zznx == null) {
                this.zznx = context;
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (zzam.class) {
                try {
                    try {
                        try {
                            if (zzam.zznq == null) {
                                AdvertisingIdClient advertisingIdClient = new AdvertisingIdClient(this.zznx);
                                advertisingIdClient.start();
                                AdvertisingIdClient unused = zzam.zznq = advertisingIdClient;
                            }
                        } catch (GooglePlayServicesRepairableException e) {
                            zzam.zznr.countDown();
                        } catch (IOException e2) {
                            zzam.zznr.countDown();
                        }
                    } catch (GooglePlayServicesNotAvailableException e3) {
                        boolean unused2 = zzam.zzns = true;
                        zzam.zznr.countDown();
                    }
                } finally {
                    zzam.zznr.countDown();
                }
            }
        }
    }

    protected zzam(Context context, zzap zzapVar, zzaq zzaqVar, boolean z) {
        super(context, zzapVar, zzaqVar);
        this.zznt = z;
    }

    public static zzam zza(String str, Context context, boolean z) {
        zzah zzahVar = new zzah();
        zza(str, context, zzahVar);
        if (z) {
            synchronized (zzam.class) {
                if (zznq == null) {
                    new Thread(new zzb(context)).start();
                }
            }
        }
        return new zzam(context, zzahVar, new zzas(239), z);
    }

    zza zzY() throws IOException {
        zza zzaVar;
        try {
            if (!zznr.await(2L, TimeUnit.SECONDS)) {
                return new zza(null, false);
            }
            synchronized (zzam.class) {
                if (zznq == null) {
                    zzaVar = new zza(null, false);
                } else {
                    AdvertisingIdClient.Info info = zznq.getInfo();
                    zzaVar = new zza(zzk(info.getId()), info.isLimitAdTrackingEnabled());
                }
            }
            return zzaVar;
        } catch (InterruptedException e) {
            return new zza(null, false);
        }
    }

    @Override // com.google.android.gms.internal.zzal, com.google.android.gms.internal.zzak
    protected void zzc(Context context) {
        super.zzc(context);
        try {
            if (zzns || !this.zznt) {
                zza(24, zze(context));
            } else {
                zza zzaVarZzY = zzY();
                String id = zzaVarZzY.getId();
                if (id != null) {
                    zza(28, zzaVarZzY.isLimitAdTrackingEnabled() ? 1L : 0L);
                    zza(26, 5L);
                    zza(24, id);
                }
            }
        } catch (zzal.zza e) {
        } catch (IOException e2) {
        }
    }
}
