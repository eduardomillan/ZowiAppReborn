package com.bq.zowi.models.commands

class ToneCommand(frequency: Int, duration: Long) : MotionlessCommand() {

    private val frequency: Int = frequency

    init {
        setAction(Command.Action.TONE)
        setDurationRaw(duration)
    }

    override fun getAllowedActions(): Array<Command.Action> = TONE_ACTIONS
    override fun getCommandValue(): String = "T ${frequency} ${_duration}${Command.CRLN}"
    override fun copy(): Command = ToneCommand(frequency, _duration!!)

    companion object {
        private val TONE_ACTIONS = arrayOf(Command.Action.TONE)
    }
}
