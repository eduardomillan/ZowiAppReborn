package com.adobe.mobile;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/* JADX INFO: loaded from: classes.dex */
public final class Analytics {
    private static final String NO_ANALYTICS_MESSAGE = "Analytics - ADBMobile is not configured correctly to use Analytics.";

    public interface TimedActionBlock<Boolean> {
        Boolean call(long j, long j2, Map<String, Object> map);
    }

    public enum BEACON_PROXIMITY {
        PROXIMITY_IMMEDIATE(1),
        PROXIMITY_NEAR(2),
        PROXIMITY_FAR(3),
        PROXIMITY_UNKNOWN(0);

        private final int value;

        BEACON_PROXIMITY(int value) {
            this.value = value;
        }

        protected int getValue() {
            return this.value;
        }

        @Override // java.lang.Enum
        public String toString() {
            switch (this) {
                case PROXIMITY_IMMEDIATE:
                    return "1";
                case PROXIMITY_NEAR:
                    return "2";
                case PROXIMITY_FAR:
                    return "3";
                default:
                    return "0";
            }
        }
    }

    public static void trackState(final String state, Map<String, Object> contextData) {
        final Map<String, Object> cdata = contextData != null ? new HashMap<>(contextData) : null;
        StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Analytics.1
            @Override // java.lang.Runnable
            public void run() {
                AnalyticsTrackState.trackState(state, cdata);
            }
        });
    }

    public static void trackAction(final String action, Map<String, Object> contextData) {
        final Map<String, Object> cdata = contextData != null ? new HashMap<>(contextData) : null;
        StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Analytics.2
            @Override // java.lang.Runnable
            public void run() {
                AnalyticsTrackAction.trackAction(action, cdata);
            }
        });
    }

    public static void trackLocation(final Location location, Map<String, Object> contextData) {
        final Map<String, Object> cdata = contextData != null ? new HashMap<>(contextData) : null;
        StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Analytics.3
            @Override // java.lang.Runnable
            public void run() {
                AnalyticsTrackLocation.trackLocation(location, cdata);
            }
        });
    }

    public static void trackBeacon(final String uuid, final String major, final String minor, final BEACON_PROXIMITY proximity, Map<String, Object> contextData) {
        final Map<String, Object> cdata = contextData != null ? new HashMap<>(contextData) : null;
        StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Analytics.4
            @Override // java.lang.Runnable
            public void run() {
                AnalyticsTrackBeacon.trackBeacon(uuid, major, minor, proximity, cdata);
            }
        });
    }

    public static void clearBeacon() {
        StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Analytics.5
            @Override // java.lang.Runnable
            public void run() {
                AnalyticsTrackBeacon.clearBeacon();
            }
        });
    }

    public static void trackLifetimeValueIncrease(final BigDecimal amount, final HashMap<String, Object> contextData) {
        StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Analytics.6
            @Override // java.lang.Runnable
            public void run() {
                AnalyticsTrackLifetimeValueIncrease.trackLifetimeValueIncrease(amount, contextData);
            }
        });
    }

    public static void trackTimedActionStart(final String action, Map<String, Object> contextData) {
        final Map<String, Object> cdata = contextData != null ? new HashMap<>(contextData) : null;
        StaticMethods.getTimedActionsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Analytics.7
            @Override // java.lang.Runnable
            public void run() {
                AnalyticsTrackTimedAction.sharedInstance().trackTimedActionStart(action, cdata);
            }
        });
    }

    public static void trackTimedActionUpdate(final String action, Map<String, Object> contextData) {
        final Map<String, Object> cdata = contextData != null ? new HashMap<>(contextData) : null;
        StaticMethods.getTimedActionsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Analytics.8
            @Override // java.lang.Runnable
            public void run() {
                AnalyticsTrackTimedAction.sharedInstance().trackTimedActionUpdate(action, cdata);
            }
        });
    }

    public static void trackTimedActionEnd(final String action, final TimedActionBlock<Boolean> logic) {
        StaticMethods.getTimedActionsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Analytics.9
            @Override // java.lang.Runnable
            public void run() {
                AnalyticsTrackTimedAction.sharedInstance().trackTimedActionEnd(action, logic);
            }
        });
    }

    public static boolean trackingTimedActionExists(String action) {
        return AnalyticsTrackTimedAction.sharedInstance().trackTimedActionExists(action);
    }

    public static void processReferrer(Context context, final Intent intent) {
        StaticMethods.setSharedContext(context);
        StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Analytics.10
            @Override // java.lang.Runnable
            public void run() {
                ReferrerHandler.processIntent(intent);
            }
        });
    }

    public static String getTrackingIdentifier() {
        FutureTask<String> f = new FutureTask<>(new Callable<String>() { // from class: com.adobe.mobile.Analytics.11
            @Override // java.util.concurrent.Callable
            public String call() throws Exception {
                return StaticMethods.getAID();
            }
        });
        StaticMethods.getAnalyticsExecutor().execute(f);
        try {
            return f.get();
        } catch (Exception e) {
            StaticMethods.logErrorFormat("Analytics - Unable to get TrackingIdentifier (%s)", e.getMessage());
            return null;
        }
    }

    public static long getQueueSize() {
        FutureTask<Long> f = new FutureTask<>(new Callable<Long>() { // from class: com.adobe.mobile.Analytics.12
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.concurrent.Callable
            public Long call() throws Exception {
                return Long.valueOf(AnalyticsWorker.sharedInstance().getTrackingQueueSize());
            }
        });
        StaticMethods.getAnalyticsExecutor().execute(f);
        try {
            return f.get().longValue();
        } catch (Exception e) {
            StaticMethods.logErrorFormat("Analytics - Unable to get QueueSize (%s)", e.getMessage());
            return 0L;
        }
    }

    public static void clearQueue() {
        StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Analytics.13
            @Override // java.lang.Runnable
            public void run() {
                AnalyticsWorker.sharedInstance().clearTrackingQueue();
            }
        });
    }

    public static void sendQueuedHits() {
        StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Analytics.14
            @Override // java.lang.Runnable
            public void run() {
                AnalyticsWorker.sharedInstance().forceKick();
            }
        });
    }
}
