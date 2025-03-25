package com.adewan.scout.core.network

import android.util.Log
import com.adewan.scout.BuildConfig
import com.adewan.scout.core.network.models.IgdbAuthentication
import com.adewan.scout.core.network.models.IgdbGame
import com.adewan.scout.core.network.models.IgdbGenre
import com.adewan.scout.core.network.models.IgdbPlatform
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.Url
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

const val igdbAuthenticationEndpoint = "https://id.twitch.tv/oauth2/token"
const val gameEndpoint = "https://api.igdb.com/v4/games"
const val popularityEndpoint = "https://api.igdb.com/v4/popularity_primitives"
const val genreEndpoint = "https://api.igdb.com/v4/genres"
const val platformEndpoint = "https://api.igdb.com/v4/platforms"


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
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("KtorClient", message)
                    }
                }
                level = LogLevel.ALL
                sanitizeHeader { header -> header == HttpHeaders.Authorization }
            }
        }

    suspend fun getAuthentication(): IgdbAuthentication {
        val qualifiedUrl =
            "$igdbAuthenticationEndpoint?client_id=${BuildConfig.clientId}&client_secret=${BuildConfig.clientSecret}&grant_type=client_credentials"
        return client.post(url = Url(qualifiedUrl)).body()
    }

    suspend fun getGameInfoRowsForQuery(
        accessToken: String,
        query: String
    ): List<IgdbGame> {
        return client
            .post(url = Url(gameEndpoint)) {
                header("Client-ID", BuildConfig.clientId)
                header("Authorization", "Bearer $accessToken")
                header("Content-Type", "application/json")
                setBody(query)
            }
            .body()
    }

    suspend fun getGenres(accessToken: String): List<IgdbGenre> {
        return client.post(url = Url(genreEndpoint)) {
            header("Client-ID", BuildConfig.clientId)
            header("Authorization", "Bearer $accessToken")
            header("Content-Type", "application/json")
            setBody("f name, slug; l 200;")
        }.body()
    }

    suspend fun getPlatforms(accessToken: String): List<IgdbPlatform> {
        return client.post(url = Url(platformEndpoint)) {
            header("Client-ID", BuildConfig.clientId)
            header("Authorization", "Bearer $accessToken")
            header("Content-Type", "application/json")
            setBody("f name, slug, generation; l 200;")
        }.body()
    }

}