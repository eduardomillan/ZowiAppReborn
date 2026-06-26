package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzdj {
    public static final zzdk zzxo = new zzdk() { // from class: com.google.android.gms.internal.zzdj.1
        @Override // com.google.android.gms.internal.zzdk
        public void zza(zziz zzizVar, Map<String, String> map) {
        }
    };
    public static final zzdk zzxp = new zzdk() { // from class: com.google.android.gms.internal.zzdj.3
        @Override // com.google.android.gms.internal.zzdk
        public void zza(zziz zzizVar, Map<String, String> map) {
            String str = map.get("urls");
            if (TextUtils.isEmpty(str)) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("URLs missing in canOpenURLs GMSG.");
                return;
            }
            String[] strArrSplit = str.split(",");
            HashMap map2 = new HashMap();
            PackageManager packageManager = zzizVar.getContext().getPackageManager();
            for (String str2 : strArrSplit) {
                String[] strArrSplit2 = str2.split(";", 2);
                map2.put(str2, Boolean.valueOf(packageManager.resolveActivity(new Intent(strArrSplit2.length > 1 ? strArrSplit2[1].trim() : "android.intent.action.VIEW", Uri.parse(strArrSplit2[0].trim())), 65536) != null));
            }
            zzizVar.zzb("openableURLs", map2);
        }
    };
    public static final zzdk zzxq = new zzdk() { // from class: com.google.android.gms.internal.zzdj.4
        @Override // com.google.android.gms.internal.zzdk
        public void zza(zziz zzizVar, Map<String, String> map) {
            PackageManager packageManager = zzizVar.getContext().getPackageManager();
            try {
                try {
                    JSONArray jSONArray = new JSONObject(map.get("data")).getJSONArray("intents");
                    JSONObject jSONObject = new JSONObject();
                    for (int i = 0; i < jSONArray.length(); i++) {
                        try {
                            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                            String strOptString = jSONObject2.optString("id");
                            String strOptString2 = jSONObject2.optString("u");
                            String strOptString3 = jSONObject2.optString("i");
                            String strOptString4 = jSONObject2.optString("m");
                            String strOptString5 = jSONObject2.optString("p");
                            String strOptString6 = jSONObject2.optString("c");
                            jSONObject2.optString("f");
                            jSONObject2.optString("e");
                            Intent intent = new Intent();
                            if (!TextUtils.isEmpty(strOptString2)) {
                                intent.setData(Uri.parse(strOptString2));
                            }
                            if (!TextUtils.isEmpty(strOptString3)) {
                                intent.setAction(strOptString3);
                            }
                            if (!TextUtils.isEmpty(strOptString4)) {
                                intent.setType(strOptString4);
                            }
                            if (!TextUtils.isEmpty(strOptString5)) {
                                intent.setPackage(strOptString5);
                            }
                            if (!TextUtils.isEmpty(strOptString6)) {
                                String[] strArrSplit = strOptString6.split("/", 2);
                                if (strArrSplit.length == 2) {
                                    intent.setComponent(new ComponentName(strArrSplit[0], strArrSplit[1]));
                                }
                            }
                            try {
                                jSONObject.put(strOptString, packageManager.resolveActivity(intent, 65536) != null);
                            } catch (JSONException e) {
                                com.google.android.gms.ads.internal.util.client.zzb.zzb("Error constructing openable urls response.", e);
                            }
                        } catch (JSONException e2) {
                            com.google.android.gms.ads.internal.util.client.zzb.zzb("Error parsing the intent data.", e2);
                        }
                    }
                    zzizVar.zzb("openableIntents", jSONObject);
                } catch (JSONException e3) {
                    zzizVar.zzb("openableIntents", new JSONObject());
                }
            } catch (JSONException e4) {
                zzizVar.zzb("openableIntents", new JSONObject());
            }
        }
    };
    public static final zzdk zzxr = new zzdk() { // from class: com.google.android.gms.internal.zzdj.5
        @Override // com.google.android.gms.internal.zzdk
        public void zza(zziz zzizVar, Map<String, String> map) {
            zzan zzanVarZzhg;
            String str = map.get("u");
            if (str == null) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("URL missing from click GMSG.");
                return;
            }
            Uri uri = Uri.parse(str);
            try {
                zzanVarZzhg = zzizVar.zzhg();
            } catch (zzao e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Unable to append parameter to URL: " + str);
            }
            Uri uriZza = (zzanVarZzhg == null || !zzanVarZzhg.zzb(uri)) ? uri : zzanVarZzhg.zza(uri, zzizVar.getContext());
            new zzij(zzizVar.getContext(), zzizVar.zzhh().zzJu, uriZza.toString()).zzfu();
        }
    };
    public static final zzdk zzxs = new zzdk() { // from class: com.google.android.gms.internal.zzdj.6
        @Override // com.google.android.gms.internal.zzdk
        public void zza(zziz zzizVar, Map<String, String> map) {
            com.google.android.gms.ads.internal.overlay.zzd zzdVarZzhc = zzizVar.zzhc();
            if (zzdVarZzhc != null) {
                zzdVarZzhc.close();
                return;
            }
            com.google.android.gms.ads.internal.overlay.zzd zzdVarZzhd = zzizVar.zzhd();
            if (zzdVarZzhd != null) {
                zzdVarZzhd.close();
            } else {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("A GMSG tried to close something that wasn't an overlay.");
            }
        }
    };
    public static final zzdk zzxt = new zzdk() { // from class: com.google.android.gms.internal.zzdj.7
        @Override // com.google.android.gms.internal.zzdk
        public void zza(zziz zzizVar, Map<String, String> map) {
            zzizVar.zzD("1".equals(map.get("custom_close")));
        }
    };
    public static final zzdk zzxu = new zzdk() { // from class: com.google.android.gms.internal.zzdj.8
        @Override // com.google.android.gms.internal.zzdk
        public void zza(zziz zzizVar, Map<String, String> map) {
            String str = map.get("u");
            if (str == null) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("URL missing from httpTrack GMSG.");
            } else {
                new zzij(zzizVar.getContext(), zzizVar.zzhh().zzJu, str).zzfu();
            }
        }
    };
    public static final zzdk zzxv = new zzdk() { // from class: com.google.android.gms.internal.zzdj.9
        @Override // com.google.android.gms.internal.zzdk
        public void zza(zziz zzizVar, Map<String, String> map) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaG("Received log message: " + map.get("string"));
        }
    };
    public static final zzdk zzxw = new zzdk() { // from class: com.google.android.gms.internal.zzdj.10
        @Override // com.google.android.gms.internal.zzdk
        public void zza(zziz zzizVar, Map<String, String> map) {
            String str = map.get("tx");
            String str2 = map.get("ty");
            String str3 = map.get("td");
            try {
                int i = Integer.parseInt(str);
                int i2 = Integer.parseInt(str2);
                int i3 = Integer.parseInt(str3);
                zzan zzanVarZzhg = zzizVar.zzhg();
                if (zzanVarZzhg != null) {
                    zzanVarZzhg.zzab().zza(i, i2, i3);
                }
            } catch (NumberFormatException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Could not parse touch parameters from gmsg.");
            }
        }
    };
    public static final zzdk zzxx = new zzdk() { // from class: com.google.android.gms.internal.zzdj.2
        @Override // com.google.android.gms.internal.zzdk
        public void zza(zziz zzizVar, Map<String, String> map) {
            if (zzby.zzvs.get().booleanValue()) {
                zzizVar.zzE(!Boolean.parseBoolean(map.get("disabled")));
            }
        }
    };
    public static final zzdk zzxy = new zzds();
    public static final zzdk zzxz = new zzdw();
    public static final zzdk zzxA = new zzdi();
}
