package presentation.components.question

import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import presentation.theme.colors.LocalAppColors

@Composable
fun QuestionSingle(question: String, onToggle: (Boolean) -> Unit) {
    QuestionSingleTitle(question)
    QuestionToggle(onToggle = onToggle)
}

@Composable
fun QuestionSingleTitle(title: String) {
    Text(title, color = LocalAppColors.current.primary)
}

@Composable
fun QuestionToggle(onToggle: (Boolean) -> Unit){
    var checked by remember { mutableStateOf(true) }
    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
            onToggle(checked)
        },
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = LocalAppColors.current.primary,
            uncheckedThumbColor = Color.White,
            uncheckedTrackColor = LocalAppColors.current.darkGray,
        )
    )
}