package presentation.components.color.colorPicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
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
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun ColorPicker(title: String) {
    val cellCount = 6

    // get colors
    val colors = mutableListOf<PetColor>()

    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(),
    ) {
        Box {
            DialogTitle(title)
            LazyVerticalGrid(
                columns = GridCells.Fixed(cellCount),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 30.dp, top = 10.dp, start = 5.dp),
            ) {
                for (i in 1..colors.size)
                    item(span = { GridItemSpan(cellCount) }) {
                        ColorCircle(colors[i])
                    }
                item() {

                }
                item() {

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
fun ColorCircle(petColor: PetColor) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(HexColorConverter.convert((petColor.color)))
            .clip(CircleShape)
    )
}
