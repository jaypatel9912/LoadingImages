package com.example.loadingimages.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

object NetworkModule {

    fun provideHttpClient(): HttpClient = HttpClient(Android) {
        val timeout = 30_000L
        install(Logging)
        install(DefaultRequest) {
            url("https://api.unsplash.com")
            contentType(ContentType.Application.Json)
        }

        install(HttpTimeout) {
            requestTimeoutMillis = timeout
            connectTimeoutMillis = timeout
        }
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                    encodeDefaults = true
                },
            )
        }
    }

}

val networkModule = module {
    factory { NetworkModule.provideHttpClient() }
}