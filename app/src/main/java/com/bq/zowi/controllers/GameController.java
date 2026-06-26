package com.bq.zowi.controllers;

import java.lang.reflect.Type;
import rx.Single;

/* JADX INFO: loaded from: classes.dex */
public interface GameController {

    public enum GAME_ID {
        ZOWI_SAYS_GAME_ID,
        MOUTHS_GAME_ID,
        TIMELINE_GAME_ID,
        GAMEPAD_GAME_ID
    }

    Single<Boolean> isFirstPlay(GAME_ID game_id, boolean z);

    <T> Single<T> loadProgress(GAME_ID game_id, Class<T> cls);

    <T> Single<T> loadProgress(GAME_ID game_id, Type type);

    Single<Void> resetGamesProgress();

    Single<Void> saveProgress(GAME_ID game_id, Object obj);
}
