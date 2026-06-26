package com.adobe.mobile;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.adobe.mobile.RemoteDownload;
import com.adobe.mobile.StaticMethods;
import com.google.android.gms.search.SearchAuth;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
final class MobileConfig {
    private static final String CONFIG_FILE_NAME = "ADBMobileConfig.json";
    private static final String CONFIG_PRIVACY_OPTED_IN = "optedin";
    private static final String CONFIG_PRIVACY_OPTED_OUT = "optedout";
    private static final String CONFIG_PRIVACY_UNKNOWN = "optunknown";
    private static final boolean DEFAULT_AAM_ANALYTICS_FORWARD_ENABLED = false;
    private static final boolean DEFAULT_BACKDATE_SESSION_INFO = true;
    private static final int DEFAULT_BATCH_LIMIT = 0;
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final int DEFAULT_LIFECYCLE_TIMEOUT = 300;
    private static final int DEFAULT_LOCATION_TIMEOUT = 2;
    private static final boolean DEFAULT_OFFLINE_TRACKING = false;
    private static final int DEFAULT_REFERRER_TIMEOUT = 0;
    private static final boolean DEFAULT_SSL = false;
    private static final String JSON_CONFIG_AAM_ANALYTICS_FORWARD_KEY = "analyticsForwardingEnabled";
    private static final String JSON_CONFIG_AAM_KEY = "audienceManager";
    private static final String JSON_CONFIG_ACQUISITION_KEY = "acquisition";
    private static final String JSON_CONFIG_ANALYTICS_KEY = "analytics";
    private static final String JSON_CONFIG_APP_IDENTIFIER_KEY = "appid";
    private static final String JSON_CONFIG_BACKDATE_SESSION_INFO_KEY = "backdateSessionInfo";
    private static final String JSON_CONFIG_BATCH_LIMIT_KEY = "batchLimit";
    private static final String JSON_CONFIG_CHAR_SET_KEY = "charset";
    private static final String JSON_CONFIG_CLIENT_CODE_KEY = "clientCode";
    private static final String JSON_CONFIG_LIFECYCLE_TIMEOUT_KEY = "lifecycleTimeout";
    private static final String JSON_CONFIG_MARKETINGCLOUD_KEY = "marketingCloud";
    private static final String JSON_CONFIG_MESSAGES_KEY = "messages";
    private static final String JSON_CONFIG_MESSAGES_URL_KEY = "messages";
    private static final String JSON_CONFIG_OFFLINE_TRACKING_KEY = "offlineEnabled";
    private static final String JSON_CONFIG_ORGID_KEY = "org";
    private static final String JSON_CONFIG_POINTS_OF_INTEREST_KEY = "poi";
    private static final String JSON_CONFIG_POI_REMOTES_KEY = "analytics.poi";
    private static final String JSON_CONFIG_PRIVACY_DEFAULT_KEY = "privacyDefault";
    private static final String JSON_CONFIG_REFERRER_TIMEOUT_KEY = "referrerTimeout";
    private static final String JSON_CONFIG_REMOTES_KEY = "remotes";
    private static final String JSON_CONFIG_REPORT_SUITES_KEY = "rsids";
    private static final String JSON_CONFIG_SERVER_KEY = "server";
    private static final String JSON_CONFIG_SSL_KEY = "ssl";
    private static final String JSON_CONFIG_TARGET_KEY = "target";
    private static final String JSON_CONFIG_TIMEOUT_KEY = "timeout";
    private boolean _aamAnalyticsForwardingEnabled;
    private String _aamServer;
    private String _acquisitionAppIdentifier;
    private String _acquisitionServer;
    private boolean _backdateSessionInfoEnabled;
    private int _batchLimit;
    private String _characterSet;
    private String _clientCode;
    private int _defaultLocationTimeout;
    private int _lifecycleTimeout;
    private String _marketingCloudOrganizationId;
    private String _messagesURL;
    private boolean _offlineTrackingEnabled;
    private String _pointsOfInterestURL;
    private MobilePrivacyStatus _privacyStatus;
    private int _referrerTimeout;
    private String _reportSuiteIds;
    private boolean _ssl;
    private String _trackingServer;
    private static final MobilePrivacyStatus DEFAULT_PRIVACY_STATUS = MobilePrivacyStatus.MOBILE_PRIVACY_STATUS_OPT_IN;
    private static MobileConfig _instance = null;
    private static final Object _instanceMutex = new Object();
    private static final Object _usingAnalyticsMutex = new Object();
    private static final Object _usingGooglePlayServicesMutex = new Object();
    private static final Object _usingAudienceManagerMutex = new Object();
    private static final Object _usingTargetMutex = new Object();
    private static InputStream _userDefinedInputStream = null;
    private static final Object _userDefinedInputStreamMutex = new Object();
    private boolean _networkConnectivity = false;
    private List<List<Object>> _pointsOfInterest = null;
    private ArrayList<Message> _inAppMessages = null;
    private ArrayList<Message> _callbackTemplates = null;
    private Boolean _usingAnalytics = null;
    private Boolean _usingGooglePlayServices = null;
    private Boolean _usingAudienceManager = null;
    private Boolean _usingTarget = null;

