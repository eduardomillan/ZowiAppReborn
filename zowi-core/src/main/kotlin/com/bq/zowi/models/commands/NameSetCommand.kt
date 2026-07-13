package com.bq.zowi.models.commands

class NameSetCommand(name: String) : MotionlessCommand() {

    private val name: String = name

    init {
        setAction(Command.Action.SET_NAME)
    }

    override fun getAllowedActions(): Array<Command.Action> = NAME_SET_ACTIONS
    override fun getCommandValue(): String = "R $name${Command.CRLN}"
    override fun copy(): Command = NameSetCommand(name)

    companion object {
        private val NAME_SET_ACTIONS = arrayOf(Command.Action.SET_NAME)
    }
}
