package com.google.android.gms.analytics.internal;

import android.app.Activity;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class zzal implements zzp {
    public String zzLq;
    public double zzPJ = -1.0d;
    public int zzPK = -1;
    public int zzPL = -1;
    public int zzPM = -1;
    public int zzPN = -1;
    public Map<String, String> zzPO = new HashMap();

    public int getSessionTimeout() {
        return this.zzPK;
    }

    public String getTrackingId() {
        return this.zzLq;
    }

    public String zzbo(String str) {
        String str2 = this.zzPO.get(str);
        return str2 != null ? str2 : str;
    }

    public boolean zzlc() {
        return this.zzLq != null;
    }

    public boolean zzld() {
        return this.zzPJ >= 0.0d;
    }

    public double zzle() {
        return this.zzPJ;
    }

    public boolean zzlf() {
        return this.zzPK >= 0;
    }

    public boolean zzlg() {
        return this.zzPL != -1;
    }

    public boolean zzlh() {
        return this.zzPL == 1;
    }

    public boolean zzli() {
        return this.zzPM != -1;
    }

    public boolean zzlj() {
        return this.zzPM == 1;
    }

    public boolean zzlk() {
        return this.zzPN == 1;
    }

    public String zzq(Activity activity) {
        return zzbo(activity.getClass().getCanonicalName());
    }
}
