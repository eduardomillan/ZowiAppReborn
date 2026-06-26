package com.bugsnag.android;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.text.TextUtils;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class Client {
    private final Context appContext;
    private final AppData appData;
    private final Breadcrumbs breadcrumbs;
    private final Configuration config;
    private final DeviceData deviceData;
    private final ErrorStore errorStore;
    private final User user;

    public Client(Context androidContext) {
        this(androidContext, null);
    }

    public Client(Context androidContext, String apiKey) {
        this(androidContext, apiKey, true);
    }

    public Client(Context androidContext, String apiKey, boolean enableExceptionHandler) {
        this.user = new User();
        if (androidContext == null) {
            throw new NullPointerException("You must provide a non-null android Context");
        }
        this.appContext = androidContext.getApplicationContext();
        String buildUUID = null;
        if (TextUtils.isEmpty(apiKey)) {
            try {
                ApplicationInfo ai = this.appContext.getPackageManager().getApplicationInfo(this.appContext.getPackageName(), 128);
                apiKey = ai.metaData.getString("com.bugsnag.android.API_KEY");
                buildUUID = ai.metaData.getString("com.bugsnag.android.BUILD_UUID");
            } catch (Exception e) {
            }
        }
        if (apiKey == null) {
            throw new NullPointerException("You must provide a Bugsnag API key");
        }
        this.config = new Configuration(apiKey);
        if (buildUUID != null) {
            this.config.buildUUID = buildUUID;
        }
        this.appData = new AppData(this.appContext, this.config);
        this.deviceData = new DeviceData(this.appContext);
        AppState.init();
        this.breadcrumbs = new Breadcrumbs();
        setProjectPackages(this.appContext.getPackageName());
        setUserId(this.deviceData.getUserId());
        this.errorStore = new ErrorStore(this.config, this.appContext);
        if (enableExceptionHandler) {
            enableExceptionHandler();
        }
        this.errorStore.flush();
    }

    public void setAppVersion(String appVersion) {
        this.config.appVersion = appVersion;
    }

    public void setContext(String context) {
        this.config.context = context;
    }

    public void setEndpoint(String endpoint) {
        this.config.endpoint = endpoint;
    }

    public void setBuildUUID(String buildUUID) {
        this.config.buildUUID = buildUUID;
    }

    public void setFilters(String... filters) {
        this.config.filters = filters;
    }

    public void setIgnoreClasses(String... ignoreClasses) {
        this.config.ignoreClasses = ignoreClasses;
    }

    public void setNotifyReleaseStages(String... notifyReleaseStages) {
        this.config.notifyReleaseStages = notifyReleaseStages;
    }

    public void setProjectPackages(String... projectPackages) {
        this.config.projectPackages = projectPackages;
    }

    public void setReleaseStage(String releaseStage) {
        this.config.releaseStage = releaseStage;
    }

    public void setSendThreads(boolean sendThreads) {
        this.config.sendThreads = sendThreads;
    }

    public void setUser(String id, String email, String name) {
        this.user.setId(id);
        this.user.setEmail(email);
        this.user.setName(name);
    }

    public void setUserId(String id) {
        this.user.setId(id);
    }

    public void setUserEmail(String email) {
        this.user.setEmail(email);
    }

    public void setUserName(String name) {
        this.user.setName(name);
    }

    public void beforeNotify(BeforeNotify beforeNotify) {
        this.config.beforeNotifyTasks.add(beforeNotify);
    }

    public void notify(Throwable exception) {
        Error error = new Error(this.config, exception);
        notify(error, false);
    }

    public void notifyBlocking(Throwable exception) {
        Error error = new Error(this.config, exception);
        notify(error, true);
    }

    public void notify(Throwable exception, Severity severity) {
        Error error = new Error(this.config, exception);
        error.setSeverity(severity);
        notify(error, false);
    }

    public void notifyBlocking(Throwable exception, Severity severity) {
        Error error = new Error(this.config, exception);
        error.setSeverity(severity);
        notify(error, true);
    }

    public void notify(Throwable exception, MetaData metaData) {
        Error error = new Error(this.config, exception);
        error.setMetaData(metaData);
        notify(error, false);
    }

    public void notifyBlocking(Throwable exception, MetaData metaData) {
        Error error = new Error(this.config, exception);
        error.setMetaData(metaData);
        notify(error, true);
    }

    public void notify(Throwable exception, Severity severity, MetaData metaData) {
        Error error = new Error(this.config, exception);
        error.setSeverity(severity);
        error.setMetaData(metaData);
        notify(error, false);
    }

    public void notifyBlocking(Throwable exception, Severity severity, MetaData metaData) {
        Error error = new Error(this.config, exception);
        error.setSeverity(severity);
        error.setMetaData(metaData);
        notify(error, true);
    }

    public void notify(String name, String message, StackTraceElement[] stacktrace, Severity severity, MetaData metaData) {
        Error error = new Error(this.config, name, message, stacktrace);
        error.setSeverity(severity);
        error.setMetaData(metaData);
        notify(error, false);
    }

    public void notifyBlocking(String name, String message, StackTraceElement[] stacktrace, Severity severity, MetaData metaData) {
        Error error = new Error(this.config, name, message, stacktrace);
        error.setSeverity(severity);
        error.setMetaData(metaData);
        notify(error, true);
    }

    public void notify(String name, String message, String context, StackTraceElement[] stacktrace, Severity severity, MetaData metaData) {
        Error error = new Error(this.config, name, message, stacktrace);
        error.setSeverity(severity);
        error.setMetaData(metaData);
        error.setContext(context);
        notify(error, false);
    }

    public void notifyBlocking(String name, String message, String context, StackTraceElement[] stacktrace, Severity severity, MetaData metaData) {
        Error error = new Error(this.config, name, message, stacktrace);
        error.setSeverity(severity);
        error.setMetaData(metaData);
        error.setContext(context);
        notify(error, true);
    }

    public void addToTab(String tab, String key, Object value) {
        this.config.metaData.addToTab(tab, key, value);
    }

    public void clearTab(String tabName) {
        this.config.metaData.clearTab(tabName);
    }

    public MetaData getMetaData() {
        return this.config.metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.config.metaData = metaData;
    }

    public void leaveBreadcrumb(String breadcrumb) {
        this.breadcrumbs.add(breadcrumb);
    }

    public void setMaxBreadcrumbs(int numBreadcrumbs) {
        this.breadcrumbs.setSize(numBreadcrumbs);
    }

    public void clearBreadcrumbs() {
        this.breadcrumbs.clear();
    }

    public void enableExceptionHandler() {
        ExceptionHandler.enable(this);
    }

    public void disableExceptionHandler() {
        ExceptionHandler.disable(this);
    }

    private void notify(final Error error, boolean blocking) {
        if (!error.shouldIgnoreClass()) {
            Configuration configuration = this.config;
            if (configuration.notifyReleaseStages == null ? true : Arrays.asList(configuration.notifyReleaseStages).contains(this.appData.getReleaseStage())) {
                error.setAppData(this.appData);
                error.setDeviceData(this.deviceData);
                error.setAppState(new AppState(this.appContext));
                error.setDeviceState(new DeviceState(this.appContext));
                error.setBreadcrumbs(this.breadcrumbs);
                error.setUser(this.user);
                if (!runBeforeNotifyTasks(error)) {
                    AppData.info("Skipping notification - beforeNotify task returned false");
                    return;
                }
                final Notification notification = new Notification(this.config);
                notification.addError(error);
                if (blocking) {
                    deliver(notification, error);
                } else {
                    Async.run(new Runnable() { // from class: com.bugsnag.android.Client.1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Client.this.deliver(notification, error);
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void deliver(Notification notification, Error error) {
        try {
            int errorCount = notification.deliver();
            AppData.info(String.format("Sent %d new error(s) to Bugsnag", Integer.valueOf(errorCount)));
        } catch (HttpClient$BadResponseException e) {
            AppData.info("Bad response when sending data to Bugsnag");
        } catch (HttpClient$NetworkException e2) {
            AppData.info("Could not send error(s) to Bugsnag, saving to disk to send later");
            this.errorStore.write(error);
        } catch (Exception e3) {
            AppData.warn("Problem sending error to Bugsnag", e3);
        }
    }

    private boolean runBeforeNotifyTasks(Error error) {
        for (BeforeNotify beforeNotify : this.config.beforeNotifyTasks) {
            try {
            } catch (Throwable ex) {
                AppData.warn("BeforeNotify threw an Exception", ex);
            }
            if (!beforeNotify.run(error)) {
                return false;
            }
        }
        return true;
    }
}
