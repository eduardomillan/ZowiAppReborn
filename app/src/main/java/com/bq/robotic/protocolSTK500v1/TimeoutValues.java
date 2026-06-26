package com.bq.robotic.protocolSTK500v1;

/* JADX INFO: loaded from: classes.dex */
public enum TimeoutValues {
    DEFAULT(450),
    CONNECT(450),
    READ(800),
    SHORT_READ(800),
    RESTART(800),
    WRITE(75);

    private final long timeout;

    TimeoutValues(long t) {
        this.timeout = t;
    }

    public long getTimeout() {
        return this.timeout;
    }
}
