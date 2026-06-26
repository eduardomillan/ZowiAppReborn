package com.bq.zowi.models;

/* JADX INFO: loaded from: classes.dex */
public class Achievement {
    public String id;
    public String type;
    public boolean unlocked;

    public enum Id {
        crusaito,
        flapping,
        shake_leg,
        tip_toe,
        jitter,
        ascending_turn,
        swing,
        super_happy,
        sleepy,
        fart,
        confused,
        in_love,
        angry,
        anxious,
        magic,
        wave,
        mouths_editor
    }

    public enum Type {
        movement,
        animation,
        game
    }

    public Achievement(String id, String type, boolean unlocked) {
        this.id = id;
        this.type = type;
        this.unlocked = unlocked;
    }
}
