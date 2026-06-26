package com.bq.zowi.models.commands;

import com.bq.zowi.models.commands.Command;

/* JADX INFO: loaded from: classes.dex */
public class StaticCommand extends MovementCommand {
    private static final Command.Action[] STATIC_ACTIONS = {Command.Action.UPDOWN, Command.Action.JUMP, Command.Action.TIP_TOE, Command.Action.JITTER, Command.Action.ASCENDING_TURN, Command.Action.SWING};
    private static final Command.Direction[] STATIC_DIRECTIONS = new Command.Direction[0];

    public StaticCommand(Command.Action action, long duration) {
        setAction(action);
        setDuration(duration);
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command.Action[] getAllowedActions() {
        return STATIC_ACTIONS;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command.Direction[] getAllowedDirections() {
        return STATIC_DIRECTIONS;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command copy() {
        return new StaticCommand(getAction(), getDuration().longValue());
    }
}
