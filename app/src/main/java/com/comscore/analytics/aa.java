package com.comscore.analytics;

/* JADX INFO: loaded from: classes.dex */
/* synthetic */ class aa {
    static final /* synthetic */ int[] a;
    static final /* synthetic */ int[] b = new int[SessionState.values().length];

    static {
        try {
            b[SessionState.ACTIVE_USER.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            b[SessionState.USER.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            b[SessionState.APPLICATION.ordinal()] = 3;
        } catch (NoSuchFieldError e3) {
        }
        try {
            b[SessionState.INACTIVE.ordinal()] = 4;
        } catch (NoSuchFieldError e4) {
        }
        a = new int[ApplicationState.values().length];
        try {
            a[ApplicationState.INACTIVE.ordinal()] = 1;
        } catch (NoSuchFieldError e5) {
        }
        try {
            a[ApplicationState.BACKGROUND_UX_ACTIVE.ordinal()] = 2;
        } catch (NoSuchFieldError e6) {
        }
        try {
            a[ApplicationState.FOREGROUND.ordinal()] = 3;
        } catch (NoSuchFieldError e7) {
        }
    }
}
