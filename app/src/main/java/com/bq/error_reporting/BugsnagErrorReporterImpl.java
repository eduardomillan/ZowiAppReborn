package com.bq.error_reporting;

import android.os.Build;
import android.content.Context;
import com.bq.error_reporting.ErrorReporter;
import com.bugsnag.android.Bugsnag;
import com.bugsnag.android.MetaData;
import com.bugsnag.android.Severity;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes.dex */
public class BugsnagErrorReporterImpl implements ErrorReporter {
    private String apiKey;
    private Context applicationContext;
    private ErrorReporter.Metadata defaultMetadata;
    private boolean enabled;

    public BugsnagErrorReporterImpl(@NotNull Context context, @NotNull String apiKey, ErrorReporter.Metadata defaultMetadata) {
        this.applicationContext = context.getApplicationContext();
        this.apiKey = apiKey;
        this.defaultMetadata = defaultMetadata;
        this.enabled = Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU;
    }

    @Override // com.bq.error_reporting.ErrorReporter
    public void init() {
        if (!this.enabled) {
            return;
        }
        Bugsnag.init(this.applicationContext, this.apiKey);
        Bugsnag.setMetaData(mapMetadata(this.defaultMetadata));
    }

    @Override // com.bq.error_reporting.ErrorReporter
    public void setUserData(String userId, String userName, String userEmail) {
        if (!this.enabled) {
            return;
        }
        if (userId != null) {
            Bugsnag.setUserId(userId);
        }
        if (userName != null) {
            Bugsnag.setUserName(userName);
        }
        if (userEmail != null) {
            Bugsnag.setUserEmail(userEmail);
        }
    }

    @Override // com.bq.error_reporting.ErrorReporter
    public void setReleaseStage(String releaseStage) {
        if (!this.enabled) {
            return;
        }
        Bugsnag.setReleaseStage(releaseStage);
    }

    @Override // com.bq.error_reporting.ErrorReporter
    public void logHandledException(Throwable throwable, ErrorReporter.Level errorLevel, ErrorReporter.Metadata metadata) {
        logHandledExceptionInternal(throwable, errorLevel, metadata, false);
    }

    @Override // com.bq.error_reporting.ErrorReporter
    public void logHandledExceptionBlocking(Throwable throwable, ErrorReporter.Level errorLevel, ErrorReporter.Metadata metadata) {
        logHandledExceptionInternal(throwable, errorLevel, metadata, true);
    }

    @Override // com.bq.error_reporting.ErrorReporter
    public void leaveBreadcrumb(String message) {
        if (!this.enabled) {
            return;
        }
        Bugsnag.leaveBreadcrumb(message);
    }

    private void logHandledExceptionInternal(final Throwable throwable, ErrorReporter.Level errorLevel, ErrorReporter.Metadata metadata, boolean blocking) {
        if (!this.enabled) {
            return;
        }
        final Severity severity = mapErrorLevel(errorLevel);
        final MetaData errorMetadata = mapMetadata(metadata);
        Thread notifyThread = new Thread(new Runnable() { // from class: com.bq.error_reporting.BugsnagErrorReporterImpl.1
            @Override // java.lang.Runnable
            public void run() {
                Bugsnag.getClient().notifyBlocking(throwable, severity, errorMetadata);
            }
        });
        notifyThread.start();
        if (blocking) {
            try {
                notifyThread.join();
            } catch (InterruptedException e) {
            }
        }
    }

    private Severity mapErrorLevel(ErrorReporter.Level errorLevel) {
        switch (errorLevel) {
            case INFO:
                return Severity.INFO;
            case WARNING:
                return Severity.WARNING;
            default:
                return Severity.ERROR;
        }
    }

    private MetaData mapMetadata(ErrorReporter.Metadata metadata) {
        MetaData bugsnagMetadata = new MetaData();
        if (metadata != null) {
            for (Map.Entry<String, Map<String, Object>> categoryEntry : metadata.entrySet()) {
                String categoryName = categoryEntry.getKey();
                Map<String, Object> categoryMap = categoryEntry.getValue();
                for (Map.Entry<String, Object> categoryPairEntry : categoryMap.entrySet()) {
                    String category = categoryName != null ? categoryName : ErrorReporter.DEFAULT_CATEGORY_NAME;
                    String key = categoryPairEntry.getKey();
                    Object value = categoryPairEntry.getValue();
                    bugsnagMetadata.addToTab(category, key, value == null ? "null" : value.toString());
                }
            }
        }
        return bugsnagMetadata;
    }
}
