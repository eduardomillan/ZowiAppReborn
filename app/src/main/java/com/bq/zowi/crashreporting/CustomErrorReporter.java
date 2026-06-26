package com.bq.zowi.crashreporting;

import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public interface CustomErrorReporter {
    void init();

    void leaveBreadcrumb(String str);

    void logHandledException(Throwable th, HashMap<String, String> map);

    void setReleaseStage(String str);

    void setUserData(String str, String str2, String str3);
}
