package com.bq.zowi.cli.adapters

import com.bq.zowi.api.BTConnectionController
import com.bq.zowi.api.ZowiDataController
import com.bq.zowi.utils.Grove
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class CliZowiDataController(
    private val connectionController: BTConnectionController
) : ZowiDataController {

    private val nameSubject = BehaviorSubject.create<String>()
    private val appIdSubject = BehaviorSubject.create<String>()
    private val batterySubject = BehaviorSubject.create<Float>()
    private val distanceSubject = BehaviorSubject.create<Int>()
    private val noiseSubject = BehaviorSubject.create<Int>()

    private var disposable: Disposable? = null

    init {
        disposable = connectionController.getReceivedMessageObservable()
            .subscribe { message -> parseMessage(message) }
    }

    private fun parseMessage(message: String) {
        try {
            val parts = message.split(" ")
            if (parts.size < 2) return

            when (parts[0]) {
                "N" -> {
                    val name = parts.drop(1).joinToString(" ").trim()
                    if (name.isNotEmpty()) nameSubject.onNext(name)
                }
                "U" -> {
                    val appId = parts[1].trim()
                    if (appId.isNotEmpty()) appIdSubject.onNext(appId)
                }
                "B" -> {
                    val battery = parts[1].trim().toFloatOrNull()
                    if (battery != null) batterySubject.onNext(battery)
                }
                "D" -> {
                    val distance = parts[1].trim().toIntOrNull()
                    if (distance != null) distanceSubject.onNext(distance)
                }
                "I" -> {
                    val noise = parts[1].trim().toIntOrNull()
                    if (noise != null) noiseSubject.onNext(noise)
                }
            }
        } catch (e: Exception) {
            Grove.d(e, "CLI: Failed to parse message: $message")
        }
    }

    override fun getBatteryLevel(): Single<Float> {
        connectionController.sendMessage("B\r\n")
        return batterySubject.firstOrError()
    }

    override fun getDistanceLevel(): Single<Int> {
        connectionController.sendMessage("D\r\n")
        return distanceSubject.firstOrError()
    }

    override fun getNoiseLevel(): Single<Int> {
        connectionController.sendMessage("I\r\n")
        return noiseSubject.firstOrError()
    }

    override fun getZowiAppId(): Single<String> {
        connectionController.sendMessage("U\r\n")
        return appIdSubject.firstOrError()
    }

    override fun getZowiName(): Single<String> {
        connectionController.sendMessage("N\r\n")
        return nameSubject.firstOrError()
    }

    override fun waitForBatteryLevelReception(): Single<Float> {
        return batterySubject.firstOrError()
    }

    override fun waitForDistanceLevelReception(): Single<Int> {
        return distanceSubject.firstOrError()
    }

    override fun waitForNoiseLevelReception(): Single<Int> {
        return noiseSubject.firstOrError()
    }

    override fun waitForZowiAppIdReception(): Single<String> {
        return appIdSubject.firstOrError()
    }

    override fun waitForZowiNameReception(): Single<String> {
        return nameSubject.firstOrError()
    }

    fun dispose() {
        disposable?.dispose()
    }
}
