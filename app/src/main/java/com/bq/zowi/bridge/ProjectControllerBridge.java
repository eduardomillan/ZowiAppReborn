package com.bq.zowi.bridge;

import com.bq.zowi.controllers.ProjectController;
import com.bq.zowi.models.Project;
import rx.Single;

public class ProjectControllerBridge implements ProjectController {
    private final com.bq.zowi.api.ProjectController core;

    public ProjectControllerBridge(com.bq.zowi.api.ProjectController core) {
        this.core = core;
    }

    @Override
    public Single<Void> blockProjectQuiz(String projectId) {
        return RxBridge.toRxSingle(core.blockProjectQuiz(projectId));
    }

    @Override
    public Single<Project> getProject(String projectId) {
        return RxBridge.toRxSingle(core.getProject(projectId));
    }

    @Override
    public Single<Boolean> getProjectIsCompleted(String projectId) {
        return RxBridge.toRxSingle(core.getProjectIsCompleted(projectId));
    }

    @Override
    public Single<Long> isProjectQuizBlocked(String projectId) {
        return RxBridge.toRxSingle(core.isProjectQuizBlocked(projectId));
    }

    @Override
    public Single<Void> resetAllProjects() {
        return RxBridge.toRxSingle(core.resetAllProjects());
    }

    @Override
    public Single<Void> setProjectCompleted(String projectId) {
        return RxBridge.toRxSingle(core.setProjectCompleted(projectId));
    }
}
