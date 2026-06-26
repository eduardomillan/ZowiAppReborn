package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.internal.zzgy;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzgz {
    private WeakHashMap<Context, zza> zzGO = new WeakHashMap<>();

    private class zza {
        public final long zzGP = com.google.android.gms.ads.internal.zzp.zzbz().currentTimeMillis();
        public final zzgy zzGQ;

        public zza(zzgy zzgyVar) {
            this.zzGQ = zzgyVar;
        }

        public boolean hasExpired() {
            return zzby.zzvn.get().longValue() + this.zzGP < com.google.android.gms.ads.internal.zzp.zzbz().currentTimeMillis();
        }
    }

    public zzgy zzC(Context context) {
        zza zzaVar = this.zzGO.get(context);
        zzgy zzgyVarZzfX = (zzaVar == null || zzaVar.hasExpired() || !zzby.zzvm.get().booleanValue()) ? new zzgy.zza(context).zzfX() : new zzgy.zza(context, zzaVar.zzGQ).zzfX();
        this.zzGO.put(context, new zza(zzgyVarZzfX));
        return zzgyVarZzfX;
    }
}
