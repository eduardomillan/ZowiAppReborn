package com.bq.zowi.controllers;

import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.dex */
public interface SessionController {
    @Nullable
    String loadActiveZowiDeviceAddress();

    String loadActiveZowiName();

    String loadDefaultZowiName();

    void resetActiveZowi();

    void saveActiveZowiDeviceAddress(String str);

    void saveActiveZowiName(String str);
}
