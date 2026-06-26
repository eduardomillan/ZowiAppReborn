package com.bq.zowi.interactors;

import androidx.annotation.Nullable;
import com.bq.zowi.controllers.ZowiDataController;
import com.bq.zowi.interactors.CheckInstalledZowiAppInteractor;
import java.util.concurrent.TimeUnit;
import rx.Single;
import rx.functions.Func1;

/* JADX INFO: loaded from: classes.dex */
public class CheckInstalledZowiAppInteractorImpl implements CheckInstalledZowiAppInteractor {
    private static final int APP_ID_RECEPTION_TIMEOUT = 3000;
    private final int factoryFirmwareVersion;
    private ZowiDataController zowiDataController;

    public CheckInstalledZowiAppInteractorImpl(int factoryFirmwareVersion, ZowiDataController zowiDataController) {
        this.zowiDataController = zowiDataController;
        this.factoryFirmwareVersion = factoryFirmwareVersion;
    }

    @Override // com.bq.zowi.interactors.CheckInstalledZowiAppInteractor
    public Single<CheckInstalledZowiAppInteractor.InstalledZowiAppStatus> checkAndManageInstalledZowiApp() {
        return this.zowiDataController.getZowiAppId().timeout(3000L, TimeUnit.MILLISECONDS, Single.just(null)).map(new Func1<String, CheckInstalledZowiAppInteractor.InstalledZowiAppStatus>() { // from class: com.bq.zowi.interactors.CheckInstalledZowiAppInteractorImpl.1
            @Override // rx.functions.Func1
            public CheckInstalledZowiAppInteractor.InstalledZowiAppStatus call(String installedZowiAppId) {
                return CheckInstalledZowiAppInteractorImpl.this.manageInstalledZowiApp(installedZowiAppId);
            }
        });
    }

    @Override // com.bq.zowi.interactors.CheckInstalledZowiAppInteractor
    public CheckInstalledZowiAppInteractor.InstalledZowiAppStatus manageInstalledZowiApp(@Nullable String installedZowiAppId) {
        if (installedZowiAppId == null) {
            return CheckInstalledZowiAppInteractor.InstalledZowiAppStatus.UNKNOWN;
        }
        if (!installedZowiAppId.startsWith(CheckInstalledZowiAppInteractor.MAIN_ZOWI_APP_ID_PREFFIX)) {
            return CheckInstalledZowiAppInteractor.InstalledZowiAppStatus.CUSTOM_FIRMWARE;
        }
        int mainZowiAppInstalledVersion = Integer.valueOf(installedZowiAppId.replace(CheckInstalledZowiAppInteractor.MAIN_ZOWI_APP_ID_PREFFIX, "")).intValue();
        if (mainZowiAppInstalledVersion >= this.factoryFirmwareVersion) {
            return CheckInstalledZowiAppInteractor.InstalledZowiAppStatus.OK;
        }
        return CheckInstalledZowiAppInteractor.InstalledZowiAppStatus.UPDATE_NEEDED;
    }
}
