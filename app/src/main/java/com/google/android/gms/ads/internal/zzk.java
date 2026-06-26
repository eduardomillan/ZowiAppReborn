package com.google.android.gms.ads.internal;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.Window;
import com.comscore.utils.Constants;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzby;
import com.google.android.gms.internal.zzcg;
import com.google.android.gms.internal.zzdo;
import com.google.android.gms.internal.zzem;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzhs;
import com.google.android.gms.internal.zzhz;
import com.google.android.gms.internal.zzid;
import com.google.android.gms.internal.zziz;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzk extends zzc implements zzdo {
    protected transient boolean zzpk;
    private boolean zzpl;
    private float zzpm;
    private String zzpn;

    @zzgr
    private class zza extends zzhz {
        private final String zzpo;

        public zza(String str) {
            this.zzpo = str;
        }

        @Override // com.google.android.gms.internal.zzhz
        public void onStop() {
        }

        @Override // com.google.android.gms.internal.zzhz
        public void zzbn() {
            zzp.zzbv().zzh(zzk.this.zzot.context, this.zzpo);
        }
    }

    @zzgr
    private class zzb extends zzhz {
        private final String zzpo;
        private final Bitmap zzpq;

        public zzb(Bitmap bitmap, String str) {
            this.zzpq = bitmap;
            this.zzpo = str;
        }

        @Override // com.google.android.gms.internal.zzhz
        public void onStop() {
        }

        @Override // com.google.android.gms.internal.zzhz
        public void zzbn() {
            InterstitialAdParameterParcel interstitialAdParameterParcel = new InterstitialAdParameterParcel(zzk.this.zzot.zzpt, zzk.this.zzbl(), zzk.this.zzot.zzpt ? zzp.zzbv().zza(zzk.this.zzot.context, this.zzpq, this.zzpo) : false ? this.zzpo : null, zzk.this.zzpl, zzk.this.zzpm);
            int requestedOrientation = zzk.this.zzot.zzqo.zzBD.getRequestedOrientation();
            if (requestedOrientation == -1) {
                requestedOrientation = zzk.this.zzot.zzqo.orientation;
            }
            final AdOverlayInfoParcel adOverlayInfoParcel = new AdOverlayInfoParcel(zzk.this, zzk.this, zzk.this, zzk.this.zzot.zzqo.zzBD, requestedOrientation, zzk.this.zzot.zzqj, zzk.this.zzot.zzqo.zzEP, interstitialAdParameterParcel);
            zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.ads.internal.zzk.zzb.1
                @Override // java.lang.Runnable
                public void run() {
                    zzp.zzbt().zza(zzk.this.zzot.context, adOverlayInfoParcel);
                }
            });
        }
    }

    public zzk(Context context, AdSizeParcel adSizeParcel, String str, zzem zzemVar, VersionInfoParcel versionInfoParcel, zzd zzdVar) {
        super(context, adSizeParcel, str, zzemVar, versionInfoParcel, zzdVar);
        this.zzpk = false;
        this.zzpn = Constants.DEFAULT_BACKGROUND_PAGE_NAME + hashCode() + ".png";
    }

    private void zzb(Bundle bundle) {
        zzp.zzbv().zzb(this.zzot.context, this.zzot.zzqj.zzJu, "gmob-apps", bundle, false);
    }

    private void zzbm() {
        new zza(this.zzpn).zzfu();
        if (this.zzot.zzbN()) {
            this.zzot.zzbK();
            this.zzot.zzqo = null;
            this.zzot.zzpt = false;
            this.zzpk = false;
        }
    }

    @Override // com.google.android.gms.ads.internal.zzb, com.google.android.gms.ads.internal.client.zzs
    public void showInterstitial() {
        zzx.zzci("showInterstitial must be called on the main UI thread.");
        if (this.zzot.zzqo == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("The interstitial has not loaded.");
            return;
        }
        if (zzby.zzvo.get().booleanValue()) {
            String packageName = this.zzot.context.getApplicationContext() != null ? this.zzot.context.getApplicationContext().getPackageName() : this.zzot.context.getPackageName();
            if (!this.zzpk) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("It is not recommended to show an interstitial before onAdLoaded completes.");
                Bundle bundle = new Bundle();
                bundle.putString("appid", packageName);
                bundle.putString("action", "show_interstitial_before_load_finish");
                zzb(bundle);
            }
            if (!zzp.zzbv().zzN(this.zzot.context)) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("It is not recommended to show an interstitial when app is not in foreground.");
                Bundle bundle2 = new Bundle();
                bundle2.putString("appid", packageName);
                bundle2.putString("action", "show_interstitial_app_not_in_foreground");
                zzb(bundle2);
            }
        }
        if (this.zzot.zzbO()) {
            return;
        }
        if (this.zzot.zzqo.zzEK) {
            try {
                this.zzot.zzqo.zzzv.showInterstitial();
                return;
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not show interstitial.", e);
                zzbm();
                return;
            }
        }
        if (this.zzot.zzqo.zzBD == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("The interstitial failed to load.");
            return;
        }
        if (this.zzot.zzqo.zzBD.zzhi()) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("The interstitial is already showing.");
            return;
        }
        this.zzot.zzqo.zzBD.zzC(true);
        if (this.zzot.zzqo.zzHw != null) {
            this.zzov.zza(this.zzot.zzqn, this.zzot.zzqo);
        }
        Bitmap bitmapZzO = this.zzot.zzpt ? zzp.zzbv().zzO(this.zzot.context) : null;
        if (zzby.zzvz.get().booleanValue() && bitmapZzO != null) {
            new zzb(bitmapZzO, this.zzpn).zzfu();
            return;
        }
        InterstitialAdParameterParcel interstitialAdParameterParcel = new InterstitialAdParameterParcel(this.zzot.zzpt, zzbl(), null, false, 0.0f);
        int requestedOrientation = this.zzot.zzqo.zzBD.getRequestedOrientation();
        if (requestedOrientation == -1) {
            requestedOrientation = this.zzot.zzqo.orientation;
        }
        zzp.zzbt().zza(this.zzot.context, new AdOverlayInfoParcel(this, this, this, this.zzot.zzqo.zzBD, requestedOrientation, this.zzot.zzqj, this.zzot.zzqo.zzEP, interstitialAdParameterParcel));
    }

    @Override // com.google.android.gms.ads.internal.zzc
    protected zziz zza(zzhs.zza zzaVar, zze zzeVar) {
        zziz zzizVarZza = zzp.zzbw().zza(this.zzot.context, this.zzot.zzqn, false, false, this.zzot.zzqi, this.zzot.zzqj, this.zzoo, this.zzow);
        zzizVarZza.zzhe().zzb(this, null, this, this, zzby.zzvc.get().booleanValue(), this, this, zzeVar, null);
        zzizVarZza.zzaJ(zzaVar.zzHC.zzEC);
        return zzizVarZza;
    }

    @Override // com.google.android.gms.internal.zzdo
    public void zza(boolean z, float f) {
        this.zzpl = z;
        this.zzpm = f;
    }

    @Override // com.google.android.gms.ads.internal.zzb, com.google.android.gms.ads.internal.zza
    public boolean zza(AdRequestParcel adRequestParcel, zzcg zzcgVar) {
        if (this.zzot.zzqo == null) {
            return super.zza(adRequestParcel, zzcgVar);
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaH("An interstitial is already loading. Aborting.");
        return false;
    }

    @Override // com.google.android.gms.ads.internal.zzb
    protected boolean zza(AdRequestParcel adRequestParcel, zzhs zzhsVar, boolean z) {
        if (this.zzot.zzbN() && zzhsVar.zzBD != null) {
            zzp.zzbx().zza(zzhsVar.zzBD.getWebView());
        }
        return this.zzos.zzbp();
    }

    @Override // com.google.android.gms.ads.internal.zzc, com.google.android.gms.ads.internal.zzb, com.google.android.gms.ads.internal.zza
    public boolean zza(zzhs zzhsVar, zzhs zzhsVar2) {
        if (!super.zza(zzhsVar, zzhsVar2)) {
            return false;
        }
        if (!this.zzot.zzbN() && this.zzot.zzqG != null && zzhsVar2.zzHw != null) {
            this.zzov.zza(this.zzot.zzqn, zzhsVar2, this.zzot.zzqG);
        }
        return true;
    }

    @Override // com.google.android.gms.ads.internal.zza
    protected boolean zzaQ() {
        zzbm();
        return super.zzaQ();
    }

    @Override // com.google.android.gms.ads.internal.zza
    protected boolean zzaT() {
        if (!super.zzaT()) {
            return false;
        }
        this.zzpk = true;
        return true;
    }

    @Override // com.google.android.gms.ads.internal.zzb, com.google.android.gms.ads.internal.overlay.zzg
    public void zzaW() {
        recordImpression();
        super.zzaW();
    }

    protected boolean zzbl() {
        Window window;
        if (!(this.zzot.context instanceof Activity) || (window = ((Activity) this.zzot.context).getWindow()) == null || window.getDecorView() == null) {
            return false;
        }
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        window.getDecorView().getGlobalVisibleRect(rect, null);
        window.getDecorView().getWindowVisibleDisplayFrame(rect2);
        return (rect.bottom == 0 || rect2.bottom == 0 || rect.top != rect2.top) ? false : true;
    }

    @Override // com.google.android.gms.internal.zzdo
    public void zzd(boolean z) {
        this.zzot.zzpt = z;
    }
}
