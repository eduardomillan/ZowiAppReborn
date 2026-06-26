package com.adobe.mobile;

import android.content.Context;
import android.content.SharedPreferences;
import com.adobe.mobile.StaticMethods;
import com.adobe.mobile.WearableDataResponse;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
final class ConfigSynchronizer {
    private static final Object _handheldInstallDateMutex = new Object();
    private static final Object _aidMutex = new Object();
    private static final Object _visitorIDMutex = new Object();
    private static final Object _pushEnabledMutex = new Object();
    private static final Object _adiDMutex = new Object();
    private static final Object _visServiceMutex = new Object();
    private static final Object _privacyStatusMutex = new Object();

    ConfigSynchronizer() {
    }

    protected static void syncVisitorID(String vid) {
        if (!StaticMethods.isWearableApp()) {
            DataMap dataMap = new DataMap();
            dataMap.putString("APP_MEASUREMENT_VISITOR_ID", vid);
            syncData("/abdmobile/data/config/visitorId", dataMap);
        }
    }

    protected static void syncAdvertisingIdentifier(String adid) {
        if (!StaticMethods.isWearableApp()) {
            DataMap dataMap = new DataMap();
            dataMap.putString("ADOBEMOBILE_STOREDDEFAULTS_ADVERTISING_IDENTIFIER", adid);
            syncData("/abdmobile/data/config/adId", dataMap);
        }
    }

    protected static void syncPushEnabled(boolean enabled) {
        if (!StaticMethods.isWearableApp()) {
            DataMap dataMap = new DataMap();
            dataMap.putBoolean("ADBMOBILE_KEY_PUSH_ENABLED", enabled);
            syncData("/abdmobile/data/config/pushEnabled", dataMap);
        }
    }

    protected static void syncVidService(String mid, String hint, String blob, long ssl, long lastSync, String customerIDs) {
        if (!StaticMethods.isWearableApp()) {
            DataMap dataMap = new DataMap();
            dataMap.putString("ADBMOBILE_PERSISTED_MID", mid);
            dataMap.putString("ADBMOBILE_PERSISTED_MID_BLOB", blob);
            dataMap.putString("ADBMOBILE_PERSISTED_MID_HINT", hint);
            dataMap.putLong("ADBMOBILE_VISITORID_TTL", ssl);
            dataMap.putLong("ADBMOBILE_VISITORID_SYNC", lastSync);
            dataMap.putString("ADBMOBILE_VISITORID_IDS", customerIDs);
            syncData("/abdmobile/data/config/vidService", dataMap);
        }
    }

    protected static void syncPrivacyStatus(int privacyStatus) {
        if (!StaticMethods.isWearableApp()) {
            DataMap dataMap = new DataMap();
            dataMap.putInt("PrivacyStatus", privacyStatus);
            syncData("/abdmobile/data/config/privacyStatus", dataMap);
        }
    }

    protected static void syncData(String path, DataMap dataMap) {
        try {
            GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(StaticMethods.getSharedContext()).addApi(Wearable.API).build();
            GoogleApiClientWrapper.connect(mGoogleApiClient);
            ConnectionResult connectionResult = GoogleApiClientWrapper.blockingConnect(mGoogleApiClient, 15000L, TimeUnit.MILLISECONDS);
            if (connectionResult == null || !connectionResult.isSuccess()) {
                StaticMethods.logWarningFormat("Wearable - Failed to setup connection", new Object[0]);
                return;
            }
            PutDataMapRequest dataMapRequest = PutDataMapRequest.create(path);
            dataMapRequest.getDataMap().putAll(dataMap);
            PutDataRequest request = dataMapRequest.asPutDataRequest();
            Wearable.DataApi.putDataItem(mGoogleApiClient, request);
        } catch (StaticMethods.NullContextException e) {
        }
    }

