package com.google.android.gms.internal;

import android.content.Context;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.internal.zzgf;
import com.google.android.gms.internal.zzgg;
import com.google.android.gms.internal.zzhs;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzgk extends zzgf {
    protected zzei zzDA;
    private zzec zzDz;
    private final zzcg zzoo;
    private zzem zzox;
    private zzee zzzA;

    zzgk(Context context, zzhs.zza zzaVar, zzem zzemVar, zzgg.zza zzaVar2, zzcg zzcgVar) {
        super(context, zzaVar, zzaVar2);
        this.zzox = zzemVar;
        this.zzzA = zzaVar.zzHx;
        this.zzoo = zzcgVar;
    }

    @Override // com.google.android.gms.internal.zzgf, com.google.android.gms.internal.zzhz
    public void onStop() {
        synchronized (this.zzDh) {
            super.onStop();
            if (this.zzDz != null) {
                this.zzDz.cancel();
            }
        }
    }

    @Override // com.google.android.gms.internal.zzgf
    protected zzhs zzA(int i) {
        AdRequestInfoParcel adRequestInfoParcel = this.zzDe.zzHC;
        return new zzhs(adRequestInfoParcel.zzEn, null, this.zzDf.zzyY, i, this.zzDf.zzyZ, this.zzDf.zzEM, this.zzDf.orientation, this.zzDf.zzzc, adRequestInfoParcel.zzEq, this.zzDf.zzEK, this.zzDA != null ? this.zzDA.zzzu : null, this.zzDA != null ? this.zzDA.zzzv : null, this.zzDA != null ? this.zzDA.zzzw : AdMobAdapter.class.getName(), this.zzzA, this.zzDA != null ? this.zzDA.zzzx : null, this.zzDf.zzEL, this.zzDe.zzqn, this.zzDf.zzEJ, this.zzDe.zzHz, this.zzDf.zzEO, this.zzDf.zzEP, this.zzDe.zzHw, null);
    }

    @Override // com.google.android.gms.internal.zzgf
    protected void zzh(long j) throws zzgf.zza {
        synchronized (this.zzDh) {
            this.zzDz = new zzek(this.mContext, this.zzDe.zzHC, this.zzox, this.zzzA, this.zzDf.zzth, j, zzby.zzvw.get().longValue(), this.zzoo);
        }
        this.zzDA = this.zzDz.zzc(this.zzzA.zzyW);
        switch (this.zzDA.zzzt) {
            case 0:
                return;
            case 1:
                throw new zzgf.zza("No fill from any mediation ad networks.", 3);
            default:
                throw new zzgf.zza("Unexpected mediation result: " + this.zzDA.zzzt, 0);
        }
    }
}
