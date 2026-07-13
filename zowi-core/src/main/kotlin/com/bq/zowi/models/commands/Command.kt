package com.bq.zowi.models.commands

interface Command {
    companion object {
        const val CRLN = "\r\n"
    }

    enum class Direction { FORWARD, BACKWARD, LEFT, RIGHT }

    enum class Action {
        STOP, WALK, TURN, UPDOWN, MOONWALKER, SWING, CRUSAITO, JUMP,
        FLAPPING, TIP_TOE, BEND, SHAKE_LEG, JITTER, ASCENDING_TURN, FALL,
        HAPPY, SUPER_HAPPY, SAD, SLEEPY, FART, CONFUSED, IN_LOVE, ANGRY,
        ANXIOUS, MAGIC, WAVE, VICTORY, GAME_OVER,
        MOUTH_SMILE, MOUTH_SAD, MOUTH_CONFUSED, MOUTH_BIG_SURPRISE,
        MOUTH_SMALL_SURPRISE, MOUTH_HAPPY_OPEN, MOUTH_HAPPY_CLOSED,
        MOUTH_SAD_OPEN, MOUTH_SAD_CLOSED, MOUTH_HEART, MOUTH_THUNDER,
        MOUTH_X, MOUTH_INTERROGATION, MOUTH_TONGUE_OUT, MOUTH_DIAGONAL,
        MOUTH_ANGRY, MOUTH_CULITO, MOUTH_OK, MOUTH_LINE, MOUTH_VAMP1,
        MOUTH_VAMP2, MOUTH_ZERO, MOUTH_ONE, MOUTH_TWO, MOUTH_THREE,
        MOUTH_FOUR, MOUTH_FIVE, MOUTH_SIX, MOUTH_SEVEN, MOUTH_EIGHT,
        MOUTH_NINE, MOUTH_EMPTY, MOUTH_WALRUS, MOUTH_CONCENTRATED,
        MOUTH_BIG_OPEN, MOUTH_TEETH,
        LED_MOUTH, TONE, DISTANCE, NOISE, BATTERY, APP_ID, NAME, SET_NAME,
        CALIBRATE_TRIM, CALIBRATE_GRADES;

        val id: String get() = toString().lowercase()
    }

    fun copy(): Command
    fun getAction(): Action
    fun getAllowedActions(): Array<Action>
    fun getAllowedDirections(): Array<Direction>
    fun getAllowedDurations(): Array<Long>
    fun getCommandValue(): String
    fun getDirection(): Direction?
    fun getDuration(): Long?
    fun getId(): String
    fun isRepeatable(): Boolean
    fun setAction(action: Action)
    fun setDirection(direction: Direction)
    fun setDuration(duration: Long)
}
