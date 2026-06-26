package com.bq.zowi.views.interactive.settings;

import com.bq.zowi.views.interactive.InteractiveBaseView;

/* JADX INFO: loaded from: classes.dex */
public interface SettingsView extends InteractiveBaseView {
    void showForgetPlayingHistoryError();

    void showForgetPlayingHistorySuccess();

    void showForgetZowiError();

    void showForgetZowiSuccess();

    void showInvalidNameError();

    void showNameChangeError();

    void showNameChangeSuccess();

    void showRestoringInfoWhenCalibratingAlteredZowi();
}
