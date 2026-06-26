package com.comscore.android.id;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.v4.os.EnvironmentCompat;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"NewApi"})
public class API9 {
    public static String getAndroidSerial(Context context) {
        String str;
        if (Integer.valueOf(Build.VERSION.SDK_INT).intValue() >= 9 && (str = Build.SERIAL) != null) {
            try {
                if (str.length() > 0 && !str.equals(EnvironmentCompat.MEDIA_UNKNOWN) && str.length() > 3 && !str.substring(0, 3).equals("***")) {
                    if (!str.substring(0, 3).equals("000")) {
                        return str;
                    }
                }
            } catch (Exception e) {
            }
        }
        return null;
    }
}
