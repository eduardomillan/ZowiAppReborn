package com.comscore.android.id;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"NewApi"})
public class API4 {
    private static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";
    private static boolean a = false;
    private static boolean b = false;

    public static boolean isPackageInstalledFromGooglePlayStore(Context context) {
        if (a) {
            return b;
        }
        if (Build.VERSION.SDK_INT > 4) {
            try {
                String installerPackageName = context.getPackageManager().getInstallerPackageName(context.getPackageName());
                if (GOOGLE_PLAY_STORE_PACKAGE.equals(installerPackageName) || "com.google.play".equals(installerPackageName)) {
                    a = true;
                    b = true;
                    return true;
                }
            } catch (Exception e) {
                a = true;
                b = false;
                return false;
            }
        }
        a = true;
        b = false;
        return false;
    }
}
