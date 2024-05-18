import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import core.di.provideBreedsApi
import core.di.provideKtorfit
import core.di.provideLoginApi
import core.di.providePetsApi
import core.di.provideSettings
import data.remote.Preferences
import data.repository.LoginRepositoryImpl
import data.repository.PetDetailRepositoryImpl
import data.repository.PetsRepoitoryImpl
import data.repository.RegisterRepositoryImpl
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.dsl.module
import presentation.screen.login.LoginViewModel
import presentation.screen.petDetail.MyPetsViewModel
import presentation.screen.petDetail.PetDetailViewModel
import presentation.screen.register.RegisterViewModel

enum class AppScreen() {
    Login(),
    PetDetail(),
    MyPets(),
    Register()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    AppContent()
}

fun appModule() = module {
    factory { provideKtorfit() }

    single<RegisterViewModel> { RegisterViewModel()}
    single<RegisterRepositoryImpl> { RegisterRepositoryImpl() }

    single<LoginRepositoryImpl> { LoginRepositoryImpl() }
    single<LoginViewModel> { LoginViewModel()}

    single { provideLoginApi(get()) }

    single<MyPetsViewModel> { MyPetsViewModel()}
    single<PetsRepoitoryImpl> { PetsRepoitoryImpl() }
    single { providePetsApi(get()) }

    single<PetDetailViewModel> { PetDetailViewModel() }
    single<PetDetailRepositoryImpl> { PetDetailRepositoryImpl() }
    single { provideBreedsApi(get()) }

    single<Preferences> { Preferences() }
    single { provideSettings() }
}


