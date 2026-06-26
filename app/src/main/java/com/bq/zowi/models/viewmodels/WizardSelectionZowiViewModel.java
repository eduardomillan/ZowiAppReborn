package com.bq.zowi.models.viewmodels;

/* JADX INFO: loaded from: classes.dex */
public class WizardSelectionZowiViewModel {
    private String deviceAddress;
    private boolean isConnecting = false;
    private boolean selected;
    private String zowiName;

    public WizardSelectionZowiViewModel(String deviceAddress, boolean selected, String zowiName) {
        this.selected = false;
        this.deviceAddress = deviceAddress;
        this.selected = selected;
        this.zowiName = zowiName;
    }

    public String getDeviceAddress() {
        return this.deviceAddress;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public String getZowiName() {
        return this.zowiName;
    }

    public boolean isConnecting() {
        return this.isConnecting;
    }

    public void setIsConnecting(boolean isConnecting) {
        this.isConnecting = isConnecting;
    }
}
