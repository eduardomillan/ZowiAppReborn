package com.bq.zowi.api

import io.reactivex.Single

interface GameController {
    enum class GAME_ID {
        ZOWI_SAYS_GAME_ID,
        MOUTHS_GAME_ID,
        TIMELINE_GAME_ID,
        GAMEPAD_GAME_ID
    }

    fun isFirstPlay(gameId: GAME_ID, forceFirst: Boolean): Single<Boolean>
    fun <T> loadProgress(gameId: GAME_ID, clazz: Class<T>): Single<T>
    fun <T> loadProgress(gameId: GAME_ID, type: java.lang.reflect.Type): Single<T>
    fun resetGamesProgress(): Single<Void>
    fun saveProgress(gameId: GAME_ID, data: Any): Single<Void>
}
