package com.bq.zowi.models.commands;

import com.bq.zowi.models.commands.Command;

/* JADX INFO: loaded from: classes.dex */
public class ToneCommand extends MotionlessCommand {
    private static final String TONE = "T";
    private static final Command.Action[] TONE_ACTIONS = {Command.Action.TONE};
    private int frecuency;

    public ToneCommand(int frecuency, long duration) {
        setAction(Command.Action.TONE);
        this.frecuency = frecuency;
        this.duration = Long.valueOf(duration);
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command.Action[] getAllowedActions() {
        return TONE_ACTIONS;
    }

    @Override // com.bq.zowi.models.commands.Command
    public String getCommandValue() {
        return "T " + this.frecuency + " " + this.duration + Command.CRLN;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command copy() {
        return new ToneCommand(this.frecuency, this.duration.longValue());
    }
}
