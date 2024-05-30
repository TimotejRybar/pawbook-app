package presentation.screen.petDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import data.model.entity.BreedEntity
import data.model.entity.ColorEntity
import data.model.entity.DoctorEntity
import domain.model.PetItem
import domain.model.enums.PetDetailState
import io.ktor.util.date.GMTDate
import domain.model.enums.PetPropFieldType
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.breed
import pawbook.composeapp.generated.resources.continue_pet_upload_profile_photo
import pawbook.composeapp.generated.resources.doctor
import pawbook.composeapp.generated.resources.gender
import pawbook.composeapp.generated.resources.save
import pawbook.composeapp.generated.resources.search_doctor
import pawbook.composeapp.generated.resources.search_pet_breed
import pawbook.composeapp.generated.resources.select
import pawbook.composeapp.generated.resources.select_photo
import pawbook.composeapp.generated.resources.sofka
import presentation.components.autocomplete.AutoComplete
import presentation.components.color.colorField.ColorField
import presentation.screen.login.ButtonStyle
import presentation.theme.colors.LocalAppColors
import presentation.screen.login.StyledButton
import utils.compose.PetPropFieldUtils

@Composable
fun PetDetail(pet: PetItem, viewModel: PetDetailViewModel = koinInject(), onSaved: () -> Unit) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(state == PetDetailState.SAVED) {
        if(state == PetDetailState.SAVED)
        onSaved()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        PetInfo(viewModel, pet)
    }
    LaunchedEffect(key1 = true) {
        viewModel.pet.value._id = "CREATE"
        viewModel.init()
    }


}

@Composable
fun PetInfo(viewModel: PetDetailViewModel, pet: PetItem) {
    val breeds by viewModel.breeds.collectAsState()
    val doctors by viewModel.doctors.collectAsState()
    val colors by viewModel.colors.collectAsState()

    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            PetPhoto(viewModel)
            PetProps(viewModel, breeds, colors, doctors, pet)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SaveButton(onFormSubmit: () -> Unit) {
    StyledButton(stringResource(Res.string.save), ButtonStyle.FillPrimary, 0.dp) {
        onFormSubmit()
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PetPhoto(viewModel: PetDetailViewModel) {
    val openDialog = remember { mutableStateOf(false) }

    val context = LocalPlatformContext.current
    val pickerLauncher = rememberFilePickerLauncher(
            type = FilePickerFileType.Image,
    selectionMode = FilePickerSelectionMode.Single,
    onResult = { files ->
            viewModel.uploadProfilePicture(context, files)
        })

    CirclePhoto {
        openDialog.value = true
    }
    if (openDialog.value) {
        AlertDialog(
            containerColor = LocalAppColors.current.secondary,
            onDismissRequest = {},
            properties = DialogProperties(),
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LocalAppColors.current.primary
                    ),
                    onClick = {
                        pickerLauncher.launch()
                    }) {
                    Text(color = Color.White, text = stringResource(Res.string.select))
                }
            },
            icon = {
                Icon(imageVector = Icons.Default.Star, "dialog icon")
            },
            title = { Text(stringResource(Res.string.select_photo)) },
            text = { Text(stringResource(Res.string.continue_pet_upload_profile_photo)) },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LocalAppColors.current.darkGray
                    ),
                    onClick = {
                        openDialog.value = false
                    }) {
                    Text("Zrušiť")
                }
            }
        )
    }
}

