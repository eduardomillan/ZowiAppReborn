package com.bugsnag.android;

import android.text.TextUtils;
import com.bugsnag.android.JsonStream;
import java.io.IOException;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class Error implements JsonStream.Streamable {
    private AppData appData;
    private AppState appState;
    private Breadcrumbs breadcrumbs;
    private Configuration config;
    private String context;
    private DeviceData deviceData;
    private DeviceState deviceState;
    private Throwable exception;
    private StackTraceElement[] frames;
    private String groupingHash;
    private String message;
    private String name;
    private User user;
    private Severity severity = Severity.WARNING;
    private MetaData metaData = new MetaData();

    Error(Configuration config, Throwable exception) {
        this.config = config;
        this.exception = exception;
    }

    Error(Configuration config, String name, String message, StackTraceElement[] frames) {
        this.config = config;
        this.name = name;
        this.message = message;
        this.frames = frames;
    }

    @Override // com.bugsnag.android.JsonStream.Streamable
    public void toStream(JsonStream writer) throws IOException {
        MetaData mergedMetaData = MetaData.merge(this.config.metaData, this.metaData);
        mergedMetaData.setFilters(this.config.filters);
        writer.beginObject();
        writer.name("payloadVersion").value("2");
        writer.name("context").value(getContext());
        this.severity.toStream(writer.name("severity"));
        mergedMetaData.toStream(writer.name("metaData"));
        if (this.config.projectPackages != null) {
            writer.name("projectPackages").beginArray();
            for (String projectPackage : this.config.projectPackages) {
                writer.value(projectPackage);
            }
            writer.endArray();
        }
        if (this.exception != null) {
            new Exceptions(this.config, this.exception).toStream(writer.name("exceptions"));
        } else {
            new Exceptions(this.config, this.name, this.message, this.frames).toStream(writer.name("exceptions"));
        }
        if (this.user != null) {
            this.user.toStream(writer.name("user"));
        }
        if (this.appData != null) {
            this.appData.toStream(writer.name("app"));
        }
        if (this.appState != null) {
            this.appState.toStream(writer.name("appState"));
        }
        if (this.deviceData != null) {
            this.deviceData.toStream(writer.name("device"));
        }
        if (this.deviceState != null) {
            this.deviceState.toStream(writer.name("deviceState"));
        }
        if (this.breadcrumbs != null) {
            this.breadcrumbs.toStream(writer.name("breadcrumbs"));
        }
        if (this.groupingHash != null) {
            writer.name("groupingHash").value(this.groupingHash);
        }
        if (this.config.sendThreads) {
            new ThreadState(this.config).toStream(writer.name("threads"));
        }
        writer.endObject();
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getContext() {
        if (this.context != null && !TextUtils.isEmpty(this.context)) {
            return this.context;
        }
        if (this.config.context != null) {
            return this.config.context;
        }
        if (this.appState != null) {
            return this.appState.getActiveScreenClass();
        }
        return null;
    }

    public void setGroupingHash(String groupingHash) {
        this.groupingHash = groupingHash;
    }

    public void setSeverity(Severity severity) {
        if (severity != null) {
            this.severity = severity;
        }
    }

    public Severity getSeverity() {
        return this.severity;
    }

    public void setUser(String id, String email, String name) {
        this.user = new User(id, email, name);
    }

    public void setUserId(String id) {
        this.user = new User(this.user);
        this.user.setId(id);
    }

    public void setUserEmail(String email) {
        this.user = new User(this.user);
        this.user.setEmail(email);
    }

    public void setUserName(String name) {
        this.user = new User(this.user);
        this.user.setName(name);
    }

    public void addToTab(String tabName, String key, Object value) {
        this.metaData.addToTab(tabName, key, value);
    }

    public void clearTab(String tabName) {
        this.metaData.clearTab(tabName);
    }

    public MetaData getMetaData() {
        return this.metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public String getExceptionName() {
        return this.exception != null ? this.exception.getClass().getName() : this.name;
    }

    public String getExceptionMessage() {
        return this.exception != null ? this.exception.getLocalizedMessage() : this.message;
    }

    public Throwable getException() {
        return this.exception;
    }

    final void setAppData(AppData appData) {
        this.appData = appData;
    }

    final void setDeviceData(DeviceData deviceData) {
        this.deviceData = deviceData;
    }

    final void setAppState(AppState appState) {
        this.appState = appState;
    }

    final void setDeviceState(DeviceState deviceState) {
        this.deviceState = deviceState;
    }

    final void setUser(User user) {
        this.user = user;
    }

    final void setBreadcrumbs(Breadcrumbs breadcrumbs) {
        this.breadcrumbs = breadcrumbs;
    }

    final boolean shouldIgnoreClass() {
        Configuration configuration = this.config;
        String exceptionName = getExceptionName();
        if (configuration.ignoreClasses == null) {
            return false;
        }
        return Arrays.asList(configuration.ignoreClasses).contains(exceptionName);
    }
}
