package com.google.android.gms.ads.internal;

import android.content.Context;
import android.os.RemoteException;
import android.view.MotionEvent;
import android.view.View;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.zze;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzcg;
import com.google.android.gms.internal.zzch;
import com.google.android.gms.internal.zzck;
import com.google.android.gms.internal.zzem;
import com.google.android.gms.internal.zzfi;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzhs;
import com.google.android.gms.internal.zzid;
import com.google.android.gms.internal.zziz;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public abstract class zzc extends zzb implements zzg, zzfi {
    public zzc(Context context, AdSizeParcel adSizeParcel, String str, zzem zzemVar, VersionInfoParcel versionInfoParcel, zzd zzdVar) {
        super(context, adSizeParcel, str, zzemVar, versionInfoParcel, zzdVar);
    }

    @Override // com.google.android.gms.ads.internal.zzg
    public void recordClick() {
        onAdClicked();
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.ads.internal.zzg
    public void recordImpression() {
        zza(this.zzot.zzqo, false);
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
    protected zziz zza(zzhs.zza zzaVar, zze zzeVar) {
        zziz zzizVar;
        View nextView = this.zzot.zzqk.getNextView();
        if (nextView instanceof zziz) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaF("Reusing webview...");
            zziz zzizVar2 = (zziz) nextView;
            zzizVar2.zza(this.zzot.context, this.zzot.zzqn, this.zzoo);
            zzizVar = zzizVar2;
        } else {
            if (nextView != 0) {
                this.zzot.zzqk.removeView(nextView);
            }
            zziz zzizVarZza = zzp.zzbw().zza(this.zzot.context, this.zzot.zzqn, false, false, this.zzot.zzqi, this.zzot.zzqj, this.zzoo, this.zzow);
            if (this.zzot.zzqn.zztg == null) {
                zzb(zzizVarZza.getView());
            }
            zzizVar = zzizVarZza;
        }
        zzizVar.zzhe().zzb(this, this, this, this, false, this, null, zzeVar, this);
        zzizVar.zzaJ(zzaVar.zzHC.zzEC);
        return zzizVar;
    }

    @Override // com.google.android.gms.internal.zzfi
    public void zza(int i, int i2, int i3, int i4) {
        zzaS();
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.ads.internal.client.zzs
    public void zza(zzck zzckVar) {
        zzx.zzci("setOnCustomRenderedAdLoadedListener must be called on the main UI thread.");
        this.zzot.zzqC = zzckVar;
    }

    @Override // com.google.android.gms.ads.internal.zza
    protected void zza(final zzhs.zza zzaVar, final zzcg zzcgVar) {
        if (zzaVar.errorCode != -2) {
            zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.ads.internal.zzc.1
                @Override // java.lang.Runnable
                public void run() {
                    zzc.this.zzb(new zzhs(zzaVar, null, null, null, null, null, null));
                }
            });
            return;
        }
        if (zzaVar.zzqn != null) {
            this.zzot.zzqn = zzaVar.zzqn;
        }
        if (!zzaVar.zzHD.zzEK) {
            zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.ads.internal.zzc.2
                @Override // java.lang.Runnable
                public void run() {
                    if (zzaVar.zzHD.zzET && zzc.this.zzot.zzqC != null) {
                        zzch zzchVar = new zzch(zzc.this, zzaVar.zzHD.zzBF != null ? zzp.zzbv().zzaz(zzaVar.zzHD.zzBF) : null, zzaVar.zzHD.body);
                        zzc.this.zzot.zzqH = 1;
                        try {
                            zzc.this.zzot.zzqC.zza(zzchVar);
                            return;
                        } catch (RemoteException e) {
                            com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not call the onCustomRenderedAdLoadedListener.", e);
                        }
                    }
                    final zze zzeVar = new zze();
                    zziz zzizVarZza = zzc.this.zza(zzaVar, zzeVar);
                    zzeVar.zza(new zze.zzb(zzaVar, zzizVarZza));
                    zzizVarZza.setOnTouchListener(new View.OnTouchListener() { // from class: com.google.android.gms.ads.internal.zzc.2.1
                        @Override // android.view.View.OnTouchListener
                        public boolean onTouch(View v, MotionEvent event) {
                            zzeVar.recordClick();
                            return false;
                        }
                    });
                    zzizVarZza.setOnClickListener(new View.OnClickListener() { // from class: com.google.android.gms.ads.internal.zzc.2.2
                        @Override // android.view.View.OnClickListener
                        public void onClick(View v) {
                            zzeVar.recordClick();
                        }
                    });
                    zzc.this.zzot.zzqH = 0;
                    zzc.this.zzot.zzqm = zzp.zzbu().zza(zzc.this.zzot.context, zzc.this, zzaVar, zzc.this.zzot.zzqi, zzizVarZza, zzc.this.zzox, zzc.this, zzcgVar);
                }
            });
            return;
        }
        this.zzot.zzqH = 0;
        this.zzot.zzqm = zzp.zzbu().zza(this.zzot.context, this, zzaVar, this.zzot.zzqi, null, this.zzox, this, zzcgVar);
    }

    @Override // com.google.android.gms.ads.internal.zzb, com.google.android.gms.ads.internal.zza
    protected boolean zza(zzhs zzhsVar, zzhs zzhsVar2) {
        if (this.zzot.zzbN() && this.zzot.zzqk != null) {
            this.zzot.zzqk.zzbT().zzaC(zzhsVar2.zzEP);
        }
        return super.zza(zzhsVar, zzhsVar2);
    }

    @Override // com.google.android.gms.internal.zzfi
    public void zzbc() {
        zzaQ();
    }

    @Override // com.google.android.gms.ads.internal.zzg
    public void zzc(View view) {
        this.zzot.zzqG = view;
        zzb(new zzhs(this.zzot.zzqp, null, null, null, null, null, null));
    }
}
