package presentation.screen.petDashboard.trackEpilepsy

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Heartbeat
import data.model.entity.PetEntity
import domain.model.EpilepsyRecord
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.components.petField.DialogTitle
import presentation.components.progressBars.ProgressInput
import presentation.screen.login.StyledButton
import presentation.screen.petDashboard.PetDashboardViewModel
import presentation.screen.petDashboard.trackWeight.TrackWeightDialog
import presentation.theme.colors.LocalAppColors

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TrackEpilepsyOverview(
    viewModel: PetDashboardViewModel,
    pet: PetEntity
) {
    var openDialog by remember { mutableStateOf(false) }

    ListItem(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable {
                openDialog = true
            },
        leadingContent = {
            Row {
                Icon(
                    FontAwesomeIcons.Solid.Heartbeat,
                    modifier = Modifier.size(24.dp),
                    contentDescription = "last seizure icon"
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "Posledný záchvat",
                    fontSize = 18.sp
                )
            }
        },
        headlineContent = {

        },
        trailingContent = {
            Text(
                text = "14 dní",
                fontSize = 18.sp
            )
        })

    if(openDialog) {
        TrackWeightDialog(viewModel, pet, "test"){
            openDialog = false
        }
    }
}

@Composable
fun TrackEpilepsyDialog(viewModel: PetDashboardViewModel, pet: PetEntity, title: String) {

    var openInputDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Dialog(
        onDismissRequest = {

        },
        properties = DialogProperties(),
    ) {
        Card(
            modifier = Modifier.padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = LocalAppColors.current.secondary,
            ),
            shape = RoundedCornerShape(20.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().background(LocalAppColors.current.secondary),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                DialogTitle(title)
                StyledButton("Pridať záznam") {
                    openInputDialog = true
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    if(openInputDialog) {
        EpilepsyRecordDialog {
            // save weight record
            coroutineScope.launch {
                viewModel.addEpilepsyRecord(pet, it)
            }
        }
    }
}

@Composable
fun EpilepsyRecordDialog(onSubmit: (EpilepsyRecord) -> Unit) {

    val epilepsyRecord by remember { mutableStateOf<EpilepsyRecord>(EpilepsyRecord()) }

    Dialog(
        onDismissRequest = {

        },
        properties = DialogProperties(),
    ) {
        Card(
            modifier = Modifier.padding(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = LocalAppColors.current.secondary,
            ),
            shape = RoundedCornerShape(20.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().background(LocalAppColors.current.secondary),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                BasicCheckbox("Zvracanie") {
                    epilepsyRecord.barfing = it
                }
                BasicCheckbox("Slintanie") {
                    epilepsyRecord.drooling = it
                }
                BasicCheckbox("Strata vedomia") {
                    epilepsyRecord.fainting = it
                }
                ProgressInput("Intenzita kŕčov", "Slabé", "Silné",3,2) {
                    epilepsyRecord.spasms = it
                }
                SeizureDurationSlider(){
                    epilepsyRecord.duration = it
                }
                NoteField {
                    epilepsyRecord.note = it
                }

                StyledButton("Potvrdiť") {
                    epilepsyRecord.let { onSubmit(it) }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteField(onTextChanged: (value: String) -> Unit) {
    val value = remember { mutableStateOf("") }

    TextField(
        label = {
            Text("Poznámky", color = Color.Black)
        },
        maxLines = 6,
        minLines = 3,
        value = value.value,
        onValueChange = {
            onTextChanged(it)
        },
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

@Composable
fun SeizureDurationSlider(onValueChange: (sliderValue: Int) -> Unit) {
    var sliderValue by remember { mutableIntStateOf(0) }
    Column {
        Slider(
            value = sliderValue.toFloat(),
            steps = 1,
            valueRange = 0f..10f,
            onValueChange = {
                sliderValue = it.toInt()
                onValueChange(sliderValue)
            }
        )
        Text(text = "Dĺžka záchvatu: $sliderValue minút")
    }
}

@Composable
fun BasicCheckbox(title: String, onChecked: (Boolean) -> Unit){
    var checked by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            title
        )
        Checkbox(
            checked = checked,
            onCheckedChange = {
                checked = it
                onChecked(it)
            }
        )
    }
}
