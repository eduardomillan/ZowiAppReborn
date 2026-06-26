package com.google.android.gms.ads.mediation;

import com.google.android.gms.ads.formats.NativeAd;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public abstract class NativeContentAdMapper extends NativeAdMapper {
    private NativeAd.Image zzKK;
    private String zzwo;
    private List<NativeAd.Image> zzwp;
    private String zzwq;
    private String zzws;
    private String zzwz;

    public final String getAdvertiser() {
        return this.zzwz;
    }

    public final String getBody() {
        return this.zzwq;
    }

    public final String getCallToAction() {
        return this.zzws;
    }

    public final String getHeadline() {
        return this.zzwo;
    }

    public final List<NativeAd.Image> getImages() {
        return this.zzwp;
    }

    public final NativeAd.Image getLogo() {
        return this.zzKK;
    }

    public final void setAdvertiser(String advertiser) {
        this.zzwz = advertiser;
    }

    public final void setBody(String body) {
        this.zzwq = body;
    }

    public final void setCallToAction(String callToAction) {
        this.zzws = callToAction;
    }

    public final void setHeadline(String headline) {
        this.zzwo = headline;
    }

    public final void setImages(List<NativeAd.Image> images) {
        this.zzwp = images;
    }

    public final void setLogo(NativeAd.Image logo) {
        this.zzKK = logo;
    }
}
