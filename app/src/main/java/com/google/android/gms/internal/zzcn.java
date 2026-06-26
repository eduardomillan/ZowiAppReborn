package com.google.android.gms.internal;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.RemoteException;
import com.google.android.gms.ads.formats.NativeAd;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzcn extends NativeAd.Image {
    private final Drawable mDrawable;
    private final Uri mUri;
    private final double zzwn;
    private final zzcm zzxc;

    public zzcn(zzcm zzcmVar) {
        com.google.android.gms.dynamic.zzd zzdVarZzdv;
        Uri uri = null;
        this.zzxc = zzcmVar;
        try {
            zzdVarZzdv = this.zzxc.zzdv();
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Failed to get drawable.", e);
        }
        Drawable drawable = zzdVarZzdv != null ? (Drawable) com.google.android.gms.dynamic.zze.zzp(zzdVarZzdv) : null;
        this.mDrawable = drawable;
        try {
            uri = this.zzxc.getUri();
        } catch (RemoteException e2) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Failed to get uri.", e2);
        }
        this.mUri = uri;
        double scale = 1.0d;
        try {
            scale = this.zzxc.getScale();
        } catch (RemoteException e3) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Failed to get scale.", e3);
        }
        this.zzwn = scale;
    }

    @Override // com.google.android.gms.ads.formats.NativeAd.Image
    public Drawable getDrawable() {
        return this.mDrawable;
    }

    @Override // com.google.android.gms.ads.formats.NativeAd.Image
    public double getScale() {
        return this.zzwn;
    }

    @Override // com.google.android.gms.ads.formats.NativeAd.Image
    public Uri getUri() {
        return this.mUri;
    }
}
