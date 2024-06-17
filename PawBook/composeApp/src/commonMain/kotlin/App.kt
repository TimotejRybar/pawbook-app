import androidx.compose.runtime.Composable
import core.di.KoinModule
import core.di.KoinModule.provideIntentLauncher
import core.util.IntentLauncher
import data.local.Preferences
import data.repository.GalleryRepositoryImpl
import data.repository.LoginRepositoryImpl
import data.repository.PetCalendarRepositoryImpl
import data.repository.PetDashboardRepositoryImpl
import data.repository.PetDetailRepositoryImpl
import data.repository.PetsRepoitoryImpl
import data.repository.ProfileRepositoryImpl
import data.repository.RegisterRepositoryImpl
import data.repository.StorageRepositoryImpl
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.dsl.module
import presentation.screen.calendar.PetCalendarActivityViewModel
import presentation.screen.calendar.PetCalendarViewModel
import presentation.screen.contact.ContactViewModel
import presentation.screen.gallery.GalleryViewModel
import presentation.screen.galleryUpload.GalleryUploadViewModel
import presentation.screen.home.HomeViewModel
import presentation.screen.login.LoginViewModel
import presentation.screen.petCreate.MyPetsViewModel
import presentation.screen.petCreate.PetCreateViewModel
import presentation.screen.petDashboard.PetDashboardViewModel
import presentation.screen.profile.ProfileViewModel
import presentation.screen.register.RegisterViewModel
import presentation.screen.splash.SplashViewModel
import presentation.screen.storage.StorageViewModel

enum class AppScreen() {
    Splash,
    Login,
    PetCreate,
    MyPets,
    Calendar,
    CalendarActivity,
    Register,
    Gallery,
    PetDashboard,
    Home,
    Profile,
    Contact,
    Storage,
    Conversations,
    GalleryUpload;

    companion object {
        fun petDashboardRoute(petId: String) = "PetDashboard/$petId"
    }
}
@Composable
@Preview
fun App() {
    AppContent()
}

fun appModule() = module {
    factory { KoinModule.provideKtorfit() }

    single { KoinModule.provideColorsApi(get()) }

    single { KoinModule.provideDatabase() }

    single<SplashViewModel> { SplashViewModel() }

    single<RegisterViewModel> { RegisterViewModel()}
    single<RegisterRepositoryImpl> { RegisterRepositoryImpl() }

    single<LoginRepositoryImpl> { LoginRepositoryImpl() }
    single<LoginViewModel> { LoginViewModel()}

    single { KoinModule.provideLoginApi(get()) }

    single<MyPetsViewModel> { MyPetsViewModel()}
    single<PetsRepoitoryImpl> { PetsRepoitoryImpl() }
    single { KoinModule.providePetsApi(get()) }

    single<PetCreateViewModel> { PetCreateViewModel() }
    single<PetDetailRepositoryImpl> { PetDetailRepositoryImpl() }
    single { KoinModule.provideBreedsApi(get()) }
    single { KoinModule.provideDoctorsApi(get()) }

    single<Preferences> { Preferences() }
    single { KoinModule.provideSettings() }
    single { KoinModule.provideDatabaseSync() }

    single<PetCalendarViewModel> { PetCalendarViewModel() }
    single<PetCalendarActivityViewModel> { PetCalendarActivityViewModel() }
    single { KoinModule.provideCalendarActivityApi(get()) }
    single<PetCalendarRepositoryImpl> { PetCalendarRepositoryImpl() }

    single { KoinModule.provideStorageApi(get()) }

    single<PetDashboardRepositoryImpl> { PetDashboardRepositoryImpl() }
    single<PetDashboardViewModel> { PetDashboardViewModel() }

    single<ProfileViewModel> { ProfileViewModel() }
    single<ProfileRepositoryImpl> { ProfileRepositoryImpl()}

    single<ContactViewModel> { ContactViewModel() }

    single<HomeViewModel> { HomeViewModel()}

    single<StorageViewModel> { StorageViewModel() }
    single<StorageRepositoryImpl> { StorageRepositoryImpl() }

    single<IntentLauncher> { provideIntentLauncher() }

    single<GalleryRepositoryImpl> { GalleryRepositoryImpl() }
    single<GalleryViewModel> { GalleryViewModel() }
    single<GalleryUploadViewModel> { GalleryUploadViewModel()}
}


