package com.comscore.utils;

import android.annotation.SuppressLint;
import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
public class API9 {
    @SuppressLint({"NewApi"})
    public static String getSerial() {
        return Build.VERSION.SDK_INT >= 9 ? Build.SERIAL : "";
    }
}
