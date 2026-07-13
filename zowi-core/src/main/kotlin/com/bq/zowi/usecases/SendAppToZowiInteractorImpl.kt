package com.bq.zowi.usecases

import com.bq.zowi.api.AssetController
import com.bq.zowi.api.BTConnectionController
import com.bq.zowi.api.KitonNetworkController
import com.bq.zowi.api.SessionController
import com.bq.zowi.api.ZowiDataController
import com.bq.zowi.utils.Grove
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SendAppToZowiInteractorImpl(
    private val connectToZowiInteractor: ConnectToZowiInteractor,
    private val btConnectionController: BTConnectionController,
    private val assetController: AssetController,
    private val sessionController: SessionController,
    private val zowiDataController: ZowiDataController,
    private val kitonNetworkController: KitonNetworkController
) : SendAppToZowiInteractor {

    override fun sendAppToZowi(appHexPath: String): Observable<Int> {
        val activeZowiAddress = sessionController.loadActiveZowiDeviceAddress() ?: ""
        return getHexInputStream(appHexPath)
            .flatMapObservable { hexInputStream ->
                connectToZowiInteractor.connectToZowi(activeZowiAddress, false)
                    .andThen(Single.just(0))
                    .toObservable()
                    .concatWith(
                        btConnectionController.sendHexToZowi(hexInputStream)
                            .subscribeOn(Schedulers.io())
                    )
                    .concatWith(
                        Observable.create { emitter ->
                            btConnectionController.enabledReadingIncomingData()
                            emitter.onNext(100)
                            emitter.onComplete()
                            Grove.d("Send HEX to Zowi process complete. Checking installed ZowiAppId.")
                        }
                    )
                    .concatWith(
                        zowiDataController.waitForZowiAppIdReception()
                            .timeout(6000L, TimeUnit.MILLISECONDS, Single.create { emitter ->
                                Grove.d("No installed ZowiAppId received after sending HEX to Zowi.")
                                sendHitToKiton(activeZowiAddress, null)
                                emitter.onError(IllegalStateException("No FW ID is returned from Zowi after programming."))
                            })
                            .toObservable()
                            .flatMap { installedZowiAppId ->
                                Grove.d("A valid ZowiAppId received after sending HEX to Zowi: $installedZowiAppId. Success!")
                                sendHitToKiton(activeZowiAddress, installedZowiAppId)
                                Observable.just(100)
                            }
                    )
            }
    }

    private fun getHexInputStream(hexPath: String): Single<java.io.InputStream> {
        return Single.create { emitter ->
            try {
                val inputStream = assetController.openAsset(hexPath)
                emitter.onSuccess(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
                emitter.onError(e)
            }
        }
    }

    private fun sendHitToKiton(activeZowiAddress: String, zowiAppId: String?) {
        kitonNetworkController.sendIsAliveToKiton(activeZowiAddress, zowiAppId)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { Grove.d("Kiton answered: $it") },
                { Grove.d(it, "Kiton isAlive error") }
            )
    }
}
