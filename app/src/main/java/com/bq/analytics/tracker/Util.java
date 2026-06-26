package com.bq.analytics.tracker;

/* JADX INFO: loaded from: classes.dex */
public final class Util {
    private Util() {
    }

    public static <T> T requireNonNull(T o, String message) {
        if (o == null) {
            throw new NullPointerException(message);
        }
        return o;
    }
}
