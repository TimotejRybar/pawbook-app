import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import core.di.initKoin
import core.di.provideKtorfit
import core.di.provideLoginApi
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.theme.colors.LightThemeAppColors
import presentation.theme.colors.LocalAppColors
import presentation.components.PetDetailExtended
import presentation.navigation.Navigation
import presentation.screen.myPets.MyPets
import presentation.screen.petDetail.PetDetail
import core.util.PetUtils
import data.remote.LoginApi
import data.repository.LoginRepositoryImpl
import domain.repository.LoginRepository
import org.koin.core.KoinApplication
import org.koin.dsl.module
import presentation.screen.login.LoginScreen
import presentation.screen.login.LoginViewModel

enum class AppScreen() {
    Login(),
    PetDetail(),
    MyPets()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(navController: NavHostController = rememberNavController()) {
    initKoin(appDeclaration = {
        modules(appModule())
    })
    CompositionLocalProvider(
        LocalAppColors provides LightThemeAppColors
    ) {
        MaterialTheme(
        ) {
            var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            Navigation(drawerState, onNavigate = {
                when (it) {
                    AppScreen.Login -> navController.navigate(AppScreen.Login.name)
                    AppScreen.PetDetail -> navController.navigate(AppScreen.PetDetail.name)
                    AppScreen.MyPets -> navController.navigate(AppScreen.MyPets.name)
                }
            }) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("My Pets") },
                            navigationIcon = {
                                IconButton(onClick = {
                                    scope.launch {
                                        if (drawerState.isOpen) drawerState.close()
                                        else drawerState.open()
                                    }

                                }) {
                                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                                }
                            }
                        )
                    },
                    containerColor = LocalAppColors.current.secondary
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = AppScreen.Login.name,
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(5.dp)
                    ) {

                        val loginViewModel = LoginViewModel()
                        composable(route = AppScreen.Login.name) {
                            LoginScreen(loginViewModel) {
                                navController.navigate(AppScreen.MyPets.name)
                            }
                        }


                        composable(route = AppScreen.MyPets.name) {
                            MyPets(onItemClick = {
                                navController.navigate(AppScreen.PetDetail.name)
                            })
                        }

                        composable(route = AppScreen.PetDetail.name) {
                            PetDetail(onDismissClick = {
                                navController.navigate(AppScreen.Login.name)
                            })
                        }
                    }
                }
            }
        }
    }
}

fun appModule() = module {
    factory { provideKtorfit() }
    single<LoginRepositoryImpl> { LoginRepositoryImpl() }
    single<LoginViewModel> { LoginViewModel()}
    single { provideLoginApi(get()) }
}


