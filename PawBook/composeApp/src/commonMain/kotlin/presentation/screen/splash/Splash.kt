package presentation.screen.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import domain.model.enums.SplashState
import org.koin.compose.koinInject
import presentation.screen.login.Logo

@Composable
fun Splash(viewModel: SplashViewModel = koinInject(), onReady: () -> Unit) {
    val splashState = viewModel.state.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo()
            Text("Načítavam...")
        }
    }

    LaunchedEffect(true) {
        viewModel.synchronizeDatabase()
    }

    if(splashState.value == SplashState.READY) {
        LaunchedEffect(true) {
            onReady()
        }
    }

}