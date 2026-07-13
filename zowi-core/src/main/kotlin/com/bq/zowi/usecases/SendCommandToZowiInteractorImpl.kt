package com.bq.zowi.usecases

import com.bq.zowi.api.BTConnectionController
import com.bq.zowi.models.commands.Command
import com.bq.zowi.utils.Grove
import io.reactivex.Completable

class SendCommandToZowiInteractorImpl(
    private val btConnectionController: BTConnectionController
) : SendCommandToZowiInteractor {

    override fun sendCommandToZowi(command: Command): Completable {
        return Completable.fromAction {
            btConnectionController.sendMessage(command.getCommandValue())
            Grove.d("Message sent: ${command.getCommandValue()}")
        }
    }
}
