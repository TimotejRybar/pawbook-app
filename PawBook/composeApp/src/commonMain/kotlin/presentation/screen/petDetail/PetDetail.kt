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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.runtime.getValue
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
import com.hoc081098.kmp.viewmodel.koin.compose.koinKmpViewModel
import domain.model.PetBreed
import io.ktor.util.date.GMTDate
import domain.model.enums.PetPropFieldType
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.sofka
import presentation.screen.login.ButtonStyle
import presentation.theme.colors.LocalAppColors
import presentation.screen.login.StyledButton
import utils.compose.PetPropFieldUtils

@Composable
fun PetDetail(petId: String, viewModel: PetDetailViewModel = koinKmpViewModel(
key = "PetDetailViewModel-$petId",
parameters = { parametersOf(petId) }
), onDismissClick: () -> Unit) {
    //Navbar()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        PetInfo(viewModel)
    }

    viewModel.init()
}

@Composable
fun PetInfo(viewModel: PetDetailViewModel) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            PetPhoto()
            PetProps(viewModel)
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun SaveButton(onFormSubmit: () -> Unit) {
    StyledButton("Save", ButtonStyle.FillPrimary, 0.dp) {

    }
}

@Composable
fun PetPhoto() {
    var openDialog = remember { mutableStateOf(true) }
    CirclePhoto() {
        openDialog.value = true
    }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {},
            properties = DialogProperties(),
            confirmButton = {
                Button(
                    onClick = {
                        //openDialog.value = false
                    }) {
                    Text("Select")
                }
            },
            icon = {
                Icon(imageVector = Icons.Default.Star, "dialog icon")
            },
            title = { Text("Select photo") },
            text = { Text("Continue If you want to upload new main pet photo") },
            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                    }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun PetProps(viewModel: PetDetailViewModel) {
    val name = mutableStateOf("")
    val weight = mutableStateOf("")

    PetPropField(PetPropFieldType.NAME) {
        name.value = it
    }
    DatePropField()
    PetPropField(PetPropFieldType.WEIGHT) {
        weight.value = it
    }
    PetPropField(PetPropFieldType.COLOR) {

    }
    BreedSpinner(viewModel.breeds)
    GenderSpinner()
    Spacer(modifier = Modifier.height(20.dp))
    SaveButton() {
        // save + check errors
    }
}

@Composable
fun GenderSpinner() {
    val genderOptions = arrayListOf("Male", "Female")
    Spinner(text = "Gender", options = genderOptions, autoComplete = false) {

    }
}

@Composable
fun BreedSpinner(breeds: List<PetBreed>) {

    val breedsStrings = breeds.map { it.name }

    Spinner(text = "Breed", options = breedsStrings, autoComplete = true) {

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
    autoComplete: Boolean,
    options: List<String>,
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = it
        }
    ) {
        TextField(
            readOnly = !autoComplete,
            value = selectedOptionText,
            onValueChange = {
                selectedOptionText = it
                if(autoComplete) expanded = true // Always expand when typing in autocomplete mode
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

        val filteredOptions = if (autoComplete && selectedOptionText.isNotEmpty()) {
            options.filter { it.contains(selectedOptionText, ignoreCase = true) }
        } else {
            options
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier.background(LocalAppColors.current.secondary)
        ) {
            filteredOptions.forEach { selectionOption ->
                DropdownMenuItem(
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

