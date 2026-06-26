package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.internal.zzgg;
import com.google.android.gms.internal.zzhs;
import com.google.android.gms.internal.zzja;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public abstract class zzgc implements zzgh<Void>, zzja.zza {
    protected final Context mContext;
    protected final zzgg.zza zzDd;
    protected final zzhs.zza zzDe;
    protected AdResponseParcel zzDf;
    private Runnable zzDg;
    protected final Object zzDh = new Object();
    private AtomicBoolean zzDi = new AtomicBoolean(true);
    protected final zziz zzoM;

    protected zzgc(Context context, zzhs.zza zzaVar, zziz zzizVar, zzgg.zza zzaVar2) {
        this.mContext = context;
        this.zzDe = zzaVar;
        this.zzDf = this.zzDe.zzHD;
        this.zzoM = zzizVar;
        this.zzDd = zzaVar2;
    }

    private zzhs zzA(int i) {
        AdRequestInfoParcel adRequestInfoParcel = this.zzDe.zzHC;
        return new zzhs(adRequestInfoParcel.zzEn, this.zzoM, this.zzDf.zzyY, i, this.zzDf.zzyZ, this.zzDf.zzEM, this.zzDf.orientation, this.zzDf.zzzc, adRequestInfoParcel.zzEq, this.zzDf.zzEK, null, null, null, null, null, this.zzDf.zzEL, this.zzDe.zzqn, this.zzDf.zzEJ, this.zzDe.zzHz, this.zzDf.zzEO, this.zzDf.zzEP, this.zzDe.zzHw, null);
    }

    @Override // com.google.android.gms.internal.zzgh
    public void cancel() {
        if (this.zzDi.getAndSet(false)) {
            this.zzoM.stopLoading();
            com.google.android.gms.ads.internal.zzp.zzbx().zza(this.zzoM.getWebView());
            zzz(-1);
            zzid.zzIE.removeCallbacks(this.zzDg);
        }
    }

    @Override // com.google.android.gms.internal.zzja.zza
    public void zza(zziz zzizVar, boolean z) {
        com.google.android.gms.ads.internal.util.client.zzb.zzaF("WebView finished loading.");
        if (this.zzDi.getAndSet(false)) {
            zzz(z ? zzft() : -1);
            zzid.zzIE.removeCallbacks(this.zzDg);
        }
    }

    @Override // com.google.android.gms.internal.zzgh
    /* JADX INFO: renamed from: zzfr, reason: merged with bridge method [inline-methods] */
    public final Void zzfu() {
        com.google.android.gms.common.internal.zzx.zzci("Webview render task needs to be called on UI thread.");
        this.zzDg = new Runnable() { // from class: com.google.android.gms.internal.zzgc.1
            @Override // java.lang.Runnable
            public void run() {
                if (zzgc.this.zzDi.get()) {
                    com.google.android.gms.ads.internal.util.client.zzb.e("Timed out waiting for WebView to finish loading.");
                    zzgc.this.cancel();
                }
            }
        };
        zzid.zzIE.postDelayed(this.zzDg, zzby.zzvw.get().longValue());
        zzfs();
        return null;
    }

    protected abstract void zzfs();

    protected int zzft() {
        return -2;
    }

    protected void zzz(int i) {
        if (i != -2) {
            this.zzDf = new AdResponseParcel(i, this.zzDf.zzzc);
        }
        this.zzDd.zzb(zzA(i));
    }
}
