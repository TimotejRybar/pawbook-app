package presentation.components.question

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import presentation.theme.colors.LocalAppColors
import presentation.components.models.AnswerItem

@Composable
fun QuestionMultiple(question: String, answers: List<AnswerItem>, onAnswerClicked: (AnswerItem) -> Unit) {
    QuestionText(question)
    Answers(answers, onAnswerClicked)
}

@Composable
fun QuestionText(questionText: String) {
    Text(questionText, fontSize = 16.sp, color = LocalAppColors.current.primary)
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Answers( answers: List<AnswerItem>, onAnswerClicked: (AnswerItem) -> Unit) {
    FlowRow() {
        answers.forEach {
            Answer(it, onClick = onAnswerClicked)
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Answer(answer: AnswerItem, onClick: (AnswerItem)-> Unit) {

    val selected = remember { mutableStateOf(false) }
    val containerColor: Color = if(selected.value) {
        LocalAppColors.current.darkGray
    } else {
        LocalAppColors.current.primary
    }
    Box(modifier = Modifier.padding(4.dp)) {
        Button(
            onClick =
            {
                onClick(answer)
                if(selected.value) selected.value = false
                else selected.value = true
            },
            modifier = Modifier.shadow(0.dp).width(64.dp).padding(0.dp, 0.dp).align(Alignment.Center),
            contentPadding = PaddingValues(12.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = containerColor
            ),
            shape = RoundedCornerShape(50),
        ) {
            Text(text = stringResource(answer.stringResource), fontSize = 10.sp, lineHeight = 11.sp, textAlign = TextAlign.Center)
        }
    }
}