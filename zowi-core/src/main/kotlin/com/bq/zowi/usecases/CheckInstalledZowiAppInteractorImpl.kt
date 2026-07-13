package com.bq.zowi.usecases

import com.bq.zowi.api.ZowiDataController
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class CheckInstalledZowiAppInteractorImpl(
    private val factoryFirmwareVersion: Int,
    private val zowiDataController: ZowiDataController
) : CheckInstalledZowiAppInteractor {

    override fun checkAndManageInstalledZowiApp(): Single<CheckInstalledZowiAppInteractor.InstalledZowiAppStatus> {
        return zowiDataController.getZowiAppId()
            .timeout(3000L, TimeUnit.MILLISECONDS, Single.just(null))
            .map { manageInstalledZowiApp(it) }
    }

    override fun manageInstalledZowiApp(installedZowiAppId: String?): CheckInstalledZowiAppInteractor.InstalledZowiAppStatus {
        if (installedZowiAppId == null) {
            return CheckInstalledZowiAppInteractor.InstalledZowiAppStatus.UNKNOWN
        }
        if (!installedZowiAppId.startsWith(CheckInstalledZowiAppInteractor.MAIN_ZOWI_APP_ID_PREFFIX)) {
            return CheckInstalledZowiAppInteractor.InstalledZowiAppStatus.CUSTOM_FIRMWARE
        }
        val mainZowiAppInstalledVersion = installedZowiAppId
            .replace(CheckInstalledZowiAppInteractor.MAIN_ZOWI_APP_ID_PREFFIX, "")
            .toInt()
        return if (mainZowiAppInstalledVersion >= factoryFirmwareVersion) {
            CheckInstalledZowiAppInteractor.InstalledZowiAppStatus.OK
        } else {
            CheckInstalledZowiAppInteractor.InstalledZowiAppStatus.UPDATE_NEEDED
        }
    }
}
