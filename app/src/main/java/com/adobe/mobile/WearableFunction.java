package com.adobe.mobile;

import android.content.Context;
import com.adobe.mobile.Config;
import com.adobe.mobile.StaticMethods;
import com.adobe.mobile.WearableDataResponse;

/* JADX INFO: loaded from: classes.dex */
final class WearableFunction {
    private static boolean sendHitFlag = false;

    WearableFunction() {
    }

    protected static boolean isHandheldAppStarted() {
        try {
            return StaticMethods.getSharedPreferences().getLong("ADMS_Handheld_App_InstallDate", 0L) != 0;
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logWarningFormat("Wearable - Error getting install date of handheld app", new Object[0]);
            return false;
        }
    }

    protected static boolean shouldSendHit() {
        if (sendHitFlag) {
            return true;
        }
        if (Config.getApplicationType() != Config.ApplicationType.APPLICATION_TYPE_WEARABLE) {
            sendHitFlag = true;
            return true;
        }
        if (isHandheldAppStarted()) {
            sendHitFlag = true;
            return true;
        }
        StaticMethods.logWarningFormat("Analytics - Failed to send the Wearable request, containing app should get launched before Wearable app.", new Object[0]);
        return false;
    }

    protected static void sendGenericRequest(String url, int timeout) {
        retrieveData(url, timeout);
    }

    protected static byte[] retrieveData(String url, int readTimeout) {
        if (!StaticMethods.isWearableApp()) {
            return null;
        }
        try {
            Context appCtx = StaticMethods.getSharedContext().getApplicationContext();
            WearableDataResponse.GetResponse response = (WearableDataResponse.GetResponse) new WearableDataConnection(appCtx).send(WearableDataRequest.createGetRequest(url, readTimeout));
            if (response == null) {
                return null;
            }
            return response.getResult();
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logErrorFormat("Analytics - Error registering network receiver (%s)", e.getMessage());
            return null;
        }
    }

    protected static byte[] retrieveAnalyticsRequestData(String url, String postBody, int timeout) {
        if (!StaticMethods.isWearableApp()) {
            return null;
        }
        try {
            Context appCtx = StaticMethods.getSharedContext().getApplicationContext();
            WearableDataResponse.PostResponse response = (WearableDataResponse.PostResponse) new WearableDataConnection(appCtx).send(WearableDataRequest.createPostRequest(url, postBody, timeout));
            if (response == null) {
                return null;
            }
            return response.getResult();
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logErrorFormat("Analytics - Error registering network receiver (%s)", e.getMessage());
            return null;
        }
    }

    protected static boolean sendThirdPartyRequest(String url, String postBody, int timeout, String postType) {
        if (!StaticMethods.isWearableApp()) {
            return true;
        }
        try {
            Context appCtx = StaticMethods.getSharedContext().getApplicationContext();
            WearableDataResponse response = new WearableDataConnection(appCtx).send(WearableDataRequest.createThirdPartyRequest(url, postBody, timeout, postType));
            return response != null && response.isSuccess();
        } catch (StaticMethods.NullContextException e) {
            StaticMethods.logErrorFormat("External Callback - Error registering network receiver (%s)", e.getMessage());
            return false;
        }
    }
}
