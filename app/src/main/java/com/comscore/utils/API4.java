package com.comscore.utils;

import android.content.Context;
import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
public class API4 {
    private static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";

    public static boolean isPackageInstalledFromGooglePlayStore(Context context) {
        if (Build.VERSION.SDK_INT <= 4) {
            return false;
        }
        try {
            String installerPackageName = context.getPackageManager().getInstallerPackageName(context.getPackageName());
            return GOOGLE_PLAY_STORE_PACKAGE.equals(installerPackageName) || "com.google.play".equals(installerPackageName);
        } catch (Exception e) {
            return false;
        }
    }
}
