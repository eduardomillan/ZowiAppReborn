package com.bq.zowi.models.commands

class ForwardRunnerCommand(
    action: Command.Action,
    direction: Command.Direction,
    duration: Long
) : MovementCommand() {

    init {
        setAction(action)
        setDirection(direction)
        setDurationRaw(duration)
    }

    override fun getAllowedActions(): Array<Command.Action> = FORWARD_ACTION
    override fun getAllowedDirections(): Array<Command.Direction> = FORWARD_DIRECTION
    override fun setDuration(duration: Long) { _duration = duration }
    override fun isRepeatable(): Boolean = false
    override fun copy(): Command = ForwardBackwardCommand(getAction(), getDirection()!!, getDuration()!!)

    companion object {
        private val FORWARD_ACTION = arrayOf(Command.Action.WALK)
        private val FORWARD_DIRECTION = arrayOf(Command.Direction.FORWARD)
    }
}
