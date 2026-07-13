package com.bq.zowi.adapters

import com.bq.zowi.api.KitonNetworkController
import com.bq.zowi.models.networkModels.KitonIsAliveResponseNetworkModel
import com.bq.zowi.utils.Grove
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.reactivex.Single
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class AndroidCoreKitonNetworkController(
    private val gson: Gson = Gson(),
    private val baseUrl: String = "https://kiton.bq.com/api"
) : KitonNetworkController {

    override fun sendIsAliveToKiton(
        macAddress: String,
        zowiAppId: String?
    ): Single<KitonIsAliveResponseNetworkModel> {
        return Single.fromCallable {
            var conn: HttpURLConnection? = null
            try {
                val jsonBody = gson.toJson(mapOf(
                    "mac_address" to macAddress,
                    "zowi_app_id" to (zowiAppId ?: "")
                ))

                val url = URL("$baseUrl/isalive")
                conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json")
                conn.doOutput = true
                conn.connectTimeout = 5000
                conn.readTimeout = 5000

                OutputStreamWriter(conn.outputStream).use { it.write(jsonBody) }

                val responseCode = conn.responseCode
                val body = if (responseCode in 200..299) {
                    BufferedReader(InputStreamReader(conn.inputStream)).use { it.readText() }
                } else {
                    null
                }

                if (body != null) {
                    gson.fromJson(body, KitonIsAliveResponseNetworkModel::class.java)
                } else {
                    val obj = JsonObject()
                    obj.addProperty("code", responseCode)
                    obj.addProperty("message", "HTTP $responseCode")
                    gson.fromJson(obj, KitonIsAliveResponseNetworkModel::class.java)
                }
            } catch (e: Exception) {
                Grove.d(e, "Kiton request failed")
                val obj = JsonObject()
                obj.addProperty("code", -1)
                obj.addProperty("message", e.message)
                gson.fromJson(obj, KitonIsAliveResponseNetworkModel::class.java)
            } finally {
                conn?.disconnect()
            }
        }
    }
}
