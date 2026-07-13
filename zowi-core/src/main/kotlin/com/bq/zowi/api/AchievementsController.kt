package com.bq.zowi.api

import com.bq.zowi.models.Achievement
import io.reactivex.Single

interface AchievementsController {
    fun getAchievement(id: Achievement.Id): Single<Achievement>
    fun getAchievementsList(): Single<ArrayList<Achievement>>
    fun resetAchievementsList()
    fun unlockAchievement(id: Achievement.Id): Single<Achievement>
    fun unlockAllAchievements(): Single<Void>
}
