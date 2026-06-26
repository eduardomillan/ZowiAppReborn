package com.google.ads.mediation;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeAdView;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.internal.client.zzl;
import com.google.android.gms.ads.mediation.MediationNativeAdapter;
import com.google.android.gms.ads.mediation.MediationNativeListener;
import com.google.android.gms.ads.mediation.NativeAppInstallAdMapper;
import com.google.android.gms.ads.mediation.NativeContentAdMapper;
import com.google.android.gms.ads.mediation.NativeMediationAdRequest;
import com.google.android.gms.internal.zzgr;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public abstract class AbstractAdViewAdapter implements com.google.android.gms.ads.mediation.MediationBannerAdapter, com.google.android.gms.ads.mediation.MediationInterstitialAdapter, MediationNativeAdapter {
    public static final String AD_UNIT_ID_PARAMETER = "pubid";
    protected AdView zzaK;
    protected InterstitialAd zzaL;
    private AdLoader zzaM;

    static class zza extends NativeAppInstallAdMapper {
        private final NativeAppInstallAd zzaN;

        public zza(NativeAppInstallAd nativeAppInstallAd) {
            this.zzaN = nativeAppInstallAd;
            setHeadline(nativeAppInstallAd.getHeadline().toString());
            setImages(nativeAppInstallAd.getImages());
            setBody(nativeAppInstallAd.getBody().toString());
            setIcon(nativeAppInstallAd.getIcon());
            setCallToAction(nativeAppInstallAd.getCallToAction().toString());
            setStarRating(nativeAppInstallAd.getStarRating().doubleValue());
            setStore(nativeAppInstallAd.getStore().toString());
            setPrice(nativeAppInstallAd.getPrice().toString());
            setOverrideImpressionRecording(true);
            setOverrideClickHandling(true);
        }

        @Override // com.google.android.gms.ads.mediation.NativeAdMapper
        public void trackView(View view) {
            if (view instanceof NativeAdView) {
                ((NativeAdView) view).setNativeAd(this.zzaN);
            }
        }
    }

    static class zzb extends NativeContentAdMapper {
        private final NativeContentAd zzaO;

        public zzb(NativeContentAd nativeContentAd) {
            this.zzaO = nativeContentAd;
            setHeadline(nativeContentAd.getHeadline().toString());
            setImages(nativeContentAd.getImages());
            setBody(nativeContentAd.getBody().toString());
            setLogo(nativeContentAd.getLogo());
            setCallToAction(nativeContentAd.getCallToAction().toString());
            setAdvertiser(nativeContentAd.getAdvertiser().toString());
            setOverrideImpressionRecording(true);
            setOverrideClickHandling(true);
        }

        @Override // com.google.android.gms.ads.mediation.NativeAdMapper
        public void trackView(View view) {
            if (view instanceof NativeAdView) {
                ((NativeAdView) view).setNativeAd(this.zzaO);
            }
        }
    }

    static final class zzc extends AdListener implements com.google.android.gms.ads.internal.client.zza {
        final AbstractAdViewAdapter zzaP;
        final com.google.android.gms.ads.mediation.MediationBannerListener zzaQ;

        public zzc(AbstractAdViewAdapter abstractAdViewAdapter, com.google.android.gms.ads.mediation.MediationBannerListener mediationBannerListener) {
            this.zzaP = abstractAdViewAdapter;
            this.zzaQ = mediationBannerListener;
        }

        @Override // com.google.android.gms.ads.internal.client.zza
        public void onAdClicked() {
            this.zzaQ.onAdClicked(this.zzaP);
        }

        @Override // com.google.android.gms.ads.AdListener
        public void onAdClosed() {
            this.zzaQ.onAdClosed(this.zzaP);
        }

        @Override // com.google.android.gms.ads.AdListener
        public void onAdFailedToLoad(int errorCode) {
            this.zzaQ.onAdFailedToLoad(this.zzaP, errorCode);
        }

        @Override // com.google.android.gms.ads.AdListener
        public void onAdLeftApplication() {
            this.zzaQ.onAdLeftApplication(this.zzaP);
        }

        @Override // com.google.android.gms.ads.AdListener
        public void onAdLoaded() {
            this.zzaQ.onAdLoaded(this.zzaP);
        }

        @Override // com.google.android.gms.ads.AdListener
        public void onAdOpened() {
            this.zzaQ.onAdOpened(this.zzaP);
        }
    }

    static final class zzd extends AdListener implements com.google.android.gms.ads.internal.client.zza {
        final AbstractAdViewAdapter zzaP;
        final com.google.android.gms.ads.mediation.MediationInterstitialListener zzaR;

        public zzd(AbstractAdViewAdapter abstractAdViewAdapter, com.google.android.gms.ads.mediation.MediationInterstitialListener mediationInterstitialListener) {
            this.zzaP = abstractAdViewAdapter;
            this.zzaR = mediationInterstitialListener;
        }

        @Override // com.google.android.gms.ads.internal.client.zza
        public void onAdClicked() {
            this.zzaR.onAdClicked(this.zzaP);
        }

        @Override // com.google.android.gms.ads.AdListener
        public void onAdClosed() {
            this.zzaR.onAdClosed(this.zzaP);
        }

        @Override // com.google.android.gms.ads.AdListener
        public void onAdFailedToLoad(int errorCode) {
            this.zzaR.onAdFailedToLoad(this.zzaP, errorCode);
        }

        @Override // com.google.android.gms.ads.AdListener
        public void onAdLeftApplication() {
            this.zzaR.onAdLeftApplication(this.zzaP);
        }

        @Override // com.google.android.gms.ads.AdListener
        public void onAdLoaded() {
            this.zzaR.onAdLoaded(this.zzaP);
        }

        @Override // com.google.android.gms.ads.AdListener
        public void onAdOpened() {
            this.zzaR.onAdOpened(this.zzaP);
        }
    }

    static final class zze extends AdListener implements NativeAppInstallAd.OnAppInstallAdLoadedListener, NativeContentAd.OnContentAdLoadedListener, com.google.android.gms.ads.internal.client.zza {
        final AbstractAdViewAdapter zzaP;
        final MediationNativeListener zzaS;

        public zze(AbstractAdViewAdapter abstractAdViewAdapter, MediationNativeListener mediationNativeListener) {
            this.zzaP = abstractAdViewAdapter;
            this.zzaS = mediationNativeListener;
        }

        @Override // com.google.android.gms.ads.internal.client.zza
        public void onAdClicked() {
            this.zzaS.onAdClicked(this.zzaP);
        }

        @Override // com.google.android.gms.ads.AdListener
        public void onAdClosed() {
            this.zzaS.onAdClosed(this.zzaP);
        }

        @Override // com.google.android.gms.ads.AdListener
        public void onAdFailedToLoad(int errorCode) {
            this.zzaS.onAdFailedToLoad(this.zzaP, errorCode);
        }

        @Override // com.google.android.gms.ads.AdListener
        public void onAdLeftApplication() {
            this.zzaS.onAdLeftApplication(this.zzaP);
        }

        @Override // com.google.android.gms.ads.AdListener
        public void onAdLoaded() {
        }

        @Override // com.google.android.gms.ads.AdListener
        public void onAdOpened() {
            this.zzaS.onAdOpened(this.zzaP);
        }

        @Override // com.google.android.gms.ads.formats.NativeAppInstallAd.OnAppInstallAdLoadedListener
        public void onAppInstallAdLoaded(NativeAppInstallAd ad) {
            this.zzaS.onAdLoaded(this.zzaP, new zza(ad));
        }

        @Override // com.google.android.gms.ads.formats.NativeContentAd.OnContentAdLoadedListener
        public void onContentAdLoaded(NativeContentAd ad) {
            this.zzaS.onAdLoaded(this.zzaP, new zzb(ad));
        }
    }

    public String getAdUnitId(Bundle serverParameters) {
        return serverParameters.getString(AD_UNIT_ID_PARAMETER);
    }

    @Override // com.google.android.gms.ads.mediation.MediationBannerAdapter
    public View getBannerView() {
        return this.zzaK;
    }

    @Override // com.google.android.gms.ads.mediation.MediationAdapter
    public void onDestroy() {
        if (this.zzaK != null) {
            this.zzaK.destroy();
            this.zzaK = null;
        }
        if (this.zzaL != null) {
            this.zzaL = null;
        }
        if (this.zzaM != null) {
            this.zzaM = null;
        }
    }

    @Override // com.google.android.gms.ads.mediation.MediationAdapter
    public void onPause() {
        if (this.zzaK != null) {
            this.zzaK.pause();
        }
    }

    @Override // com.google.android.gms.ads.mediation.MediationAdapter
    public void onResume() {
        if (this.zzaK != null) {
            this.zzaK.resume();
        }
    }

    @Override // com.google.android.gms.ads.mediation.MediationBannerAdapter
    public void requestBannerAd(Context context, com.google.android.gms.ads.mediation.MediationBannerListener bannerListener, Bundle serverParameters, AdSize adSize, com.google.android.gms.ads.mediation.MediationAdRequest mediationAdRequest, Bundle extras) {
        this.zzaK = new AdView(context);
        this.zzaK.setAdSize(new AdSize(adSize.getWidth(), adSize.getHeight()));
        this.zzaK.setAdUnitId(getAdUnitId(serverParameters));
        this.zzaK.setAdListener(new zzc(this, bannerListener));
        this.zzaK.loadAd(zza(context, mediationAdRequest, extras, serverParameters));
    }

    @Override // com.google.android.gms.ads.mediation.MediationInterstitialAdapter
    public void requestInterstitialAd(Context context, com.google.android.gms.ads.mediation.MediationInterstitialListener interstitialListener, Bundle serverParameters, com.google.android.gms.ads.mediation.MediationAdRequest mediationAdRequest, Bundle extras) {
        this.zzaL = new InterstitialAd(context);
        this.zzaL.setAdUnitId(getAdUnitId(serverParameters));
        this.zzaL.setAdListener(new zzd(this, interstitialListener));
        this.zzaL.loadAd(zza(context, mediationAdRequest, extras, serverParameters));
    }

    @Override // com.google.android.gms.ads.mediation.MediationNativeAdapter
    public void requestNativeAd(Context context, MediationNativeListener listener, Bundle serverParameters, NativeMediationAdRequest mediationAdRequest, Bundle extras) {
        zze zzeVar = new zze(this, listener);
        AdLoader.Builder builderWithAdListener = zza(context, serverParameters.getString(AD_UNIT_ID_PARAMETER)).withAdListener(zzeVar);
        NativeAdOptions nativeAdOptions = mediationAdRequest.getNativeAdOptions();
        if (nativeAdOptions != null) {
            builderWithAdListener.withNativeAdOptions(nativeAdOptions);
        }
        if (mediationAdRequest.isAppInstallAdRequested()) {
            builderWithAdListener.forAppInstallAd(zzeVar);
        }
        if (mediationAdRequest.isContentAdRequested()) {
            builderWithAdListener.forContentAd(zzeVar);
        }
        this.zzaM = builderWithAdListener.build();
        this.zzaM.loadAd(zza(context, mediationAdRequest, extras, serverParameters));
    }

    @Override // com.google.android.gms.ads.mediation.MediationInterstitialAdapter
    public void showInterstitial() {
        this.zzaL.show();
    }

    protected abstract Bundle zza(Bundle bundle, Bundle bundle2);

    AdLoader.Builder zza(Context context, String str) {
        return new AdLoader.Builder(context, str);
    }

    AdRequest zza(Context context, com.google.android.gms.ads.mediation.MediationAdRequest mediationAdRequest, Bundle bundle, Bundle bundle2) {
        AdRequest.Builder builder = new AdRequest.Builder();
        Date birthday = mediationAdRequest.getBirthday();
        if (birthday != null) {
            builder.setBirthday(birthday);
        }
        int gender = mediationAdRequest.getGender();
        if (gender != 0) {
            builder.setGender(gender);
        }
        Set<String> keywords = mediationAdRequest.getKeywords();
        if (keywords != null) {
            Iterator<String> it = keywords.iterator();
            while (it.hasNext()) {
                builder.addKeyword(it.next());
            }
        }
        Location location = mediationAdRequest.getLocation();
        if (location != null) {
            builder.setLocation(location);
        }
        if (mediationAdRequest.isTesting()) {
            builder.addTestDevice(zzl.zzcF().zzQ(context));
        }
        if (mediationAdRequest.taggedForChildDirectedTreatment() != -1) {
            builder.tagForChildDirectedTreatment(mediationAdRequest.taggedForChildDirectedTreatment() == 1);
        }
        builder.addNetworkExtrasBundle(AdMobAdapter.class, zza(bundle, bundle2));
        return builder.build();
    }
}
