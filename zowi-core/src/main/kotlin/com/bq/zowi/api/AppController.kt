package com.bq.zowi.api

import io.reactivex.Single

interface AppController {
    fun getDaysOfUse(): Single<Int>
    fun isFirstUsage(): Single<Boolean>
    fun logAppStarted(): Single<Void>
    fun resetAppLogs(): Single<Void>
}
