package com.adobe.mobile;

import android.content.SharedPreferences;
import com.adobe.mobile.AudienceManager;
import com.adobe.mobile.StaticMethods;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
final class AudienceManagerWorker {
    private static final String AUDIENCE_MANAGER_CUSTOMER_DATA_PREFIX = "c_";
    private static final String AUDIENCE_MANAGER_DATA_PROVIDER_ID_KEY = "d_dpid";
    private static final String AUDIENCE_MANAGER_DATA_PROVIDER_USER_ID_KEY = "d_dpuuid";
    private static final String AUDIENCE_MANAGER_JSON_COOKIE_NAME_KEY = "cn";
    private static final String AUDIENCE_MANAGER_JSON_COOKIE_VALUE_KEY = "cv";
    private static final String AUDIENCE_MANAGER_JSON_DESTS_KEY = "dests";
    private static final String AUDIENCE_MANAGER_JSON_STUFF_KEY = "stuff";
    private static final String AUDIENCE_MANAGER_JSON_URL_KEY = "c";
    private static final String AUDIENCE_MANAGER_JSON_USER_ID_KEY = "uuid";
    private static final String AUDIENCE_MANAGER_SHARED_PREFS_PROFILE_KEY = "AAMUserProfile";
    private static final String AUDIENCE_MANAGER_SHARED_PREFS_USER_ID_KEY = "AAMUserId";
    private static final String AUDIENCE_MANAGER_URL_SUFFIX = "&d_ptfm=android&d_dst=1&d_rtbd=json";
    private static final String AUDIENCE_MANAGER_USER_ID_KEY = "d_uuid";
    private static String _dpid = null;
    private static String _dpuuid = null;
    private static HashMap<String, Object> _visitorProfile = null;
    private static volatile boolean VisitorProfile_pred = true;
    private static String _urlPrefix = null;
    private static volatile boolean UrlPrefix_pred = true;

    AudienceManagerWorker() {
    }

    public static HashMap<String, Object> GetVisitorProfile() {
        FutureTask<HashMap<String, Object>> f = new FutureTask<>(new Callable<HashMap<String, Object>>() { // from class: com.adobe.mobile.AudienceManagerWorker.1
            @Override // java.util.concurrent.Callable
            public HashMap<String, Object> call() throws Exception {
                if (AudienceManagerWorker.VisitorProfile_pred) {
                    try {
                        String profile = StaticMethods.getSharedPreferences().getString(AudienceManagerWorker.AUDIENCE_MANAGER_SHARED_PREFS_PROFILE_KEY, null);
                        if (profile != null && profile.length() > 0) {
                            try {
                                JSONObject profileData = new JSONObject(profile);
                                HashMap unused = AudienceManagerWorker._visitorProfile = StaticMethods.mapFromJson(profileData);
                            } catch (JSONException ex) {
                                StaticMethods.logWarningFormat("Audience Manager - Problem accessing profile data (%s)", ex.getLocalizedMessage());
                            }
                            boolean unused2 = AudienceManagerWorker.VisitorProfile_pred = false;
                        }
                    } catch (StaticMethods.NullContextException e) {
                        StaticMethods.logErrorFormat("Audience Manager - Problem accessing profile data (%s)", e.getMessage());
                    }
                }
                return AudienceManagerWorker._visitorProfile;
            }
        });
        StaticMethods.getAudienceExecutor().execute(f);
        try {
            return f.get();
        } catch (Exception e) {
            StaticMethods.logErrorFormat("Audience Manager - Unable to retrieve Visitor Profile", e.getMessage());
            return null;
        }
    }

    public static String GetDpuuid() {
        FutureTask<String> f = new FutureTask<>(new Callable<String>() { // from class: com.adobe.mobile.AudienceManagerWorker.2
            @Override // java.util.concurrent.Callable
            public String call() throws Exception {
                return AudienceManagerWorker._dpuuid;
            }
        });
        StaticMethods.getAudienceExecutor().execute(f);
        try {
            return f.get();
        } catch (Exception e) {
            StaticMethods.logErrorFormat("Audience Manager - Unable to get Dpid (%s)", e.getMessage());
            return null;
        }
    }

