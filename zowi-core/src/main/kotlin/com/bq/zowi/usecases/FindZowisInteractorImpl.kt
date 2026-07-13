package com.bq.zowi.usecases

import com.bq.zowi.api.BTAdapterController
import com.bq.zowi.api.DeviceHandle
import com.bq.zowi.utils.Grove
import io.reactivex.Single

class FindZowisInteractorImpl(
    private val btAdapterController: BTAdapterController
) : FindZowisInteractor {

    override fun findZowis(): Single<DeviceHandle> {
        return btAdapterController.enableBluetooth()
            .flatMapObservable { btAdapterController.startBTDiscovery() }
            .filter { device ->
                if (device.address.isEmpty()) return@filter false
                val deviceName = device.name
                val deviceAddress = device.address
                Grove.d("Device found! name=$deviceName address=$deviceAddress")
                val isZowiName = deviceName?.startsWith("Zowi") == true
                val isZowiMac = deviceAddress.startsWith("B4:9D:0B:3")
                isZowiName || isZowiMac
            }
            .firstOrError()
    }

    override fun stopFindingZowis() {
        btAdapterController.stopBTDiscovery()
    }
}
