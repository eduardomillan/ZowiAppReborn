package com.bq.zowi.models.commands;

/* JADX INFO: loaded from: classes.dex */
public class GridCommand {
    private Command command;
    private boolean unlocked = true;

    public GridCommand(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return this.command;
    }

    public boolean isUnlocked() {
        return this.unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
    }

    public String getCommandId() {
        return getCommand().getId();
    }
}
