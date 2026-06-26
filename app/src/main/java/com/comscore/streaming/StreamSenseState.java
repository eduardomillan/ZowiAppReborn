package com.comscore.streaming;

/* JADX INFO: loaded from: classes.dex */
public enum StreamSenseState {
    IDLE("idle", 0, StreamSenseEventType.END),
    PLAYING("playing", 1, StreamSenseEventType.PLAY),
    PAUSED("paused", 2, StreamSenseEventType.PAUSE),
    BUFFERING("buffering", 3, StreamSenseEventType.BUFFER);

    private String a;
    private int b;
    private StreamSenseEventType c;

    StreamSenseState(String str, int i, StreamSenseEventType streamSenseEventType) {
        this.a = str;
        this.b = i;
        this.c = streamSenseEventType;
    }

    public int getCode() {
        return this.b;
    }

    public String getName() {
        return this.a;
    }

    public StreamSenseEventType toEventType() {
        return this.c;
    }

    @Override // java.lang.Enum
    public String toString() {
        return getName();
    }
}
