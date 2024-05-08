package presentation.components.progressBars

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.theme.colors.LocalAppColors

@Composable
fun ProgressInput(question: String, lowTitle: String, highTitle: String, maxProgress: Int, defaultProgress: Int) {
    Row {
        ProgressInputTitle(question)
    }
    Row {
        LowTitle(lowTitle)
        ProgressBars(maxProgress, defaultProgress) {
        }
        HighTitle(highTitle)
    }
}

@Composable
fun HighTitle(title: String) {
    Text(title, fontSize = 16.sp)
}

@Composable
fun ProgressBars(maxProgress: Int, defaultProgress: Int, onSelected: (Int) -> Unit) {
    var color = LocalAppColors.current.primary
    Canvas(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .width(200.dp)
            .height(80.dp)
    ) {
        val width = size.width / (maxProgress *3)
        val maxHeight = size.height - (size.height/5)
        val spacing = 4.dp

        for (i in 1..maxProgress) {
            val rectHeight = maxHeight / maxProgress * i

            drawRect(
                color = color,
                size = Size(width, rectHeight),
                style = if(i > defaultProgress) Stroke(2.dp.toPx()) else Fill,
                topLeft = Offset(width * i + (width / 2) + spacing.toPx() * i, size.height - rectHeight)
            )
        }
    }
}

@Composable
fun LowTitle(title: String) {
    Text(title, fontSize = 16.sp, color = LocalAppColors.current.primary)
}

@Composable
fun ProgressInputTitle(title: String) {
    Text(title, fontSize = 16.sp, color = LocalAppColors.current.primary)
}
