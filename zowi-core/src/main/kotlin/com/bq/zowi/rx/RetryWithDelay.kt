package com.bq.zowi.rx

import com.bq.zowi.utils.Grove
import io.reactivex.Observable
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

class RetryWithDelay(
    private val maxRetries: Int,
    private val retryDelayMillis: Int
) : Function<Observable<out Throwable>, Observable<*>> {

    private var retryCount = 0

    override fun apply(attempts: Observable<out Throwable>): Observable<*> {
        return attempts.flatMap { throwable ->
            if (retryCount++ < maxRetries) {
                Grove.d(throwable, "Rx Retry: $retryCount")
                Observable.timer(retryDelayMillis.toLong(), TimeUnit.MILLISECONDS)
            } else {
                Grove.d(throwable, "Rx Max retries hit. Passing error along")
                Observable.error<Any>(throwable)
            }
        }
    }
}
