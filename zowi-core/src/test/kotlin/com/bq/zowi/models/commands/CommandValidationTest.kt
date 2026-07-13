package com.bq.zowi.models.commands

import org.junit.Test

class CommandValidationTest {

    @Test(expected = IllegalArgumentException::class)
    fun mouthCommandRejectsWalkAction() {
        MouthCommand(Command.Action.WALK)
    }

    @Test(expected = IllegalArgumentException::class)
    fun mouthCommandRejectsTurnAction() {
        MouthCommand(Command.Action.TURN)
    }

    @Test(expected = IllegalArgumentException::class)
    fun mouthCommandRejectsStopAction() {
        MouthCommand(Command.Action.STOP)
    }

    @Test(expected = IllegalArgumentException::class)
    fun staticCommandRejectsForwardDirection() {
        val command = StaticCommand(Command.Action.JUMP, 700)
        command.setDirection(Command.Direction.FORWARD)
    }

    @Test(expected = IllegalArgumentException::class)
    fun staticCommandRejectsBackwardDirection() {
        val command = StaticCommand(Command.Action.UPDOWN, 1000)
        command.setDirection(Command.Direction.BACKWARD)
    }

    @Test(expected = IllegalArgumentException::class)
    fun staticCommandRejectsLeftDirection() {
        val command = StaticCommand(Command.Action.SWING, 2000)
        command.setDirection(Command.Direction.LEFT)
    }

    @Test(expected = IllegalArgumentException::class)
    fun staticCommandRejectsRightDirection() {
        val command = StaticCommand(Command.Action.TIP_TOE, 700)
        command.setDirection(Command.Direction.RIGHT)
    }

    @Test(expected = IllegalArgumentException::class)
    fun forwardBackwardCommandRejectsInvalidDuration() {
        val command = ForwardBackwardCommand(Command.Action.WALK, Command.Direction.FORWARD, 700)
        command.setDuration(500)
    }

    @Test(expected = IllegalArgumentException::class)
    fun leftRightCommandRejectsInvalidDuration() {
        val command = LeftRightCommand(Command.Action.TURN, Command.Direction.LEFT, 1000)
        command.setDuration(3000)
    }

    @Test(expected = IllegalArgumentException::class)
    fun forwardBackwardCommandRejectsLeftDirection() {
        val command = ForwardBackwardCommand(Command.Action.WALK, Command.Direction.FORWARD, 700)
        command.setDirection(Command.Direction.LEFT)
    }

    @Test(expected = IllegalArgumentException::class)
    fun forwardBackwardCommandRejectsRightDirection() {
        val command = ForwardBackwardCommand(Command.Action.WALK, Command.Direction.BACKWARD, 1000)
        command.setDirection(Command.Direction.RIGHT)
    }

    @Test(expected = IllegalArgumentException::class)
    fun leftRightCommandRejectsForwardDirection() {
        val command = LeftRightCommand(Command.Action.TURN, Command.Direction.LEFT, 700)
        command.setDirection(Command.Direction.FORWARD)
    }

    @Test(expected = IllegalArgumentException::class)
    fun leftRightCommandRejectsBackwardDirection() {
        val command = LeftRightCommand(Command.Action.MOONWALKER, Command.Direction.RIGHT, 1000)
        command.setDirection(Command.Direction.BACKWARD)
    }

    @Test(expected = IllegalArgumentException::class)
    fun stopCommandRejectsNonStopAction() {
        val command = StopCommand()
        command.setAction(Command.Action.WALK)
    }

    @Test(expected = IllegalArgumentException::class)
    fun toneCommandRejectsNonToneAction() {
        val command = ToneCommand(440, 500)
        command.setAction(Command.Action.STOP)
    }

    @Test(expected = IllegalArgumentException::class)
    fun leftRightCommandRejectsInvalidAction() {
        val command = LeftRightCommand(Command.Action.TURN, Command.Direction.LEFT, 700)
        command.setAction(Command.Action.WALK)
    }

    @Test(expected = IllegalArgumentException::class)
    fun forwardBackwardCommandRejectsInvalidAction() {
        val command = ForwardBackwardCommand(Command.Action.WALK, Command.Direction.FORWARD, 700)
        command.setAction(Command.Action.TURN)
    }

    @Test(expected = IllegalArgumentException::class)
    fun animationCommandRejectsInvalidAction() {
        AnimationCommand(Command.Action.WALK)
    }

    @Test(expected = IllegalArgumentException::class)
    fun animationCommandRejectsDirection() {
        val command = AnimationCommand(Command.Action.HAPPY)
        command.setDirection(Command.Direction.FORWARD)
    }

    @Test(expected = IllegalArgumentException::class)
    fun dataRequestCommandRejectsInvalidAction() {
        DataRequestCommand(Command.Action.WALK)
    }

    @Test(expected = IllegalArgumentException::class)
    fun calibrationCommandRejectsInvalidAction() {
        val command = CalibrationCommand(CalibrationCommand.CALIBRATE_TRIM, 0, 0, 0, 0)
        command.setAction(Command.Action.STOP)
    }

    @Test(expected = IllegalArgumentException::class)
    fun ledCommandRejectsInvalidAction() {
        val command = LedCommand("000000000000000000000000000000")
        command.setAction(Command.Action.STOP)
    }
}
