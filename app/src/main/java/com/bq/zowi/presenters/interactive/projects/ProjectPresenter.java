package com.bq.zowi.presenters.interactive.projects;

import com.bq.zowi.presenters.interactive.InteractiveBasePresenter;
import com.bq.zowi.views.interactive.projects.ProjectView;
import com.bq.zowi.wireframes.projects.ProjectWireframe;

/* JADX INFO: loaded from: classes.dex */
public interface ProjectPresenter extends InteractiveBasePresenter<ProjectView, ProjectWireframe> {
    void checkProjectCompleteness(String str);

    void checkProjectQuizBlocked(String str);

    void homeButtonPressed();

    void loadProject(String str);

    void loadProjectHexToZowi(String str);

    void loadProjectQuiz(String str);

    void manageLowBatteryForInstallingProjectFirmware(String str);
}
