package com.bq.zowi.models.commands;

import com.bq.zowi.models.commands.Command;

/* JADX INFO: loaded from: classes.dex */
public class LeftRightRunnerCommand extends MovementCommand {
    private static final Command.Action[] LEFT_RIGHT_ACTIONS = {Command.Action.TURN};
    private static final Command.Direction[] LEFT_RIGHT_DIRECTIONS = {Command.Direction.LEFT, Command.Direction.RIGHT};

    public LeftRightRunnerCommand(Command.Action action, Command.Direction direction, long duration) {
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

    @Override // com.bq.zowi.models.commands.BaseCommand, com.bq.zowi.models.commands.Command
    public void setDuration(long duration) {
        this.duration = Long.valueOf(duration);
    }
}
