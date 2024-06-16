package presentation.screen.petDashboard

import AppScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Cut
import compose.icons.fontawesomeicons.solid.Venus
import core.enums.ActivityType
import data.model.entity.CalendarActivityEntity
import data.model.entity.ColorEntity
import data.model.entity.PetEntity
import domain.model.enums.PetDashboardState
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.sofka
import presentation.components.color.colorField.ColorFieldReadOnly
import presentation.screen.petDashboard.itemDoctor.PetDoctor
import presentation.screen.petDashboard.trackEpilepsy.TrackEpilepsyOverview
import presentation.screen.petDashboard.trackWeight.TrackWeightOverview
import presentation.theme.colors.LocalAppColors
import utils.compose.format

@Composable
fun PetDashboard(petId: String, onRedirect: (route: String) -> Unit, viewModel: PetDashboardViewModel = koinInject()) {

    val state by viewModel.state.collectAsState()
    val pet by viewModel.pet.collectAsState()
    val petColors by viewModel.petColors.collectAsState()
    val petDoctor by viewModel.petDoctor.collectAsState()
    val profilePicture by viewModel.profilePicture.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.loadPet(petId)
        viewModel.loadPetProfilePhoto(petId)
    }

    if(state == PetDashboardState.LOADED) {
        viewModel.loadDoctor(pet?.doctor as String)
    }

        if (pet != null) {
            Column(
                modifier = Modifier.padding(16.dp, 32.dp).fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if(profilePicture != null) {
                        CirclePhoto(profilePicture)
                    }
                    else {
                        CirclePhotoPlaceholder() {
                        }
                    }
                    Column(
                        modifier = Modifier.padding(start = 16.dp),
                        verticalArrangement = Arrangement.Center
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
                if(pet != null) {
                    if(petDoctor != null) {
                        PetDoctor(viewModel, petDoctor)
                    }
                    TrackWeightOverview(viewModel, pet)
                    TrackEpilepsyOverview(viewModel, pet)
                    NextTrimmingOverview(viewModel, pet,null, onRedirect = {
                        onRedirect(it)
                    })
                }
            }
        }
}

@Composable
fun CirclePhotoPlaceholder(onClick: () -> Unit) {
    val primaryColor = LocalAppColors.current.primary
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape)
            .clickable { onClick() }
            .drawBehind {
                val paint = Paint().apply {
                    isAntiAlias = true
                    strokeWidth = 4f
                    color = primaryColor
                    style = PaintingStyle.Stroke
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                }
                drawIntoCanvas {
                    it.drawCircle(
                        center = center,
                        radius = size.minDimension / 2 - paint.strokeWidth / 2,
                        paint = paint
                    )
                }
            }
    ) {
        Text(
            text = "Tu bude fotka tvojho miláčika",
            color = primaryColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp),
            style = TextStyle(fontSize = 12.sp)
        )
    }
}

@Composable
fun NextTrimmingOverview(viewModel: PetDashboardViewModel, petEntity: PetEntity?, nextTrimmingActivity: CalendarActivityEntity?, onRedirect: (route: String) -> Unit){
    ListItem(
        modifier = Modifier
            .padding(16.dp, 16.dp)
            .fillMaxWidth()
            .clickable {
                if(nextTrimmingActivity == null)
                onRedirect(AppScreen.CalendarActivity.name + "/" + ActivityType.NailTrimming.value + "/" + petEntity?.id)
            },
        leadingContent = {
            Row {
                Icon(
                    FontAwesomeIcons.Solid.Cut,
                    modifier = Modifier.size(24.dp),
                    contentDescription = "trimming icon"
                )
                Spacer(modifier = Modifier.width(5.dp))
                if(nextTrimmingActivity != null) {
                    Text(
                        text = "Strihanie o 3 dni",
                        fontSize = 18.sp
                    )
                } else {
                    Text(
                        text = "Naplánovať strihanie",
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
fun CirclePhoto(imageData: ByteArray? = null) {
    val painter: Painter = if (imageData != null) {
        rememberAsyncImagePainter(model = imageData)
    } else {
        painterResource(Res.drawable.sofka) // Replace with your placeholder resource
    }

    Image(
        painter = painter,
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
