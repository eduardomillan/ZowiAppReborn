package com.adobe.mobile;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import com.adobe.mobile.Config;
import com.bq.zowi.models.commands.CalibrationCommand;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLDecoder;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
final class StaticMethods {
    private static final String ADID_DPID = "20914";
    private static final String AID_JSON_ID_KEY = "id";
    private static final String CARRIER_NAME_KEY = "a.CarrierName";
    private static final String DEVICE_NAME_KEY = "a.DeviceName";
    protected static final String LAST_KNOWN_TIMESTAMP_KEY = "ADBLastKnownTimestampKey";
    protected static final String LIBRARY_VERSION = "4.8.1-AN";
    private static final String MCPNS_DPID = "20919";
    private static final String NO_ACTIVITY_MESSAGE = "Message - No Current Activity (Messages must have a reference to the current activity to work properly)";
    private static final String NO_CONTEXT_MESSAGE = "Config - No Application Context (Application context must be set prior to calling any library functions.)";
    private static final String NO_SHARED_PREFERENCES_EDITOR_MESSAGE = "Config - Unable to create an instance of a SharedPreferences Editor";
    private static final String NO_SHARED_PREFERENCES_MESSAGE = "Config - No SharedPreferences available";
    private static final String PUSH_ID_ENABLED_ACTION_NAME = "Push";
    private static final String RESOLUTION_KEY = "a.Resolution";
    private static final String RUN_MODE_APPLICATION = "Application";
    private static final String RUN_MODE_EXTENSION = "Extension";
    private static final String RUN_MODE_KEY = "a.RunMode";
    protected static final String TIME_SINCE_LAUNCH_KEY = "a.TimeSinceLaunch";
    private static boolean _debugLogging;
    private static final String[] encodedChars = {"%00", "%01", "%02", "%03", "%04", "%05", "%06", "%07", "%08", "%09", "%0A", "%0B", "%0C", "%0D", "%0E", "%0F", "%10", "%11", "%12", "%13", "%14", "%15", "%16", "%17", "%18", "%19", "%1A", "%1B", "%1C", "%1D", "%1E", "%1F", "%20", "%21", "%22", "%23", "%24", "%25", "%26", "%27", "%28", "%29", "*", "%2B", "%2C", "-", ".", "%2F", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "%3A", "%3B", "%3C", "%3D", "%3E", "%3F", "%40", "A", "B", CalibrationCommand.CALIBRATE_TRIM, "D", "E", "F", CalibrationCommand.CALIBRATE_GRADES, "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "%5B", "%5C", "%5D", "%5E", "_", "%60", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "%7B", "%7C", "%7D", "%7E", "%7F", "%80", "%81", "%82", "%83", "%84", "%85", "%86", "%87", "%88", "%89", "%8A", "%8B", "%8C", "%8D", "%8E", "%8F", "%90", "%91", "%92", "%93", "%94", "%95", "%96", "%97", "%98", "%99", "%9A", "%9B", "%9C", "%9D", "%9E", "%9F", "%A0", "%A1", "%A2", "%A3", "%A4", "%A5", "%A6", "%A7", "%A8", "%A9", "%AA", "%AB", "%AC", "%AD", "%AE", "%AF", "%B0", "%B1", "%B2", "%B3", "%B4", "%B5", "%B6", "%B7", "%B8", "%B9", "%BA", "%BB", "%BC", "%BD", "%BE", "%BF", "%C0", "%C1", "%C2", "%C3", "%C4", "%C5", "%C6", "%C7", "%C8", "%C9", "%CA", "%CB", "%CC", "%CD", "%CE", "%CF", "%D0", "%D1", "%D2", "%D3", "%D4", "%D5", "%D6", "%D7", "%D8", "%D9", "%DA", "%DB", "%DC", "%DD", "%DE", "%DF", "%E0", "%E1", "%E2", "%E3", "%E4", "%E5", "%E6", "%E7", "%E8", "%E9", "%EA", "%EB", "%EC", "%ED", "%EE", "%EF", "%F0", "%F1", "%F2", "%F3", "%F4", "%F5", "%F6", "%F7", "%F8", "%F9", "%FA", "%FB", "%FC", "%FD", "%FE", "%FF"};
    private static final boolean[] utf8Mask = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, true, true, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private static final boolean[] contextDataMask = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    private static Config.ApplicationType _appType = Config.ApplicationType.APPLICATION_TYPE_HANDHELD;
    private static boolean _isWearable = false;
    private static SharedPreferences prefs = null;
    private static final Object _sharedPreferencesMutex = new Object();
    private static String appID = null;
    private static final Object _applicationIDMutex = new Object();
    private static String appName = null;
    private static final Object _applicationNameMutex = new Object();
    private static String versionName = null;
    private static final Object _applicationVersionMutex = new Object();
    private static int versionCode = -1;
    private static final Object _applicationVersionCodeMutex = new Object();
    private static String visitorID = null;
    private static final Object _visitorIDMutex = new Object();
    private static final Object _userIdentifierMutex = new Object();
    private static boolean pushEnabled = false;
    private static final Object _pushEnabledMutex = new Object();
    private static String pushIdentifier = null;
    private static final Object _pushIdentifierMutex = new Object();
    private static String advertisingIdentifier = null;
    private static final Object _advertisingIdentifierMutex = new Object();
    private static String userAgent = null;
    private static final Object _userAgentMutex = new Object();
    private static HashMap<String, Object> defaultData = null;
    private static final Object _defaultDataMutex = new Object();
    private static String resolution = null;
    private static final Object _resolutionMutex = new Object();
    private static String carrier = null;
    private static final Object _carrierMutex = new Object();
    private static String operatingSystem = null;
    private static final Object _operatingSystemMutex = new Object();
    private static String timestamp = null;
    private static final Object _timestampMutex = new Object();
    private static ExecutorService mediaExecutor = null;
    private static final Object _mediaExecutorMutex = new Object();
    private static ExecutorService timedActionsExecutor = null;
    private static final Object _timedActionsExecutorMutex = new Object();
    private static ExecutorService sharedExecutor = null;
    private static final Object _sharedExecutorMutex = new Object();
    private static ExecutorService analyticsExecutor = null;
    private static final Object _analyticsExecutorMutex = new Object();
    private static ExecutorService messagesExecutor = null;
    private static final Object _messagesExecutorMutex = new Object();
    private static ExecutorService thirdPartyCallbacksExecutor = null;
    private static final Object _thirdPartyCallbacksExecutorMutex = new Object();
    private static ExecutorService messageImageCachingExecutor = null;
    private static final Object _messageImageCachingExecutorMutex = new Object();
    private static ExecutorService audienceExecutor = null;
    private static final Object _audienceExecutorMutex = new Object();
    private static String aid = null;
    private static boolean _aidDone = false;
    private static final Object _aidMutex = new Object();
    static final Map<String, String> _contextDataKeyWhiteList = new HashMap(256);
    static int _contextDataKeyWhiteListCount = 0;
    private static Context sharedContext = null;
    private static WeakReference<Activity> _activity = null;
    private static final Object _currentActivityMutex = new Object();
    private static final char[] BYTE_TO_HEX = "000102030405060708090A0B0C0D0E0F101112131415161718191A1B1C1D1E1F202122232425262728292A2B2C2D2E2F303132333435363738393A3B3C3D3E3F404142434445464748494A4B4C4D4E4F505152535455565758595A5B5C5D5E5F606162636465666768696A6B6C6D6E6F707172737475767778797A7B7C7D7E7F808182838485868788898A8B8C8D8E8F909192939495969798999A9B9C9D9E9FA0A1A2A3A4A5A6A7A8A9AAABACADAEAFB0B1B2B3B4B5B6B7B8B9BABBBCBDBEBFC0C1C2C3C4C5C6C7C8C9CACBCCCDCECFD0D1D2D3D4D5D6D7D8D9DADBDCDDDEDFE0E1E2E3E4E5E6E7E8E9EAEBECEDEEEFF0F1F2F3F4F5F6F7F8F9FAFBFCFDFEFF".toCharArray();

    StaticMethods() {
    }

    protected static class NullContextException extends Exception {
        public NullContextException(String message) {
            super(message);
        }
    }

    protected static class NullActivityException extends Exception {
        public NullActivityException(String message) {
            super(message);
        }
    }

    protected static SharedPreferences getSharedPreferences() throws NullContextException {
        SharedPreferences sharedPreferences;
        synchronized (_sharedPreferencesMutex) {
            if (prefs == null) {
                prefs = getSharedContext().getSharedPreferences("APP_MEASUREMENT_CACHE", 0);
                if (prefs == null) {
                    logWarningFormat(NO_SHARED_PREFERENCES_MESSAGE, new Object[0]);
                }
            }
            if (prefs == null) {
                throw new NullContextException(NO_SHARED_PREFERENCES_MESSAGE);
            }
            sharedPreferences = prefs;
        }
        return sharedPreferences;
    }

    protected static String getApplicationID() {
        String str;
        synchronized (_applicationIDMutex) {
            if (appID == null) {
                appID = (getApplicationName() != null ? getApplicationName() : "") + ((getApplicationVersion() == null || getApplicationVersion().length() <= 0) ? "" : " " + getApplicationVersion() + " ") + (getApplicationVersionCode() != 0 ? String.format(Locale.US, "(%d)", Integer.valueOf(getApplicationVersionCode())) : "");
                try {
                    SharedPreferences.Editor editor = getSharedPreferencesEditor();
                    editor.putString("ADOBEMOBILE_STOREDDEFAULTS_APPID", appID);
                    editor.commit();
                } catch (NullContextException ex) {
                    logWarningFormat("Config - Unable to set Application ID in preferences (%s)", ex.getLocalizedMessage());
                }
                str = appID;
            } else {
                str = appID;
            }
        }
        return str;
    }

    private static String getApplicationName() {
        String str;
        synchronized (_applicationNameMutex) {
            if (appName == null) {
                appName = "";
                try {
                    PackageManager packageManager = getSharedContext().getPackageManager();
                    if (packageManager != null) {
                        ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getSharedContext().getPackageName(), 0);
                        if (applicationInfo != null) {
                            appName = packageManager.getApplicationLabel(applicationInfo) != null ? (String) packageManager.getApplicationLabel(applicationInfo) : "";
                        } else {
                            logWarningFormat("Analytics - ApplicationInfo was null", new Object[0]);
                            appName = "";
                        }
                    } else {
                        logWarningFormat("Analytics - PackageManager was null", new Object[0]);
                        appName = "";
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    logWarningFormat("Analytics - PackageManager couldn't find application name (%s)", e.toString());
                    appName = "";
                } catch (NullContextException e2) {
                    logErrorFormat("Config - Unable to get package to pull application name. (%s)", e2.getMessage());
                    appName = "";
                }
                str = appName;
            } else {
                str = appName;
            }
        }
        return str;
    }

    protected static String getApplicationVersion() {
        String str;
        synchronized (_applicationVersionMutex) {
            if (versionName == null) {
                versionName = "";
                try {
                    PackageManager packageManager = getSharedContext().getPackageManager();
                    if (packageManager != null) {
                        PackageInfo packageInfo = packageManager.getPackageInfo(getSharedContext().getPackageName(), 0);
                        if (packageInfo != null) {
                            versionName = packageInfo.versionName != null ? packageInfo.versionName : "";
                        } else {
                            logWarningFormat("Analytics - PackageInfo was null", new Object[0]);
                            versionName = "";
                        }
                    } else {
                        logWarningFormat("Analytics - PackageManager was null", new Object[0]);
                        versionName = "";
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    logWarningFormat("Analytics - PackageManager couldn't find application version (%s)", e.toString());
                    versionName = "";
                } catch (NullContextException e2) {
                    logErrorFormat("Config - Unable to get package to pull application version. (%s)", e2.getMessage());
                    versionName = "";
                }
                str = versionName;
            } else {
                str = versionName;
            }
        }
        return str;
    }

    protected static int getApplicationVersionCode() {
        int i;
        synchronized (_applicationVersionCodeMutex) {
            if (versionCode == -1) {
                try {
                    PackageManager packageManager = getSharedContext().getPackageManager();
                    if (packageManager != null) {
                        PackageInfo packageInfo = packageManager.getPackageInfo(getSharedContext().getPackageName(), 0);
                        if (packageInfo != null) {
                            versionCode = packageInfo.versionCode > 0 ? packageInfo.versionCode : 0;
                        } else {
                            logWarningFormat("Analytics - PackageInfo was null", new Object[0]);
                            versionCode = 0;
                        }
                    } else {
                        logWarningFormat("Analytics - PackageManager was null", new Object[0]);
                        versionCode = 0;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    logWarningFormat("Analytics - PackageManager couldn't find application version code (%s)", e.toString());
                    versionCode = 0;
                } catch (NullContextException e2) {
                    logErrorFormat("Config - Unable to get package to pull application version code. (%s)", e2.getMessage());
                    versionCode = 0;
                }
                i = versionCode;
            } else {
                i = versionCode;
            }
        }
        return i;
    }

    protected static String getVisitorID() {
        String str;
        synchronized (_visitorIDMutex) {
            if (visitorID == null) {
                try {
                    visitorID = getSharedPreferences().getString("APP_MEASUREMENT_VISITOR_ID", null);
                } catch (NullContextException e) {
                    logErrorFormat("Config - Unable to pull visitorID from shared preferences. (%s)", e.getMessage());
                }
                str = visitorID;
            } else {
                str = visitorID;
            }
        }
        return str;
    }

    protected static void setVisitorID(String uid) {
        synchronized (_userIdentifierMutex) {
            visitorID = uid;
            WearableFunctionBridge.syncVisitorIDToWearable(uid);
            try {
                SharedPreferences.Editor editor = getSharedPreferencesEditor();
                editor.putString("APP_MEASUREMENT_VISITOR_ID", uid);
                editor.commit();
            } catch (NullContextException e) {
                logErrorFormat("Config - Error updating visitorID. (%s)", e.getMessage());
            }
        }
    }

    protected static boolean isPushEnabled() {
        synchronized (_pushEnabledMutex) {
            try {
                pushEnabled = getSharedPreferences().getBoolean("ADBMOBILE_KEY_PUSH_ENABLED", false);
            } catch (NullContextException e) {
                logErrorFormat("Config - Unable to pull push status from shared preferences. (%s)", e.getMessage());
            }
        }
        return pushEnabled;
    }

    protected static void setPushEnabled(boolean enabled) {
        synchronized (_pushEnabledMutex) {
            try {
                SharedPreferences.Editor editor = getSharedPreferencesEditor();
                editor.putBoolean("ADBMOBILE_KEY_PUSH_ENABLED", enabled);
                editor.commit();
                pushEnabled = enabled;
                WearableFunctionBridge.syncPushEnabledToWearable(enabled);
            } catch (NullContextException e) {
                logErrorFormat("Config - Unable to set pushEnabled shared preferences. (%s)", e.getMessage());
            }
        }
    }

    protected static String getPushIdentifier() {
        String str;
        synchronized (_pushIdentifierMutex) {
            str = pushIdentifier;
        }
        return str;
    }

    protected static void setPushIdentifier(final String registrationID) {
        synchronized (_pushIdentifierMutex) {
            if (registrationID == null) {
                if (isPushEnabled()) {
                    setPushEnabled(false);
                    AnalyticsTrackInternal.trackInternal(PUSH_ID_ENABLED_ACTION_NAME, new HashMap<String, Object>() { // from class: com.adobe.mobile.StaticMethods.1
                        {
                            put("a.push.optin", "False");
                        }
                    }, getTimeSince1970());
                }
                if (registrationID != null && registrationID.length() >= 0 && !isPushEnabled()) {
                    setPushEnabled(true);
                    AnalyticsTrackInternal.trackInternal(PUSH_ID_ENABLED_ACTION_NAME, new HashMap<String, Object>() { // from class: com.adobe.mobile.StaticMethods.2
                        {
                            put("a.push.optin", "True");
                        }
                    }, getTimeSince1970());
                }
                pushIdentifier = registrationID;
                VisitorIDService.sharedInstance().idSync((Map<String, String>) null, new HashMap<String, String>() { // from class: com.adobe.mobile.StaticMethods.3
                    {
                        put(StaticMethods.MCPNS_DPID, registrationID);
                    }
                });
            } else {
                if (registrationID != null) {
                    setPushEnabled(true);
                    AnalyticsTrackInternal.trackInternal(PUSH_ID_ENABLED_ACTION_NAME, new HashMap<String, Object>() { // from class: com.adobe.mobile.StaticMethods.2
                        {
                            put("a.push.optin", "True");
                        }
                    }, getTimeSince1970());
                }
                pushIdentifier = registrationID;
                VisitorIDService.sharedInstance().idSync((Map<String, String>) null, new HashMap<String, String>() { // from class: com.adobe.mobile.StaticMethods.3
                    {
                        put(StaticMethods.MCPNS_DPID, registrationID);
                    }
                });
            }
        }
    }

    protected static String getAdvertisingIdentifier() {
        String str;
        synchronized (_advertisingIdentifierMutex) {
            str = advertisingIdentifier;
        }
        return str;
    }

    protected static void setAdvertisingIdentifier(final String adid) {
        if (adid == null || !adid.equals(getAdvertisingIdentifier())) {
            synchronized (_advertisingIdentifierMutex) {
                advertisingIdentifier = adid;
                WearableFunctionBridge.syncAdvertisingIdentifierToWearable(adid);
                VisitorIDService.sharedInstance().idSync((Map<String, String>) null, new HashMap<String, String>() { // from class: com.adobe.mobile.StaticMethods.4
                    {
                        put(StaticMethods.ADID_DPID, adid);
                    }
                });
            }
        }
    }

    protected static String getDefaultUserAgent() {
        String str;
        synchronized (_userAgentMutex) {
            if (userAgent == null) {
                userAgent = "Mozilla/5.0 (Linux; U; Android " + getAndroidVersion() + "; " + getDefaultAcceptLanguage() + "; " + Build.MODEL + " Build/" + Build.ID + ")";
            }
            str = userAgent;
        }
        return str;
    }

    protected static HashMap<String, Object> getDefaultData() {
        HashMap<String, Object> map;
        synchronized (_defaultDataMutex) {
            if (defaultData == null) {
                defaultData = new HashMap<>();
                defaultData.put(DEVICE_NAME_KEY, Build.MODEL);
                defaultData.put(RESOLUTION_KEY, getResolution());
                defaultData.put("a.OSVersion", getOperatingSystem());
                defaultData.put(CARRIER_NAME_KEY, getCarrier());
                defaultData.put("a.AppID", getApplicationID());
                defaultData.put(RUN_MODE_KEY, isWearableApp() ? RUN_MODE_EXTENSION : RUN_MODE_APPLICATION);
            }
            map = defaultData;
        }
        return map;
    }

    protected static String getResolution() {
        String str;
        synchronized (_resolutionMutex) {
            if (resolution == null) {
                try {
                    DisplayMetrics displayMetrics = getSharedContext().getResources().getDisplayMetrics();
                    resolution = displayMetrics.widthPixels + "x" + displayMetrics.heightPixels;
                } catch (NullContextException e) {
                    logErrorFormat("Config - Error getting device resolution. (%s)", e.getMessage());
                }
                str = resolution;
            } else {
                str = resolution;
            }
        }
        return str;
    }

    protected static String getCarrier() {
        String str;
        synchronized (_carrierMutex) {
            if (carrier == null) {
                try {
                    TelephonyManager telephonyManager = (TelephonyManager) getSharedContext().getSystemService("phone");
                    carrier = telephonyManager.getNetworkOperatorName();
                } catch (NullContextException e) {
                    logErrorFormat("Config - Error getting device carrier. (%s)", e.getMessage());
                }
                str = carrier;
            } else {
                str = carrier;
            }
        }
        return str;
    }

    protected static String getOperatingSystem() {
        String str;
        synchronized (_operatingSystemMutex) {
            if (operatingSystem == null) {
                operatingSystem = "Android " + getAndroidVersion();
                try {
                    SharedPreferences.Editor editor = getSharedPreferencesEditor();
                    editor.putString("ADOBEMOBILE_STOREDDEFAULTS_OS", operatingSystem);
                    editor.commit();
                } catch (NullContextException ex) {
                    logWarningFormat("Config - Unable to set OS version in preferences (%s)", ex.getLocalizedMessage());
                }
                str = operatingSystem;
            } else {
                str = operatingSystem;
            }
        }
        return str;
    }

    protected static String getTimestampString() {
        String str;
        synchronized (_timestampMutex) {
            if (timestamp == null) {
                Date date = new Date();
                Calendar tm = Calendar.getInstance();
                tm.setTime(date);
                timestamp = "00/00/0000 00:00:00 0 " + ((tm.getTimeZone().getOffset(1, tm.get(1), tm.get(2), tm.get(5), tm.get(7), (((((tm.get(11) * 60) + tm.get(12)) * 60) + tm.get(13)) * 1000) + tm.get(14)) / com.comscore.utils.Constants.MINIMAL_AUTOUPDATE_INTERVAL) * (-1));
            }
            str = timestamp;
        }
        return str;
    }

    protected static File getCacheDirectory() {
        try {
            return getSharedContext().getCacheDir();
        } catch (NullContextException e) {
            logErrorFormat("Config - Error getting cache directory. (%s)", e.getMessage());
            return null;
        }
    }

    protected static ExecutorService getMediaExecutor() {
        ExecutorService executorService;
        synchronized (_mediaExecutorMutex) {
            if (mediaExecutor == null) {
                mediaExecutor = Executors.newSingleThreadExecutor();
            }
            executorService = mediaExecutor;
        }
        return executorService;
    }

    public static ExecutorService getTimedActionsExecutor() {
        ExecutorService executorService;
        synchronized (_timedActionsExecutorMutex) {
            if (timedActionsExecutor == null) {
                timedActionsExecutor = Executors.newSingleThreadExecutor();
            }
            executorService = timedActionsExecutor;
        }
        return executorService;
    }

    protected static ExecutorService getSharedExecutor() {
        ExecutorService executorService;
        synchronized (_sharedExecutorMutex) {
            if (sharedExecutor == null) {
                sharedExecutor = Executors.newSingleThreadExecutor();
            }
            executorService = sharedExecutor;
        }
        return executorService;
    }

    protected static ExecutorService getAnalyticsExecutor() {
        ExecutorService executorService;
        synchronized (_analyticsExecutorMutex) {
            if (analyticsExecutor == null) {
                analyticsExecutor = Executors.newSingleThreadExecutor();
            }
            executorService = analyticsExecutor;
        }
        return executorService;
    }

    protected static ExecutorService getMessagesExecutor() {
        ExecutorService executorService;
        synchronized (_messagesExecutorMutex) {
            if (messagesExecutor == null) {
                messagesExecutor = Executors.newSingleThreadExecutor();
            }
            executorService = messagesExecutor;
        }
        return executorService;
    }

    protected static ExecutorService getThirdPartyCallbacksExecutor() {
        ExecutorService executorService;
        synchronized (_thirdPartyCallbacksExecutorMutex) {
            if (thirdPartyCallbacksExecutor == null) {
                thirdPartyCallbacksExecutor = Executors.newSingleThreadExecutor();
            }
            executorService = thirdPartyCallbacksExecutor;
        }
        return executorService;
    }

    protected static ExecutorService getMessageImageCachingExecutor() {
        ExecutorService executorService;
        synchronized (_messageImageCachingExecutorMutex) {
            if (messageImageCachingExecutor == null) {
                messageImageCachingExecutor = Executors.newSingleThreadExecutor();
            }
            executorService = messageImageCachingExecutor;
        }
        return executorService;
    }

    protected static ExecutorService getAudienceExecutor() {
        ExecutorService executorService;
        synchronized (_audienceExecutorMutex) {
            if (audienceExecutor == null) {
                audienceExecutor = Executors.newSingleThreadExecutor();
            }
            executorService = audienceExecutor;
        }
        return executorService;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0057 A[Catch: all -> 0x0073, DONT_GENERATE, TRY_ENTER, TRY_LEAVE, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0007, B:7:0x000a, B:9:0x0024, B:14:0x0034, B:16:0x0042, B:17:0x004f, B:21:0x005b, B:11:0x0028, B:24:0x0063, B:18:0x0057), top: B:28:0x0003, inners: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    protected static java.lang.String getAID() {
        /*
            java.lang.Object r4 = com.adobe.mobile.StaticMethods._aidMutex
            monitor-enter(r4)
            boolean r3 = com.adobe.mobile.StaticMethods._aidDone     // Catch: java.lang.Throwable -> L73
            if (r3 != 0) goto L57
            r3 = 1
            com.adobe.mobile.StaticMethods._aidDone = r3     // Catch: java.lang.Throwable -> L73
            android.content.SharedPreferences r3 = getSharedPreferences()     // Catch: com.adobe.mobile.StaticMethods.NullContextException -> L62 java.lang.Throwable -> L73
            java.lang.String r5 = "ADOBEMOBILE_STOREDDEFAULTS_IGNORE_AID"
            r6 = 0
            boolean r2 = r3.getBoolean(r5, r6)     // Catch: com.adobe.mobile.StaticMethods.NullContextException -> L62 java.lang.Throwable -> L73
            android.content.SharedPreferences r3 = getSharedPreferences()     // Catch: com.adobe.mobile.StaticMethods.NullContextException -> L62 java.lang.Throwable -> L73
            java.lang.String r5 = "ADOBEMOBILE_STOREDDEFAULTS_AID"
            r6 = 0
            java.lang.String r3 = r3.getString(r5, r6)     // Catch: com.adobe.mobile.StaticMethods.NullContextException -> L62 java.lang.Throwable -> L73
            com.adobe.mobile.StaticMethods.aid = r3     // Catch: com.adobe.mobile.StaticMethods.NullContextException -> L62 java.lang.Throwable -> L73
            if (r2 != 0) goto L28
            java.lang.String r3 = com.adobe.mobile.StaticMethods.aid     // Catch: com.adobe.mobile.StaticMethods.NullContextException -> L62 java.lang.Throwable -> L73
            if (r3 == 0) goto L34
        L28:
            com.adobe.mobile.MobileConfig r3 = com.adobe.mobile.MobileConfig.getInstance()     // Catch: com.adobe.mobile.StaticMethods.NullContextException -> L62 java.lang.Throwable -> L73
            boolean r3 = r3.getVisitorIdServiceEnabled()     // Catch: com.adobe.mobile.StaticMethods.NullContextException -> L62 java.lang.Throwable -> L73
            if (r3 != 0) goto L57
            if (r2 == 0) goto L57
        L34:
            java.lang.String r3 = retrieveAIDFromVisitorIDService()     // Catch: com.adobe.mobile.StaticMethods.NullContextException -> L62 java.lang.Throwable -> L73
            com.adobe.mobile.StaticMethods.aid = r3     // Catch: com.adobe.mobile.StaticMethods.NullContextException -> L62 java.lang.Throwable -> L73
            android.content.SharedPreferences$Editor r1 = getSharedPreferencesEditor()     // Catch: com.adobe.mobile.StaticMethods.NullContextException -> L62 java.lang.Throwable -> L73
            java.lang.String r3 = com.adobe.mobile.StaticMethods.aid     // Catch: com.adobe.mobile.StaticMethods.NullContextException -> L62 java.lang.Throwable -> L73
            if (r3 == 0) goto L5b
            java.lang.String r3 = "ADOBEMOBILE_STOREDDEFAULTS_AID"
            java.lang.String r5 = com.adobe.mobile.StaticMethods.aid     // Catch: com.adobe.mobile.StaticMethods.NullContextException -> L62 java.lang.Throwable -> L73
            r1.putString(r3, r5)     // Catch: com.adobe.mobile.StaticMethods.NullContextException -> L62 java.lang.Throwable -> L73
            java.lang.String r3 = "ADOBEMOBILE_STOREDDEFAULTS_IGNORE_AID"
            r5 = 0
            r1.putBoolean(r3, r5)     // Catch: com.adobe.mobile.StaticMethods.NullContextException -> L62 java.lang.Throwable -> L73
        L4f:
            r1.commit()     // Catch: com.adobe.mobile.StaticMethods.NullContextException -> L62 java.lang.Throwable -> L73
            java.lang.String r3 = com.adobe.mobile.StaticMethods.aid     // Catch: com.adobe.mobile.StaticMethods.NullContextException -> L62 java.lang.Throwable -> L73
            syncAIDIfNeeded(r3)     // Catch: com.adobe.mobile.StaticMethods.NullContextException -> L62 java.lang.Throwable -> L73
        L57:
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L73
            java.lang.String r3 = com.adobe.mobile.StaticMethods.aid
            return r3
        L5b:
            java.lang.String r3 = "ADOBEMOBILE_STOREDDEFAULTS_IGNORE_AID"
            r5 = 1
            r1.putBoolean(r3, r5)     // Catch: com.adobe.mobile.StaticMethods.NullContextException -> L62 java.lang.Throwable -> L73
            goto L4f
        L62:
            r0 = move-exception
            java.lang.String r3 = "Config - Error getting AID. (%s)"
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch: java.lang.Throwable -> L73
            r6 = 0
            java.lang.String r7 = r0.getMessage()     // Catch: java.lang.Throwable -> L73
            r5[r6] = r7     // Catch: java.lang.Throwable -> L73
            logErrorFormat(r3, r5)     // Catch: java.lang.Throwable -> L73
            goto L57
        L73:
            r3 = move-exception
            monitor-exit(r4)     // Catch: java.lang.Throwable -> L73
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.adobe.mobile.StaticMethods.getAID():java.lang.String");
    }

    private static void syncAIDIfNeeded(String aid2) {
        if (aid2 != null && MobileConfig.getInstance().getVisitorIdServiceEnabled()) {
            boolean idSynced = false;
            try {
                idSynced = getSharedPreferences().getBoolean("ADOBEMOBILE_STOREDDEFAULTS_AID_SYNCED", false);
            } catch (NullContextException ex) {
                logWarningFormat("Visitor ID - Null context when attempting to determine visitor ID sync status (%s)", ex.getLocalizedMessage());
            }
            if (!idSynced) {
                HashMap<String, String> integrationsMap = new HashMap<>();
                integrationsMap.put("AVID", aid2);
                VisitorIDService.sharedInstance().idSync(integrationsMap);
                try {
                    SharedPreferences.Editor e = getSharedPreferencesEditor();
                    e.putBoolean("ADOBEMOBILE_STOREDDEFAULTS_AID_SYNCED", true);
                    e.commit();
                } catch (NullContextException ex2) {
                    logWarningFormat("Visitor ID - Null context when attempting to persist visitor ID sync status (%s)", ex2.getLocalizedMessage());
                }
            }
        }
    }

    private static String retrieveAIDFromVisitorIDService() {
        StringBuilder urlSb = new StringBuilder(64);
        urlSb.append(MobileConfig.getInstance().getSSL() ? "https" : "http");
        urlSb.append("://");
        urlSb.append(MobileConfig.getInstance().getTrackingServer());
        urlSb.append("/id");
        boolean useVisidService = MobileConfig.getInstance().getVisitorIdServiceEnabled();
        if (useVisidService) {
            urlSb.append(VisitorIDService.sharedInstance().getAnalyticsIDRequestParameterString());
        }
        byte[] serverResponse = RequestHandler.retrieveData(urlSb.toString(), null, 500, "Analytics ID");
        String identifier = null;
        if (serverResponse != null) {
            try {
                JSONObject jsonResponse = new JSONObject(new String(serverResponse, "UTF-8"));
                identifier = jsonResponse.getString(AID_JSON_ID_KEY);
            } catch (UnsupportedEncodingException ex) {
                logErrorFormat("Analytics ID - Unable to decode /id response(%s)", ex.getLocalizedMessage());
            } catch (JSONException ex2) {
                if (!useVisidService) {
                    logErrorFormat("Analytics ID - Unable to parse /id response(%s)", ex2.getLocalizedMessage());
                }
            }
        }
        if (identifier == null || identifier.length() != 33) {
            if (useVisidService) {
                return null;
            }
            identifier = generateAID();
        }
        return identifier;
    }

    private static String generateAID() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase(Locale.US);
        Pattern firstPattern = Pattern.compile("^[89A-F]");
        Pattern secondPattern = Pattern.compile("^[4-9A-F]");
        Matcher firstMatcher = firstPattern.matcher(uuid.substring(0, 16));
        Matcher secondMatcher = secondPattern.matcher(uuid.substring(16, 32));
        SecureRandom r = new SecureRandom();
        String vi_hi = firstMatcher.replaceAll(String.valueOf(r.nextInt(7)));
        String vi_lo = secondMatcher.replaceAll(String.valueOf(r.nextInt(3)));
        return vi_hi + "-" + vi_lo;
    }

    protected static void setDebugLogging(boolean enabled) {
        _debugLogging = enabled;
    }

    protected static boolean getDebugLogging() {
        return _debugLogging;
    }

    protected static void setApplicationType(Config.ApplicationType apptype) {
        _appType = apptype;
        _isWearable = _appType == Config.ApplicationType.APPLICATION_TYPE_WEARABLE;
    }

    protected static Config.ApplicationType getApplicationType() {
        return _appType;
    }

    protected static boolean isWearableApp() {
        return _isWearable;
    }

    protected static void serializeToQueryString(Map<String, Object> parameters, StringBuilder builder) {
        if (parameters != null) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                String key = URLEncode(entry.getKey());
                if (key != null) {
                    Object obj = entry.getValue();
                    if (obj instanceof ContextData) {
                        ContextData data = (ContextData) obj;
                        if (data.value != null) {
                            serializeKeyValuePair(key, data.value, builder);
                        }
                        if (data.contextData != null && data.contextData.size() > 0) {
                            builder.append("&");
                            builder.append(key);
                            builder.append(".");
                            serializeToQueryString(data.contextData, builder);
                            builder.append("&.");
                            builder.append(key);
                        }
                    } else {
                        serializeKeyValuePair(key, obj, builder);
                    }
                }
            }
        }
    }

    private static void serializeKeyValuePair(String key, Object value, StringBuilder builder) {
        if (key != null && value != null && !(value instanceof ContextData) && key.length() > 0) {
            if (!(value instanceof String) || ((String) value).length() > 0) {
                builder.append("&");
                builder.append(key);
                builder.append("=");
                if (value instanceof List) {
                    builder.append(URLEncode(join((List) value, ",")));
                } else {
                    builder.append(URLEncode(value.toString()));
                }
            }
        }
    }

    protected static String join(Iterable<?> elements, String delimiter) {
        StringBuilder sb = new StringBuilder();
        Iterator<?> it = elements.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            if (!it.hasNext()) {
                break;
            }
            sb.append(delimiter);
        }
        return sb.toString();
    }

    protected static String URLEncode(String unencodedString) {
        if (unencodedString == null) {
            return null;
        }
        try {
            byte[] stringBytes = unencodedString.getBytes("UTF-8");
            int len = stringBytes.length;
            int curIndex = 0;
            while (curIndex < len && utf8Mask[stringBytes[curIndex] & 255]) {
                curIndex++;
            }
            if (curIndex != len) {
                StringBuilder encodedString = new StringBuilder(stringBytes.length << 1);
                if (curIndex > 0) {
                    encodedString.append(new String(stringBytes, 0, curIndex, "UTF-8"));
                }
                while (curIndex < len) {
                    encodedString.append(encodedChars[stringBytes[curIndex] & 255]);
                    curIndex++;
                }
                return encodedString.toString();
            }
            return unencodedString;
        } catch (UnsupportedEncodingException e) {
            logWarningFormat("UnsupportedEncodingException : " + e.getMessage(), new Object[0]);
            return null;
        }
    }

    protected static void logErrorFormat(String format, Object... args) {
        if (args != null && args.length > 0) {
            String formattedString = String.format("ADBMobile Error : " + format, args);
            Log.e("ADBMobile", formattedString);
        } else {
            Log.e("ADBMobile", "ADBMobile Error : " + format);
        }
    }

    protected static void logWarningFormat(String format, Object... args) {
        if (getDebugLogging()) {
            if (args != null && args.length > 0) {
                String formattedString = String.format("ADBMobile Warning : " + format, args);
                Log.w("ADBMobile", formattedString);
            } else {
                Log.w("ADBMobile", "ADBMobile Warning : " + format);
            }
        }
    }

    protected static void logDebugFormat(String format, Object... args) {
        if (getDebugLogging()) {
            if (args != null && args.length > 0) {
                String formattedString = String.format("ADBMobile Debug : " + format, args);
                Log.d("ADBMobile", formattedString);
            } else {
                Log.d("ADBMobile", "ADBMobile Debug : " + format);
            }
        }
    }

    protected static ContextData translateContextData(Map<String, Object> dict) {
        ContextData tempContextData = new ContextData();
        for (Map.Entry<String, Object> entry : cleanContextDataDictionary(dict).entrySet()) {
            String key = entry.getKey();
            List<String> list = new ArrayList<>();
            int pos = 0;
            while (true) {
                int end = key.indexOf(46, pos);
                if (end >= 0) {
                    list.add(key.substring(pos, end));
                    pos = end + 1;
                }
            }
            list.add(key.substring(pos, key.length()));
            addValueToHashMap(entry.getValue(), tempContextData, list, 0);
        }
        return tempContextData;
    }

    protected static Map<String, Object> cleanContextDataDictionary(Map<String, Object> dict) {
        if (dict == null) {
            return new HashMap();
        }
        HashMap<String, Object> tempContextData = new HashMap<>();
        for (Map.Entry<String, Object> entry : dict.entrySet()) {
            String cleanedKey = cleanContextDataKey(entry.getKey());
            if (cleanedKey != null) {
                tempContextData.put(cleanedKey, entry.getValue());
            }
        }
        return tempContextData;
    }

    protected static String cleanContextDataKey(String key) {
        String preCleanedKey;
        int outIndex;
        if (key == null) {
            return null;
        }
        synchronized (_contextDataKeyWhiteList) {
            preCleanedKey = _contextDataKeyWhiteList.get(key);
            if (preCleanedKey == null) {
                try {
                    byte[] utf8Key = key.getBytes("UTF-8");
                    byte[] outPut = new byte[utf8Key.length];
                    byte lastByte = 0;
                    int length = utf8Key.length;
                    int i = 0;
                    int outIndex2 = 0;
                    while (i < length) {
                        byte curByte = utf8Key[i];
                        if (curByte == 46 && lastByte == 46) {
                            outIndex = outIndex2;
                        } else if (contextDataMask[curByte & 255]) {
                            outIndex = outIndex2 + 1;
                            outPut[outIndex2] = curByte;
                            lastByte = curByte;
                        } else {
                            outIndex = outIndex2;
                        }
                        i++;
                        outIndex2 = outIndex;
                    }
                    if (outIndex2 == 0) {
                        preCleanedKey = null;
                    } else {
                        int startIndex = outPut[0] == 46 ? 1 : 0;
                        int endTrim = outPut[outIndex2 + (-1)] == 46 ? 1 : 0;
                        int totalLength = (outIndex2 - endTrim) - startIndex;
                        if (totalLength <= 0) {
                            preCleanedKey = null;
                        } else {
                            String cleanKey = new String(outPut, startIndex, totalLength, "UTF-8");
                            synchronized (_contextDataKeyWhiteList) {
                                if (_contextDataKeyWhiteListCount > 250) {
                                    _contextDataKeyWhiteList.clear();
                                    _contextDataKeyWhiteListCount = 0;
                                }
                                _contextDataKeyWhiteList.put(key, cleanKey);
                                _contextDataKeyWhiteListCount++;
                            }
                            preCleanedKey = cleanKey;
                        }
                    }
                } catch (UnsupportedEncodingException ex) {
                    logErrorFormat("Analytics - Unable to clean context data key (%s)", ex.getLocalizedMessage());
                    preCleanedKey = null;
                }
            }
        }
        return preCleanedKey;
    }

    private static void addValueToHashMap(Object object, ContextData table, List<String> subKeyArray, int index) {
        if (table != null && subKeyArray != null) {
            int arrayCount = subKeyArray.size();
            String keyName = index < arrayCount ? subKeyArray.get(index) : null;
            if (keyName != null) {
                ContextData data = new ContextData();
                if (table.containsKey(keyName)) {
                    data = table.get(keyName);
                }
                if (arrayCount - 1 == index) {
                    data.value = object;
                    table.put(keyName, data);
                } else {
                    table.put(keyName, data);
                    addValueToHashMap(object, data, subKeyArray, index + 1);
                }
            }
        }
    }

    protected static long getTimeSince1970() {
        return System.currentTimeMillis() / 1000;
    }

    protected static void updateLastKnownTimestamp(Long timestamp2) {
        MobileConfig config = MobileConfig.getInstance();
        if (config == null) {
            logErrorFormat("Config - Lost config instance", new Object[0]);
            return;
        }
        if (config.getOfflineTrackingEnabled()) {
            try {
                SharedPreferences.Editor editor = getSharedPreferencesEditor();
                editor.putLong(LAST_KNOWN_TIMESTAMP_KEY, timestamp2.longValue());
                editor.commit();
            } catch (NullContextException e) {
                logErrorFormat("Config - Error while updating last known timestamp. (%s)", e.getMessage());
            }
        }
    }

    protected static String getDefaultAcceptLanguage() {
        Configuration configuration;
        Locale locale;
        try {
            Resources resources = getSharedContext().getResources();
            if (resources == null || (configuration = resources.getConfiguration()) == null || (locale = configuration.locale) == null) {
                return null;
            }
            return locale.toString().replace('_', '-');
        } catch (NullContextException e) {
            logErrorFormat("Config - Error getting application resources for default accepted language. (%s)", e.getMessage());
            return null;
        }
    }

    private static String getAndroidVersion() {
        return Build.VERSION.RELEASE;
    }

    protected static SharedPreferences.Editor getSharedPreferencesEditor() throws NullContextException {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        if (editor == null) {
            throw new NullContextException(NO_SHARED_PREFERENCES_EDITOR_MESSAGE);
        }
        return editor;
    }

    protected static Context getSharedContext() throws NullContextException {
        if (sharedContext == null) {
            throw new NullContextException(NO_CONTEXT_MESSAGE);
        }
        return sharedContext;
    }

    protected static void setSharedContext(Context context) {
        if (context != null) {
            if (context instanceof Activity) {
                sharedContext = context.getApplicationContext();
            } else {
                sharedContext = context;
            }
        }
    }

    protected static long getTimeSinceLaunch() {
        long originalStartDate = Lifecycle.sessionStartTime;
        long timeSinceLaunch = getTimeSince1970() - originalStartDate;
        if (timeSinceLaunch < 604800) {
            return timeSinceLaunch;
        }
        return 0L;
    }

    protected static HashMap<String, Object> mapFromJson(JSONObject jsonData) {
        if (jsonData == null) {
            return null;
        }
        Iterator<String> keyItr = jsonData.keys();
        HashMap<String, Object> map = new HashMap<>();
        while (keyItr.hasNext()) {
            String name = keyItr.next();
            try {
                map.put(name, jsonData.getString(name));
            } catch (JSONException ex) {
                logWarningFormat("Problem parsing json data (%s)", ex.getLocalizedMessage());
            }
        }
        return map;
    }

    protected static void setCurrentActivity(Activity activity) {
        synchronized (_currentActivityMutex) {
            _activity = new WeakReference<>(activity);
        }
    }

    protected static Activity getCurrentActivity() throws NullActivityException {
        Activity activity;
        synchronized (_currentActivityMutex) {
            if (_activity == null || _activity.get() == null) {
                throw new NullActivityException(NO_ACTIVITY_MESSAGE);
            }
            activity = _activity.get();
        }
        return activity;
    }

    protected static String expandTokens(String inputString, Map<String, String> tokens) {
        String returnString = inputString;
        try {
            for (Map.Entry<String, String> entry : tokens.entrySet()) {
                returnString = returnString.replace(entry.getKey(), entry.getValue());
            }
        } catch (Exception ex) {
            logDebugFormat("Unable to expand tokens (%s)", ex.toString());
        }
        return returnString;
    }

    protected static int getCurrentOrientation() {
        try {
            return getCurrentActivity().getResources().getConfiguration().orientation;
        } catch (NullActivityException e) {
            return -1;
        }
    }

    protected static String getHexString(String originalString) {
        if (originalString == null || originalString.length() <= 0) {
            return null;
        }
        try {
            byte[] bytes = originalString.getBytes("UTF-8");
            int bytesLength = bytes.length;
            char[] chars = new char[bytesLength << 1];
            int index = 0;
            for (byte b : bytes) {
                int hexIndex = (b & 255) << 1;
                int index2 = index + 1;
                chars[index] = BYTE_TO_HEX[hexIndex];
                index = index2 + 1;
                chars[index2] = BYTE_TO_HEX[hexIndex + 1];
            }
            return new String(chars);
        } catch (UnsupportedEncodingException ex) {
            logDebugFormat("Failed to get hex from string (%s)", ex.getMessage());
            return null;
        }
    }

    protected static String hexToString(String hexString) {
        if (hexString == null || hexString.length() <= 0 || hexString.length() % 2 != 0) {
            return null;
        }
        int length = hexString.length();
        byte[] data = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        try {
            String decodedString = new String(data, "UTF-8");
            return decodedString;
        } catch (UnsupportedEncodingException ex) {
            logDebugFormat("Failed to get string from hex (%s)", ex.getMessage());
            return null;
        }
    }

    protected static String appendContextData(Map<String, Object> referrerData, String source) {
        String contextDataString;
        if (source != null && referrerData != null && referrerData.size() != 0) {
            Pattern pattern = Pattern.compile(".*(&c\\.(.*)&\\.c).*");
            Matcher matcher = pattern.matcher(source);
            if (matcher.matches() && (contextDataString = matcher.group(2)) != null) {
                Map<String, Object> contextData = new HashMap<>(64);
                List<String> keyPath = new ArrayList<>(16);
                for (String param : contextDataString.split("&")) {
                    if (param.endsWith(".") && !param.contains("=")) {
                        keyPath.add(param);
                    } else if (param.startsWith(".")) {
                        if (keyPath.size() > 0) {
                            keyPath.remove(keyPath.size() - 1);
                        }
                    } else {
                        String[] kvpair = param.split("=");
                        if (kvpair.length == 2) {
                            String contextDataKey = contextDataStringPath(keyPath, kvpair[0]);
                            try {
                                contextData.put(contextDataKey, URLDecoder.decode(kvpair[1], "UTF-8"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                contextData.putAll(referrerData);
                StringBuilder urlSb = new StringBuilder(source.substring(0, matcher.start(1)));
                HashMap<String, Object> contextMap = new HashMap<>();
                contextMap.put("c", translateContextData(contextData));
                serializeToQueryString(contextMap, urlSb);
                urlSb.append(source.substring(matcher.end(1)));
                return urlSb.toString();
            }
            return source;
        }
        return source;
    }

    protected static String contextDataStringPath(List<String> keyPath, String lastComponent) {
        StringBuilder sb = new StringBuilder();
        for (String pathComponent : keyPath) {
            sb.append(pathComponent);
        }
        sb.append(lastComponent);
        return sb.toString();
    }

    private static Locale getDeviceLocale() {
        try {
            Resources resources = getSharedContext().getResources();
            if (resources == null) {
                return Locale.US;
            }
            Configuration configuration = resources.getConfiguration();
            if (configuration == null) {
                return Locale.US;
            }
            return configuration.locale != null ? configuration.locale : Locale.US;
        } catch (NullContextException e) {
            logErrorFormat("Config - Error getting application resources for device locale. (%s)", e.getMessage());
            return Locale.US;
        }
    }

    protected static String getIso8601Date() {
        return getIso8601Date(new Date());
    }

    protected static String getIso8601Date(Date date) {
        DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZ", getDeviceLocale());
        if (date == null) {
            date = new Date();
        }
        return iso8601Format.format(date);
    }
}
