package com.bq.zowi.api

import com.bq.zowi.models.RankingEntry
import io.reactivex.Single

interface RankingController {
    fun getRanking(gameId: GameController.GAME_ID): Single<ArrayList<RankingEntry>>
    fun isScoreInTop10(gameId: GameController.GAME_ID, score: Int): Single<Boolean>
    fun resetAllRankings(): Single<Void>
    fun saveRankingEntry(gameId: GameController.GAME_ID, entry: RankingEntry): Single<ArrayList<RankingEntry>>
}
