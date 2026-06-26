package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import com.comscore.streaming.Constants;
import com.google.ads.mediation.AdUrlAdapter;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.internal.zzei;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzeh implements zzei.zza {
    private final Context mContext;
    private final NativeAdOptionsParcel zzoY;
    private final List<String> zzoZ;
    private final zzem zzox;
    private final AdRequestParcel zzpH;
    private final VersionInfoParcel zzpb;
    private final String zzzj;
    private final long zzzk;
    private final zzed zzzl;
    private final AdSizeParcel zzzm;
    private final boolean zzzn;
    private zzen zzzo;
    private zzep zzzq;
    private final Object zzpd = new Object();
    private int zzzp = -2;

    public zzeh(Context context, String str, zzem zzemVar, zzee zzeeVar, zzed zzedVar, AdRequestParcel adRequestParcel, AdSizeParcel adSizeParcel, VersionInfoParcel versionInfoParcel, boolean z, NativeAdOptionsParcel nativeAdOptionsParcel, List<String> list) {
        this.mContext = context;
        this.zzox = zzemVar;
        this.zzzl = zzedVar;
        if ("com.google.ads.mediation.customevent.CustomEventAdapter".equals(str)) {
            this.zzzj = zzdT();
        } else {
            this.zzzj = str;
        }
        this.zzzk = zzeeVar.zzyX != -1 ? zzeeVar.zzyX : Constants.HEARTBEAT_STAGE_ONE_INTERVAL;
        this.zzpH = adRequestParcel;
        this.zzzm = adSizeParcel;
        this.zzpb = versionInfoParcel;
        this.zzzn = z;
        this.zzoY = nativeAdOptionsParcel;
        this.zzoZ = list;
    }

    private void zza(long j, long j2, long j3, long j4) {
        while (this.zzzp == -2) {
            zzb(j, j2, j3, j4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void zza(zzeg zzegVar) {
        if ("com.google.ads.mediation.AdUrlAdapter".equals(this.zzzj)) {
            Bundle bundle = this.zzpH.zzsL.getBundle(this.zzzj);
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString("sdk_less_network_id", this.zzzl.zzyN);
            this.zzpH.zzsL.putBundle(this.zzzj, bundle);
        }
        try {
            if (this.zzpb.zzJw < 4100000) {
                if (this.zzzm.zztf) {
                    this.zzzo.zza(com.google.android.gms.dynamic.zze.zzy(this.mContext), this.zzpH, this.zzzl.zzyT, zzegVar);
                    return;
                } else {
                    this.zzzo.zza(com.google.android.gms.dynamic.zze.zzy(this.mContext), this.zzzm, this.zzpH, this.zzzl.zzyT, zzegVar);
                    return;
                }
            }
            if (this.zzzn) {
                this.zzzo.zza(com.google.android.gms.dynamic.zze.zzy(this.mContext), this.zzpH, this.zzzl.zzyT, this.zzzl.zzyM, zzegVar, this.zzoY, this.zzoZ);
            } else if (this.zzzm.zztf) {
                this.zzzo.zza(com.google.android.gms.dynamic.zze.zzy(this.mContext), this.zzpH, this.zzzl.zzyT, this.zzzl.zzyM, zzegVar);
            } else {
                this.zzzo.zza(com.google.android.gms.dynamic.zze.zzy(this.mContext), this.zzzm, this.zzpH, this.zzzl.zzyT, this.zzzl.zzyM, zzegVar);
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not request ad from mediation adapter.", e);
            zzq(5);
        }
    }

    private void zzb(long j, long j2, long j3, long j4) {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long j5 = j2 - (jElapsedRealtime - j);
        long j6 = j4 - (jElapsedRealtime - j3);
        if (j5 <= 0 || j6 <= 0) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaG("Timed out waiting for adapter.");
            this.zzzp = 3;
        } else {
            try {
                this.zzpd.wait(Math.min(j5, j6));
            } catch (InterruptedException e) {
                this.zzzp = -1;
            }
        }
    }

    private String zzdT() {
        try {
            if (!TextUtils.isEmpty(this.zzzl.zzyQ)) {
                return this.zzox.zzaf(this.zzzl.zzyQ) ? "com.google.android.gms.ads.mediation.customevent.CustomEventAdapter" : "com.google.ads.mediation.customevent.CustomEventAdapter";
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Fail to determine the custom event's version, assuming the old one.");
        }
        return "com.google.ads.mediation.customevent.CustomEventAdapter";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public zzen zzdU() {
        com.google.android.gms.ads.internal.util.client.zzb.zzaG("Instantiating mediation adapter: " + this.zzzj);
        if (zzby.zzvu.get().booleanValue()) {
            if ("com.google.ads.mediation.admob.AdMobAdapter".equals(this.zzzj)) {
                return new zzet(new AdMobAdapter());
            }
            if ("com.google.ads.mediation.AdUrlAdapter".equals(this.zzzj)) {
                return new zzet(new AdUrlAdapter());
            }
        }
        try {
            return this.zzox.zzae(this.zzzj);
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zza("Could not instantiate mediation adapter: " + this.zzzj, e);
            return null;
        }
    }

    public void cancel() {
        synchronized (this.zzpd) {
            try {
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not destroy mediation adapter.", e);
            }
            if (this.zzzo != null) {
                this.zzzo.destroy();
                this.zzzp = -1;
                this.zzpd.notify();
            } else {
                this.zzzp = -1;
                this.zzpd.notify();
            }
        }
    }

    public zzei zza(long j, long j2) {
        zzei zzeiVar;
        synchronized (this.zzpd) {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            final zzeg zzegVar = new zzeg();
            zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.internal.zzeh.1
                @Override // java.lang.Runnable
                public void run() {
                    synchronized (zzeh.this.zzpd) {
                        if (zzeh.this.zzzp != -2) {
                            return;
                        }
                        zzeh.this.zzzo = zzeh.this.zzdU();
                        if (zzeh.this.zzzo == null) {
                            zzeh.this.zzq(4);
                        } else {
                            zzegVar.zza(zzeh.this);
                            zzeh.this.zza(zzegVar);
                        }
                    }
                }
            });
            zza(jElapsedRealtime, this.zzzk, j, j2);
            zzeiVar = new zzei(this.zzzl, this.zzzo, this.zzzj, zzegVar, this.zzzp, this.zzzq);
        }
        return zzeiVar;
    }

    @Override // com.google.android.gms.internal.zzei.zza
    public void zza(int i, zzep zzepVar) {
        synchronized (this.zzpd) {
            this.zzzp = i;
            this.zzzq = zzepVar;
            this.zzpd.notify();
        }
    }

    @Override // com.google.android.gms.internal.zzei.zza
    public void zzq(int i) {
        synchronized (this.zzpd) {
            this.zzzp = i;
            this.zzpd.notify();
        }
    }
}
