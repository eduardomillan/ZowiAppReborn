package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.analytics.internal.zzab;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zze;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzh;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzjl;
import com.google.android.gms.internal.zzjm;
import com.google.android.gms.internal.zzjn;
import com.google.android.gms.internal.zzjo;
import com.google.android.gms.internal.zzpb;
import com.google.android.gms.internal.zzpc;
import com.google.android.gms.internal.zzpd;
import com.google.android.gms.internal.zzpe;
import com.google.android.gms.internal.zzpf;
import com.google.android.gms.internal.zzpg;
import com.google.android.gms.internal.zzph;
import com.google.android.gms.internal.zzpi;
import com.google.android.gms.internal.zzpj;
import com.google.android.gms.measurement.zzi;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class zzb extends com.google.android.gms.analytics.internal.zzc implements zzi {
    private static DecimalFormat zzLp;
    private final zzf zzLf;
    private final String zzLq;
    private final Uri zzLr;
    private final boolean zzLs;
    private final boolean zzLt;

    public zzb(zzf zzfVar, String str) {
        this(zzfVar, str, true, false);
    }

    public zzb(zzf zzfVar, String str, boolean z, boolean z2) {
        super(zzfVar);
        zzx.zzcr(str);
        this.zzLf = zzfVar;
        this.zzLq = str;
        this.zzLs = z;
        this.zzLt = z2;
        this.zzLr = zzaR(this.zzLq);
    }

    private static String zzA(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (sb.length() != 0) {
                sb.append(", ");
            }
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
        }
        return sb.toString();
    }

    private static void zza(Map<String, String> map, String str, double d) {
        if (d != 0.0d) {
            map.put(str, zzb(d));
        }
    }

    private static void zza(Map<String, String> map, String str, int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            return;
        }
        map.put(str, i + "x" + i2);
    }

    private static void zza(Map<String, String> map, String str, boolean z) {
        if (z) {
            map.put(str, "1");
        }
    }

    static Uri zzaR(String str) {
        zzx.zzcr(str);
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("uri");
        builder.authority("google-analytics.com");
        builder.path(str);
        return builder.build();
    }

    static String zzb(double d) {
        if (zzLp == null) {
            zzLp = new DecimalFormat("0.######");
        }
        return zzLp.format(d);
    }

    private static void zzb(Map<String, String> map, String str, String str2) {
        if (TextUtils.isEmpty(str2)) {
            return;
        }
        map.put(str, str2);
    }

    public static Map<String, String> zzc(com.google.android.gms.measurement.zzc zzcVar) {
        HashMap map = new HashMap();
        zzjn zzjnVar = (zzjn) zzcVar.zzd(zzjn.class);
        if (zzjnVar != null) {
            for (Map.Entry<String, Object> entry : zzjnVar.zzhZ().entrySet()) {
                String strZzh = zzh(entry.getValue());
                if (strZzh != null) {
                    map.put(entry.getKey(), strZzh);
                }
            }
        }
        zzjo zzjoVar = (zzjo) zzcVar.zzd(zzjo.class);
        if (zzjoVar != null) {
            zzb(map, "t", zzjoVar.zzia());
            zzb(map, "cid", zzjoVar.getClientId());
            zzb(map, "uid", zzjoVar.getUserId());
            zzb(map, "sc", zzjoVar.zzid());
            zza(map, "sf", zzjoVar.zzif());
            zza(map, "ni", zzjoVar.zzie());
            zzb(map, "adid", zzjoVar.zzib());
            zza(map, "ate", zzjoVar.zzic());
        }
        zzph zzphVar = (zzph) zzcVar.zzd(zzph.class);
        if (zzphVar != null) {
            zzb(map, "cd", zzphVar.zzyM());
            zza(map, "a", zzphVar.zzyN());
            zzb(map, "dr", zzphVar.zzyO());
        }
        zzpf zzpfVar = (zzpf) zzcVar.zzd(zzpf.class);
        if (zzpfVar != null) {
            zzb(map, "ec", zzpfVar.zzyJ());
            zzb(map, "ea", zzpfVar.getAction());
            zzb(map, "el", zzpfVar.getLabel());
            zza(map, "ev", zzpfVar.getValue());
        }
        zzpc zzpcVar = (zzpc) zzcVar.zzd(zzpc.class);
        if (zzpcVar != null) {
            zzb(map, "cn", zzpcVar.getName());
            zzb(map, "cs", zzpcVar.getSource());
            zzb(map, "cm", zzpcVar.zzyu());
            zzb(map, "ck", zzpcVar.zzyv());
            zzb(map, "cc", zzpcVar.getContent());
            zzb(map, "ci", zzpcVar.getId());
            zzb(map, "anid", zzpcVar.zzyw());
            zzb(map, "gclid", zzpcVar.zzyx());
            zzb(map, "dclid", zzpcVar.zzyy());
            zzb(map, "aclid", zzpcVar.zzyz());
        }
        zzpg zzpgVar = (zzpg) zzcVar.zzd(zzpg.class);
        if (zzpgVar != null) {
            zzb(map, "exd", zzpgVar.getDescription());
            zza(map, "exf", zzpgVar.zzyK());
        }
        zzpi zzpiVar = (zzpi) zzcVar.zzd(zzpi.class);
        if (zzpiVar != null) {
            zzb(map, "sn", zzpiVar.zzyQ());
            zzb(map, "sa", zzpiVar.getAction());
            zzb(map, "st", zzpiVar.getTarget());
        }
        zzpj zzpjVar = (zzpj) zzcVar.zzd(zzpj.class);
        if (zzpjVar != null) {
            zzb(map, "utv", zzpjVar.zzyR());
            zza(map, "utt", zzpjVar.getTimeInMillis());
            zzb(map, "utc", zzpjVar.zzyJ());
            zzb(map, "utl", zzpjVar.getLabel());
        }
        zzjl zzjlVar = (zzjl) zzcVar.zzd(zzjl.class);
        if (zzjlVar != null) {
            for (Map.Entry<Integer, String> entry2 : zzjlVar.zzhX().entrySet()) {
                String strZzQ = zzc.zzQ(entry2.getKey().intValue());
                if (!TextUtils.isEmpty(strZzQ)) {
                    map.put(strZzQ, entry2.getValue());
                }
            }
        }
        zzjm zzjmVar = (zzjm) zzcVar.zzd(zzjm.class);
        if (zzjmVar != null) {
            for (Map.Entry<Integer, Double> entry3 : zzjmVar.zzhY().entrySet()) {
                String strZzS = zzc.zzS(entry3.getKey().intValue());
                if (!TextUtils.isEmpty(strZzS)) {
                    map.put(strZzS, zzb(entry3.getValue().doubleValue()));
                }
            }
        }
        zzpe zzpeVar = (zzpe) zzcVar.zzd(zzpe.class);
        if (zzpeVar != null) {
            ProductAction productActionZzyF = zzpeVar.zzyF();
            if (productActionZzyF != null) {
                for (Map.Entry<String, String> entry4 : productActionZzyF.build().entrySet()) {
                    if (entry4.getKey().startsWith("&")) {
                        map.put(entry4.getKey().substring(1), entry4.getValue());
                    } else {
                        map.put(entry4.getKey(), entry4.getValue());
                    }
                }
            }
            Iterator<Promotion> it = zzpeVar.zzyI().iterator();
            int i = 1;
            while (it.hasNext()) {
                map.putAll(it.next().zzaX(zzc.zzW(i)));
                i++;
            }
            Iterator<Product> it2 = zzpeVar.zzyG().iterator();
            int i2 = 1;
            while (it2.hasNext()) {
                map.putAll(it2.next().zzaX(zzc.zzU(i2)));
                i2++;
            }
            int i3 = 1;
            for (Map.Entry<String, List<Product>> entry5 : zzpeVar.zzyH().entrySet()) {
                List<Product> value = entry5.getValue();
                String strZzZ = zzc.zzZ(i3);
                Iterator<Product> it3 = value.iterator();
                int i4 = 1;
                while (it3.hasNext()) {
                    map.putAll(it3.next().zzaX(strZzZ + zzc.zzX(i4)));
                    i4++;
                }
                if (!TextUtils.isEmpty(entry5.getKey())) {
                    map.put(strZzZ + "nm", entry5.getKey());
                }
                i3++;
            }
        }
        zzpd zzpdVar = (zzpd) zzcVar.zzd(zzpd.class);
        if (zzpdVar != null) {
            zzb(map, "ul", zzpdVar.getLanguage());
            zza(map, "sd", zzpdVar.zzyA());
            zza(map, "sr", zzpdVar.zzyB(), zzpdVar.zzyC());
            zza(map, "vp", zzpdVar.zzyD(), zzpdVar.zzyE());
        }
        zzpb zzpbVar = (zzpb) zzcVar.zzd(zzpb.class);
        if (zzpbVar != null) {
            zzb(map, "an", zzpbVar.zzkp());
            zzb(map, "aid", zzpbVar.zzuM());
            zzb(map, "aiid", zzpbVar.zzyt());
            zzb(map, "av", zzpbVar.zzkr());
        }
        return map;
    }

    private static String zzh(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            return str;
        }
        if (obj instanceof Double) {
            Double d = (Double) obj;
            if (d.doubleValue() != 0.0d) {
                return zzb(d.doubleValue());
            }
            return null;
        }
        if (!(obj instanceof Boolean)) {
            return String.valueOf(obj);
        }
        if (obj != Boolean.FALSE) {
            return "1";
        }
        return null;
    }

    @Override // com.google.android.gms.measurement.zzi
    public void zzb(com.google.android.gms.measurement.zzc zzcVar) {
        zzx.zzw(zzcVar);
        zzx.zzb(zzcVar.zzyj(), "Can't deliver not submitted measurement");
        zzx.zzcj("deliver should be called on worker thread");
        com.google.android.gms.measurement.zzc zzcVarZzye = zzcVar.zzye();
        zzjo zzjoVar = (zzjo) zzcVarZzye.zze(zzjo.class);
        if (TextUtils.isEmpty(zzjoVar.zzia())) {
            zziu().zzh(zzc(zzcVarZzye), "Ignoring measurement without type");
            return;
        }
        if (TextUtils.isEmpty(zzjoVar.getClientId())) {
            zziu().zzh(zzc(zzcVarZzye), "Ignoring measurement without client id");
            return;
        }
        if (this.zzLf.zziI().getAppOptOut()) {
            return;
        }
        double dZzif = zzjoVar.zzif();
        if (zzam.zza(dZzif, zzjoVar.getClientId())) {
            zzb("Sampling enabled. Hit sampled out. sampling rate", Double.valueOf(dZzif));
            return;
        }
        Map<String, String> mapZzc = zzc(zzcVarZzye);
        mapZzc.put("v", "1");
        mapZzc.put("_v", zze.zzMH);
        mapZzc.put("tid", this.zzLq);
        if (this.zzLf.zziI().isDryRunEnabled()) {
            zzc("Dry run is enabled. GoogleAnalytics would have sent", zzA(mapZzc));
            return;
        }
        HashMap map = new HashMap();
        zzam.zzc(map, "uid", zzjoVar.getUserId());
        zzpb zzpbVar = (zzpb) zzcVar.zzd(zzpb.class);
        if (zzpbVar != null) {
            zzam.zzc(map, "an", zzpbVar.zzkp());
            zzam.zzc(map, "aid", zzpbVar.zzuM());
            zzam.zzc(map, "av", zzpbVar.zzkr());
            zzam.zzc(map, "aiid", zzpbVar.zzyt());
        }
        mapZzc.put("_s", String.valueOf(zzhP().zza(new zzh(0L, zzjoVar.getClientId(), this.zzLq, !TextUtils.isEmpty(zzjoVar.zzib()), 0L, map))));
        zzhP().zza(new zzab(zziu(), mapZzc, zzcVar.zzyh(), true));
    }

    @Override // com.google.android.gms.measurement.zzi
    public Uri zzhI() {
        return this.zzLr;
    }
}
