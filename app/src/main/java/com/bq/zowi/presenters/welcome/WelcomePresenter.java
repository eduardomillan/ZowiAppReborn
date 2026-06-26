package com.bq.zowi.presenters.welcome;

import com.bq.zowi.presenters.BasePresenter;
import com.bq.zowi.views.welcome.WelcomeView;
import com.bq.zowi.wireframes.welcome.WelcomeWireframe;

/* JADX INFO: loaded from: classes.dex */
public interface WelcomePresenter extends BasePresenter<WelcomeView, WelcomeWireframe> {
    void startWizard();
}