    protected static DataMap getSharedConfig() {
        DataMap dataMap = new DataMap();
        try {
            dataMap.putLong("ADMS_InstallDate", StaticMethods.getSharedPreferences().getLong("ADMS_InstallDate", 0L));
            dataMap.putBoolean("ADOBEMOBILE_STOREDDEFAULTS_IGNORE_AID", StaticMethods.getSharedPreferences().getBoolean("ADOBEMOBILE_STOREDDEFAULTS_IGNORE_AID", false));
            dataMap.putString("ADOBEMOBILE_STOREDDEFAULTS_AID", StaticMethods.getSharedPreferences().getString("ADOBEMOBILE_STOREDDEFAULTS_AID", null));
            dataMap.putBoolean("ADOBEMOBILE_STOREDDEFAULTS_AID_SYNCED", StaticMethods.getSharedPreferences().getBoolean("ADOBEMOBILE_STOREDDEFAULTS_AID_SYNCED", false));
            dataMap.putString("APP_MEASUREMENT_VISITOR_ID", StaticMethods.getSharedPreferences().getString("APP_MEASUREMENT_VISITOR_ID", null));
            dataMap.putString("ADOBEMOBILE_STOREDDEFAULTS_ADVERTISING_IDENTIFIER", StaticMethods.getSharedPreferences().getString("ADOBEMOBILE_STOREDDEFAULTS_ADVERTISING_IDENTIFIER", null));
            dataMap.putBoolean("ADBMOBILE_KEY_PUSH_ENABLED", StaticMethods.getSharedPreferences().getBoolean("ADBMOBILE_KEY_PUSH_ENABLED", false));
            dataMap.putString("ADBMOBILE_PERSISTED_MID", StaticMethods.getSharedPreferences().getString("ADBMOBILE_PERSISTED_MID", null));
            dataMap.putString("ADBMOBILE_PERSISTED_MID_HINT", StaticMethods.getSharedPreferences().getString("ADBMOBILE_PERSISTED_MID_HINT", null));
            dataMap.putString("ADBMOBILE_PERSISTED_MID_BLOB", StaticMethods.getSharedPreferences().getString("ADBMOBILE_PERSISTED_MID_BLOB", null));
            dataMap.putLong("ADBMOBILE_VISITORID_TTL", StaticMethods.getSharedPreferences().getLong("ADBMOBILE_VISITORID_TTL", 0L));
            dataMap.putLong("ADBMOBILE_VISITORID_SYNC", StaticMethods.getSharedPreferences().getLong("ADBMOBILE_VISITORID_SYNC", 0L));
            dataMap.putString("ADBMOBILE_VISITORID_IDS", StaticMethods.getSharedPreferences().getString("ADBMOBILE_VISITORID_IDS", null));
            if (StaticMethods.getSharedPreferences().contains("PrivacyStatus")) {
                dataMap.putInt("PrivacyStatus", StaticMethods.getSharedPreferences().getInt("PrivacyStatus", 0));
            }
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logErrorFormat("Wearable - Error getting shared preferences", new Object[0]);
        }
        return dataMap;
    }

    protected static void restoreSharedConfig(DataMap dataMap) {
        restoreHandheldInstallDate(dataMap);
        restorePrivacyStatus(dataMap);
        restoreVisitorID(dataMap);
        restoreVidService(dataMap);
        restoreAid(dataMap);
        restoreAdid(dataMap);
        restorePushEnabled(dataMap);
    }

    private static void restoreHandheldInstallDate(DataMap dataMap) {
        synchronized (_handheldInstallDateMutex) {
            try {
                SharedPreferences.Editor editor = StaticMethods.getSharedPreferencesEditor();
                if (dataMap.containsKey("ADMS_InstallDate")) {
                    editor.putLong("ADMS_Handheld_App_InstallDate", dataMap.getLong("ADMS_InstallDate", 0L));
                }
                editor.commit();
            } catch (StaticMethods.NullContextException e) {
                StaticMethods.logErrorFormat("Wearable - Error saving Handheld App install date to shared preferences", new Object[0]);
            }
        }
    }

    private static void restoreAid(DataMap dataMap) {
        synchronized (_aidMutex) {
            try {
                SharedPreferences.Editor editor = StaticMethods.getSharedPreferencesEditor();
                editor.putBoolean("ADOBEMOBILE_STOREDDEFAULTS_IGNORE_AID", dataMap.getBoolean("ADOBEMOBILE_STOREDDEFAULTS_IGNORE_AID"));
                editor.putString("ADOBEMOBILE_STOREDDEFAULTS_AID", dataMap.getString("ADOBEMOBILE_STOREDDEFAULTS_AID"));
                editor.putBoolean("ADOBEMOBILE_STOREDDEFAULTS_AID_SYNCED", dataMap.getBoolean("ADOBEMOBILE_STOREDDEFAULTS_AID_SYNCED"));
                editor.commit();
            } catch (StaticMethods.NullContextException e) {
                StaticMethods.logErrorFormat("Wearable - Error saving Aid data to shared preferences", new Object[0]);
            }
        }
    }

    private static void restoreVisitorID(DataMap dataMap) {
        synchronized (_visitorIDMutex) {
            Config.setUserIdentifier(dataMap.getString("APP_MEASUREMENT_VISITOR_ID"));
        }
    }

    private static void restorePushEnabled(DataMap dataMap) {
        synchronized (_pushEnabledMutex) {
            StaticMethods.setPushEnabled(dataMap.getBoolean("ADBMOBILE_KEY_PUSH_ENABLED"));
        }
    }

    private static void restoreAdid(final DataMap dataMap) {
        synchronized (_adiDMutex) {
            Callable<String> task = new Callable<String>() { // from class: com.adobe.mobile.ConfigSynchronizer.1
                @Override // java.util.concurrent.Callable
                public String call() throws Exception {
                    return dataMap.getString("ADOBEMOBILE_STOREDDEFAULTS_ADVERTISING_IDENTIFIER");
                }
            };
            Config.submitAdvertisingIdentifierTask(task);
        }
    }

