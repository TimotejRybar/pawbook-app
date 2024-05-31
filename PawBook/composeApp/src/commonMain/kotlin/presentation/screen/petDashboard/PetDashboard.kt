package presentation.screen.petDashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
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

    LaunchedEffect(key1 = true){
        viewModel.loadPet(petId)
        pet?.color?.let { viewModel.hexColorsToColorEntities(it) }
        pet?.doctor?.let { viewModel.loadDoctor(it) }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Row {
            Column {
                if (pet != null) {
                        CirclePhoto(pet?.id as String)
                    PetName(pet?.name as String)
                    PetBirthday(pet?.birthDay)
                    PetWeight(pet?.weight)
                    PetBreed(pet?.breed)
                    PetGender(pet?.gender as String)
                    PetColors(petColors)
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
            .size(60.dp)
            .clip(CircleShape)
    )
}

@Composable
fun PetName(name: String) {
    Text(name, color = LocalAppColors.current.primary, fontSize = 14.sp, textDecoration = TextDecoration.Underline)
}
