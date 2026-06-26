package com.google.android.gms.ads.internal;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.formats.zzh;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzcg;
import com.google.android.gms.internal.zzck;
import com.google.android.gms.internal.zzcw;
import com.google.android.gms.internal.zzcx;
import com.google.android.gms.internal.zzcy;
import com.google.android.gms.internal.zzcz;
import com.google.android.gms.internal.zzem;
import com.google.android.gms.internal.zzeq;
import com.google.android.gms.internal.zzer;
import com.google.android.gms.internal.zzfs;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzhs;
import com.google.android.gms.internal.zzid;
import com.google.android.gms.internal.zzmi;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzn extends zzb {
    public zzn(Context context, AdSizeParcel adSizeParcel, String str, zzem zzemVar, VersionInfoParcel versionInfoParcel) {
        super(context, adSizeParcel, str, zzemVar, versionInfoParcel, null);
    }

    private static com.google.android.gms.ads.internal.formats.zzd zza(zzeq zzeqVar) throws RemoteException {
        return new com.google.android.gms.ads.internal.formats.zzd(zzeqVar.getHeadline(), zzeqVar.getImages(), zzeqVar.getBody(), zzeqVar.zzdw() != null ? zzeqVar.zzdw() : null, zzeqVar.getCallToAction(), zzeqVar.getStarRating(), zzeqVar.getStore(), zzeqVar.getPrice(), null, zzeqVar.getExtras());
    }

    private static com.google.android.gms.ads.internal.formats.zze zza(zzer zzerVar) throws RemoteException {
        return new com.google.android.gms.ads.internal.formats.zze(zzerVar.getHeadline(), zzerVar.getImages(), zzerVar.getBody(), zzerVar.zzdA() != null ? zzerVar.zzdA() : null, zzerVar.getCallToAction(), zzerVar.getAdvertiser(), null, zzerVar.getExtras());
    }

    private void zza(final com.google.android.gms.ads.internal.formats.zzd zzdVar) {
        zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.ads.internal.zzn.2
            @Override // java.lang.Runnable
            public void run() {
                try {
                    zzn.this.zzot.zzqx.zza(zzdVar);
                } catch (RemoteException e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not call OnAppInstallAdLoadedListener.onAppInstallAdLoaded().", e);
                }
            }
        });
    }

    private void zza(final com.google.android.gms.ads.internal.formats.zze zzeVar) {
        zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.ads.internal.zzn.3
            @Override // java.lang.Runnable
            public void run() {
                try {
                    zzn.this.zzot.zzqy.zza(zzeVar);
                } catch (RemoteException e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not call OnContentAdLoadedListener.onContentAdLoaded().", e);
                }
            }
        });
    }

    private void zza(final zzhs zzhsVar, final String str) {
        zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.ads.internal.zzn.4
            @Override // java.lang.Runnable
            public void run() {
                try {
                    zzn.this.zzot.zzqA.get(str).zza((com.google.android.gms.ads.internal.formats.zzf) zzhsVar.zzHB);
                } catch (RemoteException e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not call onCustomTemplateAdLoadedListener.onCustomTemplateAdLoaded().", e);
                }
            }
        });
    }

    @Override // com.google.android.gms.ads.internal.zzb, com.google.android.gms.ads.internal.zza, com.google.android.gms.ads.internal.client.zzs
    public void pause() {
        throw new IllegalStateException("Native Ad DOES NOT support pause().");
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.ads.internal.zzg
    public void recordImpression() {
        zza(this.zzot.zzqo, false);
    }

    @Override // com.google.android.gms.ads.internal.zzb, com.google.android.gms.ads.internal.zza, com.google.android.gms.ads.internal.client.zzs
    public void resume() {
        throw new IllegalStateException("Native Ad DOES NOT support resume().");
    }

    @Override // com.google.android.gms.ads.internal.zzb, com.google.android.gms.ads.internal.client.zzs
    public void showInterstitial() {
        throw new IllegalStateException("Interstitial is NOT supported by NativeAdManager.");
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.ads.internal.client.zzs
    public void zza(zzck zzckVar) {
        throw new IllegalStateException("CustomRendering is NOT supported by NativeAdManager.");
    }

    @Override // com.google.android.gms.ads.internal.zzb, com.google.android.gms.ads.internal.zza, com.google.android.gms.ads.internal.client.zzs
    public void zza(zzfs zzfsVar) {
        throw new IllegalStateException("In App Purchase is NOT supported by NativeAdManager.");
    }

    @Override // com.google.android.gms.ads.internal.zza
    public void zza(final zzhs.zza zzaVar, zzcg zzcgVar) {
        if (zzaVar.zzqn != null) {
            this.zzot.zzqn = zzaVar.zzqn;
        }
        if (zzaVar.errorCode != -2) {
            zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.ads.internal.zzn.1
                @Override // java.lang.Runnable
                public void run() {
                    zzn.this.zzb(new zzhs(zzaVar, null, null, null, null, null, null));
                }
            });
            return;
        }
        this.zzot.zzqH = 0;
        this.zzot.zzqm = zzp.zzbu().zza(this.zzot.context, this, zzaVar, this.zzot.zzqi, null, this.zzox, this, zzcgVar);
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("AdRenderer: " + this.zzot.zzqm.getClass().getName());
    }

    public void zza(zzmi<String, zzcz> zzmiVar) {
        zzx.zzci("setOnCustomTemplateAdLoadedListeners must be called on the main UI thread.");
        this.zzot.zzqA = zzmiVar;
    }

    public void zza(List<String> list) {
        zzx.zzci("setNativeTemplates must be called on the main UI thread.");
        this.zzot.zzqD = list;
    }

    @Override // com.google.android.gms.ads.internal.zzb
    protected boolean zza(AdRequestParcel adRequestParcel, zzhs zzhsVar, boolean z) {
        return this.zzos.zzbp();
    }

    @Override // com.google.android.gms.ads.internal.zzb, com.google.android.gms.ads.internal.zza
    protected boolean zza(zzhs zzhsVar, zzhs zzhsVar2) {
        zza((List<String>) null);
        if (!this.zzot.zzbN()) {
            throw new IllegalStateException("Native ad DOES NOT have custom rendering mode.");
        }
        if (zzhsVar2.zzEK) {
            try {
                zzeq zzeqVarZzdV = zzhsVar2.zzzv.zzdV();
                zzer zzerVarZzdW = zzhsVar2.zzzv.zzdW();
                if (zzeqVarZzdV != null) {
                    com.google.android.gms.ads.internal.formats.zzd zzdVarZza = zza(zzeqVarZzdV);
                    zzdVarZza.zza(new com.google.android.gms.ads.internal.formats.zzg(this.zzot.context, this, this.zzot.zzqi, zzeqVarZzdV));
                    zza(zzdVarZza);
                } else {
                    if (zzerVarZzdW == null) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzaH("No matching mapper for retrieved native ad template.");
                        zze(0);
                        return false;
                    }
                    com.google.android.gms.ads.internal.formats.zze zzeVarZza = zza(zzerVarZzdW);
                    zzeVarZza.zza(new com.google.android.gms.ads.internal.formats.zzg(this.zzot.context, this, this.zzot.zzqi, zzerVarZzdW));
                    zza(zzeVarZza);
                }
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to get native ad mapper", e);
            }
        } else {
            zzh.zza zzaVar = zzhsVar2.zzHB;
            if ((zzaVar instanceof com.google.android.gms.ads.internal.formats.zze) && this.zzot.zzqy != null) {
                zza((com.google.android.gms.ads.internal.formats.zze) zzhsVar2.zzHB);
            } else if ((zzaVar instanceof com.google.android.gms.ads.internal.formats.zzd) && this.zzot.zzqx != null) {
                zza((com.google.android.gms.ads.internal.formats.zzd) zzhsVar2.zzHB);
            } else {
                if (!(zzaVar instanceof com.google.android.gms.ads.internal.formats.zzf) || this.zzot.zzqA == null || this.zzot.zzqA.get(((com.google.android.gms.ads.internal.formats.zzf) zzaVar).getCustomTemplateId()) == null) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzaH("No matching listener for retrieved native ad template.");
                    zze(0);
                    return false;
                }
                zza(zzhsVar2, ((com.google.android.gms.ads.internal.formats.zzf) zzaVar).getCustomTemplateId());
            }
        }
        return super.zza(zzhsVar, zzhsVar2);
    }

    public void zzb(NativeAdOptionsParcel nativeAdOptionsParcel) {
        zzx.zzci("setNativeAdOptions must be called on the main UI thread.");
        this.zzot.zzqB = nativeAdOptionsParcel;
    }

    public void zzb(zzcw zzcwVar) {
        zzx.zzci("setOnAppInstallAdLoadedListener must be called on the main UI thread.");
        this.zzot.zzqx = zzcwVar;
    }

    public void zzb(zzcx zzcxVar) {
        zzx.zzci("setOnContentAdLoadedListener must be called on the main UI thread.");
        this.zzot.zzqy = zzcxVar;
    }

    public void zzb(zzmi<String, zzcy> zzmiVar) {
        zzx.zzci("setOnCustomClickListener must be called on the main UI thread.");
        this.zzot.zzqz = zzmiVar;
    }

    public zzmi<String, zzcz> zzbo() {
        zzx.zzci("getOnCustomTemplateAdLoadedListeners must be called on the main UI thread.");
        return this.zzot.zzqA;
    }

    public zzcy zzr(String str) {
        zzx.zzci("getOnCustomClickListener must be called on the main UI thread.");
        return this.zzot.zzqz.get(str);
    }
}
