package com.bq.zowi.models.commands

import org.junit.Assert.assertEquals
import org.junit.Test

class CommandSerializationTest {

    @Test
    fun stopCommandSerializesCorrectly() {
        val command = StopCommand()
        assertEquals("S\r\n", command.getCommandValue())
    }

    @Test
    fun walkForwardSerializesCorrectly() {
        val command = ForwardBackwardCommand(Command.Action.WALK, Command.Direction.FORWARD, 700)
        assertEquals("M 1 700 \r\n", command.getCommandValue())
    }

    @Test
    fun walkBackwardSerializesCorrectly() {
        val command = ForwardBackwardCommand(Command.Action.WALK, Command.Direction.BACKWARD, 1000)
        assertEquals("M 2 1000 \r\n", command.getCommandValue())
    }

    @Test
    fun turnLeftSerializesCorrectly() {
        val command = LeftRightCommand(Command.Action.TURN, Command.Direction.LEFT, 2000)
        assertEquals("M 3 2000 \r\n", command.getCommandValue())
    }

    @Test
    fun turnRightSerializesCorrectly() {
        val command = LeftRightCommand(Command.Action.TURN, Command.Direction.RIGHT, 700)
        assertEquals("M 4 700 \r\n", command.getCommandValue())
    }

    @Test
    fun moonwalkerLeftSerializesCorrectly() {
        val command = LeftRightCommand(Command.Action.MOONWALKER, Command.Direction.LEFT, 1000)
        assertEquals("M 6 1000 30\r\n", command.getCommandValue())
    }

    @Test
    fun moonwalkerRightSerializesCorrectly() {
        val command = LeftRightCommand(Command.Action.MOONWALKER, Command.Direction.RIGHT, 1000)
        assertEquals("M 7 1000 30\r\n", command.getCommandValue())
    }

    @Test
    fun crusaitoLeftSerializesCorrectly() {
        val command = LeftRightCommand(Command.Action.CRUSAITO, Command.Direction.LEFT, 2000)
        assertEquals("M 9 2000 30\r\n", command.getCommandValue())
    }

    @Test
    fun crusaitoRightSerializesCorrectly() {
        val command = LeftRightCommand(Command.Action.CRUSAITO, Command.Direction.RIGHT, 700)
        assertEquals("M 10 700 30\r\n", command.getCommandValue())
    }

    @Test
    fun flappingForwardSerializesCorrectly() {
        val command = ForwardBackwardCommand(Command.Action.FLAPPING, Command.Direction.FORWARD, 1000)
        assertEquals("M 12 1000 10\r\n", command.getCommandValue())
    }

    @Test
    fun flappingBackwardSerializesCorrectly() {
        val command = ForwardBackwardCommand(Command.Action.FLAPPING, Command.Direction.BACKWARD, 2000)
        assertEquals("M 13 2000 10\r\n", command.getCommandValue())
    }

    @Test
    fun bendLeftSerializesCorrectly() {
        val command = LeftRightCommand(Command.Action.BEND, Command.Direction.LEFT, 700)
        assertEquals("M 15 700 \r\n", command.getCommandValue())
    }

    @Test
    fun bendRightSerializesCorrectly() {
        val command = LeftRightCommand(Command.Action.BEND, Command.Direction.RIGHT, 1000)
        assertEquals("M 16 1000 \r\n", command.getCommandValue())
    }

    @Test
    fun shakeLegLeftSerializesCorrectly() {
        val command = LeftRightCommand(Command.Action.SHAKE_LEG, Command.Direction.LEFT, 2000)
        assertEquals("M 17 2000 \r\n", command.getCommandValue())
    }

    @Test
    fun shakeLegRightSerializesCorrectly() {
        val command = LeftRightCommand(Command.Action.SHAKE_LEG, Command.Direction.RIGHT, 700)
        assertEquals("M 18 700 \r\n", command.getCommandValue())
    }

