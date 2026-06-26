package com.adobe.mobile;

import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class AudienceManager {

    public interface AudienceManagerCallback<T> {
        void call(T t);
    }

    public static HashMap<String, Object> getVisitorProfile() {
        return AudienceManagerWorker.GetVisitorProfile();
    }

    public static String getDpuuid() {
        return AudienceManagerWorker.GetDpuuid();
    }

    public static String getDpid() {
        return AudienceManagerWorker.GetDpid();
    }

    public static void setDpidAndDpuuid(String dpid, String dpuuid) {
        AudienceManagerWorker.SetDpidAndDpuuid(dpid, dpuuid);
    }

    public static void signalWithData(Map<String, Object> data, AudienceManagerCallback<Map<String, Object>> callback) {
        if (StaticMethods.isWearableApp()) {
            StaticMethods.logWarningFormat("Audience Manager - Method signalWithData is not available for Wearable", new Object[0]);
        } else {
            AudienceManagerWorker.SubmitSignal(data, callback);
        }
    }

    public static void reset() {
        AudienceManagerWorker.Reset();
    }
}