    public static String GetDpid() {
        FutureTask<String> f = new FutureTask<>(new Callable<String>() { // from class: com.adobe.mobile.AudienceManagerWorker.3
            @Override // java.util.concurrent.Callable
            public String call() throws Exception {
                return AudienceManagerWorker._dpid;
            }
        });
        StaticMethods.getAudienceExecutor().execute(f);
        try {
            return f.get();
        } catch (Exception e) {
            StaticMethods.logErrorFormat("Audience Manager - Unable to get Dpid (%s)", e.getMessage());
            return null;
        }
    }

    public static void SetDpidAndDpuuid(final String newDpid, final String newDpuuid) {
        StaticMethods.getAudienceExecutor().execute(new Runnable() { // from class: com.adobe.mobile.AudienceManagerWorker.4
            @Override // java.lang.Runnable
            public void run() {
                String unused = AudienceManagerWorker._dpid = newDpid;
                String unused2 = AudienceManagerWorker._dpuuid = newDpuuid;
            }
        });
    }

    public static class SubmitSignalTask implements Runnable {
        public final AudienceManager.AudienceManagerCallback<Map<String, Object>> callback;
        public final Map<String, Object> data;

        public SubmitSignalTask(Map<String, Object> initData, AudienceManager.AudienceManagerCallback<Map<String, Object>> initCallback) {
            this.data = initData;
            this.callback = initCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            final HashMap<String, Object> callbackData = new HashMap<>();
            try {
                try {
                    try {
                        if (!MobileConfig.getInstance().mobileUsingAudienceManager()) {
                            if (this.callback != null) {
                                new Thread(new Runnable() { // from class: com.adobe.mobile.AudienceManagerWorker.SubmitSignalTask.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        SubmitSignalTask.this.callback.call(callbackData);
                                    }
                                }).start();
                                return;
                            }
                            return;
                        }
                        if (MobileConfig.getInstance().getPrivacyStatus() == MobilePrivacyStatus.MOBILE_PRIVACY_STATUS_OPT_OUT) {
                            StaticMethods.logDebugFormat("Audience Manager - Privacy status is set to opt out, no signals will be submitted.", new Object[0]);
                            if (this.callback != null) {
                                new Thread(new Runnable() { // from class: com.adobe.mobile.AudienceManagerWorker.SubmitSignalTask.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        SubmitSignalTask.this.callback.call(callbackData);
                                    }
                                }).start();
                                return;
                            }
                            return;
                        }
                        String requestUrl = AudienceManagerWorker.BuildSchemaRequest(this.data);
                        if (requestUrl.length() <= 1) {
                            StaticMethods.logWarningFormat("Audience Manager - Unable to create URL object", new Object[0]);
                            if (this.callback != null) {
                                new Thread(new Runnable() { // from class: com.adobe.mobile.AudienceManagerWorker.SubmitSignalTask.1
                                    @Override // java.lang.Runnable
                                    public void run() {
                                        SubmitSignalTask.this.callback.call(callbackData);
                                    }
                                }).start();
                                return;
                            }
                            return;
                        }
                        StaticMethods.logDebugFormat("Audience Manager - request (%s)", requestUrl);
                        byte[] responseBytes = RequestHandler.retrieveData(requestUrl, null, 2000, "Audience Manager");
                        String response = "";
                        if (responseBytes != null && responseBytes.length > 0) {
                            response = new String(responseBytes, "UTF-8");
                        }
                        JSONObject jsonResponse = new JSONObject(response);
                        callbackData.putAll(AudienceManagerWorker.processJsonResponse(jsonResponse));
                        if (this.callback != null) {
                            new Thread(new Runnable() { // from class: com.adobe.mobile.AudienceManagerWorker.SubmitSignalTask.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    SubmitSignalTask.this.callback.call(callbackData);
                                }
                            }).start();
                        }
                    } catch (JSONException e) {
                        StaticMethods.logWarningFormat("Audience Manager - Unable to parse JSON data (%s)", e.getLocalizedMessage());
                        if (this.callback != null) {
                            new Thread(new Runnable() { // from class: com.adobe.mobile.AudienceManagerWorker.SubmitSignalTask.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    SubmitSignalTask.this.callback.call(callbackData);
                                }
                            }).start();
                        }
                    } catch (Exception e2) {
                        StaticMethods.logWarningFormat("Audience Manager - Unexpected error parsing result (%s)", e2.getLocalizedMessage());
                        if (this.callback != null) {
                            new Thread(new Runnable() { // from class: com.adobe.mobile.AudienceManagerWorker.SubmitSignalTask.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    SubmitSignalTask.this.callback.call(callbackData);
                                }
                            }).start();
                        }
                    }
                } catch (UnsupportedEncodingException e3) {
                    StaticMethods.logWarningFormat("Audience Manager - Unable to decode server response (%s)", e3.getLocalizedMessage());
                    if (this.callback != null) {
                        new Thread(new Runnable() { // from class: com.adobe.mobile.AudienceManagerWorker.SubmitSignalTask.1
                            @Override // java.lang.Runnable
                            public void run() {
                                SubmitSignalTask.this.callback.call(callbackData);
                            }
                        }).start();
                    }
                }
            } catch (Throwable th) {
                if (this.callback != null) {
                    new Thread(new Runnable() { // from class: com.adobe.mobile.AudienceManagerWorker.SubmitSignalTask.1
                        @Override // java.lang.Runnable
                        public void run() {
                            SubmitSignalTask.this.callback.call(callbackData);
                        }
                    }).start();
                }
                throw th;
            }
        }
    }

    public static void SubmitSignal(Map<String, Object> data, AudienceManager.AudienceManagerCallback<Map<String, Object>> callback) {
        StaticMethods.getAudienceExecutor().execute(new SubmitSignalTask(data, callback));
    }

    public static void Reset() {
        StaticMethods.getAudienceExecutor().execute(new Runnable() { // from class: com.adobe.mobile.AudienceManagerWorker.5
            @Override // java.lang.Runnable
            public void run() {
                AudienceManagerWorker.SetUUID(null);
                AudienceManagerWorker.SetVisitorProfile(null);
            }
        });
    }

    protected static HashMap<String, Object> processJsonResponse(JSONObject response) {
        ProcessDestsArray(response);
        try {
            SetUUID(response.getString(AUDIENCE_MANAGER_JSON_USER_ID_KEY));
        } catch (JSONException e) {
            StaticMethods.logWarningFormat("Audience Manager - Unable to parse JSON data (%s)", e.getLocalizedMessage());
        }
        HashMap<String, Object> returnedMap = ProcessStuffArray(response);
        if (returnedMap.size() <= 0) {
            StaticMethods.logWarningFormat("Audience Manager - response was empty", new Object[0]);
            return null;
        }
        StaticMethods.logDebugFormat("Audience Manager - response (%s)", returnedMap);
        SetVisitorProfile(returnedMap);
        return returnedMap;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String BuildSchemaRequest(Map<String, Object> data) {
        if (GetUrlPrefix() == null) {
            return null;
        }
        String urlString = GetUrlPrefix() + GetCustomUrlVariables(data) + GetDataProviderUrlVariables() + AUDIENCE_MANAGER_URL_SUFFIX;
        return urlString.replace("?&", "?");
    }

    private static String GetCustomUrlVariables(Map<String, Object> data) {
        if (data == null || data.size() <= 0) {
            return "";
        }
        StringBuilder urlVars = new StringBuilder(1024);
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key != null && key.length() > 0 && value != null && value.toString().length() > 0 && entry.getValue().getClass() == String.class) {
                urlVars.append("&").append(AUDIENCE_MANAGER_CUSTOMER_DATA_PREFIX).append(StaticMethods.URLEncode(SanitizeUrlVariableName(key))).append("=").append(StaticMethods.URLEncode(value.toString()));
            }
        }
        return urlVars.toString();
    }

    private static String SanitizeUrlVariableName(String name) {
        return name.replace(".", "_");
    }

    private static String GetDataProviderUrlVariables() {
        StringBuilder urlVars = new StringBuilder();
        if (MobileConfig.getInstance().getVisitorIdServiceEnabled()) {
            urlVars.append(VisitorIDService.sharedInstance().getAAMParameterString());
        }
        if (GetUUID() != null) {
            urlVars.append("&").append(AUDIENCE_MANAGER_USER_ID_KEY).append("=").append(GetUUID());
        }
        if (_dpid != null && _dpid.length() > 0 && _dpuuid != null && _dpuuid.length() > 0) {
            urlVars.append("&").append(AUDIENCE_MANAGER_DATA_PROVIDER_ID_KEY).append("=").append(_dpid).append("&").append(AUDIENCE_MANAGER_DATA_PROVIDER_USER_ID_KEY).append("=").append(_dpuuid);
        }
        return urlVars.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void SetUUID(String newUuid) {
        try {
            SharedPreferences.Editor editor = StaticMethods.getSharedPreferencesEditor();
            if (newUuid == null) {
                editor.remove(AUDIENCE_MANAGER_SHARED_PREFS_USER_ID_KEY);
            } else {
                editor.putString(AUDIENCE_MANAGER_SHARED_PREFS_USER_ID_KEY, newUuid);
            }
            editor.commit();
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logErrorFormat("Audience Manager - Error updating uuid in shared preferences (%s)", e.getMessage());
        }
    }

    private static String GetUUID() {
        try {
            return StaticMethods.getSharedPreferences().getString(AUDIENCE_MANAGER_SHARED_PREFS_USER_ID_KEY, null);
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logErrorFormat("Audience Manager - Error getting uuid from shared preferences (%s).", e.getMessage());
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void SetVisitorProfile(Map<String, Object> profile) {
        VisitorProfile_pred = false;
        try {
            SharedPreferences.Editor editor = StaticMethods.getSharedPreferencesEditor();
            if (profile != null) {
                JSONObject profileJSON = new JSONObject(profile);
                editor.putString(AUDIENCE_MANAGER_SHARED_PREFS_PROFILE_KEY, profileJSON.toString());
                _visitorProfile = new HashMap<>(profile);
            } else {
                editor.remove(AUDIENCE_MANAGER_SHARED_PREFS_PROFILE_KEY);
                _visitorProfile = null;
            }
            editor.commit();
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logErrorFormat("Audience Manager - Error updating visitor profile (%s)", e.getMessage());
        }
    }

    private static void ProcessDestsArray(JSONObject jsonResponse) {
        try {
            JSONArray dests = jsonResponse.getJSONArray(AUDIENCE_MANAGER_JSON_DESTS_KEY);
            for (int i = 0; i < dests.length(); i++) {
                JSONObject dest = dests.getJSONObject(i);
                String url = dest.getString(AUDIENCE_MANAGER_JSON_URL_KEY);
                if (url != null && url.length() > 0) {
                    RequestHandler.sendGenericRequest(url, null, 5000, "Audience Manager");
                }
            }
        } catch (JSONException ex) {
            StaticMethods.logDebugFormat("Audience Manager - No destination in response (%s)", ex.getLocalizedMessage());
        }
    }

    private static HashMap<String, Object> ProcessStuffArray(JSONObject jsonResponse) {
        HashMap<String, Object> returnedMap = new HashMap<>();
        try {
            JSONArray stuffArray = jsonResponse.getJSONArray(AUDIENCE_MANAGER_JSON_STUFF_KEY);
            for (int i = 0; i < stuffArray.length(); i++) {
                JSONObject stuff = stuffArray.getJSONObject(i);
                if (stuff != null) {
                    returnedMap.put(stuff.getString(AUDIENCE_MANAGER_JSON_COOKIE_NAME_KEY), stuff.getString(AUDIENCE_MANAGER_JSON_COOKIE_VALUE_KEY));
                }
            }
        } catch (JSONException ex) {
            StaticMethods.logDebugFormat("Audience Manager - No 'stuff' array in response (%s)", ex.getLocalizedMessage());
        }
        return returnedMap;
    }

    private static String GetUrlPrefix() {
        if (UrlPrefix_pred && MobileConfig.getInstance().mobileUsingAudienceManager()) {
            UrlPrefix_pred = false;
            Object[] objArr = new Object[2];
            objArr[0] = MobileConfig.getInstance().getSSL() ? "https" : "http";
            objArr[1] = MobileConfig.getInstance().getAamServer();
            _urlPrefix = String.format("%s://%s/event?", objArr);
        }
        return _urlPrefix;
    }
}
