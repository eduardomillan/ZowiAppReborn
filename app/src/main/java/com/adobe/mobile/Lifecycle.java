package com.adobe.mobile;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import com.adobe.mobile.StaticMethods;
import com.comscore.measurement.MeasurementDispatcher;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
final class Lifecycle {
    protected static final String ADB_LIFECYCLE_PUSH_MESSAGE_ID_KEY = "adb_m_id";
    protected static final String ADB_TRACK_INTERNAL_PUSH_CLICK_THROUGH = "PushMessage";
    protected static long sessionStartTime = 0;
    protected static volatile boolean lifecycleHasRun = false;
    private static final HashMap<String, Object> _lifecycleContextData = new HashMap<>();
    private static final HashMap<String, Object> _lifecycleContextDataLowercase = new HashMap<>();
    private static final HashMap<String, Object> _previousSessionlifecycleContextData = new HashMap<>();
    private static final Object _contextDataMutex = new Object();
    private static final Object _lowercaseContextDataMutex = new Object();

    Lifecycle() {
    }

    protected static void start(Activity activity, Map<String, Object> data) {
        if (!lifecycleHasRun) {
            lifecycleHasRun = true;
            try {
                SharedPreferences userDefaults = StaticMethods.getSharedPreferences();
                Activity currentActivity = null;
                try {
                    currentActivity = StaticMethods.getCurrentActivity();
                } catch (StaticMethods.NullActivityException e) {
                }
                if (currentActivity != null && activity != null && currentActivity.getComponentName().toString().equals(activity.getComponentName().toString())) {
                    Messages.checkForInAppMessage(null, null, null);
                }
                StaticMethods.setCurrentActivity(activity);
                MobileConfig mobileConfig = MobileConfig.getInstance();
                long lastPauseDate = userDefaults.getLong("ADMS_PauseDate", 0L);
                int sessionTimeout = mobileConfig.getLifecycleTimeout();
                boolean isNewSession = true;
                if (lastPauseDate > 0) {
                    int pausedTime = (int) ((new Date().getTime() - lastPauseDate) / 1000);
                    long originalStartDate = userDefaults.getLong("ADMS_SessionStart", 0L);
                    sessionStartTime = originalStartDate / 1000;
                    AnalyticsTrackTimedAction.sharedInstance().trackTimedActionUpdateAdjustedStartTime(pausedTime);
                    if (pausedTime < sessionTimeout && originalStartDate > 0) {
                        try {
                            SharedPreferences.Editor editor = StaticMethods.getSharedPreferencesEditor();
                            editor.putLong("ADMS_SessionStart", ((long) (pausedTime * 1000)) + originalStartDate);
                            editor.commit();
                        } catch (StaticMethods.NullContextException e2) {
                            StaticMethods.logErrorFormat("Lifecycle - Error while updating start time (%s).", e2.getMessage());
                        }
                        sessionStartTime = userDefaults.getLong("ADMS_SessionStart", 0L) / 1000;
                        isNewSession = false;
                    }
                }
                long date = new Date().getTime();
                if (isNewSession) {
                    mobileConfig.downloadRemoteConfigs();
                    _lifecycleContextData.clear();
                    clearContextDataLowercase();
                    HashMap<String, Object> contextData = data != null ? new HashMap<>(data) : new HashMap<>();
                    if (!userDefaults.contains("ADMS_InstallDate")) {
                        addInstallData(contextData, date);
                    } else {
                        addNonInstallData(contextData, date);
                        addUpgradeData(contextData, date);
                        addSessionLengthData(contextData);
                    }
                    addLifecycleGenericData(contextData, date);
                    generateLifecycleToBeSaved(contextData);
                    persistLifecycleContextData();
                    if (mobileConfig.mobileUsingAnalytics()) {
                        AnalyticsTrackInternal.trackInternal("Lifecycle", contextData, StaticMethods.getTimeSince1970() - 1);
                    }
                    if (!mobileConfig.getAamAnalyticsForwardingEnabled()) {
                        AudienceManagerWorker.SubmitSignal(_lifecycleContextData, null);
                    }
                }
                checkForPushMessageClickThrough(activity);
                resetLifecycleFlags(date);
            } catch (StaticMethods.NullContextException e3) {
                StaticMethods.logErrorFormat("Lifecycle - Error starting lifecycle (%s).", e3.getMessage());
            }
        }
    }

