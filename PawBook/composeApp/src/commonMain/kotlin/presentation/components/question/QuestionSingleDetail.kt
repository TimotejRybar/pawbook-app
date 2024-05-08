package presentation.components.question

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import presentation.theme.colors.LocalAppColors

@Composable
fun QuestionSingleDetail(question: String, hint: String, onTextChanged: (String) -> Unit) {
    Row {
        QuestionSingleTitle(question)

    }
    Row {
        QuestionSingleField(
            hint = hint,
            onTextChanged = onTextChanged
        )
        QuestionToggle() {

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionSingleField(hint: String, onTextChanged: (String) -> Unit) {
    val value = remember { mutableStateOf("") }
    var isValid by remember { mutableStateOf(false) }

    TextField(
        label = {
            Text(hint, color = Color.Black)
        },
        isError = !isValid,
        value = value.value,
        onValueChange = {
            onTextChanged(it)
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedIndicatorColor =  LocalAppColors.current.primary,
            unfocusedIndicatorColor = LocalAppColors.current.primary,
            errorContainerColor = Color.Transparent,
            errorIndicatorColor = LocalAppColors.current.error,
            errorTextColor = LocalAppColors.current.error)
    )
}