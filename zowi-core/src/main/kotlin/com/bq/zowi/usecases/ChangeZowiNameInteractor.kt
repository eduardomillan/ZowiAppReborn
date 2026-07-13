package com.bq.zowi.usecases

import io.reactivex.Completable

interface ChangeZowiNameInteractor {
    fun changeZowiName(name: String): Completable
    fun resetZowiNameToFactory(): Completable
}