    protected static void stop() {
        lifecycleHasRun = false;
        StaticMethods.updateLastKnownTimestamp(Long.valueOf(StaticMethods.getTimeSince1970()));
        try {
            SharedPreferences.Editor editor = StaticMethods.getSharedPreferencesEditor();
            editor.putBoolean("ADMS_SuccessfulClose", true);
            editor.putLong("ADMS_PauseDate", new Date().getTime());
            editor.commit();
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logErrorFormat("Lifecycle - Error updating lifecycle pause data (%s)", e.getMessage());
        }
        try {
            Activity currentActivity = StaticMethods.getCurrentActivity();
            if (currentActivity.isFinishing()) {
                Messages.resetAllInAppMessages();
            }
        } catch (StaticMethods.NullActivityException e2) {
        }
    }

    private static void persistLifecycleContextData() {
        try {
            SharedPreferences.Editor editor = StaticMethods.getSharedPreferencesEditor();
            JSONObject lifecycleJSON = new JSONObject(_lifecycleContextData);
            editor.putString("ADMS_LifecycleData", lifecycleJSON.toString());
            editor.commit();
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logWarningFormat("Lifecycle - Error persisting lifecycle data (%s)", e.getMessage());
        }
    }

    protected static HashMap<String, Object> getContextData() {
        HashMap<String, Object> map;
        synchronized (_contextDataMutex) {
            if (_lifecycleContextData.size() > 0) {
                map = _lifecycleContextData;
            } else if (_previousSessionlifecycleContextData.size() > 0) {
                map = _previousSessionlifecycleContextData;
            } else {
                addPersistedLifecycleToMap(_previousSessionlifecycleContextData);
                map = _previousSessionlifecycleContextData;
            }
        }
        return map;
    }

    protected static void updateContextData(HashMap<String, Object> data) {
        synchronized (_contextDataMutex) {
            _lifecycleContextData.putAll(data);
        }
        synchronized (_lowercaseContextDataMutex) {
            for (Map.Entry<String, Object> entry : data.entrySet()) {
                _lifecycleContextDataLowercase.put(entry.getKey().toLowerCase(), entry.getValue());
            }
        }
    }

    protected static void removeContextData(String key) {
        synchronized (_contextDataMutex) {
            _lifecycleContextData.remove(key);
        }
        synchronized (_lowercaseContextDataMutex) {
            _lifecycleContextDataLowercase.remove(key.toLowerCase());
        }
    }

    protected static Map<String, Object> getContextDataLowercase() {
        HashMap<String, Object> map;
        synchronized (_lowercaseContextDataMutex) {
            if (_lifecycleContextDataLowercase.size() <= 0) {
                HashMap<String, Object> previousSessionlifecycleContextData = new HashMap<>();
                addPersistedLifecycleToMap(previousSessionlifecycleContextData);
                for (Map.Entry<String, Object> entry : previousSessionlifecycleContextData.entrySet()) {
                    _lifecycleContextDataLowercase.put(entry.getKey().toLowerCase(), entry.getValue());
                }
            }
            map = _lifecycleContextDataLowercase;
        }
        return map;
    }

    private static void clearContextDataLowercase() {
        synchronized (_lowercaseContextDataMutex) {
            _lifecycleContextDataLowercase.clear();
        }
    }

    private static void addPersistedLifecycleToMap(Map<String, Object> data) {
        try {
            String lifecycleJSONString = StaticMethods.getSharedPreferences().getString("ADMS_LifecycleData", null);
            if (lifecycleJSONString != null && lifecycleJSONString.length() > 0) {
                JSONObject lifecycleData = new JSONObject(lifecycleJSONString);
                data.putAll(StaticMethods.mapFromJson(lifecycleData));
            }
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logErrorFormat("Lifecycle - Issue loading persisted lifecycle data", e.getMessage());
        } catch (JSONException ex) {
            StaticMethods.logWarningFormat("Lifecycle - Issue loading persisted lifecycle data (%s)", ex.getMessage());
        }
    }

    private static void generateLifecycleToBeSaved(Map<String, Object> data) {
        HashMap<String, Object> mutableData = data != null ? new HashMap<>(data) : new HashMap<>();
        mutableData.putAll(StaticMethods.getDefaultData());
        mutableData.put("a.locale", StaticMethods.getDefaultAcceptLanguage());
        _lifecycleContextData.putAll(mutableData);
        clearContextDataLowercase();
        for (Map.Entry<String, Object> entry : _lifecycleContextData.entrySet()) {
            _lifecycleContextDataLowercase.put(entry.getKey().toLowerCase(), entry.getValue());
        }
    }

