package com.bq.zowi.bridge;

import com.bq.zowi.controllers.GameController;
import com.bq.zowi.controllers.RankingController;
import com.bq.zowi.models.RankingEntry;
import java.util.ArrayList;
import rx.Single;

public class RankingControllerBridge implements RankingController {
    private final com.bq.zowi.api.RankingController core;

    public RankingControllerBridge(com.bq.zowi.api.RankingController core) {
        this.core = core;
    }

    @Override
    public Single<ArrayList<RankingEntry>> getRanking(GameController.GAME_ID gameId) {
        return RxBridge.toRxSingle(core.getRanking(mapGameId(gameId)));
    }

    @Override
    public Single<Boolean> isScoreInTop10(GameController.GAME_ID gameId, int score) {
        return RxBridge.toRxSingle(core.isScoreInTop10(mapGameId(gameId), score));
    }

    @Override
    public Single<Void> resetAllRankings() {
        return RxBridge.toRxSingle(core.resetAllRankings());
    }

    @Override
    public Single<ArrayList<RankingEntry>> saveRankingEntry(GameController.GAME_ID gameId, RankingEntry entry) {
        return RxBridge.toRxSingle(core.saveRankingEntry(mapGameId(gameId), entry));
    }

    private com.bq.zowi.api.GameController.GAME_ID mapGameId(GameController.GAME_ID oldId) {
        return com.bq.zowi.api.GameController.GAME_ID.valueOf(oldId.name());
    }
}
