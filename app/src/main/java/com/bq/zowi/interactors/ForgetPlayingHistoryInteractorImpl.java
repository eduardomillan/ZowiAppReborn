package com.bq.zowi.interactors;

import com.bq.zowi.controllers.AchievementsController;
import com.bq.zowi.controllers.AppController;
import com.bq.zowi.controllers.GameController;
import com.bq.zowi.controllers.ProjectController;
import com.bq.zowi.controllers.RankingController;
import rx.Single;
import rx.functions.Func1;

/* JADX INFO: loaded from: classes.dex */
public class ForgetPlayingHistoryInteractorImpl implements ForgetPlayingHistoryInteractor {
    private final AchievementsController achievementsController;
    private final AppController appController;
    private final GameController gameController;
    private final ProjectController projectController;
    private final RankingController rankingController;

    public ForgetPlayingHistoryInteractorImpl(ProjectController projectController, AppController appController, GameController gameController, RankingController rankingController, AchievementsController achievementsController) {
        this.projectController = projectController;
        this.appController = appController;
        this.gameController = gameController;
        this.rankingController = rankingController;
        this.achievementsController = achievementsController;
    }

    @Override // com.bq.zowi.interactors.ForgetPlayingHistoryInteractor
    public Single<Void> forgetPlayingHistory() {
        return this.projectController.resetAllProjects().flatMap(new Func1<Void, Single<? extends Void>>() { // from class: com.bq.zowi.interactors.ForgetPlayingHistoryInteractorImpl.4
            @Override // rx.functions.Func1
            public Single<? extends Void> call(Void aVoid) {
                return ForgetPlayingHistoryInteractorImpl.this.appController.resetAppLogs();
            }
        }).flatMap(new Func1<Void, Single<? extends Void>>() { // from class: com.bq.zowi.interactors.ForgetPlayingHistoryInteractorImpl.3
            @Override // rx.functions.Func1
            public Single<? extends Void> call(Void aVoid) {
                return ForgetPlayingHistoryInteractorImpl.this.gameController.resetGamesProgress();
            }
        }).flatMap(new Func1<Void, Single<? extends Void>>() { // from class: com.bq.zowi.interactors.ForgetPlayingHistoryInteractorImpl.2
            @Override // rx.functions.Func1
            public Single<? extends Void> call(Void aVoid) {
                return ForgetPlayingHistoryInteractorImpl.this.rankingController.resetAllRankings();
            }
        }).map(new Func1<Void, Void>() { // from class: com.bq.zowi.interactors.ForgetPlayingHistoryInteractorImpl.1
            @Override // rx.functions.Func1
            public Void call(Void aVoid) {
                ForgetPlayingHistoryInteractorImpl.this.achievementsController.resetAchievementsList();
                return null;
            }
        });
    }
}
