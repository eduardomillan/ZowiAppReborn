package com.bq.zowi.presenters.interactive.projects;

import com.bq.zowi.api.BTConnectionController;
import com.bq.zowi.api.ProjectController;
import com.bq.zowi.api.SessionController;
import com.bq.zowi.models.Project;
import com.bq.zowi.models.viewmodels.ProjectViewModel;
import com.bq.zowi.presenters.interactive.InteractiveBasePresenterImpl;
import com.bq.zowi.usecases.CheckInstalledZowiAppInteractor;
import com.bq.zowi.usecases.ConnectToZowiInteractor;
import com.bq.zowi.usecases.MeasureZowiBatteryLevelInteractor;
import com.bq.zowi.usecases.SendAppToZowiInteractor;
import com.bq.zowi.utils.Grove;
import com.bq.zowi.views.interactive.projects.ProjectView;
import com.bq.zowi.wireframes.projects.ProjectWireframe;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;

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

    @Override
    public void loadProject(String projectId) {
        this.disposables.add(this.projectController.getProject(projectId).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            project -> ((ProjectView) ProjectPresenterImpl.this.getView()).paintProject(ProjectViewModel.projectViewModelFromProject(project), ProjectPresenterImpl.this.sessionController.loadActiveZowiName()),
            error -> ((ProjectView) ProjectPresenterImpl.this.getView()).showProjectLoadingError()
        ));
    }

    @Override
    public void checkProjectCompleteness(String projectId) {
        this.disposables.add(this.projectController.getProjectIsCompleted(projectId).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            isComplete -> ((ProjectView) ProjectPresenterImpl.this.getView()).paintProjectCompleteness(isComplete.booleanValue()),
            error -> { }
        ));
    }

    @Override
    public void checkProjectQuizBlocked(String projectId) {
        this.disposables.add(this.projectController.isProjectQuizBlocked(projectId).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            blockadePendingMillis -> ((ProjectView) ProjectPresenterImpl.this.getView()).paintProjectQuizBlocked(blockadePendingMillis.longValue() != -1, blockadePendingMillis.longValue()),
            error -> { }
        ));
    }

    @Override
    public void loadProjectQuiz(String projectId) {
        ((ProjectWireframe) getWireframe()).presentQuiz(projectId);
    }

    @Override
    public void homeButtonPressed() {
        ((ProjectWireframe) getWireframe()).presentHome();
    }

    @Override
    public void manageLowBatteryForInstallingProjectFirmware(final String projectHex) {
        this.disposables.add(this.measureZowiBatteryLevelInteractor.measureAndManageZowiBatteryLevel().subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            isBatteryLevelOverThreshold -> {
                if (isBatteryLevelOverThreshold.booleanValue()) {
                    Grove.d("Battery level is OK", new Object[0]);
                    ProjectPresenterImpl.this.loadProjectHexToZowi(projectHex);
                } else {
                    Grove.d("Battery level is too low!", new Object[0]);
                    ((ProjectView) ProjectPresenterImpl.this.getView()).showLowBatteryForInstallingProjectFirmwareDialog(ProjectPresenterImpl.this.sessionController.loadActiveZowiName(), projectHex);
                }
            },
            error -> Grove.d("BATTERY_LEVEL RETRIEVAL ERROR!!! " + error.getMessage(), new Object[0])
        ));
    }

    @Override
    public void loadProjectHexToZowi(final String projectHex) {
        ((ProjectView) getView()).showIsInstallingFw();
        ((ProjectView) getView()).showHexLoadingProgress();
        this.disposables.add(this.sendAppToZowiInteractor.sendAppToZowi(projectHex).sample(100L, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(this.uiScheduler).subscribe(
            progress -> {
                ((ProjectView) ProjectPresenterImpl.this.getView()).showIsInstallingFw();
                if (progress != null) {
                    ((ProjectView) ProjectPresenterImpl.this.getView()).setHexLoadingProgressValue(progress.intValue());
                } else {
                    ((ProjectView) ProjectPresenterImpl.this.getView()).updateNotificationOnFwInstallationError();
                    ((ProjectView) ProjectPresenterImpl.this.getView()).showHexLoadingError(ProjectPresenterImpl.this.sessionController.loadActiveZowiName(), ProjectPresenterImpl.this.connectionController.isConnected(), projectHex);
                }
            },
            e -> {
                ((ProjectView) ProjectPresenterImpl.this.getView()).updateNotificationOnFwInstallationError();
                ((ProjectView) ProjectPresenterImpl.this.getView()).showHexLoadingError(ProjectPresenterImpl.this.sessionController.loadActiveZowiName(), ProjectPresenterImpl.this.connectionController.isConnected(), projectHex);
                ((ProjectView) ProjectPresenterImpl.this.getView()).hideHexLoadingProgress();
            },
            () -> {
                ((ProjectView) ProjectPresenterImpl.this.getView()).updateNotificationOnProjectHextInstallationSuccess();
                ((ProjectView) ProjectPresenterImpl.this.getView()).showHexLoadingSuccess(ProjectPresenterImpl.this.sessionController.loadActiveZowiName());
                ((ProjectView) ProjectPresenterImpl.this.getView()).hideHexLoadingProgress();
            }
        ));
    }
}
