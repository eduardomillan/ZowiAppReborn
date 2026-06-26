package com.google.android.gms.ads.internal;

import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.View;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.ThinAdSizeParcel;
import com.google.android.gms.ads.internal.client.zzs;
import com.google.android.gms.ads.internal.client.zzu;
import com.google.android.gms.ads.internal.client.zzv;
import com.google.android.gms.ads.internal.request.zza;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzay;
import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbk;
import com.google.android.gms.internal.zzby;
import com.google.android.gms.internal.zzce;
import com.google.android.gms.internal.zzcg;
import com.google.android.gms.internal.zzck;
import com.google.android.gms.internal.zzdg;
import com.google.android.gms.internal.zzfs;
import com.google.android.gms.internal.zzfw;
import com.google.android.gms.internal.zzgg;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzhs;
import com.google.android.gms.internal.zzht;
import com.google.android.gms.internal.zzhw;
import com.google.android.gms.internal.zzhx;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public abstract class zza extends zzs.zza implements com.google.android.gms.ads.internal.client.zza, com.google.android.gms.ads.internal.overlay.zzn, zza.InterfaceC0018zza, zzdg, zzgg.zza, zzhw {
    protected zzcg zzoo;
    protected zzce zzop;
    protected zzce zzoq;
    boolean zzor = false;
    protected final zzo zzos;
    protected final zzq zzot;
    protected transient AdRequestParcel zzou;
    protected final zzay zzov;
    protected final zzd zzow;

    zza(zzq zzqVar, zzo zzoVar, zzd zzdVar) {
        this.zzot = zzqVar;
        this.zzos = zzoVar == null ? new zzo(this) : zzoVar;
        this.zzow = zzdVar;
        zzp.zzbv().zzI(this.zzot.context);
        zzp.zzby().zzb(this.zzot.context, this.zzot.zzqj);
        this.zzov = zzp.zzby().zzgt();
    }

    private AdRequestParcel zza(AdRequestParcel adRequestParcel) {
        return (!GooglePlayServicesUtil.zzag(this.zzot.context) || adRequestParcel.zzsJ == null) ? adRequestParcel : new com.google.android.gms.ads.internal.client.zzf(adRequestParcel).zza(null).zzcA();
    }

    private boolean zzaR() {
        com.google.android.gms.ads.internal.util.client.zzb.zzaG("Ad leaving application.");
        if (this.zzot.zzqs == null) {
            return false;
        }
        try {
            this.zzot.zzqs.onAdLeftApplication();
            return true;
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not call AdListener.onAdLeftApplication().", e);
            return false;
        }
    }

    @Override // com.google.android.gms.ads.internal.client.zzs
    public void destroy() {
        zzx.zzci("destroy must be called on the main UI thread.");
        this.zzos.cancel();
        this.zzov.zzf(this.zzot.zzqo);
        this.zzot.destroy();
    }

    @Override // com.google.android.gms.ads.internal.client.zzs
    public boolean isLoading() {
        return this.zzor;
    }

    @Override // com.google.android.gms.ads.internal.client.zzs
    public boolean isReady() {
        zzx.zzci("isLoaded must be called on the main UI thread.");
        return this.zzot.zzql == null && this.zzot.zzqm == null && this.zzot.zzqo != null;
    }

    @Override // com.google.android.gms.ads.internal.client.zza
    public void onAdClicked() {
        if (this.zzot.zzqo == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Ad state was null when trying to ping click URLs.");
            return;
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Pinging click URLs.");
        this.zzot.zzqq.zzgg();
        if (this.zzot.zzqo.zzyY != null) {
            zzp.zzbv().zza(this.zzot.context, this.zzot.zzqj.zzJu, this.zzot.zzqo.zzyY);
        }
        if (this.zzot.zzqr != null) {
            try {
                this.zzot.zzqr.onAdClicked();
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not notify onAdClicked event.", e);
            }
        }
    }

    @Override // com.google.android.gms.internal.zzdg
    public void onAppEvent(String name, String info) {
        if (this.zzot.zzqt != null) {
            try {
                this.zzot.zzqt.onAppEvent(name, info);
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not call the AppEventListener.", e);
            }
        }
    }

    @Override // com.google.android.gms.ads.internal.client.zzs
    public void pause() {
        zzx.zzci("pause must be called on the main UI thread.");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void recordImpression() {
        zzc(this.zzot.zzqo);
    }

    @Override // com.google.android.gms.ads.internal.client.zzs
    public void resume() {
        zzx.zzci("resume must be called on the main UI thread.");
    }

    @Override // com.google.android.gms.ads.internal.client.zzs
    public void setManualImpressionsEnabled(boolean enabled) {
        throw new UnsupportedOperationException("Attempt to call setManualImpressionsEnabled for an unsupported ad type.");
    }

    @Override // com.google.android.gms.ads.internal.client.zzs
    public void stopLoading() {
        zzx.zzci("stopLoading must be called on the main UI thread.");
        this.zzor = false;
        this.zzot.zzf(true);
    }

    Bundle zza(zzbk zzbkVar) {
        String strZzcm;
        if (zzbkVar == null) {
            return null;
        }
        if (zzbkVar.zzcx()) {
            zzbkVar.wakeup();
        }
        zzbh zzbhVarZzcv = zzbkVar.zzcv();
        if (zzbhVarZzcv != null) {
            strZzcm = zzbhVarZzcv.zzcm();
            com.google.android.gms.ads.internal.util.client.zzb.zzaF("In AdManger: loadAd, " + zzbhVarZzcv.toString());
        } else {
            strZzcm = null;
        }
        if (strZzcm == null) {
            return null;
        }
        Bundle bundle = new Bundle(1);
        bundle.putString("fingerprint", strZzcm);
        bundle.putInt("v", 1);
        return bundle;
    }

    @Override // com.google.android.gms.ads.internal.client.zzs
    public void zza(AdSizeParcel adSizeParcel) {
        zzx.zzci("setAdSize must be called on the main UI thread.");
        this.zzot.zzqn = adSizeParcel;
        if (this.zzot.zzqo != null && this.zzot.zzqo.zzBD != null && this.zzot.zzqH == 0) {
            this.zzot.zzqo.zzBD.zza(adSizeParcel);
        }
        if (this.zzot.zzqk == null) {
            return;
        }
        if (this.zzot.zzqk.getChildCount() > 1) {
            this.zzot.zzqk.removeView(this.zzot.zzqk.getNextView());
        }
        this.zzot.zzqk.setMinimumWidth(adSizeParcel.widthPixels);
        this.zzot.zzqk.setMinimumHeight(adSizeParcel.heightPixels);
        this.zzot.zzqk.requestLayout();
    }

    @Override // com.google.android.gms.ads.internal.client.zzs
    public void zza(com.google.android.gms.ads.internal.client.zzn zznVar) {
        zzx.zzci("setAdListener must be called on the main UI thread.");
        this.zzot.zzqr = zznVar;
    }

    @Override // com.google.android.gms.ads.internal.client.zzs
    public void zza(com.google.android.gms.ads.internal.client.zzo zzoVar) {
        zzx.zzci("setAdListener must be called on the main UI thread.");
        this.zzot.zzqs = zzoVar;
    }

    @Override // com.google.android.gms.ads.internal.client.zzs
    public void zza(zzu zzuVar) {
        zzx.zzci("setAppEventListener must be called on the main UI thread.");
        this.zzot.zzqt = zzuVar;
    }

    @Override // com.google.android.gms.ads.internal.client.zzs
    public void zza(zzv zzvVar) {
        zzx.zzci("setCorrelationIdProvider must be called on the main UI thread");
        this.zzot.zzqu = zzvVar;
    }

    @Override // com.google.android.gms.ads.internal.client.zzs
    public void zza(zzck zzckVar) {
        throw new IllegalStateException("setOnCustomRenderedAdLoadedListener is not supported for current ad type");
    }

    @Override // com.google.android.gms.ads.internal.client.zzs
    public void zza(zzfs zzfsVar) {
        throw new IllegalStateException("setInAppPurchaseListener is not supported for current ad type");
    }

    @Override // com.google.android.gms.ads.internal.client.zzs
    public void zza(zzfw zzfwVar, String str) {
        throw new IllegalStateException("setPlayStorePurchaseParams is not supported for current ad type");
    }

    @Override // com.google.android.gms.ads.internal.request.zza.InterfaceC0018zza
    public void zza(zzhs.zza zzaVar) {
        if (zzaVar.zzHD.zzEO != -1 && !TextUtils.isEmpty(zzaVar.zzHD.zzEY)) {
            long jZzo = zzo(zzaVar.zzHD.zzEY);
            if (jZzo != -1) {
                this.zzoo.zza(this.zzoo.zzb(jZzo + zzaVar.zzHD.zzEO), "stc");
            }
        }
        this.zzoo.zzT(zzaVar.zzHD.zzEY);
        this.zzoo.zza(this.zzop, "arf");
        this.zzoq = this.zzoo.zzdn();
        this.zzoo.zze("gqi", zzaVar.zzHD.zzEZ);
        this.zzot.zzql = null;
        this.zzot.zzqp = zzaVar;
        zza(zzaVar, this.zzoo);
    }

    protected abstract void zza(zzhs.zza zzaVar, zzcg zzcgVar);

    @Override // com.google.android.gms.internal.zzhw
    public void zza(HashSet<zzht> hashSet) {
        this.zzot.zza(hashSet);
    }

    protected abstract boolean zza(AdRequestParcel adRequestParcel, zzcg zzcgVar);

    boolean zza(zzhs zzhsVar) {
        return false;
    }

    protected abstract boolean zza(zzhs zzhsVar, zzhs zzhsVar2);

    void zzaL() {
        this.zzoo = new zzcg(zzby.zzuQ.get().booleanValue(), "load_ad", this.zzot.zzqn.zzte);
        this.zzop = new zzce(-1L, null, null);
        this.zzoq = new zzce(-1L, null, null);
    }

    @Override // com.google.android.gms.ads.internal.client.zzs
    public com.google.android.gms.dynamic.zzd zzaM() {
        zzx.zzci("getAdFrame must be called on the main UI thread.");
        return com.google.android.gms.dynamic.zze.zzy(this.zzot.zzqk);
    }

    @Override // com.google.android.gms.ads.internal.client.zzs
    public AdSizeParcel zzaN() {
        zzx.zzci("getAdSize must be called on the main UI thread.");
        if (this.zzot.zzqn == null) {
            return null;
        }
        return new ThinAdSizeParcel(this.zzot.zzqn);
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzn
    public void zzaO() {
        zzaR();
    }

    @Override // com.google.android.gms.ads.internal.client.zzs
    public void zzaP() {
        zzx.zzci("recordManualImpression must be called on the main UI thread.");
        if (this.zzot.zzqo == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Ad state was null when trying to ping manual tracking URLs.");
            return;
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Pinging manual tracking URLs.");
        if (this.zzot.zzqo.zzEM != null) {
            zzp.zzbv().zza(this.zzot.context, this.zzot.zzqj.zzJu, this.zzot.zzqo.zzEM);
        }
    }

    protected boolean zzaQ() {
        com.google.android.gms.ads.internal.util.client.zzb.v("Ad closing.");
        if (this.zzot.zzqs == null) {
            return false;
        }
        try {
            this.zzot.zzqs.onAdClosed();
            return true;
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not call AdListener.onAdClosed().", e);
            return false;
        }
    }

    protected boolean zzaS() {
        com.google.android.gms.ads.internal.util.client.zzb.zzaG("Ad opening.");
        if (this.zzot.zzqs == null) {
            return false;
        }
        try {
            this.zzot.zzqs.onAdOpened();
            return true;
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not call AdListener.onAdOpened().", e);
            return false;
        }
    }

    protected boolean zzaT() {
        com.google.android.gms.ads.internal.util.client.zzb.zzaG("Ad finished loading.");
        this.zzor = false;
        if (this.zzot.zzqs == null) {
            return false;
        }
        try {
            this.zzot.zzqs.onAdLoaded();
            return true;
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not call AdListener.onAdLoaded().", e);
            return false;
        }
    }

    protected void zzb(View view) {
        this.zzot.zzqk.addView(view, zzp.zzbx().zzgJ());
    }

    @Override // com.google.android.gms.internal.zzgg.zza
    public void zzb(zzhs zzhsVar) {
        this.zzoo.zza(this.zzoq, "awr");
        this.zzot.zzqm = null;
        if (zzhsVar.errorCode != -2 && zzhsVar.errorCode != 3) {
            zzp.zzby().zzb(this.zzot.zzbJ());
        }
        if (zzhsVar.errorCode == -1) {
            this.zzor = false;
            return;
        }
        if (zza(zzhsVar)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaF("Ad refresh scheduled.");
        }
        if (zzhsVar.errorCode != -2) {
            zze(zzhsVar.errorCode);
            return;
        }
        if (this.zzot.zzqF == null) {
            this.zzot.zzqF = new zzhx(this.zzot.zzqh);
        }
        this.zzov.zze(this.zzot.zzqo);
        if (zza(this.zzot.zzqo, zzhsVar)) {
            this.zzot.zzqo = zzhsVar;
            this.zzot.zzbS();
            this.zzoo.zze("is_mraid", this.zzot.zzqo.zzbY() ? "1" : "0");
            this.zzoo.zze("is_mediation", this.zzot.zzqo.zzEK ? "1" : "0");
            if (this.zzot.zzqo.zzBD != null && this.zzot.zzqo.zzBD.zzhe() != null) {
                this.zzoo.zze("is_video", this.zzot.zzqo.zzBD.zzhe().zzhr() ? "1" : "0");
            }
            this.zzoo.zza(this.zzop, "ttc");
            if (zzp.zzby().zzgo() != null) {
                zzp.zzby().zzgo().zza(this.zzoo);
            }
            if (this.zzot.zzbN()) {
                zzaT();
            }
        }
    }

    @Override // com.google.android.gms.ads.internal.client.zzs
    public boolean zzb(AdRequestParcel adRequestParcel) {
        zzx.zzci("loadAd must be called on the main UI thread.");
        AdRequestParcel adRequestParcelZza = zza(adRequestParcel);
        if (this.zzor) {
            if (this.zzou != null) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Aborting last ad request since another ad request is already in progress. The current request object will still be cached for future refreshes.");
            }
            this.zzou = adRequestParcelZza;
            return false;
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaG("Starting ad request.");
        this.zzor = true;
        zzaL();
        this.zzop = this.zzoo.zzdn();
        if (!adRequestParcelZza.zzsE) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaG("Use AdRequest.Builder.addTestDevice(\"" + com.google.android.gms.ads.internal.client.zzl.zzcF().zzQ(this.zzot.context) + "\") to get test ads on this device.");
        }
        return zza(adRequestParcelZza, this.zzoo);
    }

    protected void zzc(zzhs zzhsVar) {
        if (zzhsVar == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Ad state was null when trying to ping impression URLs.");
            return;
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Pinging Impression URLs.");
        this.zzot.zzqq.zzgf();
        if (zzhsVar.zzyZ != null) {
            zzp.zzbv().zza(this.zzot.context, this.zzot.zzqj.zzJu, zzhsVar.zzyZ);
        }
    }

    protected boolean zzc(AdRequestParcel adRequestParcel) {
        Object parent = this.zzot.zzqk.getParent();
        return (parent instanceof View) && ((View) parent).isShown() && zzp.zzbv().zzgB();
    }

    public void zzd(AdRequestParcel adRequestParcel) {
        if (zzc(adRequestParcel)) {
            zzb(adRequestParcel);
        } else {
            com.google.android.gms.ads.internal.util.client.zzb.zzaG("Ad is not visible. Not refreshing ad.");
            this.zzos.zzg(adRequestParcel);
        }
    }

    protected boolean zze(int i) {
        com.google.android.gms.ads.internal.util.client.zzb.zzaH("Failed to load ad: " + i);
        this.zzor = false;
        if (this.zzot.zzqs == null) {
            return false;
        }
        try {
            this.zzot.zzqs.onAdFailedToLoad(i);
            return true;
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not call AdListener.onAdFailedToLoad().", e);
            return false;
        }
    }

    long zzo(String str) {
        int iIndexOf = str.indexOf("ufe");
        int iIndexOf2 = str.indexOf(44, iIndexOf);
        if (iIndexOf2 == -1) {
            iIndexOf2 = str.length();
        }
        try {
            return Long.parseLong(str.substring(iIndexOf + 4, iIndexOf2));
        } catch (IndexOutOfBoundsException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Invalid index for Url fetch time in CSI latency info.");
            return -1L;
        } catch (NumberFormatException e2) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Cannot find valid format of Url fetch time in CSI latency info.");
            return -1L;
        }
    }
}
