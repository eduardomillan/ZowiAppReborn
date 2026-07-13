package com.bq.zowi.adapters

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.SharedPreferences
import android.content.res.AssetManager
import com.bq.zowi.api.*
import com.google.gson.Gson

class CoreAdapterProvider(
    private val context: Context,
    private val prefs: SharedPreferences,
    private val assets: AssetManager,
    private val btAdapter: BluetoothAdapter,
    private val defaultZowiName: String
) {
    private val gson = Gson()

    val keyValueStore: KeyValueStore by lazy { AndroidKeyValueStore(prefs) }
    val assetProvider: AssetProvider by lazy { AndroidAssetProvider(assets) }
    val btAdapterController: BTAdapterController by lazy { AndroidBtAdapterController(context, btAdapter) }
    val btConnectionController: BTConnectionController by lazy { AndroidBtConnectionController(context, btAdapter) }
    val sessionController: SessionController by lazy { AndroidCoreSessionController(keyValueStore, defaultZowiName) }
    val appController: AppController by lazy { AndroidCoreAppController(keyValueStore) }
    val gameController: GameController by lazy { AndroidCoreGameController(keyValueStore, gson) }
    val rankingController: RankingController by lazy { AndroidCoreRankingController(keyValueStore, gson) }
    val achievementsController: AchievementsController by lazy { AndroidCoreAchievementsController(keyValueStore, assetProvider, gson) }
    val projectController: ProjectController by lazy { AndroidCoreProjectController(keyValueStore, assetProvider, gson) }
    val assetController: AssetController by lazy { AndroidCoreAssetController(assetProvider) }
    val zowiDataController: ZowiDataController by lazy { AndroidCoreZowiDataController(btConnectionController) }
    val kitonNetworkController: KitonNetworkController by lazy { AndroidCoreKitonNetworkController(gson) }
}
