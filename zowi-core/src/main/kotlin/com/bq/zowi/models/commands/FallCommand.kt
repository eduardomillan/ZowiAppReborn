package com.bq.zowi.models.commands

class FallCommand(action: Command.Action) : MovementCommand() {

    init {
        setAction(action)
        setDuration(FALL_DURATION)
    }

    override fun getAllowedActions(): Array<Command.Action> = FALL_ACTION
    override fun getAllowedDirections(): Array<Command.Direction> = emptyArray()
    override fun copy(): Command = FallCommand(getAction())

    companion object {
        private val FALL_ACTION = arrayOf(Command.Action.FALL)
        private const val FALL_DURATION = 2000L
    }
}
