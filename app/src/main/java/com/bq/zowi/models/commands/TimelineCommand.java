package com.bq.zowi.models.commands;

/* JADX INFO: loaded from: classes.dex */
public class TimelineCommand {
    private Command command;
    private boolean isThisCommandBeingPlayed = false;
    private boolean isTimelineBeingPlayed = false;
    private int repetitions;

    public TimelineCommand(Command command, int repetitions) {
        this.command = command;
        this.repetitions = repetitions;
    }

    public Command getCommand() {
        return this.command;
    }

    public int getRepetitions() {
        return this.repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public void setIsBeingPlayed(boolean isBeingPlayed) {
        this.isThisCommandBeingPlayed = isBeingPlayed;
    }

    public void setIsTimelineBeingPlayed(boolean isTimelineBeingPlayed) {
        this.isTimelineBeingPlayed = isTimelineBeingPlayed;
    }

    public boolean isTimelineBeingPlayed() {
        return this.isTimelineBeingPlayed;
    }

    public boolean isThisCommandBeingPlayed() {
        return this.isThisCommandBeingPlayed;
    }

    public TimelineCommand copy() {
        return new TimelineCommand(this.command.copy(), this.repetitions);
    }
}
