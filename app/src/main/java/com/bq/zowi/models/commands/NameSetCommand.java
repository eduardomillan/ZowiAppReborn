package com.bq.zowi.models.commands;

import com.bq.zowi.models.commands.Command;

/* JADX INFO: loaded from: classes.dex */
public class NameSetCommand extends MotionlessCommand {
    private static final Command.Action[] NAME_SET_ACTIONS = {Command.Action.SET_NAME};
    private static final String SET_NAME = "R";
    private final String name;

    public NameSetCommand(String name) {
        this.name = name;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command.Action[] getAllowedActions() {
        return NAME_SET_ACTIONS;
    }

    @Override // com.bq.zowi.models.commands.Command
    public String getCommandValue() {
        return "R " + this.name + Command.CRLN;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command copy() {
        return new NameSetCommand(this.name);
    }
}
