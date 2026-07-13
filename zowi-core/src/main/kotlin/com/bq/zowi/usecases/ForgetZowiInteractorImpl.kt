package com.bq.zowi.usecases

import com.bq.zowi.api.BTConnectionController
import com.bq.zowi.api.SessionController
import io.reactivex.Completable

class ForgetZowiInteractorImpl(
    private val changeZowiNameInteractor: ChangeZowiNameInteractor,
    private val sessionController: SessionController,
    private val connectionController: BTConnectionController
) : ForgetZowiInteractor {

    override fun forgetZowi(): Completable {
        return changeZowiNameInteractor.resetZowiNameToFactory()
            .andThen(Completable.fromAction {
                sessionController.resetActiveZowi()
                connectionController.stopConnection()
            })
    }
}
