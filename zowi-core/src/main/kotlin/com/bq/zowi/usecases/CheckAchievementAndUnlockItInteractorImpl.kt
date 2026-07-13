package com.bq.zowi.usecases

import com.bq.zowi.api.AchievementsController
import com.bq.zowi.models.Achievement
import com.bq.zowi.models.commands.AnimationCommand
import com.bq.zowi.models.commands.Command
import io.reactivex.Single

class CheckAchievementAndUnlockItInteractorImpl(
    private val achievementsController: AchievementsController,
    private val sendCommandToZowiInteractor: SendCommandToZowiInteractor
) : CheckAchievementAndUnlockItInteractor {

    override fun checkAchievementAndUnlockIt(achievementId: Achievement.Id): Single<Achievement> {
        return achievementsController.getAchievement(achievementId)
            .flatMap { achievement ->
                if (achievement.unlocked) {
                    Single.just(null)
                } else {
                    sendCommandToZowiInteractor.sendCommandToZowi(
                        AnimationCommand(Command.Action.VICTORY)
                    ).subscribe(object : io.reactivex.CompletableObserver {
                        override fun onSubscribe(d: io.reactivex.disposables.Disposable) {}
                        override fun onComplete() {}
                        override fun onError(e: Throwable) {
                            com.bq.zowi.utils.Grove.d("Send COMMAND to Zowi ERROR! ${e.message}")
                        }
                    })
                    achievementsController.unlockAchievement(achievementId)
                }
            }
    }
}