    protected static MobileConfig getInstance() {
        MobileConfig mobileConfig;
        synchronized (_instanceMutex) {
            if (_instance == null) {
                _instance = new MobileConfig();
            }
            mobileConfig = _instance;
        }
        return mobileConfig;
    }

    private MobileConfig() throws Throwable {
        String privacyString;
        this._reportSuiteIds = null;
        this._trackingServer = null;
        this._characterSet = DEFAULT_CHARSET;
        this._ssl = false;
        this._offlineTrackingEnabled = false;
        this._backdateSessionInfoEnabled = true;
        this._lifecycleTimeout = DEFAULT_LIFECYCLE_TIMEOUT;
        this._referrerTimeout = 0;
        this._batchLimit = 0;
        this._privacyStatus = DEFAULT_PRIVACY_STATUS;
        this._pointsOfInterestURL = null;
        this._clientCode = null;
        this._defaultLocationTimeout = 2;
        this._aamServer = null;
        this._aamAnalyticsForwardingEnabled = false;
        this._acquisitionServer = null;
        this._acquisitionAppIdentifier = null;
        this._messagesURL = null;
        this._marketingCloudOrganizationId = null;
        startNotifier();
        JSONObject configSettings = loadBundleConfig();
        if (configSettings != null) {
            JSONObject analyticsSettings = null;
            try {
                analyticsSettings = configSettings.getJSONObject(JSON_CONFIG_ANALYTICS_KEY);
            } catch (JSONException e) {
                StaticMethods.logDebugFormat("Analytics - Not configured.", new Object[0]);
            }
            if (analyticsSettings != null) {
                try {
                    this._trackingServer = analyticsSettings.getString(JSON_CONFIG_SERVER_KEY);
                    this._reportSuiteIds = analyticsSettings.getString(JSON_CONFIG_REPORT_SUITES_KEY);
                } catch (JSONException e2) {
                    this._trackingServer = null;
                    this._reportSuiteIds = null;
                    StaticMethods.logDebugFormat("Analytics - Not Configured.", new Object[0]);
                }
                try {
                    this._characterSet = analyticsSettings.getString(JSON_CONFIG_CHAR_SET_KEY);
                } catch (JSONException e3) {
                    this._characterSet = DEFAULT_CHARSET;
                }
                try {
                    this._ssl = analyticsSettings.getBoolean(JSON_CONFIG_SSL_KEY);
                } catch (JSONException e4) {
                    this._ssl = false;
                }
                try {
                    this._offlineTrackingEnabled = analyticsSettings.getBoolean(JSON_CONFIG_OFFLINE_TRACKING_KEY);
                } catch (JSONException e5) {
                    this._offlineTrackingEnabled = false;
                }
                try {
                    this._backdateSessionInfoEnabled = analyticsSettings.getBoolean(JSON_CONFIG_BACKDATE_SESSION_INFO_KEY);
                } catch (JSONException e6) {
                    this._backdateSessionInfoEnabled = true;
                }
                try {
                    this._lifecycleTimeout = analyticsSettings.getInt(JSON_CONFIG_LIFECYCLE_TIMEOUT_KEY);
                } catch (JSONException e7) {
                    this._lifecycleTimeout = DEFAULT_LIFECYCLE_TIMEOUT;
                }
                try {
                    this._referrerTimeout = analyticsSettings.getInt(JSON_CONFIG_REFERRER_TIMEOUT_KEY);
                } catch (JSONException e8) {
                    this._referrerTimeout = 0;
                }
                try {
                    this._batchLimit = analyticsSettings.getInt(JSON_CONFIG_BATCH_LIMIT_KEY);
                } catch (JSONException e9) {
                    this._batchLimit = 0;
                }
                try {
                    if (StaticMethods.getSharedPreferences().contains("PrivacyStatus")) {
                        this._privacyStatus = MobilePrivacyStatus.values()[StaticMethods.getSharedPreferences().getInt("PrivacyStatus", 0)];
                    } else {
                        try {
                            privacyString = analyticsSettings.getString(JSON_CONFIG_PRIVACY_DEFAULT_KEY);
                        } catch (JSONException e10) {
                            privacyString = null;
                        }
                        this._privacyStatus = privacyString != null ? privacyStatusFromString(privacyString) : DEFAULT_PRIVACY_STATUS;
                    }
                    try {
                        JSONArray poiArray = analyticsSettings.getJSONArray(JSON_CONFIG_POINTS_OF_INTEREST_KEY);
                        loadPoiFromJsonArray(poiArray);
                    } catch (JSONException e11) {
                        StaticMethods.logErrorFormat("Analytics - Malformed POI List(%s)", e11.getLocalizedMessage());
                    }
                } catch (StaticMethods.NullContextException e12) {
                    StaticMethods.logErrorFormat("Config - Error pulling privacy from shared preferences. (%s)", e12.getMessage());
                    return;
                }
            }
            JSONObject targetSettings = null;
            try {
                targetSettings = configSettings.getJSONObject(JSON_CONFIG_TARGET_KEY);
            } catch (JSONException e13) {
                StaticMethods.logDebugFormat("Target - Not Configured.", new Object[0]);
            }
            if (targetSettings != null) {
                try {
                    this._clientCode = targetSettings.getString(JSON_CONFIG_CLIENT_CODE_KEY);
                } catch (JSONException e14) {
                    this._clientCode = null;
                    StaticMethods.logDebugFormat("Target - Not Configured.", new Object[0]);
                }
                try {
                    this._defaultLocationTimeout = targetSettings.getInt(JSON_CONFIG_TIMEOUT_KEY);
                } catch (JSONException e15) {
                    this._defaultLocationTimeout = 2;
                }
            }
            JSONObject aamSettings = null;
            try {
                aamSettings = configSettings.getJSONObject(JSON_CONFIG_AAM_KEY);
            } catch (JSONException e16) {
                StaticMethods.logDebugFormat("Audience Manager - Not Configured.", new Object[0]);
            }
            if (aamSettings != null) {
                try {
                    this._aamServer = aamSettings.getString(JSON_CONFIG_SERVER_KEY);
                } catch (JSONException e17) {
                    this._aamServer = null;
                    StaticMethods.logDebugFormat("Audience Manager - Not Configured.", new Object[0]);
                }
                try {
                    this._aamAnalyticsForwardingEnabled = aamSettings.getBoolean(JSON_CONFIG_AAM_ANALYTICS_FORWARD_KEY);
                } catch (JSONException e18) {
                    this._aamAnalyticsForwardingEnabled = false;
                }
                if (this._aamAnalyticsForwardingEnabled) {
                    StaticMethods.logDebugFormat("Audience Manager - Analytics Server-Side Forwarding Is Enabled.", new Object[0]);
                }
            }
            JSONObject acquisitionSettings = null;
            try {
                acquisitionSettings = configSettings.getJSONObject(JSON_CONFIG_ACQUISITION_KEY);
            } catch (JSONException e19) {
                StaticMethods.logDebugFormat("Acquisition - Not Configured.", new Object[0]);
            }
            if (acquisitionSettings != null) {
                try {
                    this._acquisitionAppIdentifier = acquisitionSettings.getString(JSON_CONFIG_APP_IDENTIFIER_KEY);
                    this._acquisitionServer = acquisitionSettings.getString(JSON_CONFIG_SERVER_KEY);
                } catch (JSONException e20) {
                    this._acquisitionAppIdentifier = null;
                    this._acquisitionServer = null;
                    StaticMethods.logDebugFormat("Acquisition - Not configured correctly (missing server or app identifier).", new Object[0]);
                }
            }
            JSONObject remoteURLs = null;
            try {
                remoteURLs = configSettings.getJSONObject(JSON_CONFIG_REMOTES_KEY);
            } catch (JSONException e21) {
                StaticMethods.logDebugFormat("Remotes - Not Configured.", new Object[0]);
            }
            if (remoteURLs != null) {
                try {
                    this._messagesURL = remoteURLs.getString("messages");
                } catch (JSONException e22) {
                    StaticMethods.logDebugFormat("Config - No in-app messages remote url loaded (%s)", e22.getLocalizedMessage());
                }
                try {
                    this._pointsOfInterestURL = remoteURLs.getString(JSON_CONFIG_POI_REMOTES_KEY);
                } catch (JSONException e23) {
                    StaticMethods.logDebugFormat("Config - No points of interest remote url loaded (%s)", e23.getLocalizedMessage());
                }
            }
            JSONArray jsonMessages = null;
            try {
                jsonMessages = configSettings.getJSONArray("messages");
            } catch (JSONException e24) {
                StaticMethods.logDebugFormat("Messages - Not configured locally.", new Object[0]);
            }
            if (jsonMessages != null && jsonMessages.length() > 0) {
                loadMessagesFromJsonArray(jsonMessages);
            }
            JSONObject marketingCloudSettings = null;
            try {
                marketingCloudSettings = configSettings.getJSONObject(JSON_CONFIG_MARKETINGCLOUD_KEY);
            } catch (JSONException e25) {
                StaticMethods.logDebugFormat("Marketing Cloud - Not configured locally.", new Object[0]);
            }
            if (marketingCloudSettings != null) {
                try {
                    this._marketingCloudOrganizationId = marketingCloudSettings.getString(JSON_CONFIG_ORGID_KEY);
                } catch (JSONException e26) {
                    this._marketingCloudOrganizationId = null;
                    StaticMethods.logDebugFormat("Visitor - ID Service Not Configured.", new Object[0]);
                }
            }
            loadCachedRemotes();
            updateBlacklist();
        }
    }

