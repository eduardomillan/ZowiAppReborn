package com.google.android.gms.internal;

import android.os.Bundle;
import android.view.View;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.mediation.NativeContentAdMapper;
import com.google.android.gms.internal.zzer;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzew extends zzer.zza {
    private final NativeContentAdMapper zzzO;

    public zzew(NativeContentAdMapper nativeContentAdMapper) {
        this.zzzO = nativeContentAdMapper;
    }

    @Override // com.google.android.gms.internal.zzer
    public String getAdvertiser() {
        return this.zzzO.getAdvertiser();
    }

    @Override // com.google.android.gms.internal.zzer
    public String getBody() {
        return this.zzzO.getBody();
    }

    @Override // com.google.android.gms.internal.zzer
    public String getCallToAction() {
        return this.zzzO.getCallToAction();
    }

    @Override // com.google.android.gms.internal.zzer
    public Bundle getExtras() {
        return this.zzzO.getExtras();
    }

    @Override // com.google.android.gms.internal.zzer
    public String getHeadline() {
        return this.zzzO.getHeadline();
    }

    @Override // com.google.android.gms.internal.zzer
    public List getImages() {
        List<NativeAd.Image> images = this.zzzO.getImages();
        if (images == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (NativeAd.Image image : images) {
            arrayList.add(new com.google.android.gms.ads.internal.formats.zzc(image.getDrawable(), image.getUri(), image.getScale()));
        }
        return arrayList;
    }

    @Override // com.google.android.gms.internal.zzer
    public boolean getOverrideClickHandling() {
        return this.zzzO.getOverrideClickHandling();
    }

    @Override // com.google.android.gms.internal.zzer
    public boolean getOverrideImpressionRecording() {
        return this.zzzO.getOverrideImpressionRecording();
    }

    @Override // com.google.android.gms.internal.zzer
    public void recordImpression() {
        this.zzzO.recordImpression();
    }

    @Override // com.google.android.gms.internal.zzer
    public void zzc(com.google.android.gms.dynamic.zzd zzdVar) {
        this.zzzO.handleClick((View) com.google.android.gms.dynamic.zze.zzp(zzdVar));
    }

    @Override // com.google.android.gms.internal.zzer
    public void zzd(com.google.android.gms.dynamic.zzd zzdVar) {
        this.zzzO.trackView((View) com.google.android.gms.dynamic.zze.zzp(zzdVar));
    }

    @Override // com.google.android.gms.internal.zzer
    public zzcm zzdA() {
        NativeAd.Image logo = this.zzzO.getLogo();
        if (logo != null) {
            return new com.google.android.gms.ads.internal.formats.zzc(logo.getDrawable(), logo.getUri(), logo.getScale());
        }
        return null;
    }
}
