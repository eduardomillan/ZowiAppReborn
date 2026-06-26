package com.adobe.mobile;

import android.content.SharedPreferences;
import com.adobe.mobile.RequestHandler;
import com.adobe.mobile.StaticMethods;
import com.adobe.mobile.Target;
import java.math.BigDecimal;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/* JADX INFO: loaded from: classes.dex */
final class TargetWorker {
    protected static final String COOKIE_EXPIRES_KEY_SUFFIX = "_Expires";
    protected static final String COOKIE_NAME_PCID = "mboxPC";
    protected static final String COOKIE_NAME_SESSION = "mboxSession";
    protected static final String COOKIE_VALUE_KEY_SUFFIX = "_Value";
    protected static final String LOCATION_CONTENT_TYPE = "text%2Fplain%3Bcharset%3Dutf-8";
    protected static final String LOCATION_SERVER_SUFFIX = ".tt.omtrdc.net";
    protected static final String LOCATION_SESSION_STRING = "&mboxSession=%s&mboxPC=%s&mboxXDomain=disabled";
    private static final String OFFER_ENCODING = "UTF-8";
    private static final int TO_MILLI = 1000;
    private static String _pcId;
    private static HashMap<String, Object> _persistentParameters;
    private static String _sessionId;
    private static final Object _sessionIdMutex = new Object();
    private static final Object _pcIdMutex = new Object();
    private static final Object _parameterMutex = new Object();
    private static String _serverURL = null;
    private static final Object _serverURLMutex = new Object();

    TargetWorker() {
    }

    protected static void loadRequest(TargetLocationRequest request, Target.TargetCallback<String> callback) {
        if (request == null) {
            StaticMethods.logWarningFormat("Target - LocationRequest parameter is null", new Object[0]);
            if (callback != null) {
                callback.call(null);
                return;
            }
            return;
        }
        SendRequestTask backgroundTask = new SendRequestTask(request, callback);
        new Thread(backgroundTask).start();
    }

    protected static class SendRequestTask implements Runnable {
        private Target.TargetCallback<String> callback;
        private BigDecimal lifetimeValue;
        private TargetLocationRequest request;

