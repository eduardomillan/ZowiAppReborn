package com.comscore.android.id;

/* JADX INFO: loaded from: classes.dex */
public class DeviceId {
    private String a;
    private int b;
    private int c;

    public DeviceId(String str) {
        this.a = str;
        this.b = 0;
        this.c = 0;
    }

    public DeviceId(String str, int i, int i2) {
        this.a = str;
        this.b = i;
        this.c = i2;
    }

    public int getCommonness() {
        return this.b;
    }

    public String getId() {
        return this.a;
    }

    public int getPersistency() {
        return this.c;
    }

    public String getSuffix() {
        return getCommonness() + "" + getPersistency();
    }
}
