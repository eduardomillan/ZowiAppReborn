package com.bq.zowi.usecases

import com.bq.zowi.api.ZowiDataController
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class MeasureZowiBatteryLevelInteractorImpl(
    private val zowiDataController: ZowiDataController
) : MeasureZowiBatteryLevelInteractor {

    override fun measureAndManageZowiBatteryLevel(): Single<Boolean> {
        return zowiDataController.getBatteryLevel()
            .flatMap { batteryLevel -> manageZowiBatteryLevel(batteryLevel) }
            .timeout(500L, TimeUnit.MILLISECONDS, Single.just(true))
    }

    override fun manageZowiBatteryLevel(batteryLevel: Float): Single<Boolean> {
        return Single.just(batteryLevel >= MeasureZowiBatteryLevelInteractor.BATTERY_LEVEL_LOWER_THRESHOLD)
    }
}
