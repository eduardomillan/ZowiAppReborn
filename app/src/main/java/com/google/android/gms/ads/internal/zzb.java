package com.google.android.gms.ads.internal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.DisplayMetrics;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.purchase.GInAppPurchaseManagerInfoParcel;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.ads.internal.request.CapabilityParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzby;
import com.google.android.gms.internal.zzcg;
import com.google.android.gms.internal.zzdm;
import com.google.android.gms.internal.zzef;
import com.google.android.gms.internal.zzem;
import com.google.android.gms.internal.zzfp;
import com.google.android.gms.internal.zzfs;
import com.google.android.gms.internal.zzfw;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzhs;
import com.google.android.gms.internal.zzht;
import com.google.android.gms.internal.zzid;
import java.util.ArrayList;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public abstract class zzb extends zza implements com.google.android.gms.ads.internal.overlay.zzg, com.google.android.gms.ads.internal.purchase.zzj, zzdm, zzef {
    private final Messenger mMessenger;
    protected final zzem zzox;
    protected transient boolean zzoy;

    public zzb(Context context, AdSizeParcel adSizeParcel, String str, zzem zzemVar, VersionInfoParcel versionInfoParcel, zzd zzdVar) {
        this(new zzq(context, adSizeParcel, str, versionInfoParcel), zzemVar, null, zzdVar);
    }

    zzb(zzq zzqVar, zzem zzemVar, zzo zzoVar, zzd zzdVar) {
        super(zzqVar, zzoVar, zzdVar);
        this.zzox = zzemVar;
        this.mMessenger = new Messenger(new zzfp(this.zzot.context));
        this.zzoy = false;
    }

    private AdRequestInfoParcel.zza zza(AdRequestParcel adRequestParcel, Bundle bundle) {
        PackageInfo packageInfo;
        ApplicationInfo applicationInfo = this.zzot.context.getApplicationInfo();
        try {
            packageInfo = this.zzot.context.getPackageManager().getPackageInfo(applicationInfo.packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
        }
        DisplayMetrics displayMetrics = this.zzot.context.getResources().getDisplayMetrics();
        Bundle bundle2 = null;
        if (this.zzot.zzqk != null && this.zzot.zzqk.getParent() != null) {
            int[] iArr = new int[2];
            this.zzot.zzqk.getLocationOnScreen(iArr);
            int i = iArr[0];
            int i2 = iArr[1];
            int width = this.zzot.zzqk.getWidth();
            int height = this.zzot.zzqk.getHeight();
            int i3 = 0;
            if (this.zzot.zzqk.isShown() && i + width > 0 && i2 + height > 0 && i <= displayMetrics.widthPixels && i2 <= displayMetrics.heightPixels) {
                i3 = 1;
            }
            bundle2 = new Bundle(5);
            bundle2.putInt("x", i);
            bundle2.putInt("y", i2);
            bundle2.putInt("width", width);
            bundle2.putInt("height", height);
            bundle2.putInt("visible", i3);
        }
        String strZzgm = zzp.zzby().zzgm();
        this.zzot.zzqq = new zzht(strZzgm, this.zzot.zzqh);
        this.zzot.zzqq.zzi(adRequestParcel);
        String strZza = zzp.zzbv().zza(this.zzot.context, this.zzot.zzqk, this.zzot.zzqn);
        long value = 0;
        if (this.zzot.zzqu != null) {
            try {
                value = this.zzot.zzqu.getValue();
            } catch (RemoteException e2) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Cannot get correlation id, default to 0.");
            }
        }
        String string = UUID.randomUUID().toString();
        Bundle bundleZza = zzp.zzby().zza(this.zzot.context, this, strZzgm);
        ArrayList arrayList = new ArrayList();
        for (int i4 = 0; i4 < this.zzot.zzqA.size(); i4++) {
            arrayList.add(this.zzot.zzqA.keyAt(i4));
        }
        return new AdRequestInfoParcel.zza(bundle2, adRequestParcel, this.zzot.zzqn, this.zzot.zzqh, applicationInfo, packageInfo, strZzgm, zzp.zzby().getSessionId(), this.zzot.zzqj, bundleZza, this.zzot.zzqD, arrayList, bundle, zzp.zzby().zzgq(), this.mMessenger, displayMetrics.widthPixels, displayMetrics.heightPixels, displayMetrics.density, strZza, value, string, zzby.zzdf(), this.zzot.zzqg, this.zzot.zzqB, new CapabilityParcel(this.zzot.zzqv != null, this.zzot.zzqw != null && zzp.zzby().zzgv()), this.zzot.zzbR());
    }

    @Override // com.google.android.gms.ads.internal.client.zzs
    public String getMediationAdapterClassName() {
        if (this.zzot.zzqo == null) {
            return null;
        }
        return this.zzot.zzqo.zzzw;
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.ads.internal.client.zza
    public void onAdClicked() {
        if (this.zzot.zzqo == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Ad state was null when trying to ping click URLs.");
            return;
        }
        if (this.zzot.zzqo.zzHx != null && this.zzot.zzqo.zzHx.zzyY != null) {
            zzp.zzbH().zza(this.zzot.context, this.zzot.zzqj.zzJu, this.zzot.zzqo, this.zzot.zzqh, false, this.zzot.zzqo.zzHx.zzyY);
        }
        if (this.zzot.zzqo.zzzu != null && this.zzot.zzqo.zzzu.zzyR != null) {
            zzp.zzbH().zza(this.zzot.context, this.zzot.zzqj.zzJu, this.zzot.zzqo, this.zzot.zzqh, false, this.zzot.zzqo.zzzu.zzyR);
        }
        super.onAdClicked();
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.ads.internal.client.zzs
    public void pause() {
        zzx.zzci("pause must be called on the main UI thread.");
        if (this.zzot.zzqo != null && this.zzot.zzqo.zzBD != null && this.zzot.zzbN()) {
            zzp.zzbx().zza(this.zzot.zzqo.zzBD.getWebView());
        }
        if (this.zzot.zzqo != null && this.zzot.zzqo.zzzv != null) {
            try {
                this.zzot.zzqo.zzzv.pause();
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Could not pause mediation adapter.");
            }
        }
        this.zzov.zzg(this.zzot.zzqo);
        this.zzos.pause();
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.ads.internal.client.zzs
    public void resume() {
        zzx.zzci("resume must be called on the main UI thread.");
        if (this.zzot.zzqo != null && this.zzot.zzqo.zzBD != null && this.zzot.zzbN()) {
            zzp.zzbx().zzb(this.zzot.zzqo.zzBD.getWebView());
        }
        if (this.zzot.zzqo != null && this.zzot.zzqo.zzzv != null) {
            try {
                this.zzot.zzqo.zzzv.resume();
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Could not resume mediation adapter.");
            }
        }
        this.zzos.resume();
        this.zzov.zzh(this.zzot.zzqo);
    }

    @Override // com.google.android.gms.ads.internal.client.zzs
    public void showInterstitial() {
        throw new IllegalStateException("showInterstitial is not supported for current ad type");
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.ads.internal.client.zzs
    public void zza(zzfs zzfsVar) {
        zzx.zzci("setInAppPurchaseListener must be called on the main UI thread.");
        this.zzot.zzqv = zzfsVar;
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.ads.internal.client.zzs
    public void zza(zzfw zzfwVar, String str) {
        zzx.zzci("setPlayStorePurchaseParams must be called on the main UI thread.");
        this.zzot.zzqE = new com.google.android.gms.ads.internal.purchase.zzk(str);
        this.zzot.zzqw = zzfwVar;
        if (zzp.zzby().zzgp() || zzfwVar == null) {
            return;
        }
        new com.google.android.gms.ads.internal.purchase.zzc(this.zzot.context, this.zzot.zzqw, this.zzot.zzqE).zzfu();
    }

    protected void zza(zzhs zzhsVar, boolean z) {
        if (zzhsVar == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Ad state was null when trying to ping impression URLs.");
            return;
        }
        super.zzc(zzhsVar);
        if (zzhsVar.zzHx != null && zzhsVar.zzHx.zzyZ != null) {
            zzp.zzbH().zza(this.zzot.context, this.zzot.zzqj.zzJu, zzhsVar, this.zzot.zzqh, z, zzhsVar.zzHx.zzyZ);
        }
        if (zzhsVar.zzzu == null || zzhsVar.zzzu.zzyS == null) {
            return;
        }
        zzp.zzbH().zza(this.zzot.context, this.zzot.zzqj.zzJu, zzhsVar, this.zzot.zzqh, z, zzhsVar.zzzu.zzyS);
    }

    @Override // com.google.android.gms.internal.zzdm
    public void zza(String str, ArrayList<String> arrayList) {
        com.google.android.gms.ads.internal.purchase.zzd zzdVar = new com.google.android.gms.ads.internal.purchase.zzd(str, arrayList, this.zzot.context, this.zzot.zzqj.zzJu);
        if (this.zzot.zzqv != null) {
            try {
                this.zzot.zzqv.zza(zzdVar);
                return;
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Could not start In-App purchase.");
                return;
            }
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaH("InAppPurchaseListener is not set. Try to launch default purchase flow.");
        if (!com.google.android.gms.ads.internal.client.zzl.zzcF().zzR(this.zzot.context)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Google Play Service unavailable, cannot launch default purchase flow.");
            return;
        }
        if (this.zzot.zzqw == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("PlayStorePurchaseListener is not set.");
            return;
        }
        if (this.zzot.zzqE == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("PlayStorePurchaseVerifier is not initialized.");
            return;
        }
        if (this.zzot.zzqI) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("An in-app purchase request is already in progress, abort");
            return;
        }
        this.zzot.zzqI = true;
        try {
            if (this.zzot.zzqw.isValidPurchase(str)) {
                zzp.zzbF().zza(this.zzot.context, this.zzot.zzqj.zzJx, new GInAppPurchaseManagerInfoParcel(this.zzot.context, this.zzot.zzqE, zzdVar, this));
            } else {
                this.zzot.zzqI = false;
            }
        } catch (RemoteException e2) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Could not start In-App purchase.");
            this.zzot.zzqI = false;
        }
    }

    @Override // com.google.android.gms.ads.internal.purchase.zzj
    public void zza(String str, boolean z, int i, final Intent intent, com.google.android.gms.ads.internal.purchase.zzf zzfVar) {
        try {
            if (this.zzot.zzqw != null) {
                this.zzot.zzqw.zza(new com.google.android.gms.ads.internal.purchase.zzg(this.zzot.context, str, z, i, intent, zzfVar));
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Fail to invoke PlayStorePurchaseListener.");
        }
        zzid.zzIE.postDelayed(new Runnable() { // from class: com.google.android.gms.ads.internal.zzb.1
            @Override // java.lang.Runnable
            public void run() {
                int iZzd = zzp.zzbF().zzd(intent);
                zzp.zzbF();
                if (iZzd == 0 && zzb.this.zzot.zzqo != null && zzb.this.zzot.zzqo.zzBD != null && zzb.this.zzot.zzqo.zzBD.zzhc() != null) {
                    zzb.this.zzot.zzqo.zzBD.zzhc().close();
                }
                zzb.this.zzot.zzqI = false;
            }
        }, 500L);
    }

    @Override // com.google.android.gms.ads.internal.zza
    public boolean zza(AdRequestParcel adRequestParcel, zzcg zzcgVar) {
        if (!zzaU()) {
            return false;
        }
        Bundle bundleZza = zza(zzp.zzby().zzE(this.zzot.context));
        this.zzos.cancel();
        this.zzot.zzqH = 0;
        AdRequestInfoParcel.zza zzaVarZza = zza(adRequestParcel, bundleZza);
        zzcgVar.zze("seq_num", zzaVarZza.zzEq);
        zzcgVar.zze("request_id", zzaVarZza.zzEC);
        zzcgVar.zze("session_id", zzaVarZza.zzEr);
        if (zzaVarZza.zzEo != null) {
            zzcgVar.zze("app_version", String.valueOf(zzaVarZza.zzEo.versionCode));
        }
        this.zzot.zzql = zzp.zzbr().zza(this.zzot.context, zzaVarZza, this.zzot.zzqi, this);
        return true;
    }

    protected boolean zza(AdRequestParcel adRequestParcel, zzhs zzhsVar, boolean z) {
        if (!z && this.zzot.zzbN()) {
            if (zzhsVar.zzzc > 0) {
                this.zzos.zza(adRequestParcel, zzhsVar.zzzc);
            } else if (zzhsVar.zzHx != null && zzhsVar.zzHx.zzzc > 0) {
                this.zzos.zza(adRequestParcel, zzhsVar.zzHx.zzzc);
            } else if (!zzhsVar.zzEK && zzhsVar.errorCode == 2) {
                this.zzos.zzg(adRequestParcel);
            }
        }
        return this.zzos.zzbp();
    }

    @Override // com.google.android.gms.ads.internal.zza
    boolean zza(zzhs zzhsVar) {
        AdRequestParcel adRequestParcel;
        boolean z = false;
        if (this.zzou != null) {
            adRequestParcel = this.zzou;
            this.zzou = null;
        } else {
            adRequestParcel = zzhsVar.zzEn;
            if (adRequestParcel.extras != null) {
                z = adRequestParcel.extras.getBoolean("_noRefresh", false);
            }
        }
        return zza(adRequestParcel, zzhsVar, z);
    }

    @Override // com.google.android.gms.ads.internal.zza
    protected boolean zza(zzhs zzhsVar, zzhs zzhsVar2) {
        int i;
        int i2 = 0;
        if (zzhsVar != null && zzhsVar.zzzx != null) {
            zzhsVar.zzzx.zza((zzef) null);
        }
        if (zzhsVar2.zzzx != null) {
            zzhsVar2.zzzx.zza(this);
        }
        if (zzhsVar2.zzHx != null) {
            i = zzhsVar2.zzHx.zzzf;
            i2 = zzhsVar2.zzHx.zzzg;
        } else {
            i = 0;
        }
        this.zzot.zzqF.zzf(i, i2);
        return true;
    }

    protected boolean zzaU() {
        return zzp.zzbv().zza(this.zzot.context.getPackageManager(), this.zzot.context.getPackageName(), "android.permission.INTERNET") && zzp.zzbv().zzH(this.zzot.context);
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzg
    public void zzaV() {
        this.zzov.zze(this.zzot.zzqo);
        this.zzoy = false;
        zzaQ();
        this.zzot.zzqq.zzgh();
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzg
    public void zzaW() {
        this.zzoy = true;
        zzaS();
    }

    @Override // com.google.android.gms.internal.zzef
    public void zzaX() {
        onAdClicked();
    }

    @Override // com.google.android.gms.internal.zzef
    public void zzaY() {
        zzaV();
    }

    @Override // com.google.android.gms.internal.zzef
    public void zzaZ() {
        zzaO();
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.internal.zzgg.zza
    public void zzb(zzhs zzhsVar) {
        super.zzb(zzhsVar);
        if (zzhsVar.errorCode != 3 || zzhsVar.zzHx == null || zzhsVar.zzHx.zzza == null) {
            return;
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("Pinging no fill URLs.");
        zzp.zzbH().zza(this.zzot.context, this.zzot.zzqj.zzJu, zzhsVar, this.zzot.zzqh, false, zzhsVar.zzHx.zzza);
    }

    @Override // com.google.android.gms.internal.zzef
    public void zzba() {
        zzaW();
    }

    @Override // com.google.android.gms.internal.zzef
    public void zzbb() {
        if (this.zzot.zzqo != null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Mediation adapter " + this.zzot.zzqo.zzzw + " refreshed, but mediation adapters should never refresh.");
        }
        zza(this.zzot.zzqo, true);
        zzaT();
    }

    @Override // com.google.android.gms.ads.internal.zza
    protected boolean zzc(AdRequestParcel adRequestParcel) {
        return super.zzc(adRequestParcel) && !this.zzoy;
    }
}
