package com.adobe.mobile;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class AcquisitionHandler {
    AcquisitionHandler() {
    }

    protected static void campaignStartForApp(String appId, Map<String, Object> data) {
        String url = constructURLForCampaignStartRequest(appId, StaticMethods.getAdvertisingIdentifier(), data);
        StaticMethods.logDebugFormat("Acquisition - Sending acquisition request  (%s)", url);
        RequestHandler.sendGenericRequest(url, null, 5000, "Acquisition");
    }

    protected static String constructURLForCampaignStartRequest(String appId, String adid, Map<String, Object> data) {
        if (appId == null || appId.length() <= 0) {
            StaticMethods.logDebugFormat("Acquisition - Acquisition application identifier is blank", new Object[0]);
            return null;
        }
        StringBuilder urlSb = new StringBuilder();
        urlSb.append("http://c00.adobe.com/v3/").append(appId).append("/start?");
        StringBuilder querySb = new StringBuilder();
        if (adid != null) {
            querySb.append(querySb.length() > 0 ? "&" : "");
            querySb.append("a_cid=").append(StaticMethods.URLEncode(adid));
        }
        if (data != null && data.size() > 0) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (key != null && key.length() != 0 && value != null && value.toString().length() != 0) {
                    querySb.append(querySb.length() > 0 ? "&" : "");
                    querySb.append("ctx");
                    querySb.append(StaticMethods.URLEncode(key));
                    querySb.append("=");
                    querySb.append(StaticMethods.URLEncode(value.toString()));
                }
            }
        }
        return urlSb.append((CharSequence) querySb).toString();
    }
}
