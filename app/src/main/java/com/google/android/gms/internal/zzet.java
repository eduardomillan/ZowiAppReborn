package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.MediationBannerAdapter;
import com.google.android.gms.ads.mediation.MediationInterstitialAdapter;
import com.google.android.gms.ads.mediation.MediationNativeAdapter;
import com.google.android.gms.ads.mediation.NativeAdMapper;
import com.google.android.gms.ads.mediation.NativeAppInstallAdMapper;
import com.google.android.gms.ads.mediation.NativeContentAdMapper;
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdAdapter;
import com.google.android.gms.internal.zzen;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzet extends zzen.zza {
    private final MediationAdapter zzzJ;
    private zzeu zzzK;

    public zzet(MediationAdapter mediationAdapter) {
        this.zzzJ = mediationAdapter;
    }

    private Bundle zza(String str, int i, String str2) throws RemoteException {
        com.google.android.gms.ads.internal.util.client.zzb.zzaH("Server parameters: " + str);
        try {
            Bundle bundle = new Bundle();
            if (str != null) {
                JSONObject jSONObject = new JSONObject(str);
                Bundle bundle2 = new Bundle();
                Iterator<String> itKeys = jSONObject.keys();
                while (itKeys.hasNext()) {
                    String next = itKeys.next();
                    bundle2.putString(next, jSONObject.getString(next));
                }
                bundle = bundle2;
            }
            if (this.zzzJ instanceof AdMobAdapter) {
                bundle.putString("adJson", str2);
                bundle.putInt("tagForChildDirectedTreatment", i);
            }
            return bundle;
        } catch (Throwable th) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not get Server Parameters Bundle.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.zzen
    public void destroy() throws RemoteException {
        try {
            this.zzzJ.onDestroy();
        } catch (Throwable th) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not destroy adapter.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.zzen
    public com.google.android.gms.dynamic.zzd getView() throws RemoteException {
        if (!(this.zzzJ instanceof MediationBannerAdapter)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("MediationAdapter is not a MediationBannerAdapter: " + this.zzzJ.getClass().getCanonicalName());
            throw new RemoteException();
        }
        try {
            return com.google.android.gms.dynamic.zze.zzy(((MediationBannerAdapter) this.zzzJ).getBannerView());
        } catch (Throwable th) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not get banner view from adapter.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.zzen
    public boolean isInitialized() throws RemoteException {
        if (!(this.zzzJ instanceof MediationRewardedVideoAdAdapter)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("MediationAdapter is not a MediationRewardedVideoAdAdapter: " + this.zzzJ.getClass().getCanonicalName());
            throw new RemoteException();
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Check if adapter is initialized.");
        try {
            return ((MediationRewardedVideoAdAdapter) this.zzzJ).isInitialized();
        } catch (Throwable th) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not check if adapter is initialized.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.zzen
    public void pause() throws RemoteException {
        try {
            this.zzzJ.onPause();
        } catch (Throwable th) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not pause adapter.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.zzen
    public void resume() throws RemoteException {
        try {
            this.zzzJ.onResume();
        } catch (Throwable th) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not resume adapter.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.zzen
    public void showInterstitial() throws RemoteException {
        if (!(this.zzzJ instanceof MediationInterstitialAdapter)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("MediationAdapter is not a MediationInterstitialAdapter: " + this.zzzJ.getClass().getCanonicalName());
            throw new RemoteException();
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Showing interstitial from adapter.");
        try {
            ((MediationInterstitialAdapter) this.zzzJ).showInterstitial();
        } catch (Throwable th) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not show interstitial from adapter.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.zzen
    public void showVideo() throws RemoteException {
        if (!(this.zzzJ instanceof MediationRewardedVideoAdAdapter)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("MediationAdapter is not a MediationRewardedVideoAdAdapter: " + this.zzzJ.getClass().getCanonicalName());
            throw new RemoteException();
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Show rewarded video ad from adapter.");
        try {
            ((MediationRewardedVideoAdAdapter) this.zzzJ).showVideo();
        } catch (Throwable th) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not show rewarded video ad from adapter.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.zzen
    public void zza(AdRequestParcel adRequestParcel, String str) throws RemoteException {
        if (!(this.zzzJ instanceof MediationRewardedVideoAdAdapter)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("MediationAdapter is not a MediationRewardedVideoAdAdapter: " + this.zzzJ.getClass().getCanonicalName());
            throw new RemoteException();
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Requesting rewarded video ad from adapter.");
        try {
            MediationRewardedVideoAdAdapter mediationRewardedVideoAdAdapter = (MediationRewardedVideoAdAdapter) this.zzzJ;
            mediationRewardedVideoAdAdapter.loadAd(new zzes(adRequestParcel.zzsB == -1 ? null : new Date(adRequestParcel.zzsB), adRequestParcel.zzsC, adRequestParcel.zzsD != null ? new HashSet(adRequestParcel.zzsD) : null, adRequestParcel.zzsJ, adRequestParcel.zzsE, adRequestParcel.zzsF), zza(str, adRequestParcel.zzsF, null), adRequestParcel.zzsL != null ? adRequestParcel.zzsL.getBundle(mediationRewardedVideoAdAdapter.getClass().getName()) : null);
        } catch (Throwable th) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not load rewarded video ad from adapter.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.zzen
    public void zza(com.google.android.gms.dynamic.zzd zzdVar, AdRequestParcel adRequestParcel, String str, com.google.android.gms.ads.internal.reward.mediation.client.zza zzaVar, String str2) throws RemoteException {
        if (!(this.zzzJ instanceof MediationRewardedVideoAdAdapter)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("MediationAdapter is not a MediationRewardedVideoAdAdapter: " + this.zzzJ.getClass().getCanonicalName());
            throw new RemoteException();
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Initialize rewarded video adapter.");
        try {
            MediationRewardedVideoAdAdapter mediationRewardedVideoAdAdapter = (MediationRewardedVideoAdAdapter) this.zzzJ;
            mediationRewardedVideoAdAdapter.initialize((Context) com.google.android.gms.dynamic.zze.zzp(zzdVar), new zzes(adRequestParcel.zzsB == -1 ? null : new Date(adRequestParcel.zzsB), adRequestParcel.zzsC, adRequestParcel.zzsD != null ? new HashSet(adRequestParcel.zzsD) : null, adRequestParcel.zzsJ, adRequestParcel.zzsE, adRequestParcel.zzsF), str, new com.google.android.gms.ads.internal.reward.mediation.client.zzb(zzaVar), zza(str2, adRequestParcel.zzsF, null), adRequestParcel.zzsL != null ? adRequestParcel.zzsL.getBundle(mediationRewardedVideoAdAdapter.getClass().getName()) : null);
        } catch (Throwable th) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not initialize rewarded video adapter.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.zzen
    public void zza(com.google.android.gms.dynamic.zzd zzdVar, AdRequestParcel adRequestParcel, String str, zzeo zzeoVar) throws RemoteException {
        zza(zzdVar, adRequestParcel, str, (String) null, zzeoVar);
    }

    @Override // com.google.android.gms.internal.zzen
    public void zza(com.google.android.gms.dynamic.zzd zzdVar, AdRequestParcel adRequestParcel, String str, String str2, zzeo zzeoVar) throws RemoteException {
        if (!(this.zzzJ instanceof MediationInterstitialAdapter)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("MediationAdapter is not a MediationInterstitialAdapter: " + this.zzzJ.getClass().getCanonicalName());
            throw new RemoteException();
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Requesting interstitial ad from adapter.");
        try {
            MediationInterstitialAdapter mediationInterstitialAdapter = (MediationInterstitialAdapter) this.zzzJ;
            mediationInterstitialAdapter.requestInterstitialAd((Context) com.google.android.gms.dynamic.zze.zzp(zzdVar), new zzeu(zzeoVar), zza(str, adRequestParcel.zzsF, str2), new zzes(adRequestParcel.zzsB == -1 ? null : new Date(adRequestParcel.zzsB), adRequestParcel.zzsC, adRequestParcel.zzsD != null ? new HashSet(adRequestParcel.zzsD) : null, adRequestParcel.zzsJ, adRequestParcel.zzsE, adRequestParcel.zzsF), adRequestParcel.zzsL != null ? adRequestParcel.zzsL.getBundle(mediationInterstitialAdapter.getClass().getName()) : null);
        } catch (Throwable th) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not request interstitial ad from adapter.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.zzen
    public void zza(com.google.android.gms.dynamic.zzd zzdVar, AdRequestParcel adRequestParcel, String str, String str2, zzeo zzeoVar, NativeAdOptionsParcel nativeAdOptionsParcel, List<String> list) throws RemoteException {
        if (!(this.zzzJ instanceof MediationNativeAdapter)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("MediationAdapter is not a MediationNativeAdapter: " + this.zzzJ.getClass().getCanonicalName());
            throw new RemoteException();
        }
        try {
            MediationNativeAdapter mediationNativeAdapter = (MediationNativeAdapter) this.zzzJ;
            zzex zzexVar = new zzex(adRequestParcel.zzsB == -1 ? null : new Date(adRequestParcel.zzsB), adRequestParcel.zzsC, adRequestParcel.zzsD != null ? new HashSet(adRequestParcel.zzsD) : null, adRequestParcel.zzsJ, adRequestParcel.zzsE, adRequestParcel.zzsF, nativeAdOptionsParcel, list);
            Bundle bundle = adRequestParcel.zzsL != null ? adRequestParcel.zzsL.getBundle(mediationNativeAdapter.getClass().getName()) : null;
            this.zzzK = new zzeu(zzeoVar);
            mediationNativeAdapter.requestNativeAd((Context) com.google.android.gms.dynamic.zze.zzp(zzdVar), this.zzzK, zza(str, adRequestParcel.zzsF, str2), zzexVar, bundle);
        } catch (Throwable th) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not request interstitial ad from adapter.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.zzen
    public void zza(com.google.android.gms.dynamic.zzd zzdVar, AdSizeParcel adSizeParcel, AdRequestParcel adRequestParcel, String str, zzeo zzeoVar) throws RemoteException {
        zza(zzdVar, adSizeParcel, adRequestParcel, str, null, zzeoVar);
    }

    @Override // com.google.android.gms.internal.zzen
    public void zza(com.google.android.gms.dynamic.zzd zzdVar, AdSizeParcel adSizeParcel, AdRequestParcel adRequestParcel, String str, String str2, zzeo zzeoVar) throws RemoteException {
        if (!(this.zzzJ instanceof MediationBannerAdapter)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("MediationAdapter is not a MediationBannerAdapter: " + this.zzzJ.getClass().getCanonicalName());
            throw new RemoteException();
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Requesting banner ad from adapter.");
        try {
            MediationBannerAdapter mediationBannerAdapter = (MediationBannerAdapter) this.zzzJ;
            mediationBannerAdapter.requestBannerAd((Context) com.google.android.gms.dynamic.zze.zzp(zzdVar), new zzeu(zzeoVar), zza(str, adRequestParcel.zzsF, str2), com.google.android.gms.ads.zza.zza(adSizeParcel.width, adSizeParcel.height, adSizeParcel.zzte), new zzes(adRequestParcel.zzsB == -1 ? null : new Date(adRequestParcel.zzsB), adRequestParcel.zzsC, adRequestParcel.zzsD != null ? new HashSet(adRequestParcel.zzsD) : null, adRequestParcel.zzsJ, adRequestParcel.zzsE, adRequestParcel.zzsF), adRequestParcel.zzsL != null ? adRequestParcel.zzsL.getBundle(mediationBannerAdapter.getClass().getName()) : null);
        } catch (Throwable th) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not request banner ad from adapter.", th);
            throw new RemoteException();
        }
    }

    @Override // com.google.android.gms.internal.zzen
    public zzeq zzdV() {
        NativeAdMapper nativeAdMapperZzeb = this.zzzK.zzeb();
        if (nativeAdMapperZzeb instanceof NativeAppInstallAdMapper) {
            return new zzev((NativeAppInstallAdMapper) nativeAdMapperZzeb);
        }
        return null;
    }

    @Override // com.google.android.gms.internal.zzen
    public zzer zzdW() {
        NativeAdMapper nativeAdMapperZzeb = this.zzzK.zzeb();
        if (nativeAdMapperZzeb instanceof NativeContentAdMapper) {
            return new zzew((NativeContentAdMapper) nativeAdMapperZzeb);
        }
        return null;
    }

    @Override // com.google.android.gms.internal.zzen
    public Bundle zzdX() {
        if (this.zzzJ instanceof zzjj) {
            return ((zzjj) this.zzzJ).zzdX();
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaH("MediationAdapter is not a v2 MediationBannerAdapter: " + this.zzzJ.getClass().getCanonicalName());
        return new Bundle();
    }

    @Override // com.google.android.gms.internal.zzen
    public Bundle zzdY() {
        if (this.zzzJ instanceof zzjk) {
            return ((zzjk) this.zzzJ).zzdY();
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaH("MediationAdapter is not a v2 MediationInterstitialAdapter: " + this.zzzJ.getClass().getCanonicalName());
        return new Bundle();
    }

    @Override // com.google.android.gms.internal.zzen
    public Bundle zzdZ() {
        return new Bundle();
    }
}
