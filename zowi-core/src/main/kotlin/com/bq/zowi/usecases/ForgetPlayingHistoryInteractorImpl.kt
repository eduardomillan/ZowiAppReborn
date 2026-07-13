package com.bq.zowi.usecases

import com.bq.zowi.api.AchievementsController
import com.bq.zowi.api.AppController
import com.bq.zowi.api.GameController
import com.bq.zowi.api.ProjectController
import com.bq.zowi.api.RankingController
import io.reactivex.Completable

class ForgetPlayingHistoryInteractorImpl(
    private val projectController: ProjectController,
    private val appController: AppController,
    private val gameController: GameController,
    private val rankingController: RankingController,
    private val achievementsController: AchievementsController
) : ForgetPlayingHistoryInteractor {

    override fun forgetPlayingHistory(): Completable {
        return projectController.resetAllProjects()
            .ignoreElement()
            .andThen(appController.resetAppLogs().ignoreElement())
            .andThen(gameController.resetGamesProgress().ignoreElement())
            .andThen(rankingController.resetAllRankings().ignoreElement())
            .andThen(Completable.fromAction {
                achievementsController.resetAchievementsList()
            })
    }
}
