package com.google.android.gms.ads.internal.formats;

import android.content.Context;
import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.ads.internal.zzn;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzan;
import com.google.android.gms.internal.zzeq;
import com.google.android.gms.internal.zzer;
import com.google.android.gms.internal.zziz;
import java.lang.ref.WeakReference;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class zzg extends zzh {
    private Object zzpd;
    private zzeq zzwD;
    private zzer zzwE;
    private final zzn zzwF;
    private zzh zzwG;
    private boolean zzwH;

    private zzg(Context context, zzn zznVar, zzan zzanVar) {
        super(context, zznVar, null, zzanVar, null, null, null);
        this.zzwH = false;
        this.zzpd = new Object();
        this.zzwF = zznVar;
    }

    public zzg(Context context, zzn zznVar, zzan zzanVar, zzeq zzeqVar) {
        this(context, zznVar, zzanVar);
        this.zzwD = zzeqVar;
    }

    public zzg(Context context, zzn zznVar, zzan zzanVar, zzer zzerVar) {
        this(context, zznVar, zzanVar);
        this.zzwE = zzerVar;
    }

    @Override // com.google.android.gms.ads.internal.formats.zzh
    public void recordImpression() {
        zzx.zzci("recordImpression must be called on the main UI thread.");
        synchronized (this.zzpd) {
            zzl(true);
            if (this.zzwG != null) {
                this.zzwG.recordImpression();
            } else {
                try {
                    if (this.zzwD != null && !this.zzwD.getOverrideClickHandling()) {
                        this.zzwD.recordImpression();
                    } else if (this.zzwE != null && !this.zzwE.getOverrideClickHandling()) {
                        this.zzwE.recordImpression();
                    }
                } catch (RemoteException e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to call recordImpression", e);
                }
            }
            this.zzwF.recordImpression();
        }
    }

    @Override // com.google.android.gms.ads.internal.formats.zzh
    public zzb zza(View.OnClickListener onClickListener) {
        return null;
    }

    @Override // com.google.android.gms.ads.internal.formats.zzh
    public void zza(View view, Map<String, WeakReference<View>> map, JSONObject jSONObject, JSONObject jSONObject2) {
        zzx.zzci("performClick must be called on the main UI thread.");
        synchronized (this.zzpd) {
            if (this.zzwG == null) {
                try {
                    if (this.zzwD != null && !this.zzwD.getOverrideClickHandling()) {
                        this.zzwD.zzc(com.google.android.gms.dynamic.zze.zzy(view));
                    }
                    if (this.zzwE != null && !this.zzwE.getOverrideClickHandling()) {
                        this.zzwD.zzc(com.google.android.gms.dynamic.zze.zzy(view));
                    }
                } catch (RemoteException e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to call performClick", e);
                }
            }
            this.zzwG.zza(view, map, jSONObject, jSONObject2);
            this.zzwF.onAdClicked();
        }
    }

    public void zzb(zzh zzhVar) {
        synchronized (this.zzpd) {
            this.zzwG = zzhVar;
        }
    }

    public boolean zzdB() {
        boolean z;
        synchronized (this.zzpd) {
            z = this.zzwH;
        }
        return z;
    }

    @Override // com.google.android.gms.ads.internal.formats.zzh
    public zziz zzdC() {
        return null;
    }

    @Override // com.google.android.gms.ads.internal.formats.zzh
    public void zzh(View view) {
        synchronized (this.zzpd) {
            this.zzwH = true;
            try {
                if (this.zzwD != null) {
                    this.zzwD.zzd(com.google.android.gms.dynamic.zze.zzy(view));
                } else if (this.zzwE != null) {
                    this.zzwE.zzd(com.google.android.gms.dynamic.zze.zzy(view));
                }
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to call prepareAd", e);
            }
            this.zzwH = false;
        }
    }
}
