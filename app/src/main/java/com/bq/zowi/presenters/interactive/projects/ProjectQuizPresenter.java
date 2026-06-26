package com.bq.zowi.presenters.interactive.projects;

import com.bq.zowi.presenters.interactive.InteractiveBasePresenter;
import com.bq.zowi.views.interactive.projects.ProjectQuizView;
import com.bq.zowi.wireframes.projects.ProjectQuizWireframe;

/* JADX INFO: loaded from: classes.dex */
public interface ProjectQuizPresenter extends InteractiveBasePresenter<ProjectQuizView, ProjectQuizWireframe> {
    void achievementContinueButtonClicked();

    void homeButtonPressed();

    void loadProjectQuiz(String str);

    void projectCompleted(String str);

    void quizFailure(String str);
}
