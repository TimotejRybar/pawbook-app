package presentation.screen.register

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.enums.LoginState
import domain.model.enums.RegisterState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import pawbook.composeapp.generated.resources.Res
import pawbook.composeapp.generated.resources.pawbook_logo
import presentation.components.loading.LoadingAnimation
import presentation.screen.login.InputField
import presentation.screen.login.InputPasswordField
import presentation.screen.login.InputType
import presentation.screen.login.Logo
import presentation.screen.login.StyledButton
import presentation.theme.colors.LocalAppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(viewModel: RegisterViewModel = koinInject(), onCreateAccount: () -> Unit ) {
    val loginState by viewModel.state.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo()
            RegisterForm() { username, email, password ->
                viewModel.register(username, password, email) {
                    onCreateAccount()
                }
            }
            Footer()
        }
        AnimatedContent(targetState = loginState) { targetCount ->
            if (targetCount == RegisterState.LOADING) {
                LoadingAnimation()
            }

            val openAlertDialog = remember { mutableStateOf(true) }

            if (targetCount == RegisterState.NO_INTERNET) {
                if(openAlertDialog.value) {
                    BasicAlertDialog(onDismissRequest = { openAlertDialog.value = false }) {
                        Card {
                            Text("Please check your internet connection", Modifier.padding(24.dp))
                        }
                    }
                }
            }

            val openErrorDialog = remember { mutableStateOf(true) }

            if(targetCount == RegisterState.INVALID_EMAIL) {
                if(openErrorDialog.value) {
                    BasicAlertDialog(onDismissRequest = { openErrorDialog.value = false }) {
                        Card {
                            Text("This e-mail is already registered", Modifier.padding(24.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Footer() {
    Text("www.pawbook.com", fontSize = 10.sp)
}

@Composable
fun RegisterForm(onRegisterSubmit: (username: String, email: String, password: String) -> Unit) {
    val email = remember { mutableStateOf("")}
    val password = remember { mutableStateOf("")}
    val confirmPassword = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("")}

    InputField("Name", name.value, InputType.TEXT){
        name.value = it
    }
    Spacer(modifier = Modifier.height(5.dp))
    InputField("E-mail", email.value, InputType.EMAIL){
        email.value = it
    }
    Spacer(modifier = Modifier.height(5.dp))
    InputPasswordField("Password", password.value){
        password.value = it
    }
    Spacer(modifier = Modifier.height(5.dp))
    InputPasswordField("Confirm password", confirmPassword.value){
        confirmPassword.value = it
    }
    Spacer(modifier = Modifier.height(10.dp))
    RegisterButton() {
        onRegisterSubmit(name.value, email.value, password.value)
    }
}

@Composable
fun RegisterButton(onRegisterSubmit: () -> Unit) {
    // call submit on click
    StyledButton(text = "Create account", extraHorizontalPadding = 0.dp, onClick = onRegisterSubmit)
}