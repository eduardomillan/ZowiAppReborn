package com.bq.zowi.models.commands

class StopCommand : MotionlessCommand() {

    init {
        setAction(Command.Action.STOP)
    }

    override fun getAllowedActions(): Array<Command.Action> = STOP_ACTIONS
    override fun getCommandValue(): String = "S\r\n"
    override fun copy(): Command = StopCommand()

    companion object {
        private val STOP_ACTIONS = arrayOf(Command.Action.STOP)
    }
}
