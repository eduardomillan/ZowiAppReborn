package com.bq.zowi.presenters.interactive;

import com.bq.zowi.presenters.BasePresenter;
import com.bq.zowi.views.interactive.InteractiveBaseView;

/* JADX INFO: loaded from: classes.dex */
public interface InteractiveBasePresenter<V extends InteractiveBaseView, W> extends BasePresenter<V, W> {
    void checkAndManageZowiAppInstalled();

    void installFactoryFirmware(boolean z);

    void launchWizard();

    void manageConnection();

    void manageLowBatteryForInstallingFirmware(boolean z);

    void manageLowBatteryForInstallingFirmware(boolean z, boolean z2);

    void manageLowBatteryForInstallingFirmware(boolean z, boolean z2, float f);

    void manageZowiName();

    void measureAndManageBatteryLevel();
}
