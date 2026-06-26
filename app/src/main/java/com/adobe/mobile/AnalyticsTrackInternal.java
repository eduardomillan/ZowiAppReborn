package com.adobe.mobile;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class AnalyticsTrackInternal {
    AnalyticsTrackInternal() {
    }

    protected static void trackInternal(String action, Map<String, Object> data) {
        trackInternal(action, data, StaticMethods.getTimeSince1970());
    }

    protected static void trackInternal(String action, Map<String, Object> data, long timeStamp) {
        HashMap<String, Object> mutableData = data != null ? new HashMap<>(data) : new HashMap<>();
        mutableData.put("a.internalaction", action != null ? action : "None");
        HashMap<String, Object> rawLinkVars = new HashMap<>();
        rawLinkVars.put("pe", "lnk_o");
        StringBuilder sbAppend = new StringBuilder().append("ADBINTERNAL:");
        if (action == null) {
            action = "None";
        }
        rawLinkVars.put("pev2", sbAppend.append(action).toString());
        rawLinkVars.put("pageName", StaticMethods.getApplicationID());
        RequestBuilder.buildAndSendRequest(mutableData, rawLinkVars, timeStamp);
    }
}
