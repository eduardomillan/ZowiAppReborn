package com.bq.zowi.controllers;

import android.bluetooth.BluetoothDevice;
import java.io.InputStream;
import java.io.OutputStream;
import rx.Observable;
import rx.Single;

/* JADX INFO: loaded from: classes.dex */
public interface BTConnectionController {
    Single<Void> connect(BluetoothDevice bluetoothDevice, boolean z);

    void enabledReadingIncomingData();

    InputStream getBTInputStream();

    OutputStream getBTOutputStream();

    Observable<Integer> getConnectionStatusObservable();

    Observable<String> getReceivedMessageObservable();

    boolean isConnected();

    boolean isSendingHexToZowi();

    Observable<Integer> sendHexToZowi(InputStream inputStream);

    void sendMessage(String str);

    void stopConnection();
}
