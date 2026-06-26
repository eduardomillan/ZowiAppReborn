package com.bq.zowi.models.commands;

import com.bq.zowi.models.commands.Command;

/* JADX INFO: loaded from: classes.dex */
public class CalibrationCommand extends MotionlessCommand {
    public static final String CALIBRATE_GRADES = "G";
    public static final String CALIBRATE_TRIM = "C";
    private final Command.Action[] CALIBRATE_ACTIONS = {Command.Action.CALIBRATE_TRIM, Command.Action.CALIBRATE_GRADES};
    private int leftFootRL;
    private int leftLegYL;
    private int rightFootRR;
    private int rightLegYR;
    private String type;

    public CalibrationCommand(String type, int leftLegYL, int rightLegYR, int leftFootRL, int rightFootRR) {
        if (type.equals(CALIBRATE_TRIM)) {
            setAction(Command.Action.CALIBRATE_TRIM);
        }
        if (type.equals(CALIBRATE_GRADES)) {
            setAction(Command.Action.CALIBRATE_GRADES);
        }
        this.type = type;
        this.leftLegYL = leftLegYL;
        this.rightLegYR = rightLegYR;
        this.leftFootRL = leftFootRL;
        this.rightFootRR = rightFootRR;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command.Action[] getAllowedActions() {
        return this.CALIBRATE_ACTIONS;
    }

    @Override // com.bq.zowi.models.commands.Command
    public String getCommandValue() {
        return this.type + " " + this.leftLegYL + " " + this.rightLegYR + " " + this.leftFootRL + " " + this.rightFootRR + Command.CRLN;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command copy() {
        return new CalibrationCommand(this.type, this.leftLegYL, this.rightLegYR, this.leftFootRL, this.rightFootRR);
    }
}
