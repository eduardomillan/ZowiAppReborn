package com.adobe.mobile;

import android.support.v4.os.EnvironmentCompat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class RequestBuilder {
    private static final String PRIVACY_MODE_KEY = "a.privacy.mode";
    private static final String VAR_ESCAPE_PREFIX = "&&";

    RequestBuilder() {
    }

    protected static void buildAndSendRequest(Map<String, Object> data, Map<String, Object> vars, long timeStamp) {
        if (WearableFunctionBridge.shouldSendHit() && MobileConfig.getInstance().mobileUsingAnalytics()) {
            HashMap<String, Object> mutableData = new HashMap<>();
            mutableData.putAll(StaticMethods.getDefaultData());
            long t = StaticMethods.getTimeSinceLaunch();
            if (t > 0) {
                mutableData.put("a.TimeSinceLaunch", String.valueOf(t));
            }
            if (data != null) {
                mutableData.putAll(data);
            }
            if (MobileConfig.getInstance().getPrivacyStatus() == MobilePrivacyStatus.MOBILE_PRIVACY_STATUS_UNKNOWN) {
                mutableData.put(PRIVACY_MODE_KEY, EnvironmentCompat.MEDIA_UNKNOWN);
            }
            HashMap<String, Object> mutableVars = vars != null ? new HashMap<>(vars) : new HashMap<>();
            String aid = StaticMethods.getAID();
            if (aid != null) {
                mutableVars.put("aid", StaticMethods.getAID());
            }
            if (StaticMethods.getVisitorID() != null) {
                mutableVars.put(com.comscore.utils.Constants.VID_KEY, StaticMethods.getVisitorID());
            }
            mutableVars.put("ce", MobileConfig.getInstance().getCharacterSet());
            if (MobileConfig.getInstance().getOfflineTrackingEnabled()) {
                mutableVars.put("ts", Long.toString(timeStamp));
            }
            mutableVars.put("t", StaticMethods.getTimestampString());
            Iterator<Map.Entry<String, Object>> it = mutableData.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> kvPair = it.next();
                String key = kvPair.getKey();
                if (key == null) {
                    it.remove();
                } else if (key.startsWith("&&")) {
                    mutableVars.put(key.substring("&&".length()), kvPair.getValue());
                    it.remove();
                }
            }
            Messages.checkForInAppMessage(new HashMap(mutableVars), new HashMap(mutableData), new HashMap(Lifecycle.getContextDataLowercase()));
            Messages.checkFor3rdPartyCallbacks(new HashMap(mutableVars), new HashMap(mutableData));
            mutableVars.put("c", StaticMethods.translateContextData(mutableData));
            StringBuilder requestString = new StringBuilder(2048);
            requestString.append("ndh=1");
            if (MobileConfig.getInstance().getVisitorIdServiceEnabled()) {
                requestString.append(VisitorIDService.sharedInstance().getAnalyticsIdVisitorString());
            }
            StaticMethods.serializeToQueryString(mutableVars, requestString);
            StaticMethods.logDebugFormat("Analytics - Attempting to send request parameters(%s)", requestString);
            AnalyticsWorker.sharedInstance().queue(requestString.toString(), timeStamp);
        }
    }
}
