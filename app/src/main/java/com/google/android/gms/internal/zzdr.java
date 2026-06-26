package com.google.android.gms.internal;

import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.overlay.AdLauncherIntentInfoParcel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@zzgr
public final class zzdr implements zzdk {
    private final com.google.android.gms.ads.internal.zze zzxQ;
    private final zzfc zzxR;
    private final zzdm zzxT;

    public static class zza extends zzhz {
        private final String zzF;
        private final zziz zzoM;
        private final String zzxU = "play.google.com";
        private final String zzxV = "market";
        private final int zzxW = 10;

        public zza(zziz zzizVar, String str) {
            this.zzoM = zzizVar;
            this.zzF = str;
        }

        @Override // com.google.android.gms.internal.zzhz
        public void onStop() {
        }

        public Intent zzaa(String str) {
            Uri uri = Uri.parse(str);
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(268435456);
            intent.setData(uri);
            return intent;
        }

        /* JADX WARN: Code restructure failed: missing block: B:28:0x008f, code lost:
        
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Arrived at landing page, this ideally should not happen. Will open it in browser.");
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x0097, code lost:
        
            r0 = r2;
         */
        /* JADX WARN: Removed duplicated region for block: B:55:0x0116  */
        /* JADX WARN: Removed duplicated region for block: B:59:0x00aa A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:65:0x008f A[EDGE_INSN: B:65:0x008f->B:28:0x008f BREAK  A[LOOP:0: B:3:0x0003->B:36:0x00ad], SYNTHETIC] */
        @Override // com.google.android.gms.internal.zzhz
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void zzbn() {
            /*
                Method dump skipped, instruction units count: 284
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzdr.zza.zzbn():void");
        }
    }

    public static class zzb {
        public Intent zza(Intent intent, ResolveInfo resolveInfo) {
            Intent intent2 = new Intent(intent);
            intent2.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
            return intent2;
        }

        public ResolveInfo zza(Context context, Intent intent) {
            return zza(context, intent, new ArrayList<>());
        }

        public ResolveInfo zza(Context context, Intent intent, ArrayList<ResolveInfo> arrayList) {
            ResolveInfo resolveInfo;
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                return null;
            }
            List<ResolveInfo> listQueryIntentActivities = packageManager.queryIntentActivities(intent, 65536);
            ResolveInfo resolveInfoResolveActivity = packageManager.resolveActivity(intent, 65536);
            if (listQueryIntentActivities != null && resolveInfoResolveActivity != null) {
                int i = 0;
                while (true) {
                    int i2 = i;
                    if (i2 >= listQueryIntentActivities.size()) {
                        break;
                    }
                    ResolveInfo resolveInfo2 = listQueryIntentActivities.get(i2);
                    if (resolveInfoResolveActivity != null && resolveInfoResolveActivity.activityInfo.name.equals(resolveInfo2.activityInfo.name)) {
                        resolveInfo = resolveInfoResolveActivity;
                        break;
                    }
                    i = i2 + 1;
                }
            } else {
                resolveInfo = null;
            }
            arrayList.addAll(listQueryIntentActivities);
            return resolveInfo;
        }

        public Intent zzb(Context context, Map<String, String> map) {
            List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
            ResolveInfo resolveInfoZza;
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            String str = map.get("u");
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            Uri uri = Uri.parse(str);
            boolean z = Boolean.parseBoolean(map.get("use_first_package"));
            boolean z2 = Boolean.parseBoolean(map.get("use_running_process"));
            Uri uriBuild = "http".equalsIgnoreCase(uri.getScheme()) ? uri.buildUpon().scheme("https").build() : "https".equalsIgnoreCase(uri.getScheme()) ? uri.buildUpon().scheme("http").build() : null;
            ArrayList<ResolveInfo> arrayList = new ArrayList<>();
            Intent intentZzd = zzd(uri);
            Intent intentZzd2 = zzd(uriBuild);
            ResolveInfo resolveInfoZza2 = zza(context, intentZzd, arrayList);
            if (resolveInfoZza2 != null) {
                return zza(intentZzd, resolveInfoZza2);
            }
            if (intentZzd2 != null && (resolveInfoZza = zza(context, intentZzd2)) != null) {
                Intent intentZza = zza(intentZzd, resolveInfoZza);
                if (zza(context, intentZza) != null) {
                    return intentZza;
                }
            }
            if (arrayList.size() == 0) {
                return intentZzd;
            }
            if (z2 && activityManager != null && (runningAppProcesses = activityManager.getRunningAppProcesses()) != null) {
                for (ResolveInfo resolveInfo : arrayList) {
                    Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
                    while (it.hasNext()) {
                        if (it.next().processName.equals(resolveInfo.activityInfo.packageName)) {
                            return zza(intentZzd, resolveInfo);
                        }
                    }
                }
            }
            return z ? zza(intentZzd, arrayList.get(0)) : intentZzd;
        }

        public Intent zzd(Uri uri) {
            if (uri == null) {
                return null;
            }
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(268435456);
            intent.setData(uri);
            intent.setAction("android.intent.action.VIEW");
            return intent;
        }
    }

    public zzdr(zzdm zzdmVar, com.google.android.gms.ads.internal.zze zzeVar, zzfc zzfcVar) {
        this.zzxT = zzdmVar;
        this.zzxQ = zzeVar;
        this.zzxR = zzfcVar;
    }

    private static void zza(Context context, Map<String, String> map) {
        if (TextUtils.isEmpty(map.get("u"))) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Destination url cannot be empty.");
            return;
        }
        try {
            com.google.android.gms.ads.internal.zzp.zzbv().zzb(context, new zzb().zzb(context, map));
        } catch (ActivityNotFoundException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH(e.getMessage());
        }
    }

    private static boolean zzc(Map<String, String> map) {
        return "1".equals(map.get("custom_close"));
    }

    private static int zzd(Map<String, String> map) {
        String str = map.get("o");
        if (str != null) {
            if ("p".equalsIgnoreCase(str)) {
                return com.google.android.gms.ads.internal.zzp.zzbx().zzgH();
            }
            if ("l".equalsIgnoreCase(str)) {
                return com.google.android.gms.ads.internal.zzp.zzbx().zzgG();
            }
            if ("c".equalsIgnoreCase(str)) {
                return com.google.android.gms.ads.internal.zzp.zzbx().zzgI();
            }
        }
        return -1;
    }

    private static void zze(zziz zzizVar, Map<String, String> map) {
        String str = map.get("u");
        if (TextUtils.isEmpty(str)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Destination url cannot be empty.");
        } else {
            new zza(zzizVar, str).zzfu();
        }
    }

    private void zzm(boolean z) {
        if (this.zzxR != null) {
            this.zzxR.zzn(z);
        }
    }

    @Override // com.google.android.gms.internal.zzdk
    public void zza(zziz zzizVar, Map<String, String> map) {
        String str = map.get("a");
        if (str == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaH("Action missing from an open GMSG.");
            return;
        }
        if (this.zzxQ != null && !this.zzxQ.zzbe()) {
            this.zzxQ.zzp(map.get("u"));
            return;
        }
        zzja zzjaVarZzhe = zzizVar.zzhe();
        if ("expand".equalsIgnoreCase(str)) {
            if (zzizVar.zzhi()) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaH("Cannot expand WebView that is already expanded.");
                return;
            } else {
                zzm(false);
                zzjaVarZzhe.zza(zzc(map), zzd(map));
                return;
            }
        }
        if ("webapp".equalsIgnoreCase(str)) {
            String str2 = map.get("u");
            zzm(false);
            if (str2 != null) {
                zzjaVarZzhe.zza(zzc(map), zzd(map), str2);
                return;
            } else {
                zzjaVarZzhe.zza(zzc(map), zzd(map), map.get("html"), map.get("baseurl"));
                return;
            }
        }
        if ("in_app_purchase".equalsIgnoreCase(str)) {
            String str3 = map.get("product_id");
            String str4 = map.get("report_urls");
            if (this.zzxT != null) {
                if (str4 == null || str4.isEmpty()) {
                    this.zzxT.zza(str3, new ArrayList<>());
                    return;
                } else {
                    this.zzxT.zza(str3, new ArrayList<>(Arrays.asList(str4.split(" "))));
                    return;
                }
            }
            return;
        }
        if ("app".equalsIgnoreCase(str) && "true".equalsIgnoreCase(map.get("play_store"))) {
            zze(zzizVar, map);
            return;
        }
        if ("app".equalsIgnoreCase(str) && "true".equalsIgnoreCase(map.get("system_browser"))) {
            zza(zzizVar.getContext(), map);
            return;
        }
        zzm(true);
        zzizVar.zzhg();
        String str5 = map.get("u");
        zzjaVarZzhe.zza(new AdLauncherIntentInfoParcel(map.get("i"), !TextUtils.isEmpty(str5) ? com.google.android.gms.ads.internal.zzp.zzbv().zza(zzizVar, str5) : str5, map.get("m"), map.get("p"), map.get("c"), map.get("f"), map.get("e")));
    }
}
