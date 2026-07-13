package com.bq.zowi.adapters

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.Context
import com.bq.zowi.api.BTConnectionController
import com.bq.zowi.api.DeviceHandle
import com.bq.zowi.utils.Grove
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

class AndroidBtConnectionController(
    private val context: Context,
    private val btAdapter: BluetoothAdapter
) : BTConnectionController {

    companion object {
        private val SPP_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
    }

    private var socket: BluetoothSocket? = null
    private var connected = false
    private var sendingHex = false
    private val connectionSubject = BehaviorSubject.createDefault(0)
    private val messageSubject = PublishSubject.create<String>()
    private var readerThread: Thread? = null

    @Suppress("UNCHECKED_CAST")
    override fun connect(device: DeviceHandle, duplex: Boolean): Single<Void> {
        return Single.fromCallable {
            val btDevice = btAdapter.getRemoteDevice(device.address)
            val sppSocket = btDevice.createRfcommSocketToServiceRecord(SPP_UUID)
            sppSocket.connect()

            socket = sppSocket
            connected = true
            connectionSubject.onNext(2)

            if (duplex) {
                startReading()
            }

            Grove.d("Connected to ${device.address}")
            null as Void
        }
    }

    override fun enabledReadingIncomingData() {
        startReading()
    }

    override fun getBTInputStream(): InputStream {
        return socket?.inputStream ?: throw IllegalStateException("Not connected")
    }

    override fun getBTOutputStream(): OutputStream {
        return socket?.outputStream ?: throw IllegalStateException("Not connected")
    }

    override fun getConnectionStatusObservable(): Observable<Int> = connectionSubject

    override fun getReceivedMessageObservable(): Observable<String> = messageSubject

    override fun isConnected(): Boolean = connected

    override fun isSendingHexToZowi(): Boolean = sendingHex

    override fun sendHexToZowi(inputStream: InputStream): Observable<Int> {
        return Observable.create { emitter ->
            try {
                sendingHex = true
                val output = getBTOutputStream()
                val buffer = ByteArray(1024)
                var bytesRead: Int
                var totalSent = 0L

                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    output.write(buffer, 0, bytesRead)
                    output.flush()
                    totalSent += bytesRead
                    emitter.onNext((totalSent / 100).toInt().coerceAtMost(99))
                }

                sendingHex = false
                emitter.onNext(100)
                emitter.onComplete()
            } catch (e: Exception) {
                sendingHex = false
                emitter.onError(e)
            }
        }
    }

    override fun sendMessage(message: String) {
        val output = getBTOutputStream()
        output.write(message.toByteArray())
        output.flush()
    }

    override fun stopConnection() {
        readerThread?.interrupt()
        readerThread = null
        try {
            socket?.close()
        } catch (e: IOException) {
            Grove.d(e, "Error closing socket")
        }
        socket = null
        connected = false
        connectionSubject.onNext(0)
    }

    private fun startReading() {
        if (readerThread?.isAlive == true) return

        readerThread = Thread {
            try {
                val input = getBTInputStream()
                val buffer = ByteArray(1024)
                val sb = StringBuilder()

                while (!Thread.currentThread().isInterrupted && connected) {
                    val bytesRead = input.read(buffer)
                    if (bytesRead > 0) {
                        val chunk = String(buffer, 0, bytesRead)
                        sb.append(chunk)

                        val completeMessages = sb.toString().split("\r\n")
                        for (i in 0 until completeMessages.size - 1) {
                            val msg = completeMessages[i].trim()
                            if (msg.isNotEmpty()) {
                                messageSubject.onNext(msg)
                            }
                        }
                        sb.clear()
                        sb.append(completeMessages.last())
                    }
                }
            } catch (e: Exception) {
                if (connected) {
                    Grove.d(e, "BT read error")
                }
            }
        }.apply {
            isDaemon = true
            name = "zowi-bt-reader"
            start()
        }
    }
}
