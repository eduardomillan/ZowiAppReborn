package com.bq.error_reporting;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: classes.dex */
public interface ErrorReporter {
    public static final String DEFAULT_CATEGORY_NAME = "extras";

    public enum Level {
        INFO,
        WARNING,
        ERROR
    }

    void init();

    void leaveBreadcrumb(String str);

    void logHandledException(Throwable th, Level level, Metadata metadata);

    void logHandledExceptionBlocking(Throwable th, Level level, Metadata metadata);

    void setReleaseStage(String str);

    void setUserData(String str, String str2, String str3);

    public static class Metadata {
        private final ConcurrentHashMap<String, Map<String, Object>> metadataStorage = new ConcurrentHashMap<>();

        public void addMetadata(String category, @NotNull String key, Object value) {
            Map<String, Object> categoryMetadataMap;
            String categoryToCheck = category == null ? ErrorReporter.DEFAULT_CATEGORY_NAME : category;
            if (this.metadataStorage.containsKey(categoryToCheck)) {
                categoryMetadataMap = this.metadataStorage.get(categoryToCheck);
            } else {
                categoryMetadataMap = new HashMap<>();
                this.metadataStorage.put(categoryToCheck, categoryMetadataMap);
            }
            categoryMetadataMap.put(key, value);
        }

        public boolean removeMetadata(String category, @NotNull String key) {
            Map<String, Object> categoryMetadataMap;
            String categoryToCheck = category == null ? ErrorReporter.DEFAULT_CATEGORY_NAME : category;
            if (this.metadataStorage.containsKey(categoryToCheck)) {
                categoryMetadataMap = this.metadataStorage.get(categoryToCheck);
            } else {
                categoryMetadataMap = new HashMap<>();
                this.metadataStorage.put(categoryToCheck, categoryMetadataMap);
            }
            return categoryMetadataMap.remove(key) != null;
        }

        public boolean removeCategoryMetadata(String category) {
            return this.metadataStorage.remove(category) != null;
        }

        public Set<Map.Entry<String, Map<String, Object>>> entrySet() {
            return this.metadataStorage.entrySet();
        }
    }
}
