package com.bq.zowi.models.commands;

import com.bq.zowi.models.commands.Command;

/* JADX INFO: loaded from: classes.dex */
public class MouthCommand extends MotionlessCommand {
    public static final String ANGRY = "000000011110100001100001000000";
    public static final String BIG_OPEN = "011110100001100001100001011110";
    public static final String BIG_SURPRISE = "001100010010100001010010001100";
    public static final String CONCENTRATED = "011000100100100100111111000000";
    public static final String CONFUSED = "000000001000010101100010000000";
    public static final String CULITO = "000000100001101101010010000000";
    public static final String DIAGONAL = "100000010000001000000100000010";
    public static final String EIGHT = "001100010010001100010010001100";
    public static final String EMPTY = "000000000000000000000000000000";
    public static final String FIVE = "011110010000011100000010011100";
    public static final String FOUR = "010010010010011110000010000010";
    public static final String HAPPY_CLOSED = "000000111111011110000000000000";
    public static final String HAPPY_OPEN = "000000111111010010001100000000";
    public static final String HEART = "010010101101100001010010001100";
    public static final String INTERROGATION = "001100010010000100000100000100";
    private static final String LED_MOUTH = "L 00";
    public static final String LINE = "000000000000111111000000000000";
    private static final Command.Action[] MOUTH_ACTIONS = {Command.Action.MOUTH_SMILE, Command.Action.MOUTH_SAD, Command.Action.MOUTH_CONFUSED, Command.Action.MOUTH_BIG_SURPRISE, Command.Action.MOUTH_SMALL_SURPRISE, Command.Action.MOUTH_HAPPY_OPEN, Command.Action.MOUTH_HAPPY_CLOSED, Command.Action.MOUTH_SAD_OPEN, Command.Action.MOUTH_SAD_CLOSED, Command.Action.MOUTH_HEART, Command.Action.MOUTH_THUNDER, Command.Action.MOUTH_X, Command.Action.MOUTH_INTERROGATION, Command.Action.MOUTH_TONGUE_OUT, Command.Action.MOUTH_DIAGONAL, Command.Action.MOUTH_ANGRY, Command.Action.MOUTH_CULITO, Command.Action.MOUTH_OK, Command.Action.MOUTH_LINE, Command.Action.MOUTH_VAMP1, Command.Action.MOUTH_VAMP2, Command.Action.MOUTH_ZERO, Command.Action.MOUTH_ONE, Command.Action.MOUTH_TWO, Command.Action.MOUTH_THREE, Command.Action.MOUTH_FOUR, Command.Action.MOUTH_FIVE, Command.Action.MOUTH_SIX, Command.Action.MOUTH_SEVEN, Command.Action.MOUTH_EIGHT, Command.Action.MOUTH_NINE, Command.Action.MOUTH_EMPTY, Command.Action.MOUTH_WALRUS, Command.Action.MOUTH_CONCENTRATED, Command.Action.MOUTH_BIG_OPEN, Command.Action.MOUTH_TEETH};
    public static final String NINE = "001100010010001110000010001110";
    public static final String OK = "000001000010010100001000000000";
    public static final String ONE = "000100001100000100000100001110";
    public static final String SAD = "000000001100010010100001000000";
    public static final String SAD_CLOSED = "000000001100011110110011000000";
    public static final String SAD_OPEN = "000000001100010010111111000000";
    public static final String SEVEN = "011110000010000100001000010000";
    public static final String SIX = "000100001000011100010010001100";
    public static final String SMALL_SURPRISE = "000000000000001100001100000000";
    public static final String SMILE = "000000100001010010001100000000";
    public static final String TEETH = "111111101101101101101101111111";
    public static final String THREE = "001100010010000100010010001100";
    public static final String THUNDER = "000100001000011100001000010000";
    public static final String TONGUE_OUT = "111111001001001001000110000000";
    public static final String TWO = "001100010010000100001000011110";
    public static final String VAMP1 = "111111101101101101010010000000";
    public static final String VAMP2 = "111111101101010010000000000000";
    public static final String WALRUS = "001100010010010010100001100001";
    public static final String X = "100001010010001100010010100001";
    public static final String ZERO = "001100010010010010010010001100";

    public MouthCommand(Command.Action action) {
        setAction(action);
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command.Action[] getAllowedActions() {
        return MOUTH_ACTIONS;
    }

    @Override // com.bq.zowi.models.commands.Command
    public String getCommandValue() {
        return LED_MOUTH + getLedMatrix() + Command.CRLN;
    }

    @Override // com.bq.zowi.models.commands.Command
    public Command copy() {
        return null;
    }

    public String getLedMatrix() {
        switch (getAction()) {
            case MOUTH_SMILE:
                return SMILE;
            case MOUTH_SAD:
                return SAD;
            case MOUTH_CONFUSED:
                return CONFUSED;
            case MOUTH_BIG_SURPRISE:
                return BIG_SURPRISE;
            case MOUTH_SMALL_SURPRISE:
                return SMALL_SURPRISE;
            case MOUTH_HAPPY_OPEN:
                return HAPPY_OPEN;
            case MOUTH_HAPPY_CLOSED:
                return HAPPY_CLOSED;
            case MOUTH_SAD_OPEN:
                return SAD_OPEN;
            case MOUTH_SAD_CLOSED:
                return SAD_CLOSED;
            case MOUTH_HEART:
                return HEART;
            case MOUTH_THUNDER:
                return THUNDER;
            case MOUTH_X:
                return X;
            case MOUTH_INTERROGATION:
                return INTERROGATION;
            case MOUTH_TONGUE_OUT:
                return TONGUE_OUT;
            case MOUTH_DIAGONAL:
                return DIAGONAL;
            case MOUTH_ANGRY:
                return ANGRY;
            case MOUTH_CULITO:
                return CULITO;
            case MOUTH_OK:
                return OK;
            case MOUTH_LINE:
                return LINE;
            case MOUTH_VAMP1:
                return VAMP1;
            case MOUTH_VAMP2:
                return VAMP2;
            case MOUTH_ZERO:
                return ZERO;
            case MOUTH_ONE:
                return ONE;
            case MOUTH_TWO:
                return TWO;
            case MOUTH_THREE:
                return THREE;
            case MOUTH_FOUR:
                return FOUR;
            case MOUTH_FIVE:
                return FIVE;
            case MOUTH_SIX:
                return SIX;
            case MOUTH_SEVEN:
                return SEVEN;
            case MOUTH_EIGHT:
                return EIGHT;
            case MOUTH_NINE:
                return NINE;
            case MOUTH_EMPTY:
                return EMPTY;
            case MOUTH_WALRUS:
                return WALRUS;
            case MOUTH_CONCENTRATED:
                return CONCENTRATED;
            case MOUTH_BIG_OPEN:
                return BIG_OPEN;
            case MOUTH_TEETH:
                return TEETH;
            default:
                return "";
        }
    }
}
