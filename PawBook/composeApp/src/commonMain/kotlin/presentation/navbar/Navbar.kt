package presentation.navbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.pawbook_logo

@OptIn(ExperimentalResourceApi::class)
@Composable
fun Navbar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.pawbook_logo),
            contentDescription = "logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(200.dp, 120.dp)
        )
    }
}