    @Test
    fun staticUpDownSerializesCorrectly() {
        val command = StaticCommand(Command.Action.UPDOWN, 1000)
        assertEquals("M 5 1000 \r\n", command.getCommandValue())
    }

    @Test
    fun staticJumpSerializesCorrectly() {
        val command = StaticCommand(Command.Action.JUMP, 700)
        assertEquals("M 11 700 \r\n", command.getCommandValue())
    }

    @Test
    fun staticTipToeSerializesCorrectly() {
        val command = StaticCommand(Command.Action.TIP_TOE, 2000)
        assertEquals("M 14 2000 \r\n", command.getCommandValue())
    }

    @Test
    fun staticJitterSerializesCorrectly() {
        val command = StaticCommand(Command.Action.JITTER, 1000)
        assertEquals("M 19 1000 \r\n", command.getCommandValue())
    }

    @Test
    fun staticAscendingTurnSerializesCorrectly() {
        val command = StaticCommand(Command.Action.ASCENDING_TURN, 700)
        assertEquals("M 20 700 \r\n", command.getCommandValue())
    }

    @Test
    fun staticSwingSerializesCorrectly() {
        val command = StaticCommand(Command.Action.SWING, 2000)
        assertEquals("M 8 2000 \r\n", command.getCommandValue())
    }

    @Test
    fun toneCommandSerializesCorrectly() {
        val command = ToneCommand(440, 500)
        assertEquals("T 440 500\r\n", command.getCommandValue())
    }

    @Test
    fun toneCommandWithDifferentFrequencySerializesCorrectly() {
        val command = ToneCommand(880, 1000)
        assertEquals("T 880 1000\r\n", command.getCommandValue())
    }

    @Test
    fun mouthSmileSerializesCorrectly() {
        val command = MouthCommand(Command.Action.MOUTH_SMILE)
        assertEquals("L 00${MouthCommand.SMILE}\r\n", command.getCommandValue())
    }

    @Test
    fun mouthSadSerializesCorrectly() {
        val command = MouthCommand(Command.Action.MOUTH_SAD)
        assertEquals("L 00${MouthCommand.SAD}\r\n", command.getCommandValue())
    }

    @Test
    fun mouthHeartSerializesCorrectly() {
        val command = MouthCommand(Command.Action.MOUTH_HEART)
        assertEquals("L 00${MouthCommand.HEART}\r\n", command.getCommandValue())
    }

    @Test
    fun mouthEmptySerializesCorrectly() {
        val command = MouthCommand(Command.Action.MOUTH_EMPTY)
        assertEquals("L 00${MouthCommand.EMPTY}\r\n", command.getCommandValue())
    }

    @Test
    fun mouthZeroSerializesCorrectly() {
        val command = MouthCommand(Command.Action.MOUTH_ZERO)
        assertEquals("L 00${MouthCommand.ZERO}\r\n", command.getCommandValue())
    }

    @Test
    fun mouthNineSerializesCorrectly() {
        val command = MouthCommand(Command.Action.MOUTH_NINE)
        assertEquals("L 00${MouthCommand.NINE}\r\n", command.getCommandValue())
    }

    @Test
    fun mouthTeethSerializesCorrectly() {
        val command = MouthCommand(Command.Action.MOUTH_TEETH)
        assertEquals("L 00${MouthCommand.TEETH}\r\n", command.getCommandValue())
    }

    @Test
    fun nameSetCommandSerializesCorrectly() {
        val command = NameSetCommand("Zowi")
        assertEquals("R Zowi\r\n", command.getCommandValue())
    }

    @Test
    fun nameSetCommandWithSpacesSerializesCorrectly() {
        val command = NameSetCommand("My Robot")
        assertEquals("R My Robot\r\n", command.getCommandValue())
    }

    @Test
    fun ledCommandSerializesCorrectly() {
        val matrix = "111111101101101101101101111111"
        val command = LedCommand(matrix)
        assertEquals("L 00$matrix\r\n", command.getCommandValue())
    }

