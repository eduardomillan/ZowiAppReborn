package com.bq.zowi.models.commands;

/* JADX INFO: loaded from: classes.dex */
public interface Command {
    public static final String CRLN = "\r\n";

    public enum Direction {
        FORWARD,
        BACKWARD,
        LEFT,
        RIGHT
    }

    Command copy();

    Action getAction();

    Action[] getAllowedActions();

    Direction[] getAllowedDirections();

    Long[] getAllowedDurations();

    String getCommandValue();

    Direction getDirection();

    Long getDuration();

    String getId();

    boolean isRepeatible();

    void setAction(Action action);

    void setDirection(Direction direction);

    void setDuration(long j);

    public enum Action {
        STOP,
        WALK,
        TURN,
        UPDOWN,
        MOONWALKER,
        SWING,
        CRUSAITO,
        JUMP,
        FLAPPING,
        TIP_TOE,
        BEND,
        SHAKE_LEG,
        JITTER,
        ASCENDING_TURN,
        FALL,
        HAPPY,
        SUPER_HAPPY,
        SAD,
        SLEEPY,
        FART,
        CONFUSED,
        IN_LOVE,
        ANGRY,
        ANXIOUS,
        MAGIC,
        WAVE,
        VICTORY,
        GAME_OVER,
        MOUTH_SMILE,
        MOUTH_SAD,
        MOUTH_CONFUSED,
        MOUTH_BIG_SURPRISE,
        MOUTH_SMALL_SURPRISE,
        MOUTH_HAPPY_OPEN,
        MOUTH_HAPPY_CLOSED,
        MOUTH_SAD_OPEN,
        MOUTH_SAD_CLOSED,
        MOUTH_HEART,
        MOUTH_THUNDER,
        MOUTH_X,
        MOUTH_INTERROGATION,
        MOUTH_TONGUE_OUT,
        MOUTH_DIAGONAL,
        MOUTH_ANGRY,
        MOUTH_CULITO,
        MOUTH_OK,
        MOUTH_LINE,
        MOUTH_VAMP1,
        MOUTH_VAMP2,
        MOUTH_ZERO,
        MOUTH_ONE,
        MOUTH_TWO,
        MOUTH_THREE,
        MOUTH_FOUR,
        MOUTH_FIVE,
        MOUTH_SIX,
        MOUTH_SEVEN,
        MOUTH_EIGHT,
        MOUTH_NINE,
        MOUTH_EMPTY,
        MOUTH_WALRUS,
        MOUTH_CONCENTRATED,
        MOUTH_BIG_OPEN,
        MOUTH_TEETH,
        LED_MOUTH,
        TONE,
        DISTANCE,
        NOISE,
        BATTERY,
        APP_ID,
        NAME,
        SET_NAME,
        CALIBRATE_TRIM,
        CALIBRATE_GRADES;

        public String getId() {
            return toString().toLowerCase();
        }
    }
}
