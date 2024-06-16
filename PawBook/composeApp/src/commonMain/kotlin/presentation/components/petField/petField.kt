package presentation.components.petField

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.model.entity.PetEntity
import presentation.theme.colors.LocalAppColors

@Composable
fun PetField(title: String, selectedPets: List<PetEntity>, availablePets: List<PetEntity>, petProfilePhotos: Map<String, ByteArray>, onPetSelected: (List<PetEntity>) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    val colors = remember { mutableStateListOf<PetEntity>() }
    val newPets = remember {  mutableStateListOf<PetEntity>() }

    LaunchedEffect(key1 = selectedPets.size) {
        newPets.clear()
        newPets.addAll(selectedPets)
    }

    Title(title)
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        newPets.forEach { pet ->
            val petProfilePhoto = petProfilePhotos[pet.id]
            PetCircle(pet, petProfilePhoto, 48.dp, true, false) { pet: PetEntity, selected: Boolean ->

            }
        }

        IconButton(onClick = { showDialog = true }) {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = Icons.Rounded.Add,
                contentDescription = "add",
                tint = LocalAppColors.current.primary
            )
        }
    }

    if (showDialog) {
        PetPicker(
            title = title,
            onPetsSelected = { updatedPets ->
                newPets.clear()
                newPets.addAll(updatedPets)
                showDialog = false
                onPetSelected(newPets)
            },
            selectedPets = colors,
            availablePets = availablePets,
            petProfiles = petProfilePhotos,
            onDismissRequest = { showDialog = false }
        )
    }
}

@Composable
fun Title(title: String) {
    Spacer(modifier = Modifier.height(12.dp))
    Column {
        Text(title, fontSize = 16.sp, color = Color.Black, textAlign = TextAlign.Center)
    }
}