    protected boolean mobileUsingAnalytics() {
        boolean zBooleanValue;
        boolean z = false;
        synchronized (_usingAnalyticsMutex) {
            if (this._usingAnalytics == null) {
                if (getReportSuiteIds() != null && getReportSuiteIds().length() > 0 && getTrackingServer() != null && getTrackingServer().length() > 0) {
                    z = true;
                }
                this._usingAnalytics = Boolean.valueOf(z);
                if (!this._usingAnalytics.booleanValue()) {
                    StaticMethods.logDebugFormat("Analytics - Your config file is not set up to use Analytics(missing report suite id(s) or tracking server information)", new Object[0]);
                }
            }
            zBooleanValue = this._usingAnalytics.booleanValue();
        }
        return zBooleanValue;
    }

    protected boolean mobileUsingGooglePlayServices() {
        boolean zBooleanValue;
        synchronized (_usingGooglePlayServicesMutex) {
            if (this._usingGooglePlayServices == null) {
                this._usingGooglePlayServices = Boolean.valueOf(WearableFunctionBridge.isGooglePlayServicesEnabled());
            }
            zBooleanValue = this._usingGooglePlayServices.booleanValue();
        }
        return zBooleanValue;
    }

    protected boolean mobileUsingAudienceManager() {
        boolean zBooleanValue = false;
        if (!StaticMethods.isWearableApp()) {
            synchronized (_usingAudienceManagerMutex) {
                if (this._usingAudienceManager == null) {
                    if (getAamServer() != null && getAamServer().length() > 0) {
                        zBooleanValue = true;
                    }
                    this._usingAudienceManager = Boolean.valueOf(zBooleanValue);
                    if (!this._usingAudienceManager.booleanValue()) {
                        StaticMethods.logDebugFormat("Audience Manager - Your config file is not set up to use Audience Manager(missing audience manager server information)", new Object[0]);
                    }
                }
                zBooleanValue = this._usingAudienceManager.booleanValue();
            }
        }
        return zBooleanValue;
    }

