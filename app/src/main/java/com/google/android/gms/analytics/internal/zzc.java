package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.internal.zzmn;

/* JADX INFO: loaded from: classes.dex */
public class zzc {
    private final zzf zzME;

    protected zzc(zzf zzfVar) {
        com.google.android.gms.common.internal.zzx.zzw(zzfVar);
        this.zzME = zzfVar;
    }

    private void zza(int i, String str, Object obj, Object obj2, Object obj3) {
        zzaf zzafVarZziH = this.zzME != null ? this.zzME.zziH() : null;
        if (zzafVarZziH != null) {
            zzafVarZziH.zza(i, str, obj, obj2, obj3);
            return;
        }
        String str2 = zzy.zzOg.get();
        if (Log.isLoggable(str2, i)) {
            Log.println(i, str2, zzc(str, obj, obj2, obj3));
        }
    }

    protected static String zzc(String str, Object obj, Object obj2, Object obj3) {
        if (str == null) {
            str = "";
        }
        String strZzi = zzi(obj);
        String strZzi2 = zzi(obj2);
        String strZzi3 = zzi(obj3);
        StringBuilder sb = new StringBuilder();
        String str2 = "";
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
            str2 = ": ";
        }
        if (!TextUtils.isEmpty(strZzi)) {
            sb.append(str2);
            sb.append(strZzi);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(strZzi2)) {
            sb.append(str2);
            sb.append(strZzi2);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(strZzi3)) {
            sb.append(str2);
            sb.append(strZzi3);
        }
        return sb.toString();
    }

    private static String zzi(Object obj) {
        if (obj == null) {
            return "";
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof Boolean) {
            return obj == Boolean.TRUE ? "true" : "false";
        }
        return obj instanceof Throwable ? ((Throwable) obj).toString() : obj.toString();
    }

    protected Context getContext() {
        return this.zzME.getContext();
    }

    public void zza(String str, Object obj) {
        zza(2, str, obj, null, null);
    }

    public void zza(String str, Object obj, Object obj2) {
        zza(2, str, obj, obj2, null);
    }

    public void zza(String str, Object obj, Object obj2, Object obj3) {
        zza(3, str, obj, obj2, obj3);
    }

    public void zzb(String str, Object obj) {
        zza(3, str, obj, null, null);
    }

    public void zzb(String str, Object obj, Object obj2) {
        zza(3, str, obj, obj2, null);
    }

    public void zzb(String str, Object obj, Object obj2, Object obj3) {
        zza(5, str, obj, obj2, obj3);
    }

    public void zzba(String str) {
        zza(2, str, null, null, null);
    }

    public void zzbb(String str) {
        zza(3, str, null, null, null);
    }

    public void zzbc(String str) {
        zza(4, str, null, null, null);
    }

    public void zzbd(String str) {
        zza(5, str, null, null, null);
    }

    public void zzbe(String str) {
        zza(6, str, null, null, null);
    }

    public void zzc(String str, Object obj) {
        zza(4, str, obj, null, null);
    }

    public void zzc(String str, Object obj, Object obj2) {
        zza(5, str, obj, obj2, null);
    }

    public void zzd(String str, Object obj) {
        zza(5, str, obj, null, null);
    }

    public void zzd(String str, Object obj, Object obj2) {
        zza(6, str, obj, obj2, null);
    }

    public void zze(String str, Object obj) {
        zza(6, str, obj, null, null);
    }

    public GoogleAnalytics zzhK() {
        return this.zzME.zziI();
    }

    protected zzb zzhP() {
        return this.zzME.zzhP();
    }

    protected zzan zzhQ() {
        return this.zzME.zzhQ();
    }

    protected zza zziA() {
        return this.zzME.zziK();
    }

    protected zzk zziB() {
        return this.zzME.zziB();
    }

    protected zzu zziC() {
        return this.zzME.zziC();
    }

    public boolean zziD() {
        return Log.isLoggable(zzy.zzOg.get(), 2);
    }

    public zzf zziq() {
        return this.zzME;
    }

    protected void zzir() {
        if (zziv().zzjA()) {
            throw new IllegalStateException("Call only supported on the client side");
        }
    }

    protected void zzis() {
        this.zzME.zzis();
    }

    protected zzmn zzit() {
        return this.zzME.zzit();
    }

    protected zzaf zziu() {
        return this.zzME.zziu();
    }

    protected zzr zziv() {
        return this.zzME.zziv();
    }

    protected com.google.android.gms.measurement.zzg zziw() {
        return this.zzME.zziw();
    }

    protected zzv zzix() {
        return this.zzME.zzix();
    }

    protected zzai zziy() {
        return this.zzME.zziy();
    }

    protected zzn zziz() {
        return this.zzME.zziL();
    }
}
