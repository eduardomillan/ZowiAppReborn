package com.bq.robotic.droid2ino.activities;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseBluetoothSendOnlyActivity extends BaseBluetoothConnectionActivity {
    @Override // com.bq.robotic.droid2ino.activities.BaseBluetoothConnectionActivity
    public final void onNewMessage(String message) {
    }

    @Override // com.bq.robotic.droid2ino.activities.BaseBluetoothConnectionActivity
    protected void setupSession() {
        super.setupSession();
        this.mBluetoothConnection.setDuplexConnection(false);
    }
}
