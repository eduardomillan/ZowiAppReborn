package com.bq.zowi.models.commands;

import com.bq.zowi.models.commands.Command;

/* JADX INFO: loaded from: classes.dex */
public abstract class MotionlessCommand extends BaseCommand {
    private static final Long[] ALLOWED_DURATIONS = new Long[0];
    private static final Command.Direction[] ALLOWED_DIRECTIONS = new Command.Direction[0];

    @Override // com.bq.zowi.models.commands.Command
    public Long[] getAllowedDurations() {
        return ALLOWED_DURATIONS;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command.Direction[] getAllowedDirections() {
        return ALLOWED_DIRECTIONS;
    }

    @Override // com.bq.zowi.models.commands.Command
    public boolean isRepeatible() {
        return false;
    }
}
