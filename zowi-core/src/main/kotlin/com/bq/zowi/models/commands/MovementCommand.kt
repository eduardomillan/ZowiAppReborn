package com.bq.zowi.models.commands

abstract class MovementCommand : BaseCommand() {

    override fun getAllowedDurations(): Array<Long> = ALLOWED_DURATIONS
    override fun isRepeatable(): Boolean = true

    override fun getCommandValue(): String {
        var commandValue: String
        var commandExtras = ""

        when (getAction()) {
            Command.Action.WALK ->
                commandValue = if (getDirection() == Command.Direction.FORWARD) WALK_FORWARD else WALK_BACKWARD
            Command.Action.TURN ->
                commandValue = if (getDirection() == Command.Direction.LEFT) TURN_LEFT else TURN_RIGHT
            Command.Action.MOONWALKER -> {
                commandValue = if (getDirection() == Command.Direction.LEFT) MOONWALKER_LEFT else "M 7"
                commandExtras = "30"
            }
            Command.Action.CRUSAITO -> {
                commandValue = if (getDirection() == Command.Direction.LEFT) CRUSAITO_LEFT else CRUSAITO_RIGHT
                commandExtras = "30"
            }
            Command.Action.FLAPPING -> {
                commandValue = if (getDirection() == Command.Direction.FORWARD) FLAPPING_FORWARD else FLAPPING_BACKWARD
                commandExtras = FLAPPING_SIZE
            }
            Command.Action.BEND ->
                commandValue = if (getDirection() == Command.Direction.LEFT) BEND_LEFT else BEND_RIGHT
            Command.Action.SHAKE_LEG ->
                commandValue = if (getDirection() == Command.Direction.LEFT) SHAKE_LEG_LEFT else SHAKE_LEG_RIGHT
            Command.Action.UPDOWN -> commandValue = UPDOWN
            Command.Action.JUMP -> commandValue = JUMP
            Command.Action.TIP_TOE -> commandValue = TIP_TOE
            Command.Action.JITTER -> commandValue = JITTER
            Command.Action.ASCENDING_TURN -> commandValue = ASCENDING_TURN
            Command.Action.SWING -> commandValue = SWING
            Command.Action.FALL -> { commandValue = "M 7"; commandExtras = FALL_SIZE }
            Command.Action.HAPPY -> commandValue = HAPPY
            Command.Action.SUPER_HAPPY -> commandValue = SUPER_HAPPY
            Command.Action.SAD -> commandValue = SAD
            Command.Action.SLEEPY -> commandValue = SLEEPY
            Command.Action.FART -> commandValue = FART
            Command.Action.CONFUSED -> commandValue = CONFUSED
            Command.Action.IN_LOVE -> commandValue = IN_LOVE
            Command.Action.ANGRY -> commandValue = ANGRY
            Command.Action.ANXIOUS -> commandValue = ANXIOUS
            Command.Action.MAGIC -> commandValue = MAGIC
            Command.Action.WAVE -> commandValue = WAVE
            Command.Action.VICTORY -> commandValue = VICTORY
            Command.Action.GAME_OVER -> commandValue = GAME_OVER
            else -> throw IllegalStateException("Action not matching any valid value")
        }

        if (getDuration() != null) {
            commandValue = "$commandValue ${getDuration()}"
        }
        return "$commandValue $commandExtras${Command.CRLN}"
    }

    companion object {
        val ALLOWED_DURATIONS = arrayOf(700L, 1000L, 2000L)

        private const val WALK_FORWARD = "M 1"
        private const val WALK_BACKWARD = "M 2"
        private const val TURN_LEFT = "M 3"
        private const val TURN_RIGHT = "M 4"
        private const val UPDOWN = "M 5"
        private const val MOONWALKER_LEFT = "M 6"
        private const val CRUSAITO_LEFT = "M 9"
        private const val CRUSAITO_RIGHT = "M 10"
        private const val JUMP = "M 11"
        private const val FLAPPING_FORWARD = "M 12"
        private const val FLAPPING_BACKWARD = "M 13"
        private const val TIP_TOE = "M 14"
        private const val BEND_LEFT = "M 15"
        private const val BEND_RIGHT = "M 16"
        private const val SHAKE_LEG_LEFT = "M 17"
        private const val SHAKE_LEG_RIGHT = "M 18"
        private const val JITTER = "M 19"
        private const val ASCENDING_TURN = "M 20"
        private const val SWING = "M 8"
        private const val FALL_SIZE = "65"
        private const val FLAPPING_SIZE = "10"
        private const val HAPPY = "H 1"
        private const val SUPER_HAPPY = "H 2"
        private const val SAD = "H 3"
        private const val SLEEPY = "H 4"
        private const val FART = "H 5"
        private const val CONFUSED = "H 6"
        private const val IN_LOVE = "H 7"
        private const val ANGRY = "H 8"
        private const val ANXIOUS = "H 9"
        private const val MAGIC = "H 10"
        private const val WAVE = "H 11"
        private const val VICTORY = "H 12"
        private const val GAME_OVER = "H 13"
    }
}
