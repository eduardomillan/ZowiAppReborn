package com.adobe.mobile;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class AnalyticsTimedAction {
    protected long adjustedStartTime;
    protected Map<String, Object> contextData;
    protected int databaseID;
    protected long startTime;

    protected AnalyticsTimedAction(Map<String, Object> contextData, long startTime, long adjustedStartTime, int databaseID) {
        this.contextData = contextData;
        this.databaseID = databaseID;
        this.startTime = startTime;
        this.adjustedStartTime = adjustedStartTime;
    }
}
