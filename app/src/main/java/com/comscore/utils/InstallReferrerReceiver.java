package com.comscore.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.comscore.analytics.Core;
import com.comscore.analytics.comScore;
import com.comscore.applications.EventType;
import java.net.URLDecoder;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class InstallReferrerReceiver extends BroadcastReceiver {
    public static final String CS_NONE = "CS_NONE";
    public static final String CS_REFERRER_PREF_KEY = "CS_REFERRER_PREF_KEY";
    public static final String REFERRER_LABEL = "ns_ap_referrer";
    private static final String a = "InstallReferrerReceiver";

    private void a(String str, Context context) {
        SharedPreferences.Editor editorEdit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editorEdit.putString(CS_REFERRER_PREF_KEY, str);
        editorEdit.commit();
        CSLog.d(a, "Stored data");
    }

    public static HashMap<String, String> retrieveReferrerLabels(Context context) {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (defaultSharedPreferences.contains(CS_REFERRER_PREF_KEY)) {
            SharedPreferences.Editor editorEdit = defaultSharedPreferences.edit();
            String string = defaultSharedPreferences.getString(CS_REFERRER_PREF_KEY, CS_NONE);
            CSLog.d(comScore.getAppName().toString(), "referrer was set as: '" + string + "'");
            editorEdit.remove(CS_REFERRER_PREF_KEY);
            editorEdit.commit();
            if (string != null && string.length() > 0 && !string.equals(CS_NONE)) {
                return splitReferrer(string);
            }
        }
        return null;
    }

    public static HashMap<String, String> splitReferrer(String str) {
        HashMap<String, String> map = new HashMap<>();
        if (str != null && str.length() > 0) {
            for (String str2 : str.split("&")) {
                int iIndexOf = str2.indexOf("=");
                if (iIndexOf >= 0) {
                    map.put(str2.substring(0, iIndexOf), str2.substring(iIndexOf + 1));
                } else {
                    map.put(REFERRER_LABEL, str2);
                }
            }
            if (map.size() > 0 && !map.containsKey(REFERRER_LABEL)) {
                map.put(REFERRER_LABEL, "1");
            }
        }
        return map;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String stringExtra;
        CSLog.d(a, "onReceive()");
        String strDecode = CS_NONE;
        if (intent != null) {
            try {
                if (intent.getAction().equals("com.android.vending.INSTALL_REFERRER") && (stringExtra = intent.getStringExtra("referrer")) != null) {
                    strDecode = URLDecoder.decode(stringExtra, "UTF-8");
                    context.getSharedPreferences("referrer", 0).edit().putString("referrer", strDecode).commit();
                }
            } catch (Exception e) {
                CSLog.e(a, "onReceive()" + e.getMessage());
            }
        }
        CSLog.d(a, "Received referrer: '" + strDecode + "'");
        if (strDecode != CS_NONE) {
            Core core = comScore.getCore();
            if (core == null || core.getAppContext() == null || core.getColdStartCount() <= 0) {
                a(strDecode, context);
            } else {
                core.notify(EventType.HIDDEN, splitReferrer(strDecode), true);
            }
        }
    }
}
