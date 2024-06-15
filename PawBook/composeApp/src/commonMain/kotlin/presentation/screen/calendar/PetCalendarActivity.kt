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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.enums.ActivityType
import data.model.entity.PetEntity
import domain.model.CalendarActivity
import io.ktor.util.date.GMTDate
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.activity_duration_type
import pawbook.composeapp.generated.resources.activity_type
import pawbook.composeapp.generated.resources.add
import pawbook.composeapp.generated.resources.description
import pawbook.composeapp.generated.resources.location
import pawbook.composeapp.generated.resources.repeated_activity
import pawbook.composeapp.generated.resources.select_date
import pawbook.composeapp.generated.resources.select_pets
import pawbook.composeapp.generated.resources.select_time
import presentation.components.petField.PetField
import presentation.screen.login.InputField
import presentation.screen.login.InputType
import presentation.screen.login.StyledButton
import presentation.screen.petCreate.CustomDatePickerDialog
import presentation.screen.petCreate.Spinner
import presentation.screen.petCreate.noRippleClickable
import presentation.theme.colors.LocalAppColors
import utils.compose.addHours
import utils.compose.addMinutes

enum class ActivityDuration {
   // val activityOptions = arrayListOf("15 minút", "30 minút", "1 hodina", "2 hodiny", "4 hodiny", "Celý deň")
    MINUTES_15,
    MINUTES_30,
    HOURS_1,
    HOURS_2,
    HOURS_4,
    HOURS_8
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PetCalendarActivity (viewModel: PetCalendarActivityViewModel = koinInject(), onSubmit: () -> Unit) {
    val pets = viewModel.pets.collectAsState()
    val selectedPets = remember { mutableListOf<PetEntity>() }
    val activity = remember { mutableStateOf<ActivityType?>(null) }
    val description = remember { mutableStateOf("") }
    val location = remember { mutableStateOf("") }
    val start = remember { mutableStateOf<LocalDateTime?>(LocalDateTime(LocalDate(1,1,1), LocalTime(1,1))) }
    val time = remember { mutableStateOf<LocalTime?>(LocalTime(12,0)) }
    val duration = remember { mutableStateOf<ActivityDuration?>(null) }

    LaunchedEffect(true) {
        viewModel.loadPets()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().background(LocalAppColors.current.secondary),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            DateField() {
                start.value = LocalDateTime(it, time.value as LocalTime)
            }
            TimeField() {
                time.value = it
                start.value = LocalDateTime(start.value?.date as LocalDate, time.value as LocalTime)
            }
            DurationField {
                duration.value = it
            }
            RepeatEvent()
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
                viewModel.createActivity(CalendarActivity(null, ArrayList(selectedPets.map { mapPetEntityToDto(it) }), start.value, calculateEnd(start.value, duration.value),  activity.value as ActivityType, location.value, description.value, null, null))
                onSubmit()
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun RepeatEvent() {

    val options = remember { arrayListOf("Len raz", "Každý deň", "Každý týždeň", "Každý mesiac") }

    Spinner(
        defaultValue = "",
        text = stringResource(Res.string.repeated_activity),
        options = options)
    {

    }
}

fun mapPetEntityToDto(petEntity: PetEntity): String{
 return petEntity.id
 //return PetItem(petEntity.id, petEntity.name, petEntity.shortDescription, petEntity.petType, null, petEntity.birthDay, petEntity.weight, petEntity.color, petEntity.breed, petEntity.photo, petEntity.updatedAt, petEntity.createdAt)
}

fun calculateEnd(value: LocalDateTime?, activityDuration: ActivityDuration?): LocalDateTime? {
    when(activityDuration){
        ActivityDuration.MINUTES_15 -> value?.addMinutes(15)
        ActivityDuration.MINUTES_30 -> value?.addMinutes(30)
        ActivityDuration.HOURS_1 -> value?.addHours(1)
        ActivityDuration.HOURS_2 -> value?.addHours(2)
        ActivityDuration.HOURS_4 -> value?.addHours(4)
        ActivityDuration.HOURS_8 -> value?.addHours(8)
        null -> TODO()
    }
    return value
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DurationField(onSelected: (ActivityDuration) -> Unit) {
    val activityOptions = arrayListOf("15 minút", "30 minút", "1 hodina", "2 hodiny", "4 hodiny", "Celý deň")

    Spinner(defaultValue = "", text = stringResource(Res.string.activity_duration_type), options = activityOptions) {
        onSelected(ActivityDuration.entries[activityOptions.indexOf(it)])
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
fun TimeField(onSelected: (LocalTime?) -> Unit) {
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
                onSelected(it)
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
fun DateField(onSelected: (LocalDate) -> Unit) {
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
                onSelected(LocalDate(date.value.year, date.value.month.ordinal, date.value.dayOfMonth))
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
fun ActivitySpinner(onSelected: (ActivityType) -> Unit) {
    val activityOptions = arrayListOf("Vychádzka", "Návšteva veterinára", "Lieky")
    Spinner(defaultValue = "", text = stringResource(Res.string.activity_type), options = activityOptions) {
        onSelected(ActivityType.entries[activityOptions.indexOf(it)])
    }
}
