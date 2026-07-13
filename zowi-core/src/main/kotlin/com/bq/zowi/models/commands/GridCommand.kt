package com.bq.zowi.models.commands

class GridCommand(private val command: Command) {
    private var unlocked: Boolean = true

    fun getCommand(): Command = command
    fun isUnlocked(): Boolean = unlocked
    fun setUnlocked(unlocked: Boolean) { this.unlocked = unlocked }
    fun getCommandId(): String = command.getId()
}
