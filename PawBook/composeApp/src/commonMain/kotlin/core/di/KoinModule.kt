package core.di

import data.remote.LoginApi
import de.jensklingenberg.ktorfit.Ktorfit
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
        .baseUrl("https://localhost:3000/v1/")
        .build()
}

fun provideLoginApi(ktorfit: Ktorfit): LoginApi = ktorfit.create()

// called by iOS
fun initKoin() = initKoin{}

fun commonModule() = module {
    //factory { Greeting() }
}