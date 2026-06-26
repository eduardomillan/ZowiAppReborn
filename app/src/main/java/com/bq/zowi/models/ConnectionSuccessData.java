package com.bq.zowi.models;

/* JADX INFO: loaded from: classes.dex */
public class ConnectionSuccessData {
    private float batteryLevel;
    private String zowiAppId;
    private ZowiName zowiName;

    public ConnectionSuccessData(String zowiName, String zowiAppId, float batteryLevel) {
        this.zowiName = new ZowiName(zowiName);
        this.zowiAppId = zowiAppId;
        this.batteryLevel = batteryLevel;
    }

    public String getZowiName() {
        return this.zowiName.toString();
    }

    public String getZowiAppId() {
        return this.zowiAppId;
    }

    public float getBatteryLevel() {
        return this.batteryLevel;
    }
}
