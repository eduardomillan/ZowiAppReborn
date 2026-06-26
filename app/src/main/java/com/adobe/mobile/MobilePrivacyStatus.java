package com.adobe.mobile;

/* JADX INFO: loaded from: classes.dex */
public enum MobilePrivacyStatus {
    MOBILE_PRIVACY_STATUS_OPT_IN(0),
    MOBILE_PRIVACY_STATUS_OPT_OUT(1),
    MOBILE_PRIVACY_STATUS_UNKNOWN(2);

    private final int value;

    MobilePrivacyStatus(int value) {
        this.value = value;
    }

    protected int getValue() {
        return this.value;
    }
}
