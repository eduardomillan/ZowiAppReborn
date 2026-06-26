package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.internal.zzgg;
import com.google.android.gms.internal.zzhs;
import com.google.android.gms.internal.zzja;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzgi extends zzgc implements zzja.zza {
    zzgi(Context context, zzhs.zza zzaVar, zziz zzizVar, zzgg.zza zzaVar2) {
        super(context, zzaVar, zzizVar, zzaVar2);
    }

    @Override // com.google.android.gms.internal.zzgc
    protected void zzfs() {
        if (this.zzDf.errorCode != -2) {
            return;
        }
        this.zzoM.zzhe().zza(this);
        zzfz();
        com.google.android.gms.ads.internal.util.client.zzb.v("Loading HTML in WebView.");
        this.zzoM.loadDataWithBaseURL(com.google.android.gms.ads.internal.zzp.zzbv().zzaz(this.zzDf.zzBF), this.zzDf.body, "text/html", "UTF-8", null);
    }

    protected void zzfz() {
    }
}
