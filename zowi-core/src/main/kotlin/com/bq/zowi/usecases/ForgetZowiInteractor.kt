package com.bq.zowi.usecases

import io.reactivex.Completable

interface ForgetZowiInteractor {
    fun forgetZowi(): Completable
}
