package presentation.navigation

import AppScreen
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.app_name
import pawbook.composeapp.generated.resources.screen_calendar
import pawbook.composeapp.generated.resources.screen_home
import pawbook.composeapp.generated.resources.screen_my_pets

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Navigation(
    drawerState: DrawerState,
    onNavigate: (AppScreen) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(Res.string.app_name), modifier = Modifier.padding(start = 16.dp))
                    Spacer(modifier = Modifier.weight(1f))
                    CloseButton(onClose = { scope.launch { drawerState.close() } })
                }
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text(text = stringResource(Res.string.screen_home)) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        onNavigate(AppScreen.Home)
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = stringResource(Res.string.screen_my_pets))  },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        onNavigate(AppScreen.MyPets)
                    }
                )
                NavigationDrawerItem(
                    label = { Text(text = stringResource(Res.string.screen_calendar)) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                        onNavigate(AppScreen.Calendar)
                    }
                )
            }
        }
    ) {
        content(PaddingValues())
    }
}

@Composable
private fun CloseButton(onClose: () -> Unit) {
    IconButton(
        onClick = onClose,
        modifier = Modifier.padding(end = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "Close navigation"
        )
    }
}
