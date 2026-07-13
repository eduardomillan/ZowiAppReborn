package com.bq.zowi.cli.adapters

import com.bq.zowi.api.AchievementsController
import com.bq.zowi.api.AssetProvider
import com.bq.zowi.api.KeyValueStore
import com.bq.zowi.models.Achievement
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single

class CliAchievementsController(
    private val store: KeyValueStore,
    private val assetProvider: AssetProvider,
    private val gson: Gson = Gson()
) : AchievementsController {

    companion object {
        private const val KEY_ACHIEVEMENTS_LIST = "achievementsList"
        private const val KEY_ACHIEVEMENTS_VERSION = "achievementsListVersion"
        private const val INITIAL_LIST_PATH = "achievements/initial_list.json"
        private const val CURRENT_VERSION = 2
    }

    override fun getAchievement(id: Achievement.Id): Single<Achievement> {
        return getAchievementsList().map { list ->
            list.first { it.id == id.name }
        }
    }

    override fun getAchievementsList(): Single<ArrayList<Achievement>> {
        return Single.fromCallable {
            val stored = store.getString(KEY_ACHIEVEMENTS_LIST, null)
            if (stored != null) {
                val type = object : TypeToken<ArrayList<Achievement>>() {}.type
                val list = gson.fromJson<ArrayList<Achievement>>(stored, type)
                if (list != null) return@fromCallable list
            }
            loadInitialList()
        }
    }

    override fun resetAchievementsList() {
        val list = loadInitialList()
        store.putString(KEY_ACHIEVEMENTS_LIST, gson.toJson(list))
        store.putInt(KEY_ACHIEVEMENTS_VERSION, CURRENT_VERSION)
        store.commit()
    }

    override fun unlockAchievement(id: Achievement.Id): Single<Achievement> {
        return getAchievementsList().map { list ->
            val achievement = list.first { it.id == id.name }
            achievement.unlocked = true
            persistList(list)
            achievement
        }
    }

    override fun unlockAllAchievements(): Single<Void> {
        @Suppress("UNCHECKED_CAST")
        return getAchievementsList().map { list ->
            list.forEach { it.unlocked = true }
            persistList(list)
            null as Void
        }
    }

    private fun persistList(list: ArrayList<Achievement>) {
        store.putString(KEY_ACHIEVEMENTS_LIST, gson.toJson(list))
        store.commit()
    }

    private fun loadInitialList(): ArrayList<Achievement> {
        return try {
            val inputStream = assetProvider.openAsset(INITIAL_LIST_PATH)
            val type = object : TypeToken<ArrayList<Achievement>>() {}.type
            val list: ArrayList<Achievement> = gson.fromJson(inputStream.bufferedReader().readText(), type)
            list
        } catch (e: Exception) {
            createDefaultList()
        }
    }

    private fun createDefaultList(): ArrayList<Achievement> {
        return ArrayList(Achievement.Id.values().map { id ->
            Achievement(
                id = id.name,
                type = when {
                    id.name in listOf("crusaito", "flapping", "shake_leg", "tip_toe",
                        "jitter", "ascending_turn", "swing") -> "movement"
                    id.name in listOf("super_happy", "sleepy", "fart", "confused",
                        "in_love", "angry", "anxious", "magic", "wave") -> "animation"
                    else -> "game"
                },
                unlocked = id == Achievement.Id.ascending_turn
            )
        })
    }
}
