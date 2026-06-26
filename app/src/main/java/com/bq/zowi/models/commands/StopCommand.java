package com.bq.zowi.models.commands;

import com.bq.zowi.models.commands.Command;

/* JADX INFO: loaded from: classes.dex */
public class StopCommand extends MotionlessCommand {
    private static final String STOP = "S";
    private static final Command.Action[] STOP_ACTIONS = {Command.Action.STOP};

    public StopCommand() {
        setAction(Command.Action.STOP);
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command.Action[] getAllowedActions() {
        return STOP_ACTIONS;
    }

    @Override // com.bq.zowi.models.commands.Command
    public String getCommandValue() {
        return "S\r\n";
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command copy() {
        return new StopCommand();
    }
}
