package com.comscore.metrics;

import com.comscore.utils.TransmissionMode;

/* JADX INFO: loaded from: classes.dex */
/* synthetic */ class a {
    static final /* synthetic */ int[] a = new int[TransmissionMode.values().length];

    static {
        try {
            a[TransmissionMode.NEVER.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            a[TransmissionMode.DISABLED.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            a[TransmissionMode.DEFAULT.ordinal()] = 3;
        } catch (NoSuchFieldError e3) {
        }
        try {
            a[TransmissionMode.WIFIONLY.ordinal()] = 4;
        } catch (NoSuchFieldError e4) {
        }
        try {
            a[TransmissionMode.PIGGYBACK.ordinal()] = 5;
        } catch (NoSuchFieldError e5) {
        }
    }
}
