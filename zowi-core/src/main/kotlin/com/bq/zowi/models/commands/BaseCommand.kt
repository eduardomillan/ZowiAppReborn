package com.bq.zowi.models.commands

abstract class BaseCommand : Command {
    private var action: Command.Action? = null
    private var direction: Command.Direction? = null
    protected var _duration: Long? = null

    override fun getAction(): Command.Action = action!!

    override fun setAction(action: Command.Action) {
        require(action in getAllowedActions()) { "The action $action is not valid here." }
        this.action = action
    }

    override fun getDirection(): Command.Direction? = direction

    override fun setDirection(direction: Command.Direction) {
        require(direction in getAllowedDirections()) { "The direction $direction is not valid for action ${getAction()}" }
        this.direction = direction
    }

    override fun getDuration(): Long? = _duration

    override fun setDuration(duration: Long) {
        require(duration in getAllowedDurations()) { "The duration $duration is not valid for action ${getAction()}" }
        this._duration = duration
    }

    protected fun setDurationRaw(duration: Long) {
        this._duration = duration
    }

    override fun getId(): String = getAction().id
}
