package com.bq.zowi.cli.adapters

import com.bq.zowi.api.BTAdapterController
import com.bq.zowi.api.DeviceHandle
import com.fazecast.jSerialComm.SerialPort
import com.bq.zowi.utils.Grove
import io.reactivex.Observable
import io.reactivex.Single

class CliBtAdapterController : BTAdapterController {

    @Suppress("UNCHECKED_CAST")
    override fun disableBluetooth(): Single<Void> {
        return Single.fromCallable {
            Grove.d("CLI: disableBluetooth (no-op on serial)")
            null as Void
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun enableBluetooth(): Single<Void> {
        return Single.fromCallable {
            Grove.d("CLI: enableBluetooth (no-op on serial)")
            null as Void
        }
    }

    override fun getRemoteDevice(address: String): DeviceHandle {
        return DeviceHandle(name = null, address = address)
    }

    override fun startBTDiscovery(): Observable<DeviceHandle> {
        return Observable.create { emitter ->
            val ports = SerialPort.getCommPorts()
            ports.filter { isBluetoothPort(it) }.forEach { port ->
                emitter.onNext(
                    DeviceHandle(
                        name = port.descriptivePortName,
                        address = port.systemPortPath
                    )
                )
            }
            emitter.onComplete()
        }
    }

    override fun stopBTDiscovery() {
        Grove.d("CLI: stopBTDiscovery (no-op on serial)")
    }

    private fun isBluetoothPort(port: SerialPort): Boolean {
        val name = port.systemPortPath.lowercase()
        return name.contains("rfcomm") || name.contains("bluetooth") ||
                name.contains("bt") || name.contains("hc-0") ||
                name.contains("zowi")
    }

    companion object {
        fun listSerialPorts(): List<DeviceHandle> {
            return SerialPort.getCommPorts().map { port ->
                DeviceHandle(
                    name = port.descriptivePortName,
                    address = port.systemPortPath
                )
            }
        }
    }
}
