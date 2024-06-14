package presentation.screen.petDashboard.itemDoctor

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Regular
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.regular.Map
import compose.icons.fontawesomeicons.solid.ClinicMedical
import compose.icons.fontawesomeicons.solid.Phone
import core.util.HexColorConverter
import data.model.entity.DoctorEntity
import presentation.screen.login.StyledButton
import presentation.screen.petDashboard.PetDashboardViewModel
import presentation.theme.colors.LocalAppColors

@Composable
fun PetDoctor(viewModel: PetDashboardViewModel, doctor: DoctorEntity?) {

    var openDialog by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp, 0.dp)
            .fillMaxWidth()
            .background(HexColorConverter.convert("#fffbfe"))
            .clickable {
                openDialog = true
            }
    ) {
        if (doctor != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Icon(
                    FontAwesomeIcons.Solid.ClinicMedical,
                    modifier = Modifier.size(24.dp),
                    contentDescription = "clinic icon"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = doctor.name,
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    if(openDialog) {
        if (doctor != null) {
            PetDoctorDetailDialog(viewModel, doctor){
                openDialog = false
            }
        }
    }
}

@Composable
fun PetDoctorDetailDialog(viewModel: PetDashboardViewModel, doctor: DoctorEntity?, onDismiss: () -> Unit) {

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
                DialogTitle("Veterinár")
                Spacer(modifier = Modifier.height(24.dp))
                DoctorName(doctor)
                Spacer(modifier = Modifier.height(16.dp))
                DoctorAddress(doctor)
                Row {
                    DoctorCallButton(viewModel, doctor)
                    DoctorNavigationButton(viewModel, doctor)
                }
                Spacer(modifier = Modifier.height(16.dp))
                StyledButton("Zatvoriť") {
                    onDismiss()
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun DoctorNavigationButton(viewModel: PetDashboardViewModel, doctor: DoctorEntity?) {
    Button(modifier = Modifier.padding(16.dp)
        .size(64.dp)
        .clip(CircleShape),
        shape = CircleShape,
        colors = ButtonColors(
            containerColor = LocalAppColors.current.primary,
            contentColor = Color.White,
            disabledContentColor = LocalAppColors.current.darkGray,
            disabledContainerColor = LocalAppColors.current.darkGray
        ),
        onClick = {
            viewModel.navigateToDoctor(doctor)
    }) {
        Icon(FontAwesomeIcons.Regular.Map,
            contentDescription = "navigate to doctor",
            tint = LocalAppColors.current.secondary,
            modifier = Modifier.size(40.dp))
    }
}

@Composable
fun DoctorCallButton(viewModel: PetDashboardViewModel, doctor: DoctorEntity?) {
    Button(modifier = Modifier.padding(16.dp)
        .size(64.dp)
        .clip(CircleShape),
        shape = CircleShape,
        colors = ButtonColors(
            containerColor = LocalAppColors.current.primary,
            contentColor = Color.White,
            disabledContentColor = LocalAppColors.current.darkGray,
            disabledContainerColor = LocalAppColors.current.darkGray
        ),
        onClick = {
            viewModel.callDoctor(doctor)
    }) {
        Icon(
            FontAwesomeIcons.Solid.Phone,
            contentDescription = "call to doctor",
            modifier = Modifier.size(40.dp),
            tint = LocalAppColors.current.secondary
        )
    }
}

@Composable
fun DoctorName(doctor: DoctorEntity?) {
    Text(doctor?.name.toString(), fontSize = 18.sp, color = LocalAppColors.current.primary, textAlign = TextAlign.Center)
}

@Composable
fun DoctorAddress(doctor: DoctorEntity?) {
    Text(doctor?.address?.street + "\n" + doctor?.address?.city, fontSize = 18.sp, color = LocalAppColors.current.primary, textAlign = TextAlign.Center)
}

@Composable
fun DialogTitle(title: String) {
    Text(title, fontSize = 24.sp, color = LocalAppColors.current.primary, textAlign = TextAlign.Center)
}
