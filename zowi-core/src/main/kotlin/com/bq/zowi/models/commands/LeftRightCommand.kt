package com.bq.zowi.models.commands

class LeftRightCommand(
    action: Command.Action,
    direction: Command.Direction,
    duration: Long
) : MovementCommand() {

    init {
        setAction(action)
        setDirection(direction)
        setDuration(duration)
    }

    override fun getAllowedActions(): Array<Command.Action> = LEFT_RIGHT_ACTIONS
    override fun getAllowedDirections(): Array<Command.Direction> = LEFT_RIGHT_DIRECTIONS
    override fun copy(): Command = LeftRightCommand(getAction(), getDirection()!!, getDuration()!!)

    companion object {
        private val LEFT_RIGHT_ACTIONS = arrayOf(
            Command.Action.TURN, Command.Action.MOONWALKER, Command.Action.CRUSAITO,
            Command.Action.BEND, Command.Action.SHAKE_LEG
        )
        private val LEFT_RIGHT_DIRECTIONS = arrayOf(Command.Direction.LEFT, Command.Direction.RIGHT)
    }
}
