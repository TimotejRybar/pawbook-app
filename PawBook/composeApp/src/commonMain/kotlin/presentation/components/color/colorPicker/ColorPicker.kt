package presentation.components.color.colorPicker

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import core.util.HexColorConverter
import domain.model.PetColor
import domain.model.enums.PetType
import presentation.screen.login.StyledButton

@Composable
fun ColorPicker(
    title: String,
    selectedColors: SnapshotStateList<PetColor>,
    onColorsSelected: (List<PetColor>) -> Unit,
    onDismissRequest: () -> Unit
) {
    val cellCount = 4

    var availableColors = remember { mutableStateListOf<PetColor>() }
    var selectedColors = remember { mutableStateOf(selectedColors) }

    LaunchedEffect(Unit) {
        availableColors.addAll(
            listOf(
                PetColor(PetType.Dog.name, "#000000", "0", "Čierna", "black"),
                PetColor(PetType.Dog.name, "#FFFFFF", "1", "Biela", "white"),
                PetColor(PetType.Dog.name, "#562b00", "2", "Hnedá", "brown"),
                PetColor(PetType.Dog.name, "#4C4C4C", "3", "Šedá", "gray")
            )
        )
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(),
    ) {
        Card(
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                DialogTitle(title)
                LazyVerticalGrid(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    columns = GridCells.Fixed(cellCount)
                ) {
                    items(availableColors) { color ->
                        ColorCircle(color, selectedColors.value.contains(color)) { petColor: PetColor, selected: Boolean ->
                            if(selected) {
                                selectedColors.value.add(petColor)
                            } else {
                                selectedColors.value.remove(petColor)
                            }
                        }
                    }
                }
                StyledButton("Potvrdiť") {
                    onColorsSelected(selectedColors.value)
                }
            }
        }
    }
}


@Composable
fun DialogTitle(title: String) {
    Text(title, fontSize = 18.sp, color = Color.Black, textAlign = TextAlign.Center)
}

@Composable
fun ColorCircle(petColor: PetColor, isDefaultSelected: Boolean, onColorSelected: (PetColor, Boolean) -> Unit) {

    val isSelected = remember { mutableStateOf(isDefaultSelected) }

    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(50.dp)
            .clip(CircleShape)
            .clickable {
                isSelected.value = !isSelected.value
                onColorSelected(petColor, isSelected.value)
            }
            .background(HexColorConverter.convert(petColor.color), shape = CircleShape),
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
