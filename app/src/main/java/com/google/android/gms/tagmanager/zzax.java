package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class zzax {
    private static String zzaXk;
    static Map<String, String> zzaXl = new HashMap();

    public static String zzM(String str, String str2) {
        if (str2 != null) {
            return Uri.parse("http://hostname/?" + str).getQueryParameter(str2);
        }
        if (str.length() > 0) {
            return str;
        }
        return null;
    }

    public static void zzeS(String str) {
        synchronized (zzax.class) {
            zzaXk = str;
        }
    }

    public static String zzf(Context context, String str, String str2) {
        String string = zzaXl.get(str);
        if (string == null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("gtm_click_referrers", 0);
            string = sharedPreferences != null ? sharedPreferences.getString(str, "") : "";
            zzaXl.put(str, string);
        }
        return zzM(string, str2);
    }

    public static String zzn(Context context, String str) {
        if (zzaXk == null) {
            synchronized (zzax.class) {
                if (zzaXk == null) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("gtm_install_referrer", 0);
                    if (sharedPreferences != null) {
                        zzaXk = sharedPreferences.getString("referrer", "");
                    } else {
                        zzaXk = "";
                    }
                }
            }
        }
        return zzM(zzaXk, str);
    }

    public static void zzo(Context context, String str) {
        String strZzM = zzM(str, "conv");
        if (strZzM == null || strZzM.length() <= 0) {
            return;
        }
        zzaXl.put(strZzM, str);
        zzcv.zzb(context, "gtm_click_referrers", strZzM, str);
    }
}
