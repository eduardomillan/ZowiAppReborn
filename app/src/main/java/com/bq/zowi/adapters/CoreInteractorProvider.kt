package com.bq.zowi.adapters

import com.bq.zowi.usecases.*

class CoreInteractorProvider(
    private val coreProvider: CoreAdapterProvider,
    private val factoryFirmwareVersion: Int
) {
    val sendCommandToZowiInteractor: SendCommandToZowiInteractor by lazy {
        SendCommandToZowiInteractorImpl(coreProvider.btConnectionController)
    }

    val changeZowiNameInteractor: ChangeZowiNameInteractor by lazy {
        ChangeZowiNameInteractorImpl(sendCommandToZowiInteractor)
    }

    val findZowisInteractor: FindZowisInteractor by lazy {
        FindZowisInteractorImpl(coreProvider.btAdapterController)
    }

    val connectToZowiInteractor: ConnectToZowiInteractor by lazy {
        ConnectToZowiInteractorImpl(
            coreProvider.btConnectionController,
            coreProvider.btAdapterController,
            coreProvider.zowiDataController,
            coreProvider.kitonNetworkController
        )
    }

    val sendAppToZowiInteractor: SendAppToZowiInteractor by lazy {
        SendAppToZowiInteractorImpl(
            connectToZowiInteractor,
            coreProvider.btConnectionController,
            coreProvider.assetController,
            coreProvider.sessionController,
            coreProvider.zowiDataController,
            coreProvider.kitonNetworkController
        )
    }

    val checkAchievementAndUnlockItInteractor: CheckAchievementAndUnlockItInteractor by lazy {
        CheckAchievementAndUnlockItInteractorImpl(
            coreProvider.achievementsController,
            sendCommandToZowiInteractor
        )
    }

    val checkInstalledZowiAppInteractor: CheckInstalledZowiAppInteractor by lazy {
        CheckInstalledZowiAppInteractorImpl(
            factoryFirmwareVersion,
            coreProvider.zowiDataController
        )
    }

    val forgetZowiInteractor: ForgetZowiInteractor by lazy {
        ForgetZowiInteractorImpl(
            changeZowiNameInteractor,
            coreProvider.sessionController,
            coreProvider.btConnectionController
        )
    }

    val forgetPlayingHistoryInteractor: ForgetPlayingHistoryInteractor by lazy {
        ForgetPlayingHistoryInteractorImpl(
            coreProvider.projectController,
            coreProvider.appController,
            coreProvider.gameController,
            coreProvider.rankingController,
            coreProvider.achievementsController
        )
    }

    val measureZowiBatteryLevelInteractor: MeasureZowiBatteryLevelInteractor by lazy {
        MeasureZowiBatteryLevelInteractorImpl(coreProvider.zowiDataController)
    }
}
