package com.bq.zowi.views.interactive.projects;

import com.bq.zowi.models.viewmodels.ProjectViewModel;
import com.bq.zowi.views.interactive.InteractiveBaseView;

/* JADX INFO: loaded from: classes.dex */
public interface ProjectView extends InteractiveBaseView {
    void hideHexLoadingProgress();

    void paintProject(ProjectViewModel projectViewModel, String str);

    void paintProjectCompleteness(boolean z);

    void paintProjectQuizBlocked(boolean z, long j);

    void setHexLoadingProgressValue(int i);

    void showHexLoadingError(String str, boolean z, String str2);

    void showHexLoadingProgress();

    void showHexLoadingSuccess(String str);

    void showLowBatteryForInstallingProjectFirmwareDialog(String str, String str2);

    void showProjectLoadingError();

    void updateNotificationOnProjectHextInstallationSuccess();
}
