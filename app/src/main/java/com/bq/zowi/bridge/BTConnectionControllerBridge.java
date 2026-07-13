package com.bq.zowi.bridge;

import android.bluetooth.BluetoothDevice;
import com.bq.zowi.api.DeviceHandle;
import com.bq.zowi.controllers.BTConnectionController;
import java.io.InputStream;
import java.io.OutputStream;
import rx.Observable;
import rx.Single;

public class BTConnectionControllerBridge implements BTConnectionController {
    private final com.bq.zowi.api.BTConnectionController core;

    public BTConnectionControllerBridge(com.bq.zowi.api.BTConnectionController core) {
        this.core = core;
    }

    @Override
    public Single<Void> connect(BluetoothDevice bluetoothDevice, boolean duplex) {
        DeviceHandle handle = new DeviceHandle(bluetoothDevice.getName(), bluetoothDevice.getAddress());
        return RxBridge.toRxSingle(core.connect(handle, duplex));
    }

    @Override
    public void enabledReadingIncomingData() {
        core.enabledReadingIncomingData();
    }

    @Override
    public InputStream getBTInputStream() {
        return core.getBTInputStream();
    }

    @Override
    public OutputStream getBTOutputStream() {
        return core.getBTOutputStream();
    }

    @Override
    public Observable<Integer> getConnectionStatusObservable() {
        return RxBridge.toRxObservable(core.getConnectionStatusObservable());
    }

    @Override
    public Observable<String> getReceivedMessageObservable() {
        return RxBridge.toRxObservable(core.getReceivedMessageObservable());
    }

    @Override
    public boolean isConnected() {
        return core.isConnected();
    }

    @Override
    public boolean isSendingHexToZowi() {
        return core.isSendingHexToZowi();
    }

    @Override
    public Observable<Integer> sendHexToZowi(InputStream inputStream) {
        return RxBridge.toRxObservable(core.sendHexToZowi(inputStream));
    }

    @Override
    public void sendMessage(String message) {
        core.sendMessage(message);
    }

    @Override
    public void stopConnection() {
        core.stopConnection();
    }
}
