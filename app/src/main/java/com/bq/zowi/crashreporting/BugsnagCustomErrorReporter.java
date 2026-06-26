package com.bq.zowi.crashreporting;

import android.content.Context;
import com.bq.error_reporting.BugsnagErrorReporterImpl;
import com.bq.error_reporting.ErrorReporter;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes.dex */
public class BugsnagCustomErrorReporter implements CustomErrorReporter {
    private final BugsnagErrorReporterImpl instance;

    public BugsnagCustomErrorReporter(@NotNull Context context, @NotNull String apiKey, HashMap<String, String> defaultMetadata) {
        this.instance = new BugsnagErrorReporterImpl(context, apiKey, getMetadataFromHashmap(defaultMetadata));
    }

    @Override // com.bq.zowi.crashreporting.CustomErrorReporter
    public void init() {
        this.instance.init();
    }

    @Override // com.bq.zowi.crashreporting.CustomErrorReporter
    public void setUserData(String userId, String userName, String userEmail) {
        this.instance.setUserData(userId, userName, userEmail);
    }

    @Override // com.bq.zowi.crashreporting.CustomErrorReporter
    public void setReleaseStage(String releaseStage) {
        this.instance.setReleaseStage(releaseStage);
    }

    @Override // com.bq.zowi.crashreporting.CustomErrorReporter
    public void logHandledException(Throwable throwable, HashMap<String, String> metadata) {
        this.instance.logHandledException(throwable, ErrorReporter.Level.WARNING, getMetadataFromHashmap(metadata));
    }

    @Override // com.bq.zowi.crashreporting.CustomErrorReporter
    public void leaveBreadcrumb(String message) {
        this.instance.leaveBreadcrumb(message);
    }

    private static ErrorReporter.Metadata getMetadataFromHashmap(HashMap<String, String> defaultMetadata) {
        ErrorReporter.Metadata metadata = new ErrorReporter.Metadata();
        if (defaultMetadata != null) {
            for (Map.Entry<String, String> entry : defaultMetadata.entrySet()) {
                metadata.addMetadata(null, entry.getKey(), entry.getValue());
            }
        }
        return metadata;
    }
}
