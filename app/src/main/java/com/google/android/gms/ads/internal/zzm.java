package com.google.android.gms.ads.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.client.zzw;
import com.google.android.gms.internal.zzgr;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzm extends zzw.zza {
    private static final Object zzpy = new Object();
    private static zzm zzpz;
    private final Context mContext;
    private boolean zzpA = false;

    zzm(Context context) {
        this.mContext = context;
    }

    public static zzm zzq(Context context) {
        zzm zzmVar;
        synchronized (zzpy) {
            if (zzpz == null) {
                zzpz = new zzm(context.getApplicationContext());
            }
            zzmVar = zzpz;
        }
        return zzmVar;
    }

    @Override // com.google.android.gms.ads.internal.client.zzw
    public void zza() {
        synchronized (zzpy) {
            if (this.zzpA) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Mobile ads is initialized already.");
            } else {
                this.zzpA = true;
            }
        }
    }
}
