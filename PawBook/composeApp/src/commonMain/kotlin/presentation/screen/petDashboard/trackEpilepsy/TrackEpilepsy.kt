package presentation.screen.petDashboard.trackEpilepsy

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.aay.compose.baseComponents.model.GridOrientation
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Heartbeat
import data.model.entity.PetEntity
import domain.model.EpilepsyRecord
import kotlinx.datetime.LocalDateTime
import presentation.components.progressBars.ProgressInput
import presentation.screen.calendar.formatDate
import presentation.screen.login.StyledButton
import presentation.screen.petDashboard.PetDashboardViewModel
import presentation.theme.colors.LocalAppColors

@Composable
fun TrackEpilepsyOverview(
    viewModel: PetDashboardViewModel,
    pet: PetEntity?
) {
    var openDialog by remember { mutableStateOf(false) }

    ListItem(
        modifier = Modifier
            .padding(16.dp, 0.dp)
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
                fontSize = 14.sp
            )
        })

    if(openDialog) {
        if (pet != null) {
            TrackEpilepsyDialog(viewModel, pet){
                openDialog = false
            }
        }
    }
}

@Composable
fun TrackEpilepsyDialog(viewModel: PetDashboardViewModel, pet: PetEntity, onDismiss: () -> Unit) {

    var openInputDialog by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = {
            onDismiss()
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
                DialogTitle("História záchvatov")
                EpilepsyHistoryChart(pet.epilepsyHistory) {
                    openInputDialog = true
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    if(openInputDialog) {
        EpilepsyRecordDialog(onDismiss = { openInputDialog = false }) {
            // save epilepsy record
            viewModel.addEpilepsyRecord(pet, it)
        }
    }
}

@Composable
fun EpilepsyRecordDialog(onDismiss: () -> Unit, onSubmit: (EpilepsyRecord) -> Unit) {

    val epilepsyRecord by remember { mutableStateOf<EpilepsyRecord?>(null) }
    var drooling by remember { mutableStateOf(false) }
    var barfing by remember { mutableStateOf(false) }
    var fainting by remember { mutableStateOf(false) }
    var duration by remember { mutableStateOf(2) }
    var spasms by remember { mutableStateOf(2) }
    var note by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = {
            onDismiss()
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
                modifier = Modifier.fillMaxWidth().background(LocalAppColors.current.secondary)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Nový záznam", fontSize = 24.sp, color = LocalAppColors.current.primary)
                Spacer(modifier = Modifier.height(16.dp))
                ProgressInput("Intenzita kŕčov", "Slabé", "Silné",3,2) {
                    spasms = it
                }
                Spacer(modifier = Modifier.height(16.dp))
                BasicCheckbox("Zvracanie") {
                    barfing = it
                }
                BasicCheckbox("Slintanie") {
                    drooling = it
                }
                BasicCheckbox("Strata vedomia") {
                    fainting = it
                }
                Spacer(modifier = Modifier.height(16.dp))
                SeizureDurationSlider(){
                    duration = it
                }
                NoteField {
                    note = it
                }
                Spacer(modifier = Modifier.height(8.dp))
                StyledButton("Potvrdiť") {
                    onSubmit(EpilepsyRecord(null, barfing, drooling, fainting, spasms, duration, note, LocalDateTime(1,1,1,1,1,1)))
                }
                Spacer(modifier = Modifier.height(8.dp))
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
            Text("Poznámky", color = LocalAppColors.current.primary)
        },
        modifier = Modifier.padding(16.dp, 0.dp),
        maxLines = 3,
        minLines = 1,
        value = value.value,
        onValueChange = {
            value.value = it
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
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp, 0.dp)
    ) {
        Text(text = "Dĺžka záchvatu: $sliderValue minút", textAlign = TextAlign.Center, color = LocalAppColors.current.primary)
        Slider(
            colors = SliderColors(
                activeTickColor = LocalAppColors.current.primary,
                inactiveTickColor = LocalAppColors.current.primary,
                inactiveTrackColor = LocalAppColors.current.primary,
                activeTrackColor = LocalAppColors.current.primary,
                thumbColor = LocalAppColors.current.primary,
                disabledThumbColor = LocalAppColors.current.darkGray,
                disabledActiveTrackColor = LocalAppColors.current.darkGray,
                disabledActiveTickColor = LocalAppColors.current.darkGray,
                disabledInactiveTickColor = LocalAppColors.current.darkGray,
                disabledInactiveTrackColor = LocalAppColors.current.darkGray
            ),
            value = sliderValue.toFloat(),
            valueRange = 0f..10f,
            onValueChange = {
                sliderValue = it.toInt()
                onValueChange(sliderValue)
            }
        )
    }
}

@Composable
fun BasicCheckbox(title: String, onChecked: (Boolean) -> Unit){
    var checked by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            title,
            color = LocalAppColors.current.primary
        )
        Checkbox(
            colors = CheckboxColors(
                checkedCheckmarkColor = LocalAppColors.current.secondary,
                checkedBorderColor = LocalAppColors.current.primary,
                checkedBoxColor =LocalAppColors.current.primary,
                disabledBorderColor = LocalAppColors.current.primary,
                uncheckedBoxColor = Color.Transparent,
                uncheckedBorderColor = LocalAppColors.current.primary,
                disabledCheckedBoxColor = LocalAppColors.current.primary,
                uncheckedCheckmarkColor = LocalAppColors.current.primary,
                disabledUncheckedBoxColor = LocalAppColors.current.primary,
                disabledIndeterminateBorderColor = LocalAppColors.current.primary,
                disabledUncheckedBorderColor = LocalAppColors.current.primary,
                disabledIndeterminateBoxColor = LocalAppColors.current.primary
            ),
            checked = checked,
            onCheckedChange = {
                checked = it
                onChecked(it)
            }
        )
    }
}

@Composable
fun DialogTitle(title: String) {
    Text(title, fontSize = 18.sp, color = LocalAppColors.current.primary, textAlign = TextAlign.Center)
}

@Composable
fun EpilepsyHistoryChart(epilepsyHistory: ArrayList<EpilepsyRecord>, onAddClick: ()-> Unit) {

    val weightData = epilepsyHistory.map { it.calculatePoints() }

    val lineParameters = arrayListOf(
        LineParameters(
        label = "Intenzita záchvatu",
        data = weightData,
        lineColor = LocalAppColors.current.primary,
        lineType = LineType.CURVED_LINE,
        lineShadow = true,
    ))

    Column(modifier = Modifier.padding(16.dp)) {
        if (epilepsyHistory.isNotEmpty()) {
            Box(modifier = Modifier.weight(1f).fillMaxSize()) {
                LineChart(
                    modifier = Modifier.fillMaxSize(),
                    linesParameters = lineParameters,
                    isGrid = true,
                    gridColor = LocalAppColors.current.darkGray,
                    xAxisData = epilepsyHistory.map { formatDate(it.created) },
                    animateChart = true,
                    showGridWithSpacer = true,
                    yAxisStyle = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray,
                    ),
                    xAxisStyle = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray,
                    ),
                    yAxisRange = 14,
                    oneLineChart = false,
                    gridOrientation = GridOrientation.VERTICAL
                )
            }
        } else {
            Text(
                "Žiadne dáta",
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        StyledButton(
            "Pridať záznam",
            onClick = {
                onAddClick()
            }
        )
    }
}
