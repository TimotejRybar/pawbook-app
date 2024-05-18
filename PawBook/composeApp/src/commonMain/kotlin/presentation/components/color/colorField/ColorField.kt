package presentation.components.color.colorField

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.model.PetColor
import domain.model.enums.PetType
import presentation.components.color.colorPicker.ColorCircle
import presentation.components.color.colorPicker.ColorPicker
import presentation.theme.colors.LocalAppColors

@Composable
fun ColorField(title: String, onColorSelected: (PetColor) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    val colors = remember { mutableStateListOf<PetColor>() }

    LaunchedEffect(Unit) {
        colors.add(PetColor(PetType.Dog.name, "#000000", "0", "Čierna", "black"))
        colors.add(PetColor(PetType.Dog.name, "#FFFFFF", "1", "Biela", "white"))
    }

    Row(
        modifier = Modifier

    ) {
        colors.forEach { petColor ->
            ColorCircle(petColor) {
                onColorSelected(petColor)
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
        ColorPicker(
            colors = colors,
            title = title,
            onColorSelected = { newColor ->
                colors.add(newColor)
                showDialog = false
            },
            onDismissRequest = { showDialog = false }
        )
    }
}
