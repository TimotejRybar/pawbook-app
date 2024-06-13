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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.regular.Edit
import compose.icons.fontawesomeicons.regular.File
import compose.icons.fontawesomeicons.regular.Folder
import compose.icons.fontawesomeicons.solid.Plus
import data.model.entity.PetEntity
import domain.model.enums.StorageState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.contact
import pawbook.composeapp.generated.resources.documents
import pawbook.composeapp.generated.resources.loading
import pawbook.composeapp.generated.resources.messages
import pawbook.composeapp.generated.resources.new_folder
import pawbook.composeapp.generated.resources.profile
import pawbook.composeapp.generated.resources.screen_calendar
import pawbook.composeapp.generated.resources.screen_calendar_activity
import pawbook.composeapp.generated.resources.screen_home
import pawbook.composeapp.generated.resources.screen_login
import pawbook.composeapp.generated.resources.screen_my_pets
import pawbook.composeapp.generated.resources.screen_pet_dashboard
import pawbook.composeapp.generated.resources.screen_pet_edit
import pawbook.composeapp.generated.resources.screen_register
import pawbook.composeapp.generated.resources.upload_file
import presentation.components.button.FabItem
import presentation.components.button.MultiFloatingActionButton
import presentation.navigation.Navigation
import presentation.screen.calendar.PetCalendar
import presentation.screen.calendar.PetCalendarActivity
import presentation.screen.contact.Contact
import presentation.screen.gallery.Gallery
import presentation.screen.home.Home
import presentation.screen.login.LoginScreen
import presentation.screen.myPets.MyPets
import presentation.screen.petDashboard.PetDashboard
import presentation.screen.profile.Profile
import presentation.screen.register.RegisterScreen
import presentation.screen.splash.Splash
import presentation.screen.storage.Storage
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
        AppScreen.Home.name to stringResource(Res.string.screen_home),
        AppScreen.Profile.name to stringResource(Res.string.profile),
        AppScreen.Contact.name to stringResource(Res.string.contact),
        AppScreen.Storage.name to stringResource(Res.string.documents),
        AppScreen.Conversations.name to stringResource(Res.string.messages)
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
            val storageState = remember { mutableStateOf(StorageState.INIT) }
            val subPage = remember { mutableStateOf(false) }

            Navigation(drawerState, onNavigate = {
                navController.navigate(it.name)
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
                        if (navController.currentDestination?.route == AppScreen.Storage.name) {
                            MultiFloatingActionButton(fabIcon = FontAwesomeIcons.Solid.Plus, items = arrayListOf(
                                FabItem(FontAwesomeIcons.Regular.Folder, label = stringResource(Res.string.new_folder)) {
                                    storageState.value = StorageState.CREATE_FOLDER

                                },
                                FabItem(FontAwesomeIcons.Regular.File, label = stringResource(Res.string.upload_file)) {
                                    storageState.value = StorageState.UPLOAD_FILE
                                }

                            ))
                        }
                    },
                    topBar = {
                        AnimatedVisibility(
                            visible = topBarState.value,
                            enter = slideInVertically(initialOffsetY = { -it }),
                            exit = slideOutVertically(targetOffsetY = { -it }),
                            initiallyVisible = false,
                            content = {

                                subPage.value = when (navController.currentDestination?.route) {
                                    AppScreen.PetEdit.name, AppScreen.CalendarActivity.name, AppScreen.PetDashboard.name-> true
                                    else -> false
                                }

                                val topBarIcon = if (subPage.value) Icons.AutoMirrored.Filled.ArrowBack else Icons.Default.Menu

                                if (topBarState.value) {
                                    TopAppBar(
                                        title = { Text(label.value) },
                                        navigationIcon = {
                                            IconButton(onClick = {
                                                scope.launch {
                                                    if (!subPage.value) {
                                                        if (drawerState.isOpen) drawerState.close()
                                                        else drawerState.open()
                                                    } else {
                                                        navController.popBackStack()
                                                    }
                                                }
                                            }) {
                                                Icon(
                                                    topBarIcon,
                                                    contentDescription = "Menu"
                                                )
                                            }
                                        },
                                        actions = {
                                            if(navController.currentDestination?.route == AppScreen.PetDashboard.name) {
                                                IconButton(onClick = {
                                                    val petId = selectedPet.value?.id
                                                    if (petId != null) {
                                                        navController.navigate("PetEdit/$petId")
                                                    }
                                                }) {
                                                    Icon(
                                                        FontAwesomeIcons.Regular.Edit,
                                                        contentDescription = "Edit pet"
                                                    )
                                                }
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

                        composable(
                            route = "PetEdit/{petId}",
                            arguments = listOf(navArgument("petId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val petId = backStackEntry.arguments?.getString("petId") ?: return@composable
                            PetEdit(petId, onSaved = {
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

                        composable(route = AppScreen.Profile.name) {
                            Profile()
                        }

                        composable(route = AppScreen.Contact.name) {
                            Contact()
                        }

                        composable(route = AppScreen.Storage.name) {
                            Storage(storageState.value)
                        }
                    }
                }
            }
        }
    }
}