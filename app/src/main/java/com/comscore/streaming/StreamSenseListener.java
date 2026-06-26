package com.comscore.streaming;

import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public interface StreamSenseListener {
    void onStateChange(StreamSenseState streamSenseState, StreamSenseState streamSenseState2, HashMap<String, String> map, long j);
}
