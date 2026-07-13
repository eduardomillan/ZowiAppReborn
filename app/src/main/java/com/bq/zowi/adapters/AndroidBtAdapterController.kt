package com.bq.zowi.adapters

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Context
import com.bq.zowi.api.BTAdapterController
import com.bq.zowi.api.DeviceHandle
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

class AndroidBtAdapterController(
    private val context: Context,
    private val btAdapter: BluetoothAdapter
) : BTAdapterController {

    @Suppress("UNCHECKED_CAST")
    override fun disableBluetooth(): Single<Void> {
        return Single.fromCallable {
            btAdapter.disable()
            null as Void
        }.subscribeOn(AndroidSchedulers.mainThread())
    }

    @Suppress("UNCHECKED_CAST")
    override fun enableBluetooth(): Single<Void> {
        return Single.fromCallable {
            btAdapter.enable()
            null as Void
        }.subscribeOn(AndroidSchedulers.mainThread())
    }

    override fun getRemoteDevice(address: String): DeviceHandle {
        val device = btAdapter.getRemoteDevice(address)
        return DeviceHandle(name = device.name, address = device.address)
    }

    override fun startBTDiscovery(): Observable<DeviceHandle> {
        return Observable.create { emitter ->
            val receiver = object : android.content.BroadcastReceiver() {
                override fun onReceive(ctx: Context, intent: android.content.Intent) {
                    when (intent.action) {
                        BluetoothDevice.ACTION_FOUND -> {
                            val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                            if (device != null) {
                                emitter.onNext(DeviceHandle(name = device.name, address = device.address))
                            }
                        }
                        BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                            emitter.onComplete()
                        }
                    }
                }
            }

            val filter = android.content.IntentFilter().apply {
                addAction(BluetoothDevice.ACTION_FOUND)
                addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
            }
            context.registerReceiver(receiver, filter)
            btAdapter.startDiscovery()

            emitter.setCancellable {
                try {
                    context.unregisterReceiver(receiver)
                    btAdapter.cancelDiscovery()
                } catch (e: Exception) {
                    // receiver may already be unregistered
                }
            }
        }
    }

    override fun stopBTDiscovery() {
        btAdapter.cancelDiscovery()
    }
}
