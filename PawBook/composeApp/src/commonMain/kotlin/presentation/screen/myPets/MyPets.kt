package presentation.screen.myPets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.model.entity.PetEntity
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.sofka
import presentation.screen.petDetail.MyPetsViewModel
import presentation.theme.colors.LocalAppColors

@Composable
fun MyPets(viewModel: MyPetsViewModel = koinInject(),  onItemClick: (PetEntity) -> Unit) {
    LaunchedEffect(key1 = true){
        viewModel.fetch()
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.CenterStart,
    ) {
        Pets(items = viewModel.pets){
            onItemClick(it)
        }
    }
}

@Composable
fun Pets(items: SnapshotStateList<PetEntity>, onItemClick: (PetEntity) -> Unit) {
    items.forEach {
        PetCard(
            petItem = it,
            onItemClick = onItemClick
        )
    }
}

@Composable
fun PetCard(petItem: PetEntity, onItemClick: (PetEntity) -> Unit) {
    Row (horizontalArrangement = Arrangement.Center,
        modifier = Modifier.clickable { onItemClick(petItem) }.padding(16.dp, 16.dp),
        ) {
        Column {
            CirclePhoto()
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
