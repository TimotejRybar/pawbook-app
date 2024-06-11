package presentation.screen.petDashboard.trackEpilepsy

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import data.model.entity.PetEntity
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.components.petField.DialogTitle
import presentation.screen.login.InputField
import presentation.screen.login.InputType
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
            text = "Posledný záchvat",
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "pred 14 dňami",
            fontSize = 18.sp,
            modifier = Modifier.weight(1f)
        )
    }

    if(openDialog) {
        TrackWeightDialog(viewModel, pet, "test")
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
fun EpilepsyRecordDialog(onSubmit: (Float) -> Unit) {

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
                // checkboxy: zvracanie, kako, kŕče (intenzita), slintanie (intenzita), dĺžka záchvatu (spinner)
                // input poznámka
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
