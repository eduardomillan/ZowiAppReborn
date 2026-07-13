package com.bq.zowi.models

class ConnectionSuccessData(
    zowiName: String,
    val zowiAppId: String,
    val batteryLevel: Float
) {
    private val zowiName: ZowiName = ZowiName(zowiName)

    fun getZowiName(): String = zowiName.toString()
}
