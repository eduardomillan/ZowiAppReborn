package com.bq.zowi.presenters.wizard;

import com.bq.zowi.presenters.BasePresenter;
import com.bq.zowi.views.wizard.WizardView;
import com.bq.zowi.wireframes.wizard.WizardWireframe;

/* JADX INFO: loaded from: classes.dex */
public interface WizardPresenter extends BasePresenter<WizardView, WizardWireframe> {
    void changeZowiName(String str, String str2);

    void connectToZowi(String str);

    void dismissWizard();

    void findZowis();

    void wizardComplete(String str);
}