    protected boolean mobileUsingTarget() {
        boolean zBooleanValue = false;
        if (!StaticMethods.isWearableApp()) {
            synchronized (_usingTargetMutex) {
                if (this._usingTarget == null) {
                    if (getClientCode() != null && getClientCode().length() > 0) {
                        zBooleanValue = true;
                    }
                    this._usingTarget = Boolean.valueOf(zBooleanValue);
                    if (!this._usingTarget.booleanValue()) {
                        StaticMethods.logDebugFormat("TargetWorker - Your config file is not set up to use Target(missing client code information)", new Object[0]);
                    }
                }
                zBooleanValue = this._usingTarget.booleanValue();
            }
        }
        return zBooleanValue;
    }

    protected boolean mobileReferrerConfigured() {
        return this._acquisitionServer != null && this._acquisitionAppIdentifier != null && this._acquisitionServer.length() > 0 && this._acquisitionAppIdentifier.length() > 0;
    }

    protected String getReportSuiteIds() {
        return this._reportSuiteIds;
    }

    protected String getTrackingServer() {
        return this._trackingServer;
    }

    protected String getCharacterSet() {
        return this._characterSet;
    }

    protected boolean getSSL() {
        return this._ssl;
    }

    protected boolean getOfflineTrackingEnabled() {
        return this._offlineTrackingEnabled;
    }

    protected boolean getBackdateSessionInfoEnabled() {
        return this._backdateSessionInfoEnabled;
    }

    protected int getLifecycleTimeout() {
        return this._lifecycleTimeout;
    }

    protected int getBatchLimit() {
        return this._batchLimit;
    }

