package com.bq.robotic.droid2ino.utils;

import android.widget.TextView;

/* JADX INFO: loaded from: classes.dex */
public class DeviceListDialogStyle {
    private TextView devicesPairedTitleView;
    private TextView newDevicesTitleView;
    private TextView searchDevicesTitleView;

    public DeviceListDialogStyle(TextView searchDevicesTitleView, TextView devicesPairedTitleView, TextView newDevicesTitleView) {
        this.searchDevicesTitleView = searchDevicesTitleView;
        this.devicesPairedTitleView = devicesPairedTitleView;
        this.newDevicesTitleView = newDevicesTitleView;
    }

    public TextView getSearchDevicesTitleView() {
        return this.searchDevicesTitleView;
    }

    public TextView getDevicesPairedTitleView() {
        return this.devicesPairedTitleView;
    }

    public TextView getNewDevicesTitleView() {
        return this.newDevicesTitleView;
    }
}
