package com.bq.zowi.interactors;

import android.bluetooth.BluetoothDevice;
import com.bq.zowi.controllers.BTAdapterController;
import com.bq.zowi.utils.Grove;
import rx.Observable;
import rx.Single;
import rx.functions.Func1;

/* JADX INFO: loaded from: classes.dex */
public class FindZowisInteractorImpl implements FindZowisInteractor {
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
                if (bluetoothDevice != null && bluetoothDevice.getName() != null && bluetoothDevice.getAddress() != null) {
                    Grove.d("Device found! " + bluetoothDevice.getName(), new Object[0]);
                    if (bluetoothDevice.getName().startsWith("Zowi")) {
                        if (bluetoothDevice.getAddress().startsWith("B4:9D:0B:3")) {
                            return true;
                        }
                        return true;
                    }
                }
                return false;
            }
        }).first().toSingle();
    }

    @Override // com.bq.zowi.interactors.FindZowisInteractor
    public void stopFindingZowis() {
        this.btAdapterController.stopBTDiscovery();
    }
}
