package com.bq.zowi.api

import io.reactivex.Observable
import io.reactivex.Single
import java.io.InputStream
import java.io.OutputStream

interface BTConnectionController {
    fun connect(device: DeviceHandle, duplex: Boolean): Single<Void>
    fun enabledReadingIncomingData()
    fun getBTInputStream(): InputStream
    fun getBTOutputStream(): OutputStream
    fun getConnectionStatusObservable(): Observable<Int>
    fun getReceivedMessageObservable(): Observable<String>
    fun isConnected(): Boolean
    fun isSendingHexToZowi(): Boolean
    fun sendHexToZowi(inputStream: InputStream): Observable<Int>
    fun sendMessage(message: String)
    fun stopConnection()
}
