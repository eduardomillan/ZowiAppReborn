package com.bq.zowi.usecases

import io.reactivex.Observable

interface SendAppToZowiInteractor {
    fun sendAppToZowi(appHexPath: String): Observable<Int>
}
