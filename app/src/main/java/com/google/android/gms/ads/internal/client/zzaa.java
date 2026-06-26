package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.doubleclick.AppEventListener;
import com.google.android.gms.ads.doubleclick.OnCustomRenderedAdLoadedListener;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;
import com.google.android.gms.ads.purchase.InAppPurchaseListener;
import com.google.android.gms.ads.purchase.PlayStorePurchaseListener;
import com.google.android.gms.internal.zzcl;
import com.google.android.gms.internal.zzel;
import com.google.android.gms.internal.zzfx;
import com.google.android.gms.internal.zzgb;

/* JADX INFO: loaded from: classes.dex */
public class zzaa {
    private final Context mContext;
    private final zzh zznL;
    private String zzpa;
    private zza zzsy;
    private AdListener zzsz;
    private final zzel zztD;
    private zzs zztF;
    private String zztG;
    private InAppPurchaseListener zztI;
    private PlayStorePurchaseListener zztJ;
    private OnCustomRenderedAdLoadedListener zztK;
    private PublisherInterstitialAd zztL;
    private AppEventListener zztj;

    public zzaa(Context context) {
        this(context, zzh.zzcB(), null);
    }

    public zzaa(Context context, PublisherInterstitialAd publisherInterstitialAd) {
        this(context, zzh.zzcB(), publisherInterstitialAd);
    }

    public zzaa(Context context, zzh zzhVar, PublisherInterstitialAd publisherInterstitialAd) {
        this.zztD = new zzel();
        this.mContext = context;
        this.zznL = zzhVar;
        this.zztL = publisherInterstitialAd;
    }

    private void zzM(String str) throws RemoteException {
        if (this.zzpa == null) {
            zzN(str);
        }
        this.zztF = zzl.zzcG().zzb(this.mContext, new AdSizeParcel(), this.zzpa, this.zztD);
        if (this.zzsz != null) {
            this.zztF.zza(new zzc(this.zzsz));
        }
        if (this.zzsy != null) {
            this.zztF.zza(new zzb(this.zzsy));
        }
        if (this.zztj != null) {
            this.zztF.zza(new zzj(this.zztj));
        }
        if (this.zztI != null) {
            this.zztF.zza(new zzfx(this.zztI));
        }
        if (this.zztJ != null) {
            this.zztF.zza(new zzgb(this.zztJ), this.zztG);
        }
        if (this.zztK != null) {
            this.zztF.zza(new zzcl(this.zztK));
        }
    }

    private void zzN(String str) {
        if (this.zztF == null) {
            throw new IllegalStateException("The ad unit ID must be set on InterstitialAd before " + str + " is called.");
        }
    }

    public AdListener getAdListener() {
        return this.zzsz;
    }

    public String getAdUnitId() {
        return this.zzpa;
    }

    public AppEventListener getAppEventListener() {
        return this.zztj;
    }

    public InAppPurchaseListener getInAppPurchaseListener() {
        return this.zztI;
    }

    public String getMediationAdapterClassName() {
        try {
            if (this.zztF != null) {
                return this.zztF.getMediationAdapterClassName();
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to get the mediation adapter class name.", e);
        }
        return null;
    }

    public OnCustomRenderedAdLoadedListener getOnCustomRenderedAdLoadedListener() {
        return this.zztK;
    }

    public boolean isLoaded() {
        try {
            if (this.zztF == null) {
                return false;
            }
            return this.zztF.isReady();
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to check if ad is ready.", e);
            return false;
        }
    }

    public boolean isLoading() {
        try {
            if (this.zztF == null) {
                return false;
            }
            return this.zztF.isLoading();
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to check if ad is loading.", e);
            return false;
        }
    }

    public void setAdListener(AdListener adListener) {
        try {
            this.zzsz = adListener;
            if (this.zztF != null) {
                this.zztF.zza(adListener != null ? new zzc(adListener) : null);
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to set the AdListener.", e);
        }
    }

    public void setAdUnitId(String adUnitId) {
        if (this.zzpa != null) {
            throw new IllegalStateException("The ad unit ID can only be set once on InterstitialAd.");
        }
        this.zzpa = adUnitId;
    }

    public void setAppEventListener(AppEventListener appEventListener) {
        try {
            this.zztj = appEventListener;
            if (this.zztF != null) {
                this.zztF.zza(appEventListener != null ? new zzj(appEventListener) : null);
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to set the AppEventListener.", e);
        }
    }

    public void setInAppPurchaseListener(InAppPurchaseListener inAppPurchaseListener) {
        if (this.zztJ != null) {
            throw new IllegalStateException("Play store purchase parameter has already been set.");
        }
        try {
            this.zztI = inAppPurchaseListener;
            if (this.zztF != null) {
                this.zztF.zza(inAppPurchaseListener != null ? new zzfx(inAppPurchaseListener) : null);
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to set the InAppPurchaseListener.", e);
        }
    }

    public void setOnCustomRenderedAdLoadedListener(OnCustomRenderedAdLoadedListener onCustomRenderedAdLoadedListener) {
        try {
            this.zztK = onCustomRenderedAdLoadedListener;
            if (this.zztF != null) {
                this.zztF.zza(onCustomRenderedAdLoadedListener != null ? new zzcl(onCustomRenderedAdLoadedListener) : null);
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to set the OnCustomRenderedAdLoadedListener.", e);
        }
    }

    public void setPlayStorePurchaseParams(PlayStorePurchaseListener playStorePurchaseListener, String publicKey) {
        if (this.zztI != null) {
            throw new IllegalStateException("In app purchase parameter has already been set.");
        }
        try {
            this.zztJ = playStorePurchaseListener;
            this.zztG = publicKey;
            if (this.zztF != null) {
                this.zztF.zza(playStorePurchaseListener != null ? new zzgb(playStorePurchaseListener) : null, publicKey);
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to set the play store purchase parameter.", e);
        }
    }

    public void show() {
        try {
            zzN("show");
            this.zztF.showInterstitial();
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to show interstitial.", e);
        }
    }

    public void zza(zza zzaVar) {
        try {
            this.zzsy = zzaVar;
            if (this.zztF != null) {
                this.zztF.zza(zzaVar != null ? new zzb(zzaVar) : null);
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to set the AdClickListener.", e);
        }
    }

    public void zza(zzy zzyVar) {
        try {
            if (this.zztF == null) {
                zzM("loadAd");
            }
            if (this.zztF.zzb(this.zznL.zza(this.mContext, zzyVar))) {
                this.zztD.zze(zzyVar.zzcO());
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to load ad.", e);
        }
    }
}