    private static void restoreVidService(DataMap dataMap) {
        synchronized (_visServiceMutex) {
            try {
                SharedPreferences.Editor editor = StaticMethods.getSharedPreferencesEditor();
                editor.putString("ADBMOBILE_PERSISTED_MID", dataMap.getString("ADBMOBILE_PERSISTED_MID"));
                editor.putString("ADBMOBILE_PERSISTED_MID_HINT", dataMap.getString("ADBMOBILE_PERSISTED_MID_HINT"));
                editor.putString("ADBMOBILE_PERSISTED_MID_BLOB", dataMap.getString("ADBMOBILE_PERSISTED_MID_BLOB"));
                editor.putLong("ADBMOBILE_VISITORID_TTL", dataMap.getLong("ADBMOBILE_VISITORID_TTL"));
                editor.putLong("ADBMOBILE_VISITORID_SYNC", dataMap.getLong("ADBMOBILE_VISITORID_SYNC"));
                editor.putString("ADBMOBILE_VISITORID_IDS", dataMap.getString("ADBMOBILE_VISITORID_IDS"));
                editor.commit();
            } catch (StaticMethods.NullContextException e) {
                StaticMethods.logErrorFormat("Wearable - Error saving Visitor Id Service data to shared preferences", new Object[0]);
            }
            VisitorIDService.sharedInstance().resetVariablesFromSharedPreferences();
        }
    }

    private static void restorePrivacyStatus(DataMap dataMap) {
        synchronized (_privacyStatusMutex) {
            if (dataMap.getInt("PrivacyStatus") >= MobilePrivacyStatus.values().length) {
                StaticMethods.logWarningFormat("Wearable - Invalid PrivacyStatus value (%d)", Integer.valueOf(dataMap.getInt("PrivacyStatus")));
            } else {
                if (dataMap.containsKey("PrivacyStatus")) {
                    Config.setPrivacyStatus(MobilePrivacyStatus.values()[dataMap.getInt("PrivacyStatus")]);
                }
            }
        }
    }

    protected static void restoreConfig(DataItem item) {
        DataMap dataMap;
        if (item != null && item.getUri() != null && item.getUri().getPath() != null && (dataMap = DataMapItem.fromDataItem(item).getDataMap()) != null) {
            String path = item.getUri().getPath();
            if (path.compareTo("/abdmobile/data/config/visitorId") == 0) {
                restoreVisitorID(dataMap);
                return;
            }
            if (path.compareTo("/abdmobile/data/config/vidService") == 0) {
                restoreVidService(dataMap);
                return;
            }
            if (path.compareTo("/abdmobile/data/config/privacyStatus") == 0) {
                restorePrivacyStatus(dataMap);
            } else if (path.compareTo("/abdmobile/data/config/adId") == 0) {
                restoreAdid(dataMap);
            } else if (path.compareTo("/abdmobile/data/config/pushEnabled") == 0) {
                restorePushEnabled(dataMap);
            }
        }
    }

    protected static void syncConfigFromHandheld() throws Throwable {
        if (StaticMethods.isWearableApp()) {
            try {
                Context appCtx = StaticMethods.getSharedContext().getApplicationContext();
                WearableDataResponse.ShareConfigResponse response = (WearableDataResponse.ShareConfigResponse) new WearableDataConnection(appCtx).send(WearableDataRequest.createShareConfigRequest(15000));
                if (response != null && response.getResult() != null) {
                    restoreSharedConfig(response.getResult());
                }
                String pointsOfInterestURL = MobileConfig.getInstance().getPointsOfInterestURL();
                String inAppMessagesURL = MobileConfig.getInstance().getInAppMessageURL();
                if (pointsOfInterestURL != null || inAppMessagesURL != null) {
                    String poiFileName = null;
                    File poiFile = RemoteDownload.getFileForCachedURL(pointsOfInterestURL);
                    if (poiFile != null) {
                        poiFileName = poiFile.getName();
                    }
                    String iamFileName = null;
                    File iamFile = RemoteDownload.getFileForCachedURL(inAppMessagesURL);
                    if (iamFile != null) {
                        iamFileName = iamFile.getName();
                    }
                    new WearableDataConnection(appCtx).send(WearableDataRequest.createFileRequest(pointsOfInterestURL, poiFileName, 15000));
                    new WearableDataConnection(appCtx).send(WearableDataRequest.createFileRequest(inAppMessagesURL, iamFileName, 15000));
                    MobileConfig.getInstance().loadCachedRemotes();
                }
            } catch (StaticMethods.NullContextException e) {
                StaticMethods.logErrorFormat("Analytics - Error syncing Points of Interest and In-app Messages from handheld app to wearable app (%s)", e.getLocalizedMessage());
            }
        }
    }
}
