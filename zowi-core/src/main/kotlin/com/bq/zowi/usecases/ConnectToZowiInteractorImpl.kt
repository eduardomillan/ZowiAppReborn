package com.bq.zowi.usecases

import com.bq.zowi.api.BTAdapterController
import com.bq.zowi.api.BTConnectionController
import com.bq.zowi.api.KitonNetworkController
import com.bq.zowi.api.ZowiDataController
import com.bq.zowi.models.ConnectionSuccessData
import com.bq.zowi.rx.RetryWithDelay
import com.bq.zowi.utils.Grove
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ConnectToZowiInteractorImpl(
    private val btConnectionController: BTConnectionController,
    private val btAdapterController: BTAdapterController,
    private val zowiDataController: ZowiDataController,
    private val kitonNetworkController: KitonNetworkController
) : ConnectToZowiInteractor {

    override fun connectToZowiAndRetrieveData(deviceAddress: String): Single<ConnectionSuccessData> {
        return connectToZowi(deviceAddress, true)
            .andThen(
                Single.zip(
                    zowiDataController.waitForZowiNameReception(),
                    zowiDataController.waitForZowiAppIdReception(),
                    zowiDataController.waitForBatteryLevelReception()
                ) { name, appId, battery ->
                    ConnectionSuccessData(name, appId, battery)
                }.timeout(3000L, TimeUnit.MILLISECONDS, Single.just(null as ConnectionSuccessData?))
            )
            .map { it!! }
            .doOnSuccess { data ->
                kitonNetworkController.sendIsAliveToKiton(deviceAddress, data.zowiAppId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        { Grove.d("Kiton answered: $it") },
                        { Grove.d(it, "Kiton isAlive error") }
                    )
            }
    }

    override fun connectToZowi(deviceAddress: String, duplex: Boolean): Completable {
        val alreadyTriedDisablingBluetooth = booleanArrayOf(false)
        return btAdapterController.enableBluetooth()
            .flatMap {
                Grove.d("Bluetooth enabled. Start connecting.")
                btConnectionController.connect(
                    btAdapterController.getRemoteDevice(deviceAddress),
                    duplex
                )
            }
            .toObservable()
            .retryWhen(RetryWithDelay(3, 1000))
            .retryWhen { observable ->
                observable.flatMap { throwable ->
                    if (!alreadyTriedDisablingBluetooth[0]) {
                        alreadyTriedDisablingBluetooth[0] = true
                        Grove.d(throwable, "ConnectToZowi error. Retried several times and still failing. Trying disabling and re-enabling bluetooth.")
                        btAdapterController.disableBluetooth().toObservable()
                    } else {
                        Grove.d(throwable, "Already tried disabling and re-enabling bluetooth. Nothing else to do. Just fail.")
                        Observable.error<Any>(throwable)
                    }
                }
            }
            .ignoreElements()
    }

    override fun disconnectFromZowi(deviceAddress: String): Completable {
        return Completable.fromAction {
            btConnectionController.stopConnection()
        }.andThen(Completable.timer(1000L, TimeUnit.MILLISECONDS))
    }
}
