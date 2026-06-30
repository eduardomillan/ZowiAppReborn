package com.bq.zowi.interactors;

import android.bluetooth.BluetoothDevice;
import android.util.Log;
import com.bq.zowi.BuildConfig;
import com.bq.zowi.controllers.BTAdapterController;
import com.bq.zowi.utils.Grove;
import rx.Observable;
import rx.Single;
import rx.functions.Func1;

/* JADX INFO: loaded from: classes.dex */
public class FindZowisInteractorImpl implements FindZowisInteractor {
    private static final String BT_LOG_TAG = "ZowiBT";
    private final String ZOWI_DEVICE_PREFIX = "Zowi";
    private final String ZOWI_MAC_HEADER = "B4:9D:0B:3";
    private BTAdapterController btAdapterController;

    public FindZowisInteractorImpl(BTAdapterController btAdapterController) {
        this.btAdapterController = btAdapterController;
    }

    @Override // com.bq.zowi.interactors.FindZowisInteractor
    public Single<BluetoothDevice> findZowis() {
        return this.btAdapterController.enableBluetooth().flatMapObservable(new Func1<Void, Observable<BluetoothDevice>>() { // from class: com.bq.zowi.interactors.FindZowisInteractorImpl.2
            @Override // rx.functions.Func1
            public Observable<BluetoothDevice> call(Void aVoid) {
                return FindZowisInteractorImpl.this.btAdapterController.startBTDiscovery();
            }
        }).filter(new Func1<BluetoothDevice, Boolean>() { // from class: com.bq.zowi.interactors.FindZowisInteractorImpl.1
            @Override // rx.functions.Func1
            public Boolean call(BluetoothDevice bluetoothDevice) {
                if (bluetoothDevice == null || bluetoothDevice.getAddress() == null) {
                    return false;
                }
                String deviceName = bluetoothDevice.getName();
                String deviceAddress = bluetoothDevice.getAddress();
                Grove.d("Device found! name=" + deviceName + " address=" + deviceAddress, new Object[0]);
                boolean isZowiName = deviceName != null && deviceName.startsWith("Zowi");
                boolean isZowiMac = deviceAddress.startsWith("B4:9D:0B:3");
                if (BuildConfig.DEBUG) {
                    Log.d(BT_LOG_TAG, "Filter candidate name=" + deviceName + " address=" + deviceAddress + " matchName=" + isZowiName + " matchMac=" + isZowiMac);
                }
                return Boolean.valueOf(isZowiName || isZowiMac);
            }
        }).first().toSingle();
    }

    @Override // com.bq.zowi.interactors.FindZowisInteractor
    public void stopFindingZowis() {
        this.btAdapterController.stopBTDiscovery();
    }
}
