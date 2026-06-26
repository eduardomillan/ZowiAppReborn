package com.bugsnag.android;

import android.content.Context;

/* JADX INFO: loaded from: classes.dex */
public final class Bugsnag {
    private static Client client;

    private Bugsnag() {
    }

    public static Client init(Context androidContext) {
        Client client2 = new Client(androidContext);
        client = client2;
        return client2;
    }

    public static Client init(Context androidContext, String apiKey) {
        Client client2 = new Client(androidContext, apiKey);
        client = client2;
        return client2;
    }

    public static Client init(Context androidContext, String apiKey, boolean enableExceptionHandler) {
        Client client2 = new Client(androidContext, apiKey, enableExceptionHandler);
        client = client2;
        return client2;
    }

    public static void setAppVersion(String appVersion) {
        getClient().setAppVersion(appVersion);
    }

    public static void setContext(String context) {
        getClient().setContext(context);
    }

    public static void setEndpoint(String endpoint) {
        getClient().setEndpoint(endpoint);
    }

    public static void setBuildUUID(String buildUUID) {
        getClient().setBuildUUID(buildUUID);
    }

    public static void setFilters(String... filters) {
        getClient().setFilters(filters);
    }

    public static void setIgnoreClasses(String... ignoreClasses) {
        getClient().setIgnoreClasses(ignoreClasses);
    }

    public static void setNotifyReleaseStages(String... notifyReleaseStages) {
        getClient().setNotifyReleaseStages(notifyReleaseStages);
    }

    public static void setProjectPackages(String... projectPackages) {
        getClient().setProjectPackages(projectPackages);
    }

    public static void setReleaseStage(String releaseStage) {
        getClient().setReleaseStage(releaseStage);
    }

    public static void setSendThreads(boolean sendThreads) {
        getClient().setSendThreads(sendThreads);
    }

    public static void setUser(String id, String email, String name) {
        getClient().setUser(id, email, name);
    }

    public static void setUserId(String id) {
        getClient().setUserId(id);
    }

    public static void setUserEmail(String email) {
        getClient().setUserEmail(email);
    }

    public static void setUserName(String name) {
        getClient().setUserName(name);
    }

    public static void beforeNotify(BeforeNotify beforeNotify) {
        getClient().beforeNotify(beforeNotify);
    }

    public static void notify(Throwable exception) {
        getClient().notify(exception);
    }

    public static void notify(Throwable exception, Severity severity) {
        getClient().notify(exception, severity);
    }

    public static void notify(Throwable exception, MetaData metaData) {
        getClient().notify(exception, metaData);
    }

    public static void notify(Throwable exception, Severity severity, MetaData metaData) {
        getClient().notify(exception, severity, metaData);
    }

    public static void notify(String name, String message, StackTraceElement[] stacktrace, Severity severity, MetaData metaData) {
        getClient().notify(name, message, stacktrace, severity, metaData);
    }

    public static void notify(String name, String message, String context, StackTraceElement[] stacktrace, Severity severity, MetaData metaData) {
        getClient().notify(name, message, context, stacktrace, severity, metaData);
    }

    public static void addToTab(String tab, String key, Object value) {
        getClient().addToTab(tab, key, value);
    }

    public static void clearTab(String tabName) {
        getClient().clearTab(tabName);
    }

    public static MetaData getMetaData() {
        return getClient().getMetaData();
    }

    public static void setMetaData(MetaData metaData) {
        getClient().setMetaData(metaData);
    }

    public static void leaveBreadcrumb(String message) {
        getClient().leaveBreadcrumb(message);
    }

    public final void setMaxBreadcrumbs(int numBreadcrumbs) {
        getClient().setMaxBreadcrumbs(numBreadcrumbs);
    }

    public final void clearBreadcrumbs() {
        getClient().clearBreadcrumbs();
    }

    public static void enableExceptionHandler() {
        getClient().enableExceptionHandler();
    }

    public final void disableExceptionHandler() {
        getClient().disableExceptionHandler();
    }

    public static Client getClient() {
        if (client == null) {
            throw new IllegalStateException("You must call Bugsnag.init before any other Bugsnag methods");
        }
        return client;
    }
}
