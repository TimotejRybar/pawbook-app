package presentation.components.color.colorField

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.PetColor
import presentation.components.color.colorPicker.ColorCircle
import presentation.components.color.colorPicker.ColorPicker
import presentation.theme.colors.LocalAppColors

@Composable
fun ColorField(title: String, onColorSelected: (List<PetColor>) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    val colors = remember { mutableStateListOf<PetColor>() }

    Row(
        modifier = Modifier

    ) {
        colors.forEach { petColor ->
            ColorCircle(petColor, false) { color: PetColor, selected: Boolean ->

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
            title = title,
            onColorsSelected = { newColors ->
                colors.clear()
                colors.addAll(newColors)
                showDialog = false
            },
            selectedColors = colors,
            onDismissRequest = { showDialog = false }
        )
    }
}
