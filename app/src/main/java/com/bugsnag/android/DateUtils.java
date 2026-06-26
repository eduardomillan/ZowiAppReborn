package com.bugsnag.android;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* JADX INFO: loaded from: classes.dex */
final class DateUtils {
    private static final ThreadLocal<DateFormat> iso8601Holder = new ThreadLocal<DateFormat>() { // from class: com.bugsnag.android.DateUtils.1
        @Override // java.lang.ThreadLocal
        protected final /* bridge */ /* synthetic */ DateFormat initialValue() {
            TimeZone timeZone = TimeZone.getTimeZone("UTC");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
            simpleDateFormat.setTimeZone(timeZone);
            return simpleDateFormat;
        }
    };

    static String toISO8601(Date date) {
        return iso8601Holder.get().format(date);
    }
}
