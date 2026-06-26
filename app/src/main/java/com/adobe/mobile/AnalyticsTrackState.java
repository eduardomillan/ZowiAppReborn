package com.adobe.mobile;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class AnalyticsTrackState {
    AnalyticsTrackState() {
    }

    protected static void trackState(String state, Map<String, Object> data) {
        HashMap<String, Object> mutableVars = new HashMap<>();
        if (state == null || state.length() <= 0) {
            state = StaticMethods.getApplicationID();
        }
        mutableVars.put("pageName", state);
        RequestBuilder.buildAndSendRequest(data, mutableVars, StaticMethods.getTimeSince1970());
    }
}
