package com.google.android.gms.ads.internal.formats;

import android.os.Bundle;
import com.google.android.gms.ads.internal.formats.zzh;
import com.google.android.gms.internal.zzcm;
import com.google.android.gms.internal.zzcs;
import com.google.android.gms.internal.zzgr;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zze extends zzcs.zza implements zzh.zza {
    private final Bundle mExtras;
    private final Object zzpd = new Object();
    private final String zzwo;
    private final List<zzc> zzwp;
    private final String zzwq;
    private final String zzws;
    private final zza zzww;
    private zzh zzwx;
    private final zzcm zzwy;
    private final String zzwz;

    public zze(String str, List list, String str2, zzcm zzcmVar, String str3, String str4, zza zzaVar, Bundle bundle) {
        this.zzwo = str;
        this.zzwp = list;
        this.zzwq = str2;
        this.zzwy = zzcmVar;
        this.zzws = str3;
        this.zzwz = str4;
        this.zzww = zzaVar;
        this.mExtras = bundle;
    }

    @Override // com.google.android.gms.internal.zzcs
    public String getAdvertiser() {
        return this.zzwz;
    }

    @Override // com.google.android.gms.internal.zzcs
    public String getBody() {
        return this.zzwq;
    }

    @Override // com.google.android.gms.internal.zzcs
    public String getCallToAction() {
        return this.zzws;
    }

    @Override // com.google.android.gms.ads.internal.formats.zzh.zza
    public String getCustomTemplateId() {
        return "";
    }

    @Override // com.google.android.gms.internal.zzcs
    public Bundle getExtras() {
        return this.mExtras;
    }

    @Override // com.google.android.gms.internal.zzcs
    public String getHeadline() {
        return this.zzwo;
    }

    @Override // com.google.android.gms.internal.zzcs
    public List getImages() {
        return this.zzwp;
    }

    @Override // com.google.android.gms.ads.internal.formats.zzh.zza
    public void zza(zzh zzhVar) {
        synchronized (this.zzpd) {
            this.zzwx = zzhVar;
        }
    }

    @Override // com.google.android.gms.internal.zzcs
    public zzcm zzdA() {
        return this.zzwy;
    }

    @Override // com.google.android.gms.internal.zzcs
    public com.google.android.gms.dynamic.zzd zzdx() {
        return com.google.android.gms.dynamic.zze.zzy(this.zzwx);
    }

    @Override // com.google.android.gms.ads.internal.formats.zzh.zza
    public String zzdy() {
        return "1";
    }

    @Override // com.google.android.gms.ads.internal.formats.zzh.zza
    public zza zzdz() {
        return this.zzww;
    }
}
