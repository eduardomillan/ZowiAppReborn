package com.bq.zowi.interactors;

import android.bluetooth.BluetoothDevice;
import rx.Single;

/* JADX INFO: loaded from: classes.dex */
public interface FindZowisInteractor {
    Single<BluetoothDevice> findZowis();

    void stopFindingZowis();
}
