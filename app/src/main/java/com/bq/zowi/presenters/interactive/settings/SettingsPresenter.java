package com.bq.zowi.presenters.interactive.settings;

import com.bq.zowi.presenters.interactive.InteractiveBasePresenter;
import com.bq.zowi.views.interactive.settings.SettingsView;
import com.bq.zowi.wireframes.settings.SettingsWireframe;

/* JADX INFO: loaded from: classes.dex */
public interface SettingsPresenter extends InteractiveBasePresenter<SettingsView, SettingsWireframe> {
    void calibrateZowi();

    void changeZowiName(String str);

    void forgetPlayingHistory();

    void forgetZowi();

    void homeButtonPressed();

    void lookForAppUpdates();

    void manageLowBatteryWhenCalibratingForInstallingFirmware();

    void visitHospital();
}
