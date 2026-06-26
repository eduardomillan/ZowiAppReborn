package com.google.android.gms.internal;

import android.os.Bundle;
import android.view.View;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.mediation.NativeAppInstallAdMapper;
import com.google.android.gms.internal.zzeq;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzev extends zzeq.zza {
    private final NativeAppInstallAdMapper zzzN;

    public zzev(NativeAppInstallAdMapper nativeAppInstallAdMapper) {
        this.zzzN = nativeAppInstallAdMapper;
    }

    @Override // com.google.android.gms.internal.zzeq
    public String getBody() {
        return this.zzzN.getBody();
    }

    @Override // com.google.android.gms.internal.zzeq
    public String getCallToAction() {
        return this.zzzN.getCallToAction();
    }

    @Override // com.google.android.gms.internal.zzeq
    public Bundle getExtras() {
        return this.zzzN.getExtras();
    }

    @Override // com.google.android.gms.internal.zzeq
    public String getHeadline() {
        return this.zzzN.getHeadline();
    }

    @Override // com.google.android.gms.internal.zzeq
    public List getImages() {
        List<NativeAd.Image> images = this.zzzN.getImages();
        if (images == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (NativeAd.Image image : images) {
            arrayList.add(new com.google.android.gms.ads.internal.formats.zzc(image.getDrawable(), image.getUri(), image.getScale()));
        }
        return arrayList;
    }

    @Override // com.google.android.gms.internal.zzeq
    public boolean getOverrideClickHandling() {
        return this.zzzN.getOverrideClickHandling();
    }

    @Override // com.google.android.gms.internal.zzeq
    public boolean getOverrideImpressionRecording() {
        return this.zzzN.getOverrideImpressionRecording();
    }

    @Override // com.google.android.gms.internal.zzeq
    public String getPrice() {
        return this.zzzN.getPrice();
    }

    @Override // com.google.android.gms.internal.zzeq
    public double getStarRating() {
        return this.zzzN.getStarRating();
    }

    @Override // com.google.android.gms.internal.zzeq
    public String getStore() {
        return this.zzzN.getStore();
    }

    @Override // com.google.android.gms.internal.zzeq
    public void recordImpression() {
        this.zzzN.recordImpression();
    }

    @Override // com.google.android.gms.internal.zzeq
    public void zzc(com.google.android.gms.dynamic.zzd zzdVar) {
        this.zzzN.handleClick((View) com.google.android.gms.dynamic.zze.zzp(zzdVar));
    }

    @Override // com.google.android.gms.internal.zzeq
    public void zzd(com.google.android.gms.dynamic.zzd zzdVar) {
        this.zzzN.trackView((View) com.google.android.gms.dynamic.zze.zzp(zzdVar));
    }

    @Override // com.google.android.gms.internal.zzeq
    public zzcm zzdw() {
        NativeAd.Image icon = this.zzzN.getIcon();
        if (icon != null) {
            return new com.google.android.gms.ads.internal.formats.zzc(icon.getDrawable(), icon.getUri(), icon.getScale());
        }
        return null;
    }
}
