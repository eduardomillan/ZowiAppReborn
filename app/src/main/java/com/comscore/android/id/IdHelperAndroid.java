package com.comscore.android.id;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.support.v4.os.EnvironmentCompat;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
public class IdHelperAndroid {
    public static final String NO_ID_AVAILABLE = "none";
    private static boolean c = false;
    private static final String a = "com.google.android.gms";
    private static final String b = "com.google.android.gms.ads.identifier.service.START";
    private static final boolean d = false;

    private static String a(Context context) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Cannot be called from the main thread");
        }
        String id = "";
        b bVar = new b();
        Intent intent = new Intent(b);
        intent.setPackage(a);
        if (context.bindService(intent, bVar, 1)) {
            try {
                id = new c(bVar.getBinder()).getId();
            } catch (Exception e) {
            } finally {
                context.unbindService(bVar);
            }
        }
        return id;
    }

    private static boolean b(Context context) {
        if (Build.VERSION.SDK_INT <= 4) {
            return false;
        }
        if (d) {
            return true;
        }
        return API4.isPackageInstalledFromGooglePlayStore(context);
    }

    private static final String c(Context context) {
        String strE = e(context);
        if (strE == null || strE.length() <= 0 || strE.equals(EnvironmentCompat.MEDIA_UNKNOWN) || strE.length() <= 3 || strE.substring(0, 3).equals("***") || strE.substring(0, 3).equals("000")) {
            return null;
        }
        return strE;
    }

    private static final String d(Context context) {
        String strF = f(context);
        if (strF == null || strF.length() <= 0) {
            return null;
        }
        return strF;
    }

    private static final String e(Context context) {
        if (Integer.valueOf(Build.VERSION.SDK_INT).intValue() >= 9) {
            return API9.getAndroidSerial(context);
        }
        return null;
    }

    private static final String f(Context context) {
        String string;
        if (Integer.valueOf(Build.VERSION.SDK_INT).intValue() < 3 || (string = Settings.Secure.getString(context.getContentResolver(), "android_id")) == null || string.length() <= 0 || "9774d56d682e549c".equals(string) || EnvironmentCompat.MEDIA_UNKNOWN.equals(string) || "android_id".equals(string)) {
            return null;
        }
        return string;
    }

    public static final DeviceId getAdvertisingDeviceId(Context context) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Cannot be called from the main thread");
        }
        if (!b(context)) {
            return getDeviceId(context);
        }
        try {
            if (isGooglePlayServicesAvailable(context)) {
                return new DeviceId(isAdvertisingIdEnabled(context) ? a(context) : NO_ID_AVAILABLE);
            }
            return null;
        } catch (IllegalStateException e) {
            throw e;
        }
    }

    public static DeviceId getDeviceId(Context context) {
        String strC = c(context);
        int i = 3;
        int i2 = 1;
        if (strC == null) {
            strC = d(context);
            i = 7;
            i2 = 2;
        }
        return new DeviceId(strC, i, i2);
    }

    public static boolean isAdvertisingIdEnabled(Context context) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Cannot be called from the main thread");
        }
        if (!c) {
            b bVar = new b();
            Intent intent = new Intent(b);
            intent.setPackage(a);
            if (context.bindService(intent, bVar, 1)) {
                try {
                    z = new c(bVar.getBinder()).isLimitAdTrackingEnabled(true) ? false : true;
                } catch (Exception e) {
                } finally {
                    context.unbindService(bVar);
                }
            }
            if (!z) {
                c = true;
            }
        }
        return z;
    }

    public static boolean isGooglePlayServicesAvailable(Context context) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Cannot be called from the main thread");
        }
        if (Build.VERSION.SDK_INT <= 8) {
            return false;
        }
        try {
            context.getPackageManager().getPackageInfo(GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE, 0);
            b bVar = new b();
            Intent intent = new Intent(b);
            intent.setPackage(a);
            if (!context.bindService(intent, bVar, 1)) {
                return false;
            }
            context.unbindService(bVar);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String md5(String str) {
        try {
            byte[] bArrDigest = MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(bArrDigest.length * 2);
            for (byte b2 : bArrDigest) {
                int i = b2 & 255;
                if (i < 16) {
                    sb.append('0');
                }
                sb.append(Integer.toHexString(i));
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException("Huh, MD5 should be supported?", e2);
        }
    }
}
