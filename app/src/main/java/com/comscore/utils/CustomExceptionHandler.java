package com.comscore.utils;

import com.comscore.analytics.Core;
import com.comscore.applications.EventType;
import java.io.UnsupportedEncodingException;
import java.lang.Thread;
import java.net.URLEncoder;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class CustomExceptionHandler implements Thread.UncaughtExceptionHandler {
    private Thread.UncaughtExceptionHandler a = Thread.getDefaultUncaughtExceptionHandler();
    private Core b;

    public CustomExceptionHandler(Core core) {
        this.b = core;
    }

    private void a(Throwable th) {
        StackTraceElement[] stackTrace = th.getStackTrace();
        String str = "";
        int i = 0;
        while (i < stackTrace.length && i < 5) {
            StackTraceElement stackTraceElement = stackTrace[i];
            String str2 = stackTraceElement.getFileName() + "@" + stackTraceElement.getLineNumber() + "|" + stackTraceElement.getClassName() + "." + stackTraceElement.getMethodName();
            if (!str.equals("")) {
                str2 = str + ";" + str2;
            }
            i++;
            str = str2;
        }
        HashMap<String, String> map = new HashMap<>();
        if (!str.equals("")) {
            try {
                map.put("ns_ap_uxc", URLEncoder.encode(str, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                CSLog.e(this, "Error encoding the URL (ns_ap_uxc)");
                CSLog.printStackTrace(e);
            }
        }
        try {
            map.put("ns_ap_uxs", URLEncoder.encode(th.toString(), "UTF-8"));
        } catch (UnsupportedEncodingException e2) {
            CSLog.e(this, "Error encoding the URL (ns_ap_uxs)");
            CSLog.printStackTrace(e2);
        }
        this.b.getOfflineCache().saveApplicationMeasurement(EventType.HIDDEN, map, true);
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread thread, Throwable th) {
        a(th);
        this.a.uncaughtException(thread, th);
    }
}
