package com.bq.zowi.models.commands

class CalibrationCommand(
    type: String,
    leftLegYL: Int,
    rightLegYR: Int,
    leftFootRL: Int,
    rightFootRR: Int
) : MotionlessCommand() {

    private val type: String = type
    private val leftLegYL: Int = leftLegYL
    private val rightLegYR: Int = rightLegYR
    private val leftFootRL: Int = leftFootRL
    private val rightFootRR: Int = rightFootRR

    init {
        when (type) {
            CALIBRATE_TRIM -> setAction(Command.Action.CALIBRATE_TRIM)
            CALIBRATE_GRADES -> setAction(Command.Action.CALIBRATE_GRADES)
        }
    }

    override fun getAllowedActions(): Array<Command.Action> = CALIBRATE_ACTIONS
    override fun getCommandValue(): String =
        "$type $leftLegYL $rightLegYR $leftFootRL $rightFootRR${Command.CRLN}"

    override fun copy(): Command =
        CalibrationCommand(type, leftLegYL, rightLegYR, leftFootRL, rightFootRR)

    companion object {
        const val CALIBRATE_GRADES = "G"
        const val CALIBRATE_TRIM = "C"
        private val CALIBRATE_ACTIONS = arrayOf(Command.Action.CALIBRATE_TRIM, Command.Action.CALIBRATE_GRADES)
    }
}
