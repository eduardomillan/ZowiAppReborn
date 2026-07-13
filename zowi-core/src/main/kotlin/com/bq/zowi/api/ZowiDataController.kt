package com.bq.zowi.api

import io.reactivex.Single

interface ZowiDataController {
    fun getBatteryLevel(): Single<Float>
    fun getDistanceLevel(): Single<Int>
    fun getNoiseLevel(): Single<Int>
    fun getZowiAppId(): Single<String>
    fun getZowiName(): Single<String>
    fun waitForBatteryLevelReception(): Single<Float>
    fun waitForDistanceLevelReception(): Single<Int>
    fun waitForNoiseLevelReception(): Single<Int>
    fun waitForZowiAppIdReception(): Single<String>
    fun waitForZowiNameReception(): Single<String>
}
