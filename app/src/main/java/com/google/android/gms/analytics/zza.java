package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzjo;
import com.google.android.gms.measurement.zzf;
import com.google.android.gms.measurement.zzi;
import java.util.ListIterator;

/* JADX INFO: loaded from: classes.dex */
public class zza extends zzf<zza> {
    private final com.google.android.gms.analytics.internal.zzf zzLf;
    private boolean zzLg;

    public zza(com.google.android.gms.analytics.internal.zzf zzfVar) {
        super(zzfVar.zziw(), zzfVar.zzit());
        this.zzLf = zzfVar;
    }

    public void enableAdvertisingIdCollection(boolean enable) {
        this.zzLg = enable;
    }

    @Override // com.google.android.gms.measurement.zzf
    protected void zza(com.google.android.gms.measurement.zzc zzcVar) {
        zzjo zzjoVar = (zzjo) zzcVar.zze(zzjo.class);
        if (TextUtils.isEmpty(zzjoVar.getClientId())) {
            zzjoVar.setClientId(this.zzLf.zziL().zzjt());
        }
        if (this.zzLg && TextUtils.isEmpty(zzjoVar.zzib())) {
            com.google.android.gms.analytics.internal.zza zzaVarZziK = this.zzLf.zziK();
            zzjoVar.zzaV(zzaVarZziK.zzig());
            zzjoVar.zzG(zzaVarZziK.zzic());
        }
    }

    public void zzaP(String str) {
        zzx.zzcr(str);
        zzaQ(str);
        zzyq().add(new zzb(this.zzLf, str));
    }

    public void zzaQ(String str) {
        Uri uriZzaR = zzb.zzaR(str);
        ListIterator<zzi> listIterator = zzyq().listIterator();
        while (listIterator.hasNext()) {
            if (uriZzaR.equals(listIterator.next().zzhI())) {
                listIterator.remove();
            }
        }
    }

    com.google.android.gms.analytics.internal.zzf zzhF() {
        return this.zzLf;
    }

    @Override // com.google.android.gms.measurement.zzf
    public com.google.android.gms.measurement.zzc zzhG() {
        com.google.android.gms.measurement.zzc zzcVarZzye = zzyp().zzye();
        zzcVarZzye.zzb(this.zzLf.zziB().zzjb());
        zzcVarZzye.zzb(this.zzLf.zziC().zzki());
        zzd(zzcVarZzye);
        return zzcVarZzye;
    }
}
