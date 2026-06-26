package com.google.android.gms.ads.internal;

import android.content.Context;
import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzem;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzhs;
import com.google.android.gms.internal.zziz;
import com.google.android.gms.internal.zzja;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzf extends zzc {
    private boolean zzoN;

    public zzf(Context context, AdSizeParcel adSizeParcel, String str, zzem zzemVar, VersionInfoParcel versionInfoParcel, zzd zzdVar) {
        super(context, adSizeParcel, str, zzemVar, versionInfoParcel, zzdVar);
    }

    private AdSizeParcel zzb(zzhs.zza zzaVar) {
        AdSize adSizeZzcD;
        if (zzaVar.zzHD.zzti) {
            return this.zzot.zzqn;
        }
        String str = zzaVar.zzHD.zzEN;
        if (str != null) {
            String[] strArrSplit = str.split("[xX]");
            strArrSplit[0] = strArrSplit[0].trim();
            strArrSplit[1] = strArrSplit[1].trim();
            adSizeZzcD = new AdSize(Integer.parseInt(strArrSplit[0]), Integer.parseInt(strArrSplit[1]));
        } else {
            adSizeZzcD = this.zzot.zzqn.zzcD();
        }
        return new AdSizeParcel(this.zzot.context, adSizeZzcD);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:593)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private boolean zzb(zzhs zzhsVar, zzhs zzhsVar2) {
        boolean z;
        if (zzhsVar2.zzEK) {
            try {
                com.google.android.gms.dynamic.zzd view = zzhsVar2.zzzv.getView();
                if (view == null) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzaH("View in mediation adapter is null.");
                    z = false;
                } else {
                    View view2 = (View) com.google.android.gms.dynamic.zze.zzp(view);
                    View nextView = this.zzot.zzqk.getNextView();
                    if (nextView != 0) {
                        if (nextView instanceof zziz) {
                            ((zziz) nextView).destroy();
                        }
                        this.zzot.zzqk.removeView(nextView);
                    }
                    try {
                        zzb(view2);
                    } catch (Throwable th) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not add mediation view to view hierarchy.", th);
                        z = false;
                    }
                }
                return z;
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not get View from mediation adapter.", e);
                return false;
            }
        }
        if (zzhsVar2.zzHy != null && zzhsVar2.zzBD != null) {
            zzhsVar2.zzBD.zza(zzhsVar2.zzHy);
            this.zzot.zzqk.removeAllViews();
            this.zzot.zzqk.setMinimumWidth(zzhsVar2.zzHy.widthPixels);
            this.zzot.zzqk.setMinimumHeight(zzhsVar2.zzHy.heightPixels);
            zzb(zzhsVar2.zzBD.getView());
        }
        if (this.zzot.zzqk.getChildCount() > 1) {
            this.zzot.zzqk.showNext();
        }
        if (zzhsVar != null) {
            View nextView2 = this.zzot.zzqk.getNextView();
            if (nextView2 instanceof zziz) {
                ((zziz) nextView2).zza(this.zzot.context, this.zzot.zzqn, this.zzoo);
            } else if (nextView2 != 0) {
                this.zzot.zzqk.removeView(nextView2);
            }
            this.zzot.zzbM();
        }
        this.zzot.zzqk.setVisibility(0);
        z = true;
        return z;
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.ads.internal.client.zzs
    public void setManualImpressionsEnabled(boolean enabled) {
        zzx.zzci("setManualImpressionsEnabled must be called from the main thread.");
        this.zzoN = enabled;
    }

    @Override // com.google.android.gms.ads.internal.zzb, com.google.android.gms.ads.internal.client.zzs
    public void showInterstitial() {
        throw new IllegalStateException("Interstitial is NOT supported by BannerAdManager.");
    }

    @Override // com.google.android.gms.ads.internal.zzc
    protected zziz zza(zzhs.zza zzaVar, zze zzeVar) {
        if (this.zzot.zzqn.zzti) {
            this.zzot.zzqn = zzb(zzaVar);
        }
        return super.zza(zzaVar, zzeVar);
    }

    @Override // com.google.android.gms.ads.internal.zzc, com.google.android.gms.ads.internal.zzb, com.google.android.gms.ads.internal.zza
    public boolean zza(zzhs zzhsVar, final zzhs zzhsVar2) {
        if (!super.zza(zzhsVar, zzhsVar2)) {
            return false;
        }
        if (this.zzot.zzbN() && !zzb(zzhsVar, zzhsVar2)) {
            zze(0);
            return false;
        }
        zza(zzhsVar2, false);
        if (this.zzot.zzbN()) {
            if (zzhsVar2.zzBD != null) {
                if (zzhsVar2.zzHw != null) {
                    this.zzov.zza(this.zzot.zzqn, zzhsVar2);
                }
                if (zzhsVar2.zzbY()) {
                    this.zzov.zza(this.zzot.zzqn, zzhsVar2).zza(zzhsVar2.zzBD);
                } else {
                    zzhsVar2.zzBD.zzhe().zza(new zzja.zzb() { // from class: com.google.android.gms.ads.internal.zzf.1
                        @Override // com.google.android.gms.internal.zzja.zzb
                        public void zzbf() {
                            zzf.this.zzov.zza(zzf.this.zzot.zzqn, zzhsVar2).zza(zzhsVar2.zzBD);
                        }
                    });
                }
            }
        } else if (this.zzot.zzqG != null && zzhsVar2.zzHw != null) {
            this.zzov.zza(this.zzot.zzqn, zzhsVar2, this.zzot.zzqG);
        }
        return true;
    }

    @Override // com.google.android.gms.ads.internal.zzb
    protected boolean zzaU() {
        boolean z = true;
        if (!zzp.zzbv().zza(this.zzot.context.getPackageManager(), this.zzot.context.getPackageName(), "android.permission.INTERNET")) {
            com.google.android.gms.ads.internal.client.zzl.zzcF().zza(this.zzot.zzqk, this.zzot.zzqn, "Missing internet permission in AndroidManifest.xml.", "Missing internet permission in AndroidManifest.xml. You must have the following declaration: <uses-permission android:name=\"android.permission.INTERNET\" />");
            z = false;
        }
        if (!zzp.zzbv().zzH(this.zzot.context)) {
            com.google.android.gms.ads.internal.client.zzl.zzcF().zza(this.zzot.zzqk, this.zzot.zzqn, "Missing AdActivity with android:configChanges in AndroidManifest.xml.", "Missing AdActivity with android:configChanges in AndroidManifest.xml. You must have the following declaration within the <application> element: <activity android:name=\"com.google.android.gms.ads.AdActivity\" android:configChanges=\"keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize\" />");
            z = false;
        }
        if (!z && this.zzot.zzqk != null) {
            this.zzot.zzqk.setVisibility(0);
        }
        return z;
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.ads.internal.client.zzs
    public boolean zzb(AdRequestParcel adRequestParcel) {
        return super.zzb(zze(adRequestParcel));
    }

    AdRequestParcel zze(AdRequestParcel adRequestParcel) {
        if (adRequestParcel.zzsG == this.zzoN) {
            return adRequestParcel;
        }
        return new AdRequestParcel(adRequestParcel.versionCode, adRequestParcel.zzsB, adRequestParcel.extras, adRequestParcel.zzsC, adRequestParcel.zzsD, adRequestParcel.zzsE, adRequestParcel.zzsF, adRequestParcel.zzsG || this.zzoN, adRequestParcel.zzsH, adRequestParcel.zzsI, adRequestParcel.zzsJ, adRequestParcel.zzsK, adRequestParcel.zzsL, adRequestParcel.zzsM, adRequestParcel.zzsN, adRequestParcel.zzsO, adRequestParcel.zzsP);
    }
}
