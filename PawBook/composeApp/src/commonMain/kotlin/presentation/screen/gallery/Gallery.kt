package presentation.screen.gallery

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.common.Config
import data.model.entity.PetEntity
import data.model.entity.PetPhotoEntity
import domain.model.PetItem
import io.kamel.core.utils.URI
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.sofka
import presentation.screen.calendar.PetCalendarOverviewItem
import presentation.screen.petDetail.MyPetsViewModel
import presentation.theme.colors.LocalAppColors

@Composable
fun Gallery(viewModel: GalleryViewModel = koinInject(),  onItemClick: (PetPhotoEntity) -> Unit) {
    val photos = remember { mutableListOf<PetPhotoEntity>() }

    LaunchedEffect(key1 = true){
        viewModel.loadPhotos(){
            photos.clear()
            photos.addAll(it)
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart,
    ) {
        GalleryContent(items = photos, onItemClick)
    }
}

@Composable
fun GalleryContent(items: List<PetPhotoEntity>, onItemClick: (PetPhotoEntity) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .heightIn(max = 150.dp)
    ) {
        items(items) { item ->
            PetPhoto(
                photo = item,
                onItemClick = onItemClick
            )
        }
    }
}

@Composable
fun PetPhoto(photo: PetPhotoEntity, onItemClick: (PetPhotoEntity) -> Unit) {
    Row (horizontalArrangement = Arrangement.Center,
        modifier = Modifier.clickable { onItemClick(photo) }.padding(16.dp, 16.dp),
    ) {
        KamelImage(
            resource = asyncPainterResource(data = URI(Config.STORAGE_URl)),
            contentDescription = "Pet photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .clickable {
                    onItemClick(photo)
                }
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CirclePhoto() {
    Image(
        painter = painterResource(Res.drawable.sofka),
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
