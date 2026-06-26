package com.google.android.gms.tagmanager;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class zzy implements zzbh {
    private int zzNW = 5;

    @Override // com.google.android.gms.tagmanager.zzbh
    public void e(String message) {
        if (this.zzNW <= 6) {
            Log.e("GoogleTagManager", message);
        }
    }

    @Override // com.google.android.gms.tagmanager.zzbh
    public void setLogLevel(int logLevel) {
        this.zzNW = logLevel;
    }

    @Override // com.google.android.gms.tagmanager.zzbh
    public void v(String message) {
        if (this.zzNW <= 2) {
            Log.v("GoogleTagManager", message);
        }
    }

    @Override // com.google.android.gms.tagmanager.zzbh
    public void zzaF(String str) {
        if (this.zzNW <= 3) {
            Log.d("GoogleTagManager", str);
        }
    }

    @Override // com.google.android.gms.tagmanager.zzbh
    public void zzaG(String str) {
        if (this.zzNW <= 4) {
            Log.i("GoogleTagManager", str);
        }
    }

    @Override // com.google.android.gms.tagmanager.zzbh
    public void zzaH(String str) {
        if (this.zzNW <= 5) {
            Log.w("GoogleTagManager", str);
        }
    }

    @Override // com.google.android.gms.tagmanager.zzbh
    public void zzb(String str, Throwable th) {
        if (this.zzNW <= 6) {
            Log.e("GoogleTagManager", str, th);
        }
    }

    @Override // com.google.android.gms.tagmanager.zzbh
    public void zzd(String str, Throwable th) {
        if (this.zzNW <= 5) {
            Log.w("GoogleTagManager", str, th);
        }
    }
}
