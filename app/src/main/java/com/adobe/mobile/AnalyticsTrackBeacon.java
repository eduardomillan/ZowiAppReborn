package com.adobe.mobile;

import com.adobe.mobile.Analytics;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class AnalyticsTrackBeacon {
    private static final String BEACON_ACTION_NAME = "Beacon";
    private static final String BEACON_MAJOR_KEY = "a.beacon.major";
    private static final String BEACON_MINOR_KEY = "a.beacon.minor";
    private static final String BEACON_PROX_KEY = "a.beacon.prox";
    private static final String BEACON_UUID_KEY = "a.beacon.uuid";

    AnalyticsTrackBeacon() {
    }

    protected static void trackBeacon(String uuid, String major, String minor, Analytics.BEACON_PROXIMITY prox, Map<String, Object> data) {
        HashMap<String, Object> contextData = new HashMap<>();
        clearBeacon();
        if (uuid != null) {
            contextData.put(BEACON_UUID_KEY, uuid);
            TargetWorker.addPersistentParameter(BEACON_UUID_KEY, uuid);
        }
        if (major != null) {
            contextData.put(BEACON_MAJOR_KEY, major);
            TargetWorker.addPersistentParameter(BEACON_MAJOR_KEY, major);
        }
        if (minor != null) {
            contextData.put(BEACON_MINOR_KEY, minor);
            TargetWorker.addPersistentParameter(BEACON_MINOR_KEY, minor);
        }
        if (prox != null) {
            contextData.put(BEACON_PROX_KEY, prox.toString());
            TargetWorker.addPersistentParameter(BEACON_PROX_KEY, prox.toString());
        }
        Lifecycle.updateContextData(contextData);
        if (data != null) {
            contextData.putAll(data);
        }
        AnalyticsTrackInternal.trackInternal(BEACON_ACTION_NAME, contextData);
    }

    protected static void clearBeacon() {
        TargetWorker.removePersistentParameter(BEACON_UUID_KEY);
        TargetWorker.removePersistentParameter(BEACON_MAJOR_KEY);
        TargetWorker.removePersistentParameter(BEACON_MINOR_KEY);
        TargetWorker.removePersistentParameter(BEACON_PROX_KEY);
        Lifecycle.removeContextData(BEACON_UUID_KEY);
        Lifecycle.removeContextData(BEACON_MAJOR_KEY);
        Lifecycle.removeContextData(BEACON_MINOR_KEY);
        Lifecycle.removeContextData(BEACON_PROX_KEY);
    }
}
