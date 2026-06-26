package com.comscore.utils;

import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class CSLog {
    public static void d(Class<? extends Object> cls, String str) {
        if (Constants.DEBUG) {
            Log.d(cls.getSimpleName(), str);
        }
    }

    public static void d(Object obj, String str) {
        if (Constants.DEBUG) {
            d((Class<? extends Object>) obj.getClass(), str);
        }
    }

    public static void e(Class<? extends Object> cls, String str) {
        if (Constants.DEBUG) {
            Log.e(cls.getSimpleName(), str);
        }
    }

    public static void e(Object obj, String str) {
        if (Constants.DEBUG) {
            e((Class<? extends Object>) obj.getClass(), str);
        }
    }

    public static void printStackTrace(Exception exc) {
        if (Constants.DEBUG) {
            exc.printStackTrace();
        }
    }

    public static void v(Class<? extends Object> cls, String str) {
        if (Constants.DEBUG) {
            Log.v(cls.getSimpleName(), str);
        }
    }

    public static void v(Object obj, String str) {
        if (Constants.DEBUG) {
            v((Class<? extends Object>) obj.getClass(), str);
        }
    }
}
