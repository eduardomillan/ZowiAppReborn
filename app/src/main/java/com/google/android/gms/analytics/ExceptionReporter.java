package com.google.android.gms.analytics;

import android.content.Context;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.internal.zzae;
import java.lang.Thread;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class ExceptionReporter implements Thread.UncaughtExceptionHandler {
    private final Context mContext;
    private final Thread.UncaughtExceptionHandler zzLv;
    private final Tracker zzLw;
    private ExceptionParser zzLx;
    private GoogleAnalytics zzLy;

    public ExceptionReporter(Tracker tracker, Thread.UncaughtExceptionHandler originalHandler, Context context) {
        if (tracker == null) {
            throw new NullPointerException("tracker cannot be null");
        }
        if (context == null) {
            throw new NullPointerException("context cannot be null");
        }
        this.zzLv = originalHandler;
        this.zzLw = tracker;
        this.zzLx = new StandardExceptionParser(context, new ArrayList());
        this.mContext = context.getApplicationContext();
        zzae.v("ExceptionReporter created, original handler is " + (originalHandler == null ? "null" : originalHandler.getClass().getName()));
    }

    public ExceptionParser getExceptionParser() {
        return this.zzLx;
    }

    public void setExceptionParser(ExceptionParser exceptionParser) {
        this.zzLx = exceptionParser;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public void uncaughtException(Thread t, Throwable e) {
        String description = "UncaughtException";
        if (this.zzLx != null) {
            description = this.zzLx.getDescription(t != null ? t.getName() : null, e);
        }
        zzae.v("Reporting uncaught exception: " + description);
        this.zzLw.send(new HitBuilders.ExceptionBuilder().setDescription(description).setFatal(true).build());
        GoogleAnalytics googleAnalyticsZzhK = zzhK();
        googleAnalyticsZzhK.dispatchLocalHits();
        googleAnalyticsZzhK.zzhO();
        if (this.zzLv != null) {
            zzae.v("Passing exception to the original handler");
            this.zzLv.uncaughtException(t, e);
        }
    }

    GoogleAnalytics zzhK() {
        if (this.zzLy == null) {
            this.zzLy = GoogleAnalytics.getInstance(this.mContext);
        }
        return this.zzLy;
    }

    Thread.UncaughtExceptionHandler zzhL() {
        return this.zzLv;
    }
}
