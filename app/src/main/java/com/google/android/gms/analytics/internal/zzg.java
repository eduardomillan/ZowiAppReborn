package com.google.android.gms.analytics.internal;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.internal.zzmn;
import com.google.android.gms.internal.zzmp;

/* JADX INFO: loaded from: classes.dex */
public class zzg {
    private final Context zzMX;
    private final Context zzqZ;

    public zzg(Context context) {
        com.google.android.gms.common.internal.zzx.zzw(context);
        Context applicationContext = context.getApplicationContext();
        com.google.android.gms.common.internal.zzx.zzb(applicationContext, "Application context can't be null");
        this.zzqZ = applicationContext;
        this.zzMX = applicationContext;
    }

    public Context getApplicationContext() {
        return this.zzqZ;
    }

    protected com.google.android.gms.measurement.zzg zzY(Context context) {
        return com.google.android.gms.measurement.zzg.zzaJ(context);
    }

    protected zzu zza(zzf zzfVar) {
        return new zzu(zzfVar);
    }

    protected zzk zzb(zzf zzfVar) {
        return new zzk(zzfVar);
    }

    protected zza zzc(zzf zzfVar) {
        return new zza(zzfVar);
    }

    protected zzn zzd(zzf zzfVar) {
        return new zzn(zzfVar);
    }

    protected zzan zze(zzf zzfVar) {
        return new zzan(zzfVar);
    }

    protected zzaf zzf(zzf zzfVar) {
        return new zzaf(zzfVar);
    }

    protected zzr zzg(zzf zzfVar) {
        return new zzr(zzfVar);
    }

    protected zzmn zzh(zzf zzfVar) {
        return zzmp.zzqt();
    }

    protected GoogleAnalytics zzi(zzf zzfVar) {
        return new GoogleAnalytics(zzfVar);
    }

    public Context zziG() {
        return this.zzMX;
    }

    zzl zzj(zzf zzfVar) {
        return new zzl(zzfVar, this);
    }

    zzag zzk(zzf zzfVar) {
        return new zzag(zzfVar);
    }

    protected zzb zzl(zzf zzfVar) {
        return new zzb(zzfVar, this);
    }

    public zzj zzm(zzf zzfVar) {
        return new zzj(zzfVar);
    }

    public zzah zzn(zzf zzfVar) {
        return new zzah(zzfVar);
    }

    public zzi zzo(zzf zzfVar) {
        return new zzi(zzfVar);
    }

    public zzv zzp(zzf zzfVar) {
        return new zzv(zzfVar);
    }

    public zzai zzq(zzf zzfVar) {
        return new zzai(zzfVar);
    }
}
