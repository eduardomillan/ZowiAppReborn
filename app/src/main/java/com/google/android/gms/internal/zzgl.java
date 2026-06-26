package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.internal.zzgg;
import com.google.android.gms.internal.zzhs;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzgl extends zzhz {
    private final zzgm zzDB;
    private Future<zzhs> zzDC;
    private final zzgg.zza zzDd;
    private final zzhs.zza zzDe;
    private final AdResponseParcel zzDf;
    private final Object zzpd;

    public zzgl(Context context, com.google.android.gms.ads.internal.zzn zznVar, zzbc zzbcVar, zzhs.zza zzaVar, zzan zzanVar, zzgg.zza zzaVar2) {
        this(zzaVar, zzaVar2, new zzgm(context, zznVar, zzbcVar, new zzih(context), zzanVar, zzaVar));
    }

    zzgl(zzhs.zza zzaVar, zzgg.zza zzaVar2, zzgm zzgmVar) {
        this.zzpd = new Object();
        this.zzDe = zzaVar;
        this.zzDf = zzaVar.zzHD;
        this.zzDd = zzaVar2;
        this.zzDB = zzgmVar;
    }

    private zzhs zzB(int i) {
        return new zzhs(this.zzDe.zzHC.zzEn, null, null, i, null, null, this.zzDf.orientation, this.zzDf.zzzc, this.zzDe.zzHC.zzEq, false, null, null, null, null, null, this.zzDf.zzEL, this.zzDe.zzqn, this.zzDf.zzEJ, this.zzDe.zzHz, this.zzDf.zzEO, this.zzDf.zzEP, this.zzDe.zzHw, null);
    }

    @Override // com.google.android.gms.internal.zzhz
    public void onStop() {
        synchronized (this.zzpd) {
            if (this.zzDC != null) {
                this.zzDC.cancel(true);
            }
        }
    }

    @Override // com.google.android.gms.internal.zzhz
    public void zzbn() {
        int i;
        final zzhs zzhsVarZzB;
        try {
            synchronized (this.zzpd) {
                this.zzDC = zzic.zza(this.zzDB);
            }
            zzhsVarZzB = this.zzDC.get(60000L, TimeUnit.MILLISECONDS);
            i = -2;
        } catch (InterruptedException e) {
            zzhsVarZzB = null;
            i = -1;
        } catch (CancellationException e2) {
            zzhsVarZzB = null;
            i = -1;
        } catch (ExecutionException e3) {
            i = 0;
            zzhsVarZzB = null;
        } catch (TimeoutException e4) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Timed out waiting for native ad.");
            this.zzDC.cancel(true);
            i = 2;
            zzhsVarZzB = null;
        }
        if (zzhsVarZzB == null) {
            zzhsVarZzB = zzB(i);
        }
        zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.internal.zzgl.1
            @Override // java.lang.Runnable
            public void run() {
                zzgl.this.zzDd.zzb(zzhsVarZzB);
            }
        });
    }
}
