package com.comscore.streaming.plugin;

import com.comscore.streaming.StreamSenseState;

/* JADX INFO: loaded from: classes.dex */
/* synthetic */ class c {
    static final /* synthetic */ int[] a = new int[StreamSenseState.values().length];

    static {
        try {
            a[StreamSenseState.BUFFERING.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            a[StreamSenseState.IDLE.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
        try {
            a[StreamSenseState.PAUSED.ordinal()] = 3;
        } catch (NoSuchFieldError e3) {
        }
        try {
            a[StreamSenseState.PLAYING.ordinal()] = 4;
        } catch (NoSuchFieldError e4) {
        }
    }
}
