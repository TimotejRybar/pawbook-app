import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import data.model.entity.PetEntity
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.loading
import pawbook.composeapp.generated.resources.login
import pawbook.composeapp.generated.resources.screen_calendar
import pawbook.composeapp.generated.resources.screen_calendar_activity
import pawbook.composeapp.generated.resources.screen_home
import pawbook.composeapp.generated.resources.screen_login
import pawbook.composeapp.generated.resources.screen_my_pets
import pawbook.composeapp.generated.resources.screen_pet_dashboard
import pawbook.composeapp.generated.resources.screen_pet_edit
import pawbook.composeapp.generated.resources.screen_register
import presentation.screen.calendar.PetCalendar
import presentation.navigation.Navigation
import presentation.screen.calendar.PetCalendarActivity
import presentation.screen.gallery.Gallery
import presentation.screen.home.Home
import presentation.screen.login.LoginScreen
import presentation.screen.myPets.MyPets
import presentation.screen.petDashboard.PetDashboard
import presentation.screen.petDetail.PetDetail
import presentation.screen.register.RegisterScreen
import presentation.screen.splash.Splash
import presentation.theme.colors.LightThemeAppColors
import presentation.theme.colors.LocalAppColors

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class,
    ExperimentalResourceApi::class
)
@Composable
fun AppContent(navController: NavHostController = rememberNavController()) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val topBarState = rememberSaveable { mutableStateOf(false) }
    val selectedPet = remember { mutableStateOf<PetEntity?>(null) }

    val routeToLabelMap = mapOf(
        AppScreen.Splash.name to stringResource(Res.string.loading),
        AppScreen.Login.name to stringResource(Res.string.screen_login),
        AppScreen.Register.name to stringResource(Res.string.screen_register),
        AppScreen.MyPets.name to stringResource(Res.string.screen_my_pets),
        AppScreen.PetEdit.name to stringResource(Res.string.screen_pet_edit),
        AppScreen.Calendar.name to stringResource(Res.string.screen_calendar),
        AppScreen.CalendarActivity.name to stringResource(Res.string.screen_calendar_activity),
        AppScreen.PetDashboard.name to stringResource(Res.string.screen_pet_dashboard),
        AppScreen.Home.name to stringResource(Res.string.screen_home)
    )

    val currentRoute = navBackStackEntry?.destination?.route?.substringBefore("/")
    val label = remember(currentRoute) { mutableStateOf(routeToLabelMap[currentRoute] ?: "Unknown") }

    when (navBackStackEntry?.destination?.route) {
        AppScreen.Splash.name -> {
            topBarState.value = false
        }

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
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            Navigation(drawerState, onNavigate = {
                when (it) {
                    AppScreen.Login -> navController.navigate(AppScreen.Login.name)
                    AppScreen.PetEdit -> navController.navigate(AppScreen.PetEdit.name)
                    AppScreen.MyPets -> navController.navigate(AppScreen.MyPets.name)
                    AppScreen.Register -> navController.navigate(AppScreen.Register.name)
                    AppScreen.Splash -> navController.navigate(AppScreen.Splash.name)
                    AppScreen.Calendar -> navController.navigate(AppScreen.Calendar.name)
                    AppScreen.CalendarActivity -> navController.navigate(AppScreen.CalendarActivity.name)
                    AppScreen.Gallery -> navController.navigate(AppScreen.Gallery.name)
                    AppScreen.PetDashboard -> navController.navigate(AppScreen.petDashboardRoute(petId = selectedPet.value?.id ?: ""))
                    AppScreen.Home -> navController.navigate(AppScreen.Home.name)
                }
            }) {
                Scaffold(
                    floatingActionButton = {
                        if (navController.currentDestination?.route == AppScreen.MyPets.name) {
                            FloatingActionButton(containerColor = LocalAppColors.current.primary,
                                shape = CircleShape,
                                contentColor = LocalAppColors.current.secondary,
                                onClick = {
                                    navController.navigate(AppScreen.PetEdit.name)
                                }) {
                                Icon(Icons.Filled.Add, "")
                            }
                        }
                        if (navController.currentDestination?.route == AppScreen.Calendar.name) {
                            FloatingActionButton(containerColor = LocalAppColors.current.primary,
                                shape = CircleShape,
                                contentColor = LocalAppColors.current.secondary,
                                onClick = {
                                    navController.navigate(AppScreen.CalendarActivity.name)
                                }) {
                                Icon(Icons.Filled.Add, "")
                            }
                        }
                    },
                    topBar = {
                        AnimatedVisibility(
                            initiallyVisible = false,
                            visible = topBarState.value,
                            enter = slideInVertically(initialOffsetY = { -it }),
                            exit = slideOutVertically(targetOffsetY = { -it }),
                            content = {
                                if (topBarState.value) {
                                    TopAppBar(
                                        title = { Text(label.value) },
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
                        )
                    }) {
                    NavHost(
                        navController = navController,
                        startDestination = AppScreen.Splash.name,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(LocalAppColors.current.secondary)
                            .verticalScroll(rememberScrollState())
                            .padding(5.dp)
                    ) {

                        composable(route = AppScreen.Splash.name) {
                            Splash{
                                navController.navigate(AppScreen.Login.name)
                            }
                        }

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
                                selectedPet.value = it
                                navController.navigate(AppScreen.petDashboardRoute(it.id))
                            })
                        }

                        composable(route = AppScreen.PetEdit.name) {
                            PetDetail(selectedPet.value, onSaved = {
                                navController.navigate(AppScreen.Login.name)
                            })
                        }

                        composable(route = AppScreen.Calendar.name) {
                            PetCalendar()
                        }

                        composable(route = AppScreen.CalendarActivity.name) {
                            PetCalendarActivity() {
                                navController.navigate(AppScreen.Calendar.name)
                            }
                        }

                        composable(route = AppScreen.Gallery.name) {
                            Gallery(){
                            }
                        }

                        composable(
                            route = "PetDashboard/{petId}",
                            arguments = listOf(navArgument("petId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val petId = backStackEntry.arguments?.getString("petId") ?: return@composable
                            PetDashboard(petId = petId)
                        }

                        composable(route = AppScreen.Home.name) {
                            Home()
                        }
                    }
                }
            }
        }
    }
}