    private static void resetLifecycleFlags(long date) {
        try {
            SharedPreferences.Editor editor = StaticMethods.getSharedPreferencesEditor();
            if (!StaticMethods.getSharedPreferences().contains("ADMS_SessionStart")) {
                editor.putLong("ADMS_SessionStart", date);
                sessionStartTime = date / 1000;
            }
            editor.putString("ADMS_LastVersion", StaticMethods.getApplicationVersion());
            editor.putBoolean("ADMS_SuccessfulClose", false);
            editor.remove("ADMS_PauseDate");
            editor.commit();
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logErrorFormat("Lifecycle - Error resetting lifecycle flags (%s).", e.getMessage());
        }
    }

    private static void addInstallData(Map<String, Object> cdata, long date) {
        DateFormat dayMonthYearFormat = new SimpleDateFormat("M/d/yyyy", Locale.US);
        cdata.put("a.InstallDate", dayMonthYearFormat.format(Long.valueOf(date)));
        cdata.put("a.InstallEvent", "InstallEvent");
        cdata.put("a.DailyEngUserEvent", "DailyEngUserEvent");
        cdata.put("a.MonthlyEngUserEvent", "MonthlyEngUserEvent");
        try {
            if (StaticMethods.getSharedPreferences().contains("ADMS_Referrer_ContextData_Json_String")) {
                Map<String, Object> referrerContextData = ReferrerHandler.parseV3Response(StaticMethods.getSharedPreferences().getString("ADMS_Referrer_ContextData_Json_String", null));
                cdata.putAll(referrerContextData);
            } else if (StaticMethods.getSharedPreferences().contains("utm_campaign")) {
                String source = StaticMethods.getSharedPreferences().getString("utm_source", null);
                String medium = StaticMethods.getSharedPreferences().getString("utm_medium", null);
                String term = StaticMethods.getSharedPreferences().getString("utm_term", null);
                String content = StaticMethods.getSharedPreferences().getString("utm_content", null);
                String campaign = StaticMethods.getSharedPreferences().getString("utm_campaign", null);
                String trackingcode = StaticMethods.getSharedPreferences().getString("trackingcode", null);
                if (source != null && campaign != null) {
                    cdata.put("a.referrer.campaign.source", source);
                    cdata.put("a.referrer.campaign.medium", medium);
                    cdata.put("a.referrer.campaign.term", term);
                    cdata.put("a.referrer.campaign.content", content);
                    cdata.put("a.referrer.campaign.name", campaign);
                    cdata.put("a.referrer.campaign.trackingcode", trackingcode);
                }
            } else if (MobileConfig.getInstance().mobileReferrerConfigured() && MobileConfig.getInstance().getReferrerTimeout() > 0) {
                ReferrerHandler.setReferrerProcessed(false);
                Messages.block3rdPartyCallbacksQueueForReferrer();
            }
            SharedPreferences.Editor editor = StaticMethods.getSharedPreferencesEditor();
            editor.putLong("ADMS_InstallDate", date);
            editor.commit();
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logErrorFormat("Lifecycle - Error setting install data (%s).", e.getMessage());
        }
    }

    private static void addUpgradeData(Map<String, Object> cdata, long date) {
        try {
            SharedPreferences.Editor editor = StaticMethods.getSharedPreferencesEditor();
            long upgradeDate = StaticMethods.getSharedPreferences().getLong("ADMS_UpgradeDate", 0L);
            if (!StaticMethods.getApplicationVersion().equalsIgnoreCase(StaticMethods.getSharedPreferences().getString("ADMS_LastVersion", ""))) {
                cdata.put("a.UpgradeEvent", "UpgradeEvent");
                editor.putLong("ADMS_UpgradeDate", date);
                editor.putInt("ADMS_LaunchesAfterUpgrade", 0);
            } else if (upgradeDate > 0) {
                cdata.put("a.DaysSinceLastUpgrade", calculateDaysSince(upgradeDate, date));
            }
            if (upgradeDate > 0) {
                int newLaunchesAfterUpgradeCount = StaticMethods.getSharedPreferences().getInt("ADMS_LaunchesAfterUpgrade", 0) + 1;
                cdata.put("a.LaunchesSinceUpgrade", "" + newLaunchesAfterUpgradeCount);
                editor.putInt("ADMS_LaunchesAfterUpgrade", newLaunchesAfterUpgradeCount);
            }
            editor.commit();
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logErrorFormat("Lifecycle - Error setting upgrade data (%s).", e.getMessage());
        }
    }

