package presentation.screen.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.model.entity.ColorEntity
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.activity_planning_dialog_title
import pawbook.composeapp.generated.resources.add
import presentation.components.color.colorPicker.DialogTitle
import presentation.screen.login.StyledButton
import presentation.screen.myPets.Pets
import presentation.theme.colors.LocalAppColors



@OptIn(ExperimentalResourceApi::class)
@Composable
fun PetCalendarActivity (viewModel: PetCalendarActivityViewModel = koinInject(), onSubmit: () -> Unit) {
    val pets = viewModel.pets.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().background(LocalAppColors.current.secondary),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            //DialogTitle(stringResource(Res.string.activity_planning_dialog_title))

            StyledButton(stringResource(Res.string.add)) {
                onSubmit()
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}