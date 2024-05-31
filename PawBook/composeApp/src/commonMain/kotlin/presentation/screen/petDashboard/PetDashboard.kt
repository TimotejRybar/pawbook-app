package presentation.screen.petDashboard

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.common.Config
import data.model.entity.ColorEntity
import data.model.entity.DoctorEntity
import data.model.entity.PetEntity
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.weight_unit
import presentation.components.color.colorField.ColorFieldReadOnly
import presentation.theme.colors.LocalAppColors
import utils.compose.format

@Composable
fun PetDashboard(petId: String, viewModel: PetDashboardViewModel = koinInject()) {

    val pet by viewModel.pet.collectAsState()
    val petColors by viewModel.petColors.collectAsState()
    val petDoctor by viewModel.petDoctor.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.loadPet(petId)
        pet?.color?.let { viewModel.hexColorsToColorEntities(it) }
        pet?.doctor?.let { viewModel.loadDoctor(it) }
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
                    horizontalArrangement = Arrangement.Start
                ) {
                    CirclePhoto(pet?.id as String)
                    Column(
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Row {
                            PetName(pet?.name as String)
                            Icon(
                                modifier = Modifier.size(24.dp),
                                imageVector = Icons.Rounded.Add,
                                contentDescription = "arrow",
                                tint = Color.Black
                            )
                        }
                        PetBirthday(pet?.birthDay)
                        PetWeight(pet?.weight)
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
                    PetDoctor(petDoctor)
                }
            }
        }
    }
}

@Composable
fun PetDoctor(doctor: DoctorEntity?) {
    Text(text = doctor?.name.toString())
    IconButton(onClick = {  }) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.Rounded.KeyboardArrowDown,
            contentDescription = "arrow",
            tint = Color.Black
        )
    }
}

@Composable
fun PetColors(colors: List<ColorEntity>?) {
    if (colors != null) {
        ColorFieldReadOnly("Farba psa", colors)
    }
}

@Composable
fun PetGender(gender: String) {
    Icon(
        modifier = Modifier.size(24.dp),
        imageVector = Icons.Rounded.Settings,
        contentDescription = "arrow",
        tint = Color.Black
    )
}

@Composable
fun PetBreed(breed: String?) {
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PetWeight(weight: Float?) {
    Text(weight.toString() + " " + stringResource(Res.string.weight_unit))
}

@Composable
fun PetBirthday(birthDay: LocalDateTime?) {
    Text(
        text = birthDay?.format().toString()
    )
}

@Composable
fun CirclePhoto(photoURL: String) {
    KamelImage(
        resource = asyncPainterResource(Config.STORAGE_URl + photoURL),
        contentDescription = "Pet photo",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(150.dp)
            .clip(CircleShape)
    )
}

@Composable
fun PetName(name: String) {
    Text(name, color = LocalAppColors.current.primary, fontSize = 32.sp, textDecoration = TextDecoration.Underline)
}
