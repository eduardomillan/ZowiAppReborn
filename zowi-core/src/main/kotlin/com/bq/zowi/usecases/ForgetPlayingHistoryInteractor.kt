package com.bq.zowi.usecases

import io.reactivex.Completable

interface ForgetPlayingHistoryInteractor {
    fun forgetPlayingHistory(): Completable
}
