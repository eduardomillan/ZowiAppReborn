package com.bq.zowi.usecases

import io.reactivex.Single

interface MeasureZowiBatteryLevelInteractor {
    companion object {
        const val BATTERY_LEVEL_LOWER_THRESHOLD = 50.0f
    }

    fun manageZowiBatteryLevel(batteryLevel: Float): Single<Boolean>
    fun measureAndManageZowiBatteryLevel(): Single<Boolean>
}
