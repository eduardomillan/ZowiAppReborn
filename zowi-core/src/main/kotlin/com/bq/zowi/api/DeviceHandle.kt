package com.bq.zowi.api

/**
 * Platform-agnostic representation of a Bluetooth device.
 * Replaces Android's [android.bluetooth.BluetoothDevice] in the core module.
 */
data class DeviceHandle(
    val name: String?,
    val address: String
)
