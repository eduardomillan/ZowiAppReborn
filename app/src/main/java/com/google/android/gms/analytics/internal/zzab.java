package com.google.android.gms.analytics.internal;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class zzab {
    private final List<Command> zzPe;
    private final long zzPf;
    private final long zzPg;
    private final int zzPh;
    private final boolean zzPi;
    private final String zzPj;
    private final Map<String, String> zzvS;

    public zzab(zzc zzcVar, Map<String, String> map, long j, boolean z) {
        this(zzcVar, map, j, z, 0L, 0, null);
    }

    public zzab(zzc zzcVar, Map<String, String> map, long j, boolean z, long j2, int i) {
        this(zzcVar, map, j, z, j2, i, null);
    }

    public zzab(zzc zzcVar, Map<String, String> map, long j, boolean z, long j2, int i, List<Command> list) {
        String strZza;
        String strZza2;
        com.google.android.gms.common.internal.zzx.zzw(zzcVar);
        com.google.android.gms.common.internal.zzx.zzw(map);
        this.zzPg = j;
        this.zzPi = z;
        this.zzPf = j2;
        this.zzPh = i;
        this.zzPe = list != null ? list : Collections.EMPTY_LIST;
        this.zzPj = zzl(list);
        HashMap map2 = new HashMap();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (zzj(entry.getKey()) && (strZza2 = zza(zzcVar, entry.getKey())) != null) {
                map2.put(strZza2, zzb(zzcVar, entry.getValue()));
            }
        }
        for (Map.Entry<String, String> entry2 : map.entrySet()) {
            if (!zzj(entry2.getKey()) && (strZza = zza(zzcVar, entry2.getKey())) != null) {
                map2.put(strZza, zzb(zzcVar, entry2.getValue()));
            }
        }
        if (!TextUtils.isEmpty(this.zzPj)) {
            zzam.zzc(map2, "_v", this.zzPj);
            if (this.zzPj.equals("ma4.0.0") || this.zzPj.equals("ma4.0.1")) {
                map2.remove("adid");
            }
        }
        this.zzvS = Collections.unmodifiableMap(map2);
    }

    public static zzab zza(zzc zzcVar, zzab zzabVar, Map<String, String> map) {
        return new zzab(zzcVar, map, zzabVar.zzkA(), zzabVar.zzkC(), zzabVar.zzkz(), zzabVar.zzky(), zzabVar.zzkB());
    }

    private static String zza(zzc zzcVar, Object obj) {
        if (obj == null) {
            return null;
        }
        String string = obj.toString();
        if (string.startsWith("&")) {
            string = string.substring(1);
        }
        int length = string.length();
        if (length > 256) {
            string = string.substring(0, 256);
            zzcVar.zzc("Hit param name is too long and will be trimmed", Integer.valueOf(length), string);
        }
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        return string;
    }

    private static String zzb(zzc zzcVar, Object obj) {
        String string = obj == null ? "" : obj.toString();
        int length = string.length();
        if (length <= 8192) {
            return string;
        }
        String strSubstring = string.substring(0, 8192);
        zzcVar.zzc("Hit param value is too long and will be trimmed", Integer.valueOf(length), strSubstring);
        return strSubstring;
    }

    private static boolean zzj(Object obj) {
        if (obj == null) {
            return false;
        }
        return obj.toString().startsWith("&");
    }

    private static String zzl(List<Command> list) {
        String value;
        if (list != null) {
            for (Command command : list) {
                if ("appendVersion".equals(command.getId())) {
                    value = command.getValue();
                    break;
                }
            }
            value = null;
        } else {
            value = null;
        }
        if (TextUtils.isEmpty(value)) {
            return null;
        }
        return value;
    }

    private String zzo(String str, String str2) {
        com.google.android.gms.common.internal.zzx.zzcr(str);
        com.google.android.gms.common.internal.zzx.zzb(!str.startsWith("&"), "Short param name required");
        String str3 = this.zzvS.get(str);
        return str3 != null ? str3 : str2;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ht=").append(this.zzPg);
        if (this.zzPf != 0) {
            stringBuffer.append(", dbId=").append(this.zzPf);
        }
        if (this.zzPh != 0) {
            stringBuffer.append(", appUID=").append(this.zzPh);
        }
        ArrayList<String> arrayList = new ArrayList(this.zzvS.keySet());
        Collections.sort(arrayList);
        for (String str : arrayList) {
            stringBuffer.append(", ");
            stringBuffer.append(str);
            stringBuffer.append("=");
            stringBuffer.append(this.zzvS.get(str));
        }
        return stringBuffer.toString();
    }

    public long zzkA() {
        return this.zzPg;
    }

    public List<Command> zzkB() {
        return this.zzPe;
    }

    public boolean zzkC() {
        return this.zzPi;
    }

    public long zzkD() {
        return zzam.zzbq(zzo("_s", "0"));
    }

    public String zzkE() {
        return zzo("_m", "");
    }

    public int zzky() {
        return this.zzPh;
    }

    public long zzkz() {
        return this.zzPf;
    }

    public Map<String, String> zzn() {
        return this.zzvS;
    }
}
