package com.google.android.gms.ads.internal.formats;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.internal.zzcm;
import com.google.android.gms.internal.zzgr;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzc extends zzcm.zza {
    private final Uri mUri;
    private final Drawable zzwm;
    private final double zzwn;

    public zzc(Drawable drawable, Uri uri, double d) {
        this.zzwm = drawable;
        this.mUri = uri;
        this.zzwn = d;
    }

    @Override // com.google.android.gms.internal.zzcm
    public double getScale() {
        return this.zzwn;
    }

    @Override // com.google.android.gms.internal.zzcm
    public Uri getUri() throws RemoteException {
        return this.mUri;
    }

    @Override // com.google.android.gms.internal.zzcm
    public com.google.android.gms.dynamic.zzd zzdv() throws RemoteException {
        return com.google.android.gms.dynamic.zze.zzy(this.zzwm);
    }
}
