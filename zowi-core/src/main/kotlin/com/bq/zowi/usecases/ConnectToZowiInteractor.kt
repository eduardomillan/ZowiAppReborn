package com.bq.zowi.usecases

import com.bq.zowi.models.ConnectionSuccessData
import io.reactivex.Completable
import io.reactivex.Single

interface ConnectToZowiInteractor {
    fun connectToZowi(deviceAddress: String, duplex: Boolean): Completable
    fun connectToZowiAndRetrieveData(deviceAddress: String): Single<ConnectionSuccessData>
    fun disconnectFromZowi(deviceAddress: String): Completable
}
