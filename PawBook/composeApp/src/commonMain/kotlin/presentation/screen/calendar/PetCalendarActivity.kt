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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.model.entity.ColorEntity
import domain.model.enums.PetPropFieldType
import io.ktor.util.date.GMTDate
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.activity_planning_dialog_title
import pawbook.composeapp.generated.resources.add
import pawbook.composeapp.generated.resources.select_date
import pawbook.composeapp.generated.resources.select_time
import presentation.components.color.colorPicker.DialogTitle
import presentation.screen.login.StyledButton
import presentation.screen.myPets.Pets
import presentation.screen.petDetail.CustomDatePickerDialog
import presentation.screen.petDetail.noRippleClickable
import presentation.theme.colors.LocalAppColors
import utils.compose.PetPropFieldUtils


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
            DateField()
            TimeField()
            StyledButton(stringResource(Res.string.add)) {
                onSubmit()
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TimeField() {
    val date = remember { mutableStateOf(LocalTime(12,0)) }
    val isOpen = remember { mutableStateOf(false) }

    TextField(
        label = {
            Text(stringResource(Res.string.select_time), color = Color.Black)
        },
        value = date.value.toString(),
        enabled = false,
        onValueChange = { },
        modifier = Modifier.noRippleClickable {
            isOpen.value = true
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            cursorColor = Color.Black,
            disabledLabelColor = Color.Black,
            disabledTextColor = Color.Black,
            focusedIndicatorColor = LocalAppColors.current.primary,
            unfocusedIndicatorColor = LocalAppColors.current.primary,
            disabledIndicatorColor = LocalAppColors.current.primary
        )

    )

    if (isOpen.value) {
        CustomDatePickerDialog(
            onAccept = {
                isOpen.value = false // close dialog

                if (it != null) { // Set the date
                    date.value = LocalTime.fromNanosecondOfDay(it)
                }
            },
            onCancel = {
                isOpen.value = false //close dialog
            })
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun DateField() {
    val date = remember { mutableStateOf(GMTDate()) }
    val isOpen = remember { mutableStateOf(false) }

    TextField(
        label = {
            Text(stringResource(Res.string.select_date), color = Color.Black)
        },
        value = (date.value.dayOfMonth.toString() + ". " + date.value.month.toString() + " " +  date.value.year.toString()),
        enabled = false,
        onValueChange = { },
        modifier = Modifier.noRippleClickable {
            isOpen.value = true
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            cursorColor = Color.Black,
            disabledLabelColor = Color.Black,
            disabledTextColor = Color.Black,
            focusedIndicatorColor = LocalAppColors.current.primary,
            unfocusedIndicatorColor = LocalAppColors.current.primary,
            disabledIndicatorColor = LocalAppColors.current.primary
        )

    )

    if (isOpen.value) {
        CustomDatePickerDialog(
            onAccept = {
                isOpen.value = false // close dialog

                if (it != null) { // Set the date
                    date.value = GMTDate(it)
                }
            },
            onCancel = {
                isOpen.value = false //close dialog
            })
    }
}
