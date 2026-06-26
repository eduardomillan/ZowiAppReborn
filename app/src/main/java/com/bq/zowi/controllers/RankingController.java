package com.bq.zowi.controllers;

import com.bq.zowi.controllers.GameController;
import com.bq.zowi.models.RankingEntry;
import java.util.ArrayList;
import rx.Single;

/* JADX INFO: loaded from: classes.dex */
public interface RankingController {
    Single<ArrayList<RankingEntry>> getRanking(GameController.GAME_ID game_id);

    Single<Boolean> isScoreInTop10(GameController.GAME_ID game_id, int i);

    Single<Void> resetAllRankings();

    Single<ArrayList<RankingEntry>> saveRankingEntry(GameController.GAME_ID game_id, RankingEntry rankingEntry);
}
