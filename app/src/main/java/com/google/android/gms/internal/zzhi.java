package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.internal.zzhs;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzhi extends zzhz implements zzhj, zzhm {
    private final Context mContext;
    private final zzhs.zza zzDe;
    private final String zzGY;
    private final zzhh zzHg;
    private final zzhm zzHh;
    private final String zzHi;
    private final String zzzj;
    private int zzHj = 0;
    private int zzDv = 3;
    private final Object zzpd = new Object();

    public zzhi(Context context, String str, String str2, String str3, zzhs.zza zzaVar, zzhh zzhhVar, zzhm zzhmVar) {
        this.mContext = context;
        this.zzzj = str;
        this.zzGY = str2;
        this.zzHi = str3;
        this.zzDe = zzaVar;
        this.zzHg = zzhhVar;
        this.zzHh = zzhmVar;
    }

    private void zzk(long j) {
        while (true) {
            synchronized (this.zzpd) {
                if (this.zzHj != 0) {
                    return;
                }
                if (!zzf(j)) {
                    return;
                }
            }
        }
    }

    @Override // com.google.android.gms.internal.zzhz
    public void onStop() {
    }

    @Override // com.google.android.gms.internal.zzhj
    public void zzK(int i) {
        zzb(this.zzzj, 0);
    }

    @Override // com.google.android.gms.internal.zzhm
    public void zzav(String str) {
        synchronized (this.zzpd) {
            this.zzHj = 1;
            this.zzpd.notify();
        }
    }

    @Override // com.google.android.gms.internal.zzhm
    public void zzb(String str, int i) {
        synchronized (this.zzpd) {
            this.zzHj = 2;
            this.zzDv = i;
            this.zzpd.notify();
        }
    }

    @Override // com.google.android.gms.internal.zzhz
    public void zzbn() {
        if (this.zzHg == null || this.zzHg.zzgd() == null || this.zzHg.zzgc() == null) {
            return;
        }
        final zzhl zzhlVarZzgd = this.zzHg.zzgd();
        zzhlVarZzgd.zza((zzhm) this);
        zzhlVarZzgd.zza((zzhj) this);
        final AdRequestParcel adRequestParcel = this.zzDe.zzHC.zzEn;
        final zzen zzenVarZzgc = this.zzHg.zzgc();
        try {
            if (zzenVarZzgc.isInitialized()) {
                com.google.android.gms.ads.internal.util.client.zza.zzJt.post(new Runnable() { // from class: com.google.android.gms.internal.zzhi.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            zzenVarZzgc.zza(adRequestParcel, zzhi.this.zzHi);
                        } catch (RemoteException e) {
                            com.google.android.gms.ads.internal.util.client.zzb.zzd("Fail to load ad from adapter.", e);
                            zzhi.this.zzb(zzhi.this.zzzj, 0);
                        }
                    }
                });
            } else {
                com.google.android.gms.ads.internal.util.client.zza.zzJt.post(new Runnable() { // from class: com.google.android.gms.internal.zzhi.2
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            zzenVarZzgc.zza(com.google.android.gms.dynamic.zze.zzy(zzhi.this.mContext), adRequestParcel, zzhi.this.zzGY, zzhlVarZzgd, zzhi.this.zzHi);
                        } catch (RemoteException e) {
                            com.google.android.gms.ads.internal.util.client.zzb.zzd("Fail to initialize adapter " + zzhi.this.zzzj, e);
                            zzhi.this.zzb(zzhi.this.zzzj, 0);
                        }
                    }
                });
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Fail to check if adapter is initialized.", e);
            zzb(this.zzzj, 0);
        }
        zzk(com.google.android.gms.ads.internal.zzp.zzbz().elapsedRealtime());
        zzhlVarZzgd.zza((zzhm) null);
        zzhlVarZzgd.zza((zzhj) null);
        if (this.zzHj == 1) {
            this.zzHh.zzav(this.zzzj);
        } else {
            this.zzHh.zzb(this.zzzj, this.zzDv);
        }
    }

    protected boolean zzf(long j) {
        long jElapsedRealtime = 20000 - (com.google.android.gms.ads.internal.zzp.zzbz().elapsedRealtime() - j);
        if (jElapsedRealtime <= 0) {
            return false;
        }
        try {
            this.zzpd.wait(jElapsedRealtime);
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override // com.google.android.gms.internal.zzhj
    public void zzge() {
        this.zzHg.zzgd();
        AdRequestParcel adRequestParcel = this.zzDe.zzHC.zzEn;
        try {
            this.zzHg.zzgc().zza(adRequestParcel, this.zzHi);
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Fail to load ad from adapter.", e);
            zzb(this.zzzj, 0);
        }
    }
}
