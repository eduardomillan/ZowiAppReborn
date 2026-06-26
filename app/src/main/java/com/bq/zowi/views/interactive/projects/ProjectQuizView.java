package com.bq.zowi.views.interactive.projects;

import com.bq.zowi.models.viewmodels.AchievementViewModel;
import com.bq.zowi.models.viewmodels.ProjectViewModel;
import com.bq.zowi.views.interactive.InteractiveBaseView;

/* JADX INFO: loaded from: classes.dex */
public interface ProjectQuizView extends InteractiveBaseView {
    void paintProjectQuiz(ProjectViewModel projectViewModel);

    @Override // com.bq.zowi.views.interactive.InteractiveBaseView
    void showAchievementUnlock(AchievementViewModel achievementViewModel);

    void showFailureFeedback();

    void showProjectQuizLoadingError();

    void showQuizCompleteWithoutAchievement();
}
