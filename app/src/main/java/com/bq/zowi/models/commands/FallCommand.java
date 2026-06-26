package com.bq.zowi.models.commands;

import com.bq.zowi.models.commands.Command;

/* JADX INFO: loaded from: classes.dex */
public class FallCommand extends MovementCommand {
    private static final Command.Action[] FALL_ACTION = {Command.Action.FALL};
    private static final Command.Direction[] FALL_DIRECTIONS = new Command.Direction[0];
    private static final long FALL_DURATION = 2000;

    public FallCommand(Command.Action action) {
        setAction(action);
        setDuration(FALL_DURATION);
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command.Action[] getAllowedActions() {
        return FALL_ACTION;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command.Direction[] getAllowedDirections() {
        return FALL_DIRECTIONS;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command copy() {
        return new FallCommand(getAction());
    }
}
