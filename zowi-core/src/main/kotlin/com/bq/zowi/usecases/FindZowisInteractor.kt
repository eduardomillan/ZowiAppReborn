package com.bq.zowi.usecases

import com.bq.zowi.api.DeviceHandle
import io.reactivex.Single

interface FindZowisInteractor {
    fun findZowis(): Single<DeviceHandle>
    fun stopFindingZowis()
}
