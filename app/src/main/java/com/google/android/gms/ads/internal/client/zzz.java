package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.AppEventListener;
import com.google.android.gms.ads.doubleclick.OnCustomRenderedAdLoadedListener;
import com.google.android.gms.ads.purchase.InAppPurchaseListener;
import com.google.android.gms.ads.purchase.PlayStorePurchaseListener;
import com.google.android.gms.internal.zzcl;
import com.google.android.gms.internal.zzel;
import com.google.android.gms.internal.zzfx;
import com.google.android.gms.internal.zzgb;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public class zzz {
    private final zzh zznL;
    private boolean zzoN;
    private String zzpa;
    private zza zzsy;
    private AdListener zzsz;
    private final zzel zztD;
    private final AtomicBoolean zztE;
    private zzs zztF;
    private String zztG;
    private ViewGroup zztH;
    private InAppPurchaseListener zztI;
    private PlayStorePurchaseListener zztJ;
    private OnCustomRenderedAdLoadedListener zztK;
    private AppEventListener zztj;
    private AdSize[] zztk;

    public zzz(ViewGroup viewGroup) {
        this(viewGroup, null, false, zzh.zzcB());
    }

    public zzz(ViewGroup viewGroup, AttributeSet attributeSet, boolean z) {
        this(viewGroup, attributeSet, z, zzh.zzcB());
    }

    zzz(ViewGroup viewGroup, AttributeSet attributeSet, boolean z, zzh zzhVar) {
        this(viewGroup, attributeSet, z, zzhVar, null);
    }

    zzz(ViewGroup viewGroup, AttributeSet attributeSet, boolean z, zzh zzhVar, zzs zzsVar) {
        this.zztD = new zzel();
        this.zztH = viewGroup;
        this.zznL = zzhVar;
        this.zztF = zzsVar;
        this.zztE = new AtomicBoolean(false);
        if (attributeSet != null) {
            Context context = viewGroup.getContext();
            try {
                zzk zzkVar = new zzk(context, attributeSet);
                this.zztk = zzkVar.zzi(z);
                this.zzpa = zzkVar.getAdUnitId();
                if (viewGroup.isInEditMode()) {
                    zzl.zzcF().zza(viewGroup, new AdSizeParcel(context, this.zztk[0]), "Ads by Google");
                }
            } catch (IllegalArgumentException e) {
                zzl.zzcF().zza(viewGroup, new AdSizeParcel(context, AdSize.BANNER), e.getMessage(), e.getMessage());
            }
        }
    }

    private void zzcS() {
        try {
            com.google.android.gms.dynamic.zzd zzdVarZzaM = this.zztF.zzaM();
            if (zzdVarZzaM == null) {
                return;
            }
            this.zztH.addView((View) com.google.android.gms.dynamic.zze.zzp(zzdVarZzaM));
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to get an ad frame.", e);
        }
    }

    public void destroy() {
        try {
            if (this.zztF != null) {
                this.zztF.destroy();
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to destroy AdView.", e);
        }
    }

    public AdListener getAdListener() {
        return this.zzsz;
    }

    public AdSize getAdSize() {
        AdSizeParcel adSizeParcelZzaN;
        try {
            if (this.zztF != null && (adSizeParcelZzaN = this.zztF.zzaN()) != null) {
                return adSizeParcelZzaN.zzcD();
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to get the current AdSize.", e);
        }
        if (this.zztk != null) {
            return this.zztk[0];
        }
        return null;
    }

    public AdSize[] getAdSizes() {
        return this.zztk;
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

    public boolean isLoading() {
        try {
            if (this.zztF != null) {
                return this.zztF.isLoading();
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to check if ad is loading.", e);
        }
        return false;
    }

    public void pause() {
        try {
            if (this.zztF != null) {
                this.zztF.pause();
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to call pause.", e);
        }
    }

    public void recordManualImpression() {
        if (this.zztE.getAndSet(true)) {
            return;
        }
        try {
            if (this.zztF != null) {
                this.zztF.zzaP();
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to record impression.", e);
        }
    }

    public void resume() {
        try {
            if (this.zztF != null) {
                this.zztF.resume();
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to call resume.", e);
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

    public void setAdSizes(AdSize... adSizes) {
        if (this.zztk != null) {
            throw new IllegalStateException("The ad size can only be set once on AdView.");
        }
        zza(adSizes);
    }

    public void setAdUnitId(String adUnitId) {
        if (this.zzpa != null) {
            throw new IllegalStateException("The ad unit ID can only be set once on AdView.");
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

    public void setManualImpressionsEnabled(boolean manualImpressionsEnabled) {
        this.zzoN = manualImpressionsEnabled;
        try {
            if (this.zztF != null) {
                this.zztF.setManualImpressionsEnabled(this.zzoN);
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to set manual impressions.", e);
        }
    }

    public void setOnCustomRenderedAdLoadedListener(OnCustomRenderedAdLoadedListener onCustomRenderedAdLoadedListener) {
        this.zztK = onCustomRenderedAdLoadedListener;
        try {
            if (this.zztF != null) {
                this.zztF.zza(onCustomRenderedAdLoadedListener != null ? new zzcl(onCustomRenderedAdLoadedListener) : null);
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to set the onCustomRenderedAdLoadedListener.", e);
        }
    }

    public void setPlayStorePurchaseParams(PlayStorePurchaseListener playStorePurchaseListener, String publicKey) {
        if (this.zztI != null) {
            throw new IllegalStateException("InAppPurchaseListener has already been set.");
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
                zzcT();
            }
            if (this.zztF.zzb(this.zznL.zza(this.zztH.getContext(), zzyVar))) {
                this.zztD.zze(zzyVar.zzcO());
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to load ad.", e);
        }
    }

    public void zza(AdSize... adSizeArr) {
        this.zztk = adSizeArr;
        try {
            if (this.zztF != null) {
                this.zztF.zza(new AdSizeParcel(this.zztH.getContext(), this.zztk));
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to set the ad size.", e);
        }
        this.zztH.requestLayout();
    }

    void zzcT() throws RemoteException {
        if ((this.zztk == null || this.zzpa == null) && this.zztF == null) {
            throw new IllegalStateException("The ad size and ad unit ID must be set before loadAd is called.");
        }
        this.zztF = zzcU();
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
        this.zztF.zza(zzl.zzcH());
        this.zztF.setManualImpressionsEnabled(this.zzoN);
        zzcS();
    }

    protected zzs zzcU() throws RemoteException {
        Context context = this.zztH.getContext();
        return zzl.zzcG().zza(context, new AdSizeParcel(context, this.zztk), this.zzpa, this.zztD);
    }
}
