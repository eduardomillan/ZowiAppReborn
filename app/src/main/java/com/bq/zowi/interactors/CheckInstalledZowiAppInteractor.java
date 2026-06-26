package com.bq.zowi.interactors;

import android.support.annotation.Nullable;
import rx.Single;

/* JADX INFO: loaded from: classes.dex */
public interface CheckInstalledZowiAppInteractor {
    public static final String MAIN_ZOWI_APP_ID_PREFFIX = "ZOWI_BASE_v";

    public enum InstalledZowiAppStatus {
        UNKNOWN,
        OK,
        UPDATE_NEEDED,
        CUSTOM_FIRMWARE
    }

    Single<InstalledZowiAppStatus> checkAndManageInstalledZowiApp();

    InstalledZowiAppStatus manageInstalledZowiApp(@Nullable String str);
}