@Composable
fun PetProps(viewModel: PetDetailViewModel, breeds: List<BreedEntity>, colors: List<ColorEntity>, doctors: List<DoctorEntity>, pet: PetItem) {
    val name = remember { mutableStateOf("") }
    val weight = remember { mutableStateOf("") }
    val now = Clock.System.now()
    val tz = TimeZone.currentSystemDefault()
    val today = now.toLocalDateTime(tz)
    val birthDay = remember { mutableStateOf(today) }
    val breed = remember { mutableStateOf<BreedEntity?>(null) }
    val gender = remember { mutableStateOf("") }
    val color = remember { mutableStateListOf("") }
    val doctor = remember { mutableStateOf<DoctorEntity?>(null) }
    val photo by viewModel.profilePicture.collectAsState()

    PetPropField(PetPropFieldType.NAME) {
        name.value = it
    }
    DatePropField()
    PetPropField(PetPropFieldType.WEIGHT) {
        weight.value = it
    }
    ColorSpinner(colors) { it1 ->
        color.clear()
        color.addAll(it1.map{it.color})
    }
    BreedSpinner(breeds) {
        breed.value = it
    }
    GenderSpinner {
        gender.value = it
    }
    DoctorSpinner(doctors) {
        doctor.value = it
    }
    Spacer(modifier = Modifier.height(20.dp))
    SaveButton {
       // create new pet
       viewModel.createPet(PetItem(null, name.value, "", pet.petType, null, birthDay.value, weight.value.toFloat(),
           color,breed.value?.id as String, photo, null, null))
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DoctorSpinner(doctors: List<DoctorEntity>, onSelected: (DoctorEntity) -> Unit) {
    AutoComplete(stringResource(Res.string.doctor), stringResource(Res.string.search_doctor), doctors) {
        onSelected(it as DoctorEntity)
    }
}

@Composable
fun ColorSpinner(colors: List<ColorEntity>, onColorSelected: (colors: List<ColorEntity>) -> Unit) {
    ColorField("Farba zvieratka", colors) {
        onColorSelected(colors)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GenderSpinner(onSelected: (String) -> Unit) {
    val genderOptions = arrayListOf("Pes", "Fenka")
    Spinner(text = stringResource(Res.string.gender), options = genderOptions) {
        onSelected(it)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BreedSpinner(breeds: List<BreedEntity>, onSelected: (BreedEntity) -> Unit) {
    AutoComplete(stringResource(Res.string.breed),
        stringResource(Res.string.search_pet_breed), breeds) {
        onSelected(it as BreedEntity)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetPropField(petPropFieldType: PetPropFieldType, onValueChange: (String) -> Unit) {
    val text = PetPropFieldUtils.getPropFieldText(petPropFieldType)
    var inputType = KeyboardOptions(keyboardType = KeyboardType.Text)
    val value = remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(false) }

    when(petPropFieldType) {
        PetPropFieldType.WEIGHT -> {
            inputType = KeyboardOptions(keyboardType = KeyboardType.Number)
        }
        else -> {

        }
    }

    TextField(
        label = {
            Text(text, color = Color.Black)
        },
        isError = !isValid,
        value = value.value,
        onValueChange = {
            value.value = it
            isValid = validateField(petPropFieldType, it)
            onValueChange(it)
        },
        keyboardOptions = inputType,
        colors = TextFieldDefaults.textFieldColors(
        containerColor = Color.Transparent,
        cursorColor = Color.Black,
        focusedIndicatorColor =  LocalAppColors.current.primary,
        unfocusedIndicatorColor = LocalAppColors.current.primary,
            errorContainerColor = Color.Transparent,
            errorIndicatorColor = LocalAppColors.current.error,
            errorTextColor = LocalAppColors.current.error)
    )
}

fun validateField(petPropFieldType: PetPropFieldType, it: String): Boolean {
    when(petPropFieldType){
        PetPropFieldType.NAME -> {
            return it.length > 1
        }

        PetPropFieldType.WEIGHT -> {
            var isValid = it.isNotEmpty()
            try { it.toFloat()}
            catch (e: Exception) { isValid = false}
            return isValid
        }

        else -> {
            return true
        }
    }
}

@Composable
fun DatePropField() {
    val text = PetPropFieldUtils.getPropFieldText(PetPropFieldType.BIRTH)
    val date = remember { mutableStateOf(GMTDate()) }
    val isOpen = remember { mutableStateOf(false) }

    TextField(
        label = {
            Text(text, color = Color.Black)
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
fun CirclePhoto(onClick: () -> Unit) {
    Image(
        painter = painterResource(Res.drawable.sofka),
        contentDescription = "Pet photo",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .clickable { onClick() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    onAccept: (Long?) -> Unit,
    onCancel: () -> Unit
) {
    val state = rememberDatePickerState()

    DatePickerDialog(
        colors = DatePickerDefaults.colors(containerColor = LocalAppColors.current.secondary),
        onDismissRequest = { },
        confirmButton = {
            Button(onClick = { onAccept(state.selectedDateMillis) }) {
                Text("Accept")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = state)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Spinner(
    text: String,
    options: List<String>,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded // Toggle the dropdown menu
        }
    ) {
        TextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {
                selectedOptionText = it
            },
            label = { Text(text, color = Color.Black) },
            modifier = Modifier.menuAnchor(),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedIndicatorColor = LocalAppColors.current.primary,
                unfocusedIndicatorColor = LocalAppColors.current.primary,
                disabledIndicatorColor = LocalAppColors.current.primary
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier.background(LocalAppColors.current.secondary)
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxHeight(),
                    text = {
                        Text(text = selectionOption)
                    },
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                        onSelected(selectedOptionText)
                    }
                )
            }
        }
    }
}



inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier =
    this.then(
        composed {
            clickable(indication = null,
                interactionSource = remember { MutableInteractionSource() }) {
                onClick()
            }
        })

