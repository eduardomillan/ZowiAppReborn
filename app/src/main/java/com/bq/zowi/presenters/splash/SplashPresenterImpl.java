package com.bq.zowi.presenters.splash;

import com.bq.zowi.api.SessionController;
import com.bq.zowi.presenters.BasePresenterImpl;
import com.bq.zowi.views.splash.SplashView;
import com.bq.zowi.wireframes.splash.SplashWireframe;

public class SplashPresenterImpl extends BasePresenterImpl<SplashView, SplashWireframe> implements SplashPresenter {
    private SessionController sessionController;

    public SplashPresenterImpl(SessionController sessionController) {
        this.sessionController = sessionController;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void onContinueClicked() {
        boolean isActiveSession = this.sessionController.loadActiveZowiDeviceAddress() != null || this.sessionController.hasDismissedWizard();
        getWireframe().dismissSplash(isActiveSession);
    }
}
