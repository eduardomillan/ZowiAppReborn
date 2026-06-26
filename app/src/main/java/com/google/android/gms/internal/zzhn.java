package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.internal.zzhs;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzhn extends zzhz implements zzhm {
    private final Context mContext;
    private final zzhs.zza zzDe;
    private final String zzGY;
    private final zzhg zzHs;
    private final ArrayList<Future> zzHp = new ArrayList<>();
    private final ArrayList<String> zzHq = new ArrayList<>();
    private final HashSet<String> zzHr = new HashSet<>();
    private final Object zzpd = new Object();

    public zzhn(Context context, String str, zzhs.zza zzaVar, zzhg zzhgVar) {
        this.mContext = context;
        this.zzGY = str;
        this.zzDe = zzaVar;
        this.zzHs = zzhgVar;
    }

    private void zzk(String str, String str2) {
        synchronized (this.zzpd) {
            zzhh zzhhVarZzau = this.zzHs.zzau(str);
            if (zzhhVarZzau == null || zzhhVarZzau.zzgd() == null || zzhhVarZzau.zzgc() == null) {
                return;
            }
            this.zzHp.add(new zzhi(this.mContext, str, this.zzGY, str2, this.zzDe, zzhhVarZzau, this).zzfu());
            this.zzHq.add(str);
        }
    }

    @Override // com.google.android.gms.internal.zzhz
    public void onStop() {
    }

    @Override // com.google.android.gms.internal.zzhm
    public void zzav(String str) {
        synchronized (this.zzpd) {
            this.zzHr.add(str);
        }
    }

    @Override // com.google.android.gms.internal.zzhm
    public void zzb(String str, int i) {
    }

    @Override // com.google.android.gms.internal.zzhz
    public void zzbn() {
        for (zzed zzedVar : this.zzDe.zzHx.zzyW) {
            String str = zzedVar.zzyT;
            Iterator<String> it = zzedVar.zzyO.iterator();
            while (it.hasNext()) {
                zzk(it.next(), str);
            }
        }
        for (int i = 0; i < this.zzHp.size(); i++) {
            try {
                this.zzHp.get(i).get();
                synchronized (this.zzpd) {
                    if (this.zzHr.contains(this.zzHq.get(i))) {
                        final zzhs zzhsVar = new zzhs(this.zzDe.zzHC.zzEn, null, this.zzDe.zzHD.zzyY, -2, this.zzDe.zzHD.zzyZ, this.zzDe.zzHD.zzEM, this.zzDe.zzHD.orientation, this.zzDe.zzHD.zzzc, this.zzDe.zzHC.zzEq, this.zzDe.zzHD.zzEK, this.zzDe.zzHx.zzyW.get(i), null, this.zzHq.get(i), this.zzDe.zzHx, null, this.zzDe.zzHD.zzEL, this.zzDe.zzqn, this.zzDe.zzHD.zzEJ, this.zzDe.zzHz, this.zzDe.zzHD.zzEO, this.zzDe.zzHD.zzEP, this.zzDe.zzHw, null);
                        com.google.android.gms.ads.internal.util.client.zza.zzJt.post(new Runnable() { // from class: com.google.android.gms.internal.zzhn.1
                            @Override // java.lang.Runnable
                            public void run() {
                                zzhn.this.zzHs.zzb(zzhsVar);
                            }
                        });
                        return;
                    }
                }
            } catch (InterruptedException e) {
            } catch (Exception e2) {
            }
        }
        final zzhs zzhsVar2 = new zzhs(this.zzDe.zzHC.zzEn, null, this.zzDe.zzHD.zzyY, 3, this.zzDe.zzHD.zzyZ, this.zzDe.zzHD.zzEM, this.zzDe.zzHD.orientation, this.zzDe.zzHD.zzzc, this.zzDe.zzHC.zzEq, this.zzDe.zzHD.zzEK, null, null, null, this.zzDe.zzHx, null, this.zzDe.zzHD.zzEL, this.zzDe.zzqn, this.zzDe.zzHD.zzEJ, this.zzDe.zzHz, this.zzDe.zzHD.zzEO, this.zzDe.zzHD.zzEP, this.zzDe.zzHw, null);
        com.google.android.gms.ads.internal.util.client.zza.zzJt.post(new Runnable() { // from class: com.google.android.gms.internal.zzhn.2
            @Override // java.lang.Runnable
            public void run() {
                zzhn.this.zzHs.zzb(zzhsVar2);
            }
        });
    }
}
