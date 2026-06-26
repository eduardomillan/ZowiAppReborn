package com.comscore.streaming.plugin;

import com.comscore.streaming.StreamSenseEventType;
import com.comscore.streaming.StreamSenseState;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public interface StreamSensePluginListener {
    void onGetLabels(StreamSenseEventType streamSenseEventType, HashMap<String, String> map);

    void onPostStateChange(StreamSenseState streamSenseState);

    void onPreStateChange(StreamSenseState streamSenseState);
}
