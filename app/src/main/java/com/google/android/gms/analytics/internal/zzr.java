package com.google.android.gms.analytics.internal;

import android.content.pm.ApplicationInfo;
import android.os.Process;
import android.text.TextUtils;
import com.comscore.measurement.MeasurementDispatcher;
import com.google.android.gms.internal.zzmy;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public class zzr {
    private final zzf zzLf;
    private volatile Boolean zzNT;
    private String zzNU;
    private Set<Integer> zzNV;

    protected zzr(zzf zzfVar) {
        com.google.android.gms.common.internal.zzx.zzw(zzfVar);
        this.zzLf = zzfVar;
    }

    public boolean zzjA() {
        return com.google.android.gms.common.internal.zzd.zzaeK;
    }

    public boolean zzjB() {
        if (this.zzNT == null) {
            synchronized (this) {
                if (this.zzNT == null) {
                    ApplicationInfo applicationInfo = this.zzLf.getContext().getApplicationInfo();
                    String strZzj = zzmy.zzj(this.zzLf.getContext(), Process.myPid());
                    if (applicationInfo != null) {
                        String str = applicationInfo.processName;
                        this.zzNT = Boolean.valueOf(str != null && str.equals(strZzj));
                    }
                    if ((this.zzNT == null || !this.zzNT.booleanValue()) && "com.google.android.gms.analytics".equals(strZzj)) {
                        this.zzNT = Boolean.TRUE;
                    }
                    if (this.zzNT == null) {
                        this.zzNT = Boolean.TRUE;
                        this.zzLf.zziu().zzbe("My process not in the list of running processes");
                    }
                }
            }
        }
        return this.zzNT.booleanValue();
    }

    public boolean zzjC() {
        return zzy.zzOf.get().booleanValue();
    }

    public int zzjD() {
        return zzy.zzOy.get().intValue();
    }

    public int zzjE() {
        return zzy.zzOC.get().intValue();
    }

    public int zzjF() {
        return zzy.zzOD.get().intValue();
    }

    public int zzjG() {
        return zzy.zzOE.get().intValue();
    }

    public long zzjH() {
        return zzy.zzOn.get().longValue();
    }

    public long zzjI() {
        return zzy.zzOm.get().longValue();
    }

    public long zzjJ() {
        return zzy.zzOq.get().longValue();
    }

    public long zzjK() {
        return zzy.zzOr.get().longValue();
    }

    public int zzjL() {
        return zzy.zzOs.get().intValue();
    }

    public int zzjM() {
        return zzy.zzOt.get().intValue();
    }

    public long zzjN() {
        return zzy.zzOG.get().intValue();
    }

    public String zzjO() {
        return zzy.zzOv.get();
    }

    public String zzjP() {
        return zzy.zzOu.get();
    }

    public String zzjQ() {
        return zzy.zzOw.get();
    }

    public String zzjR() {
        return zzy.zzOx.get();
    }

    public zzm zzjS() {
        return zzm.zzbj(zzy.zzOz.get());
    }

    public zzo zzjT() {
        return zzo.zzbk(zzy.zzOA.get());
    }

    public Set<Integer> zzjU() {
        String str = zzy.zzOF.get();
        if (this.zzNV == null || this.zzNU == null || !this.zzNU.equals(str)) {
            String[] strArrSplit = TextUtils.split(str, ",");
            HashSet hashSet = new HashSet();
            for (String str2 : strArrSplit) {
                try {
                    hashSet.add(Integer.valueOf(Integer.parseInt(str2)));
                } catch (NumberFormatException e) {
                }
            }
            this.zzNU = str;
            this.zzNV = hashSet;
        }
        return this.zzNV;
    }

    public long zzjV() {
        return zzy.zzOO.get().longValue();
    }

    public long zzjW() {
        return zzy.zzOP.get().longValue();
    }

    public long zzjX() {
        return zzy.zzOS.get().longValue();
    }

    public int zzjY() {
        return zzy.zzOj.get().intValue();
    }

    public int zzjZ() {
        return zzy.zzOl.get().intValue();
    }

    public String zzka() {
        return "google_analytics_v4.db";
    }

    public String zzkb() {
        return "google_analytics2_v4.db";
    }

    public long zzkc() {
        return MeasurementDispatcher.MILLIS_PER_DAY;
    }

    public int zzkd() {
        return zzy.zzOI.get().intValue();
    }

    public int zzke() {
        return zzy.zzOJ.get().intValue();
    }

    public long zzkf() {
        return zzy.zzOK.get().longValue();
    }

    public long zzkg() {
        return zzy.zzOT.get().longValue();
    }
}
