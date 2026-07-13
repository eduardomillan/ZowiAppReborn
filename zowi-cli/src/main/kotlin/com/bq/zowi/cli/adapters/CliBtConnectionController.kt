package com.bq.zowi.cli.adapters

import com.bq.zowi.api.BTConnectionController
import com.bq.zowi.api.DeviceHandle
import com.fazecast.jSerialComm.SerialPort
import com.bq.zowi.utils.Grove
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.io.InputStream
import java.io.OutputStream
import java.util.concurrent.atomic.AtomicBoolean

class CliBtConnectionController : BTConnectionController {

    private var serialPort: SerialPort? = null
    private val connected = AtomicBoolean(false)
    private val sendingHex = AtomicBoolean(false)
    private val connectionSubject = BehaviorSubject.createDefault(0)
    private val messageSubject = PublishSubject.create<String>()
    private var readerThread: Thread? = null

    @Suppress("UNCHECKED_CAST")
    override fun connect(device: DeviceHandle, duplex: Boolean): Single<Void> {
        return Single.fromCallable {
            Grove.d("CLI: Connecting to ${device.address}")

            val port = SerialPort.getCommPorts().firstOrNull {
                it.systemPortPath == device.address || it.descriptivePortName == device.address
            } ?: throw IllegalStateException("Serial port not found: ${device.address}")

            port.baudRate = 115200
            port.numDataBits = 8
            port.numStopBits = SerialPort.ONE_STOP_BIT
            port.parity = SerialPort.NO_PARITY
            port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0)

            if (!port.openPort()) {
                throw IllegalStateException("Failed to open port: ${device.address}")
            }

            serialPort = port
            connected.set(true)
            connectionSubject.onNext(2)

            if (duplex) {
                startReading()
            }

            Grove.d("CLI: Connected to ${device.address}")
            null as Void
        }
    }

    override fun enabledReadingIncomingData() {
        startReading()
    }

    override fun getBTInputStream(): InputStream {
        return serialPort?.inputStream ?: throw IllegalStateException("Not connected")
    }

    override fun getBTOutputStream(): OutputStream {
        return serialPort?.outputStream ?: throw IllegalStateException("Not connected")
    }

    override fun getConnectionStatusObservable(): Observable<Int> = connectionSubject

    override fun getReceivedMessageObservable(): Observable<String> = messageSubject

    override fun isConnected(): Boolean = connected.get()

    override fun isSendingHexToZowi(): Boolean = sendingHex.get()

    override fun sendHexToZowi(inputStream: InputStream): Observable<Int> {
        return Observable.create { emitter ->
            try {
                sendingHex.set(true)
                val output = getBTOutputStream()
                val buffer = ByteArray(1024)
                var bytesRead: Int
                var totalSent = 0
                val totalSize = inputStream.available().toLong()

                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    output.write(buffer, 0, bytesRead)
                    output.flush()
                    totalSent += bytesRead
                    val progress = if (totalSize > 0) (totalSent * 100 / totalSize).toInt() else 0
                    emitter.onNext(progress.coerceAtMost(99))
                }

                sendingHex.set(false)
                emitter.onNext(100)
                emitter.onComplete()
            } catch (e: Exception) {
                sendingHex.set(false)
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
        serialPort?.closePort()
        serialPort = null
        connected.set(false)
        connectionSubject.onNext(0)
        Grove.d("CLI: Disconnected")
    }

    private fun startReading() {
        if (readerThread?.isAlive == true) return

        readerThread = Thread {
            try {
                val input = getBTInputStream()
                val buffer = ByteArray(1024)
                val sb = StringBuilder()

                while (!Thread.currentThread().isInterrupted && connected.get()) {
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
                if (connected.get()) {
                    Grove.d(e, "CLI: Read error")
                }
            }
        }.apply {
            isDaemon = true
            name = "zowi-cli-reader"
            start()
        }
    }
}
