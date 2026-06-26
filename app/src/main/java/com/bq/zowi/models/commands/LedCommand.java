package com.bq.zowi.models.commands;

import com.bq.zowi.models.commands.Command;

/* JADX INFO: loaded from: classes.dex */
public class LedCommand extends MotionlessCommand {
    private static final String LED_MOUTH = "L 00";
    private static final Command.Action[] LED_MOUTH_ACTIONS = {Command.Action.LED_MOUTH};
    private String ledMatrix;

    public LedCommand(String ledMatrix) {
        setAction(Command.Action.LED_MOUTH);
        this.ledMatrix = ledMatrix;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command.Action[] getAllowedActions() {
        return LED_MOUTH_ACTIONS;
    }

    @Override // com.bq.zowi.models.commands.Command
    public String getCommandValue() {
        return LED_MOUTH + this.ledMatrix + Command.CRLN;
    }

    public String getLedMatrix() {
        return this.ledMatrix;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command copy() {
        return new LedCommand(this.ledMatrix);
    }
}
