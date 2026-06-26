package com.bugsnag.android;

import java.util.Collection;
import java.util.LinkedList;

/* JADX INFO: loaded from: classes.dex */
final class Configuration {
    String apiKey;
    String appVersion;
    String buildUUID;
    String context;
    String[] ignoreClasses;
    String[] projectPackages;
    String releaseStage;
    String endpoint = "https://notify.bugsnag.com";
    String[] filters = {"password"};
    String[] notifyReleaseStages = null;
    boolean sendThreads = true;
    MetaData metaData = new MetaData();
    Collection<BeforeNotify> beforeNotifyTasks = new LinkedList();

    Configuration(String apiKey) {
        this.apiKey = apiKey;
    }

    final boolean inProject(String className) {
        if (this.projectPackages == null) {
            return false;
        }
        for (String packageName : this.projectPackages) {
            if (packageName != null && className.startsWith(packageName)) {
                return true;
            }
        }
        return false;
    }
}
