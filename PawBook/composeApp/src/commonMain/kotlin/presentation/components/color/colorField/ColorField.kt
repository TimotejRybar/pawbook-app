package presentation.components.color.colorField

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
import data.model.entity.ColorEntity
import presentation.components.color.colorPicker.ColorCircle
import presentation.components.color.colorPicker.ColorPicker
import presentation.theme.colors.LocalAppColors

@Composable
fun ColorFieldReadOnly(title: String, defaultColors: List<ColorEntity>) {
    val colors = remember { mutableStateListOf<ColorEntity>() }

    LaunchedEffect(true){
        colors.clear()
        colors.addAll(defaultColors)
    }

    Title(title)
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        colors.forEach { petColor ->
            ColorCircle(petColor, 24.dp, true, false) { color: ColorEntity, selected: Boolean ->
            }
        }
    }
}

@Composable
fun ColorField(title: String, defaultColors: ArrayList<ColorEntity>, availableColors: List<ColorEntity>, onColorSelected: (List<ColorEntity>) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    val colors = remember { mutableStateListOf<ColorEntity>() }
    val newColors = remember { mutableStateOf(defaultColors) }

    newColors.value.addAll(defaultColors)

    LaunchedEffect(true){
        colors.clear()
        colors.addAll(defaultColors)
    }

    presentation.components.petField.Title(title)
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        newColors.value.forEach { petColor ->
            ColorCircle(petColor, 24.dp, true, false) { color: ColorEntity, selected: Boolean ->

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
            onColorsSelected = { updatedColors ->
                newColors.value.clear()
                newColors.value.addAll(updatedColors)
                showDialog = false
                onColorSelected(newColors.value)
            },
            selectedColors = colors,
            availableColors = availableColors,
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
