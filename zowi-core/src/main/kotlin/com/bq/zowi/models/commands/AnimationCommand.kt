package com.bq.zowi.models.commands

class AnimationCommand(action: Command.Action) : MovementCommand() {

    init {
        setAction(action)
    }

    override fun getAllowedActions(): Array<Command.Action> = GESTURE_ACTIONS
    override fun getAllowedDirections(): Array<Command.Direction> = emptyArray()
    override fun getAllowedDurations(): Array<Long> = emptyArray()
    override fun copy(): Command = AnimationCommand(getAction())

    companion object {
        private val GESTURE_ACTIONS = arrayOf(
            Command.Action.HAPPY, Command.Action.SUPER_HAPPY, Command.Action.SAD,
            Command.Action.SLEEPY, Command.Action.FART, Command.Action.CONFUSED,
            Command.Action.IN_LOVE, Command.Action.ANGRY, Command.Action.ANXIOUS,
            Command.Action.MAGIC, Command.Action.WAVE, Command.Action.VICTORY,
            Command.Action.GAME_OVER
        )
    }
}
