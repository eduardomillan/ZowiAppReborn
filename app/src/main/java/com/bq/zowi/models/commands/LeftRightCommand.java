package com.bq.zowi.models.commands;

import com.bq.zowi.models.commands.Command;

/* JADX INFO: loaded from: classes.dex */
public class LeftRightCommand extends MovementCommand {
    private static final Command.Action[] LEFT_RIGHT_ACTIONS = {Command.Action.TURN, Command.Action.MOONWALKER, Command.Action.CRUSAITO, Command.Action.BEND, Command.Action.SHAKE_LEG};
    private static final Command.Direction[] LEFT_RIGHT_DIRECTIONS = {Command.Direction.LEFT, Command.Direction.RIGHT};

    public LeftRightCommand(Command.Action action, Command.Direction direction, long duration) {
        setAction(action);
        setDirection(direction);
        setDuration(duration);
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command.Action[] getAllowedActions() {
        return LEFT_RIGHT_ACTIONS;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command.Direction[] getAllowedDirections() {
        return LEFT_RIGHT_DIRECTIONS;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command copy() {
        return new LeftRightCommand(getAction(), getDirection(), getDuration().longValue());
    }
}
