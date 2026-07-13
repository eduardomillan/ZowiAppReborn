package com.bq.zowi.models.commands

class LeftRightRunnerCommand(
    action: Command.Action,
    direction: Command.Direction,
    duration: Long
) : MovementCommand() {

    init {
        setAction(action)
        setDirection(direction)
        setDurationRaw(duration)
    }

    override fun getAllowedActions(): Array<Command.Action> = LEFT_RIGHT_ACTIONS
    override fun getAllowedDirections(): Array<Command.Direction> = LEFT_RIGHT_DIRECTIONS
    override fun copy(): Command = LeftRightCommand(getAction(), getDirection()!!, getDuration()!!)
    override fun setDuration(duration: Long) { _duration = duration }

    companion object {
        private val LEFT_RIGHT_ACTIONS = arrayOf(Command.Action.TURN)
        private val LEFT_RIGHT_DIRECTIONS = arrayOf(Command.Direction.LEFT, Command.Direction.RIGHT)
    }
}
