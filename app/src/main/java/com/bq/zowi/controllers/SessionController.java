package com.bq.zowi.controllers;

import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.dex */
public interface SessionController {
    @Nullable
    String loadActiveZowiDeviceAddress();

    String loadActiveZowiName();

    String loadDefaultZowiName();

    boolean hasDismissedWizard();

    void resetActiveZowi();

    void saveActiveZowiDeviceAddress(String str);

    void saveActiveZowiName(String str);

    void saveWizardDismissed(boolean dismissed);
}
