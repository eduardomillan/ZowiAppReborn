package com.google.android.gms.ads.internal.formats;

import com.google.android.gms.ads.internal.formats.zzh;
import com.google.android.gms.internal.zzcm;
import com.google.android.gms.internal.zzcu;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzmi;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzf extends zzcu.zza implements zzh.zza {
    private final Object zzpd = new Object();
    private final String zzwA;
    private final zzmi<String, zzc> zzwB;
    private final zzmi<String, String> zzwC;
    private final zza zzww;
    private zzh zzwx;

    public zzf(String str, zzmi<String, zzc> zzmiVar, zzmi<String, String> zzmiVar2, zza zzaVar) {
        this.zzwA = str;
        this.zzwB = zzmiVar;
        this.zzwC = zzmiVar2;
        this.zzww = zzaVar;
    }

    @Override // com.google.android.gms.internal.zzcu
    public List<String> getAvailableAssetNames() {
        int i = 0;
        String[] strArr = new String[this.zzwB.size() + this.zzwC.size()];
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzwB.size(); i3++) {
            strArr[i2] = this.zzwB.keyAt(i3);
            i2++;
        }
        while (i < this.zzwC.size()) {
            strArr[i2] = this.zzwC.keyAt(i);
            i++;
            i2++;
        }
        return Arrays.asList(strArr);
    }

    @Override // com.google.android.gms.internal.zzcu, com.google.android.gms.ads.internal.formats.zzh.zza
    public String getCustomTemplateId() {
        return this.zzwA;
    }

    @Override // com.google.android.gms.internal.zzcu
    public void performClick(String assetName) {
        synchronized (this.zzpd) {
            if (this.zzwx == null) {
                com.google.android.gms.ads.internal.util.client.zzb.e("Attempt to call performClick before ad initialized.");
            } else {
                this.zzwx.zza(assetName, null, null);
            }
        }
    }

    @Override // com.google.android.gms.internal.zzcu
    public void recordImpression() {
        synchronized (this.zzpd) {
            if (this.zzwx == null) {
                com.google.android.gms.ads.internal.util.client.zzb.e("Attempt to perform recordImpression before ad initialized.");
            } else {
                this.zzwx.recordImpression();
            }
        }
    }

    @Override // com.google.android.gms.internal.zzcu
    public String zzU(String str) {
        return this.zzwC.get(str);
    }

    @Override // com.google.android.gms.internal.zzcu
    public zzcm zzV(String str) {
        return this.zzwB.get(str);
    }

    @Override // com.google.android.gms.ads.internal.formats.zzh.zza
    public void zza(zzh zzhVar) {
        synchronized (this.zzpd) {
            this.zzwx = zzhVar;
        }
    }

    @Override // com.google.android.gms.ads.internal.formats.zzh.zza
    public String zzdy() {
        return "3";
    }

    @Override // com.google.android.gms.ads.internal.formats.zzh.zza
    public zza zzdz() {
        return this.zzww;
    }
}
