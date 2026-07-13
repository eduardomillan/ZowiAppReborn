package com.bq.zowi.usecases

import io.reactivex.Single

interface CheckInstalledZowiAppInteractor {
    companion object {
        const val MAIN_ZOWI_APP_ID_PREFFIX = "ZOWI_BASE_v"
    }

    enum class InstalledZowiAppStatus {
        UNKNOWN, OK, UPDATE_NEEDED, CUSTOM_FIRMWARE
    }

    fun checkAndManageInstalledZowiApp(): Single<InstalledZowiAppStatus>
    fun manageInstalledZowiApp(installedZowiAppId: String?): InstalledZowiAppStatus
}
