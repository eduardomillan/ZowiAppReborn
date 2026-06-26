package com.adobe.mobile;

import android.app.Activity;
import android.content.Context;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/* JADX INFO: loaded from: classes.dex */
public final class Config {

    public enum ApplicationType {
        APPLICATION_TYPE_HANDHELD(0),
        APPLICATION_TYPE_WEARABLE(1);

        private final int value;

        ApplicationType(int value) {
            this.value = value;
        }

        protected int getValue() {
            return this.value;
        }
    }

    public static void setContext(Context context) {
        setContext(context, ApplicationType.APPLICATION_TYPE_HANDHELD);
    }

    public static void setContext(Context context, ApplicationType appType) {
        StaticMethods.setSharedContext(context);
        setApplicationType(appType);
        if (appType == ApplicationType.APPLICATION_TYPE_WEARABLE) {
            StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Config.1
                @Override // java.lang.Runnable
                public void run() {
                    WearableFunctionBridge.syncConfigFromHandheld();
                }
            });
        }
    }

    public static String getVersion() {
        return "4.8.1-AN";
    }

    public static MobilePrivacyStatus getPrivacyStatus() {
        FutureTask<MobilePrivacyStatus> f = new FutureTask<>(new Callable<MobilePrivacyStatus>() { // from class: com.adobe.mobile.Config.2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public MobilePrivacyStatus call() throws Exception {
                return MobileConfig.getInstance().getPrivacyStatus();
            }
        });
        StaticMethods.getSharedExecutor().execute(f);
        try {
            return f.get();
        } catch (Exception e) {
            StaticMethods.logErrorFormat("Analytics - Unable to get PrivacyStatus (%s)", e.getMessage());
            return null;
        }
    }

    public static void setPrivacyStatus(final MobilePrivacyStatus status) {
        StaticMethods.getSharedExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Config.3
            @Override // java.lang.Runnable
            public void run() {
                MobileConfig.getInstance().setPrivacyStatus(status);
            }
        });
    }

    public static String getUserIdentifier() {
        FutureTask<String> f = new FutureTask<>(new Callable<String>() { // from class: com.adobe.mobile.Config.4
            @Override // java.util.concurrent.Callable
            public String call() throws Exception {
                return StaticMethods.getVisitorID();
            }
        });
        StaticMethods.getAnalyticsExecutor().execute(f);
        try {
            return f.get();
        } catch (Exception e) {
            StaticMethods.logErrorFormat("Analytics - Unable to get UserIdentifier (%s)", e.getMessage());
            return null;
        }
    }

    public static void setUserIdentifier(final String identifier) {
        StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Config.5
            @Override // java.lang.Runnable
            public void run() {
                StaticMethods.setVisitorID(identifier);
            }
        });
    }

    public static void setPushIdentifier(final String registrationId) {
        StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Config.6
            @Override // java.lang.Runnable
            public void run() {
                StaticMethods.setPushIdentifier(registrationId);
            }
        });
    }

    public static void submitAdvertisingIdentifierTask(final Callable<String> task) {
        StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Config.7
            @Override // java.lang.Runnable
            public void run() {
                try {
                    StaticMethods.setAdvertisingIdentifier((String) task.call());
                } catch (Exception ex) {
                    StaticMethods.logErrorFormat("Config - Error running the task to get Advertising Identifier (%s).", ex.getLocalizedMessage());
                }
            }
        });
    }

    public static ApplicationType getApplicationType() {
        return StaticMethods.getApplicationType();
    }

    public static void setApplicationType(ApplicationType appType) {
        StaticMethods.setApplicationType(appType);
    }

    public static Boolean getDebugLogging() {
        return Boolean.valueOf(StaticMethods.getDebugLogging());
    }

    public static void setDebugLogging(Boolean debugLogging) {
        StaticMethods.setDebugLogging(debugLogging.booleanValue());
    }

    public static BigDecimal getLifetimeValue() {
        FutureTask<BigDecimal> f = new FutureTask<>(new Callable<BigDecimal>() { // from class: com.adobe.mobile.Config.8
            @Override // java.util.concurrent.Callable
            public BigDecimal call() throws Exception {
                return AnalyticsTrackLifetimeValueIncrease.getLifetimeValue();
            }
        });
        StaticMethods.getAnalyticsExecutor().execute(f);
        try {
            return f.get();
        } catch (Exception e) {
            StaticMethods.logErrorFormat("Analytics - Unable to get lifetime value (%s)", e.getMessage());
            return null;
        }
    }

    public static void collectLifecycleData() {
        if (StaticMethods.isWearableApp()) {
            StaticMethods.logWarningFormat("Analytics - Method collectLifecycleData is not available for Wearable", new Object[0]);
        } else {
            StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Config.9
                @Override // java.lang.Runnable
                public void run() {
                    Lifecycle.start(null, null);
                }
            });
        }
    }

    public static void collectLifecycleData(final Activity activity) {
        if (StaticMethods.isWearableApp()) {
            StaticMethods.logWarningFormat("Analytics - Method collectLifecycleData is not available for Wearable", new Object[0]);
        } else {
            StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Config.10
                @Override // java.lang.Runnable
                public void run() {
                    Lifecycle.start(activity, null);
                }
            });
        }
    }

    public static void collectLifecycleData(final Activity activity, final Map<String, Object> contextData) {
        if (StaticMethods.isWearableApp()) {
            StaticMethods.logWarningFormat("Analytics - Method collectLifecycleData is not available for Wearable", new Object[0]);
        } else {
            StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Config.11
                @Override // java.lang.Runnable
                public void run() {
                    Lifecycle.start(activity, contextData);
                }
            });
        }
    }

    public static void pauseCollectingLifecycleData() {
        if (StaticMethods.isWearableApp()) {
            StaticMethods.logWarningFormat("Analytics - Method pauseCollectingLifecycleData is not available for Wearable", new Object[0]);
        } else {
            MessageAlert.clearCurrentDialog();
            StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Config.12
                @Override // java.lang.Runnable
                public void run() {
                    Lifecycle.stop();
                }
            });
        }
    }

    public static void setSmallIconResourceId(final int resourceId) {
        if (StaticMethods.isWearableApp()) {
            StaticMethods.logWarningFormat("Analytics - Method setSmallIconResourceId is not available for Wearable", new Object[0]);
        } else {
            StaticMethods.getMessagesExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Config.13
                @Override // java.lang.Runnable
                public void run() {
                    Messages.setSmallIconResourceId(resourceId);
                }
            });
        }
    }

    public static void setLargeIconResourceId(final int resourceId) {
        if (StaticMethods.isWearableApp()) {
            StaticMethods.logWarningFormat("Analytics - Method setLargeIconResourceId is not available for Wearable", new Object[0]);
        } else {
            StaticMethods.getMessagesExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Config.14
                @Override // java.lang.Runnable
                public void run() {
                    Messages.setLargeIconResourceId(resourceId);
                }
            });
        }
    }

    public static void overrideConfigStream(InputStream stream) {
        MobileConfig.setUserDefinedConfigPath(stream);
    }
}
