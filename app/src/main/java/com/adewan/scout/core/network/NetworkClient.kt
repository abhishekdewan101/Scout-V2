package com.adewan.scout.core.network

import com.adewan.scout.BuildConfig
import com.adewan.scout.core.network.models.IgdbAuthentication
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.http.Url
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import logcat.LogPriority
import logcat.logcat

const val igdbAuthenticationEndpoint = "https://id.twitch.tv/oauth2/token"
const val gameEndpoint = "https://api.igdb.com/v4/games"
const val popularityEndpoint = "https://api.igdb.com/v4/popularity_primitives"


class NetworkClient {
    private val client =
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    json =
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    })
            }
            install(Logging) {
                if (BuildConfig.DEBUG) {
                    level = LogLevel.ALL
                    logger =
                        object : Logger {
                            override fun log(message: String) {
                                logcat(LogPriority.INFO) { message }
                            }
                        }
                }
            }
        }

    suspend fun getAuthentication(): IgdbAuthentication {
        val qualifiedUrl =
            "$igdbAuthenticationEndpoint?client_id=${BuildConfig.clientId}&client_secret=${BuildConfig.clientSecret}&grant_type=client_credentials"
        return client.post(url = Url(qualifiedUrl)).body()
    }

}