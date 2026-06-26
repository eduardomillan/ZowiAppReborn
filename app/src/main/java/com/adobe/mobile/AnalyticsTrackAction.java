package com.adobe.mobile;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class AnalyticsTrackAction {
    AnalyticsTrackAction() {
    }

    protected static void trackAction(String action, Map<String, Object> data) {
        HashMap<String, Object> mutableData = data != null ? new HashMap<>(data) : new HashMap<>();
        String actionName = action != null ? action : "";
        mutableData.put("a.action", actionName);
        HashMap<String, Object> mutableVars = new HashMap<>();
        mutableVars.put("pe", "lnk_o");
        mutableVars.put("pev2", "AMACTION:" + actionName);
        mutableVars.put("pageName", StaticMethods.getApplicationID());
        RequestBuilder.buildAndSendRequest(mutableData, mutableVars, StaticMethods.getTimeSince1970());
    }
}
