package com.adobe.mobile;

import android.content.Context;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes.dex */
final class WearableFunctionBridge {
    private static Class<?> configSynchronizerClassLoader;
    private static Class<?> wearableFunctionClassLoader;

    WearableFunctionBridge() {
    }

    private static Class<?> getWearableFunctionClass() {
        if (wearableFunctionClassLoader != null) {
            return wearableFunctionClassLoader;
        }
        try {
            ClassLoader classLoader = WearableFunctionBridge.class.getClassLoader();
            wearableFunctionClassLoader = classLoader.loadClass("com.adobe.mobile.WearableFunction");
        } catch (Exception e) {
            StaticMethods.logErrorFormat("Wearable - Failed to load class com.adobe.mobile.WearableFunction", e.getLocalizedMessage());
        }
        return wearableFunctionClassLoader;
    }

    private static Class<?> getConfigSynchronizerClass() {
        if (configSynchronizerClassLoader != null) {
            return configSynchronizerClassLoader;
        }
        try {
            ClassLoader classLoader = WearableFunctionBridge.class.getClassLoader();
            configSynchronizerClassLoader = classLoader.loadClass("com.adobe.mobile.ConfigSynchronizer");
        } catch (Exception e) {
            StaticMethods.logErrorFormat("Wearable - Failed to load class com.adobe.mobile.ConfigSynchronizer", e.getLocalizedMessage());
        }
        return configSynchronizerClassLoader;
    }

    protected static boolean isGooglePlayServicesEnabled() {
        try {
            ClassLoader classLoader = WearableFunctionBridge.class.getClassLoader();
            Class<?> GoogleApiAvailabilityClass = classLoader.loadClass("com.google.android.gms.common.GoogleApiAvailability");
            Method getInstanceMethod = GoogleApiAvailabilityClass.getDeclaredMethod("getInstance", new Class[0]);
            Object instance = getInstanceMethod.invoke(null, new Object[0]);
            Method isGooglePlayServicesAvailableMethod = GoogleApiAvailabilityClass.getDeclaredMethod("isGooglePlayServicesAvailable", Context.class);
            Object result = isGooglePlayServicesAvailableMethod.invoke(instance, StaticMethods.getSharedContext());
            if (result instanceof Integer) {
                return ((Integer) result).intValue() == 0;
            }
        } catch (IllegalStateException e) {
            StaticMethods.logDebugFormat("Wearable - Google Play Services is not enabled in your app's AndroidManifest.xml", e.getLocalizedMessage());
        } catch (Exception e2) {
        }
        try {
            ClassLoader classLoader2 = WearableFunctionBridge.class.getClassLoader();
            Class<?> GooglePlayServicesUtilClass = classLoader2.loadClass("com.google.android.gms.common.GooglePlayServicesUtil");
            Method isGooglePlayServicesAvailableMethod2 = GooglePlayServicesUtilClass.getDeclaredMethod("isGooglePlayServicesAvailable", Context.class);
            Object result2 = isGooglePlayServicesAvailableMethod2.invoke(null, StaticMethods.getSharedContext());
            if (result2 instanceof Integer) {
                return ((Integer) result2).intValue() == 0;
            }
        } catch (IllegalStateException e3) {
            StaticMethods.logDebugFormat("Wearable - Google Play Services is not enabled in your app's AndroidManifest.xml", e3.getLocalizedMessage());
        } catch (Exception e4) {
        }
        return false;
    }

    protected static boolean shouldSendHit() {
        if (!StaticMethods.isWearableApp()) {
            return true;
        }
        try {
            Method shouldSendHitMethod = getWearableFunctionClass().getDeclaredMethod("shouldSendHit", new Class[0]);
            Object result = shouldSendHitMethod.invoke(null, new Object[0]);
            if (result instanceof Boolean) {
                return ((Boolean) result).booleanValue();
            }
            return true;
        } catch (Exception e) {
            StaticMethods.logErrorFormat("Wearable - Error checking status of handheld app (%s)", e.getLocalizedMessage());
            return true;
        }
    }

    protected static void sendGenericRequest(String url, int timeout, String source) {
        try {
            Method sendGenericRequestMethod = getWearableFunctionClass().getDeclaredMethod("sendGenericRequest", String.class, Integer.TYPE);
            sendGenericRequestMethod.invoke(null, url, Integer.valueOf(timeout));
            StaticMethods.logDebugFormat("%s - Request Sent(%s)", source, url);
        } catch (Exception e) {
            StaticMethods.logErrorFormat("Wearable - Error sending request (%s)", e.getLocalizedMessage());
        }
    }

    protected static byte[] retrieveData(String url, int readTimeout) {
        try {
            Method retrieveDataMethod = getWearableFunctionClass().getDeclaredMethod("retrieveData", String.class, Integer.TYPE);
            Object result = retrieveDataMethod.invoke(null, url, Integer.valueOf(readTimeout));
            if (result instanceof byte[]) {
                return (byte[]) result;
            }
        } catch (Exception e) {
            StaticMethods.logErrorFormat("Wearable - Error sending request (%s)", e.getLocalizedMessage());
        }
        return null;
    }

