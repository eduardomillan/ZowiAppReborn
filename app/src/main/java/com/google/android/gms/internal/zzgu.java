package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import com.bq.error_reporting.ErrorReporter;
import com.comscore.android.id.IdHelperAndroid;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.SearchAdRequestParcel;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.appdatasearch.DocumentContents;
import com.google.android.gms.appdatasearch.DocumentSection;
import com.google.android.gms.appdatasearch.UsageInfo;
import com.google.android.gms.appindexing.AndroidAppUri;
import com.google.android.gms.internal.zzhb;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzgu {
    private static final SimpleDateFormat zzFN = new SimpleDateFormat("yyyyMMdd", Locale.US);

    private static String zzI(int i) {
        return String.format(Locale.US, "#%06x", Integer.valueOf(16777215 & i));
    }

    /* JADX WARN: Removed duplicated region for block: B:86:0x024e A[PHI: r18
      0x024e: PHI (r18v3 int) = (r18v2 int), (r18v5 int) binds: [B:74:0x01ab, B:79:0x01bc] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.android.gms.ads.internal.request.AdResponseParcel zza(android.content.Context r34, com.google.android.gms.ads.internal.request.AdRequestInfoParcel r35, java.lang.String r36) {
        /*
            Method dump skipped, instruction units count: 607
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgu.zza(android.content.Context, com.google.android.gms.ads.internal.request.AdRequestInfoParcel, java.lang.String):com.google.android.gms.ads.internal.request.AdResponseParcel");
    }

    public static JSONObject zza(Context context, AdRequestInfoParcel adRequestInfoParcel, zzgy zzgyVar, zzhb.zza zzaVar, Location location, zzbr zzbrVar, String str, String str2, List<String> list) {
        try {
            HashMap map = new HashMap();
            if (list.size() > 0) {
                map.put("eid", TextUtils.join(",", list));
            }
            if (adRequestInfoParcel.zzEm != null) {
                map.put("ad_pos", adRequestInfoParcel.zzEm);
            }
            zza((HashMap<String, Object>) map, adRequestInfoParcel.zzEn);
            map.put("format", adRequestInfoParcel.zzqn.zzte);
            if (adRequestInfoParcel.zzqn.width == -1) {
                map.put("smart_w", "full");
            }
            if (adRequestInfoParcel.zzqn.height == -2) {
                map.put("smart_h", "auto");
            }
            if (adRequestInfoParcel.zzqn.zzti) {
                map.put("fluid", "height");
            }
            if (adRequestInfoParcel.zzqn.zztg != null) {
                StringBuilder sb = new StringBuilder();
                for (AdSizeParcel adSizeParcel : adRequestInfoParcel.zzqn.zztg) {
                    if (sb.length() != 0) {
                        sb.append("|");
                    }
                    sb.append(adSizeParcel.width == -1 ? (int) (adSizeParcel.widthPixels / zzgyVar.zzEz) : adSizeParcel.width);
                    sb.append("x");
                    sb.append(adSizeParcel.height == -2 ? (int) (adSizeParcel.heightPixels / zzgyVar.zzEz) : adSizeParcel.height);
                }
                map.put("sz", sb);
            }
            if (adRequestInfoParcel.zzEt != 0) {
                map.put("native_version", Integer.valueOf(adRequestInfoParcel.zzEt));
                map.put("native_templates", adRequestInfoParcel.zzqD);
                map.put("native_image_orientation", zzc(adRequestInfoParcel.zzqB));
                if (!adRequestInfoParcel.zzEE.isEmpty()) {
                    map.put("native_custom_templates", adRequestInfoParcel.zzEE);
                }
            }
            map.put("slotname", adRequestInfoParcel.zzqh);
            map.put("pn", adRequestInfoParcel.applicationInfo.packageName);
            if (adRequestInfoParcel.zzEo != null) {
                map.put("vc", Integer.valueOf(adRequestInfoParcel.zzEo.versionCode));
            }
            map.put("ms", str2);
            map.put("seq_num", adRequestInfoParcel.zzEq);
            map.put("session_id", adRequestInfoParcel.zzEr);
            map.put("js", adRequestInfoParcel.zzqj.zzJu);
            zza((HashMap<String, Object>) map, zzgyVar, zzaVar);
            map.put("fdz", Integer.valueOf(zzbrVar.zzdd()));
            map.put("platform", Build.MANUFACTURER);
            map.put("submodel", Build.MODEL);
            if (adRequestInfoParcel.zzEn.versionCode >= 2 && adRequestInfoParcel.zzEn.zzsJ != null) {
                zza((HashMap<String, Object>) map, adRequestInfoParcel.zzEn.zzsJ);
            }
            if (adRequestInfoParcel.versionCode >= 2) {
                map.put("quality_signals", adRequestInfoParcel.zzEs);
            }
            if (adRequestInfoParcel.versionCode >= 4 && adRequestInfoParcel.zzEv) {
                map.put("forceHttps", Boolean.valueOf(adRequestInfoParcel.zzEv));
            }
            Bundle bundle = (adRequestInfoParcel.versionCode < 4 || adRequestInfoParcel.zzEu == null) ? new Bundle() : adRequestInfoParcel.zzEu;
            zza(context, adRequestInfoParcel, bundle);
            map.put("content_info", bundle);
            if (adRequestInfoParcel.versionCode >= 5) {
                map.put("u_sd", Float.valueOf(adRequestInfoParcel.zzEz));
                map.put("sh", Integer.valueOf(adRequestInfoParcel.zzEy));
                map.put("sw", Integer.valueOf(adRequestInfoParcel.zzEx));
            } else {
                map.put("u_sd", Float.valueOf(zzgyVar.zzEz));
                map.put("sh", Integer.valueOf(zzgyVar.zzEy));
                map.put("sw", Integer.valueOf(zzgyVar.zzEx));
            }
            if (adRequestInfoParcel.versionCode >= 6) {
                if (!TextUtils.isEmpty(adRequestInfoParcel.zzEA)) {
                    try {
                        map.put("view_hierarchy", new JSONObject(adRequestInfoParcel.zzEA));
                    } catch (JSONException e) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzd("Problem serializing view hierarchy to JSON", e);
                    }
                }
                map.put("correlation_id", Long.valueOf(adRequestInfoParcel.zzEB));
            }
            if (adRequestInfoParcel.versionCode >= 7) {
                map.put("request_id", adRequestInfoParcel.zzEC);
            }
            if (adRequestInfoParcel.versionCode >= 11 && adRequestInfoParcel.zzEG != null) {
                map.put("capability", adRequestInfoParcel.zzEG.toBundle());
            }
            zza((HashMap<String, Object>) map, str);
            if (adRequestInfoParcel.versionCode >= 12 && !TextUtils.isEmpty(adRequestInfoParcel.zzEH)) {
                map.put("anchor", adRequestInfoParcel.zzEH);
            }
            if (com.google.android.gms.ads.internal.util.client.zzb.zzN(2)) {
                com.google.android.gms.ads.internal.util.client.zzb.v("Ad Request JSON: " + com.google.android.gms.ads.internal.zzp.zzbv().zzz(map).toString(2));
            }
            return com.google.android.gms.ads.internal.zzp.zzbv().zzz(map);
        } catch (JSONException e2) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Problem serializing ad request to JSON: " + e2.getMessage());
            return null;
        }
    }

    static void zza(Context context, AdRequestInfoParcel adRequestInfoParcel, Bundle bundle) {
        if (!zzby.zzuZ.get().booleanValue()) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaG("App index is not enabled");
            return;
        }
        if (!zzmm.zzjA()) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaG("Not on service, return");
            return;
        }
        if (com.google.android.gms.ads.internal.client.zzl.zzcF().zzgT()) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaG("Cannot invoked on UI thread");
            return;
        }
        if (adRequestInfoParcel == null || adRequestInfoParcel.zzEo == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Invalid ad request info");
            return;
        }
        String str = adRequestInfoParcel.zzEo.packageName;
        if (TextUtils.isEmpty(str)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Fail to get package name");
            return;
        }
        try {
            zza(zzd(context, str), str, bundle);
        } catch (RuntimeException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaG("Fail to add app index to content info");
        }
    }

    static void zza(UsageInfo usageInfo, String str, Bundle bundle) {
        if (usageInfo == null || usageInfo.zzlu() == null) {
            return;
        }
        DocumentContents documentContentsZzlu = usageInfo.zzlu();
        String strZzln = documentContentsZzlu.zzln();
        if (!TextUtils.isEmpty(strZzln)) {
            bundle.putString("web_url", strZzln);
        }
        try {
            DocumentSection documentSectionZzbw = documentContentsZzlu.zzbw("intent_data");
            if (documentSectionZzbw == null || TextUtils.isEmpty(documentSectionZzbw.zzQj)) {
                return;
            }
            bundle.putString("app_uri", AndroidAppUri.newAndroidAppUri(str, Uri.parse(documentSectionZzbw.zzQj)).toString());
        } catch (IllegalArgumentException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Failed to parse the third-party Android App URI");
        }
    }

    private static void zza(HashMap<String, Object> map, Location location) {
        HashMap map2 = new HashMap();
        Float fValueOf = Float.valueOf(location.getAccuracy() * 1000.0f);
        Long lValueOf = Long.valueOf(location.getTime() * 1000);
        Long lValueOf2 = Long.valueOf((long) (location.getLatitude() * 1.0E7d));
        Long lValueOf3 = Long.valueOf((long) (location.getLongitude() * 1.0E7d));
        map2.put("radius", fValueOf);
        map2.put("lat", lValueOf2);
        map2.put("long", lValueOf3);
        map2.put("time", lValueOf);
        map.put("uule", map2);
    }

    private static void zza(HashMap<String, Object> map, AdRequestParcel adRequestParcel) {
        String strZzgy = zzhy.zzgy();
        if (strZzgy != null) {
            map.put("abf", strZzgy);
        }
        if (adRequestParcel.zzsB != -1) {
            map.put("cust_age", zzFN.format(new Date(adRequestParcel.zzsB)));
        }
        if (adRequestParcel.extras != null) {
            map.put(ErrorReporter.DEFAULT_CATEGORY_NAME, adRequestParcel.extras);
        }
        if (adRequestParcel.zzsC != -1) {
            map.put("cust_gender", Integer.valueOf(adRequestParcel.zzsC));
        }
        if (adRequestParcel.zzsD != null) {
            map.put("kw", adRequestParcel.zzsD);
        }
        if (adRequestParcel.zzsF != -1) {
            map.put("tag_for_child_directed_treatment", Integer.valueOf(adRequestParcel.zzsF));
        }
        if (adRequestParcel.zzsE) {
            map.put("adtest", "on");
        }
        if (adRequestParcel.versionCode >= 2) {
            if (adRequestParcel.zzsG) {
                map.put("d_imp_hdr", 1);
            }
            if (!TextUtils.isEmpty(adRequestParcel.zzsH)) {
                map.put("ppid", adRequestParcel.zzsH);
            }
            if (adRequestParcel.zzsI != null) {
                zza(map, adRequestParcel.zzsI);
            }
        }
        if (adRequestParcel.versionCode >= 3 && adRequestParcel.zzsK != null) {
            map.put("url", adRequestParcel.zzsK);
        }
        if (adRequestParcel.versionCode >= 5) {
            if (adRequestParcel.zzsM != null) {
                map.put("custom_targeting", adRequestParcel.zzsM);
            }
            if (adRequestParcel.zzsN != null) {
                map.put("category_exclusions", adRequestParcel.zzsN);
            }
            if (adRequestParcel.zzsO != null) {
                map.put("request_agent", adRequestParcel.zzsO);
            }
        }
        if (adRequestParcel.versionCode < 6 || adRequestParcel.zzsP == null) {
            return;
        }
        map.put("request_pkg", adRequestParcel.zzsP);
    }

    private static void zza(HashMap<String, Object> map, SearchAdRequestParcel searchAdRequestParcel) {
        String str;
        String str2 = null;
        if (Color.alpha(searchAdRequestParcel.zztP) != 0) {
            map.put("acolor", zzI(searchAdRequestParcel.zztP));
        }
        if (Color.alpha(searchAdRequestParcel.backgroundColor) != 0) {
            map.put("bgcolor", zzI(searchAdRequestParcel.backgroundColor));
        }
        if (Color.alpha(searchAdRequestParcel.zztQ) != 0 && Color.alpha(searchAdRequestParcel.zztR) != 0) {
            map.put("gradientto", zzI(searchAdRequestParcel.zztQ));
            map.put("gradientfrom", zzI(searchAdRequestParcel.zztR));
        }
        if (Color.alpha(searchAdRequestParcel.zztS) != 0) {
            map.put("bcolor", zzI(searchAdRequestParcel.zztS));
        }
        map.put("bthick", Integer.toString(searchAdRequestParcel.zztT));
        switch (searchAdRequestParcel.zztU) {
            case 0:
                str = IdHelperAndroid.NO_ID_AVAILABLE;
                break;
            case 1:
                str = "dashed";
                break;
            case 2:
                str = "dotted";
                break;
            case 3:
                str = "solid";
                break;
            default:
                str = null;
                break;
        }
        if (str != null) {
            map.put("btype", str);
        }
        switch (searchAdRequestParcel.zztV) {
            case 0:
                str2 = "light";
                break;
            case 1:
                str2 = "medium";
                break;
            case 2:
                str2 = "dark";
                break;
        }
        if (str2 != null) {
            map.put("callbuttoncolor", str2);
        }
        if (searchAdRequestParcel.zztW != null) {
            map.put("channel", searchAdRequestParcel.zztW);
        }
        if (Color.alpha(searchAdRequestParcel.zztX) != 0) {
            map.put("dcolor", zzI(searchAdRequestParcel.zztX));
        }
        if (searchAdRequestParcel.zztY != null) {
            map.put("font", searchAdRequestParcel.zztY);
        }
        if (Color.alpha(searchAdRequestParcel.zztZ) != 0) {
            map.put("hcolor", zzI(searchAdRequestParcel.zztZ));
        }
        map.put("headersize", Integer.toString(searchAdRequestParcel.zzua));
        if (searchAdRequestParcel.zzub != null) {
            map.put("q", searchAdRequestParcel.zzub);
        }
    }

    private static void zza(HashMap<String, Object> map, zzgy zzgyVar, zzhb.zza zzaVar) {
        map.put("am", Integer.valueOf(zzgyVar.zzGs));
        map.put("cog", zzx(zzgyVar.zzGt));
        map.put("coh", zzx(zzgyVar.zzGu));
        if (!TextUtils.isEmpty(zzgyVar.zzGv)) {
            map.put("carrier", zzgyVar.zzGv);
        }
        map.put("gl", zzgyVar.zzGw);
        if (zzgyVar.zzGx) {
            map.put("simulator", 1);
        }
        if (zzgyVar.zzGy) {
            map.put("is_sidewinder", 1);
        }
        map.put("ma", zzx(zzgyVar.zzGz));
        map.put("sp", zzx(zzgyVar.zzGA));
        map.put("hl", zzgyVar.zzGB);
        if (!TextUtils.isEmpty(zzgyVar.zzGC)) {
            map.put("mv", zzgyVar.zzGC);
        }
        map.put("muv", Integer.valueOf(zzgyVar.zzGD));
        if (zzgyVar.zzGE != -2) {
            map.put("cnt", Integer.valueOf(zzgyVar.zzGE));
        }
        map.put("gnt", Integer.valueOf(zzgyVar.zzGF));
        map.put("pt", Integer.valueOf(zzgyVar.zzGG));
        map.put("rm", Integer.valueOf(zzgyVar.zzGH));
        map.put("riv", Integer.valueOf(zzgyVar.zzGI));
        Bundle bundle = new Bundle();
        bundle.putString("build", zzgyVar.zzGN);
        Bundle bundle2 = new Bundle();
        bundle2.putBoolean("is_charging", zzgyVar.zzGK);
        bundle2.putDouble("battery_level", zzgyVar.zzGJ);
        bundle.putBundle("battery", bundle2);
        Bundle bundle3 = new Bundle();
        bundle3.putInt("active_network_state", zzgyVar.zzGM);
        bundle3.putBoolean("active_network_metered", zzgyVar.zzGL);
        if (zzaVar != null) {
            Bundle bundle4 = new Bundle();
            bundle4.putInt("predicted_latency_micros", zzaVar.zzGS);
            bundle4.putLong("predicted_down_throughput_bps", zzaVar.zzGT);
            bundle4.putLong("predicted_up_throughput_bps", zzaVar.zzGU);
            bundle3.putBundle("predictions", bundle4);
        }
        bundle.putBundle("network", bundle3);
        map.put("device", bundle);
    }

    private static void zza(HashMap<String, Object> map, String str) {
        if (str != null) {
            HashMap map2 = new HashMap();
            map2.put("token", str);
            map.put("pan", map2);
        }
    }

    private static String zzc(NativeAdOptionsParcel nativeAdOptionsParcel) {
        switch (nativeAdOptionsParcel != null ? nativeAdOptionsParcel.zzwS : 0) {
            case 1:
                return "portrait";
            case 2:
                return "landscape";
            default:
                return "any";
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0085  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static com.google.android.gms.appdatasearch.UsageInfo zzd(android.content.Context r6, java.lang.String r7) throws java.lang.Throwable {
        /*
            r1 = 0
            com.google.android.gms.common.api.GoogleApiClient$Builder r0 = new com.google.android.gms.common.api.GoogleApiClient$Builder     // Catch: java.lang.SecurityException -> L73 java.lang.Throwable -> L81
            r0.<init>(r6)     // Catch: java.lang.SecurityException -> L73 java.lang.Throwable -> L81
            com.google.android.gms.common.api.Api<com.google.android.gms.common.api.Api$ApiOptions$NoOptions> r2 = com.google.android.gms.appdatasearch.zza.zzPV     // Catch: java.lang.SecurityException -> L73 java.lang.Throwable -> L81
            com.google.android.gms.common.api.GoogleApiClient$Builder r0 = r0.addApi(r2)     // Catch: java.lang.SecurityException -> L73 java.lang.Throwable -> L81
            com.google.android.gms.common.api.GoogleApiClient r2 = r0.build()     // Catch: java.lang.SecurityException -> L73 java.lang.Throwable -> L81
            r2.connect()     // Catch: java.lang.Throwable -> L89 java.lang.SecurityException -> L8f
            com.google.android.gms.appdatasearch.GetRecentContextCall$Request$zza r0 = new com.google.android.gms.appdatasearch.GetRecentContextCall$Request$zza     // Catch: java.lang.Throwable -> L89 java.lang.SecurityException -> L8f
            r0.<init>()     // Catch: java.lang.Throwable -> L89 java.lang.SecurityException -> L8f
            r3 = 1
            com.google.android.gms.appdatasearch.GetRecentContextCall$Request$zza r0 = r0.zzL(r3)     // Catch: java.lang.Throwable -> L89 java.lang.SecurityException -> L8f
            com.google.android.gms.appdatasearch.GetRecentContextCall$Request$zza r0 = r0.zzby(r7)     // Catch: java.lang.Throwable -> L89 java.lang.SecurityException -> L8f
            com.google.android.gms.appdatasearch.GetRecentContextCall$Request r0 = r0.zzlr()     // Catch: java.lang.Throwable -> L89 java.lang.SecurityException -> L8f
            com.google.android.gms.appdatasearch.zzk r3 = com.google.android.gms.appdatasearch.zza.zzPW     // Catch: java.lang.Throwable -> L89 java.lang.SecurityException -> L8f
            com.google.android.gms.common.api.PendingResult r0 = r3.zza(r2, r0)     // Catch: java.lang.Throwable -> L89 java.lang.SecurityException -> L8f
            r4 = 1
            java.util.concurrent.TimeUnit r3 = java.util.concurrent.TimeUnit.SECONDS     // Catch: java.lang.Throwable -> L89 java.lang.SecurityException -> L8f
            com.google.android.gms.common.api.Result r0 = r0.await(r4, r3)     // Catch: java.lang.Throwable -> L89 java.lang.SecurityException -> L8f
            com.google.android.gms.appdatasearch.GetRecentContextCall$Response r0 = (com.google.android.gms.appdatasearch.GetRecentContextCall.Response) r0     // Catch: java.lang.Throwable -> L89 java.lang.SecurityException -> L8f
            if (r0 == 0) goto L41
            com.google.android.gms.common.api.Status r3 = r0.getStatus()     // Catch: java.lang.Throwable -> L89 java.lang.SecurityException -> L8f
            boolean r3 = r3.isSuccess()     // Catch: java.lang.Throwable -> L89 java.lang.SecurityException -> L8f
            if (r3 != 0) goto L4c
        L41:
            java.lang.String r0 = "Fail to obtain recent context call"
            com.google.android.gms.ads.internal.util.client.zzb.zzaG(r0)     // Catch: java.lang.Throwable -> L89 java.lang.SecurityException -> L8f
            if (r2 == 0) goto L4b
            r2.disconnect()
        L4b:
            return r1
        L4c:
            java.util.List<com.google.android.gms.appdatasearch.UsageInfo> r3 = r0.zzQB     // Catch: java.lang.Throwable -> L89 java.lang.SecurityException -> L8f
            if (r3 == 0) goto L58
            java.util.List<com.google.android.gms.appdatasearch.UsageInfo> r3 = r0.zzQB     // Catch: java.lang.Throwable -> L89 java.lang.SecurityException -> L8f
            boolean r3 = r3.isEmpty()     // Catch: java.lang.Throwable -> L89 java.lang.SecurityException -> L8f
            if (r3 == 0) goto L63
        L58:
            java.lang.String r0 = "Fail to obtain recent context"
            com.google.android.gms.ads.internal.util.client.zzb.zzaG(r0)     // Catch: java.lang.Throwable -> L89 java.lang.SecurityException -> L8f
            if (r2 == 0) goto L4b
            r2.disconnect()
            goto L4b
        L63:
            java.util.List<com.google.android.gms.appdatasearch.UsageInfo> r0 = r0.zzQB     // Catch: java.lang.Throwable -> L89 java.lang.SecurityException -> L8f
            r3 = 0
            java.lang.Object r0 = r0.get(r3)     // Catch: java.lang.Throwable -> L89 java.lang.SecurityException -> L8f
            com.google.android.gms.appdatasearch.UsageInfo r0 = (com.google.android.gms.appdatasearch.UsageInfo) r0     // Catch: java.lang.Throwable -> L89 java.lang.SecurityException -> L8f
            if (r2 == 0) goto L71
            r2.disconnect()
        L71:
            r1 = r0
            goto L4b
        L73:
            r0 = move-exception
            r0 = r1
        L75:
            java.lang.String r2 = "Fail to get recent context"
            com.google.android.gms.ads.internal.util.client.zzb.zzaH(r2)     // Catch: java.lang.Throwable -> L8b
            if (r0 == 0) goto L92
            r0.disconnect()
            r0 = r1
            goto L71
        L81:
            r0 = move-exception
            r2 = r1
        L83:
            if (r2 == 0) goto L88
            r2.disconnect()
        L88:
            throw r0
        L89:
            r0 = move-exception
            goto L83
        L8b:
            r1 = move-exception
            r2 = r0
            r0 = r1
            goto L83
        L8f:
            r0 = move-exception
            r0 = r2
            goto L75
        L92:
            r0 = r1
            goto L71
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzgu.zzd(android.content.Context, java.lang.String):com.google.android.gms.appdatasearch.UsageInfo");
    }

    private static Integer zzx(boolean z) {
        return Integer.valueOf(z ? 1 : 0);
    }
}
