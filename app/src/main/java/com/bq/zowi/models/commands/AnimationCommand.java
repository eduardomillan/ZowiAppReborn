package com.bq.zowi.models.commands;

import com.bq.zowi.models.commands.Command;

/* JADX INFO: loaded from: classes.dex */
public class AnimationCommand extends MovementCommand {
    private static final Command.Action[] GESTURE_ACTIONS = {Command.Action.HAPPY, Command.Action.SUPER_HAPPY, Command.Action.SAD, Command.Action.SLEEPY, Command.Action.FART, Command.Action.CONFUSED, Command.Action.IN_LOVE, Command.Action.ANGRY, Command.Action.ANXIOUS, Command.Action.MAGIC, Command.Action.WAVE, Command.Action.VICTORY, Command.Action.GAME_OVER};
    private static final Command.Direction[] GESTURE_DIRECTIONS = new Command.Direction[0];
    private static final Long[] GESTURE_DURATIONS = new Long[0];

    public AnimationCommand(Command.Action action) {
        setAction(action);
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command.Action[] getAllowedActions() {
        return GESTURE_ACTIONS;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command.Direction[] getAllowedDirections() {
        return GESTURE_DIRECTIONS;
    }

    @Override // com.bq.zowi.models.commands.MovementCommand, com.bq.zowi.models.commands.Command
    public Long[] getAllowedDurations() {
        return GESTURE_DURATIONS;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command copy() {
        return null;
    }
}
