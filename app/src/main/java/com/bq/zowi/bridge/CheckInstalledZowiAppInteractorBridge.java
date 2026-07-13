package com.bq.zowi.bridge;

import androidx.annotation.Nullable;
import com.bq.zowi.interactors.CheckInstalledZowiAppInteractor;
import rx.Single;

/**
 * Bridge from core (RxJava2) CheckInstalledZowiAppInteractor to old (RxJava1) interface.
 * Maps the core {@code InstalledZowiAppStatus} enum to the old interface's enum.
 */
public class CheckInstalledZowiAppInteractorBridge implements com.bq.zowi.interactors.CheckInstalledZowiAppInteractor {
    private final com.bq.zowi.usecases.CheckInstalledZowiAppInteractor core;

    public CheckInstalledZowiAppInteractorBridge(com.bq.zowi.usecases.CheckInstalledZowiAppInteractor core) {
        this.core = core;
    }

    @Override
    public Single<CheckInstalledZowiAppInteractor.InstalledZowiAppStatus> checkAndManageInstalledZowiApp() {
        return RxBridge.toRxSingle(
            core.checkAndManageInstalledZowiApp().map(this::mapStatus)
        );
    }

    @Override
    public CheckInstalledZowiAppInteractor.InstalledZowiAppStatus manageInstalledZowiApp(@Nullable String installedZowiAppId) {
        return mapStatus(core.manageInstalledZowiApp(installedZowiAppId));
    }

    private CheckInstalledZowiAppInteractor.InstalledZowiAppStatus mapStatus(
            com.bq.zowi.usecases.CheckInstalledZowiAppInteractor.InstalledZowiAppStatus coreStatus) {
        return CheckInstalledZowiAppInteractor.InstalledZowiAppStatus.valueOf(coreStatus.name());
    }
}
