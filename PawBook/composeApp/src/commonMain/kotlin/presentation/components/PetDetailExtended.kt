package presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import domain.model.PetItem
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.InternalResourceApi
import presentation.theme.colors.LocalAppColors
import presentation.components.calendar.PetCalendar
import presentation.navbar.Navbar
import presentation.screen.petDetail.CirclePhoto

@Composable
fun PetDetailExtended(petItem: PetItem, onDismissClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        PetInfoExtended(petItem)
    }
}

@Composable
fun PetInfoExtended(petItem: PetItem) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Navbar()
            PetPhoto()
            PetName(petItem.name)
            Spacer(modifier = Modifier.height(20.dp))
            PetQuestions()
        }
    }
}

@Composable
fun PetName(name: String) {
    Text(name, color = LocalAppColors.current.primary, fontSize = 14.sp, textDecoration = TextDecoration.Underline)
}

@OptIn(InternalResourceApi::class, ExperimentalResourceApi::class)
@Composable
fun PetQuestions() {
    /*QuestionMultiple(
        stringResource(Res.string.question1), listOf(
            AnswerItem("test_1", stringResource = Res.string.question1answer1),
            AnswerItem("test_2", stringResource = Res.string.question1answer2),
            AnswerItem("test_2", stringResource = Res.string.question1answer2),
            AnswerItem("test_2", stringResource = Res.string.question1answer3),
            AnswerItem("test_2", stringResource = Res.string.question1answer2),
            AnswerItem("test_2", stringResource = Res.string.question1answer3)
        )
    ) {

    }*/

    PetCalendar()

    //QuestionSingleDetail(stringResource(Res.string.question1), "test"){
    //}

    //QuestionSingle("Sterilized?") {
    //}

    //ProgressInput("Dog activity", "Low", "High", 5, 1)
}

@Composable
fun PetPhoto() {
    var openDialog = remember { mutableStateOf(true) }
    CirclePhoto() {
        openDialog.value = true
    }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {},
            properties = DialogProperties(),
            confirmButton = {
                Button(
                    onClick = {
                        //openDialog.value = false
                    }) {
                    Text("Select")
                }
            },
            icon = {
                Icon(imageVector = Icons.Default.Star, "dialog icon")
            },
            title = { Text("Select photo") },
            text = { Text("Continue If you want to upload new main pet photo") },
            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                    }) {
                    Text("Cancel")
                }
            }
        )
    }
}
