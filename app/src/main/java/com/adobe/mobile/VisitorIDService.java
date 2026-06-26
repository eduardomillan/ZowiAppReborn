package com.adobe.mobile;

import android.content.SharedPreferences;
import com.adobe.mobile.StaticMethods;
import com.adobe.mobile.VisitorID;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
final class VisitorIDService {
    static final String ANALYTICS_PARAMETER_KEY_BLOB = "aamb";
    static final String ANALYTICS_PARAMETER_KEY_LOCATION_HINT = "aamlh";
    static final String ANALYTICS_PARAMETER_KEY_MID = "mid";
    static final String ANALYTICS_PARAMETER_KEY_ORG = "mcorgid";
    static final String CID_DELIMITER = "%01";
    static final String RESPONSE_KEY_BLOB = "d_blob";
    static final String RESPONSE_KEY_ERROR = "error_msg";
    static final String RESPONSE_KEY_HINT = "dcs_region";
    static final String RESPONSE_KEY_MID = "d_mid";
    static final String RESPONSE_KEY_TTL = "id_sync_ttl";
    static final String SERVER = "dpm.demdex.net";
    static final String TARGET_PARAMETER_KEY_AID = "mboxMCAVID";
    static final String TARGET_PARAMETER_KEY_BLOB = "mboxAAMB";
    static final String TARGET_PARAMETER_KEY_HINT = "mboxMCGLH";
    static final String TARGET_PARAMETER_KEY_MID = "mboxMCGVID";
    static final int TIMEOUT = 2000;
    static final String VISITOR_ID_PARAMETER_KEY_CUSTOMER = "d_cid_ic";
    private static VisitorIDService _instance = null;
    private static final Object _instanceMutex = new Object();
    private String _aamIdString;
    private String _analyticsIdString;
    private String _blob;
    private List<VisitorID> _customerIds;
    private long _lastSync;
    private String _locationHint;
    private String _marketingCloudID;
    private long _ttl;
    private final Executor _visitorIDExector = Executors.newSingleThreadExecutor();

    public static VisitorIDService sharedInstance() {
        VisitorIDService visitorIDService;
        synchronized (_instanceMutex) {
            if (_instance == null) {
                _instance = new VisitorIDService();
            }
            visitorIDService = _instance;
        }
        return visitorIDService;
    }

    protected VisitorIDService() {
        resetVariablesFromSharedPreferences();
        idSync(null);
    }

