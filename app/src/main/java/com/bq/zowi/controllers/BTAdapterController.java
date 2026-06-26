package com.bq.zowi.controllers;

import android.bluetooth.BluetoothDevice;
import rx.Observable;
import rx.Single;

/* JADX INFO: loaded from: classes.dex */
public interface BTAdapterController {

    public static class BluetoothNotDisabledException extends IllegalStateException {
    }

    public static class BluetoothNotEnabledException extends IllegalStateException {
    }

    Single<Void> disableBluetooth();

    Single<Void> enableBluetooth();

    BluetoothDevice getRemoteDevice(String str);

    Observable<BluetoothDevice> startBTDiscovery();

    void stopBTDiscovery();
}
