package com.google.android.gms.internal;

import android.content.Context;
import android.os.Build;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class zzbz {
    private Context mContext;
    private String zzqV;
    private boolean zzvA = zzby.zzuQ.get().booleanValue();
    private String zzvB = zzby.zzuR.get();
    private Map<String, String> zzvC = new LinkedHashMap();

    public zzbz(Context context, String str) {
        this.mContext = null;
        this.zzqV = null;
        this.mContext = context;
        this.zzqV = str;
        this.zzvC.put("s", "gmob_sdk");
        this.zzvC.put("v", "3");
        this.zzvC.put("os", Build.VERSION.RELEASE);
        this.zzvC.put("sdk", Build.VERSION.SDK);
        this.zzvC.put("device", com.google.android.gms.ads.internal.zzp.zzbv().zzgE());
        this.zzvC.put("app", context.getApplicationContext() != null ? context.getApplicationContext().getPackageName() : context.getPackageName());
        zzgy zzgyVarZzC = com.google.android.gms.ads.internal.zzp.zzbB().zzC(this.mContext);
        this.zzvC.put("network_coarse", Integer.toString(zzgyVarZzC.zzGE));
        this.zzvC.put("network_fine", Integer.toString(zzgyVarZzC.zzGF));
    }

    Context getContext() {
        return this.mContext;
    }

    String zzbV() {
        return this.zzqV;
    }

    boolean zzdg() {
        return this.zzvA;
    }

    String zzdh() {
        return this.zzvB;
    }

    Map<String, String> zzdi() {
        return this.zzvC;
    }
}
