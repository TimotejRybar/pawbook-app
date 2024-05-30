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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import domain.model.PetItem
import kotlinx.coroutines.launch
import presentation.screen.calendar.PetCalendar
import presentation.navigation.Navigation
import presentation.screen.calendar.PetCalendarActivity
import presentation.screen.login.LoginScreen
import presentation.screen.myPets.MyPets
import presentation.screen.petDetail.PetDetail
import presentation.screen.register.RegisterScreen
import presentation.screen.splash.Splash
import presentation.theme.colors.LightThemeAppColors
import presentation.theme.colors.LocalAppColors

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun AppContent(navController: NavHostController = rememberNavController()) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val topBarState = rememberSaveable { mutableStateOf(false) }
    val selectedPet = remember { mutableStateOf(PetItem.empty()) }

    val routeToLabelMap = mapOf(
        AppScreen.Splash.name to "Načítavam",
        AppScreen.Login.name to "Prihlásenie",
        AppScreen.Register.name to "Registrácia",
        AppScreen.MyPets.name to "Moje zvieratká",
        AppScreen.PetDetail.name to "Detail zvieratka",
        AppScreen.Calendar.name to "Kalendár",
        AppScreen.CalendarActivity.name to "Plánovanie aktivity"
    )

    val currentRoute = navBackStackEntry?.destination?.route
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
                    AppScreen.PetDetail -> navController.navigate(AppScreen.PetDetail.name)
                    AppScreen.MyPets -> navController.navigate(AppScreen.MyPets.name)
                    AppScreen.Register -> navController.navigate(AppScreen.Register.name)
                    AppScreen.Splash -> navController.navigate(AppScreen.Splash.name)
                    AppScreen.Calendar -> navController.navigate(AppScreen.Calendar.name)
                    AppScreen.CalendarActivity -> navController.navigate(AppScreen.CalendarActivity.name)
                }
            }) {
                Scaffold(
                    floatingActionButton = {
                        if (navController.currentDestination?.route == AppScreen.MyPets.name) {
                            FloatingActionButton(containerColor = LocalAppColors.current.primary,
                                shape = CircleShape,
                                contentColor = LocalAppColors.current.secondary,
                                onClick = {
                                    navController.navigate(AppScreen.PetDetail.name)
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
                                navController.navigate(AppScreen.PetDetail.name)
                            })
                        }

                        composable(route = AppScreen.PetDetail.name) {
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
                    }
                }
            }
        }
    }
}