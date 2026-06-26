package com.google.android.gms.internal;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzdt extends zzhz {
    final zziz zzoM;
    final zzdv zzxY;
    private final String zzxZ;

    zzdt(zziz zzizVar, zzdv zzdvVar, String str) {
        this.zzoM = zzizVar;
        this.zzxY = zzdvVar;
        this.zzxZ = str;
        com.google.android.gms.ads.internal.zzp.zzbI().zza(this);
    }

    @Override // com.google.android.gms.internal.zzhz
    public void onStop() {
        this.zzxY.abort();
    }

    @Override // com.google.android.gms.internal.zzhz
    public void zzbn() {
        try {
            this.zzxY.zzab(this.zzxZ);
        } finally {
            zzid.zzIE.post(new Runnable() { // from class: com.google.android.gms.internal.zzdt.1
                @Override // java.lang.Runnable
                public void run() {
                    com.google.android.gms.ads.internal.zzp.zzbI().zzb(zzdt.this);
                }
            });
        }
    }
}
