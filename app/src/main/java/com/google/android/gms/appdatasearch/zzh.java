package com.google.android.gms.appdatasearch;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class zzh {
    private static final String[] zzQD = {"text1", "text2", "icon", "intent_action", "intent_data", "intent_data_id", "intent_extra_data", "suggest_large_icon", "intent_activity"};
    private static final Map<String, Integer> zzQE = new HashMap(zzQD.length);

    static {
        for (int i = 0; i < zzQD.length; i++) {
            zzQE.put(zzQD[i], Integer.valueOf(i));
        }
    }

    public static String zzak(int i) {
        if (i < 0 || i >= zzQD.length) {
            return null;
        }
        return zzQD[i];
    }

    public static int zzbz(String str) {
        Integer num = zzQE.get(str);
        if (num == null) {
            throw new IllegalArgumentException("[" + str + "] is not a valid global search section name");
        }
        return num.intValue();
    }

    public static int zzls() {
        return zzQD.length;
    }
}
