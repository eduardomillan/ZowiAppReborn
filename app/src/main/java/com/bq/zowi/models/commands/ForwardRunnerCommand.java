package com.bq.zowi.models.commands;

import com.bq.zowi.models.commands.Command;

/* JADX INFO: loaded from: classes.dex */
public class ForwardRunnerCommand extends MovementCommand {
    private static final Command.Action[] FORWARD_ACTION = {Command.Action.WALK};
    private static final Command.Direction[] FORWARD_DIRECTION = {Command.Direction.FORWARD};

    public ForwardRunnerCommand(Command.Action action, Command.Direction direction, long duration) {
        setAction(action);
        setDirection(direction);
        setDuration(duration);
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command.Action[] getAllowedActions() {
        return FORWARD_ACTION;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command.Direction[] getAllowedDirections() {
        return FORWARD_DIRECTION;
    }

    @Override // com.bq.zowi.models.commands.BaseCommand, com.bq.zowi.models.commands.Command
    public void setDuration(long duration) {
        this.duration = Long.valueOf(duration);
    }

    @Override // com.bq.zowi.models.commands.MovementCommand, com.bq.zowi.models.commands.Command
    public boolean isRepeatible() {
        return false;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command copy() {
        return new ForwardBackwardCommand(getAction(), getDirection(), getDuration().longValue());
    }
}
