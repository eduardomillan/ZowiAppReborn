package com.comscore.utils;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public class Permissions {
    private static String[] a = null;

    public static Boolean check(Context context, String str) {
        if (a == null) {
            try {
                a = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096).requestedPermissions;
            } catch (Exception e) {
            }
        }
        if (a != null) {
            for (int i = 0; i < a.length; i++) {
                if (a[i].equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }
}
