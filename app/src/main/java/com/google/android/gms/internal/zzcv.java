package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeCustomTemplateAd;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzcv implements NativeCustomTemplateAd {
    private final zzcu zzxi;

    public zzcv(zzcu zzcuVar) {
        this.zzxi = zzcuVar;
    }

    @Override // com.google.android.gms.ads.formats.NativeCustomTemplateAd
    public List<String> getAvailableAssetNames() {
        try {
            return this.zzxi.getAvailableAssetNames();
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Failed to get available asset names.", e);
            return null;
        }
    }

    @Override // com.google.android.gms.ads.formats.NativeCustomTemplateAd
    public String getCustomTemplateId() {
        try {
            return this.zzxi.getCustomTemplateId();
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Failed to get custom template id.", e);
            return null;
        }
    }

    @Override // com.google.android.gms.ads.formats.NativeCustomTemplateAd
    public NativeAd.Image getImage(String assetName) {
        try {
            zzcm zzcmVarZzV = this.zzxi.zzV(assetName);
            if (zzcmVarZzV != null) {
                return new zzcn(zzcmVarZzV);
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Failed to get image.", e);
        }
        return null;
    }

    @Override // com.google.android.gms.ads.formats.NativeCustomTemplateAd
    public CharSequence getText(String assetName) {
        try {
            return this.zzxi.zzU(assetName);
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Failed to get string.", e);
            return null;
        }
    }

    @Override // com.google.android.gms.ads.formats.NativeCustomTemplateAd
    public void performClick(String assetName) {
        try {
            this.zzxi.performClick(assetName);
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Failed to perform click.", e);
        }
    }

    @Override // com.google.android.gms.ads.formats.NativeCustomTemplateAd
    public void recordImpression() {
        try {
            this.zzxi.recordImpression();
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Failed to record impression.", e);
        }
    }
}
