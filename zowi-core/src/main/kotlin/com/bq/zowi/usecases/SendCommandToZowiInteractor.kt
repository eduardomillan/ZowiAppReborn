package com.bq.zowi.usecases

import com.bq.zowi.models.commands.Command
import io.reactivex.Completable

interface SendCommandToZowiInteractor {
    fun sendCommandToZowi(command: Command): Completable
}
