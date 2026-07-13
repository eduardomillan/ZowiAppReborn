package com.bq.zowi.usecases

import com.bq.zowi.models.ZowiName
import com.bq.zowi.models.commands.NameSetCommand
import io.reactivex.Completable

class ChangeZowiNameInteractorImpl(
    private val sendCommandToZowiInteractor: SendCommandToZowiInteractor
) : ChangeZowiNameInteractor {

    override fun changeZowiName(name: String): Completable {
        return sendCommandToZowiInteractor.sendCommandToZowi(NameSetCommand(name))
    }

    override fun resetZowiNameToFactory(): Completable {
        return sendCommandToZowiInteractor.sendCommandToZowi(NameSetCommand(ZowiName.getFactoryName()))
    }
}