    @Test
    fun ledCommandGetLedMatrixReturnsInput() {
        val matrix = "000000000000000000000000000000"
        val command = LedCommand(matrix)
        assertEquals(matrix, command.getLedMatrix())
    }

    @Test
    fun calibrationTrimSerializesCorrectly() {
        val command = CalibrationCommand(CalibrationCommand.CALIBRATE_TRIM, 10, 20, 30, 40)
        assertEquals("C 10 20 30 40\r\n", command.getCommandValue())
    }

    @Test
    fun calibrationGradesSerializesCorrectly() {
        val command = CalibrationCommand(CalibrationCommand.CALIBRATE_GRADES, -5, 5, -10, 10)
        assertEquals("G -5 5 -10 10\r\n", command.getCommandValue())
    }

    @Test
    fun calibrationCommandWithZerosSerializesCorrectly() {
        val command = CalibrationCommand(CalibrationCommand.CALIBRATE_TRIM, 0, 0, 0, 0)
        assertEquals("C 0 0 0 0\r\n", command.getCommandValue())
    }

    @Test
    fun gridCommandWrapsUnderlyingCommand() {
        val inner = ForwardBackwardCommand(Command.Action.WALK, Command.Direction.FORWARD, 700)
        val grid = GridCommand(inner)
        assertEquals(inner, grid.getCommand())
        assertEquals("walk", grid.getCommandId())
    }

    @Test
    fun gridCommandDefaultsToUnlocked() {
        val command = StopCommand()
        val grid = GridCommand(command)
        assertEquals(true, grid.isUnlocked())
    }

    @Test
    fun gridCommandSetUnlockedWorks() {
        val command = StopCommand()
        val grid = GridCommand(command)
        grid.setUnlocked(false)
        assertEquals(false, grid.isUnlocked())
        grid.setUnlocked(true)
        assertEquals(true, grid.isUnlocked())
    }

    @Test
    fun animationHappySerializesCorrectly() {
        val command = AnimationCommand(Command.Action.HAPPY)
        assertEquals("H 1 \r\n", command.getCommandValue())
    }

    @Test
    fun animationSuperHappySerializesCorrectly() {
        val command = AnimationCommand(Command.Action.SUPER_HAPPY)
        assertEquals("H 2 \r\n", command.getCommandValue())
    }

    @Test
    fun animationSadSerializesCorrectly() {
        val command = AnimationCommand(Command.Action.SAD)
        assertEquals("H 3 \r\n", command.getCommandValue())
    }

    @Test
    fun animationGameOverSerializesCorrectly() {
        val command = AnimationCommand(Command.Action.GAME_OVER)
        assertEquals("H 13 \r\n", command.getCommandValue())
    }

    @Test
    fun fallCommandSerializesCorrectly() {
        val command = FallCommand(Command.Action.FALL)
        assertEquals("M 7 2000 65\r\n", command.getCommandValue())
    }

    @Test
    fun dataRequestDistanceSerializesCorrectly() {
        val command = DataRequestCommand(Command.Action.DISTANCE)
        assertEquals("D\r\n", command.getCommandValue())
    }

    @Test
    fun dataRequestNoiseSerializesCorrectly() {
        val command = DataRequestCommand(Command.Action.NOISE)
        assertEquals("N\r\n", command.getCommandValue())
    }

    @Test
    fun dataRequestBatterySerializesCorrectly() {
        val command = DataRequestCommand(Command.Action.BATTERY)
        assertEquals("B\r\n", command.getCommandValue())
    }

    @Test
    fun dataRequestAppIdSerializesCorrectly() {
        val command = DataRequestCommand(Command.Action.APP_ID)
        assertEquals("I\r\n", command.getCommandValue())
    }

    @Test
    fun dataRequestNameSerializesCorrectly() {
        val command = DataRequestCommand(Command.Action.NAME)
        assertEquals("E\r\n", command.getCommandValue())
    }
}
