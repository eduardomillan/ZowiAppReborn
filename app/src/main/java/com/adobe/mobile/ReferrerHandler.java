package com.adobe.mobile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.adobe.mobile.StaticMethods;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
final class ReferrerHandler {
    static final String ACQUISITION_V3_TOKEN = "adb_acq_v3";
    static final String CONTEXT_DATA_KEY = "contextData";
    private static boolean _referrerProcessed = true;
    static final String[] REFERRER_FIELDS = {"utm_source", "utm_medium", "utm_term", "utm_content", "utm_campaign", "trackingcode"};

    ReferrerHandler() {
    }

    protected static void setReferrerProcessed(boolean processed) {
        _referrerProcessed = processed;
    }

    protected static boolean getReferrerProcessed() {
        return _referrerProcessed;
    }

    public static void processIntent(Intent intent) {
        String referrerURL = getReferrerURLFromIntent(intent);
        if (referrerURL == null || referrerURL.length() == 0) {
            StaticMethods.logDebugFormat("Analytics - Ignoring referrer due to the intent's referrer string being empty", new Object[0]);
            return;
        }
        StaticMethods.logDebugFormat("Analytics - Received referrer information(%s)", referrerURL);
        HashMap<String, Object> referrerFields = parseReferrerURLToMap(referrerURL);
        if (isV3Response(referrerFields)) {
            handleV3Acquisition(referrerFields);
        } else {
            handleV1Acquisition(referrerFields);
        }
    }

