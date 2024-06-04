package core.di

import com.russhwolf.settings.Settings
import core.common.DatabaseSync
import data.local.AppDatabase
import data.local.getDatabase
import data.remote.BreedApi
import data.remote.CalendarActivityApi
import data.remote.ColorApi
import data.remote.DoctorApi
import data.remote.AuthApi
import data.remote.PetApi
import data.local.Preferences
import data.remote.StorageApi
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

object KoinModule {

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
            .baseUrl("https://pawbook.uplab.sk/v1/")
            .httpClient(HttpClient {
                // install(HttpCache)
                install(ContentNegotiation)
                {
                    json(
                        Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                            explicitNulls = false
                        }
                    )
                }
                install(DefaultRequest) {
                    header("Authorization", "Bearer $token")
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                }
            }).build()
    }

    fun provideLoginApi(ktorfit: Ktorfit): AuthApi = ktorfit.create()
    fun providePetsApi(ktorfit: Ktorfit): PetApi = ktorfit.create()
    fun provideBreedsApi(ktorfit: Ktorfit): BreedApi = ktorfit.create()
    fun provideDoctorsApi(ktorfit: Ktorfit): DoctorApi = ktorfit.create()
    fun provideColorsApi(ktorfit: Ktorfit): ColorApi = ktorfit.create()
    fun provideStorageApi(ktorfit: Ktorfit): StorageApi = ktorfit.create()
    fun provideCalendarActivityApi(ktorfit: Ktorfit): CalendarActivityApi = ktorfit.create()
    fun provideSettings(): Settings = Settings()
    fun provideDatabase(): AppDatabase = getDatabase()
    fun provideDatabaseSync(): DatabaseSync = DatabaseSync()

    // called by iOS
    fun doInitKoin() = initKoin {}

    private fun commonModule() = module {
        //factory { Greeting() }
    }
}


