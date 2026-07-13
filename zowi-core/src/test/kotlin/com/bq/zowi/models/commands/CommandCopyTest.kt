package com.bq.zowi.models.commands

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotSame
import org.junit.Test

class CommandCopyTest {

    @Test
    fun stopCommandCopyIsIndependent() {
        val original = StopCommand()
        val copy = original.copy() as StopCommand

        assertNotSame(original, copy)
        assertEquals(original.getCommandValue(), copy.getCommandValue())
        assertEquals(original.getAction(), copy.getAction())
    }

    @Test
    fun forwardBackwardCommandCopyPreservesOriginalOnDirectionChange() {
        val original = ForwardBackwardCommand(Command.Action.WALK, Command.Direction.FORWARD, 700)
        val copy = original.copy() as ForwardBackwardCommand

        assertNotSame(original, copy)
        assertEquals(original.getCommandValue(), copy.getCommandValue())

        copy.setDirection(Command.Direction.BACKWARD)

        assertEquals(Command.Direction.FORWARD, original.getDirection())
        assertEquals(Command.Direction.BACKWARD, copy.getDirection())
    }

    @Test
    fun forwardBackwardCommandCopyPreservesOriginalOnDurationChange() {
        val original = ForwardBackwardCommand(Command.Action.WALK, Command.Direction.FORWARD, 700)
        val copy = original.copy() as ForwardBackwardCommand

        copy.setDuration(2000)

        assertEquals(700L, original.getDuration())
        assertEquals(2000L, copy.getDuration())
    }

    @Test
    fun leftRightCommandCopyPreservesOriginalOnDirectionChange() {
        val original = LeftRightCommand(Command.Action.TURN, Command.Direction.LEFT, 1000)
        val copy = original.copy() as LeftRightCommand

        assertNotSame(original, copy)
        assertEquals(original.getCommandValue(), copy.getCommandValue())

        copy.setDirection(Command.Direction.RIGHT)

        assertEquals(Command.Direction.LEFT, original.getDirection())
        assertEquals(Command.Direction.RIGHT, copy.getDirection())
    }

    @Test
    fun leftRightCommandCopyGetCommandValueMatchesOriginal() {
        val original = LeftRightCommand(Command.Action.MOONWALKER, Command.Direction.LEFT, 2000)
        val copy = original.copy()

        assertEquals(original.getCommandValue(), copy.getCommandValue())
    }

    @Test
    fun staticCommandCopyIsIndependent() {
        val original = StaticCommand(Command.Action.JUMP, 1000)
        val copy = original.copy() as StaticCommand

        assertNotSame(original, copy)
        assertEquals(original.getCommandValue(), copy.getCommandValue())
        assertEquals(original.getAction(), copy.getAction())
        assertEquals(original.getDuration(), copy.getDuration())
    }

    @Test
    fun toneCommandCopyIsIndependent() {
        val original = ToneCommand(440, 500)
        val copy = original.copy() as ToneCommand

        assertNotSame(original, copy)
        assertEquals(original.getCommandValue(), copy.getCommandValue())
    }

    @Test
    fun mouthCommandCopyIsIndependent() {
        val original = MouthCommand(Command.Action.MOUTH_SMILE)
        val copy = original.copy() as MouthCommand

        assertNotSame(original, copy)
        assertEquals(original.getCommandValue(), copy.getCommandValue())
        assertEquals(original.getAction(), copy.getAction())
    }

    @Test
    fun mouthCommandCopyPreservesOriginalOnActionChange() {
        val original = MouthCommand(Command.Action.MOUTH_SMILE)
        val copy = original.copy()

        assertEquals(original.getCommandValue(), copy.getCommandValue())
        assertEquals(original.getAction(), copy.getAction())
    }

    @Test
    fun nameSetCommandCopyIsIndependent() {
        val original = NameSetCommand("Zowi")
        val copy = original.copy()

        assertNotSame(original, copy)
        assertEquals(original.getCommandValue(), copy.getCommandValue())
    }

    @Test
    fun ledCommandCopyIsIndependent() {
        val matrix = "111111101101101101101101111111"
        val original = LedCommand(matrix)
        val copy = original.copy() as LedCommand

        assertNotSame(original, copy)
        assertEquals(original.getCommandValue(), copy.getCommandValue())
        assertEquals(original.getLedMatrix(), copy.getLedMatrix())
    }

    @Test
    fun calibrationCommandCopyIsIndependent() {
        val original = CalibrationCommand(CalibrationCommand.CALIBRATE_TRIM, 10, 20, 30, 40)
        val copy = original.copy()

        assertNotSame(original, copy)
        assertEquals(original.getCommandValue(), copy.getCommandValue())
    }

    @Test
    fun animationCommandCopyPreservesOriginalOnCopy() {
        val original = AnimationCommand(Command.Action.HAPPY)
        val copy = original.copy()

        assertNotSame(original, copy)
        assertEquals(original.getCommandValue(), copy.getCommandValue())
        assertEquals(original.getAction(), copy.getAction())
    }

    @Test
    fun fallCommandCopyIsIndependent() {
        val original = FallCommand(Command.Action.FALL)
        val copy = original.copy()

        assertNotSame(original, copy)
        assertEquals(original.getCommandValue(), copy.getCommandValue())
    }

    @Test
    fun dataRequestCommandCopyIsIndependent() {
        val original = DataRequestCommand(Command.Action.DISTANCE)
        val copy = original.copy()

        assertNotSame(original, copy)
        assertEquals(original.getCommandValue(), copy.getCommandValue())
    }

    @Test
    fun timelineCommandCopyCreatesIndependentCommandCopy() {
        val inner = ForwardBackwardCommand(Command.Action.WALK, Command.Direction.FORWARD, 700)
        val original = TimelineCommand(inner, 3)
        val copy = original.copy()

        assertNotSame(original, copy)
        assertNotSame(original.command, copy.command)
        assertEquals(original.repetitions, copy.repetitions)
        assertEquals(original.command.getCommandValue(), copy.command.getCommandValue())
    }
}
