package com.bq.zowi.api

import io.reactivex.Observable
import io.reactivex.Single

interface BTAdapterController {
    class BluetoothNotDisabledException : IllegalStateException()
    class BluetoothNotEnabledException : IllegalStateException()

    fun disableBluetooth(): Single<Void>
    fun enableBluetooth(): Single<Void>
    fun getRemoteDevice(address: String): DeviceHandle
    fun startBTDiscovery(): Observable<DeviceHandle>
    fun stopBTDiscovery()
}
