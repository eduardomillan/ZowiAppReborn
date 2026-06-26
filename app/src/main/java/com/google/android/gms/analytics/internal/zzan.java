package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public class zzan extends zzd {
    protected boolean zzLD;
    protected int zzNW;
    protected String zzOZ;
    protected boolean zzPQ;
    protected boolean zzPR;
    protected boolean zzPS;
    protected String zzPa;
    protected int zzPc;

    public zzan(zzf zzfVar) {
        super(zzfVar);
    }

    private static int zzbv(String str) {
        String lowerCase = str.toLowerCase();
        if ("verbose".equals(lowerCase)) {
            return 0;
        }
        if ("info".equals(lowerCase)) {
            return 1;
        }
        if ("warning".equals(lowerCase)) {
            return 2;
        }
        return "error".equals(lowerCase) ? 3 : -1;
    }

    public int getLogLevel() {
        zziE();
        return this.zzNW;
    }

    void zza(zzaa zzaaVar) {
        int iZzbv;
        zzba("Loading global XML config values");
        if (zzaaVar.zzko()) {
            String strZzkp = zzaaVar.zzkp();
            this.zzOZ = strZzkp;
            zzb("XML config - app name", strZzkp);
        }
        if (zzaaVar.zzkq()) {
            String strZzkr = zzaaVar.zzkr();
            this.zzPa = strZzkr;
            zzb("XML config - app version", strZzkr);
        }
        if (zzaaVar.zzks() && (iZzbv = zzbv(zzaaVar.zzkt())) >= 0) {
            this.zzNW = iZzbv;
            zza("XML config - log level", Integer.valueOf(iZzbv));
        }
        if (zzaaVar.zzku()) {
            int iZzkv = zzaaVar.zzkv();
            this.zzPc = iZzkv;
            this.zzPR = true;
            zzb("XML config - dispatch period (sec)", Integer.valueOf(iZzkv));
        }
        if (zzaaVar.zzkw()) {
            boolean zZzkx = zzaaVar.zzkx();
            this.zzLD = zZzkx;
            this.zzPS = true;
            zzb("XML config - dry run", Boolean.valueOf(zZzkx));
        }
    }

    @Override // com.google.android.gms.analytics.internal.zzd
    protected void zzhR() {
        zzlm();
    }

    public String zzkp() {
        zziE();
        return this.zzOZ;
    }

    public String zzkr() {
        zziE();
        return this.zzPa;
    }

    public boolean zzks() {
        zziE();
        return this.zzPQ;
    }

    public boolean zzku() {
        zziE();
        return this.zzPR;
    }

    public boolean zzkw() {
        zziE();
        return this.zzPS;
    }

    public boolean zzkx() {
        zziE();
        return this.zzLD;
    }

    public int zzll() {
        zziE();
        return this.zzPc;
    }

    protected void zzlm() {
        ApplicationInfo applicationInfo;
        int i;
        zzaa zzaaVarZzad;
        Context context = getContext();
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 129);
        } catch (PackageManager.NameNotFoundException e) {
            zzd("PackageManager doesn't know about the app package", e);
            applicationInfo = null;
        }
        if (applicationInfo == null) {
            zzbd("Couldn't get ApplicationInfo to load global config");
            return;
        }
        Bundle bundle = applicationInfo.metaData;
        if (bundle == null || (i = bundle.getInt("com.google.android.gms.analytics.globalConfigResource")) <= 0 || (zzaaVarZzad = new zzz(zziq()).zzad(i)) == null) {
            return;
        }
        zza(zzaaVarZzad);
    }
}
