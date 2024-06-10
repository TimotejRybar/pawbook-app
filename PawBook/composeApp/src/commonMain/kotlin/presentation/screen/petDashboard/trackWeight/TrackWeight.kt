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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ArrowDown
import compose.icons.fontawesomeicons.solid.ArrowUp
import data.model.entity.PetEntity
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.weight_unit
import presentation.components.petField.DialogTitle
import presentation.screen.login.InputField
import presentation.screen.login.InputType
import presentation.screen.login.StyledButton
import presentation.screen.petDashboard.PetDashboardViewModel
import presentation.theme.colors.LocalAppColors

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TrackWeightOverview(
    viewModel: PetDashboardViewModel,
    pet: PetEntity
) {
    val currentWeight: Float? = pet.weightHistory.last().weight ?: null
    val previousWeight: Float? = pet.weightHistory.get(pet.weightHistory.size-2).weight ?: null
    var arrowIcon: ImageVector?
    val arrowColor: Color?

    var weightIncreased: Boolean?
    previousWeight.let {
        weightIncreased = currentWeight as Float > it as Float

        arrowIcon =
            if (weightIncreased == true) FontAwesomeIcons.Solid.ArrowUp else FontAwesomeIcons.Solid.ArrowDown
        arrowColor =
            if (weightIncreased == true) Color.Green else Color.Red
    }
    var openDialog by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable{
                openDialog = true
            }
    ) {
        Text(
            text = "$currentWeight" + stringResource(Res.string.weight_unit),
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )
        if(arrowIcon != null) {
            Icon(
                imageVector = arrowIcon as ImageVector,
                contentDescription = null,
                tint = arrowColor as Color,
                modifier = Modifier.size(24.dp)
            )
        }
    }

    if(openDialog) {
        WeightRecordDialog {
            // save weight record
            coroutineScope.launch {
                viewModel.addWeightRecord(pet, it)
            }
        }
    }
}

@Composable
fun TrackWeight() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // dialog with AAY-chart graph with weight progress
        // button which opens dialog to fill in current weight
        TrackWeightDialog("bla bla bla")
    }
}

@Composable
fun TrackWeightDialog(title: String) {

    var inputWeight by remember { mutableStateOf(0f) }
    var openInputDialog by remember { mutableStateOf(false) }

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
        WeightRecordDialog() {
            inputWeight = it
        }
    }
}

@Composable
fun WeightRecordDialog(onSubmit: (Float) -> Unit) {

    var dialogWeight by remember { mutableStateOf("") }

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
                InputField("Váha zvieratka (kg)", dialogWeight, InputType.DECIMAL){
                    dialogWeight = it
                }
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

@Composable
fun EnterWeightDialog(onDismiss: () -> Unit, onSave: (Float) -> Unit) {
    var weightInput by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Enter Current Weight")
        },
        text = {
            Column {
                OutlinedTextField(
                    value = weightInput,
                    onValueChange = { weightInput = it },
                    label = { Text("Weight (kg)") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    weightInput.toFloatOrNull()?.let { weight ->
                        onSave(weight)
                    }
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}