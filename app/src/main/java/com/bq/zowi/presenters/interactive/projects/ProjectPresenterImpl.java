package com.bq.zowi.presenters.interactive.projects;

import com.bq.zowi.controllers.BTConnectionController;
import com.bq.zowi.controllers.ProjectController;
import com.bq.zowi.controllers.SessionController;
import com.bq.zowi.interactors.CheckInstalledZowiAppInteractor;
import com.bq.zowi.interactors.ConnectToZowiInteractor;
import com.bq.zowi.interactors.MeasureZowiBatteryLevelInteractor;
import com.bq.zowi.interactors.SendAppToZowiInteractor;
import com.bq.zowi.models.Project;
import com.bq.zowi.models.viewmodels.ProjectViewModel;
import com.bq.zowi.presenters.interactive.InteractiveBasePresenterImpl;
import com.bq.zowi.utils.Grove;
import com.bq.zowi.views.interactive.projects.ProjectView;
import com.bq.zowi.wireframes.projects.ProjectWireframe;
import java.util.concurrent.TimeUnit;
import rx.Scheduler;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/* JADX INFO: loaded from: classes.dex */
public class ProjectPresenterImpl extends InteractiveBasePresenterImpl<ProjectView, ProjectWireframe> implements ProjectPresenter {
    private MeasureZowiBatteryLevelInteractor measureZowiBatteryLevelInteractor;
    private final ProjectController projectController;
    private final SendAppToZowiInteractor sendAppToZowiInteractor;
    private final SessionController sessionController;
    private final Scheduler uiScheduler;

    public ProjectPresenterImpl(SessionController sessionController, BTConnectionController connectionController, ConnectToZowiInteractor connectToZowiInteractor, ProjectController projectController, MeasureZowiBatteryLevelInteractor measureZowiBatteryLevelInteractor, CheckInstalledZowiAppInteractor checkInstalledZowiAppInteractor, SendAppToZowiInteractor sendAppToZowiInteractor, String factoryFirmwarePath, Scheduler uiScheduler) {
        super(sessionController, connectionController, connectToZowiInteractor, measureZowiBatteryLevelInteractor, checkInstalledZowiAppInteractor, sendAppToZowiInteractor, factoryFirmwarePath, uiScheduler);
        this.projectController = projectController;
        this.sessionController = sessionController;
        this.uiScheduler = uiScheduler;
        this.sendAppToZowiInteractor = sendAppToZowiInteractor;
        this.measureZowiBatteryLevelInteractor = measureZowiBatteryLevelInteractor;
    }

