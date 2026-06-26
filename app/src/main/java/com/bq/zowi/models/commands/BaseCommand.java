package com.bq.zowi.models.commands;

import com.bq.zowi.models.commands.Command;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseCommand implements Command {
    private Command.Action action;
    private Command.Direction direction;
    protected Long duration;

    @Override // com.bq.zowi.models.commands.Command
    public Command.Action getAction() {
        return this.action;
    }

    @Override // com.bq.zowi.models.commands.Command
    public void setAction(Command.Action action) {
        if (!Arrays.asList(getAllowedActions()).contains(action)) {
            throw new IllegalArgumentException("The action " + action.toString() + " is not valid here.");
        }
        this.action = action;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command.Direction getDirection() {
        return this.direction;
    }

    @Override // com.bq.zowi.models.commands.Command
    public void setDirection(Command.Direction direction) {
        if (!Arrays.asList(getAllowedDirections()).contains(direction)) {
            throw new IllegalArgumentException("The direction " + direction.toString() + " is not valid for action " + getAction().toString());
        }
        this.direction = direction;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Long getDuration() {
        return this.duration;
    }

    @Override // com.bq.zowi.models.commands.Command
    public void setDuration(long duration) {
        if (!Arrays.asList(getAllowedDurations()).contains(Long.valueOf(duration))) {
            throw new IllegalArgumentException("The duration " + duration + " is not valid for action " + getAction().toString());
        }
        this.duration = Long.valueOf(duration);
    }

    @Override // com.bq.zowi.models.commands.Command
    public String getId() {
        return getAction().getId();
    }
}
