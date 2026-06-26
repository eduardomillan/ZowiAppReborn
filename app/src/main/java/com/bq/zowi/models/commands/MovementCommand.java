package com.bq.zowi.models.commands;

import com.bq.zowi.models.commands.Command;

/* JADX INFO: loaded from: classes.dex */
public abstract class MovementCommand extends BaseCommand {
    public static final Long[] ALLOWED_DURATIONS = {700L, 1000L, 2000L};
    private static final String ANGRY = "H 8";
    private static final String ANXIOUS = "H 9";
    private static final String ASCENDING_TURN = "M 20";
    private static final String BEND_LEFT = "M 15";
    private static final String BEND_RIGHT = "M 16";
    private static final String CONFUSED = "H 6";
    private static final String CRUSAITO_LEFT = "M 9";
    private static final String CRUSAITO_RIGHT = "M 10";
    private static final String CRUSAITO_SIZE = "30";
    private static final String FALL = "M 7";
    private static final String FALL_SIZE = "65";
    private static final String FART = "H 5";
    private static final String FLAPPING_BACKWARD = "M 13";
    private static final String FLAPPING_FORWARD = "M 12";
    private static final String FLAPPING_SIZE = "10";
    private static final String GAME_OVER = "H 13";
    private static final String HAPPY = "H 1";
    private static final String IN_LOVE = "H 7";
    private static final String JITTER = "M 19";
    private static final String JUMP = "M 11";
    private static final String MAGIC = "H 10";
    private static final String MOONWALKER_LEFT = "M 6";
    private static final String MOONWALKER_RIGHT = "M 7";
    private static final String MOONWALKER_SIZE = "30";
    private static final String SAD = "H 3";
    private static final String SHAKE_LEG_LEFT = "M 17";
    private static final String SHAKE_LEG_RIGHT = "M 18";
    private static final String SLEEPY = "H 4";
    private static final String SUPER_HAPPY = "H 2";
    private static final String SWING = "M 8";
    private static final String TIP_TOE = "M 14";
    private static final String TURN_LEFT = "M 3";
    private static final String TURN_RIGHT = "M 4";
    private static final String UPDOWN = "M 5";
    private static final String VICTORY = "H 12";
    private static final String WALK_BACKWARD = "M 2";
    private static final String WALK_FORWARD = "M 1";
    private static final String WAVE = "H 11";

    @Override // com.bq.zowi.models.commands.Command
    public Long[] getAllowedDurations() {
        return ALLOWED_DURATIONS;
    }

    @Override // com.bq.zowi.models.commands.Command
    public boolean isRepeatible() {
        return true;
    }

    @Override // com.bq.zowi.models.commands.Command
    public String getCommandValue() {
        String commandValue;
        String commandExtras = "";
        Command.Direction direction = getDirection();
        switch (getAction()) {
            case WALK:
                if (direction == Command.Direction.FORWARD) {
                    commandValue = WALK_FORWARD;
                } else {
                    commandValue = WALK_BACKWARD;
                }
                break;
            case TURN:
                if (direction == Command.Direction.LEFT) {
                    commandValue = TURN_LEFT;
                } else {
                    commandValue = TURN_RIGHT;
                }
                break;
            case MOONWALKER:
                if (direction == Command.Direction.LEFT) {
                    commandValue = MOONWALKER_LEFT;
                } else {
                    commandValue = "M 7";
                }
                commandExtras = "30";
                break;
            case CRUSAITO:
                if (direction == Command.Direction.LEFT) {
                    commandValue = CRUSAITO_LEFT;
                } else {
                    commandValue = CRUSAITO_RIGHT;
                }
                commandExtras = "30";
                break;
            case FLAPPING:
                if (direction == Command.Direction.FORWARD) {
                    commandValue = FLAPPING_FORWARD;
                } else {
                    commandValue = FLAPPING_BACKWARD;
                }
                commandExtras = FLAPPING_SIZE;
                break;
            case BEND:
                if (direction == Command.Direction.LEFT) {
                    commandValue = BEND_LEFT;
                } else {
                    commandValue = BEND_RIGHT;
                }
                break;
            case SHAKE_LEG:
                if (direction == Command.Direction.LEFT) {
                    commandValue = SHAKE_LEG_LEFT;
                } else {
                    commandValue = SHAKE_LEG_RIGHT;
                }
                break;
            case UPDOWN:
                commandValue = UPDOWN;
                break;
            case JUMP:
                commandValue = JUMP;
                break;
            case TIP_TOE:
                commandValue = TIP_TOE;
                break;
            case JITTER:
                commandValue = JITTER;
                break;
            case ASCENDING_TURN:
                commandValue = ASCENDING_TURN;
                break;
            case SWING:
                commandValue = SWING;
                break;
            case FALL:
                commandValue = "M 7";
                commandExtras = FALL_SIZE;
                break;
            case HAPPY:
                commandValue = HAPPY;
                break;
            case SUPER_HAPPY:
                commandValue = SUPER_HAPPY;
                break;
            case SAD:
                commandValue = SAD;
                break;
            case SLEEPY:
                commandValue = SLEEPY;
                break;
            case FART:
                commandValue = FART;
                break;
            case CONFUSED:
                commandValue = CONFUSED;
                break;
            case IN_LOVE:
                commandValue = IN_LOVE;
                break;
            case ANGRY:
                commandValue = ANGRY;
                break;
            case ANXIOUS:
                commandValue = ANXIOUS;
                break;
            case MAGIC:
                commandValue = MAGIC;
                break;
            case WAVE:
                commandValue = WAVE;
                break;
            case VICTORY:
                commandValue = VICTORY;
                break;
            case GAME_OVER:
                commandValue = GAME_OVER;
                break;
            default:
                throw new IllegalStateException("Action not matching any valid value");
        }
        if (getDuration() != null) {
            commandValue = commandValue + " " + getDuration();
        }
        return (commandValue + " " + commandExtras) + Command.CRLN;
    }
}
