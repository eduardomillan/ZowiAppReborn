package com.bq.zowi.presenters.interactive.projects;

import com.bq.zowi.api.BTConnectionController;
import com.bq.zowi.api.ProjectController;
import com.bq.zowi.api.SessionController;
import com.bq.zowi.models.Achievement;
import com.bq.zowi.models.Project;
import com.bq.zowi.models.viewmodels.AchievementViewModel;
import com.bq.zowi.models.viewmodels.ProjectViewModel;
import com.bq.zowi.presenters.interactive.InteractiveBasePresenterImpl;
import com.bq.zowi.usecases.CheckAchievementAndUnlockItInteractor;
import com.bq.zowi.usecases.CheckInstalledZowiAppInteractor;
import com.bq.zowi.usecases.ConnectToZowiInteractor;
import com.bq.zowi.usecases.MeasureZowiBatteryLevelInteractor;
import com.bq.zowi.usecases.SendAppToZowiInteractor;
import com.bq.zowi.utils.Grove;
import com.bq.zowi.views.interactive.projects.ProjectQuizView;
import com.bq.zowi.wireframes.projects.ProjectQuizWireframe;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ProjectQuizPresenterImpl extends InteractiveBasePresenterImpl<ProjectQuizView, ProjectQuizWireframe> implements ProjectQuizPresenter {
    private final CheckAchievementAndUnlockItInteractor achievementsInteractor;
    private final ProjectController projectController;
    private final Scheduler uiScheduler;

    public ProjectQuizPresenterImpl(SessionController sessionController, BTConnectionController connectionController, ConnectToZowiInteractor connectToZowiInteractor, ProjectController projectController, MeasureZowiBatteryLevelInteractor measureZowiBatteryLevelInteractor, CheckInstalledZowiAppInteractor checkInstalledZowiAppInteractor, SendAppToZowiInteractor sendAppToZowiInteractor, String factoryFirmwarePath, CheckAchievementAndUnlockItInteractor achievementsInteractor, Scheduler uiScheduler) {
        super(sessionController, connectionController, connectToZowiInteractor, measureZowiBatteryLevelInteractor, checkInstalledZowiAppInteractor, sendAppToZowiInteractor, factoryFirmwarePath, uiScheduler);
        this.projectController = projectController;
        this.achievementsInteractor = achievementsInteractor;
        this.uiScheduler = uiScheduler;
    }

    @Override
    public void loadProjectQuiz(String projectId) {
        this.disposables.add(this.projectController.getProject(projectId).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            project -> ((ProjectQuizView) ProjectQuizPresenterImpl.this.getView()).paintProjectQuiz(ProjectViewModel.projectViewModelFromProject(project)),
            error -> ((ProjectQuizView) ProjectQuizPresenterImpl.this.getView()).showProjectQuizLoadingError()
        ));
    }

    @Override
    public void homeButtonPressed() {
        ((ProjectQuizWireframe) getWireframe()).presentHome();
    }

    @Override
    public void projectCompleted(final String projectId) {
        this.disposables.add(this.projectController.setProjectCompleted(projectId).flatMap(new Function<Void, Single<Project>>() {
            @Override
            public Single<Project> apply(Void aVoid) {
                return ProjectQuizPresenterImpl.this.projectController.getProject(projectId);
            }
        }).flatMap(new Function<Project, Single<Achievement>>() {
            @Override
            public Single<Achievement> apply(Project project) {
                return ProjectQuizPresenterImpl.this.achievementsInteractor.checkAchievementAndUnlockIt(Achievement.Id.valueOf(project.achievementId));
            }
        }).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            achievement -> {
                if (achievement == null) {
                    ((ProjectQuizView) ProjectQuizPresenterImpl.this.getView()).showQuizCompleteWithoutAchievement();
                } else {
                    ((ProjectQuizView) ProjectQuizPresenterImpl.this.getView()).showAchievementUnlock(new AchievementViewModel(achievement.id, achievement.unlocked));
                }
            },
            error -> Grove.e(error, "PROJECT COMPLETED ERROR", new Object[0])
        ));
    }

    @Override
    public void quizFailure(String projectId) {
        this.disposables.add(this.projectController.blockProjectQuiz(projectId).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            v -> ((ProjectQuizView) ProjectQuizPresenterImpl.this.getView()).showFailureFeedback(),
            error -> ((ProjectQuizView) ProjectQuizPresenterImpl.this.getView()).showFailureFeedback()
        ));
    }

    @Override
    public void achievementContinueButtonClicked() {
        ((ProjectQuizWireframe) getWireframe()).presentHome();
    }
}
