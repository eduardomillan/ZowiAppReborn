package com.adobe.mobile;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
final class GoogleApiClientWrapper {
    GoogleApiClientWrapper() {
    }

    protected static void disconnect(GoogleApiClient client) {
        try {
            Method disconnectMethod = GoogleApiClient.class.getDeclaredMethod("disconnect", new Class[0]);
            disconnectMethod.invoke(client, new Object[0]);
        } catch (Exception e) {
            StaticMethods.logDebugFormat("Wearable - Unable to call GoogleApiClient.disconnect() method (%s)", e.getLocalizedMessage());
        }
    }

    protected static void connect(GoogleApiClient client) {
        try {
            Method connectMethod = GoogleApiClient.class.getDeclaredMethod("connect", new Class[0]);
            connectMethod.invoke(client, new Object[0]);
        } catch (Exception e) {
            StaticMethods.logDebugFormat("Wearable - Unable to call GoogleApiClient.connect() method (%s)", e.getLocalizedMessage());
        }
    }

    protected static Boolean isConnected(GoogleApiClient client) {
        try {
            Method connectMethod = GoogleApiClient.class.getDeclaredMethod("isConnected", new Class[0]);
            Object result = connectMethod.invoke(client, new Object[0]);
            return Boolean.valueOf(result instanceof Boolean ? ((Boolean) result).booleanValue() : false);
        } catch (Exception e) {
            StaticMethods.logDebugFormat("Wearable - Unable to call GoogleApiClient.isConnected() method (%s)", e.getLocalizedMessage());
            return false;
        }
    }

    protected static ConnectionResult blockingConnect(GoogleApiClient client, long timeout, TimeUnit timeunit) {
        try {
            Method blockingConnectMethod = GoogleApiClient.class.getDeclaredMethod("blockingConnect", Long.TYPE, TimeUnit.class);
            Object result = blockingConnectMethod.invoke(client, Long.valueOf(timeout), timeunit);
            if (result instanceof ConnectionResult) {
                return (ConnectionResult) result;
            }
        } catch (Exception e) {
            StaticMethods.logDebugFormat("Wearable - Unable to call GoogleApiClient.blockingConnect() method (%s)", e.getLocalizedMessage());
        }
        return null;
    }

    protected static Result await(PendingResult pendingResult) {
        try {
            Method awaitMethod = PendingResult.class.getDeclaredMethod("await", new Class[0]);
            Object result = awaitMethod.invoke(pendingResult, new Object[0]);
            if (result instanceof Result) {
                return (Result) result;
            }
        } catch (Exception e) {
            StaticMethods.logDebugFormat("Wearable - Unable to call PendingResult.await() method (%s)", e.getLocalizedMessage());
        }
        return null;
    }
}
