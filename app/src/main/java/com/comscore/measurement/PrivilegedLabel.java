package com.comscore.measurement;

/* JADX INFO: loaded from: classes.dex */
public class PrivilegedLabel extends Label {
    private Boolean a;

    public PrivilegedLabel(String str, String str2, Boolean bool) {
        super(str, str2, bool);
        this.a = true;
    }

    public Boolean getPrivileged() {
        return this.a;
    }
}
