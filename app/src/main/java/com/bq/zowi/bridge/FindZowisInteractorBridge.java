package com.bq.zowi.bridge;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import com.bq.zowi.api.DeviceHandle;
import rx.Single;

/**
 * Bridge from core (RxJava2) FindZowisInteractor to old (RxJava1) interface.
 * Converts {@link DeviceHandle} back to {@link BluetoothDevice} for presenter compatibility.
 */
public class FindZowisInteractorBridge implements com.bq.zowi.interactors.FindZowisInteractor {
    private final com.bq.zowi.usecases.FindZowisInteractor core;
    private final BluetoothAdapter bluetoothAdapter;

    public FindZowisInteractorBridge(com.bq.zowi.usecases.FindZowisInteractor core, BluetoothAdapter bluetoothAdapter) {
        this.core = core;
        this.bluetoothAdapter = bluetoothAdapter;
    }

    @Override
    public Single<BluetoothDevice> findZowis() {
        return RxBridge.toRxSingle(
            core.findZowis().map(handle -> bluetoothAdapter.getRemoteDevice(handle.getAddress()))
        );
    }

    @Override
    public void stopFindingZowis() {
        core.stopFindingZowis();
    }
}