    protected void setPrivacyStatus(MobilePrivacyStatus status) {
        if (status != null) {
            if (status == MobilePrivacyStatus.MOBILE_PRIVACY_STATUS_UNKNOWN && !this._offlineTrackingEnabled) {
                StaticMethods.logWarningFormat("Analytics - Cannot set privacy status to unknown when offline tracking is disabled", new Object[0]);
                return;
            }
            if (status == MobilePrivacyStatus.MOBILE_PRIVACY_STATUS_OPT_IN) {
                StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.MobileConfig.1
                    @Override // java.lang.Runnable
                    public void run() {
                        StaticMethods.logDebugFormat("Analytics - Privacy status set to opt in, attempting to send all hits in queue.", new Object[0]);
                        AnalyticsWorker.sharedInstance().kick(false);
                        ThirdPartyQueue.sharedInstance().kick(false);
                    }
                });
            }
            if (status == MobilePrivacyStatus.MOBILE_PRIVACY_STATUS_OPT_OUT) {
                StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.MobileConfig.2
                    @Override // java.lang.Runnable
                    public void run() {
                        StaticMethods.logDebugFormat("Analytics - Privacy status set to opt out, attempting to clear queue of all hits.", new Object[0]);
                        AnalyticsWorker.sharedInstance().clearTrackingQueue();
                        ThirdPartyQueue.sharedInstance().clearTrackingQueue();
                    }
                });
                StaticMethods.getAudienceExecutor().execute(new Runnable() { // from class: com.adobe.mobile.MobileConfig.3
                    @Override // java.lang.Runnable
                    public void run() {
                        StaticMethods.logDebugFormat("Audience Manager - Privacy status set to opt out, clearing Audience Manager information.", new Object[0]);
                        AudienceManagerWorker.Reset();
                    }
                });
            }
            this._privacyStatus = status;
            WearableFunctionBridge.syncPrivacyStatusToWearable(status.getValue());
            try {
                SharedPreferences.Editor editor = StaticMethods.getSharedPreferencesEditor();
                editor.putInt("PrivacyStatus", status.getValue());
                editor.commit();
            } catch (StaticMethods.NullContextException e) {
                StaticMethods.logErrorFormat("Config - Error persisting privacy status (%s).", e.getMessage());
            }
        }
    }

    protected MobilePrivacyStatus getPrivacyStatus() {
        return this._privacyStatus;
    }

    protected List<List<Object>> getPointsOfInterest() {
        return this._pointsOfInterest;
    }

    protected int getReferrerTimeout() {
        return this._referrerTimeout * 1000;
    }

    protected int getAnalyticsResponseType() {
        return this._aamAnalyticsForwardingEnabled ? 10 : 0;
    }

    protected String getClientCode() {
        return this._clientCode;
    }

    protected int getDefaultLocationTimeout() {
        return this._defaultLocationTimeout;
    }

    protected String getAamServer() {
        return this._aamServer;
    }

    protected boolean getAamAnalyticsForwardingEnabled() {
        return this._aamAnalyticsForwardingEnabled;
    }

    protected String getAcquisitionAppId() {
        return this._acquisitionAppIdentifier;
    }

    protected String getAcquisitionServer() {
        return this._acquisitionServer;
    }

    protected void downloadRemoteConfigs() {
        StaticMethods.getMessagesExecutor().execute(new Runnable() { // from class: com.adobe.mobile.MobileConfig.4
            @Override // java.lang.Runnable
            public void run() {
                if (MobileConfig.this._messagesURL == null || MobileConfig.this._messagesURL.length() <= 0) {
                    MobileConfig.this.loadMessageImages();
                } else {
                    RemoteDownload.remoteDownloadSync(MobileConfig.this._messagesURL, new RemoteDownload.RemoteDownloadBlock() { // from class: com.adobe.mobile.MobileConfig.4.1
                        @Override // com.adobe.mobile.RemoteDownload.RemoteDownloadBlock
                        public void call(boolean modified, File file) throws Throwable {
                            MobileConfig.this.updateMessagesData(file);
                            MobileConfig.this.loadMessageImages();
                            MobileConfig.this.updateBlacklist();
                        }
                    });
                }
            }
        });
        StaticMethods.getThirdPartyCallbacksExecutor().execute(new Runnable() { // from class: com.adobe.mobile.MobileConfig.5
            @Override // java.lang.Runnable
            public void run() {
                FutureTask<Void> f = new FutureTask<>(new Callable<Void>() { // from class: com.adobe.mobile.MobileConfig.5.1
                    @Override // java.util.concurrent.Callable
                    public Void call() throws Exception {
                        return null;
                    }
                });
                StaticMethods.getMessagesExecutor().execute(f);
                try {
                    f.get();
                } catch (Exception e) {
                    StaticMethods.logErrorFormat("Data Callback - Error waiting for callbacks being loaded (%s)", e.getMessage());
                }
            }
        });
        if (this._pointsOfInterestURL != null && this._pointsOfInterestURL.length() > 0) {
            RemoteDownload.remoteDownloadAsync(this._pointsOfInterestURL, new RemoteDownload.RemoteDownloadBlock() { // from class: com.adobe.mobile.MobileConfig.6
                @Override // com.adobe.mobile.RemoteDownload.RemoteDownloadBlock
                public void call(boolean modified, final File file) {
                    StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.MobileConfig.6.1
                        @Override // java.lang.Runnable
                        public void run() throws Throwable {
                            if (file != null) {
                                StaticMethods.logDebugFormat("Config - Using remote definition for points of interest", new Object[0]);
                                MobileConfig.this.updatePOIData(file);
                            }
                        }
                    });
                }
            });
        }
    }

    protected void updateMessagesData(File file) throws Throwable {
        FileInputStream fis = null;
        try {
            if (file == null) {
                if (0 != 0) {
                    try {
                        fis.close();
                        return;
                    } catch (IOException e) {
                        StaticMethods.logErrorFormat("Messages - Unable to close file stream (%s)", e.getLocalizedMessage());
                        return;
                    }
                }
                return;
            }
            try {
                FileInputStream fis2 = new FileInputStream(file);
                try {
                    JSONObject jsonData = loadConfigFromStream(fis2);
                    loadMessagesDataFromRemote(jsonData);
                    if (fis2 != null) {
                        try {
                            fis2.close();
                            fis = fis2;
                        } catch (IOException e2) {
                            StaticMethods.logErrorFormat("Messages - Unable to close file stream (%s)", e2.getLocalizedMessage());
                            fis = fis2;
                        }
                    } else {
                        fis = fis2;
                    }
                } catch (IOException e3) {
                    e = e3;
                    fis = fis2;
                    StaticMethods.logWarningFormat("Messages - Unable to open messages config file, falling back to bundled messages (%s)", e.getLocalizedMessage());
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e4) {
                            StaticMethods.logErrorFormat("Messages - Unable to close file stream (%s)", e4.getLocalizedMessage());
                        }
                    }
                } catch (JSONException e5) {
                    e = e5;
                    fis = fis2;
                    StaticMethods.logErrorFormat("Messages - Unable to read messages remote config file, falling back to bundled messages (%s)", e.getLocalizedMessage());
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e6) {
                            StaticMethods.logErrorFormat("Messages - Unable to close file stream (%s)", e6.getLocalizedMessage());
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    fis = fis2;
                }
            } catch (IOException e7) {
                e = e7;
            } catch (JSONException e8) {
                e = e8;
            }
            return;
        } catch (Throwable th2) {
            th = th2;
        }
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e9) {
                StaticMethods.logErrorFormat("Messages - Unable to close file stream (%s)", e9.getLocalizedMessage());
            }
        }
        throw th;
    }

    protected String getPointsOfInterestURL() {
        return this._pointsOfInterestURL;
    }

    protected void updatePOIData(File file) throws Throwable {
        FileInputStream fis = null;
        try {
            if (file == null) {
                if (0 != 0) {
                    try {
                        fis.close();
                        return;
                    } catch (IOException e) {
                        StaticMethods.logErrorFormat("Config - Unable to close file stream (%s)", e.getLocalizedMessage());
                        return;
                    }
                }
                return;
            }
            try {
                FileInputStream fis2 = new FileInputStream(file);
                try {
                    JSONObject jsonData = loadConfigFromStream(fis2);
                    if (jsonData != null) {
                        JSONObject analytics = jsonData.getJSONObject(JSON_CONFIG_ANALYTICS_KEY);
                        JSONArray poiArray = analytics.getJSONArray(JSON_CONFIG_POINTS_OF_INTEREST_KEY);
                        loadPoiFromJsonArray(poiArray);
                    }
                    if (fis2 != null) {
                        try {
                            fis2.close();
                            fis = fis2;
                        } catch (IOException e2) {
                            StaticMethods.logErrorFormat("Config - Unable to close file stream (%s)", e2.getLocalizedMessage());
                            fis = fis2;
                        }
                    } else {
                        fis = fis2;
                    }
                } catch (IOException e3) {
                    ex = e3;
                    fis = fis2;
                    StaticMethods.logWarningFormat("Config - Unable to open points of interest config file, falling back to bundled poi (%s)", ex.getLocalizedMessage());
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e4) {
                            StaticMethods.logErrorFormat("Config - Unable to close file stream (%s)", e4.getLocalizedMessage());
                        }
                    }
                } catch (JSONException e5) {
                    ex = e5;
                    fis = fis2;
                    StaticMethods.logErrorFormat("Config - Unable to read points of interest remote config file, falling back to bundled poi (%s)", ex.getLocalizedMessage());
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e6) {
                            StaticMethods.logErrorFormat("Config - Unable to close file stream (%s)", e6.getLocalizedMessage());
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    fis = fis2;
                }
            } catch (IOException e7) {
                ex = e7;
            } catch (JSONException e8) {
                ex = e8;
            }
            return;
        } catch (Throwable th2) {
            th = th2;
        }
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e9) {
                StaticMethods.logErrorFormat("Config - Unable to close file stream (%s)", e9.getLocalizedMessage());
            }
        }
        throw th;
    }

    protected ArrayList<Message> getInAppMessages() {
        return this._inAppMessages;
    }

    protected String getInAppMessageURL() {
        return this._messagesURL;
    }

    protected ArrayList<Message> getCallbackTemplates() {
        return this._callbackTemplates;
    }

    protected String getMarketingCloudOrganizationId() {
        return this._marketingCloudOrganizationId;
    }

    protected boolean getVisitorIdServiceEnabled() {
        return this._marketingCloudOrganizationId != null && this._marketingCloudOrganizationId.length() > 0;
    }

    private JSONObject loadBundleConfig() {
        InputStream userPath;
        JSONObject jsonData = null;
        synchronized (_userDefinedInputStreamMutex) {
            userPath = _userDefinedInputStream;
        }
        if (userPath != null) {
            try {
                StaticMethods.logDebugFormat("Config - Attempting to load config file from override stream", new Object[0]);
                jsonData = loadConfigFromStream(userPath);
            } catch (IOException e) {
                StaticMethods.logDebugFormat("Config - Error loading user defined config (%s)", e.getMessage());
            } catch (JSONException e2) {
                StaticMethods.logDebugFormat("Config - Error parsing user defined config (%s)", e2.getMessage());
            }
        }
        if (jsonData == null) {
            if (userPath != null) {
                StaticMethods.logDebugFormat("Config - Failed attempting to load custom config, will fall back to standard config location.", new Object[0]);
            }
            StaticMethods.logDebugFormat("Config - Attempting to load config file from default location", new Object[0]);
            JSONObject jsonData2 = loadConfigFile(CONFIG_FILE_NAME);
            if (jsonData2 == null) {
                StaticMethods.logDebugFormat("Config - Could not find config file at expected location.  Attempting to load from www folder", new Object[0]);
                return loadConfigFile("www" + File.separator + CONFIG_FILE_NAME);
            }
            return jsonData2;
        }
        return jsonData;
    }

    private JSONObject loadConfigFile(String configFilePath) {
        AssetManager assets;
        JSONObject jsonData = null;
        try {
            Resources resources = StaticMethods.getSharedContext().getResources();
            if (resources == null || (assets = resources.getAssets()) == null) {
                return null;
            }
            jsonData = loadConfigFromStream(assets.open(configFilePath));
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logErrorFormat("Config - Null context when attempting to read config file (%s)", e.getMessage());
        } catch (IOException e2) {
            StaticMethods.logErrorFormat("Config - Exception loading config file (%s)", e2.getMessage());
        } catch (JSONException e3) {
            StaticMethods.logErrorFormat("Config - Exception parsing config file (%s)", e3.getMessage());
        }
        return jsonData;
    }

    protected void loadCachedRemotes() throws Throwable {
        if (this._messagesURL != null && this._messagesURL.length() > 0) {
            updateMessagesData(RemoteDownload.getFileForCachedURL(this._messagesURL));
        }
        if (this._pointsOfInterestURL != null && this._pointsOfInterestURL.length() > 0) {
            updatePOIData(RemoteDownload.getFileForCachedURL(this._pointsOfInterestURL));
        }
    }

    private JSONObject loadConfigFromStream(InputStream stream) throws JSONException, IOException {
        try {
            if (stream == null) {
                return null;
            }
            try {
                byte[] data = new byte[stream.available()];
                stream.read(data);
                String jsonString = new String(data, DEFAULT_CHARSET);
                JSONObject jSONObject = new JSONObject(jsonString);
                try {
                    stream.close();
                    return jSONObject;
                } catch (IOException e) {
                    StaticMethods.logErrorFormat("Config - Unable to close stream (%s)", e.getMessage());
                    return jSONObject;
                }
            } catch (IOException e2) {
                StaticMethods.logErrorFormat("Config - Exception when reading config (%s)", e2.getMessage());
                try {
                    stream.close();
                } catch (IOException e3) {
                    StaticMethods.logErrorFormat("Config - Unable to close stream (%s)", e3.getMessage());
                }
                return new JSONObject();
            } catch (NullPointerException e4) {
                StaticMethods.logErrorFormat("Config - Stream closed when attempting to load config (%s)", e4.getMessage());
                try {
                    stream.close();
                } catch (IOException e5) {
                    StaticMethods.logErrorFormat("Config - Unable to close stream (%s)", e5.getMessage());
                }
                return new JSONObject();
            }
        } catch (Throwable th) {
            try {
                stream.close();
            } catch (IOException e6) {
                StaticMethods.logErrorFormat("Config - Unable to close stream (%s)", e6.getMessage());
            }
            throw th;
        }
    }

    public static void setUserDefinedConfigPath(InputStream stream) {
        synchronized (_userDefinedInputStreamMutex) {
            if (_userDefinedInputStream == null) {
                _userDefinedInputStream = stream;
            }
        }
    }

    private MobilePrivacyStatus privacyStatusFromString(String string) {
        if (string != null) {
            if (string.equalsIgnoreCase(CONFIG_PRIVACY_OPTED_IN)) {
                return MobilePrivacyStatus.MOBILE_PRIVACY_STATUS_OPT_IN;
            }
            if (string.equalsIgnoreCase(CONFIG_PRIVACY_OPTED_OUT)) {
                return MobilePrivacyStatus.MOBILE_PRIVACY_STATUS_OPT_OUT;
            }
            if (string.equalsIgnoreCase(CONFIG_PRIVACY_UNKNOWN)) {
                return MobilePrivacyStatus.MOBILE_PRIVACY_STATUS_UNKNOWN;
            }
        }
        return DEFAULT_PRIVACY_STATUS;
    }

    private void loadPoiFromJsonArray(JSONArray poiArray) {
        if (poiArray != null) {
            try {
                this._pointsOfInterest = new ArrayList();
                int count = poiArray.length();
                for (int i = 0; i < count; i++) {
                    JSONArray singlePOI = poiArray.getJSONArray(i);
                    ArrayList<Object> singlePoint = new ArrayList<>(4);
                    singlePoint.add(singlePOI.getString(0));
                    singlePoint.add(Double.valueOf(singlePOI.getDouble(1)));
                    singlePoint.add(Double.valueOf(singlePOI.getDouble(2)));
                    singlePoint.add(Double.valueOf(singlePOI.getDouble(3)));
                    this._pointsOfInterest.add(singlePoint);
                }
            } catch (JSONException ex) {
                StaticMethods.logErrorFormat("Messages - Unable to parse remote points of interest JSON (%s)", ex.getMessage());
            }
        }
    }

    private void loadMessagesDataFromRemote(JSONObject jsonData) {
        if (jsonData == null) {
            StaticMethods.logDebugFormat("Messages - Remote messages config was null, falling back to bundled messages", new Object[0]);
            RemoteDownload.deleteFilesInDirectory("messageImages");
            return;
        }
        JSONArray jsonMessages = null;
        try {
            jsonMessages = jsonData.getJSONArray("messages");
        } catch (JSONException e) {
            StaticMethods.logDebugFormat("Messages - Remote messages not configured, falling back to bundled messages", new Object[0]);
        }
        StaticMethods.logDebugFormat("Messages - Using remote definition for messages", new Object[0]);
        if (jsonMessages == null || jsonMessages.length() <= 0) {
            RemoteDownload.deleteFilesInDirectory("messageImages");
            this._inAppMessages = null;
        } else {
            loadMessagesFromJsonArray(jsonMessages);
        }
    }

    private void loadMessagesFromJsonArray(JSONArray messages) {
        try {
            ArrayList<Message> tempInAppMessages = new ArrayList<>();
            ArrayList<Message> tempCallbackTemplates = new ArrayList<>();
            int messageCount = messages.length();
            for (int i = 0; i < messageCount; i++) {
                JSONObject messageJson = messages.getJSONObject(i);
                Message message = Message.messageWithJsonObject(messageJson);
                if (message != null) {
                    StaticMethods.logDebugFormat("Messages - loaded message - %s", message.description());
                    if (message.getClass() == MessageTemplateCallback.class) {
                        tempCallbackTemplates.add(message);
                    } else {
                        tempInAppMessages.add(message);
                    }
                }
            }
            this._inAppMessages = tempInAppMessages;
            this._callbackTemplates = tempCallbackTemplates;
        } catch (JSONException e) {
            StaticMethods.logErrorFormat("Messages - Unable to parse messages JSON (%s)", e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBlacklist() {
        if (this._inAppMessages != null) {
            for (Message message : this._inAppMessages) {
                HashMap<String, Integer> blackList = message.loadBlacklist();
                if (message.isBlacklisted() && message.showRule.getValue() != blackList.get(message.messageId).intValue()) {
                    message.removeFromBlacklist();
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadMessageImages() {
        StaticMethods.getMessageImageCachingExecutor().execute(new Runnable() { // from class: com.adobe.mobile.MobileConfig.7
            @Override // java.lang.Runnable
            public void run() {
                if (MobileConfig.this._inAppMessages == null || MobileConfig.this._inAppMessages.size() <= 0) {
                    RemoteDownload.deleteFilesInDirectory("messageImages");
                    return;
                }
                ArrayList<String> assetUrls = new ArrayList<>();
                for (Message message : MobileConfig.this._inAppMessages) {
                    if (message.assets != null && message.assets.size() > 0) {
                        for (ArrayList<String> currentAssetArray : message.assets) {
                            if (currentAssetArray.size() > 0) {
                                for (String currentAsset : currentAssetArray) {
                                    assetUrls.add(currentAsset);
                                    RemoteDownload.remoteDownloadSync(currentAsset, SearchAuth.StatusCodes.AUTH_DISABLED, SearchAuth.StatusCodes.AUTH_DISABLED, null, "messageImages");
                                }
                            }
                        }
                    }
                }
                if (assetUrls.size() > 0) {
                    RemoteDownload.deleteFilesForDirectoryNotInList("messageImages", assetUrls);
                } else {
                    RemoteDownload.deleteFilesInDirectory("messageImages");
                }
            }
        });
    }

    protected boolean networkConnectivity() {
        return StaticMethods.isWearableApp() || this._networkConnectivity;
    }

    protected void startNotifier() {
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        Context appCtx = null;
        try {
            appCtx = StaticMethods.getSharedContext().getApplicationContext();
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logErrorFormat("Analytics - Error registering network receiver (%s)", e.getMessage());
        }
        if (appCtx != null) {
            appCtx.registerReceiver(new BroadcastReceiver() { // from class: com.adobe.mobile.MobileConfig.8
                @Override // android.content.BroadcastReceiver
                public void onReceive(Context context, Intent intent) {
                    MobileConfig.this._networkConnectivity = MobileConfig.this.getNetworkConnectivity(context);
                    if (MobileConfig.this._networkConnectivity) {
                        StaticMethods.logDebugFormat("Analytics - Network status changed (reachable)", new Object[0]);
                        AnalyticsWorker.sharedInstance().kick(false);
                    } else {
                        StaticMethods.logDebugFormat("Analytics - Network status changed (unreachable)", new Object[0]);
                    }
                }
            }, filter);
        }
    }

    protected boolean getNetworkConnectivity(Context context) {
        boolean tempNetworkConnectivity = true;
        if (context != null) {
            try {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                if (connectivityManager != null) {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null) {
                        tempNetworkConnectivity = activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected();
                    } else {
                        tempNetworkConnectivity = false;
                        StaticMethods.logWarningFormat("Analytics - Unable to determine connectivity status due to there being no default network currently active", new Object[0]);
                    }
                } else {
                    StaticMethods.logWarningFormat("Analytics - Unable to determine connectivity status due to the system service requested being unrecognized", new Object[0]);
                }
            } catch (NullPointerException e) {
                StaticMethods.logWarningFormat("Analytics - Unable to determine connectivity status due to an unexpected error (%s)", e.getLocalizedMessage());
            } catch (SecurityException e2) {
                StaticMethods.logErrorFormat("Analytics - Unable to access connectivity status due to a security error (%s)", e2.getLocalizedMessage());
            } catch (Exception e3) {
                StaticMethods.logWarningFormat("Analytics - Unable to access connectivity status due to an unexpected error (%s)", e3.getLocalizedMessage());
            }
        }
        return tempNetworkConnectivity;
    }
}
