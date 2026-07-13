package com.bq.zowi.models.commands

class LedCommand(private val ledMatrix: String) : MotionlessCommand() {

    init {
        setAction(Command.Action.LED_MOUTH)
    }

    override fun getAllowedActions(): Array<Command.Action> = LED_MOUTH_ACTIONS
    override fun getCommandValue(): String = "${LED_MOUTH}${ledMatrix}${Command.CRLN}"
    fun getLedMatrix(): String = ledMatrix
    override fun copy(): Command = LedCommand(ledMatrix)

    companion object {
        private const val LED_MOUTH = "L 00"
        private val LED_MOUTH_ACTIONS = arrayOf(Command.Action.LED_MOUTH)
    }
}
