package com.bq.zowi.models.commands;

import com.bq.zowi.models.commands.Command;

/* JADX INFO: loaded from: classes.dex */
public class DataRequestCommand extends MotionlessCommand {
    private static final String APP_ID = "I";
    private static final String BATTERY = "B";
    private static final Command.Action[] DATA_REQUEST_ACTIONS = {Command.Action.DISTANCE, Command.Action.NOISE, Command.Action.BATTERY, Command.Action.APP_ID, Command.Action.NAME};
    private static final String DISTANCE = "D";
    private static final String NAME = "E";
    private static final String NOISE = "N";

    public DataRequestCommand(Command.Action action) {
        setAction(action);
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command.Action[] getAllowedActions() {
        return DATA_REQUEST_ACTIONS;
    }

    @Override // com.bq.zowi.models.commands.Command
    public String getCommandValue() {
        String commandValue;
        switch (getAction()) {
            case DISTANCE:
                commandValue = DISTANCE;
                break;
            case NOISE:
                commandValue = NOISE;
                break;
            case BATTERY:
                commandValue = BATTERY;
                break;
            case APP_ID:
                commandValue = APP_ID;
                break;
            case NAME:
                commandValue = NAME;
                break;
            default:
                throw new IllegalStateException("Action not matching any valid value");
        }
        return commandValue + Command.CRLN;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command copy() {
        return new DataRequestCommand(getAction());
    }
}
