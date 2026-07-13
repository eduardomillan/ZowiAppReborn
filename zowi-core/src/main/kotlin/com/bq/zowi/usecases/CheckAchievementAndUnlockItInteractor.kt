package com.bq.zowi.usecases

import com.bq.zowi.models.Achievement
import io.reactivex.Single

interface CheckAchievementAndUnlockItInteractor {
    fun checkAchievementAndUnlockIt(achievementId: Achievement.Id): Single<Achievement>
}
