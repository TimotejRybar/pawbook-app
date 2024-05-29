package presentation.screen.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.model.entity.PetEntity
import io.ktor.util.date.GMTDate
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.activity_type
import pawbook.composeapp.generated.resources.add
import pawbook.composeapp.generated.resources.description
import pawbook.composeapp.generated.resources.location
import pawbook.composeapp.generated.resources.select_date
import pawbook.composeapp.generated.resources.select_pets
import pawbook.composeapp.generated.resources.select_time
import presentation.components.petField.PetField
import presentation.screen.login.InputField
import presentation.screen.login.InputType
import presentation.screen.login.StyledButton
import presentation.screen.petDetail.CustomDatePickerDialog
import presentation.screen.petDetail.Spinner
import presentation.screen.petDetail.noRippleClickable
import presentation.theme.colors.LocalAppColors


@OptIn(ExperimentalResourceApi::class)
@Composable
fun PetCalendarActivity (viewModel: PetCalendarActivityViewModel = koinInject(), onSubmit: () -> Unit) {
    val pets = viewModel.pets.collectAsState()
    val selectedPets = remember { mutableListOf<PetEntity>() }
    val activity = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val location = remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().background(LocalAppColors.current.secondary),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            DateField()
            TimeField()
            MultiPetInput(pets.value) {
                selectedPets.clear()
                selectedPets.addAll(it)
            }
            ActivitySpinner {
                activity.value = it
            }
            LocationField(location.value){
                location.value = it
            }
            DescriptionField(description.value){
                description.value = it
            }
            Spacer(modifier = Modifier.height(20.dp))
            StyledButton(stringResource(Res.string.add)) {
                onSubmit()
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LocationField(value: String, onTextChanged: (String) -> Unit) {
    InputField(stringResource(Res.string.location), value, InputType.TEXT){
        onTextChanged(it)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DescriptionField(value: String, onTextChanged: (String) -> Unit) {
    InputField(stringResource(Res.string.description), value, InputType.TEXT, maxLines = 4){
        onTextChanged(it)
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
        CustomTimePickerDialog(
            onAccept = {
                isOpen.value = false

                if (it != null) {
                    date.value = it
                }
            },
            onCancel = {
                isOpen.value = false
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun CustomTimePickerDialog(
    onAccept: (LocalTime?) -> Unit,
    onCancel: () -> Unit,
) {
    val state = rememberTimePickerState()

    TimePickerDialog{
        TimePicker(
            state = state,
            colors = TimePickerDefaults.colors(containerColor = LocalAppColors.current.secondary)
        )
    }
    StyledButton(stringResource(Res.string.add)) {
        onAccept(LocalTime(state.hour, state.minute))
    }
}

@Composable
fun TimePickerDialog(
    content: @Composable () -> Unit
) {
    content()
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

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MultiPetInput(pets: List<PetEntity>, onPetSelected: (pets: List<PetEntity>) -> Unit) {
    PetField(stringResource(Res.string.select_pets), pets) {
        onPetSelected(pets)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ActivitySpinner(onSelected: (String) -> Unit) {
    val activityOptions = arrayListOf("Vychádzka", "Návšteva veterinára")
    Spinner(text = stringResource(Res.string.activity_type), options = activityOptions) {
        onSelected(it)
    }
}
