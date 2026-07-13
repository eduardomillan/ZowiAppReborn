package com.bq.zowi.models.commands

data class TimelineCommand(
    val command: Command,
    var repetitions: Int
) {
    var isThisCommandBeingPlayed: Boolean = false
    var isTimelineBeingPlayed: Boolean = false

    fun copy(): TimelineCommand = TimelineCommand(command.copy(), repetitions)
}
