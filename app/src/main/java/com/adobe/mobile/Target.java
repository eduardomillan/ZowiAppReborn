package com.adobe.mobile;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/* JADX INFO: loaded from: classes.dex */
public final class Target {
    private static final String NO_TARGET_MESSAGE = "Target - ADBMobile is not configured correctly to use Target.";

    public interface TargetCallback<T> {
        void call(T t);
    }

    public static void loadRequest(TargetLocationRequest request, TargetCallback<String> callback) {
        if (StaticMethods.isWearableApp()) {
            StaticMethods.logWarningFormat("Target - Method loadRequest is not available for Wearable", new Object[0]);
        } else {
            TargetWorker.loadRequest(request, callback);
        }
    }

    public static TargetLocationRequest createRequest(String name, String defaultContent, Map<String, Object> parameters) {
        return new TargetLocationRequest(name, defaultContent, parameters);
    }

    public static TargetLocationRequest createOrderConfirmRequest(String name, String orderId, String orderTotal, String productPurchasedId, Map<String, Object> parameters) {
        return TargetLocationRequest.createRequestWithOrderConfirm(name, orderId, orderTotal, productPurchasedId, parameters);
    }

    public static void clearCookies() {
        StaticMethods.getAnalyticsExecutor().execute(new Runnable() { // from class: com.adobe.mobile.Target.1
            @Override // java.lang.Runnable
            public void run() {
                TargetWorker.clearCookies();
            }
        });
    }

    public static String getPcID() {
        FutureTask<String> f = new FutureTask<>(new Callable<String>() { // from class: com.adobe.mobile.Target.2
            @Override // java.util.concurrent.Callable
            public String call() throws Exception {
                return TargetWorker.getPcID();
            }
        });
        StaticMethods.getAnalyticsExecutor().execute(f);
        try {
            return f.get();
        } catch (Exception e) {
            StaticMethods.logErrorFormat("Target - Unable to get PcID (%s)", e.getMessage());
            return null;
        }
    }

    public static String getSessionID() {
        FutureTask<String> f = new FutureTask<>(new Callable<String>() { // from class: com.adobe.mobile.Target.3
            @Override // java.util.concurrent.Callable
            public String call() throws Exception {
                return TargetWorker.getSessionID();
            }
        });
        StaticMethods.getAnalyticsExecutor().execute(f);
        try {
            return f.get();
        } catch (Exception e) {
            StaticMethods.logErrorFormat("Target - Unable to get SessionID (%s)", e.getMessage());
            return null;
        }
    }
}
