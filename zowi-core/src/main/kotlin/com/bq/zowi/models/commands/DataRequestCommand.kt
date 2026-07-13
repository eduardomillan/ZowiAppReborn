package com.bq.zowi.models.commands

class DataRequestCommand(action: Command.Action) : MotionlessCommand() {

    init {
        setAction(action)
    }

    override fun getAllowedActions(): Array<Command.Action> = DATA_REQUEST_ACTIONS
    override fun getCommandValue(): String = when (getAction()) {
        Command.Action.DISTANCE -> "D"
        Command.Action.NOISE -> "N"
        Command.Action.BATTERY -> "B"
        Command.Action.APP_ID -> "I"
        Command.Action.NAME -> "E"
        else -> throw IllegalStateException("Action not matching any valid value")
    } + Command.CRLN

    override fun copy(): Command = DataRequestCommand(getAction())

    companion object {
        private val DATA_REQUEST_ACTIONS = arrayOf(
            Command.Action.DISTANCE, Command.Action.NOISE, Command.Action.BATTERY,
            Command.Action.APP_ID, Command.Action.NAME
        )
    }
}
