package com.bq.zowi.models.commands;

import com.bq.zowi.models.commands.Command;

/* JADX INFO: loaded from: classes.dex */
public class ForwardBackwardCommand extends MovementCommand {
    private static final Command.Action[] FORWARD_BACKWARD_ACTIONS = {Command.Action.WALK, Command.Action.FLAPPING};
    private static final Command.Direction[] FORWARD_BACKWARD_DIRECTIONS = {Command.Direction.FORWARD, Command.Direction.BACKWARD};

    public ForwardBackwardCommand(Command.Action action, Command.Direction direction, long duration) {
        setAction(action);
        setDirection(direction);
        setDuration(duration);
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command.Action[] getAllowedActions() {
        return FORWARD_BACKWARD_ACTIONS;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command.Direction[] getAllowedDirections() {
        return FORWARD_BACKWARD_DIRECTIONS;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command copy() {
        return new ForwardBackwardCommand(getAction(), getDirection(), getDuration().longValue());
    }
}
