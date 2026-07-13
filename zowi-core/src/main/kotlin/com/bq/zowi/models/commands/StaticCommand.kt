package com.bq.zowi.models.commands

class StaticCommand(action: Command.Action, duration: Long) : MovementCommand() {

    init {
        setAction(action)
        setDuration(duration)
    }

    override fun getAllowedActions(): Array<Command.Action> = STATIC_ACTIONS
    override fun getAllowedDirections(): Array<Command.Direction> = emptyArray()
    override fun copy(): Command = StaticCommand(getAction(), getDuration()!!)
    override fun isRepeatable(): Boolean = false

    companion object {
        private val STATIC_ACTIONS = arrayOf(
            Command.Action.UPDOWN, Command.Action.JUMP, Command.Action.TIP_TOE,
            Command.Action.JITTER, Command.Action.ASCENDING_TURN, Command.Action.SWING
        )
    }
}