    private static void addLifecycleGenericData(Map<String, Object> cdata, long date) {
        cdata.putAll(StaticMethods.getDefaultData());
        cdata.put("a.LaunchEvent", "LaunchEvent");
        cdata.put("a.OSVersion", StaticMethods.getOperatingSystem());
        DateFormat hourOfDayDateFormat = new SimpleDateFormat("H", Locale.US);
        cdata.put("a.HourOfDay", hourOfDayDateFormat.format(Long.valueOf(date)));
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);
        int dow = cal.get(7);
        cdata.put("a.DayOfWeek", Integer.toString(dow));
        String adid = StaticMethods.getAdvertisingIdentifier();
        if (adid != null) {
            cdata.put("a.adid", adid);
        }
        try {
            SharedPreferences.Editor editor = StaticMethods.getSharedPreferencesEditor();
            int launches = StaticMethods.getSharedPreferences().getInt("ADMS_Launches", 0) + 1;
            cdata.put("a.Launches", Integer.toString(launches));
            editor.putInt("ADMS_Launches", launches);
            editor.putLong("ADMS_LastDateUsed", date);
            editor.commit();
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logErrorFormat("Lifecycle - Error adding generic data (%s).", e.getMessage());
        }
    }

    private static void addNonInstallData(Map<String, Object> cdata, long date) {
        try {
            DateFormat dailyFormatter = new SimpleDateFormat("yyyy/M/d", Locale.US);
            long lastUseMillis = StaticMethods.getSharedPreferences().getLong("ADMS_LastDateUsed", 0L);
            String lastUsedDateDaily = dailyFormatter.format(new Date(lastUseMillis));
            String currentDateDaily = dailyFormatter.format(Long.valueOf(date));
            if (!currentDateDaily.equalsIgnoreCase(lastUsedDateDaily)) {
                cdata.put("a.DailyEngUserEvent", "DailyEngUserEvent");
            }
            DateFormat monthlyFormatter = new SimpleDateFormat("yyyy/M", Locale.US);
            String lastUsedDateMonthly = monthlyFormatter.format(new Date(lastUseMillis));
            String currentDateMonthly = monthlyFormatter.format(Long.valueOf(date));
            if (!currentDateMonthly.equalsIgnoreCase(lastUsedDateMonthly)) {
                cdata.put("a.MonthlyEngUserEvent", "MonthlyEngUserEvent");
            }
            long installMillis = StaticMethods.getSharedPreferences().getLong("ADMS_InstallDate", 0L);
            cdata.put("a.DaysSinceFirstUse", calculateDaysSince(installMillis, date));
            cdata.put("a.DaysSinceLastUse", calculateDaysSince(lastUseMillis, date));
            if (!StaticMethods.getSharedPreferences().getBoolean("ADMS_SuccessfulClose", false)) {
                SharedPreferences.Editor editor = StaticMethods.getSharedPreferencesEditor();
                editor.remove("ADMS_PauseDate");
                editor.remove("ADMS_SessionStart");
                sessionStartTime = StaticMethods.getTimeSince1970();
                editor.commit();
                long lastKnownTimestamp = StaticMethods.getSharedPreferences().getLong("ADBLastKnownTimestampKey", 0L);
                if (lastKnownTimestamp > 0 && MobileConfig.getInstance().mobileUsingAnalytics() && MobileConfig.getInstance().getOfflineTrackingEnabled() && MobileConfig.getInstance().getBackdateSessionInfoEnabled()) {
                    try {
                        SharedPreferences defaults = StaticMethods.getSharedPreferences();
                        HashMap<String, Object> crashCData = new HashMap<>();
                        crashCData.put("a.CrashEvent", "CrashEvent");
                        crashCData.put("a.OSVersion", defaults.getString("ADOBEMOBILE_STOREDDEFAULTS_OS", ""));
                        crashCData.put("a.AppID", defaults.getString("ADOBEMOBILE_STOREDDEFAULTS_APPID", ""));
                        AnalyticsTrackInternal.trackInternal("Crash", crashCData, 1 + lastKnownTimestamp);
                        _lifecycleContextData.put("a.CrashEvent", "CrashEvent");
                    } catch (StaticMethods.NullContextException ex) {
                        StaticMethods.logWarningFormat("Config - Unable to get crash data for backdated hit (%s)", ex.getLocalizedMessage());
                    }
                } else {
                    cdata.put("a.CrashEvent", "CrashEvent");
                }
                AnalyticsTrackTimedAction.sharedInstance().trackTimedActionUpdateActionsClearAdjustedStartTime();
            }
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logErrorFormat("Lifecycle - Error setting non install data (%s).", e.getMessage());
        }
    }

    private static void addSessionLengthData(Map<String, Object> cdata) {
        try {
            long pauseDate = StaticMethods.getSharedPreferences().getLong("ADMS_PauseDate", 0L);
            int sessionTimeout = MobileConfig.getInstance().getLifecycleTimeout();
            int timeSincePause = (int) ((new Date().getTime() - pauseDate) / 1000);
            if (timeSincePause >= sessionTimeout) {
                long sessionStart = StaticMethods.getSharedPreferences().getLong("ADMS_SessionStart", 0L);
                int sessionTime = (int) ((pauseDate - sessionStart) / 1000);
                sessionStartTime = StaticMethods.getTimeSince1970();
                if (sessionTime > 0 && sessionTime < 604800) {
                    long lastKnownTimestamp = StaticMethods.getSharedPreferences().getLong("ADBLastKnownTimestampKey", 0L);
                    if (lastKnownTimestamp > 0 && MobileConfig.getInstance().mobileUsingAnalytics() && MobileConfig.getInstance().getOfflineTrackingEnabled() && MobileConfig.getInstance().getBackdateSessionInfoEnabled()) {
                        try {
                            SharedPreferences defaults = StaticMethods.getSharedPreferences();
                            HashMap<String, Object> sessionCData = new HashMap<>();
                            sessionCData.put("a.PrevSessionLength", String.valueOf(sessionTime));
                            sessionCData.put("a.OSVersion", defaults.getString("ADOBEMOBILE_STOREDDEFAULTS_OS", ""));
                            sessionCData.put("a.AppID", defaults.getString("ADOBEMOBILE_STOREDDEFAULTS_APPID", ""));
                            AnalyticsTrackInternal.trackInternal("SessionInfo", sessionCData, 1 + lastKnownTimestamp);
                            _lifecycleContextData.put("a.PrevSessionLength", String.valueOf(sessionTime));
                        } catch (StaticMethods.NullContextException ex) {
                            StaticMethods.logWarningFormat("Config - Unable to get session data for backdated hit (%s)", ex.getLocalizedMessage());
                        }
                    } else {
                        cdata.put("a.PrevSessionLength", Integer.toString(sessionTime));
                    }
                } else {
                    cdata.put("a.ignoredSessionLength", Integer.toString(sessionTime));
                }
                SharedPreferences.Editor editor = StaticMethods.getSharedPreferencesEditor();
                editor.remove("ADMS_SessionStart");
                editor.commit();
            }
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logErrorFormat("Lifecycle - Error adding session length data (%s).", e.getMessage());
        }
    }

    private static String calculateDaysSince(long since, long to) {
        return Integer.toString((int) ((to - since) / MeasurementDispatcher.MILLIS_PER_DAY));
    }

    private static void checkForPushMessageClickThrough(Activity activity) {
        Intent intent;
        final String pushMessageID;
        if (activity != null && (intent = activity.getIntent()) != null && (pushMessageID = intent.getStringExtra(ADB_LIFECYCLE_PUSH_MESSAGE_ID_KEY)) != null && pushMessageID.length() > 0) {
            HashMap<String, Object> messageIDMap = new HashMap<String, Object>() { // from class: com.adobe.mobile.Lifecycle.1
                {
                    put("a.push.payloadId", pushMessageID);
                }
            };
            updateContextData(messageIDMap);
            if (MobileConfig.getInstance().mobileUsingAnalytics()) {
                AnalyticsTrackInternal.trackInternal(ADB_TRACK_INTERNAL_PUSH_CLICK_THROUGH, messageIDMap, StaticMethods.getTimeSince1970());
            }
        }
    }
}