        private SendRequestTask(TargetLocationRequest initRequest, Target.TargetCallback<String> initCallback) {
            this.request = initRequest;
            this.callback = initCallback;
            this.lifetimeValue = AnalyticsTrackLifetimeValueIncrease.getLifetimeValue();
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.lifetimeValue != null) {
                TargetWorker.addPersistentParameter("a.ltv.amount", this.lifetimeValue.toString());
            }
            String requestUrl = TargetWorker.getURLRequestString(this.request.name, this.request.parameters);
            if (requestUrl == null || requestUrl.length() <= 0) {
                StaticMethods.logWarningFormat("Target - LocationRequest requires a name.", new Object[0]);
                if (this.callback != null) {
                    this.callback.call(this.request.defaultContent);
                    return;
                }
                return;
            }
            try {
                byte[] responseData = RequestHandler.retrieveData(requestUrl, MobileConfig.getInstance().getDefaultLocationTimeout() * 1000, "Target", new Callable<Map<String, String>>() { // from class: com.adobe.mobile.TargetWorker.SendRequestTask.1
                    @Override // java.util.concurrent.Callable
                    public Map<String, String> call() {
                        String pcidCookie = TargetWorker.getCookieValue("mboxPC");
                        String sessionCookie = TargetWorker.getCookieValue("mboxSession");
                        String cookieString = pcidCookie + (pcidCookie.length() > 0 ? "; " : "") + sessionCookie;
                        Map<String, String> responseMap = new HashMap<>();
                        responseMap.put("Cookie", cookieString);
                        return responseMap;
                    }
                }, new RequestHandler.HeaderCallback() { // from class: com.adobe.mobile.TargetWorker.SendRequestTask.2
                    @Override // com.adobe.mobile.RequestHandler.HeaderCallback
                    public void call(Map<String, List<String>> headers) {
                        TargetWorker.readAndStoreCookiesFromHeaders(headers);
                    }
                });
                if (responseData != null && responseData.length > 0) {
                    String responseString = new String(responseData, TargetWorker.OFFER_ENCODING);
                    if (this.callback != null) {
                        this.callback.call(responseString);
                    }
                } else {
                    StaticMethods.logWarningFormat("Target - No content found or user didn't qualify for campaign for LocationRequest (%s)", this.request.name);
                    if (this.callback != null) {
                        this.callback.call(this.request.defaultContent);
                    }
                }
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
                StaticMethods.logWarningFormat("Target - Unable to retrieve content (%s)", e2.getLocalizedMessage());
                if (this.callback != null) {
                    this.callback.call(this.request.defaultContent);
                }
            }
        }
    }

    protected static void clearCookies() {
        deleteCookie("mboxSession");
        deleteCookie("mboxPC");
    }

    protected static String getPcID() {
        String str;
        synchronized (_pcIdMutex) {
            str = _pcId;
        }
        return str;
    }

    protected static String getSessionID() {
        String str;
        synchronized (_sessionIdMutex) {
            str = _sessionId;
        }
        return str;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getURLRequestString(String name, Map<String, Object> parameters) {
        String persistentParametersString;
        if (name == null || name.length() <= 0 || !MobileConfig.getInstance().mobileUsingTarget()) {
            return null;
        }
        synchronized (_parameterMutex) {
            persistentParametersString = getParamsString(_persistentParameters);
        }
        FutureTask<HashMap<String, Object>> f = new FutureTask<>(new Callable<HashMap<String, Object>>() { // from class: com.adobe.mobile.TargetWorker.1
            @Override // java.util.concurrent.Callable
            public HashMap<String, Object> call() throws Exception {
                return Lifecycle.getContextData();
            }
        });
        StaticMethods.getAnalyticsExecutor().execute(f);
        HashMap<String, Object> lifecycleData = null;
        try {
            lifecycleData = f.get();
        } catch (Exception e) {
            StaticMethods.logDebugFormat("Target - Unable to get lifecycle data (%s)", e.getMessage());
        }
        String url = getServerURL(MobileConfig.getInstance().getClientCode()) + "/m2/" + MobileConfig.getInstance().getClientCode() + "/ubox/raw?mboxContentType=" + LOCATION_CONTENT_TYPE + "&t=" + StaticMethods.getTimeSince1970() + getSessionString() + "&mboxDefault=none&mbox=" + name + (MobileConfig.getInstance().getVisitorIdServiceEnabled() ? VisitorIDService.sharedInstance().getTargetParameterString() : "") + getParamsString(parameters) + getParamsString(lifecycleData) + persistentParametersString;
        StaticMethods.logDebugFormat("Target - LocationRequest (%s) loading URL: %s", name, url);
        return url;
    }

    private static String getSessionString() {
        String p;
        String s;
        synchronized (_pcIdMutex) {
            p = _pcId;
        }
        synchronized (_sessionIdMutex) {
            s = _sessionId;
        }
        if (s != null && s.trim().length() > 0 && p != null && p.trim().length() > 0) {
            return String.format(LOCATION_SESSION_STRING, s, p);
        }
        return "";
    }

    private static String getServerURL(String client) {
        String str;
        synchronized (_serverURLMutex) {
            if (_serverURL == null) {
                _serverURL = (MobileConfig.getInstance().getSSL() ? "https://" : "http://") + client + LOCATION_SERVER_SUFFIX;
            }
            str = _serverURL;
        }
        return str;
    }

    private static String getParamsString(Map<String, Object> parameters) {
        if (parameters == null || parameters.size() == 0) {
            return "";
        }
        StringBuilder paramStringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (key != null && key.length() > 0 && value != null && value.toString().length() > 0) {
                paramStringBuilder.append("&");
                paramStringBuilder.append(StaticMethods.URLEncode(key));
                paramStringBuilder.append("=");
                paramStringBuilder.append(StaticMethods.URLEncode(value.toString()));
            }
        }
        return paramStringBuilder.toString();
    }

    private static void deleteCookie(String cookieName) {
        if (cookieName.equals("mboxSession")) {
            synchronized (_sessionIdMutex) {
                _sessionId = null;
            }
        } else if (cookieName.equals("mboxPC")) {
            synchronized (_pcIdMutex) {
                _pcId = null;
            }
        }
        try {
            SharedPreferences.Editor editor = StaticMethods.getSharedPreferencesEditor();
            editor.remove(cookieName + COOKIE_VALUE_KEY_SUFFIX);
            editor.remove(cookieName + COOKIE_EXPIRES_KEY_SUFFIX);
            editor.commit();
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logErrorFormat("Target - Error persisting cookies (%s)", e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getCookieValue(String cookieName) {
        try {
            long cookieExpires = StaticMethods.getSharedPreferences().getLong(cookieName + COOKIE_EXPIRES_KEY_SUFFIX, 0L);
            if (cookieExpires > 0 && cookieExpires > System.currentTimeMillis()) {
                String cookieValue = StaticMethods.getSharedPreferences().getString(cookieName + COOKIE_VALUE_KEY_SUFFIX, "");
                if (cookieValue != null) {
                    return cookieName + "=" + cookieValue;
                }
            } else {
                deleteCookie(cookieName);
            }
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logErrorFormat("Target - Error loading cookie data (%s)", e.getMessage());
        }
        return "";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void readAndStoreCookiesFromHeaders(Map<String, List<String>> headers) {
        List<String> cookies = headers.get("Set-Cookie");
        if (cookies != null) {
            for (String setCookie : cookies) {
                for (HttpCookie cookie : HttpCookie.parse(setCookie)) {
                    String name = cookie.getName();
                    if (name.equals("mboxSession")) {
                        synchronized (_sessionIdMutex) {
                            _sessionId = cookie.getValue();
                        }
                        storeCookie(cookie);
                    } else if (name.equals("mboxPC")) {
                        synchronized (_pcIdMutex) {
                            _pcId = cookie.getValue();
                        }
                        storeCookie(cookie);
                    } else {
                        continue;
                    }
                }
            }
        }
    }

    private static void storeCookie(HttpCookie cookie) {
        try {
            SharedPreferences.Editor editor = StaticMethods.getSharedPreferencesEditor();
            editor.putString(cookie.getName() + COOKIE_VALUE_KEY_SUFFIX, cookie.getValue());
            editor.putLong(cookie.getName() + COOKIE_EXPIRES_KEY_SUFFIX, (cookie.getMaxAge() * 1000) + System.currentTimeMillis());
            editor.commit();
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logErrorFormat("Target - Error persisting cookie (%s)", e.getLocalizedMessage());
        }
    }

    protected static void addPersistentParameter(String key, String value) {
        if (key != null && value != null) {
            synchronized (_parameterMutex) {
                if (_persistentParameters == null) {
                    _persistentParameters = new HashMap<>();
                }
                _persistentParameters.put(key, value);
            }
        }
    }

    protected static void removePersistentParameter(String key) {
        if (key != null) {
            synchronized (_parameterMutex) {
                if (_persistentParameters != null) {
                    _persistentParameters.remove(key);
                }
            }
        }
    }
}
