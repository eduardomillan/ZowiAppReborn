package com.bq.zowi.bridge;

import com.bq.zowi.controllers.GameController;
import java.lang.reflect.Type;
import rx.Single;

public class GameControllerBridge implements GameController {
    private final com.bq.zowi.api.GameController core;

    public GameControllerBridge(com.bq.zowi.api.GameController core) {
        this.core = core;
    }

    @Override
    public Single<Boolean> isFirstPlay(GAME_ID gameId, boolean forceFirst) {
        return RxBridge.toRxSingle(core.isFirstPlay(mapGameId(gameId), forceFirst));
    }

    @Override
    public <T> Single<T> loadProgress(GAME_ID gameId, Class<T> clazz) {
        return RxBridge.toRxSingle(core.loadProgress(mapGameId(gameId), clazz));
    }

    @Override
    public <T> Single<T> loadProgress(GAME_ID gameId, Type type) {
        return RxBridge.toRxSingle(core.loadProgress(mapGameId(gameId), type));
    }

    @Override
    public Single<Void> resetGamesProgress() {
        return RxBridge.toRxSingle(core.resetGamesProgress());
    }

    @Override
    public Single<Void> saveProgress(GAME_ID gameId, Object data) {
        return RxBridge.toRxSingle(core.saveProgress(mapGameId(gameId), data));
    }

    private com.bq.zowi.api.GameController.GAME_ID mapGameId(GAME_ID oldId) {
        return com.bq.zowi.api.GameController.GAME_ID.valueOf(oldId.name());
    }
}
