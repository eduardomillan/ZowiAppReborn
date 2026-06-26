package com.bq.zowi.presenters.interactive.projects;

import com.bq.zowi.controllers.BTConnectionController;
import com.bq.zowi.controllers.ProjectController;
import com.bq.zowi.controllers.SessionController;
import com.bq.zowi.interactors.CheckAchievementAndUnlockItInteractor;
import com.bq.zowi.interactors.CheckInstalledZowiAppInteractor;
import com.bq.zowi.interactors.ConnectToZowiInteractor;
import com.bq.zowi.interactors.MeasureZowiBatteryLevelInteractor;
import com.bq.zowi.interactors.SendAppToZowiInteractor;
import com.bq.zowi.models.Achievement;
import com.bq.zowi.models.Project;
import com.bq.zowi.models.viewmodels.AchievementViewModel;
import com.bq.zowi.models.viewmodels.ProjectViewModel;
import com.bq.zowi.presenters.interactive.InteractiveBasePresenterImpl;
import com.bq.zowi.utils.Grove;
import com.bq.zowi.views.interactive.projects.ProjectQuizView;
import com.bq.zowi.wireframes.projects.ProjectQuizWireframe;
import rx.Scheduler;
import rx.Single;
import rx.SingleSubscriber;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/* JADX INFO: loaded from: classes.dex */
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

    @Override // com.bq.zowi.presenters.interactive.projects.ProjectQuizPresenter
    public void loadProjectQuiz(String projectId) {
        this.subscriptions.add(this.projectController.getProject(projectId).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<Project>() { // from class: com.bq.zowi.presenters.interactive.projects.ProjectQuizPresenterImpl.1
            @Override // rx.SingleSubscriber
            public void onSuccess(Project project) {
                ((ProjectQuizView) ProjectQuizPresenterImpl.this.getView()).paintProjectQuiz(ProjectViewModel.projectViewModelFromProject(project));
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
                ((ProjectQuizView) ProjectQuizPresenterImpl.this.getView()).showProjectQuizLoadingError();
            }
        }));
    }

    @Override // com.bq.zowi.presenters.interactive.projects.ProjectQuizPresenter
    public void homeButtonPressed() {
        ((ProjectQuizWireframe) getWireframe()).presentHome();
    }

    @Override // com.bq.zowi.presenters.interactive.projects.ProjectQuizPresenter
    public void projectCompleted(final String projectId) {
        this.subscriptions.add(this.projectController.setProjectCompleted(projectId).flatMap(new Func1<Void, Single<Project>>() { // from class: com.bq.zowi.presenters.interactive.projects.ProjectQuizPresenterImpl.4
            @Override // rx.functions.Func1
            public Single<Project> call(Void aVoid) {
                return ProjectQuizPresenterImpl.this.projectController.getProject(projectId);
            }
        }).flatMap(new Func1<Project, Single<Achievement>>() { // from class: com.bq.zowi.presenters.interactive.projects.ProjectQuizPresenterImpl.3
            @Override // rx.functions.Func1
            public Single<Achievement> call(Project project) {
                return ProjectQuizPresenterImpl.this.achievementsInteractor.checkAchievementAndUnlockIt(Achievement.Id.valueOf(project.achievementId));
            }
        }).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<Achievement>() { // from class: com.bq.zowi.presenters.interactive.projects.ProjectQuizPresenterImpl.2
            @Override // rx.SingleSubscriber
            public void onSuccess(Achievement achievement) {
                if (achievement == null) {
                    ((ProjectQuizView) ProjectQuizPresenterImpl.this.getView()).showQuizCompleteWithoutAchievement();
                } else {
                    ((ProjectQuizView) ProjectQuizPresenterImpl.this.getView()).showAchievementUnlock(new AchievementViewModel(achievement.id, achievement.unlocked));
                }
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
                Grove.e(error, "PROJECT COMPLETED ERROR", new Object[0]);
            }
        }));
    }

    @Override // com.bq.zowi.presenters.interactive.projects.ProjectQuizPresenter
    public void quizFailure(String projectId) {
        this.subscriptions.add(this.projectController.blockProjectQuiz(projectId).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<Void>() { // from class: com.bq.zowi.presenters.interactive.projects.ProjectQuizPresenterImpl.5
            @Override // rx.SingleSubscriber
            public void onSuccess(Void value) {
                ((ProjectQuizView) ProjectQuizPresenterImpl.this.getView()).showFailureFeedback();
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
                ((ProjectQuizView) ProjectQuizPresenterImpl.this.getView()).showFailureFeedback();
            }
        }));
    }

    @Override // com.bq.zowi.presenters.interactive.projects.ProjectQuizPresenter
    public void achievementContinueButtonClicked() {
        ((ProjectQuizWireframe) getWireframe()).presentHome();
    }
}
