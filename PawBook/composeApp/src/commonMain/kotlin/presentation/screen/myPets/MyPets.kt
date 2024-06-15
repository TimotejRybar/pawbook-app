package presentation.screen.myPets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import data.model.entity.PetEntity
import org.koin.compose.koinInject
import presentation.screen.petCreate.MyPetsViewModel
import presentation.theme.colors.LocalAppColors

@Composable
fun MyPets(viewModel: MyPetsViewModel = koinInject(),  onItemClick: (PetEntity) -> Unit) {

    LaunchedEffect(false){
        viewModel.fetch()
    }
    Row( verticalAlignment = Alignment.CenterVertically) {
        Pets(viewModel, items = viewModel.pets) {
            onItemClick(it)
        }
    }
}

@Composable
fun Pets(viewModel: MyPetsViewModel, items: SnapshotStateList<PetEntity>, onItemClick: (PetEntity) -> Unit) {

    val petProfilePhotos = viewModel.petProfilePhotos.collectAsState()

    items.forEach {
        PetCard(
            petItem = it,
            onItemClick = onItemClick,
            profilePhoto = petProfilePhotos.value[it.id]
        )
    }
}

@Composable
fun PetCard(petItem: PetEntity, onItemClick: (PetEntity) -> Unit, profilePhoto: ByteArray?) {
    Row (horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth().clickable { onItemClick(petItem) }.padding(32.dp, 16.dp),
        ) {
        Column {
            CirclePhoto(profilePhoto)
        }
        Column(
            modifier = Modifier.padding(12.dp, 0.dp, 0.dp, 0.dp)
        ) {
            PetName(petItem.name)
            ShortDescription(petItem.shortDescription ?: "")
        }
    }
}

@Composable
fun ShortDescription(shortDescription: String) {
    Text(shortDescription, color = Color.Black, fontSize = 10.sp, lineHeight = 15.sp)
}

@Composable
fun CirclePhoto(profilePhoto: ByteArray?) {
    if(profilePhoto != null) {
        val painter = rememberAsyncImagePainter(model = profilePhoto)
        Image(
            painter = painter,
            contentDescription = "Pet photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )
    }
}

@Composable
fun PetName(name: String) {
    Text(name, color = LocalAppColors.current.primary, fontSize = 14.sp, textDecoration = TextDecoration.Underline)
}
