package presentation.components.petField

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import core.common.Config
import data.model.entity.PetEntity
import io.kamel.core.utils.URI
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import presentation.screen.login.StyledButton
import presentation.theme.colors.LocalAppColors

@Composable
fun PetPicker(
    title: String,
    availablePets: List<PetEntity>,
    selectedPets: SnapshotStateList<PetEntity>,
    onPetsSelected: (List<PetEntity>) -> Unit,
    onDismissRequest: () -> Unit
) {
    val cellCount = 4
    val newPets = remember { mutableStateOf(selectedPets) }

    Dialog(
        onDismissRequest = onDismissRequest,
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
                LazyVerticalGrid(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    columns = GridCells.Fixed(cellCount)
                ) {
                    items(availablePets) { pet ->
                        PetCircle(pet, 50.dp, false, newPets.value.contains(pet)) { petEntity: PetEntity, selected: Boolean ->
                            if(selected) {
                                newPets.value.add(pet)
                            } else {
                                newPets.value.remove(pet)
                            }
                        }
                    }
                }
                StyledButton("Potvrdiť") {
                    onPetsSelected(newPets.value)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}


@Composable
fun DialogTitle(title: String) {
    Text(title, fontSize = 18.sp, color = LocalAppColors.current.primary, textAlign = TextAlign.Center)
}

@Composable
fun PetCircle(pet: PetEntity, circleSize: Dp = 50.dp, displayOnly: Boolean, isDefaultSelected: Boolean, onPetSelected: (PetEntity, Boolean) -> Unit) {

    val isSelected = remember { mutableStateOf(isDefaultSelected) }

    KamelImage(
        resource = asyncPainterResource(data = URI(Config.STORAGE_URl)),
        contentDescription = "Pet photo",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(circleSize)
            .clip(CircleShape)
            .clickable {
                isSelected.value = !isSelected.value
                onPetSelected(pet,isSelected.value)
            }
    )
    Box(
        modifier = Modifier.padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {

        if(isSelected.value) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Selected color",
                tint = Color.White
            )
        }
    }
}
