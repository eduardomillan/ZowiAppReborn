package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.common.GooglePlayServicesUtil;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzbx {
    private final Object zzpd = new Object();
    private boolean zzpA = false;
    private SharedPreferences zzuj = null;

    public void initialize(Context context) {
        synchronized (this.zzpd) {
            if (this.zzpA) {
                return;
            }
            Context remoteContext = GooglePlayServicesUtil.getRemoteContext(context);
            if (remoteContext == null) {
                return;
            }
            this.zzuj = com.google.android.gms.ads.internal.zzp.zzbC().zzv(remoteContext);
            this.zzpA = true;
        }
    }

    public <T> T zzd(zzbu<T> zzbuVar) {
        synchronized (this.zzpd) {
            if (this.zzpA) {
                return zzbuVar.zza(this.zzuj);
            }
            return zzbuVar.zzde();
        }
    }
}
