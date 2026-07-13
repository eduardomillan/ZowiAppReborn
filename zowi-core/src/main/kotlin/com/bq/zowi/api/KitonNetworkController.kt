package com.bq.zowi.api

import com.bq.zowi.models.networkModels.KitonIsAliveResponseNetworkModel
import io.reactivex.Single

interface KitonNetworkController {
    fun sendIsAliveToKiton(macAddress: String, zowiAppId: String?): Single<KitonIsAliveResponseNetworkModel>
}
