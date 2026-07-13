package com.bq.zowi.cli.adapters

import com.bq.zowi.api.GameController
import com.bq.zowi.api.KeyValueStore
import com.google.gson.Gson
import io.reactivex.Single
import java.lang.reflect.Type

class CliGameController(
    private val store: KeyValueStore,
    private val gson: Gson = Gson()
) : GameController {

    override fun isFirstPlay(gameId: GameController.GAME_ID, forceFirst: Boolean): Single<Boolean> {
        return Single.fromCallable {
            if (forceFirst) true
            else store.getBoolean("${gameId}_is_first_play", true)
        }
    }

    override fun <T> loadProgress(gameId: GameController.GAME_ID, clazz: Class<T>): Single<T> {
        return Single.fromCallable {
            val json = store.getString("${gameId}_progress", null)
            if (json != null) gson.fromJson(json, clazz)
            else null as T
        }
    }

    override fun <T> loadProgress(gameId: GameController.GAME_ID, type: Type): Single<T> {
        return Single.fromCallable {
            val json = store.getString("${gameId}_progress", null)
            if (json != null) gson.fromJson<T>(json, type)
            else null as T
        }
    }

    override fun resetGamesProgress(): Single<Void> {
        @Suppress("UNCHECKED_CAST")
        return Single.fromCallable {
            GameController.GAME_ID.values().forEach { gameId ->
                store.remove("${gameId}_is_first_play")
                store.remove("${gameId}_progress")
            }
            store.commit()
            null as Void
        }
    }

    override fun saveProgress(gameId: GameController.GAME_ID, data: Any): Single<Void> {
        @Suppress("UNCHECKED_CAST")
        return Single.fromCallable {
            store.putBoolean("${gameId}_is_first_play", false)
            store.putString("${gameId}_progress", gson.toJson(data))
            store.commit()
            null as Void
        }
    }
}
