package com.google.android.gms.ads.internal.client;

import com.google.android.gms.internal.zzda;
import com.google.android.gms.internal.zzgr;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public class zzl {
    private static final Object zzpy = new Object();
    private static zzl zztl;
    private final com.google.android.gms.ads.internal.util.client.zza zztm = new com.google.android.gms.ads.internal.util.client.zza();
    private final zze zztn = new zze();
    private final zzm zzto = new zzm();
    private final zzad zztp = new zzad();
    private final zzda zztq = new zzda();
    private final com.google.android.gms.ads.internal.reward.client.zzf zztr = new com.google.android.gms.ads.internal.reward.client.zzf();

    static {
        zza(new zzl());
    }

    protected zzl() {
    }

    protected static void zza(zzl zzlVar) {
        synchronized (zzpy) {
            zztl = zzlVar;
        }
    }

    private static zzl zzcE() {
        zzl zzlVar;
        synchronized (zzpy) {
            zzlVar = zztl;
        }
        return zzlVar;
    }

    public static com.google.android.gms.ads.internal.util.client.zza zzcF() {
        return zzcE().zztm;
    }

    public static zze zzcG() {
        return zzcE().zztn;
    }

    public static zzm zzcH() {
        return zzcE().zzto;
    }

    public static zzad zzcI() {
        return zzcE().zztp;
    }

    public static zzda zzcJ() {
        return zzcE().zztq;
    }

    public static com.google.android.gms.ads.internal.reward.client.zzf zzcK() {
        return zzcE().zztr;
    }
}
