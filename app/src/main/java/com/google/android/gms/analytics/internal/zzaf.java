package com.google.android.gms.analytics.internal;

import android.util.Log;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class zzaf extends zzd {
    private static String zzPr = "3";
    private static String zzPs = "01VDIWEA?";
    private static zzaf zzPt;

    public zzaf(zzf zzfVar) {
        super(zzfVar);
    }

    public static zzaf zzkG() {
        return zzPt;
    }

    public void zza(int i, String str, Object obj, Object obj2, Object obj3) {
        String str2 = zzy.zzOg.get();
        if (Log.isLoggable(str2, i)) {
            Log.println(i, str2, zzc(str, obj, obj2, obj3));
        }
        if (i >= 5) {
            zzb(i, str, obj, obj2, obj3);
        }
    }

    public void zza(zzab zzabVar, String str) {
        if (str == null) {
            str = "no reason provided";
        }
        zzd("Discarding hit. " + str, zzabVar != null ? zzabVar.toString() : "no hit data");
    }

    public synchronized void zzb(int i, String str, Object obj, Object obj2, Object obj3) {
        synchronized (this) {
            com.google.android.gms.common.internal.zzx.zzw(str);
            int i2 = i >= 0 ? i : 0;
            String strSubstring = zzPr + zzPs.charAt(i2 >= zzPs.length() ? zzPs.length() - 1 : i2) + (zziv().zzjB() ? zziv().zzjA() ? 'P' : 'C' : zziv().zzjA() ? 'p' : 'c') + zze.VERSION + ":" + zzc(str, zzk(obj), zzk(obj2), zzk(obj3));
            if (strSubstring.length() > 1024) {
                strSubstring = strSubstring.substring(0, 1024);
            }
            zzai zzaiVarZziJ = zziq().zziJ();
            if (zzaiVarZziJ != null) {
                zzaiVarZziJ.zzkT().zzbn(strSubstring);
            }
        }
    }

    public void zzh(Map<String, String> map, String str) {
        String string;
        if (str == null) {
            str = "no reason provided";
        }
        if (map != null) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (sb.length() > 0) {
                    sb.append(',');
                }
                sb.append(entry.getKey());
                sb.append('=');
                sb.append(entry.getValue());
            }
            string = sb.toString();
        } else {
            string = "no hit data";
        }
        zzd("Discarding hit. " + str, string);
    }

    @Override // com.google.android.gms.analytics.internal.zzd
    protected void zzhR() {
        synchronized (zzaf.class) {
            zzPt = this;
        }
    }

    protected String zzk(Object obj) {
        if (obj == null) {
            return null;
        }
        Object l = obj instanceof Integer ? new Long(((Integer) obj).intValue()) : obj;
        if (!(l instanceof Long)) {
            return l instanceof Boolean ? String.valueOf(l) : l instanceof Throwable ? l.getClass().getCanonicalName() : "-";
        }
        if (Math.abs(((Long) l).longValue()) < 100) {
            return String.valueOf(l);
        }
        String str = String.valueOf(l).charAt(0) == '-' ? "-" : "";
        String strValueOf = String.valueOf(Math.abs(((Long) l).longValue()));
        return str + Math.round(Math.pow(10.0d, strValueOf.length() - 1)) + "..." + str + Math.round(Math.pow(10.0d, strValueOf.length()) - 1.0d);
    }
}