    protected static String getReferrerURLFromIntent(Intent intent) {
        if (intent == null) {
            StaticMethods.logWarningFormat("Analytics - Unable to process referrer due to an invalid intent parameter", new Object[0]);
            return null;
        }
        if (!intent.getAction().equals("com.android.vending.INSTALL_REFERRER")) {
            StaticMethods.logDebugFormat("Analytics - Ignoring referrer due to the intent's action not being handled by analytics", new Object[0]);
            return null;
        }
        try {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                extras.containsKey(null);
            }
            String referrerURL = intent.getStringExtra("referrer");
            if (referrerURL == null) {
                return null;
            }
            try {
                return URLDecoder.decode(referrerURL, "UTF-8");
            } catch (Exception e) {
                return referrerURL;
            }
        } catch (Exception e2) {
            return null;
        }
    }

    protected static HashMap<String, Object> parseReferrerURLToMap(String referrerURL) {
        HashMap<String, Object> referrerFields = new HashMap<>();
        for (String item : referrerURL.split("&")) {
            String[] tokens = item.split("=");
            if (tokens.length == 2) {
                referrerFields.put(tokens[0], tokens[1]);
            }
        }
        return referrerFields;
    }

    protected static void handleV1Acquisition(HashMap<String, Object> referrerFields) {
        if (Lifecycle.lifecycleHasRun) {
            if (referrerFields.containsKey("utm_source") && referrerFields.containsKey("utm_campaign")) {
                final HashMap<String, Object> referrerLifecycleContextData = new HashMap<>();
                referrerLifecycleContextData.put("a.referrer.campaign.source", referrerFields.get("utm_source"));
                referrerLifecycleContextData.put("a.referrer.campaign.medium", referrerFields.get("utm_medium"));
                referrerLifecycleContextData.put("a.referrer.campaign.term", referrerFields.get("utm_term"));
                referrerLifecycleContextData.put("a.referrer.campaign.content", referrerFields.get("utm_content"));
                referrerLifecycleContextData.put("a.referrer.campaign.name", referrerFields.get("utm_campaign"));
                referrerLifecycleContextData.put("a.referrer.campaign.trackingcode", referrerFields.get("trackingcode"));
                StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.ReferrerHandler.1
                    @Override // java.lang.Runnable
                    public void run() {
                        Lifecycle.updateContextData(referrerLifecycleContextData);
                    }
                });
                AnalyticsWorker.sharedInstance().kickWithReferrerData(referrerLifecycleContextData);
                return;
            }
            return;
        }
        try {
            SharedPreferences.Editor editor = StaticMethods.getSharedPreferencesEditor();
            for (String field : REFERRER_FIELDS) {
                if (referrerFields.containsKey(field) && referrerFields.get(field) != null) {
                    editor.putString(field, referrerFields.get(field).toString());
                }
            }
            editor.commit();
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logErrorFormat("Analytics - Error persisting referrer data (%s)", e.getMessage());
        }
        _referrerProcessed = true;
    }

    protected static void handleV3Acquisition(HashMap<String, Object> referrerFields) {
        String ugid = (String) referrerFields.get("utm_content");
        String jsonResponse = getReferrerDataFromV3Server(ugid, StaticMethods.getAdvertisingIdentifier());
        if (Lifecycle.lifecycleHasRun) {
            final HashMap<String, Object> contextData = parseV3Response(jsonResponse);
            StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.ReferrerHandler.2
                @Override // java.lang.Runnable
                public void run() {
                    Lifecycle.updateContextData(contextData);
                }
            });
            AnalyticsWorker.sharedInstance().kickWithReferrerData(contextData);
        } else {
            try {
                SharedPreferences.Editor editor = StaticMethods.getSharedPreferencesEditor();
                editor.putString("ADMS_Referrer_ContextData_Json_String", jsonResponse);
                editor.commit();
            } catch (StaticMethods.NullContextException e) {
                StaticMethods.logErrorFormat("Analytics - Error persisting referrer data (%s)", e.getMessage());
            }
            _referrerProcessed = true;
        }
    }

    protected static boolean isV3Response(HashMap<String, Object> referrerFields) {
        return ACQUISITION_V3_TOKEN.equals(referrerFields.get("utm_source")) && ACQUISITION_V3_TOKEN.equals(referrerFields.get("utm_campaign"));
    }

    protected static HashMap<String, Object> parseV3Response(String response) {
        HashMap<String, Object> contextDataMap = new HashMap<>();
        if (response != null && response.length() != 0) {
            if (_referrerProcessed) {
                StaticMethods.logDebugFormat("Analytics - Acquisition referrer timed out", new Object[0]);
            } else {
                try {
                    JSONObject responseObject = new JSONObject(response);
                    try {
                        JSONObject contextData = responseObject.getJSONObject(CONTEXT_DATA_KEY);
                        if (contextData != null && contextData.has("a.referrer.campaign.name")) {
                            Iterator<String> itKeys = contextData.keys();
                            while (itKeys.hasNext()) {
                                String key = itKeys.next().toString();
                                try {
                                    contextDataMap.put(key, contextData.getString(key));
                                } catch (JSONException e) {
                                    StaticMethods.logDebugFormat("Analytics - Unable to parse acquisition service response (the value for %s is not a string)", key);
                                }
                            }
                            StaticMethods.logDebugFormat("Analytics - Received Referrer Data(%s)", contextDataMap.toString());
                        }
                    } catch (JSONException e2) {
                        StaticMethods.logDebugFormat("Analytics - Unable to parse acquisition service response (no contextData parameter in response)", new Object[0]);
                    }
                } catch (JSONException ex) {
                    StaticMethods.logDebugFormat("Analytics - Unable to parse response(%s)", ex.getLocalizedMessage());
                }
            }
        }
        return contextDataMap;
    }

    protected static String getReferrerDataFromV3Server(String ugid, String adid) {
        if (!MobileConfig.getInstance().mobileReferrerConfigured()) {
            return null;
        }
        String url = generateURLForV3(ugid, adid);
        StaticMethods.logDebugFormat("Analytics - Trying to fetch referrer data from (%s)", url);
        byte[] responseByteArray = RequestHandler.retrieveData(url, null, MobileConfig.getInstance().getReferrerTimeout(), "Analytics");
        if (responseByteArray == null) {
            return null;
        }
        try {
            return new String(responseByteArray, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            StaticMethods.logErrorFormat("Analytics - Unable to decode response(%s)", ex.getLocalizedMessage());
            return null;
        }
    }

    protected static String generateURLForV3(String ugid, String adid) {
        StringBuilder urlSb = new StringBuilder(64);
        MobileConfig mobileConfig = MobileConfig.getInstance();
        urlSb.append(String.format("http://%s/v3/%s/end", mobileConfig.getAcquisitionServer(), mobileConfig.getAcquisitionAppId()));
        StringBuilder querySb = new StringBuilder(64);
        if (ugid != null && ugid.length() > 0) {
            querySb.append(String.format("?a_ugid=%s", StaticMethods.URLEncode(ugid)));
        }
        if (adid != null && adid.length() > 0) {
            querySb.append(querySb.length() > 0 ? "&" : "?");
            querySb.append(String.format("a_cid=%s", StaticMethods.URLEncode(adid)));
        }
        return urlSb.append((CharSequence) querySb).toString();
    }
}
