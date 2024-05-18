package presentation.components.color.colorField

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import presentation.components.color.colorPicker.ColorPicker
import presentation.screen.login.InputType
import presentation.screen.login.validateField
import presentation.theme.colors.LocalAppColors

@Composable
fun ColorField() {

}

@Composable
fun ColorInputField(title: String, value: String, type: InputType, onTextChange: (String) -> Unit) {

    val showDialog = mutableStateOf(false)

    Row(
        modifier = Modifier
            .drawBehind {

                val strokeWidth = size.width
                val y = size.height - strokeWidth / 2

                drawLine(
                    Color.LightGray,
                    Offset(0f, y),
                    Offset(size.width, y),
                    strokeWidth
                )
            }){
        // circle
    }
    IconButton(onClick = { showDialog.value = true}) {
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = Icons.Rounded.Add,
            contentDescription = "add",
            tint = LocalAppColors.current.primary
        )
    }

    if(showDialog.value) {
        ColorPicker(title)
    }
}