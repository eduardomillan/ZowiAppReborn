package com.bq.zowi.presenters.welcome;

import com.bq.zowi.presenters.BasePresenterImpl;
import com.bq.zowi.views.welcome.WelcomeView;
import com.bq.zowi.wireframes.welcome.WelcomeWireframe;

/* JADX INFO: loaded from: classes.dex */
public class WelcomePresenterImpl extends BasePresenterImpl<WelcomeView, WelcomeWireframe> implements WelcomePresenter {
    @Override // com.bq.zowi.presenters.welcome.WelcomePresenter
    public void startWizard() {
        getWireframe().showWizard();
    }
}
