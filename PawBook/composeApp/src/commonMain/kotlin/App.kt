import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import core.di.provideBreedsApi
import core.di.provideKtorfit
import core.di.provideLoginApi
import core.di.providePetsApi
import core.di.provideSettings

import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.theme.colors.LightThemeAppColors
import presentation.theme.colors.LocalAppColors
import presentation.navigation.Navigation
import presentation.screen.myPets.MyPets
import presentation.screen.petDetail.PetDetail
import data.remote.Preferences
import data.repository.LoginRepositoryImpl
import data.repository.PetDetailRepositoryImpl
import data.repository.PetsRepoitoryImpl
import data.repository.RegisterRepositoryImpl
import domain.model.PetItem
import org.koin.dsl.module
import presentation.screen.login.LoginScreen
import presentation.screen.login.LoginViewModel
import presentation.screen.petDetail.MyPetsViewModel
import presentation.screen.petDetail.PetDetailViewModel
import presentation.screen.register.RegisterScreen
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
fun App(navController: NavHostController = rememberNavController()) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val topBarState = rememberSaveable { mutableStateOf(false) }

    val selectedPet = remember { mutableStateOf(PetItem("CREATE","", "")) }

    when (navBackStackEntry?.destination?.route) {
        AppScreen.Login.name -> {
            topBarState.value = false
        }
        AppScreen.Register.name -> {
            topBarState.value = false
        }
        else -> {
            topBarState.value = true
        }
    }

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
                    AppScreen.Register -> navController.navigate(AppScreen.Register.name)
                }
            }) {
                Scaffold(
                    floatingActionButton = {
                       if(navController.currentDestination?.route == AppScreen.MyPets.name) {
                           FloatingActionButton(containerColor = LocalAppColors.current.primary, shape = CircleShape, contentColor = LocalAppColors.current.secondary,
                               onClick = {
                                    navController.navigate(AppScreen.PetDetail.name + "/" + "CREATE")
                           }){
                               Icon(Icons.Filled.Add,"")
                           }
                       }
                    },
                    topBar = {
                            AnimatedVisibility(
                                visible = topBarState.value,
                                enter = slideInVertically(initialOffsetY = { -it }),
                                exit = slideOutVertically(targetOffsetY = { -it }),
                                content = {
                                    if (topBarState.value) {
                                        TopAppBar(
                                            title = { Text("My Pets") },
                                            navigationIcon = {
                                                IconButton(onClick = {
                                                    scope.launch {
                                                        if (drawerState.isOpen) drawerState.close()
                                                        else drawerState.open()
                                                    }
                                                }) {
                                                    Icon(
                                                        Icons.Default.Menu,
                                                        contentDescription = "Menu"
                                                    )
                                                }
                                            }
                                        )
                                    }
                                },
                    )}){
                            NavHost(
                                navController = navController,
                                startDestination = AppScreen.Login.name,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(LocalAppColors.current.secondary)
                                    .verticalScroll(rememberScrollState())
                                    .padding(5.dp)
                            ) {

                                composable(route = AppScreen.Register.name) {
                                    RegisterScreen {
                                        navController.navigate(AppScreen.MyPets.name)
                                    }
                                }

                                composable(route = AppScreen.Login.name) {
                                    LoginScreen(
                                        onCreateAccount = {
                                            navController.navigate(AppScreen.Register.name)
                                    },
                                        onLoginSucces = {
                                            navController.navigate(AppScreen.MyPets.name)
                                    })
                                }

                                composable(route = AppScreen.MyPets.name) {
                                    MyPets(onItemClick = {
                                        navController.navigate(AppScreen.PetDetail.name)
                                    })
                                }

                                composable(route = AppScreen.PetDetail.name + "/" + selectedPet.value._id,
                                    arguments = listOf(navArgument("petId") { defaultValue = "CREATE" })) {
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

    single<RegisterViewModel> { RegisterViewModel()}
    single<RegisterRepositoryImpl> { RegisterRepositoryImpl() }

    single<LoginRepositoryImpl> { LoginRepositoryImpl() }
    single<LoginViewModel> { LoginViewModel()}

    single { provideLoginApi(get()) }

    single<MyPetsViewModel> { MyPetsViewModel()}
    single<PetsRepoitoryImpl> { PetsRepoitoryImpl() }
    single { providePetsApi(get()) }

    single<PetDetailViewModel> { PetDetailViewModel(get()) }
    single<PetDetailRepositoryImpl> { PetDetailRepositoryImpl() }
    single { provideBreedsApi(get()) }

    single<Preferences> { Preferences() }
    single { provideSettings() }
}


