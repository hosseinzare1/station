package ir.romina.hossein.core.di

import com.google.maps.android.ktx.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single<HttpClient> {
        HttpClient(OkHttp) {
            if (BuildConfig.DEBUG) {
                install(Logging) {
                    level = LogLevel.BODY
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            defaultRequest {
                url {
                    host = "mocki.io/v1"
                    protocol = URLProtocol.HTTPS
                }
            }
        }
    }
}