    @Override // com.bq.zowi.presenters.interactive.projects.ProjectPresenter
    public void loadProject(String projectId) {
        this.subscriptions.add(this.projectController.getProject(projectId).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<Project>() { // from class: com.bq.zowi.presenters.interactive.projects.ProjectPresenterImpl.1
            @Override // rx.SingleSubscriber
            public void onSuccess(Project project) {
                ((ProjectView) ProjectPresenterImpl.this.getView()).paintProject(ProjectViewModel.projectViewModelFromProject(project), ProjectPresenterImpl.this.sessionController.loadActiveZowiName());
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
                ((ProjectView) ProjectPresenterImpl.this.getView()).showProjectLoadingError();
            }
        }));
    }

    @Override // com.bq.zowi.presenters.interactive.projects.ProjectPresenter
    public void checkProjectCompleteness(String projectId) {
        this.subscriptions.add(this.projectController.getProjectIsCompleted(projectId).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<Boolean>() { // from class: com.bq.zowi.presenters.interactive.projects.ProjectPresenterImpl.2
            @Override // rx.SingleSubscriber
            public void onSuccess(Boolean isComplete) {
                ((ProjectView) ProjectPresenterImpl.this.getView()).paintProjectCompleteness(isComplete.booleanValue());
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
            }
        }));
    }

    @Override // com.bq.zowi.presenters.interactive.projects.ProjectPresenter
    public void checkProjectQuizBlocked(String projectId) {
        this.subscriptions.add(this.projectController.isProjectQuizBlocked(projectId).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<Long>() { // from class: com.bq.zowi.presenters.interactive.projects.ProjectPresenterImpl.3
            @Override // rx.SingleSubscriber
            public void onSuccess(Long blockadePendingMillis) {
                ((ProjectView) ProjectPresenterImpl.this.getView()).paintProjectQuizBlocked(blockadePendingMillis.longValue() != -1, blockadePendingMillis.longValue());
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
            }
        }));
    }

    @Override // com.bq.zowi.presenters.interactive.projects.ProjectPresenter
    public void loadProjectQuiz(String projectId) {
        ((ProjectWireframe) getWireframe()).presentQuiz(projectId);
    }

    @Override // com.bq.zowi.presenters.interactive.projects.ProjectPresenter
    public void homeButtonPressed() {
        ((ProjectWireframe) getWireframe()).presentHome();
    }

    @Override // com.bq.zowi.presenters.interactive.projects.ProjectPresenter
    public void manageLowBatteryForInstallingProjectFirmware(final String projectHex) {
        this.measureZowiBatteryLevelInteractor.measureAndManageZowiBatteryLevel().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(new SingleSubscriber<Boolean>() { // from class: com.bq.zowi.presenters.interactive.projects.ProjectPresenterImpl.4
            @Override // rx.SingleSubscriber
            public void onSuccess(Boolean isBatteryLevelOverThreshold) {
                if (isBatteryLevelOverThreshold.booleanValue()) {
                    Grove.d("Battery level is OK", new Object[0]);
                    ProjectPresenterImpl.this.loadProjectHexToZowi(projectHex);
                } else {
                    Grove.d("Battery level is too low!", new Object[0]);
                    ((ProjectView) ProjectPresenterImpl.this.getView()).showLowBatteryForInstallingProjectFirmwareDialog(ProjectPresenterImpl.this.sessionController.loadActiveZowiName(), projectHex);
                }
            }

            @Override // rx.SingleSubscriber
            public void onError(Throwable error) {
                Grove.d("BATTERY_LEVEL RETRIEVAL ERROR!!! " + error.getMessage(), new Object[0]);
            }
        });
    }

    @Override // com.bq.zowi.presenters.interactive.projects.ProjectPresenter
    public void loadProjectHexToZowi(final String projectHex) {
        ((ProjectView) getView()).showIsInstallingFw();
        ((ProjectView) getView()).showHexLoadingProgress();
        this.subscriptions.add(this.sendAppToZowiInteractor.sendAppToZowi(projectHex).sample(100L, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe((Subscriber<? super Integer>) new Subscriber<Integer>() { // from class: com.bq.zowi.presenters.interactive.projects.ProjectPresenterImpl.5
            @Override // rx.Observer
            public void onCompleted() {
                ((ProjectView) ProjectPresenterImpl.this.getView()).updateNotificationOnProjectHextInstallationSuccess();
                ((ProjectView) ProjectPresenterImpl.this.getView()).showHexLoadingSuccess(ProjectPresenterImpl.this.sessionController.loadActiveZowiName());
                ((ProjectView) ProjectPresenterImpl.this.getView()).hideHexLoadingProgress();
            }

            @Override // rx.Observer
            public void onError(Throwable e) {
                ((ProjectView) ProjectPresenterImpl.this.getView()).updateNotificationOnFwInstallationError();
                ((ProjectView) ProjectPresenterImpl.this.getView()).showHexLoadingError(ProjectPresenterImpl.this.sessionController.loadActiveZowiName(), ProjectPresenterImpl.this.connectionController.isConnected(), projectHex);
                ((ProjectView) ProjectPresenterImpl.this.getView()).hideHexLoadingProgress();
            }

            @Override // rx.Observer
            public void onNext(Integer progress) {
                ((ProjectView) ProjectPresenterImpl.this.getView()).showIsInstallingFw();
                if (progress != null) {
                    ((ProjectView) ProjectPresenterImpl.this.getView()).setHexLoadingProgressValue(progress.intValue());
                } else {
                    ((ProjectView) ProjectPresenterImpl.this.getView()).updateNotificationOnFwInstallationError();
                    ((ProjectView) ProjectPresenterImpl.this.getView()).showHexLoadingError(ProjectPresenterImpl.this.sessionController.loadActiveZowiName(), ProjectPresenterImpl.this.connectionController.isConnected(), projectHex);
                }
            }
        }));
    }
}