    protected static byte[] retrieveAnalyticsRequestData(String url, String postBody, int timeout, String logPrefix) {
        try {
            Method retrieveDataMethod = getWearableFunctionClass().getDeclaredMethod("retrieveAnalyticsRequestData", String.class, String.class, Integer.TYPE);
            Object result = retrieveDataMethod.invoke(null, url, postBody, Integer.valueOf(timeout));
            if (result instanceof byte[]) {
                return (byte[]) result;
            }
        } catch (Exception e) {
            StaticMethods.logErrorFormat("Wearable - Error sending request (%s)", e.getLocalizedMessage());
        }
        return null;
    }

    protected static boolean sendThirdPartyRequest(String url, String postBody, int timeout, String postType, String logPrefix) {
        try {
            Method sendThirdPartyRequestMethod = getWearableFunctionClass().getDeclaredMethod("sendThirdPartyRequest", String.class, String.class, Integer.TYPE, String.class);
            Object result = sendThirdPartyRequestMethod.invoke(null, url, postBody, Integer.valueOf(timeout), postType);
            if (result instanceof Boolean) {
                if (((Boolean) result).booleanValue()) {
                    StaticMethods.logDebugFormat("%s - Successfully forwarded hit (url:%s body:%s contentType:%s)", logPrefix, url, postBody, postType);
                } else {
                    StaticMethods.logDebugFormat("%s - Failed to forwarded hit (url:%s body:%s contentType:%s)", logPrefix, url, postBody, postType);
                }
                return ((Boolean) result).booleanValue();
            }
        } catch (Exception e) {
            StaticMethods.logErrorFormat("Wearable - Error sending request (%s)", e.getLocalizedMessage());
        }
        return false;
    }

    protected static void syncVisitorIDToWearable(String vid) {
        if (!StaticMethods.isWearableApp() && MobileConfig.getInstance().mobileUsingGooglePlayServices()) {
            try {
                Method syncVisitorIDMethod = getConfigSynchronizerClass().getDeclaredMethod("syncVisitorID", String.class);
                syncVisitorIDMethod.invoke(null, vid);
            } catch (Exception e) {
                StaticMethods.logDebugFormat("Wearable - Unable to sync visitor id (%s)", e.getLocalizedMessage());
            }
        }
    }

    protected static void syncAdvertisingIdentifierToWearable(String adid) {
        if (!StaticMethods.isWearableApp() && MobileConfig.getInstance().mobileUsingGooglePlayServices()) {
            try {
                Method syncAdidMethod = getConfigSynchronizerClass().getDeclaredMethod("syncAdvertisingIdentifier", String.class);
                syncAdidMethod.invoke(null, adid);
            } catch (Exception e) {
                StaticMethods.logDebugFormat("Wearable - Unable to sync advertisingIdentifier id (%s)", e.getLocalizedMessage());
            }
        }
    }

    protected static void syncPushEnabledToWearable(boolean enabled) {
        if (!StaticMethods.isWearableApp() && MobileConfig.getInstance().mobileUsingGooglePlayServices()) {
            try {
                Method syncPushStatusMethod = getConfigSynchronizerClass().getDeclaredMethod("syncPushEnabled", Boolean.TYPE);
                syncPushStatusMethod.invoke(null, Boolean.valueOf(enabled));
            } catch (Exception e) {
                StaticMethods.logDebugFormat("Wearable - Unable to sync push enabled status (%s)", e.getLocalizedMessage());
            }
        }
    }

    protected static void syncVidServiceToWearable(String mid, String hint, String blob, long ssl, long lastSync, String customerIDs) {
        if (!StaticMethods.isWearableApp() && MobileConfig.getInstance().mobileUsingGooglePlayServices()) {
            try {
                Method syncVidServiceMethod = getConfigSynchronizerClass().getDeclaredMethod("syncVidService", String.class, String.class, String.class, Long.TYPE, Long.TYPE, String.class);
                syncVidServiceMethod.invoke(null, mid, hint, blob, Long.valueOf(ssl), Long.valueOf(lastSync), customerIDs);
            } catch (Exception e) {
                StaticMethods.logDebugFormat("Wearable - Unable to sync visitor id service (%s)", e.getLocalizedMessage());
            }
        }
    }

    protected static void syncPrivacyStatusToWearable(int privacyStatus) {
        if (!StaticMethods.isWearableApp() && MobileConfig.getInstance().mobileUsingGooglePlayServices()) {
            try {
                Method syncPrivacyStatusMethod = getConfigSynchronizerClass().getDeclaredMethod("syncPrivacyStatus", Integer.TYPE);
                syncPrivacyStatusMethod.invoke(null, Integer.valueOf(privacyStatus));
            } catch (Exception e) {
                StaticMethods.logDebugFormat("Wearable - Unable to sync privacy status (%s)", e.getLocalizedMessage());
            }
        }
    }

    protected static void syncConfigFromHandheld() {
        if (StaticMethods.isWearableApp()) {
            try {
                Method syncConfigFromHandheldMethod = getConfigSynchronizerClass().getDeclaredMethod("syncConfigFromHandheld", new Class[0]);
                syncConfigFromHandheldMethod.invoke(null, new Object[0]);
            } catch (Exception e) {
                StaticMethods.logDebugFormat("Wearable - Unable to sync config (%s)", e.getLocalizedMessage());
            }
        }
    }
}
