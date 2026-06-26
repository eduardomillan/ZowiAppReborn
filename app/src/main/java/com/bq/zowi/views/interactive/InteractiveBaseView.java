package com.bq.zowi.views.interactive;

import com.bq.zowi.models.viewmodels.AchievementViewModel;

/* JADX INFO: loaded from: classes.dex */
public interface InteractiveBaseView {
    void hideFirmwareUpdatingDialog();

    void hideRestoreFirmwareDialog();

    boolean isZowiAltered();

    void showAchievementUnlock(AchievementViewModel achievementViewModel);

    void showCorruptedZowiDialog(String str);

    void showDemoMode();

    void showFirmwareUpdatingDialog(String str, boolean z);

    void showGoodBatteryLevel();

    void showInstallingFirmwareInfoDialog();

    void showInstallingFwErrorDialog(String str, boolean z, boolean z2);

    void showInstallingFwSuccessDialog(String str, boolean z);

    void showIsInstallingFw();

    void showLowBatteryForInstallingFirmwareDialog(String str, boolean z);

    void showLowBatteryLevel();

    void showZowiConnected();

    void showZowiConnecting();

    void showZowiDisconnected();

    void showZowiName(String str);

    void updateFirmwareUpdatingProgressBar(int i);

    void updateNotificationOnAlteredFirmwareDetected();

    void updateNotificationOnFwInstallationError();

    void updateNotificationOnFwInstallationSuccess();
}
