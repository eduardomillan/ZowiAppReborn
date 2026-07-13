package com.bq.zowi.models.commands

abstract class MotionlessCommand : BaseCommand() {
    override fun getAllowedDurations(): Array<Long> = emptyArray()
    override fun getAllowedDirections(): Array<Command.Direction> = emptyArray()
    override fun isRepeatable(): Boolean = false
}
