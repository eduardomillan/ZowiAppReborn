package com.bq.zowi.adapters

import com.bq.zowi.api.GameController
import com.bq.zowi.api.KeyValueStore
import com.bq.zowi.api.RankingController
import com.bq.zowi.models.RankingEntry
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single

class AndroidCoreRankingController(
    private val store: KeyValueStore,
    private val gson: Gson = Gson()
) : RankingController {

    override fun getRanking(gameId: GameController.GAME_ID): Single<ArrayList<RankingEntry>> {
        return Single.fromCallable {
            val json = store.getString("${gameId}_ranking", null)
            if (json != null) {
                val type = object : TypeToken<ArrayList<RankingEntry>>() {}.type
                gson.fromJson<ArrayList<RankingEntry>>(json, type) ?: arrayListOf()
            } else {
                arrayListOf()
            }
        }
    }

    override fun isScoreInTop10(gameId: GameController.GAME_ID, score: Int): Single<Boolean> {
        return getRanking(gameId).map { ranking ->
            ranking.size < 10 || score > (ranking.lastOrNull()?.points ?: 0)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun resetAllRankings(): Single<Void> {
        return Single.fromCallable {
            GameController.GAME_ID.values().forEach { gameId ->
                store.remove("${gameId}_ranking")
            }
            store.commit()
            null as Void
        }
    }

    override fun saveRankingEntry(
        gameId: GameController.GAME_ID,
        entry: RankingEntry
    ): Single<ArrayList<RankingEntry>> {
        return getRanking(gameId).map { ranking ->
            ranking.add(entry)
            ranking.sortByDescending { it.points }
            if (ranking.size > 10) {
                ranking.subList(10, ranking.size).clear()
            }
            store.putString("${gameId}_ranking", gson.toJson(ranking))
            store.commit()
            ranking
        }
    }
}
