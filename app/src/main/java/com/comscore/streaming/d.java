package com.comscore.streaming;

import com.comscore.utils.CSLog;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
class d extends f {
    final /* synthetic */ StreamSenseState a;
    final /* synthetic */ HashMap b;
    final /* synthetic */ StreamSense c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    d(StreamSense streamSense, StreamSenseState streamSenseState, HashMap map) {
        super(streamSense, null);
        this.c = streamSense;
        this.a = streamSenseState;
        this.b = map;
    }

    @Override // com.comscore.streaming.f
    public StreamSenseState getNextState() {
        return this.a;
    }

    @Override // java.lang.Runnable
    public void run() {
        CSLog.d(this.c, "Performing delayed transition");
        this.c.a(this.a, (HashMap<String, String>) this.b);
    }
}
