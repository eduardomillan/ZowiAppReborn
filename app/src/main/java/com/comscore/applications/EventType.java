package com.comscore.applications;

import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public enum EventType {
    START,
    VIEW,
    CLOSE,
    AGGREGATE,
    HIDDEN,
    KEEPALIVE;

    @Override // java.lang.Enum
    public String toString() {
        return this == KEEPALIVE ? "keep-alive" : super.toString().toLowerCase(new Locale("en", "US"));
    }
}
