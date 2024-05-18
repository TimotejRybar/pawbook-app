package presentation.components.color.colorPicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import core.util.HexColorConverter
import domain.model.PetColor

@Composable
fun ColorPicker(
    title: String,
    colors: List<PetColor>,
    onColorSelected: (PetColor) -> Unit,
    onDismissRequest: () -> Unit
) {
    val cellCount = 6

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(),
    ) {
        Box {
            DialogTitle(title)
            LazyVerticalGrid(
                columns = GridCells.Fixed(cellCount),
                contentPadding = PaddingValues(bottom = 30.dp, top = 10.dp, start = 5.dp),
            ) {
                items(colors.size) { i ->
                    ColorCircle(colors[i], onColorSelected)
                }
            }
        }
    }
}


@Composable
fun DialogTitle(title: String) {
    Text(title)
}

@Composable
fun ColorCircle(petColor: PetColor, onColorSelected: (PetColor) -> Unit) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(HexColorConverter.convert(petColor.color))
            .clip(CircleShape)
    )
}
