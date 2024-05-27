package core.di

import com.russhwolf.settings.Settings
import core.util.LocalDateTimeSerializer
import data.remote.BreedApi
import data.remote.DoctorApi
import data.remote.LoginApi
import data.remote.PetApi
import data.remote.Preferences
import de.jensklingenberg.ktorfit.Ktorfit
import domain.model.PetItem
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
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
    val prefs = Preferences()
    val token = prefs.getAccessToken()
    return Ktorfit.Builder()
        .baseUrl("http://10.0.2.2:3000/v1/")
        .httpClient(HttpClient {
            // install(HttpCache)
            install(ContentNegotiation)
            {
                json(
                    Json {
                        serializersModule = SerializersModule {
                            contextual(LocalDateTime::class, LocalDateTimeSerializer)
                        }
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(DefaultRequest) {
                header("Authorization", "Bearer $token")
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }).build()
}

fun provideLoginApi(ktorfit: Ktorfit): LoginApi = ktorfit.create()
fun providePetsApi(ktorfit: Ktorfit): PetApi = ktorfit.create()
fun provideBreedsApi(ktorfit: Ktorfit): BreedApi = ktorfit.create()
fun provideDoctorsApi(ktorfit: Ktorfit): DoctorApi = ktorfit.create()

fun provideSettings(): Settings = Settings()

// called by iOS
fun initKoin() = initKoin{}

fun commonModule() = module {
    //factory { Greeting() }
}