    protected void resetVariablesFromSharedPreferences() {
        this._visitorIDExector.execute(new Runnable() { // from class: com.adobe.mobile.VisitorIDService.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    VisitorIDService.this._customerIds = VisitorIDService.this._parseIdString(StaticMethods.getSharedPreferences().getString("ADBMOBILE_VISITORID_IDS", null));
                    VisitorIDService.this._analyticsIdString = VisitorIDService.this._generateAnalyticsCustomerIdString(VisitorIDService.this._customerIds);
                    VisitorIDService.this._aamIdString = VisitorIDService.this._generateCustomerIdString(VisitorIDService.this._customerIds);
                    VisitorIDService.this._marketingCloudID = StaticMethods.getSharedPreferences().getString("ADBMOBILE_PERSISTED_MID", null);
                    VisitorIDService.this._locationHint = StaticMethods.getSharedPreferences().getString("ADBMOBILE_PERSISTED_MID_HINT", null);
                    VisitorIDService.this._blob = StaticMethods.getSharedPreferences().getString("ADBMOBILE_PERSISTED_MID_BLOB", null);
                    VisitorIDService.this._ttl = StaticMethods.getSharedPreferences().getLong("ADBMOBILE_VISITORID_TTL", 0L);
                    VisitorIDService.this._lastSync = StaticMethods.getSharedPreferences().getLong("ADBMOBILE_VISITORID_SYNC", 0L);
                } catch (StaticMethods.NullContextException ex) {
                    VisitorIDService.this._marketingCloudID = null;
                    VisitorIDService.this._locationHint = null;
                    VisitorIDService.this._blob = null;
                    StaticMethods.logErrorFormat("Visitor - Unable to check for stored visitor ID due to context error (%s)", ex.getMessage());
                }
            }
        });
    }

    protected void idSync(Map<String, String> identifiers, VisitorID.VisitorIDAuthenticationState authenticationState) {
        idSync(identifiers, null, authenticationState);
    }

    protected void idSync(Map<String, String> identifiers, Map<String, String> dpids) {
        idSync(identifiers, dpids, VisitorID.VisitorIDAuthenticationState.VISITOR_ID_AUTHENTICATION_STATE_UNKNOWN);
    }

    protected void idSync(Map<String, String> identifiers) {
        idSync(identifiers, null, VisitorID.VisitorIDAuthenticationState.VISITOR_ID_AUTHENTICATION_STATE_UNKNOWN);
    }

    protected void idSync(Map<String, String> identifiers, Map<String, String> dpids, final VisitorID.VisitorIDAuthenticationState authenticationState) {
        final HashMap<String, String> identifiersCopy = identifiers != null ? new HashMap<>(identifiers) : null;
        final HashMap<String, String> dpidsCopy = dpids != null ? new HashMap<>(dpids) : null;
        this._visitorIDExector.execute(new Runnable() { // from class: com.adobe.mobile.VisitorIDService.2
            @Override // java.lang.Runnable
            public void run() {
                if (MobileConfig.getInstance().getVisitorIdServiceEnabled()) {
                    String orgId = MobileConfig.getInstance().getMarketingCloudOrganizationId();
                    boolean needResync = StaticMethods.getTimeSince1970() - VisitorIDService.this._lastSync > VisitorIDService.this._ttl;
                    boolean hasIdentifiers = identifiersCopy != null;
                    boolean hasDpids = dpidsCopy != null;
                    if (VisitorIDService.this._marketingCloudID == null || hasIdentifiers || hasDpids || needResync) {
                        StringBuilder url = new StringBuilder(MobileConfig.getInstance().getSSL() ? "https" : "http");
                        url.append("://");
                        url.append(VisitorIDService.SERVER);
                        url.append("/id?d_ver=2&d_orgid=");
                        url.append(orgId);
                        if (VisitorIDService.this._marketingCloudID != null) {
                            url.append("&");
                            url.append(VisitorIDService.RESPONSE_KEY_MID);
                            url.append("=");
                            url.append(VisitorIDService.this._marketingCloudID);
                        }
                        if (VisitorIDService.this._blob != null) {
                            url.append("&");
                            url.append(VisitorIDService.RESPONSE_KEY_BLOB);
                            url.append("=");
                            url.append(VisitorIDService.this._blob);
                        }
                        if (VisitorIDService.this._locationHint != null) {
                            url.append("&");
                            url.append(VisitorIDService.RESPONSE_KEY_HINT);
                            url.append("=");
                            url.append(VisitorIDService.this._locationHint);
                        }
                        List<VisitorID> newCustomerIDs = VisitorIDService.this._generateCustomerIds(identifiersCopy, authenticationState);
                        String encodedIdString = VisitorIDService.this._generateCustomerIdString(newCustomerIDs);
                        if (encodedIdString != null) {
                            url.append(encodedIdString);
                        }
                        String internalIdString = VisitorIDService.this._generateInternalIdString(dpidsCopy);
                        if (internalIdString != null) {
                            url.append(internalIdString);
                        }
                        String urlString = url.toString();
                        StaticMethods.logDebugFormat("ID Service - Sending id sync call (%s)", urlString);
                        byte[] response = RequestHandler.retrieveData(urlString, null, 2000, "ID Service");
                        JSONObject responseObject = VisitorIDService.this.parseResponse(response);
                        if (responseObject != null && responseObject.has(VisitorIDService.RESPONSE_KEY_MID) && !responseObject.has(VisitorIDService.RESPONSE_KEY_ERROR)) {
                            try {
                                VisitorIDService.this._marketingCloudID = responseObject.getString(VisitorIDService.RESPONSE_KEY_MID);
                                if (responseObject.has(VisitorIDService.RESPONSE_KEY_BLOB)) {
                                    VisitorIDService.this._blob = responseObject.getString(VisitorIDService.RESPONSE_KEY_BLOB);
                                }
                                if (responseObject.has(VisitorIDService.RESPONSE_KEY_HINT)) {
                                    VisitorIDService.this._locationHint = responseObject.getString(VisitorIDService.RESPONSE_KEY_HINT);
                                }
                                if (responseObject.has(VisitorIDService.RESPONSE_KEY_TTL)) {
                                    VisitorIDService.this._ttl = responseObject.getInt(VisitorIDService.RESPONSE_KEY_TTL);
                                }
                                StaticMethods.logDebugFormat("ID Service - Got ID Response (mid: %s, blob: %s, hint: %s, ttl: %d)", VisitorIDService.this._marketingCloudID, VisitorIDService.this._blob, VisitorIDService.this._locationHint, Long.valueOf(VisitorIDService.this._ttl));
                            } catch (JSONException ex) {
                                StaticMethods.logDebugFormat("ID Service - Error parsing response (%s)", ex.getLocalizedMessage());
                            }
                        } else {
                            if (responseObject != null && responseObject.has(VisitorIDService.RESPONSE_KEY_ERROR)) {
                                try {
                                    StaticMethods.logErrorFormat("ID Service - Service returned error (%s)", responseObject.getString(VisitorIDService.RESPONSE_KEY_ERROR));
                                } catch (JSONException ex2) {
                                    StaticMethods.logErrorFormat("ID Service - Unable to read error condition(%s)", ex2.getLocalizedMessage());
                                }
                            }
                            if (VisitorIDService.this._marketingCloudID == null) {
                                VisitorIDService.this._marketingCloudID = VisitorIDService.this._generateMID();
                                VisitorIDService.this._blob = null;
                                VisitorIDService.this._locationHint = null;
                                VisitorIDService.this._ttl = 600L;
                                StaticMethods.logDebugFormat("ID Service - Did not return an ID, generating one locally (mid: %s, ttl: %d)", VisitorIDService.this._marketingCloudID, Long.valueOf(VisitorIDService.this._ttl));
                            }
                        }
                        VisitorIDService.this._lastSync = StaticMethods.getTimeSince1970();
                        VisitorIDService.this._customerIds = VisitorIDService.this._mergeCustomerIds(newCustomerIDs);
                        VisitorIDService.this._analyticsIdString = VisitorIDService.this._generateAnalyticsCustomerIdString(VisitorIDService.this._customerIds);
                        VisitorIDService.this._aamIdString = VisitorIDService.this._generateCustomerIdString(VisitorIDService.this._customerIds);
                        String idStringToStore = VisitorIDService.this._generateStoredCustomerIdString(VisitorIDService.this._customerIds);
                        WearableFunctionBridge.syncVidServiceToWearable(VisitorIDService.this._marketingCloudID, VisitorIDService.this._locationHint, VisitorIDService.this._blob, VisitorIDService.this._ttl, VisitorIDService.this._lastSync, idStringToStore);
                        try {
                            SharedPreferences.Editor e = StaticMethods.getSharedPreferencesEditor();
                            e.putString("ADBMOBILE_VISITORID_IDS", idStringToStore);
                            e.putString("ADBMOBILE_PERSISTED_MID", VisitorIDService.this._marketingCloudID);
                            e.putString("ADBMOBILE_PERSISTED_MID_HINT", VisitorIDService.this._locationHint);
                            e.putString("ADBMOBILE_PERSISTED_MID_BLOB", VisitorIDService.this._blob);
                            e.putLong("ADBMOBILE_VISITORID_TTL", VisitorIDService.this._ttl);
                            e.putLong("ADBMOBILE_VISITORID_SYNC", VisitorIDService.this._lastSync);
                            e.commit();
                        } catch (StaticMethods.NullContextException ex3) {
                            StaticMethods.logErrorFormat("ID Service - Unable to persist identifiers to shared preferences(%s)", ex3.getLocalizedMessage());
                        }
                    }
                }
            }
        });
    }

    protected final JSONObject parseResponse(byte[] response) {
        if (response == null) {
            return null;
        }
        try {
            return new JSONObject(new String(response, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            StaticMethods.logErrorFormat("ID Service - Unable to decode response(%s)", ex.getLocalizedMessage());
            return null;
        } catch (JSONException ex2) {
            StaticMethods.logDebugFormat("ID Service - Unable to parse response(%s)", ex2.getLocalizedMessage());
            return null;
        }
    }

    protected final String getMarketingCloudID() {
        FutureTask<String> f = new FutureTask<>(new Callable<String>() { // from class: com.adobe.mobile.VisitorIDService.3
            @Override // java.util.concurrent.Callable
            public String call() throws Exception {
                return VisitorIDService.this._marketingCloudID;
            }
        });
        this._visitorIDExector.execute(f);
        try {
            return f.get();
        } catch (Exception ex) {
            StaticMethods.logErrorFormat("ID Service - Unable to retrieve marketing cloud id from queue(%s)", ex.getLocalizedMessage());
            return null;
        }
    }

    protected final List<VisitorID> getIdentifiers() {
        FutureTask<List<VisitorID>> f = new FutureTask<>(new Callable<List<VisitorID>>() { // from class: com.adobe.mobile.VisitorIDService.4
            @Override // java.util.concurrent.Callable
            public List<VisitorID> call() throws Exception {
                return new ArrayList(VisitorIDService.this._customerIds);
            }
        });
        this._visitorIDExector.execute(f);
        try {
            return f.get();
        } catch (Exception ex) {
            StaticMethods.logErrorFormat("ID Service - Unable to retrieve marketing cloud identifiers from queue(%s)", ex.getLocalizedMessage());
            return null;
        }
    }

    protected final String getAnalyticsIDRequestParameterString() {
        final StringBuilder returnValue = new StringBuilder();
        FutureTask<Void> f = new FutureTask<>(new Callable<Void>() { // from class: com.adobe.mobile.VisitorIDService.5
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                if (VisitorIDService.this._marketingCloudID != null) {
                    returnValue.append("?");
                    returnValue.append(VisitorIDService.ANALYTICS_PARAMETER_KEY_MID);
                    returnValue.append("=");
                    returnValue.append(VisitorIDService.this._marketingCloudID);
                    returnValue.append("&");
                    returnValue.append(VisitorIDService.ANALYTICS_PARAMETER_KEY_ORG);
                    returnValue.append("=");
                    returnValue.append(MobileConfig.getInstance().getMarketingCloudOrganizationId());
                    return null;
                }
                return null;
            }
        });
        this._visitorIDExector.execute(f);
        try {
            f.get();
        } catch (Exception ex) {
            StaticMethods.logErrorFormat("ID Service - Unable to retrieve analytics id parameters from queue(%s)", ex.getLocalizedMessage());
        }
        return returnValue.toString();
    }

    protected final Map<String, String> getAnalyticsParameters() {
        final Map<String, String> returnValue = new HashMap<>();
        FutureTask<Void> f = new FutureTask<>(new Callable<Void>() { // from class: com.adobe.mobile.VisitorIDService.6
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                if (VisitorIDService.this._marketingCloudID != null) {
                    returnValue.put(VisitorIDService.ANALYTICS_PARAMETER_KEY_MID, VisitorIDService.this._marketingCloudID);
                    if (VisitorIDService.this._blob != null) {
                        returnValue.put(VisitorIDService.ANALYTICS_PARAMETER_KEY_BLOB, VisitorIDService.this._blob);
                    }
                    if (VisitorIDService.this._locationHint != null) {
                        returnValue.put(VisitorIDService.ANALYTICS_PARAMETER_KEY_LOCATION_HINT, VisitorIDService.this._locationHint);
                    }
                }
                return null;
            }
        });
        this._visitorIDExector.execute(f);
        try {
            f.get();
        } catch (Exception ex) {
            StaticMethods.logErrorFormat("ID Service - Unable to retrieve analytics parameters from queue(%s)", ex.getLocalizedMessage());
        }
        return returnValue;
    }

    protected final String getAnalyticsIdVisitorString() {
        final StringBuilder idBuilder = new StringBuilder();
        FutureTask<Void> f = new FutureTask<>(new Callable<Void>() { // from class: com.adobe.mobile.VisitorIDService.7
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                if (VisitorIDService.this._marketingCloudID != null) {
                    idBuilder.append("&");
                    idBuilder.append(VisitorIDService.ANALYTICS_PARAMETER_KEY_MID);
                    idBuilder.append("=");
                    idBuilder.append(VisitorIDService.this._marketingCloudID);
                    if (VisitorIDService.this._blob != null) {
                        idBuilder.append("&");
                        idBuilder.append(VisitorIDService.ANALYTICS_PARAMETER_KEY_BLOB);
                        idBuilder.append("=");
                        idBuilder.append(VisitorIDService.this._blob);
                    }
                    if (VisitorIDService.this._locationHint != null) {
                        idBuilder.append("&");
                        idBuilder.append(VisitorIDService.ANALYTICS_PARAMETER_KEY_LOCATION_HINT);
                        idBuilder.append("=");
                        idBuilder.append(VisitorIDService.this._locationHint);
                    }
                    if (VisitorIDService.this._analyticsIdString != null) {
                        idBuilder.append(VisitorIDService.this._analyticsIdString);
                    }
                }
                return null;
            }
        });
        this._visitorIDExector.execute(f);
        try {
            f.get();
        } catch (Exception ex) {
            StaticMethods.logErrorFormat("ID Service - Unable to retrieve analytics parameters from queue(%s)", ex.getLocalizedMessage());
        }
        return idBuilder.toString();
    }

    protected final String getAAMParameterString() {
        final StringBuilder returnValue = new StringBuilder();
        FutureTask<Void> f = new FutureTask<>(new Callable<Void>() { // from class: com.adobe.mobile.VisitorIDService.8
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                if (VisitorIDService.this._marketingCloudID != null) {
                    returnValue.append("&");
                    returnValue.append(VisitorIDService.RESPONSE_KEY_MID);
                    returnValue.append("=");
                    returnValue.append(VisitorIDService.this._marketingCloudID);
                    if (VisitorIDService.this._blob != null) {
                        returnValue.append("&");
                        returnValue.append(VisitorIDService.RESPONSE_KEY_BLOB);
                        returnValue.append("=");
                        returnValue.append(VisitorIDService.this._blob);
                    }
                    if (VisitorIDService.this._locationHint != null) {
                        returnValue.append("&");
                        returnValue.append(VisitorIDService.RESPONSE_KEY_HINT);
                        returnValue.append("=");
                        returnValue.append(VisitorIDService.this._locationHint);
                    }
                    if (VisitorIDService.this._aamIdString != null) {
                        returnValue.append(VisitorIDService.this._aamIdString);
                    }
                }
                return null;
            }
        });
        this._visitorIDExector.execute(f);
        try {
            f.get();
        } catch (Exception ex) {
            StaticMethods.logErrorFormat("ID Service - Unable to retrieve audience manager parameters from queue(%s)", ex.getLocalizedMessage());
        }
        return returnValue.toString();
    }

    protected final String getTargetParameterString() {
        final StringBuilder returnValue = new StringBuilder();
        FutureTask<Void> f = new FutureTask<>(new Callable<Void>() { // from class: com.adobe.mobile.VisitorIDService.9
            @Override // java.util.concurrent.Callable
            public Void call() throws Exception {
                if (VisitorIDService.this._marketingCloudID != null) {
                    returnValue.append("&");
                    returnValue.append(VisitorIDService.TARGET_PARAMETER_KEY_MID);
                    returnValue.append("=");
                    returnValue.append(VisitorIDService.this._marketingCloudID);
                    if (VisitorIDService.this._blob != null) {
                        returnValue.append("&");
                        returnValue.append(VisitorIDService.TARGET_PARAMETER_KEY_BLOB);
                        returnValue.append("=");
                        returnValue.append(VisitorIDService.this._blob);
                    }
                    if (VisitorIDService.this._locationHint != null) {
                        returnValue.append("&");
                        returnValue.append(VisitorIDService.TARGET_PARAMETER_KEY_HINT);
                        returnValue.append("=");
                        returnValue.append(VisitorIDService.this._locationHint);
                    }
                    String aid = StaticMethods.getAID();
                    if (aid != null) {
                        returnValue.append("&");
                        returnValue.append(VisitorIDService.TARGET_PARAMETER_KEY_AID);
                        returnValue.append("=");
                        returnValue.append(aid);
                    }
                }
                return null;
            }
        });
        this._visitorIDExector.execute(f);
        try {
            f.get();
        } catch (Exception ex) {
            StaticMethods.logErrorFormat("ID Service - Unable to retrieve target parameters from queue(%s)", ex.getLocalizedMessage());
        }
        return returnValue.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String _generateMID() {
        UUID uuid = UUID.randomUUID();
        long most = uuid.getMostSignificantBits();
        long least = uuid.getLeastSignificantBits();
        Locale locale = Locale.US;
        Object[] objArr = new Object[2];
        if (most < 0) {
            most = -most;
        }
        objArr[0] = Long.valueOf(most);
        if (least < 0) {
            least = -least;
        }
        objArr[1] = Long.valueOf(least);
        return String.format(locale, "%019d%019d", objArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String _generateStoredCustomerIdString(List<VisitorID> visitorIDs) {
        if (visitorIDs == null) {
            return null;
        }
        StringBuilder customerIdString = new StringBuilder();
        for (VisitorID visitorID : visitorIDs) {
            customerIdString.append("&d_cid_ic=");
            customerIdString.append(visitorID.idType);
            if (visitorID.id != null) {
                customerIdString.append(CID_DELIMITER);
                customerIdString.append(visitorID.id);
            }
            customerIdString.append(CID_DELIMITER);
            customerIdString.append(visitorID.authenticationState.getValue());
        }
        return customerIdString.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String _generateCustomerIdString(List<VisitorID> newVisitorIDs) {
        if (newVisitorIDs == null) {
            return null;
        }
        StringBuilder customerIdString = new StringBuilder();
        for (VisitorID newVisitorID : newVisitorIDs) {
            customerIdString.append("&d_cid_ic=");
            customerIdString.append(StaticMethods.URLEncode(newVisitorID.idType));
            String urlEncodedID = StaticMethods.URLEncode(newVisitorID.id);
            if (urlEncodedID != null) {
                customerIdString.append(CID_DELIMITER);
                customerIdString.append(urlEncodedID);
            }
            customerIdString.append(CID_DELIMITER);
            customerIdString.append(newVisitorID.authenticationState.getValue());
        }
        return customerIdString.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String _generateInternalIdString(Map<String, String> dpids) {
        if (dpids == null) {
            return null;
        }
        HashMap<String, String> dpidsCopy = new HashMap<>(dpids);
        StringBuilder internalIdString = new StringBuilder();
        for (Map.Entry<String, String> entry : dpidsCopy.entrySet()) {
            internalIdString.append("&d_cid=");
            internalIdString.append(StaticMethods.URLEncode(entry.getKey()));
            internalIdString.append(CID_DELIMITER);
            internalIdString.append(StaticMethods.URLEncode(entry.getValue()));
        }
        return internalIdString.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String _generateAnalyticsCustomerIdString(List<VisitorID> visitorIDs) {
        if (visitorIDs == null) {
            return null;
        }
        HashMap<String, Object> visitorIdMap = new HashMap<>();
        for (VisitorID visitorID : visitorIDs) {
            visitorIdMap.put(visitorID.serializeIdentifierKeyForAnalyticsID(), visitorID.id);
            visitorIdMap.put(visitorID.serializeAuthenticationKeyForAnalyticsID(), Integer.valueOf(visitorID.authenticationState.getValue()));
        }
        HashMap<String, Object> translatedIds = new HashMap<>();
        translatedIds.put("cid", StaticMethods.translateContextData(visitorIdMap));
        StringBuilder requestString = new StringBuilder(2048);
        StaticMethods.serializeToQueryString(translatedIds, requestString);
        return requestString.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<VisitorID> _generateCustomerIds(Map<String, String> identifiers, VisitorID.VisitorIDAuthenticationState authenticationState) {
        if (identifiers == null) {
            return null;
        }
        HashMap<String, String> identifiersCopy = new HashMap<>(identifiers);
        List<VisitorID> tempIds = new ArrayList<>();
        for (Map.Entry<String, String> newID : identifiersCopy.entrySet()) {
            try {
                tempIds.add(new VisitorID(VISITOR_ID_PARAMETER_KEY_CUSTOMER, newID.getKey(), newID.getValue(), authenticationState));
            } catch (IllegalStateException ex) {
                StaticMethods.logWarningFormat("ID Service - Unable to create ID after encoding:(%s)", ex.getLocalizedMessage());
            }
        }
        return tempIds;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<VisitorID> _mergeCustomerIds(List<VisitorID> newCustomerIds) {
        if (newCustomerIds == null) {
            return null;
        }
        List<VisitorID> tempIds = this._customerIds != null ? new ArrayList<>(this._customerIds) : new ArrayList<>();
        for (VisitorID newID : newCustomerIds) {
            Iterator<VisitorID> it = tempIds.iterator();
            while (true) {
                if (it.hasNext()) {
                    VisitorID visitorID = it.next();
                    if (visitorID.isVisitorID(newID.idType, newID.id)) {
                        visitorID.authenticationState = newID.authenticationState;
                        break;
                    }
                } else {
                    try {
                        tempIds.add(newID);
                        break;
                    } catch (IllegalStateException ex) {
                        StaticMethods.logWarningFormat("ID Service - Unable to create ID after encoding:(%s)", ex.getLocalizedMessage());
                    }
                }
            }
        }
        return tempIds;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<VisitorID> _parseIdString(String idString) {
        if (idString == null) {
            return null;
        }
        List<String> customerIdComponentsArray = Arrays.asList(idString.split("&"));
        List<VisitorID> visitorIDs = new ArrayList<>();
        for (String customerIdString : customerIdComponentsArray) {
            if (customerIdString.length() > 0) {
                List<String> internalId = Arrays.asList(customerIdString.split("="));
                List<String> idinfo = Arrays.asList(internalId.get(1).split(CID_DELIMITER));
                if (internalId.size() == 2 && idinfo.size() == 3) {
                    try {
                        VisitorID id = new VisitorID(internalId.get(0), idinfo.get(0), idinfo.get(1), VisitorID.VisitorIDAuthenticationState.values()[Integer.parseInt(idinfo.get(2))]);
                        visitorIDs.add(id);
                    } catch (IllegalStateException ex) {
                        StaticMethods.logWarningFormat("ID Service - Unable to create ID after encoding:(%s)", ex.getLocalizedMessage());
                    } catch (NumberFormatException ex2) {
                        StaticMethods.logErrorFormat("ID Service - Unable to parse visitor ID: (%s) exception:(%s)", idString, ex2.getLocalizedMessage());
                    }
                } else {
                    return visitorIDs;
                }
            }
        }
        return visitorIDs;
    }
}
