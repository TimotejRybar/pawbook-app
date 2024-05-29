package presentation.components.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.screen.login.StyledButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoInternetDialog(onRetry: () -> Unit, onDismissRequest: () -> Unit) {
    BasicAlertDialog(onDismissRequest = { onDismissRequest() }) {
        Card {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(24.dp, 16.dp, 24.dp, 5.dp),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                text = "Vyskytla sa chyba")
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(24.dp, 5.dp),
                textAlign = TextAlign.Center,
                text = "Skontrolujte prosím svoje pripojenie k internetu")
            StyledButton("Skúsiť znovu") {
                onRetry()
            }
            Spacer(Modifier.height(20.dp))
        }
    }
}