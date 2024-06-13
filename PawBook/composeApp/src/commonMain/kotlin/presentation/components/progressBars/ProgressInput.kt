package presentation.components.progressBars

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.theme.colors.LocalAppColors

@Composable
fun ProgressInput(question: String, lowTitle: String, highTitle: String, maxProgress: Int, defaultProgress: Int, onValueSelected: (value: Int) -> Unit) {
    Row {
        ProgressInputTitle(question)
    }
    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        LowTitle(lowTitle)
        ProgressBars(maxProgress, defaultProgress) {
            onValueSelected(it)
        }
        HighTitle(highTitle)
    }
}

@Composable
fun HighTitle(title: String) {
    Text(title, fontSize = 16.sp, color = LocalAppColors.current.primary)
}

@Composable
fun ProgressBars(maxProgress: Int, defaultProgress: Int, onSelected: (Int) -> Unit) {
    val color = LocalAppColors.current.primary
    val currentProgress = remember { mutableStateOf(defaultProgress) }
    Box(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .width(100.dp)
            .height(80.dp)
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val totalWidth = size.width
                    val barWidth = totalWidth / maxProgress
                    val clickedBar = (offset.x / barWidth).toInt() + 1
                    if (clickedBar in 1..maxProgress) {
                        currentProgress.value = clickedBar
                        onSelected(clickedBar)
                    }
                }
            }
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val totalWidth = size.width
            val barWidth = totalWidth / maxProgress
            val maxHeight = size.height - (size.height / 5)

            for (i in 1..maxProgress) {
                val rectHeight = maxHeight / maxProgress * i

                drawRect(
                    color = color,
                    size = Size(barWidth * 0.7f, rectHeight), // Adjusted bar width to fit better
                    style = if (i > currentProgress.value) Stroke(2.dp.toPx()) else Fill,
                    topLeft = Offset(barWidth * (i - 1) + (barWidth * 0.15f), size.height - rectHeight)
                )
            }
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
