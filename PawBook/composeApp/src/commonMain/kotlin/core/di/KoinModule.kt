package core.di

import data.remote.LoginApi
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}): KoinApplication {
    return startKoin {
        appDeclaration()
        modules(commonModule())
    }
}

fun provideKtorfit(): Ktorfit {

    return Ktorfit.Builder()
        .baseUrl("http://10.0.2.2:3000/v1/")
        .httpClient(HttpClient {
            // install(HttpCache)
            install(ContentNegotiation)
            {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
        }).build()
}

fun provideLoginApi(ktorfit: Ktorfit): LoginApi = ktorfit.create()

// called by iOS
fun initKoin() = initKoin{}

fun commonModule() = module {
    //factory { Greeting() }
}