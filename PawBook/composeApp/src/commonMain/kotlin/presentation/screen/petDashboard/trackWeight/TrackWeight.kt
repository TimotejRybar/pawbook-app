package presentation.screen.petDashboard.trackWeight

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
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import compose.icons.fontawesomeicons.solid.BalanceScale
import data.model.entity.PetEntity
import domain.model.WeightRecord
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.weight_unit
import presentation.components.petField.DialogTitle
import presentation.screen.calendar.formatDate
import presentation.screen.login.InputField
import presentation.screen.login.InputType
import presentation.screen.login.StyledButton
import presentation.screen.petDashboard.PetDashboardViewModel
import presentation.theme.colors.LocalAppColors

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TrackWeightOverview(
    viewModel: PetDashboardViewModel,
    pet: PetEntity?
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
                    FontAwesomeIcons.Solid.BalanceScale,
                    modifier = Modifier.size(24.dp),
                    contentDescription = "last weight icon"
                )
                Spacer(modifier = Modifier.width(5.dp))
                if(pet?.weightHistory?.isNotEmpty() == true) {
                    Text(
                        text = "Posledné váženie",
                        fontSize = 18.sp,
                    )
                } else {
                    Text(
                        text = "Kliknite pre sledovanie váhy",
                        fontSize = 18.sp
                    )
                }
            }
        },
        trailingContent = {
            if(pet?.weightHistory?.isNotEmpty() == true) {
                Text(
                    text = pet.weightHistory.last().weight.toString() + " " + stringResource(Res.string.weight_unit),
                    fontSize = 14.sp,
                )
                } else {
                    Text(
                        text = "",
                        fontSize = 18.sp
                    )
            }
        },
        headlineContent = {

        },
        supportingContent = {

        },
    )

    if (openDialog) {
        TrackWeightDialog(viewModel, pet, "História váženia"){
            openDialog = false
        }
    }
}

@Composable
fun TrackWeightDialog(viewModel: PetDashboardViewModel, pet: PetEntity?, title: String, onDismiss: () -> Unit) {

    var openInputDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

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
                DialogTitle(title)
                Spacer(modifier = Modifier.height(16.dp))
                WeightHistoryChart(pet?.weightHistory) {
                    openInputDialog = true
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

    if(openInputDialog) {
        WeightRecordDialog(onDismiss = { openInputDialog = false }) {
            // save weight record
            coroutineScope.launch {
                openInputDialog = false
                viewModel.addWeightRecord(pet, it)
            }
        }
    }
}

@Composable
fun WeightHistoryChart(weightHistory: ArrayList<WeightRecord>?, onAddClick: ()-> Unit) {

    val weightData = weightHistory?.map { it.weight.toDouble() }

    val lineParameters = arrayListOf(LineParameters(
        label = "Váha (kg)",
        data = weightData as List<Double>,
        lineColor = LocalAppColors.current.primary,
        lineType = LineType.CURVED_LINE,
        lineShadow = true,
    ))

    Column(modifier = Modifier.fillMaxSize().padding(0.dp, 32.dp)) {
        if (weightHistory.isNotEmpty()) {
            Box(modifier = Modifier.weight(1f).fillMaxSize()) {
                LineChart(
                    modifier = Modifier.fillMaxSize(),
                    linesParameters = lineParameters,
                    isGrid = true,
                    gridColor = LocalAppColors.current.darkGray,
                    xAxisData = weightHistory.map { formatDate(it.created) },
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
            },
            //modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}


@Composable
fun WeightRecordDialog(onDismiss: () -> Unit, onSubmit: (Float) -> Unit) {

    var dialogWeight by remember { mutableStateOf("") }

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
                Text("Nové váženie", fontSize = 20.sp, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(16.dp))
                InputField("Váha zvieratka (kg)", dialogWeight, InputType.DECIMAL, modifier = Modifier.padding(16.dp, 0.dp)){
                    dialogWeight = it
                }
                Spacer(modifier = Modifier.height(16.dp))
                StyledButton("Potvrdiť") {
                    val floatVlaue = dialogWeight.toFloatOrNull()
                    if(floatVlaue != null) {
                        onSubmit(floatVlaue)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}