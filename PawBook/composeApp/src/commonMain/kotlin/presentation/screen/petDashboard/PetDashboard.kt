package presentation.screen.petDashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.ClinicMedical
import compose.icons.fontawesomeicons.solid.Venus
import data.model.entity.ColorEntity
import data.model.entity.DoctorEntity
import data.model.entity.PetEntity
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.imageResource
import org.koin.compose.koinInject
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.sofka
import presentation.components.color.colorField.ColorFieldReadOnly
import presentation.screen.petDashboard.trackEpilepsy.TrackEpilepsyOverview
import presentation.screen.petDashboard.trackWeight.TrackWeightOverview
import presentation.theme.colors.LocalAppColors
import utils.compose.format

@Composable
fun PetDashboard(petId: String, viewModel: PetDashboardViewModel = koinInject()) {

    val pet by viewModel.pet.collectAsState()
    val petColors by viewModel.petColors.collectAsState()
    val petDoctor by viewModel.petDoctor.collectAsState()
    var colorsLoading: StateFlow<Boolean>? = null //  TODO: try collecting this from viewmodel this is wrong impl

    LaunchedEffect(key1 = true) {
        viewModel.loadPet(petId)
        pet?.color?.let {
            colorsLoading = viewModel.hexColorsToColorEntities(it)
        }
    }

    colorsLoading?.collectAsState()?.let { isLoading ->
        if (!isLoading.value) {
            viewModel.loadDoctor(pet?.doctor as String)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (pet != null) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CirclePhoto(pet?.id as String)
                    Column(
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Row {
                            PetName(pet?.name as String)
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = FontAwesomeIcons.Solid.Venus,
                                contentDescription = "female",
                                tint = LocalAppColors.current.primary
                            )
                        }
                        PetBirthday(pet?.birthDay)
                        PetColors(petColors)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        PetBreed(pet?.breed)
                        PetGender(pet?.gender as String)
                    }
                }
                PetDoctor(petDoctor)
                TrackWeightOverview(viewModel, pet as PetEntity)
                TrackEpilepsyOverview(viewModel, pet as PetEntity)
            }
        }
    }
}

@Composable
fun PetDoctor(doctor: DoctorEntity?) {
    ListItem(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable {

            },
        leadingContent = {
            Row {
                if(doctor != null) {
                    Icon(
                        FontAwesomeIcons.Solid.ClinicMedical,
                        modifier = Modifier.size(24.dp),
                        contentDescription = "last seizure icon"
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = doctor.name,
                        fontSize = 18.sp
                    )
                }
            }
        },
        headlineContent = {

        },
        trailingContent = {

        })
}

@Composable
fun PetColors(colors: List<ColorEntity>?) {
    if (colors != null) {
        ColorFieldReadOnly("", colors)
    }
}

@Composable
fun PetGender(gender: String) {

}

@Composable
fun PetBreed(breed: String?) {
}

@Composable
fun PetBirthday(birthDay: LocalDateTime?) {
    Row {
        Text(
            text = birthDay?.format().toString(),
            color = LocalAppColors.current.primary
        )
        /*Icon(
            modifier = Modifier.size(24.dp),
            imageVector = FontAwesomeIcons.Solid.BirthdayCake,
            contentDescription = "weight",
            tint = LocalAppColors.current.primary
        )*/
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CirclePhoto(photoURL: String) {
    Image(
         imageResource(Res.drawable.sofka),
        //resource = asyncPainterResource(Config.STORAGE_URl + photoURL),
        contentDescription = "Pet photo",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
    )
}

@Composable
fun PetName(name: String) {
    Text(name, color = LocalAppColors.current.primary, fontSize = 32.sp)
}
