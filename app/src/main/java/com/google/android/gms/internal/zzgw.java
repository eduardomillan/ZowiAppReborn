package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzgw {
    private List<String> zzDJ;
    private String zzFU;
    private String zzFV;
    private List<String> zzFW;
    private String zzFX;
    private String zzFY;
    private List<String> zzFZ;
    private String zzwq;
    private final AdRequestInfoParcel zzzz;
    private long zzGa = -1;
    private boolean zzGb = false;
    private final long zzGc = -1;
    private long zzGd = -1;
    private int mOrientation = -1;
    private boolean zzGe = false;
    private boolean zzGf = false;
    private boolean zzGg = false;
    private boolean zzGh = true;
    private int zzGi = 0;
    private String zzGj = "";
    private boolean zzGk = false;

    public zzgw(AdRequestInfoParcel adRequestInfoParcel) {
        this.zzzz = adRequestInfoParcel;
    }

    static String zzd(Map<String, List<String>> map, String str) {
        List<String> list = map.get(str);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    static long zze(Map<String, List<String>> map, String str) {
        List<String> list = map.get(str);
        if (list != null && !list.isEmpty()) {
            String str2 = list.get(0);
            try {
                return (long) (Float.parseFloat(str2) * 1000.0f);
            } catch (NumberFormatException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Could not parse float from " + str + " header: " + str2);
            }
        }
        return -1L;
    }

    static List<String> zzf(Map<String, List<String>> map, String str) {
        String str2;
        List<String> list = map.get(str);
        if (list == null || list.isEmpty() || (str2 = list.get(0)) == null) {
            return null;
        }
        return Arrays.asList(str2.trim().split("\\s+"));
    }

    private boolean zzg(Map<String, List<String>> map, String str) {
        List<String> list = map.get(str);
        return (list == null || list.isEmpty() || !Boolean.valueOf(list.get(0)).booleanValue()) ? false : true;
    }

    private void zzi(Map<String, List<String>> map) {
        this.zzFU = zzd(map, "X-Afma-Ad-Size");
    }

    private void zzj(Map<String, List<String>> map) {
        List<String> listZzf = zzf(map, "X-Afma-Click-Tracking-Urls");
        if (listZzf != null) {
            this.zzFW = listZzf;
        }
    }

    private void zzk(Map<String, List<String>> map) {
        List<String> list = map.get("X-Afma-Debug-Dialog");
        if (list == null || list.isEmpty()) {
            return;
        }
        this.zzFX = list.get(0);
    }

    private void zzl(Map<String, List<String>> map) {
        List<String> listZzf = zzf(map, "X-Afma-Tracking-Urls");
        if (listZzf != null) {
            this.zzFZ = listZzf;
        }
    }

    private void zzm(Map<String, List<String>> map) {
        long jZze = zze(map, "X-Afma-Interstitial-Timeout");
        if (jZze != -1) {
            this.zzGa = jZze;
        }
    }

    private void zzn(Map<String, List<String>> map) {
        this.zzFY = zzd(map, "X-Afma-ActiveView");
    }

    private void zzo(Map<String, List<String>> map) {
        this.zzGf = "native".equals(zzd(map, "X-Afma-Ad-Format"));
    }

    private void zzp(Map<String, List<String>> map) {
        this.zzGe |= zzg(map, "X-Afma-Custom-Rendering-Allowed");
    }

    private void zzq(Map<String, List<String>> map) {
        this.zzGb |= zzg(map, "X-Afma-Mediation");
    }

    private void zzr(Map<String, List<String>> map) {
        List<String> listZzf = zzf(map, "X-Afma-Manual-Tracking-Urls");
        if (listZzf != null) {
            this.zzDJ = listZzf;
        }
    }

    private void zzs(Map<String, List<String>> map) {
        long jZze = zze(map, "X-Afma-Refresh-Rate");
        if (jZze != -1) {
            this.zzGd = jZze;
        }
    }

    private void zzt(Map<String, List<String>> map) {
        List<String> list = map.get("X-Afma-Orientation");
        if (list == null || list.isEmpty()) {
            return;
        }
        String str = list.get(0);
        if ("portrait".equalsIgnoreCase(str)) {
            this.mOrientation = com.google.android.gms.ads.internal.zzp.zzbx().zzgH();
        } else if ("landscape".equalsIgnoreCase(str)) {
            this.mOrientation = com.google.android.gms.ads.internal.zzp.zzbx().zzgG();
        }
    }

    private void zzu(Map<String, List<String>> map) {
        List<String> list = map.get("X-Afma-Use-HTTPS");
        if (list == null || list.isEmpty()) {
            return;
        }
        this.zzGg = Boolean.valueOf(list.get(0)).booleanValue();
    }

    private void zzv(Map<String, List<String>> map) {
        List<String> list = map.get("X-Afma-Content-Url-Opted-Out");
        if (list == null || list.isEmpty()) {
            return;
        }
        this.zzGh = Boolean.valueOf(list.get(0)).booleanValue();
    }

    private void zzw(Map<String, List<String>> map) {
        List<String> listZzf = zzf(map, "X-Afma-OAuth-Token-Status");
        this.zzGi = 0;
        if (listZzf == null) {
            return;
        }
        for (String str : listZzf) {
            if ("Clear".equalsIgnoreCase(str)) {
                this.zzGi = 1;
                return;
            } else if ("No-Op".equalsIgnoreCase(str)) {
                this.zzGi = 0;
                return;
            }
        }
    }

    private void zzx(Map<String, List<String>> map) {
        List<String> list = map.get("X-Afma-Gws-Query-Id");
        if (list == null || list.isEmpty()) {
            return;
        }
        this.zzGj = list.get(0);
    }

    private void zzy(Map<String, List<String>> map) {
        String strZzd = zzd(map, "X-Afma-Fluid");
        if (strZzd == null || !strZzd.equals("height")) {
            return;
        }
        this.zzGk = true;
    }

    public void zzb(String str, Map<String, List<String>> map, String str2) {
        this.zzFV = str;
        this.zzwq = str2;
        zzh(map);
    }

    public void zzh(Map<String, List<String>> map) {
        zzi(map);
        zzj(map);
        zzk(map);
        zzl(map);
        zzm(map);
        zzq(map);
        zzr(map);
        zzs(map);
        zzt(map);
        zzn(map);
        zzu(map);
        zzp(map);
        zzo(map);
        zzv(map);
        zzw(map);
        zzx(map);
        zzy(map);
    }

    public AdResponseParcel zzj(long j) {
        return new AdResponseParcel(this.zzzz, this.zzFV, this.zzwq, this.zzFW, this.zzFZ, this.zzGa, this.zzGb, -1L, this.zzDJ, this.zzGd, this.mOrientation, this.zzFU, j, this.zzFX, this.zzFY, this.zzGe, this.zzGf, this.zzGg, this.zzGh, false, this.zzGi, this.zzGj, this.zzGk);
    }
}
