package com.bq.zowi.views.wizard;

/* JADX INFO: loaded from: classes.dex */
public interface WizardView {
    void hideSpinner();

    void showConnectionError();

    void showConnectionSuccessWithEditableName(String str);

    void showInvalidNameError();

    void showNoZowisFound();

    void showSearchingForZowis();

    void showSpinner();

    void showZowiFound(String str);
}
