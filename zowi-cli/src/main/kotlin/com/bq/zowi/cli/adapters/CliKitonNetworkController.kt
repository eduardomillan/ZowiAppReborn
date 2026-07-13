package com.bq.zowi.cli.adapters

import com.bq.zowi.api.KitonNetworkController
import com.bq.zowi.models.networkModels.KitonIsAliveResponseNetworkModel
import com.bq.zowi.utils.Grove
import com.google.gson.Gson
import io.reactivex.Single
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

class CliKitonNetworkController(
    private val gson: Gson = Gson(),
    private val baseUrl: String = "https://kiton.bq.com/api"
) : KitonNetworkController {

    private val httpClient: HttpClient = HttpClient.newHttpClient()

    override fun sendIsAliveToKiton(
        macAddress: String,
        zowiAppId: String?
    ): Single<KitonIsAliveResponseNetworkModel> {
        return Single.fromCallable {
            try {
                val jsonBody = gson.toJson(mapOf(
                    "mac_address" to macAddress,
                    "zowi_app_id" to (zowiAppId ?: "")
                ))

                val request = HttpRequest.newBuilder()
                    .uri(URI.create("$baseUrl/isalive"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build()

                val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
                gson.fromJson(response.body(), KitonIsAliveResponseNetworkModel::class.java)
            } catch (e: Exception) {
                Grove.d(e, "CLI: Kiton request failed")
                KitonIsAliveResponseNetworkModel(code = -1, message = e.message)
            }
        }
    }
}
