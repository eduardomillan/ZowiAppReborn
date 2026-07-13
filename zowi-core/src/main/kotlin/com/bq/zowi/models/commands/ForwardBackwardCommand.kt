package com.bq.zowi.models.commands

class ForwardBackwardCommand(
    action: Command.Action,
    direction: Command.Direction,
    duration: Long
) : MovementCommand() {

    init {
        setAction(action)
        setDirection(direction)
        setDuration(duration)
    }

    override fun getAllowedActions(): Array<Command.Action> = FORWARD_BACKWARD_ACTIONS
    override fun getAllowedDirections(): Array<Command.Direction> = FORWARD_BACKWARD_DIRECTIONS
    override fun copy(): Command = ForwardBackwardCommand(getAction(), getDirection()!!, getDuration()!!)

    companion object {
        private val FORWARD_BACKWARD_ACTIONS = arrayOf(Command.Action.WALK, Command.Action.FLAPPING)
        private val FORWARD_BACKWARD_DIRECTIONS = arrayOf(Command.Direction.FORWARD, Command.Direction.BACKWARD)
    }
}
