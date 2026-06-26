package com.google.android.gms.ads.internal.formats;

import android.os.Bundle;
import com.google.android.gms.ads.internal.formats.zzh;
import com.google.android.gms.internal.zzcm;
import com.google.android.gms.internal.zzcq;
import com.google.android.gms.internal.zzgr;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzd extends zzcq.zza implements zzh.zza {
    private final Bundle mExtras;
    private final Object zzpd = new Object();
    private final String zzwo;
    private final List<zzc> zzwp;
    private final String zzwq;
    private final zzcm zzwr;
    private final String zzws;
    private final double zzwt;
    private final String zzwu;
    private final String zzwv;
    private final zza zzww;
    private zzh zzwx;

    public zzd(String str, List list, String str2, zzcm zzcmVar, String str3, double d, String str4, String str5, zza zzaVar, Bundle bundle) {
        this.zzwo = str;
        this.zzwp = list;
        this.zzwq = str2;
        this.zzwr = zzcmVar;
        this.zzws = str3;
        this.zzwt = d;
        this.zzwu = str4;
        this.zzwv = str5;
        this.zzww = zzaVar;
        this.mExtras = bundle;
    }

    @Override // com.google.android.gms.internal.zzcq
    public String getBody() {
        return this.zzwq;
    }

    @Override // com.google.android.gms.internal.zzcq
    public String getCallToAction() {
        return this.zzws;
    }

    @Override // com.google.android.gms.ads.internal.formats.zzh.zza
    public String getCustomTemplateId() {
        return "";
    }

    @Override // com.google.android.gms.internal.zzcq
    public Bundle getExtras() {
        return this.mExtras;
    }

    @Override // com.google.android.gms.internal.zzcq
    public String getHeadline() {
        return this.zzwo;
    }

    @Override // com.google.android.gms.internal.zzcq
    public List getImages() {
        return this.zzwp;
    }

    @Override // com.google.android.gms.internal.zzcq
    public String getPrice() {
        return this.zzwv;
    }

    @Override // com.google.android.gms.internal.zzcq
    public double getStarRating() {
        return this.zzwt;
    }

    @Override // com.google.android.gms.internal.zzcq
    public String getStore() {
        return this.zzwu;
    }

    @Override // com.google.android.gms.ads.internal.formats.zzh.zza
    public void zza(zzh zzhVar) {
        synchronized (this.zzpd) {
            this.zzwx = zzhVar;
        }
    }

    @Override // com.google.android.gms.internal.zzcq
    public zzcm zzdw() {
        return this.zzwr;
    }

    @Override // com.google.android.gms.internal.zzcq
    public com.google.android.gms.dynamic.zzd zzdx() {
        return com.google.android.gms.dynamic.zze.zzy(this.zzwx);
    }

    @Override // com.google.android.gms.ads.internal.formats.zzh.zza
    public String zzdy() {
        return "2";
    }

    @Override // com.google.android.gms.ads.internal.formats.zzh.zza
    public zza zzdz() {
        return this.zzww;
    }
}
