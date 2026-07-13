package com.bq.zowi.models.commands

class MouthCommand(action: Command.Action) : MotionlessCommand() {

    init {
        setAction(action)
    }

    override fun getAllowedActions(): Array<Command.Action> = MOUTH_ACTIONS
    override fun getCommandValue(): String = "${LED_MOUTH}${getLedMatrix()}${Command.CRLN}"
    override fun copy(): Command = MouthCommand(getAction())

    fun getLedMatrix(): String = when (getAction()) {
        Command.Action.MOUTH_SMILE -> SMILE
        Command.Action.MOUTH_SAD -> SAD
        Command.Action.MOUTH_CONFUSED -> CONFUSED
        Command.Action.MOUTH_BIG_SURPRISE -> BIG_SURPRISE
        Command.Action.MOUTH_SMALL_SURPRISE -> SMALL_SURPRISE
        Command.Action.MOUTH_HAPPY_OPEN -> HAPPY_OPEN
        Command.Action.MOUTH_HAPPY_CLOSED -> HAPPY_CLOSED
        Command.Action.MOUTH_SAD_OPEN -> SAD_OPEN
        Command.Action.MOUTH_SAD_CLOSED -> SAD_CLOSED
        Command.Action.MOUTH_HEART -> HEART
        Command.Action.MOUTH_THUNDER -> THUNDER
        Command.Action.MOUTH_X -> X
        Command.Action.MOUTH_INTERROGATION -> INTERROGATION
        Command.Action.MOUTH_TONGUE_OUT -> TONGUE_OUT
        Command.Action.MOUTH_DIAGONAL -> DIAGONAL
        Command.Action.MOUTH_ANGRY -> ANGRY
        Command.Action.MOUTH_CULITO -> CULITO
        Command.Action.MOUTH_OK -> OK
        Command.Action.MOUTH_LINE -> LINE
        Command.Action.MOUTH_VAMP1 -> VAMP1
        Command.Action.MOUTH_VAMP2 -> VAMP2
        Command.Action.MOUTH_ZERO -> ZERO
        Command.Action.MOUTH_ONE -> ONE
        Command.Action.MOUTH_TWO -> TWO
        Command.Action.MOUTH_THREE -> THREE
        Command.Action.MOUTH_FOUR -> FOUR
        Command.Action.MOUTH_FIVE -> FIVE
        Command.Action.MOUTH_SIX -> SIX
        Command.Action.MOUTH_SEVEN -> SEVEN
        Command.Action.MOUTH_EIGHT -> EIGHT
        Command.Action.MOUTH_NINE -> NINE
        Command.Action.MOUTH_EMPTY -> EMPTY
        Command.Action.MOUTH_WALRUS -> WALRUS
        Command.Action.MOUTH_CONCENTRATED -> CONCENTRATED
        Command.Action.MOUTH_BIG_OPEN -> BIG_OPEN
        Command.Action.MOUTH_TEETH -> TEETH
        else -> ""
    }

    companion object {
        private const val LED_MOUTH = "L 00"
        const val SMILE = "000000100001010010001100000000"
        const val SAD = "000000001100010010100001000000"
        const val CONFUSED = "000000001000010101100010000000"
        const val BIG_SURPRISE = "001100010010100001010010001100"
        const val SMALL_SURPRISE = "000000000000001100001100000000"
        const val HAPPY_OPEN = "000000111111010010001100000000"
        const val HAPPY_CLOSED = "000000111111011110000000000000"
        const val SAD_OPEN = "000000001100010010111111000000"
        const val SAD_CLOSED = "000000001100011110110011000000"
        const val HEART = "010010101101100001010010001100"
        const val THUNDER = "000100001000011100001000010000"
        const val X = "100001010010001100010010100001"
        const val INTERROGATION = "001100010010000100000100000100"
        const val TONGUE_OUT = "111111001001001001000110000000"
        const val DIAGONAL = "100000010000001000000100000010"
        const val ANGRY = "000000011110100001100001000000"
        const val CULITO = "000000100001101101010010000000"
        const val OK = "000001000010010100001000000000"
        const val LINE = "000000000000111111000000000000"
        const val VAMP1 = "111111101101101101010010000000"
        const val VAMP2 = "111111101101010010000000000000"
        const val ZERO = "001100010010010010010010001100"
        const val ONE = "000100001100000100000100001110"
        const val TWO = "001100010010000100001000011110"
        const val THREE = "001100010010000100010010001100"
        const val FOUR = "010010010010011110000010000010"
        const val FIVE = "011110010000011100000010011100"
        const val SIX = "000100001000011100010010001100"
        const val SEVEN = "011110000010000100001000010000"
        const val EIGHT = "001100010010001100010010001100"
        const val NINE = "001100010010001110000010001110"
        const val EMPTY = "000000000000000000000000000000"
        const val WALRUS = "001100010010010010100001100001"
        const val CONCENTRATED = "011000100100100100111111000000"
        const val BIG_OPEN = "011110100001100001100001011110"
        const val TEETH = "111111101101101101101101111111"

        private val MOUTH_ACTIONS = arrayOf(
            Command.Action.MOUTH_SMILE, Command.Action.MOUTH_SAD, Command.Action.MOUTH_CONFUSED,
            Command.Action.MOUTH_BIG_SURPRISE, Command.Action.MOUTH_SMALL_SURPRISE,
            Command.Action.MOUTH_HAPPY_OPEN, Command.Action.MOUTH_HAPPY_CLOSED,
            Command.Action.MOUTH_SAD_OPEN, Command.Action.MOUTH_SAD_CLOSED,
            Command.Action.MOUTH_HEART, Command.Action.MOUTH_THUNDER, Command.Action.MOUTH_X,
            Command.Action.MOUTH_INTERROGATION, Command.Action.MOUTH_TONGUE_OUT,
            Command.Action.MOUTH_DIAGONAL, Command.Action.MOUTH_ANGRY, Command.Action.MOUTH_CULITO,
            Command.Action.MOUTH_OK, Command.Action.MOUTH_LINE, Command.Action.MOUTH_VAMP1,
            Command.Action.MOUTH_VAMP2, Command.Action.MOUTH_ZERO, Command.Action.MOUTH_ONE,
            Command.Action.MOUTH_TWO, Command.Action.MOUTH_THREE, Command.Action.MOUTH_FOUR,
            Command.Action.MOUTH_FIVE, Command.Action.MOUTH_SIX, Command.Action.MOUTH_SEVEN,
            Command.Action.MOUTH_EIGHT, Command.Action.MOUTH_NINE, Command.Action.MOUTH_EMPTY,
            Command.Action.MOUTH_WALRUS, Command.Action.MOUTH_CONCENTRATED,
            Command.Action.MOUTH_BIG_OPEN, Command.Action.MOUTH_TEETH
        )
    }
}
